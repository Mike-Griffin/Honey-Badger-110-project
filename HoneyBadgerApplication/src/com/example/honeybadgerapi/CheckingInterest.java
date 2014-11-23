package com.example.honeybadgerapi;

public class CheckingInterest implements InterestCalculator {

	@Override
	public double calculate(double dailyAverageBalance) {
		// TODO Auto-generated method stub
<<<<<<< Updated upstream
		if(dailyAverageBalance >= this.upper){
			return (dailyAverageBalance*0.03);
		}
		else if(dailyAverageBalance < this.upper && dailyAverageBalance >= this.mid){
			return (dailyAverageBalance*0.02);
		}
		else if(dailyAverageBalance < this.mid && dailyAverageBalance >= this.lower){
			return (dailyAverageBalance*0.01);
		}
		else
		  return 0.0;
=======
		if (dailyAverageBalance >= this.upper) {
			return dailyAverageBalance + (dailyAverageBalance * 0.03);
		} else if (dailyAverageBalance < this.upper
				&& dailyAverageBalance >= this.mid) {
			return dailyAverageBalance + (dailyAverageBalance * 0.02);
		} else if (dailyAverageBalance < this.mid
				&& dailyAverageBalance >= this.lower) {
			return dailyAverageBalance + (dailyAverageBalance * 0.01);
		} else
			return dailyAverageBalance;
>>>>>>> Stashed changes
	}

}
