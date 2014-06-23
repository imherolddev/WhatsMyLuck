package com.imherolddev.whatsmyluck.preferences;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.view.MenuItem;

import com.imherolddev.whatsmyluck.MainActivity;

/**
 * 
 * @author imherolddev
 *
 */
public class SettingsActivity extends PreferenceActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		getFragmentManager().beginTransaction()
				.replace(android.R.id.content, new SettingsFragment()).commit();

		setupActionBar();

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {

		case android.R.id.home:
			gotoHome();
			return true;

		default:
			return super.onOptionsItemSelected(item);

		}

	}

	/**
	 * Handle homeAsUp click, send user to MainActivity and clear top
	 */
	private void gotoHome() {

		Intent home = new Intent(getApplicationContext(), MainActivity.class);
		home.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

		startActivity(home);

	}

	/**
	 * Enable homeAsUp navigation
	 */
	private void setupActionBar() {

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {

			getActionBar().setDisplayHomeAsUpEnabled(true);

		}

	}

}
