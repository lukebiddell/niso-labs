package elFarolBar;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public final class State {

	private final double probability;

	private final double[] crowdedSTM;
	private final double[] uncrowdedSTM;

	/*private State(int h, String[] state_str) {
		int p = 0;

		probability = Double.parseDouble(state_str[p]);

		crowdedSTM = new double[h];
		uncrowdedSTM = new double[h];

		for (int i = 0; i < h; i++) {
			p++;
			crowdedSTM[i] = Double.parseDouble(state_str[p]);
			uncrowdedSTM[i] = Double.parseDouble(state_str[p + h]);
		}

	}*/

	public State(double probability, double[] crowdedSTM, double[] uncrowdedSTM) {
		this.probability = probability;
		this.crowdedSTM = crowdedSTM;
		this.uncrowdedSTM = uncrowdedSTM;
	}
	
	public static State uniformRandom(int stateNumber, int h){
		double probability = ThreadLocalRandom.current().nextDouble();
		double[] crowdedSTM = ElFarolBar.randomDistribution(h);
		double[] uncrowdedSTM = ElFarolBar.randomDistribution(h);
		
		return new State(probability, crowdedSTM, uncrowdedSTM);
	}
	
	public static State globalDiscreteRecombination(State mother, State father){
		double probability =  ThreadLocalRandom.current().nextBoolean() ? mother.probability : father.probability;
		double[] crowdedSTM = new double[mother.crowdedSTM.length];
		double[] uncrowdedSTM = new double[mother.uncrowdedSTM.length];
		
		
		for(int i = 0; i < crowdedSTM.length; i++){
			crowdedSTM[i] = ThreadLocalRandom.current().nextBoolean() ? mother.crowdedSTM[i] : father.crowdedSTM[i];
			uncrowdedSTM[i] = ThreadLocalRandom.current().nextBoolean() ? mother.uncrowdedSTM[i] : father.uncrowdedSTM[i];
		}
		
		return new State(probability, crowdedSTM, uncrowdedSTM);
	}
	
	public State mutate(double rate, double range){
		double probability = this.probability;
		double[] crowdedSTM = this.crowdedSTM;
		double[] uncrowdedSTM = this.uncrowdedSTM;
		
		double rand = ThreadLocalRandom.current().nextDouble();

		
		if(rand < rate){
			//TODO mutate probability
			probability = ElFarolBar.mutate(probability, range);
		}
		
		for(int i = 0; i < crowdedSTM.length; i++){
			rand = ThreadLocalRandom.current().nextDouble();
			
			if(rand < rate){
				//TODO mutate crowdedSTM[i]
				crowdedSTM[i] = ElFarolBar.mutate(crowdedSTM[i], range);
			}
			
			rand = ThreadLocalRandom.current().nextDouble();
			
			if(rand < rate){
				//TODO mutate uncrowdedSTM[i]
				uncrowdedSTM[i] = ElFarolBar.mutate(uncrowdedSTM[i], range);
			}
			
		}
		
		return new State(probability, crowdedSTM, uncrowdedSTM);
	}
	
	public State mutate2(double rate, double range, int precision){
		double probability = this.probability;
		double[] crowdedSTM = this.crowdedSTM;
		double[] uncrowdedSTM = this.uncrowdedSTM;
		
		double rand = ThreadLocalRandom.current().nextDouble();

		
		if(rand < rate){
			//TODO mutate probability
			probability = ElFarolBar.mutate2(probability, range, precision);
		}
		
		for(int i = 0; i < crowdedSTM.length; i++){
			rand = ThreadLocalRandom.current().nextDouble();
			
			if(rand < rate){
				//TODO mutate crowdedSTM[i]
				crowdedSTM[i] = ElFarolBar.mutate2(crowdedSTM[i], range, precision);
			}
			
			rand = ThreadLocalRandom.current().nextDouble();
			
			if(rand < rate){
				//TODO mutate uncrowdedSTM[i]
				uncrowdedSTM[i] = ElFarolBar.mutate2(uncrowdedSTM[i], range, precision);
			}
			
		}
		
		return new State(probability, crowdedSTM, uncrowdedSTM);
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(probability);
		
		Arrays.stream(crowdedSTM).forEach(s -> sb.append(" ").append(s));
		Arrays.stream(uncrowdedSTM).forEach(s -> sb.append(" ").append(s));
		
		return sb.toString();
	}
	
	public int simulateStep(boolean crowded){
		return ElFarolBar.distributionSampler(crowded ? crowdedSTM : uncrowdedSTM);
	}
	
	public double getProbability(){
		return probability;
	}

}
