package packingup.core.utils;

import packingup.core.activities.InformationActivity;
import packingup.core.activities.R;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

public class MyAlertDialog {
	public static Builder createAlertDialog(Context context, int icon, int title, int message) {
		AlertDialog.Builder builder = createAlertDialog(context, icon, title);
		builder.setMessage(context.getString(message));
		return builder;
	}

	public static Builder createAlertDialog(Context context, int icon, int title) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setIcon(icon);
		builder.setTitle(context.getString(title));
		return builder;
	}

	public static Builder createConfirmDialog(final Context context) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context)
				.setIcon(android.R.drawable.ic_dialog_alert).setTitle(R.string.are_sure)
				.setMessage(R.string.lose_data)
				.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Intent intent = new Intent(context, InformationActivity.class);
						context.startActivity(intent);
					}
				}).setNegativeButton(R.string.no, null);
		return builder;
	}

}
