package elFarolBar;

import java.io.PrintStream;

//import helper.GeneticAlgorithm;
//public class CoevolutionAlgorithm extends GeneticAlgorithm {

public class CoevolutionAlgorithm {

	private int lambda;
	private int h;
	private int weeks;
	//private int time_budget;
	private int max_t;

	private int n;
	private double chi = 10;
	private int k = 2;
	private double range = 0.01;
	private PrintStream out = System.out;

	public CoevolutionAlgorithm(int lambda, int h, int weeks, int max_t) {
		this.lambda = lambda;
		this.h = h;
		this.weeks = weeks;
		//this.time_budget = time_budget;
		this.max_t = max_t;
	}

	public void startAlgorithm() {
		//long endTime = System.currentTimeMillis() + time_budget * 1000;
		StrategyPopulation pop = StrategyPopulation.uniformRandom(lambda, h);

		//boolean end = false;

		// while (System.currentTimeMillis() < endTime) {
		while (pop.getGeneration() <= max_t) {

			for (int week = 0; week < weeks; week++) {
				pop.simulateStep();
				out.println(pop);
			}

			pop = pop.evolve(k, chi, range);

			// end = System.currentTimeMillis() >= endTime;
		}

		/*
		 * int n = maxsat.variableCount(); long endTime =
		 * System.currentTimeMillis() + time_budget * 1000;
		 * 
		 * Population pop = new Population(maxsat);
		 * pop.populateUniformly(lambda, n);
		 * 
		 * BitString xbest = new BitString(n);
		 * 
		 * int nsat = Integer.MIN_VALUE; int t = 0; boolean end = false;
		 * 
		 * while (!end) { // System.out.println("Generation: " + t); Population
		 * next_pop = new Population(maxsat); for (int i = 0; i < lambda; i++) {
		 * BitString x = pop.tournament(k); BitString y = pop.tournament(k);
		 * 
		 * BitString new_bitstr = uniformCrossover(mutate(x, chi), mutate(y,
		 * chi));
		 * 
		 * next_pop.add(new_bitstr); int new_nsat;// = TODO;
		 * 
		 * if (new_nsat > nsat) { xbest = new_bitstr; nsat = new_nsat; //
		 * System.out.println();
		 * 
		 * // System.out.println(nsat + " (" + (maxsat.clauseCount() - // nsat)
		 * + ")"); }
		 * 
		 * // System.out.print(new_nsat + " ");
		 * 
		 * if (System.currentTimeMillis() >= endTime) { end = true; // exit
		 * while loop break; // exit for loop } }
		 * 
		 * pop = next_pop;
		 * 
		 * t++; } // System.out.println(); StringBuilder sb = new
		 * StringBuilder(); sb.append(t * lambda); sb.append("\t");
		 * sb.append(nsat); sb.append("\t"); if (!logOutput) { sb.append(xbest);
		 * } else { sb.append(maxsat.clauseCount()); sb.append("\t");
		 * sb.append(n); sb.append("\t"); sb.append(chi); sb.append("\t");
		 * sb.append(lambda); sb.append("\t"); sb.append(k); sb.append("\t");
		 * sb.append(time_budget); }
		 * 
		 * System.out.println(sb); if (out != null && out != System.out) {
		 * out.println(sb); }
		 */
	}

}