package lab4.geneticalgorithm;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import lab4.helper.Expression;
import lab4.helper.ExpressionFactory;
import lab4.helper.TrainingData;

public class ExpressionPopulation extends ArrayList<Expression> {

	private static final long serialVersionUID = -135392962374553684L;
	private int generation = 0;
	
	private Expression bestExpression;
	private double bestFitness = Double.MAX_VALUE;

	public static ExpressionPopulation randomFullMethod(int lambda, int treeDepth) {
		ExpressionPopulation pop = new ExpressionPopulation();

		for (int i = 0; i < lambda; i++) {
			pop.add(ExpressionFactory.random(treeDepth, false));
		}

		return pop;
	}
	
	public static ExpressionPopulation randomGrowthMethod(int lambda, int maxDepth) {
		ExpressionPopulation pop = new ExpressionPopulation();

		for (int i = 0; i < lambda; i++) {
			pop.add(ExpressionFactory.random(maxDepth, true));
		}

		return pop;
	}
	
	public static ExpressionPopulation randomRampedHalfHalf(int lambda, int maxDepth) {
		ExpressionPopulation pop = new ExpressionPopulation();

		for (int i = 0; i < lambda; i++) {
			//pop.add(Expression.uniformRandom());
		}

		return pop;
	}

	public ExpressionPopulation evolve(int k, double chi, TrainingData data) {
		ExpressionPopulation pop = new ExpressionPopulation();

		while (pop.size() < size()) {
			ArrayList<Expression> toAdd = new ArrayList<Expression>();

			toAdd.add(tournament(k, data));

			int spaceLeft = Math.min(size() - pop.size(), toAdd.size());

			for (int i = 0; i < spaceLeft; i++) {
				pop.add(toAdd.get(i));
			}
		}

		pop.generation = generation + 1;

		return pop;
		//return this;
	}

	private Expression tournament(int k, TrainingData data) {
		ArrayList<Expression> best_fighters = new ArrayList<Expression>();
		double best_fitness = Double.MAX_VALUE;
		double fitness = 0;
		
		for (int i = 0; i <= k; i++) {
			int rand = ThreadLocalRandom.current().nextInt(size());

			Expression fighter = get(rand);

			fitness = fighter.fitness(data);
			//System.out.println(fitness);
			if (fitness <= best_fitness) {
				if (fitness < best_fitness) {
					best_fighters.clear();
					best_fitness = fitness;
				}

				best_fighters.add(fighter);
			}

		}
		
		if(best_fighters.size() == 0){
			System.out.println(fitness);
		}
		
		return best_fighters.get(ThreadLocalRandom.current().nextInt(best_fighters.size()));
	}

	public void findBestIndividual(TrainingData data){
		for(Expression e : this){
			double fitness = e.fitness(data);
			if(fitness < bestFitness){
				bestFitness = fitness;
				bestExpression = e;
				return;
			}
		}
	}
	
	public Expression getBestExpression(){
		return bestExpression;
	}
	
	public double getBestFitness(){
		return bestFitness;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		return sb.toString();
	}
	
	public String toLogString() {
		StringBuilder sb = new StringBuilder();

		return sb.toString();
	}

	public int getGeneration() {
		return generation;
	}
}
