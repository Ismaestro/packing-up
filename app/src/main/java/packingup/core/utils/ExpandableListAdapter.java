package packingup.core.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import packingup.core.activities.ExpandableListActivity;
import packingup.core.activities.MainActivity;
import packingup.core.activities.R;
import packingup.model.Item;
import packingup.persistence.ItemDataSource;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ExpandableListAdapter extends BaseExpandableListAdapter {

	static class ViewHolder {
		CheckBox checkBox;
		ImageView imageButton;
	}

	private Activity context;
	private Map<String, List<Item>> items;
	private List<String> categories;
	private Map<String, boolean[]> checkStates;
	private ProgressBar progressBar;

	@SuppressLint("UseSparseArrays")
	public ExpandableListAdapter(Activity context, List<String> categories,
			Map<String, List<Item>> items, ProgressBar progressBar) {
		this.context = context;
		this.items = items;
		this.categories = categories;
		this.progressBar = progressBar;
		this.checkStates = new HashMap<String, boolean[]>();

		initializeCheckStates();
	}

	@SuppressLint("UseSparseArrays")
	public ExpandableListAdapter(Activity context, List<String> categories,
			Map<String, List<Item>> items, ProgressBar progressBar, Map<String, boolean[]> checkStates) {
		this.context = context;
		this.items = items;
		this.categories = categories;
		this.progressBar = progressBar;
		this.checkStates = checkStates;

		if (checkStates == null)
			initializeCheckStates();
	}

	private void initializeCheckStates() {
		for (Entry<String, List<Item>> entry : items.entrySet()) {
			checkStates.put(entry.getKey(), new boolean[entry.getValue().size()]);
		}
	}

	@Override
	public Item getChild(int groupPosition, int childPosition) {
		return items.get(categories.get(groupPosition)).get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild,
			View convertView, ViewGroup parent) {
		final ViewHolder holder;
		final Item child = getChild(groupPosition, childPosition);

		LayoutInflater inflater = context.getLayoutInflater();

		if (convertView == null) {
			convertView = inflater.inflate(R.layout.child_item, null);
			holder = new ViewHolder();
			holder.checkBox = (CheckBox) convertView.findViewById(R.id.checkBox);
			holder.imageButton = (ImageView) convertView.findViewById(R.id.delete);
			convertView.setTag(holder);
		} else
			holder = (ViewHolder) convertView.getTag();

		holder.checkBox.setOnCheckedChangeListener(null);

		if (checkStates.containsKey(categories.get(groupPosition))) {
			boolean getChecked[] = checkStates.get(categories.get(groupPosition));
			holder.checkBox.setChecked(getChecked[childPosition]);
		} else {
			boolean getChecked[] = new boolean[getChildrenCount(groupPosition)];
			checkStates.put(categories.get(groupPosition), getChecked);
			holder.checkBox.setChecked(false);
		}

		holder.checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					boolean getChecked[] = checkStates.get(categories.get(groupPosition));
					getChecked[childPosition] = isChecked;
					checkStates.put(categories.get(groupPosition), getChecked);
				} else {
					boolean getChecked[] = checkStates.get(categories.get(groupPosition));
					getChecked[childPosition] = isChecked;
					checkStates.put(categories.get(groupPosition), getChecked);
				}

				updateProgressBar();
				notifyDataSetChanged();

				if (areAllSelected()) {
					AlertDialog.Builder builder = MyAlertDialog.createAlertDialog(context,
							R.drawable.ic_launcher, R.string.congratulations, R.string.end_message);
					builder.setCancelable(false);
					builder.setPositiveButton(R.string.acept, null);
					builder.setNeutralButton(R.string.finalize, new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							final Intent intent = new Intent(context, MainActivity.class);
							context.startActivity(intent);
						}
					});
					builder.create().show();
				}
			}
		});

		holder.checkBox.setTypeface(ExpandableListActivity.typeFace);
		holder.checkBox.setText(child.getText());
		holder.imageButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog.Builder builder = MyAlertDialog.createAlertDialog(context,
						R.drawable.ic_launcher_minus, R.string.remove_item, R.string.remove);

				builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {
						List<Item> childs = items.get(categories.get(groupPosition));
						ItemDataSource itemDataSource = new ItemDataSource(context, Locale.getDefault());
						itemDataSource.removeItem(childs.get(childPosition));
						childs.remove(childPosition);
						notifyDataSetChanged();
						updateCheckStates();
					}
				});

				builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				});

				builder.create().show();
			}
		});

		return convertView;
	}

	public void updateProgressBar() {
		int selected = 0;
		for (Entry<String, boolean[]> entry : checkStates.entrySet())
			for (boolean b : entry.getValue())
				if (b == true)
					selected++;
		progressBar.setProgress(selected * 100 / getTotalItems());

		TextView textView1 = (TextView) context.findViewById(R.id.textView1);
		textView1.setTypeface(ExpandableListActivity.typeFace);
		textView1.setText(progressBar.getProgress() + "%");
	}

	private int getTotalItems() {
		int total = 0;
		for (Entry<String, List<Item>> entry : items.entrySet()) {
			total += entry.getValue().size();
		}
		return total;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return items.get(categories.get(groupPosition)).size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return categories.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return categories.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
		String category = (String) getGroup(groupPosition);
		if (convertView == null) {
			LayoutInflater infalInflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(R.layout.group_item, null);
		}
		TextView item = (TextView) convertView.findViewById(R.id.item);
		item.setTypeface(ExpandableListActivity.typeFace, Typeface.BOLD);
		item.setText(category);

		TextView selected = (TextView) convertView.findViewById(R.id.textView2);
		selected.setTypeface(ExpandableListActivity.typeFace, Typeface.BOLD);
		selected.setText(getNumberSelectedItems(category) + "/" + checkStates.get(category).length);
		return convertView;
	}

	private String getNumberSelectedItems(String category) {
		int selected = 0;
		for (boolean b : checkStates.get(category)) {
			if (b)
				selected++;
		}
		return String.valueOf(selected);
	}

	public Map<String, boolean[]> getCheckStates() {
		return checkStates;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

	public boolean areAllSelected() {
		boolean result = true;
		for (Entry<String, boolean[]> entry : checkStates.entrySet()) {
			for (boolean b : entry.getValue())
				if (b == false)
					result = false;
		}
		return result;
	}

	public void update(List<String> categories, Map<String, List<Item>> items) {
		this.items = items;
		this.categories = categories;

		updateCheckStates();
	}

	private void updateCheckStates() {
		Map<String, boolean[]> copy = new HashMap<String, boolean[]>(checkStates);

		for (Entry<String, List<Item>> entry : items.entrySet()) {
			checkStates.put(entry.getKey(), new boolean[entry.getValue().size()]);
		}

		for (Entry<String, boolean[]> entry : checkStates.entrySet()) {
			if (entry.getValue().length >= copy.get(entry.getKey()).length)
				System.arraycopy(copy.get(entry.getKey()), 0, entry.getValue(), 0,
						copy.get(entry.getKey()).length);
			else
				System.arraycopy(copy.get(entry.getKey()), 0, entry.getValue(), 0,
						entry.getValue().length);
		}

		updateProgressBar();
	}

	public boolean atLeastOneChecked() {
		for (Entry<String, boolean[]> entry : checkStates.entrySet()) {
			for (boolean b : entry.getValue())
				if (b == true)
					return true;
		}
		return false;
	}

}