package elFarolBar;

import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.ThreadLocalRandom;

public final class State {

	private final int stateNumber;

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

	public State(int stateNumber, double probability, double[] crowdedSTM, double[] uncrowdedSTM) {
		this.stateNumber = stateNumber;
		this.probability = probability;
		this.crowdedSTM = crowdedSTM;
		this.uncrowdedSTM = uncrowdedSTM;
	}
	
	public static State uniformRandom(int stateNumber, int h){
		double probability = ThreadLocalRandom.current().nextDouble();
		double[] crowdedSTM = ElFarolBar.randomDistribution(h);
		double[] uncrowdedSTM = ElFarolBar.randomDistribution(h);
		
		return new State(stateNumber, probability, crowdedSTM, uncrowdedSTM);
	}
	
	public static State globalDiscreteRecombination(State mother, State father){
		int stateNumber = mother.stateNumber;
		double probability =  ThreadLocalRandom.current().nextBoolean() ? mother.probability : father.probability;
		double[] crowdedSTM = new double[mother.crowdedSTM.length];
		double[] uncrowdedSTM = new double[mother.uncrowdedSTM.length];
		
		
		for(int i = 0; i < crowdedSTM.length; i++){
			crowdedSTM[i] = ThreadLocalRandom.current().nextBoolean() ? mother.crowdedSTM[i] : father.crowdedSTM[i];
			uncrowdedSTM[i] = ThreadLocalRandom.current().nextBoolean() ? mother.uncrowdedSTM[i] : father.uncrowdedSTM[i];
		}
		
		return new State(stateNumber, probability, crowdedSTM, uncrowdedSTM);
	}
	
	public State mutate(double rate, double range){
		int stateNumber = this.stateNumber;
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
		
		return new State(stateNumber, probability, crowdedSTM, uncrowdedSTM);
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(probability);
		for(int i = 0; i < crowdedSTM.length; i++){
			sb.append(" ");
			sb.append(crowdedSTM[i]);
		}
		for(int i = 0; i < uncrowdedSTM.length; i++){
			sb.append(" ");
			sb.append(uncrowdedSTM[i]);
		}
		
		return sb.toString();
	}
	
	public int simulateStep(boolean crowded){
		if(crowded)
			return ElFarolBar.distributionSampler(crowdedSTM);
		else
			return ElFarolBar.distributionSampler(uncrowdedSTM);
	}
	
	public double getProbability(){
		return probability;
	}

}
