package lab4.geneticalgorithm;

import java.io.PrintStream;

import lab4.helper.TrainingData;

//import helper.GeneticAlgorithm;
//public class GeneticProgrammingAlgorithm extends GeneticAlgorithm {

public class GeneticProgrammingAlgorithm {

	private int lambda, n, m, time_budget;
	private TrainingData data;
	private double chi = 5;
	private int k = 2;
	private int depth = 5;
	private InitialPopulationMethod method = InitialPopulationMethod.GROWTH;

	public GeneticProgrammingAlgorithm(int lambda, int n, int m, TrainingData data, int time_budget) {
		this.lambda = lambda;
		this.n = n;
		this.m = m;
		this.time_budget = time_budget;
		this.data = data;
	}

	public GeneticProgrammingAlgorithm(int lambda, int n, int m, TrainingData data, int depth, int time_budget, double chi,
			int k, InitialPopulationMethod method) {
		this.lambda = lambda;
		this.n = n;
		this.m = m;
		this.time_budget = time_budget;
		this.data = data;
		this.chi = chi;
		this.k = k;
		this.depth = depth;
		this.method = method;
	}

	public void startAlgorithm() {
		startAlgorithmAndLogBestPop(System.out);
	}

	public void startAlgorithmAndLogBestPop(PrintStream out) {
		boolean logging = out != System.out;
		long endTime = System.currentTimeMillis() + time_budget * 1000;

		ExpressionPopulation pop = ExpressionPopulation.random(method, depth, lambda);
		pop.findBestIndividual(data);
		
		while (System.currentTimeMillis() < endTime) {
			//System.out.println(pop.getBestExpression());
			pop = pop.evolve(k, chi, data, depth);
			pop.findBestIndividual(data);

			//System.out.println("Generation: " + pop.getGeneration() + "\tFitness: " + pop.getBestFitness());
			//System.out.println(pop.getBestFitness());// + "\t" + pop.getBestExpression());
		}
		
		//System.out.println(pop.getBestFitness());// + "\t" + pop.getBestExpression());

		//System.out.println(best_fitness);
		System.out.println(pop.getBestExpression());
		
		//pop.stream().forEach(System.out::println);
		//System.out.println(best_expression.size());

		if (logging) {
			StringBuilder sb = new StringBuilder();

			out.println(sb);

		}

	}

}
