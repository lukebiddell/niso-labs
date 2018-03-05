package elFarolBar;

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

	public static Boolean singleSampler(double prob) {
		return ThreadLocalRandom.current().nextDouble() < prob ? true : false;
	}

	public static double[] probsFromString(String probs_str) {
		String[] probs_str_arr = probs_str.split(" ");
		int length = probs_str_arr.length;
		double[] probs = new double[length];

		for (int i = 0; i < length; i++) {
			probs[i] = Double.parseDouble(probs_str_arr[i]);
		}

		return probs;
	}

	public static double[] normalise(double[] vals) {
		double[] v = new double[vals.length];
		double sum = DoubleStream.of(vals).sum();
		
		for (int i = 0; i < v.length; i++) {
			v[i] = vals[i] / sum;
		}

		return v;
	}
	
	public static double[] randomDistribution(int h){
		double[] vals = new double[h];
		for(int i = 0; i < h; i++){
			vals[i] = ThreadLocalRandom.current().nextDouble();
		}
		
		return normalise(vals);
	}
}
