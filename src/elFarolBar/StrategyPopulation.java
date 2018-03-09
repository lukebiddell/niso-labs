package elFarolBar;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class StrategyPopulation extends ArrayList<Strategy> {

	private static final long serialVersionUID = -135392962374553684L;
	private boolean crowded;
	private int week = -1;
	private int individualsInBar;
	private int generation = 0;
	private int totalPayoff = -1;

	public static StrategyPopulation uniformRandom(int lambda, int h) {
		StrategyPopulation pop = new StrategyPopulation();

		for (int i = 0; i < lambda; i++) {
			pop.add(Strategy.uniformRandom(h));
		}

		pop.crowded = false;

		return pop;
	}

	public StrategyPopulation evolve(int k, double chi, double range, int precision, AlgorithmType type) {
		StrategyPopulation pop = new StrategyPopulation();

		while (pop.size() < size()) {
			ArrayList<Strategy> toAdd = new ArrayList<Strategy>();

			if(type == AlgorithmType.GDR){
				toAdd.add(Strategy.globalDiscreteRecombination(tournament(k), tournament(k)));
			} else if (type == AlgorithmType.GDR_THEN_MUTATE){
				toAdd.add(Strategy.globalDiscreteRecombination(tournament(k), tournament(k)).mutate(chi, range));
			} else if (type == AlgorithmType.GDR_THEN_MUTATE2){
				toAdd.add(Strategy.globalDiscreteRecombination(tournament(k), tournament(k)).mutate2(chi, range, precision));
			} else if (type == AlgorithmType.MUTATE){
				toAdd.add(tournament(k).mutate(chi, range));
			} else if (type == AlgorithmType.MUTATE2){
				toAdd.add(tournament(k).mutate2(chi, range, precision));				
			}

			int spaceLeft = Math.min(size() - pop.size(), toAdd.size());

			for (int i = 0; i < spaceLeft; i++) {
				pop.add(toAdd.get(i));
			}
		}

		pop.generation = generation + 1;

		return pop;
	}

	private Strategy tournament(int k) {
		ArrayList<Strategy> best_fighters = new ArrayList<Strategy>();
		int best_fitness = Integer.MIN_VALUE;

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

	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append(week);
		sb.append("\t").append(generation);
		sb.append("\t").append(individualsInBar);
		sb.append("\t").append(crowded ? '1' : '0');
		stream().forEach(s -> sb.append("\t").append(s.isSimulatedDecision() ? '1' : '0'));
		
		return sb.toString();
	}
	
	public String toLogString() {
		StringBuilder sb = new StringBuilder();

		sb.append(week);
		sb.append("\t").append(generation);
		sb.append("\t").append(individualsInBar);
		sb.append("\t").append(crowded ? '1' : '0');
		//stream().forEach(s -> sb.append("\t").append(s.isSimulatedDecision() ? '1' : '0'));
		
		return sb.toString();
	}
	
	public int getTotalPayoff(){
		if(totalPayoff < 0){
			totalPayoff = stream().mapToInt(Strategy::getPayoff).sum();
		}
		
		return totalPayoff;
	}

	public int getGeneration() {
		return generation;
	}
	
	public int getWeek() {
		return week;
	}
	
	public int getIndividualsInBar(){
		return individualsInBar;
	}
	
	public boolean isCrowded(){
		return crowded;
	}
}
