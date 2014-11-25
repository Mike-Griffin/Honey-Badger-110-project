package com.example.honeybadgerapi;

public interface InterestCalculator {

	final double UPPER = 3000.00;
	final double MID = 2000.00;
	final double LOWER = 1000.00;
	final double SAVING_UPPER_INTEREST = 0.04;
	final double SAVING_MID_INTEREST = 0.03;
	final double SAVING_LOWER_INTEREST = 0.02;
	final double CHECKING_UPPER_INTEREST = 0.03;
	final double CHECKING_MID_INTEREST = 0.02;
	final double CHECKING_LOWER_INTEREST = 0.01;

	public double calculate(double dailyAverageBalance);

}
