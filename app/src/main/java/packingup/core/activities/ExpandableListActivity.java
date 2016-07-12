package packingup.core.activities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import packingup.core.utils.AdMob;
import packingup.core.utils.CustomToast;
import packingup.core.utils.ExpandableListAdapter;
import packingup.core.utils.Font;
import packingup.core.utils.MyAlertDialog;
import packingup.core.utils.OptionMenu;
import packingup.core.utils.SpinnerSelectedListener;
import packingup.model.Item;
import packingup.persistence.ItemDataSource;
import packingup.persistence.ItemQuery;
import packingup.persistence.ItemQuery.Category;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.crittercism.app.Crittercism;
import com.google.analytics.tracking.android.EasyTracker;
import com.google.gson.Gson;

public class ExpandableListActivity extends Activity {

	public static Typeface typeFace;

	public static final String GROUP_LIST = "groupList";
	public static final String CHILD_LIST = "childList";
	public static final String ITEMS_COLLECTION = "itemsCollection";
	public static final String CHECK_STATES = "checkStates";
	public static final String TITLE_DESTINY = "title";
	public static final String FILE_DATA = "data";

	public static boolean RESET_LIST_FLAG = false;

	private List<String> groupList;
	private List<Item> childList;
	private Map<String, List<Item>> itemsCollection;
	private ExpandableListView expListView;
	private ProgressBar progressBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Crittercism.initialize(getApplicationContext(), MainActivity.CRITTERCISM_ID);
		setContentView(R.layout.activity_expandablelist);
		AdMob.createAds(this);

		setTitle(getTitleWithDestiny());

		typeFace = Typeface.createFromAsset(getAssets(), "fonts/Ebrima.ttf");

		progressBar = (ProgressBar) findViewById(R.id.progressBar1);
		expListView = (ExpandableListView) findViewById(R.id.item_list);

		final ExpandableListAdapter expListAdapter = createAdapter();

		expListView.setAdapter(expListAdapter);

		expListAdapter.updateProgressBar();

		expListView.setOnChildClickListener(new OnChildClickListener() {
			@Override
			public boolean onChildClick(ExpandableListView parent, View v, int groupPosition,
					int childPosition, long id) {
				final Item selected = expListAdapter.getChild(groupPosition, childPosition);
				CustomToast.show(ExpandableListActivity.this, selected.getText(), Toast.LENGTH_LONG);
				return true;
			}
		});
	}

	@SuppressWarnings("unchecked")
	private ExpandableListAdapter createAdapter() {
		final ExpandableListAdapter expListAdapter;

		SharedPreferences sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(getApplicationContext());
		if (existsOldTrip()) {
			Gson gson = new Gson();
			String json = sharedPreferences.getString(GROUP_LIST, "");
			groupList = gson.fromJson(json, List.class);
			json = sharedPreferences.getString(CHILD_LIST, "");
			childList = gson.fromJson(json, List.class);
			setTitle(sharedPreferences.getString(TITLE_DESTINY, getString(R.string.title_activity_list)));

			itemsCollection = (Map<String, List<Item>>) getObjectFromFile(ITEMS_COLLECTION);

			Map<String, boolean[]> checkStates = (Map<String, boolean[]>) getObjectFromFile(CHECK_STATES);

			expListAdapter = new ExpandableListAdapter(this, groupList, itemsCollection, progressBar,
					checkStates);
		} else {
			final ItemDataSource itemDataSource = new ItemDataSource(getApplicationContext(),
					Locale.getDefault());

			loadItemsFromDB(itemDataSource);

			expListAdapter = new ExpandableListAdapter(this, groupList, itemsCollection, progressBar);
		}
		return expListAdapter;
	}

	private boolean existsOldTrip() {
		SharedPreferences sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(getApplicationContext());
		if (sharedPreferences.getBoolean(MainActivity.ON_GOING, false) == true && !RESET_LIST_FLAG)
			return true;
		else
			return false;
	}

	private Object getObjectFromFile(String name) {
		Object object;
		File file = new File(getDir(FILE_DATA, MODE_PRIVATE), name);
		try {
			ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(file));
			object = inputStream.readObject();
			inputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
			CustomToast.show(ExpandableListActivity.this, getString(R.string.error), Toast.LENGTH_LONG);
			object = null;
		}
		return object;
	}

	private String getTitleWithDestiny() {
		final Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			String destiny = bundle.getString(MainActivity.DESTINY);
			return getString(R.string.travel_to) + " " + destiny;
		}
		return getString(R.string.title_activity_list);
	}

	protected void loadItemsFromDB(ItemDataSource itemDataSource) {
		Bundle bundle = getIntent().getExtras();
		if (bundle == null) {
			bundle = restoreBundleFromPreferences();
		}

		List<Item> finalList = elaborateFinalList(bundle, itemDataSource);
		createGroupList(bundle);
		createChildList(itemDataSource, finalList);
	}

	private Bundle restoreBundleFromPreferences() {
		SharedPreferences sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(getApplicationContext());
		Bundle bundle = new Bundle();
		bundle.putBoolean(InformationActivity.WOMEN,
				sharedPreferences.getBoolean(InformationActivity.WOMEN, false));
		bundle.putBoolean(InformationActivity.MEN,
				sharedPreferences.getBoolean(InformationActivity.MEN, true));
		bundle.putBoolean(InformationActivity.CHILDREN,
				sharedPreferences.getBoolean(InformationActivity.CHILDREN, false));
		bundle.putBoolean(InformationActivity.HOLIDAY_TRAVEL,
				sharedPreferences.getBoolean(InformationActivity.HOLIDAY_TRAVEL, true));

		return bundle;
	}

	private List<Item> elaborateFinalList(Bundle bundle, ItemDataSource itemDataSource) {
		List<Item> itemsByCategory = new ArrayList<Item>();
		boolean holidaysTravel = bundle.getBoolean(InformationActivity.HOLIDAY_TRAVEL);
		boolean womenCheckBox = bundle.getBoolean(InformationActivity.WOMEN);
		boolean menCheckBox = bundle.getBoolean(InformationActivity.MEN);
		boolean childrenCheckBox = bundle.getBoolean(InformationActivity.CHILDREN);

		if (holidaysTravel)
			itemsByCategory = itemDataSource.getHolidaysItems();
		else
			itemsByCategory = itemDataSource.getWorkItems();

		List<Item> itemsByGender = itemDataSource.getItemsByGender(ItemQuery.Gender.ALL);
		if (womenCheckBox)
			itemsByGender.addAll(itemDataSource.getItemsByGender(ItemQuery.Gender.WOMEN));
		if (menCheckBox)
			itemsByGender.addAll(itemDataSource.getItemsByGender(ItemQuery.Gender.MEN));
		if (childrenCheckBox)
			itemsByGender.addAll(itemDataSource.getItemsByGender(ItemQuery.Gender.CHILDREN));

		List<Item> finalList = new ArrayList<Item>(itemsByCategory);
		finalList.retainAll(itemsByGender);

		return finalList;
	}

	private void createGroupList(Bundle bundle) {
		boolean holidaysTravel = bundle.getBoolean(InformationActivity.HOLIDAY_TRAVEL);
		groupList = new LinkedList<String>();

		groupList.add(getString(R.string.category_documentation));
		groupList.add(getString(R.string.category_clothes));
		groupList.add(getString(R.string.category_dressing_case));
		groupList.add(getString(R.string.category_media));

		if (holidaysTravel) {
			groupList.add(getString(R.string.category_mount));
			groupList.add(getString(R.string.category_beach));
			groupList.add(getString(R.string.category_kit));
		} else
			groupList.add(getString(R.string.category_work));

		groupList.add(getString(R.string.category_others));
	}

	private void createChildList(ItemDataSource itemDataSource, List<Item> finalList) {
		itemsCollection = new HashMap<String, List<Item>>();

		for (String item : groupList) {
			if (item.equals(getString(R.string.category_documentation))) {
				loadChild(getSortedArrayByCategory(itemDataSource, Category.DOCUMENTATION, finalList));
			} else if (item.equals(getString(R.string.category_clothes)))
				loadChild(getSortedArrayByCategory(itemDataSource, Category.CLOTHES, finalList));
			else if (item.equals(getString(R.string.category_dressing_case)))
				loadChild(getSortedArrayByCategory(itemDataSource, Category.DRESSING_CASE, finalList));
			else if (item.equals(getString(R.string.category_media)))
				loadChild(getSortedArrayByCategory(itemDataSource, Category.MEDIA, finalList));
			else if (item.equals(getString(R.string.category_mount)))
				loadChild(getSortedArrayByCategory(itemDataSource, Category.MOUNT, finalList));
			else if (item.equals(getString(R.string.category_beach)))
				loadChild(getSortedArrayByCategory(itemDataSource, Category.BEACH, finalList));
			else if (item.equals(getString(R.string.category_work)))
				loadChild(getSortedArrayByCategory(itemDataSource, Category.WORK, finalList));
			else if (item.equals(getString(R.string.category_kit)))
				loadChild(getSortedArrayByCategory(itemDataSource, Category.KIT, finalList));
			else
				loadChild(getSortedArrayByCategory(itemDataSource, Category.OTHERS, finalList));

			itemsCollection.put(item, childList);
		}
	}

	private Item[] getSortedArrayByCategory(ItemDataSource itemDataSource, Category category,
			List<Item> finalList) {
		Item[] array = itemDataSource.getItemsByCategory(category, finalList);
		Arrays.sort(array);
		return array;
	}

	private void loadChild(Item[] items) {
		childList = new ArrayList<Item>();
		for (Item item : items)
			childList.add(item);
	}

	private boolean showAddNewItemAlertDialog() {
		LayoutInflater newItemLayoutInflater = LayoutInflater.from(ExpandableListActivity.this);
		View newItemView = newItemLayoutInflater.inflate(R.layout.add_new_item, null);

		final EditText nameEditText = (EditText) newItemView.findViewById(R.id.editText1);
		final Spinner categorySpinner = (Spinner) newItemView.findViewById(R.id.Spinner1);
		final Spinner genderSpinner = (Spinner) newItemView.findViewById(R.id.Spinner2);
		final Button addButton = (Button) newItemView.findViewById(R.id.button1);
		final ItemDataSource itemDataSource = new ItemDataSource(getApplicationContext(),
				Locale.getDefault());

		setFonts(nameEditText, addButton);

		categorySpinner.setOnItemSelectedListener(new SpinnerSelectedListener());
		genderSpinner.setOnItemSelectedListener(new SpinnerSelectedListener());

		AlertDialog.Builder builder = MyAlertDialog.createAlertDialog(ExpandableListActivity.this,
				R.drawable.ic_launcher_plus, R.string.add_new_item);
		builder.setView(newItemView);

		final AlertDialog alertDialog = builder.create();
		alertDialog.show();

		addButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				if (nameEditText.getText().toString().isEmpty())
					CustomToast.show(ExpandableListActivity.this, getString(R.string.name_empty),
							Toast.LENGTH_SHORT);

				else if (!itemDataSource.existsItem(nameEditText.getText().toString())) {
					itemDataSource.createItem(nameEditText.getText().toString(),
							String.valueOf(categorySpinner.getSelectedItem()),
							String.valueOf(genderSpinner.getSelectedItem()));
					loadItemsFromDB(itemDataSource);

					ExpandableListAdapter adapter = (ExpandableListAdapter) expListView
							.getExpandableListAdapter();

					adapter.update(groupList, itemsCollection);
					adapter.notifyDataSetChanged();

					alertDialog.hide();
				} else
					CustomToast.show(ExpandableListActivity.this, getString(R.string.exists),
							Toast.LENGTH_LONG);
			}
		});
		return true;
	}

	public ExpandableListAdapter getExpandableListAdapter() {
		return (ExpandableListAdapter) expListView.getExpandableListAdapter();
	}

	private void setFonts(EditText nameEditText, Button addButton) {
		Typeface face = Font.getEbrimaFont(getBaseContext());
		nameEditText.setTypeface(face);
		addButton.setTypeface(face);
	}

	@Override
	public void onStart() {
		super.onStart();
		EasyTracker.getInstance(this).activityStart(this);
	}

	@Override
	public void onStop() {
		super.onStop();
		EasyTracker.getInstance(this).activityStop(this);
		saveStateTravel();
	}

	private void saveStateTravel() {
		ExpandableListAdapter expListAdapter = (ExpandableListAdapter) expListView
				.getExpandableListAdapter();
		if (!expListAdapter.areAllSelected()) {
			SharedPreferences sharedPreferences = PreferenceManager
					.getDefaultSharedPreferences(getApplicationContext());
			SharedPreferences.Editor preferencesEditor = sharedPreferences.edit();
			preferencesEditor.putBoolean(MainActivity.ON_GOING, true);
			Gson gson = new Gson();
			String json = gson.toJson(groupList);
			preferencesEditor.putString(GROUP_LIST, json);
			json = gson.toJson(childList);
			preferencesEditor.putString(CHILD_LIST, json);
			preferencesEditor.putString(TITLE_DESTINY, (String) getTitle());

			preferencesEditor.commit();

			setObjectToFile(ITEMS_COLLECTION, itemsCollection);
			setObjectToFile(CHECK_STATES, expListAdapter.getCheckStates());

			if (getIntent().getExtras() != null) {
				saveBundle(preferencesEditor, getIntent().getExtras());
			}
		}
	}

	private void saveBundle(Editor editor, Bundle bundle) {
		Set<String> keySet = bundle.keySet();
		Iterator<String> it = keySet.iterator();

		while (it.hasNext()) {
			String key = it.next();
			Object o = bundle.get(key);
			if (o == null) {
				editor.remove(key);
			} else if (o instanceof Integer) {
				editor.putInt(key, (Integer) o);
			} else if (o instanceof Long) {
				editor.putLong(key, (Long) o);
			} else if (o instanceof Boolean) {
				editor.putBoolean(key, (Boolean) o);
			} else if (o instanceof CharSequence) {
				editor.putString(key, ((CharSequence) o).toString());
			}
		}

		editor.commit();
	}

	private void setObjectToFile(String name, Object object) {
		File file = new File(getDir(FILE_DATA, MODE_PRIVATE), name);
		try {
			ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(file));
			outputStream.writeObject(object);
			outputStream.flush();
			outputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
			CustomToast.show(ExpandableListActivity.this, getString(R.string.error), Toast.LENGTH_LONG);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.expandablelist, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		OptionMenu optionMenu = new OptionMenu(ExpandableListActivity.this);
		switch (item.getItemId()) {
		case R.id.action_addNew:
			return showAddNewItemAlertDialog();
		case R.id.action_rate_me:
			return optionMenu.showRatePage();
		case R.id.action_feedback:
			return optionMenu.sendFeedBack();
		case R.id.action_instructions:
			return optionMenu.showInstructions();
		case R.id.action_cancel_trip:
			return optionMenu.cancelTrip();

		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onBackPressed() {
		CustomToast
				.show(ExpandableListActivity.this, getString(R.string.cancel_info), Toast.LENGTH_LONG);
	}
}
