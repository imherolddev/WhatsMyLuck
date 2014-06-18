package com.imherolddev.whatsmyluck;

import android.app.FragmentManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
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

public class MainActivity extends ActionBarActivity implements
		SensorEventListener {

	private SensorManager sensorManager;
	private CoinToss toss;

	TextView tv_display;
	Button btn_predict;
	StartupDialog dialog;
	Button accept;
	private boolean predicted = false;
	SharedPreferences sharedPrefs;

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

		if (!sharedPrefs.getBoolean("isAccepted", false)) {
			dialog = new StartupDialog();
			FragmentManager fm = getFragmentManager();
			dialog.show(fm, "Startup");
		}

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

		case R.id.action_help:
			return true;

		case R.id.action_about:
			return true;

		case R.id.action_settings:
			Intent settings = new Intent(this, SettingsActivity.class);
			startActivity(settings);
			return true;

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
		this.tv_display = (TextView) findViewById(R.id.tv_display);
		this.btn_predict = (Button) findViewById(R.id.btn_predict);
		return null;

	}

	@Override
	public void onResume() {

		toss = new CoinToss(this);

		super.onResume();
		// register this class as a listener for the orientation and
		// accelerometer sensors
		sensorManager.registerListener(this,
				sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
				SensorManager.SENSOR_DELAY_NORMAL);

	}

	@Override
	protected void onPause() {
		// unregister listener
		super.onPause();
		sensorManager.unregisterListener(this);
	}

	public void flipCoins() {

		this.toss.flip();

		if (toss.getAffirmation()) {

			this.toast("sure thing " + toss.getDiffPerc() + "%");

		} else {

			this.toast("maybe not " + toss.getDiffPerc() + "%");

		}

	}

	public void predictAgain(View v) {

		this.predicted = false;
		this.btn_predict.setVisibility(View.GONE);

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

		if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {

			if (!predicted) {

				if (getAccelerometer(event)) {

					predicted = true;
					this.flipCoins();
					this.btn_predict.setVisibility(View.VISIBLE);

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

		if (accelationSquareRoot >= 5) {

			return true;

		} else {

			return false;

		}
	}

	public void toast(String msg) {

		Toast.makeText(this, msg, Toast.LENGTH_LONG).show();

	}

}
