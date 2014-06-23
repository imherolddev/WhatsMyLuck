package com.imherolddev.whatsmyluck;

import java.util.Random;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Get a randomly generated positive or negative result from coin flips<br>
 * Recall the difference and the difference percentage<br>
 * 
 * @author imherolddev
 * 
 */
public class CoinToss {

	private final int PERCENT = 100;

	private int flipCount;
	private int FLIP_TIMES;
	private int headsCount;
	private int tailsCount;
	private boolean sideCoin;
	private int difference;
	private double diffPerc;
	private int deviation;

	private Context context;
	private SharedPreferences sharedPrefs;

	/**
	 * Constructor to pass context for preferences
	 * 
	 * @param context
	 *            - MainActivity context
	 */
	public CoinToss(Context context) {

		this.context = context;
		this.sharedPrefs = PreferenceManager
				.getDefaultSharedPreferences(context);

	}

	Random random = new Random();

	/**
	 * Flip a number of coins based on accuracy to find deviation<br>
	 * <ul>
	 * <li>"High" = 1_000_000 flips</li>
	 * <li>"Medium" = 10_000 flips</li>
	 * <li>"Low" = 1_000 flips</li>
	 * </ul>
	 */
	public void flip() {

		if (sharedPrefs.getString(context.getString(R.string.accuracy),
				"Medium").equals("Medium")) {

			this.FLIP_TIMES = 10000;

		} else if (sharedPrefs.getString(context.getString(R.string.accuracy),
				"Medium").equals("Low")) {

			this.FLIP_TIMES = 1000;

		} else if (sharedPrefs.getString(context.getString(R.string.accuracy),
				"Medium").equals("High")) {

			this.FLIP_TIMES = 1000000;

		}

		this.headsCount = 0;
		this.tailsCount = 0;

		for (this.flipCount = 0; this.flipCount < this.FLIP_TIMES; this.flipCount++) {

			this.sideCoin = this.random.nextBoolean();
			if (this.sideCoin) {

				this.headsCount++;

			} else {

				this.tailsCount++;

			}

		}

	}

	/**
	 * Get the difference
	 * 
	 * @return difference - greater minus the lesser
	 */
	public int getDifference() {

		if (this.getAffirmation()) {

			this.difference = this.headsCount - (this.FLIP_TIMES / 2);

		} else {

			this.difference = this.tailsCount - (this.FLIP_TIMES / 2);

		}

		return this.difference;

	}

	/**
	 * Get the percentage of the difference
	 * 
	 * @return - int formated percentage of the difference
	 */
	public double getDiffPerc() {

		this.diffPerc = ((double) this.difference / (this.FLIP_TIMES / 2)) * this.PERCENT;
		
		return this.diffPerc;

	}

	/**
	 * Get the affirmation, positive or negative
	 * 
	 * @return - boolean if heads greater than tails
	 */
	public boolean getAffirmation() {

		if (this.headsCount > this.tailsCount) {

			return true;

		} else {

			return false;

		}

	}

	/**
	 * Get the standard deviation
	 * 
	 * @return deviation - the standard deviation
	 */
	public int getDeviation() {

		this.deviation = (int) Math
				.sqrt((double) (Math.pow(this.difference, 2) / 2));

		return this.deviation;

	}

}
