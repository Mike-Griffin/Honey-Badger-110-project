package com.example.honeybadgerapi;

public class SavingInterest implements InterestCalculator {

	@Override
	public double calculate(double dailyAverageBalance) {
		// TODO Auto-generated method stub
		if (dailyAverageBalance >= UPPER) {
			return (dailyAverageBalance * SAVING_UPPER_INTEREST);
		} else if (dailyAverageBalance < UPPER
				&& dailyAverageBalance >= MID) {
			return (dailyAverageBalance * SAVING_MID_INTEREST);
		} else if (dailyAverageBalance < MID
				&& dailyAverageBalance >= LOWER) {
			return (dailyAverageBalance * SAVING_LOWER_INTEREST);
		} else
			return 0.0;
	}

}
