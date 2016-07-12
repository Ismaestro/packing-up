package packingup.core.utils;

import packingup.core.activities.R;
import android.app.Activity;
import android.widget.LinearLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

public class AdMob {
	private static final String ADMOB_UID = "a1535d8197bd594";
	private static final String XPERIA_DEVICE = "DFDE4671A4AB78C0A75878ECD134F1B1";

	public static void createAds(Activity activity) {
		AdView adView = new AdView(activity);
		adView.setAdUnitId(ADMOB_UID);
		adView.setAdSize(AdSize.SMART_BANNER);

		LinearLayout layout = (LinearLayout) activity.findViewById(R.id.adsLayout);
		layout.addView(adView);
		AdRequest adRequest = new AdRequest.Builder().addTestDevice(XPERIA_DEVICE).build();
		adView.loadAd(adRequest);
	}
}
