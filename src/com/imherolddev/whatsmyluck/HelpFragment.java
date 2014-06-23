/**
 * 
 */
package com.imherolddev.whatsmyluck;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * @author imherolddev
 * 
 */
public class HelpFragment extends Fragment {
	
	private TextView help;

	/**
	 * No arg constructor
	 */
	public HelpFragment() {
		//Empty
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		getActivity().setTitle(getActivity().getString(R.string.help));

		return inflater.inflate(R.layout.fragment_help, container, false);

	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		
		super.onActivityCreated(savedInstanceState);
		
		help = (TextView) getActivity().findViewById(R.id.tv_help);
		
		try {

			InputStreamReader input = new InputStreamReader(getResources()
					.openRawResource(R.raw.help_text));
			BufferedReader br = new BufferedReader(input);

			String line;

			while ((line = br.readLine()) != null) {

				help.append(line);
				help.append("\n");

			}

			input.close();
			br.close();

		} catch (IOException ioex) {

			// catch

		}
		
	}

	@Override
	public void onDestroyView() {

		super.onDestroyView();
		getActivity().setTitle(getActivity().getString(R.string.app_name));

	}

}
