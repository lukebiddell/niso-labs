package lab4.geneticalgorithm;

import java.io.PrintStream;

import lab4.helper.Expression;
import lab4.helper.TrainingData;

//import helper.GeneticAlgorithm;
//public class GeneticProgrammingAlgorithm extends GeneticAlgorithm {

public class GeneticProgrammingAlgorithm {

	private int lambda, n, m, time_budget;
	private TrainingData data;
	private double chi = 5;
	private int k = 2;
	private int depth = 10;

	public GeneticProgrammingAlgorithm(int lambda, int n, int m, TrainingData data, int time_budget) {
		this.lambda = lambda;
		this.n = n;
		this.m = m;
		this.time_budget = time_budget;
		this.data = data;
	}

	public GeneticProgrammingAlgorithm(int lambda, int n, int m, TrainingData data, int depth, int time_budget, double chi,
			int k) {
		this.lambda = lambda;
		this.n = n;
		this.m = m;
		this.time_budget = time_budget;
		this.data = data;
		this.chi = chi;
		this.k = k;
		this.depth = depth;
	}

	public void startAlgorithm() {
		startAlgorithmAndLogBestPop(System.out);
	}

	public void startAlgorithmAndLogBestPop(PrintStream out) {
		boolean logging = out != System.out;
		long endTime = System.currentTimeMillis() + time_budget * 1000;

		ExpressionPopulation pop = ExpressionPopulation.randomFullMethod(lambda, depth);
		//System.out.println(pop);
		Expression best_expression = pop.get(0);
		double best_fitness = Double.MAX_VALUE;
		while (System.currentTimeMillis() < endTime) {
			pop = pop.evolve(k, chi, data);
			pop.findBestIndividual(data);
			if(pop.getBestFitness() < best_fitness){
				best_fitness = pop.getBestFitness();
				best_expression = pop.getBestExpression();
				//System.out.println(best_fitness);
			}
			//System.out.println(pop.getBestFitness());// + "\t" + pop.getBestExpression());
		}
		
		//System.out.println(pop.getBestFitness());// + "\t" + pop.getBestExpression());
		//System.out.println(best_fitness);
		System.out.println(best_expression);
		//System.out.println(best_expression.size());

		if (logging) {
			StringBuilder sb = new StringBuilder();

			out.println(sb);

		}

	}

}
