package com.imherolddev.whatsmyluck;

import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentManager.OnBackStackChangedListener;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.imherolddev.whatsmyluck.dialogs.StartupDialog;
import com.imherolddev.whatsmyluck.preferences.SettingsActivity;

/**
 * 
 * @author imherolddev
 * 
 */
public class MainActivity extends ActionBarActivity implements
		SensorEventListener, OnBackStackChangedListener {

	private SensorManager sensorManager;
	private CoinToss toss;

	private TextView tv_tagline;
	private Button btn_predict;
	private StartupDialog dialog;
	private SharedPreferences sharedPrefs;
	private FragmentManager fm;

	private Fragment aboutFrag;
	private Fragment helpFrag;

	private boolean predicted = false;
	private int shaken;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}

		sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

		sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
		fm = this.getSupportFragmentManager();
		dialog = new StartupDialog();

		if (!sharedPrefs.getBoolean("isAccepted", false)) {
			dialog.show(fm, "Startup");
		}

		fm.addOnBackStackChangedListener(this);
		this.shouldDisplayHomeUp();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		switch (item.getItemId()) {

		case R.id.home:
			if (fm.getBackStackEntryCount() > 0) {
				fm.popBackStack();
			}
		case R.id.action_help:
			helpFrag = new HelpFragment();
			fm.beginTransaction().add(R.id.container, helpFrag)
					.addToBackStack(null).commit();
			return true;

		case R.id.action_about:
			aboutFrag = new AboutFragment();
			fm.beginTransaction().add(R.id.container, aboutFrag)
					.addToBackStack(null).commit();
			return true;

		case R.id.action_settings:
			Intent settings = new Intent(this, SettingsActivity.class);
			startActivity(settings);
			return true;

		case R.id.action_agreement:
			dialog.show(fm, getString(R.string.action_agreement));

		default:
			return super.onOptionsItemSelected(item);

		}

	}

	@Override
	public View onCreatePanelView(int featureId) {

		if (!sharedPrefs.getBoolean("isAccepted", false)) {
			dialog.getDialog().getWindow()
					.addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);
			dialog.setCancelable(false);
		}
		this.tv_tagline = (TextView) findViewById(R.id.tv_display);
		this.btn_predict = (Button) findViewById(R.id.btn_predict);
		return null;

	}

	@Override
	public void onResume() {

		super.onResume();
		// register this class as a listener for the orientation and
		// accelerometer sensors
		sensorManager.registerListener(this,
				sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
				SensorManager.SENSOR_DELAY_NORMAL);

		toss = new CoinToss(this);

	}

	@Override
	protected void onPause() {
		// unregister listener
		super.onPause();
		sensorManager.unregisterListener(this);
	}

	public void flipCoins() {

		this.toss.flip();

		Predictor p = new Predictor(this, this.toss.getAffirmation(),
				toss.getDifference(), toss.getDiffPerc());

		this.toast(p.getPrediction());

	}

	public void predictAgain(View v) {

		this.predicted = false;
		this.btn_predict.setVisibility(View.GONE);
		this.tv_tagline.setVisibility(View.VISIBLE);

	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}

	@Override
	public void onSensorChanged(SensorEvent event) {

		if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER
				&& getAccelerometer(event)) {

			if (!predicted) {

				predicted = true;
				shaken = 0;
				this.flipCoins();
				this.tv_tagline.setVisibility(View.INVISIBLE);
				this.btn_predict.setVisibility(View.VISIBLE);

			} else {

				shaken++;

				if (shaken == 5) {
					this.toast(getString(R.string.predicted));
				}

			}

		}

	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// Not yet needed

	}

	private boolean getAccelerometer(SensorEvent event) {

		float[] values = event.values;
		// Movement
		float x = values[0];
		float y = values[1];
		float z = values[2];

		float accelationSquareRoot = (x * x + y * y + z * z)
				/ (SensorManager.GRAVITY_EARTH * SensorManager.GRAVITY_EARTH);

		if (accelationSquareRoot >= 6) {

			return true;

		} else {

			return false;

		}
	}

	public void toast(String msg) {

		Toast.makeText(this, msg, Toast.LENGTH_LONG).show();

	}

	@Override
	public void onBackStackChanged() {
		shouldDisplayHomeUp();
	}

	public void shouldDisplayHomeUp() {
		// Enable Up button only if there are entries in the back stack
		boolean canback = getSupportFragmentManager().getBackStackEntryCount() > 0;
		getSupportActionBar().setDisplayHomeAsUpEnabled(canback);
	}

	@Override
	public boolean onSupportNavigateUp() {
		// This method is called when the up button is pressed.
		getSupportFragmentManager().popBackStack();
		return true;
	}

}
