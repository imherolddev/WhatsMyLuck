package com.imherolddev.whatsmyluck;

import java.util.Random;

public class CoinToss {

	private final int FLIP_TIMES = 1000;
	private final int RUN_TIMES = 10000;
	private final int PERCENT = 100;

	private int runCount;
	private int flipCount;

	private String result;
	private String confirm;
	private int headsCount;
	private int tailsCount;
	private boolean sideCoin;
	private int positive;
	private int negative;
	private double diffPerc;
	private boolean affirmation;

	Random random = new Random();

	public void flip() {

		this.headsCount = 0;
		this.tailsCount = 0;
		this.positive = 0;
		this.negative = 0;

		for (this.runCount = 0; this.runCount < this.RUN_TIMES; this.runCount++) {

			for (this.flipCount = 0; this.flipCount < this.FLIP_TIMES; this.flipCount++) {

				this.sideCoin = this.random.nextBoolean();

				if (this.sideCoin) {

					this.headsCount++;

				} else {

					this.tailsCount++;

				}

			}

			if (this.headsCount > this.tailsCount) {

				this.positive++;

			} else {

				this.negative++;

			}

		}

		this.result = "Heads: " + this.headsCount + " Tails: " + this.tailsCount;
		this.confirm = "Positive: " + this.positive + " Negative: " + this.negative;

		if (this.positive > this.negative) {

			this.affirmation = true;

		} else {

			this.affirmation = false;

		}

	}

	public String getResult() {

		return this.result + " " + this.confirm;

	}

	public boolean getAffirmation() {

		return this.affirmation;

	}

	public int getDiffPerc() {

		if (this.positive > this.negative) {

			this.diffPerc = ((double) this.positive / this.RUN_TIMES)
					* this.PERCENT;

		} else {

			this.diffPerc = ((double) this.negative / this.RUN_TIMES)
					* this.PERCENT;

		}

		return (int) this.diffPerc;

	}

}
