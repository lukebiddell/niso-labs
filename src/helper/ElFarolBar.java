package helper;

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.DoubleStream;

public class ElFarolBar {

	public ElFarolBar() {

	}

	public static int distributionSampler(double[] probs) {
		double sum = DoubleStream.of(probs).sum();
		double rand = ThreadLocalRandom.current().nextDouble(sum);
		double cumulative = 0;
		
		for (int i = 0; i < probs.length; i++) {
			cumulative += probs[i];
			if (rand < cumulative) {
				return i;
			}
		}
		return -1;
	}
	
	public static double[] probsFromString(String probs_str){
		String[] probs_str_arr = probs_str.split(" ");
		int length = probs_str_arr.length;
		double[] probs = new double[length];
		
		
		
		
		for(int i = 0; i < length; i++){
			probs[i] = Double.parseDouble(probs_str_arr[i]);
		}
		
		return probs;
	}
}
