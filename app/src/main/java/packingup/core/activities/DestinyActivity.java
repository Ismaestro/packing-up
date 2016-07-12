package packingup.core.activities;

import java.util.List;
import java.util.Locale;

import packingup.core.utils.CustomToast;
import packingup.core.utils.Font;
import packingup.core.utils.MyAlertDialog;
import packingup.core.utils.OptionMenu;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.crittercism.app.Crittercism;
import com.google.analytics.tracking.android.EasyTracker;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;

public class DestinyActivity extends ActionBarActivity {

	public static final String DESTINY_LATITUDE = "latitudeDestiny";
	public static final String DESTINY_LONGITUDE = "longitudeDestiny";

	private static GoogleMap destinyMap;
	private Button yesButton;
	private Button noButton;

	private Address address;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Crittercism.initialize(getApplicationContext(), MainActivity.CRITTERCISM_ID);
		setContentView(R.layout.activity_destiny);

		yesButton = (Button) findViewById(R.id.button1);
		noButton = (Button) findViewById(R.id.button2);

		setFonts();

		yesButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				launchInformationActivity(address, destinyMap.getCameraPosition().target);
			}
		});

		noButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				tryAgainAction(R.string.try_again_basic);
			}
		});

		final Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			String destiny = bundle.getString(MainActivity.DESTINY);
			showDestinyAction(destiny);
		} else
			CustomToast.show(DestinyActivity.this, getString(R.string.no_destiny), Toast.LENGTH_LONG);

	}

	private void setFonts() {
		TextView textView1 = (TextView) findViewById(R.id.textView1);
		Typeface face = Font.getEbrimaFont(getBaseContext());
		textView1.setTypeface(face);
		yesButton.setTypeface(face);
		noButton.setTypeface(face);
	}

	private void showDestinyAction(String destiny) {
		try {
			MapFragment fragMapa = (MapFragment) getFragmentManager().findFragmentById(R.id.map);

			destinyMap = fragMapa.getMap();
			destinyMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
			destinyMap.setBuildingsEnabled(true);
			destinyMap.getUiSettings().setCompassEnabled(true);

			Geocoder geocoder = new Geocoder(this, Locale.getDefault());
			List<Address> geoResults = geocoder.getFromLocationName(destiny, 1);
			geoResults = geocoder.getFromLocationName(destiny, 1);

			if (geoResults.size() > 0) {
				address = geoResults.get(0);
				CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(address.getLatitude(),
						address.getLongitude()));
				CameraUpdate zoom = CameraUpdateFactory.zoomTo(13);

				destinyMap.moveCamera(center);
				destinyMap.animateCamera(zoom);
			} else
				tryAgainAction(R.string.try_again_complete);
		} catch (Exception e) {
			e.printStackTrace();
			AlertDialog.Builder builder = MyAlertDialog.createAlertDialog(this, R.drawable.map,
					R.string.error, R.string.error_map);
			builder.setPositiveButton(R.string.continue_text, new OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					launchInformationActivity(address, destinyMap.getCameraPosition().target);
				}
			});
			builder.create().show();
		}
	}

	private void launchInformationActivity(Address address, LatLng target) {
		final Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			bundle.putDouble(DESTINY_LATITUDE, target.latitude);
			bundle.putDouble(DESTINY_LONGITUDE, target.longitude);
			if (address != null)
				bundle.putString(MainActivity.DESTINY, address.getFeatureName());

			final Intent intent = new Intent(this, InformationActivity.class);
			intent.putExtras(bundle);
			startActivity(intent);
		} else
			CustomToast.show(DestinyActivity.this, getString(R.string.no_destiny), Toast.LENGTH_LONG);
	}

	private void tryAgainAction(int message) {
		final Intent intent = new Intent(getBaseContext(), MainActivity.class);
		CustomToast.show(DestinyActivity.this, getString(message), Toast.LENGTH_LONG);
		finish();
		startActivity(intent);
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
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.destiny, menu);
		OptionMenu.setShareMenu(menu, getString(R.string.share_message));
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		OptionMenu optionMenu = new OptionMenu(DestinyActivity.this);
		switch (item.getItemId()) {
		case R.id.action_rate_me:
			return optionMenu.showRatePage();
		case R.id.action_feedback:
			return optionMenu.sendFeedBack();
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
