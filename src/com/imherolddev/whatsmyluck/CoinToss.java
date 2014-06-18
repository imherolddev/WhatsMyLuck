package com.imherolddev.whatsmyluck;

import java.util.Random;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class CoinToss {

	private final int PERCENT = 100;

	private int flipCount;
	private int FLIP_TIMES;
	private int headsCount;
	private int tailsCount;
	private boolean sideCoin;
	private double diffPerc;

	private Context context;
	private SharedPreferences sharedPrefs;

	public CoinToss(Context context) {

		this.context = context;
		this.sharedPrefs = PreferenceManager
				.getDefaultSharedPreferences(context);

	}

	Random random = new Random();

	public void flip() {

		if (sharedPrefs.getString(context.getString(R.string.accuracy),
				"Medium").equals("Medium")) {

			this.FLIP_TIMES = 100;

		} else if (sharedPrefs.getString(context.getString(R.string.accuracy),
				"Medium").equals("Low")) {

			this.FLIP_TIMES = 10;

		} else if (sharedPrefs.getString(context.getString(R.string.accuracy),
				"Medium").equals("High")) {

			this.FLIP_TIMES = 1000;

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

	public int getDiffPerc() {

		if (this.headsCount > this.tailsCount) {

			this.diffPerc = ((double) this.headsCount / this.FLIP_TIMES)
					* this.PERCENT;

		} else {

			this.diffPerc = ((double) this.tailsCount / this.FLIP_TIMES)
					* this.PERCENT;

		}

		return (int) this.diffPerc;

	}

	public boolean getAffirmation() {

		if (this.headsCount > this.tailsCount) {

			return true;

		} else {

			return false;

		}

	}

}
