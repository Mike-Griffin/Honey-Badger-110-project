package com.example.honeybadgerapi;

public class SavingInterest implements InterestCalculator {

	@Override
	public double calculate(double dailyAverageBalance) {
		// TODO Auto-generated method stub
		if(dailyAverageBalance >= this.upper){
			return (dailyAverageBalance*0.04);
		}
		else if(dailyAverageBalance < this.upper && dailyAverageBalance >= this.mid){
			return (dailyAverageBalance*0.03);
		}
		else if(dailyAverageBalance < this.mid && dailyAverageBalance >= this.lower){
			return (dailyAverageBalance*0.02);
		}
		else
		  return 0.0;
	}

}
