/**
 * 
 */
package com.imherolddev.whatsmyluck;

import java.util.Arrays;
import java.util.List;

import android.content.Context;

/**
 * @author imherolddev
 * 
 */
public class Predictor {

	private Context context;
	private boolean affirmation;
	//private int difference;
	private double diffPerc;

	private String prediction = "";
	private List<String> answers;
	private String[] strings;

	/**
	 * Constructor to set the criteria for prediction
	 * 
	 * @param c
	 *            - the Context to set
	 * @param affirmation
	 *            - positive = true, negative = false
	 * @param difference
	 *            - difference between positive and negative
	 * @param diffPerc
	 *            - percentage in favor of affirmation
	 */
	public Predictor(Context c, boolean affirmation, int difference,
			double diffPerc) {

		this.context = c;
		this.affirmation = affirmation;
		//this.difference = difference;
		this.diffPerc = diffPerc;

	}

	public String getPrediction() {

		if (affirmation) {

			strings = context.getResources().getStringArray(
					R.array.positive_answers);
			answers = Arrays.asList(strings);

		} else {

			strings = context.getResources().getStringArray(
					R.array.negative_answers);
			answers = Arrays.asList(strings);

		}

		this.getDegree();
		return this.prediction;

	}

	private void getDegree() {

		if (this.diffPerc <= 0.3) {

			this.prediction = answers.get(0);

		} else if (this.diffPerc > 0.3 && this.diffPerc <= 0.75) {

			this.prediction = answers.get(1);

		} else if (this.diffPerc > 0.75 && this.diffPerc <= 1.5) {

			this.prediction = answers.get(2);

		} else if (this.diffPerc > 1.5) {

			this.prediction = answers.get(3);

		}

	}

}
