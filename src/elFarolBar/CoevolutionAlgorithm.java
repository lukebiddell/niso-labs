package elFarolBar;

import java.io.PrintStream;

//import helper.GeneticAlgorithm;
//public class CoevolutionAlgorithm extends GeneticAlgorithm {

public class CoevolutionAlgorithm {

	private int lambda;
	private int h;
	private int weeks;
	// private int time_budget;
	private int max_t;

	// private int n;
	private double chi = 5;
	private int k = 2;
	private double range = 0.1; // [0.1, 10^-6]
	private int precision = 5; // [4,5, ..., 20]
	private AlgorithmType type = AlgorithmType.GDR;

	public CoevolutionAlgorithm(int lambda, int h, int weeks, int max_t) {
		this.lambda = lambda;
		this.h = h;
		this.weeks = weeks;
		// this.time_budget = time_budgcd ../et;
		this.max_t = max_t;
	}

	public CoevolutionAlgorithm(int lambda, int h, int weeks, int max_t, double chi, int k, double range, int precision,
			AlgorithmType type) {
		this.lambda = lambda;
		this.h = h;
		this.weeks = weeks;
		// this.time_budget = time_budget;
		this.max_t = max_t;
		this.chi = chi;
		this.k = k;
		this.range = range;
		this.precision = precision;
		this.type = type;

	}

	public void startAlgorithm() {
		startAlgorithmAndLogBestPop(System.out);
	}

	public void startAlgorithmAndLogBestPop(PrintStream out) {
		boolean logging = out != System.out;

		// long endTime = System.currentTimeMillis() + time_budget * 1000;
		StrategyPopulation pop = StrategyPopulation.uniformRandom(lambda, h);

		// boolean end = false;

		// while (System.currentTimeMillis() < endTime) {

		StrategyPopulation best_pop = pop;
		double best_average = 0;

		while (pop.getGeneration() < max_t) {
			
			int individualsInBar = 0;

			while (pop.getWeek() < weeks - 1) {
				pop.simulateStep();
				if (logging) {
					individualsInBar += pop.getIndividualsInBar();
					// StringBuilder sb = new StringBuilder(pop.toLogString());
					// sb.append("\t").append(h).append("\t").append(lambda).append("\t").append(k).append("\t").append(chi).append("\t").append(range).append("\t").append(precision).append("\t").append(type);

					// out.println(sb);

				} else {
					out.println(pop);
				}
			}

			if (pop.getTotalPayoff() > best_pop.getTotalPayoff()) {
				best_pop = pop;
				best_average = (double) individualsInBar / (double) weeks;
			}

			pop = pop.evolve(k, chi, range, precision, type);

			// end = System.currentTimeMillis() >= endTime;
		}

		if (logging) {
			StringBuilder sb = new StringBuilder();
			sb.append(best_pop.getWeek()).append("\t").append(best_pop.getGeneration()).append("\t")
					.append(best_average).append("\t").append(h).append("\t").append(lambda).append("\t").append(k)
					.append("\t").append(chi).append("\t").append(range).append("\t").append(precision).append("\t")
					.append(type);

			out.println(sb);

		}

	}
	
	public void startAlgorithmAndLogFinalPop(PrintStream out) {
		boolean logging = out != System.out;
		StrategyPopulation pop = StrategyPopulation.uniformRandom(lambda, h);


		int individualsInBar = 0;

		
		while (pop.getGeneration() < max_t) {
			

			while (pop.getWeek() < weeks - 1) {
				pop.simulateStep();
				if(!logging){
					out.println(pop);
				}
				else if (pop.getGeneration() == max_t - 1) {
					individualsInBar += pop.getIndividualsInBar();
				}
			}

			if(pop.getGeneration() < max_t - 1){
				pop = pop.evolve(k, chi, range, precision, type);
			}
			

			// end = System.currentTimeMillis() >= endTime;
		}

		if (logging) {
			double averageInBar = (double) individualsInBar / (double) weeks;
			StringBuilder sb = new StringBuilder();
			sb.append(pop.getWeek()).append("\t").append(pop.getGeneration()).append("\t")
					.append(averageInBar).append("\t").append(h).append("\t").append(lambda).append("\t").append(k)
					.append("\t").append(chi).append("\t").append(range).append("\t").append(precision).append("\t")
					.append(type);

			out.println(sb);

		}

	}

}
