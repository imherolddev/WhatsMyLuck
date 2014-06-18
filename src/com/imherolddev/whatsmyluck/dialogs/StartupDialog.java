package com.imherolddev.whatsmyluck.dialogs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.imherolddev.whatsmyluck.R;

public class StartupDialog extends DialogFragment implements ScrollViewListener {

	private AlertDialog dialog;
	private TextView agreement;
	private View view;
	private ScrollViewExt scroll;
	private SharedPreferences sharedPrefs;

	/*
	 * public StartupDialog() {
	 * 
	 * }
	 */

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		AlertDialog.Builder startupDialog = new AlertDialog.Builder(
				getActivity());
		LayoutInflater inflater = getActivity().getLayoutInflater();
		view = inflater.inflate(R.layout.dialog_agreement, null);

		agreement = (TextView) view.findViewById(R.id.tv_agreement);
		scroll = (ScrollViewExt) view.findViewById(R.id.scrollView1);
		scroll.setScrollViewListener(this);

		sharedPrefs = PreferenceManager
				.getDefaultSharedPreferences(getActivity());
		final SharedPreferences.Editor editor = sharedPrefs.edit();

		try {

			InputStreamReader input = new InputStreamReader(getResources()
					.openRawResource(R.raw.user_agreement));
			BufferedReader br = new BufferedReader(input);

			String line;

			while ((line = br.readLine()) != null) {

				agreement.append(line);
				agreement.append("\n");

			}

			input.close();
			br.close();

		} catch (IOException ioex) {

			// catch

		}

		startupDialog.setTitle("User agreement");
		startupDialog.setMessage("Please read and accept");
		startupDialog.setView(view);

		startupDialog.setPositiveButton("Accept",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {

						editor.putBoolean("isAccepted", true).apply();
						dismiss();

					}

				});

		startupDialog.setNegativeButton("Decline",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {

						getActivity().finish();

					}
				});

		dialog = startupDialog.create();
		return dialog;

	}

	@Override
	public void onStart() {

		super.onStart();
		dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);

	}

	@Override
	public void onScrollChanged(ScrollViewExt scrollView, int x, int y,
			int oldx, int oldy) {

		View view = (View) scrollView
				.getChildAt(scrollView.getChildCount() - 1);
		int diff = (view.getBottom() - (scrollView.getHeight() + scrollView
				.getScrollY()));

		// if diff is zero, then the bottom has been reached
		if (diff == 0) {

			dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);

		}

	}

}
