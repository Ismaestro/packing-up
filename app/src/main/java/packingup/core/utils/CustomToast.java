package packingup.core.utils;

import packingup.core.activities.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class CustomToast {
	public static void show(Context context, String text, int duration) {
		LayoutInflater inflater = LayoutInflater.from(context);
		View layout = inflater.inflate(R.layout.toast, null);

		ImageView image = (ImageView) layout.findViewById(R.id.toast_image);
		image.setImageResource(R.drawable.ic_launcher);

		TextView textV = (TextView) layout.findViewById(R.id.toast_text);
		textV.setText(text);

		Toast toast = new Toast(context);
		toast.setDuration(duration);
		toast.setView(layout);
		toast.show();
	}
}
