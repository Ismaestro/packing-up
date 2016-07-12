package packingup.core.activities;

import java.util.List;

import packingup.core.utils.AdMob;
import packingup.core.utils.ChangeLog;
import packingup.core.utils.CustomToast;
import packingup.core.utils.Font;
import packingup.core.utils.OptionMenu;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.speech.RecognizerIntent;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.crittercism.app.Crittercism;
import com.google.analytics.tracking.android.EasyTracker;

public class MainActivity extends ActionBarActivity {

	protected static final String CRITTERCISM_ID = "535e212cb573f173c9000006";

	public static final String DESTINY = "destiny";
	public static final String ON_GOING = "ongoing";

	private Button startButton;
	private ImageButton speechImageButton;
	private EditText destinyEditText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Crittercism.initialize(getApplicationContext(), CRITTERCISM_ID);
		setContentView(R.layout.activity_main);
		AdMob.createAds(this);

		checkOtherTravel();

		destinyEditText = (EditText) findViewById(R.id.editText1);
		destinyEditText.setOnEditorActionListener(new EditText.OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_NEXT) {
					launchDestinyActivity();
					return true;
				}
				return false;
			}
		});

		startButton = (Button) findViewById(R.id.button1);

		setFonts();
		showChangeLog();

		startButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				launchDestinyActivity();
			}
		});

		speechImageButton = (ImageButton) findViewById(R.id.imageButton1);

		speechImageButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
				intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speech Recognition");
				startActivityForResult(intent, 0);
			}
		});
	}

	private void checkOtherTravel() {
		SharedPreferences sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(getApplicationContext());
		// sharedPreferences.edit().clear().commit();
		if (sharedPreferences.getBoolean(ON_GOING, false) == true
				&& !ExpandableListActivity.RESET_LIST_FLAG) {
			final Intent intent = new Intent(this, ExpandableListActivity.class);
			startActivity(intent);
		}
	}

	private void showChangeLog() {
		ChangeLog cl = new ChangeLog(this);
		if (cl.firstRun())
			cl.getLogDialog().show();
	}

	private void setFonts() {
		TextView textView1 = (TextView) findViewById(R.id.textView1);
		Typeface face = Font.getTennesseeFont(getBaseContext());
		textView1.setTypeface(face);
		destinyEditText.setTypeface(face);
		startButton.setTypeface(face);
	}

	protected void launchDestinyActivity() {
		final String destiny = destinyEditText.getText().toString();
		if (destiny == null || destiny.length() == 0) {
			CustomToast.show(getBaseContext(), getString(R.string.no_destiny), Toast.LENGTH_SHORT);
			return;
		}

		final Bundle bundle = new Bundle();
		bundle.putString(DESTINY, destiny);

		final Intent intent = new Intent(this, DestinyActivity.class);
		intent.putExtras(bundle);
		startActivity(intent);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			List<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
			if (matches != null && !matches.isEmpty())
				destinyEditText.setText(matches.get(0));
			else
				destinyEditText.setText("Error");
		}
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

		SharedPreferences sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(getApplicationContext());
		sharedPreferences.edit().remove(MainActivity.ON_GOING).commit();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		OptionMenu.setShareMenu(menu, getString(R.string.share_message));
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		OptionMenu optionMenu = new OptionMenu(MainActivity.this);

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
