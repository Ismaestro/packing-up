package packingup.core.activities;

import packingup.core.utils.AdMob;
import packingup.core.utils.CustomToast;
import packingup.core.utils.Font;
import packingup.core.utils.OptionMenu;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.crittercism.app.Crittercism;
import com.google.analytics.tracking.android.EasyTracker;

public class InformationActivity extends ActionBarActivity {

	public static final String WOMEN = "women";
	public static final String MEN = "men";
	public static final String CHILDREN = "children";
	public static final String HOLIDAY_TRAVEL = "holidayTravel";
	public static final String WORK_TRAVEL = "workTravel";

	private CheckBox womenCheckBox;
	private CheckBox menCheckBox;
	private CheckBox childrenCheckBox;
	private RadioGroup purposeRadioGroup;
	private Button viewListButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Crittercism.initialize(getApplicationContext(), MainActivity.CRITTERCISM_ID);
		setContentView(R.layout.activity_information);
		AdMob.createAds(this);

		womenCheckBox = (CheckBox) findViewById(R.id.checkBox1);
		menCheckBox = (CheckBox) findViewById(R.id.checkBox2);
		childrenCheckBox = (CheckBox) findViewById(R.id.checkBox3);
		purposeRadioGroup = (RadioGroup) findViewById(R.id.radioGroup1);
		viewListButton = (Button) findViewById(R.id.button1);

		setFonts();

		viewListButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				ExpandableListActivity.RESET_LIST_FLAG = true;
				launchListActivity();
			}
		});
	}

	private void setFonts() {
		Typeface face = Font.getEbrimaFont(getBaseContext());
		((TextView) findViewById(R.id.textView1)).setTypeface(face);
		((TextView) findViewById(R.id.textView2)).setTypeface(face);
		((TextView) findViewById(R.id.textView3)).setTypeface(face);
		womenCheckBox.setTypeface(face);
		menCheckBox.setTypeface(face);
		childrenCheckBox.setTypeface(face);

		viewListButton.setTypeface(face);
	}

	private void launchListActivity() {
		int radioButtonID = purposeRadioGroup.getCheckedRadioButtonId();
		if (radioButtonID == -1) {
			CustomToast.show(InformationActivity.this, getString(R.string.no_purpose),
					Toast.LENGTH_SHORT);
			return;
		}

		if (!womenCheckBox.isChecked() && !menCheckBox.isChecked()) {
			CustomToast.show(InformationActivity.this, getString(R.string.no_passengers),
					Toast.LENGTH_SHORT);
			return;
		}

		final Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			if (radioButtonID == R.id.radio1) {
				bundle.putBoolean(HOLIDAY_TRAVEL, true);
				bundle.putBoolean(WORK_TRAVEL, false);
			} else {
				bundle.putBoolean(HOLIDAY_TRAVEL, false);
				bundle.putBoolean(WORK_TRAVEL, true);
			}
			bundle.putBoolean(WOMEN, womenCheckBox.isChecked());
			bundle.putBoolean(MEN, menCheckBox.isChecked());
			bundle.putBoolean(CHILDREN, childrenCheckBox.isChecked());

			final Intent intent = new Intent(this, ExpandableListActivity.class);
			intent.putExtras(bundle);
			startActivity(intent);
		} else
			CustomToast.show(InformationActivity.this, getString(R.string.error), Toast.LENGTH_SHORT);
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
		getMenuInflater().inflate(R.menu.information, menu);
		OptionMenu.setShareMenu(menu, getString(R.string.share_message));
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		OptionMenu optionMenu = new OptionMenu(InformationActivity.this);
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
