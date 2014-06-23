/**
 * 
 */
package com.imherolddev.whatsmyluck;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * @author imherolddev
 * 
 */
public class AboutFragment extends Fragment {

	private TextView website;
	private TextView other_apps;

	/**
	 * No arg constructor
	 */
	public AboutFragment() {
		//Empty
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		getActivity().setTitle(getActivity().getString(R.string.about));

		return inflater.inflate(R.layout.fragment_about, container, false);

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {

		super.onActivityCreated(savedInstanceState);

		website = (TextView) getActivity().findViewById(R.id.tv_web);
		website.setMovementMethod(LinkMovementMethod.getInstance());
		other_apps = (TextView) getActivity().findViewById(R.id.tv_apps);
		other_apps.setMovementMethod(LinkMovementMethod.getInstance());

	}

	@Override
	public void onDestroyView() {

		super.onDestroyView();
		getActivity().setTitle(getActivity().getString(R.string.app_name));

	}

}
