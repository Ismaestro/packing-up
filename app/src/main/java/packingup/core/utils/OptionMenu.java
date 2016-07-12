package packingup.core.utils;

import packingup.core.activities.ExpandableListActivity;
import packingup.core.activities.MainActivity;
import packingup.core.activities.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class OptionMenu {

	private static final String ID = "packingup.core.activities";
	private static final String EMAIL = "isma091@gmail.com";

	private Activity activity;

	public OptionMenu(Activity activity) {
		this.activity = activity;
	}

	public boolean showRatePage() {
		Uri uri = Uri.parse("market://details?id=" + ID);
		Intent myAppLinkToMarket = new Intent(Intent.ACTION_VIEW, uri);
		try {
			activity.startActivity(myAppLinkToMarket);
		} catch (ActivityNotFoundException e) {
			CustomToast.show(activity, activity.getString(R.string.error_market), Toast.LENGTH_LONG);
		}
		return true;
	}

	public boolean sendFeedBack() {
		Intent Email = new Intent(Intent.ACTION_SEND);
		Email.setType("text/email");
		Email.putExtra(Intent.EXTRA_EMAIL, new String[] { EMAIL });
		Email.putExtra(Intent.EXTRA_SUBJECT, activity.getString(R.string.email_subject));
		Email.putExtra(Intent.EXTRA_TEXT, activity.getString(R.string.email_body));
		activity.startActivity(Intent.createChooser(Email, activity.getString(R.string.email_title)));
		return true;
	}

	public boolean showInstructions() {
		LayoutInflater newItemLayoutInflater = LayoutInflater.from(activity);
		View newItemView = newItemLayoutInflater.inflate(R.layout.instructions, null);

		AlertDialog.Builder builder = MyAlertDialog.createAlertDialog(activity, R.drawable.tips,
				R.string.instructions);
		builder.setView(newItemView);

		builder.setPositiveButton(R.string.acept, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});
		builder.create().show();
		return true;
	}

	public static void setShareMenu(Menu menu, String message) {
		MenuItem shareItem = menu.findItem(R.id.action_share);
		ShareActionProvider mShareActionProvider = (ShareActionProvider) MenuItemCompat
				.getActionProvider(shareItem);

		Intent shareIntent = new Intent();
		shareIntent.setAction(Intent.ACTION_SEND);
		shareIntent.putExtra(Intent.EXTRA_TEXT, message
				+ "https://play.google.com/store/apps/details?id=" + ID);
		shareIntent.setType("text/plain");
		if (mShareActionProvider != null)
			mShareActionProvider.setShareIntent(shareIntent);
	}

	public boolean cancelTrip() {
		ExpandableListAdapter expListAdapter = ((ExpandableListActivity) activity)
				.getExpandableListAdapter();
		if (expListAdapter.atLeastOneChecked()) {
			new AlertDialog.Builder(activity).setIcon(android.R.drawable.ic_dialog_alert)
					.setTitle(R.string.are_sure).setMessage(R.string.lose_data)
					.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							resetApplicationState();
						}
					}).setNegativeButton(R.string.no, null).show();
		} else
			resetApplicationState();
		return true;
	}

	private void resetApplicationState() {
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity
				.getApplicationContext());
		SharedPreferences.Editor preferencesEditor = sharedPreferences.edit();
		preferencesEditor.remove(MainActivity.ON_GOING);
		preferencesEditor.remove(ExpandableListActivity.GROUP_LIST);
		preferencesEditor.remove(ExpandableListActivity.CHILD_LIST);
		preferencesEditor.remove(ExpandableListActivity.TITLE_DESTINY);
		preferencesEditor.commit();
		ExpandableListActivity.RESET_LIST_FLAG = true;
		final Intent intent = new Intent(activity, MainActivity.class);
		activity.startActivity(intent);
	}
}
