package elFarolBar;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class StrategyPopulation extends ArrayList<Strategy> {

	private static final long serialVersionUID = -135392962374553684L;
	private Strategy bestStrategy;
	private boolean crowded;
	private int week = 0;
	private int individualsInBar;
	private int generation = 1;
	

	public static StrategyPopulation uniformRandom(int lambda, int h) {
		StrategyPopulation pop = new StrategyPopulation();

		for (int i = 0; i < lambda; i++) {
			pop.add(Strategy.uniformRandom(h));
		}

		pop.crowded = false;

		return pop;
	}
	
	public StrategyPopulation evolve(int k) {
		StrategyPopulation pop = new StrategyPopulation();

		for(int i = 0; i < size(); i++){
			Strategy x = tournament(k);
			Strategy y = tournament(k);
			
			Strategy z = Strategy.globalDiscreteRecombination(x, y);
			//System.out.println(x);
			pop.add(z);
		}
		
		pop.generation = ++generation;
		
		
		return pop;
	}

	private Strategy tournament(int k) {
		ArrayList<Strategy> best_fighters = new ArrayList<Strategy>();
		int best_fitness = -1;

		for (int i = 0; i <= k; i++) {
			int rand = ThreadLocalRandom.current().nextInt(size());

			Strategy fighter = get(rand);
			
			int fitness = fighter.getPayoff();
			
			if (fitness >= best_fitness) {
				if (fitness > best_fitness) {
					best_fighters.clear();
					best_fitness = fitness;
				}

				best_fighters.add(fighter);
			}

		}

		return best_fighters.get(ThreadLocalRandom.current().nextInt(best_fighters.size()));
	}

	public Strategy getBestStrategy() {
		return bestStrategy;
	}

	public void simulateStep() {
		individualsInBar = 0;
		
		for (Strategy strat : this) {
			strat.simulateStep(crowded);
			individualsInBar += strat.isSimulatedDecision() ? 1 : 0;
		}
		
		
		crowded = ((double) individualsInBar / (double) size()) >= 0.6;
		
		stream().forEach(s -> s.updatePayoff(crowded));
		
		week++;
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		
		sb.append(week);
		sb.append("\t").append(generation);
		sb.append("\t").append(individualsInBar);
		sb.append("\t").append(crowded ? '1' : '0');
		stream().forEach(s -> sb.append("\t").append(s.isSimulatedDecision() ? '1' : '0'));//.append(" ").append(s.getPayoff()));
		
		return sb.toString();
	}
}
