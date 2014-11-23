package com.example.honeybadgerapi;


public interface InterestCalculator {

	final double upper = 3000.00;
	final double mid = 2000.00;
	final double lower = 1000.00;
	
	public double calculate(double dailyAverageBalance);
	
}
