package com.example.honeybadgerapi;

public class CheckingInterest implements InterestCalculator {

	@Override
	public double calculate(double dailyAverageBalance) {
		// TODO Auto-generated method stub
		if (dailyAverageBalance >= UPPER) {
			return (dailyAverageBalance * CHECKING_UPPER_INTEREST);
		} else if (dailyAverageBalance < UPPER
				&& dailyAverageBalance >= MID) {
			return (dailyAverageBalance * CHECKING_MID_INTEREST);
		} else if (dailyAverageBalance < MID
				&& dailyAverageBalance >= LOWER) {
			return (dailyAverageBalance * CHECKING_LOWER_INTEREST);
		} else
			return 0.0;
	}

}
