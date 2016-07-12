package packingup.core.utils;

import java.lang.Thread.UncaughtExceptionHandler;

import packingup.core.activities.R;
import android.app.Activity;
import android.app.AlertDialog;

public class ExceptionHandler implements UncaughtExceptionHandler {
	private Activity activity;

	public ExceptionHandler(Activity activity) {
		this.activity = activity;
	}

	public static void showException(Activity activity, int errorMap) {
		AlertDialog.Builder builder = MyAlertDialog.createAlertDialog(activity, R.drawable.ic_launcher,
				R.string.error, errorMap);
		builder.create().show();
	}

	@Override
	public void uncaughtException(Thread arg0, Throwable arg1) {
		AlertDialog.Builder builder = MyAlertDialog.createAlertDialog(activity, R.drawable.ic_launcher,
				R.string.error, R.string.error_message);
		builder.create().show();
	}
}
