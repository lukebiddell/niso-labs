package lab4.geneticalgorithm;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import javax.sql.rowset.CachedRowSet;

import lab4.helper.Expression;
import lab4.helper.ExpressionFactory;
import lab4.helper.TrainingData;

public class ExpressionPopulation extends ArrayList<Expression> {

	private static final long serialVersionUID = -135392962374553684L;
	private int generation = 0;

	private Expression bestExpression;
	private double bestFitness = Double.MAX_VALUE;
	
	private InitialPopulationMethod method;
	private int depth;

	private ExpressionPopulation(InitialPopulationMethod method, int depth) {
		this.method = method;
	}
	
	
	public static ExpressionPopulation randomFullMethod(int lambda, int treeDepth) {
		ExpressionPopulation pop = new ExpressionPopulation(InitialPopulationMethod.FULL, treeDepth);
		
		for (int i = 0; i < lambda; i++) {
			pop.add(ExpressionFactory.random(treeDepth, false));
		}

		return pop;
	}

	public static ExpressionPopulation randomGrowthMethod(int lambda, int maxDepth) {
		ExpressionPopulation pop = new ExpressionPopulation(InitialPopulationMethod.GROWTH, maxDepth);

		for (int i = 0; i < lambda; i++) {
			pop.add(ExpressionFactory.random(maxDepth, true));
		}

		return pop;
	}

	public static ExpressionPopulation randomRampedHalfHalf(int lambda, int maxDepth) {
		ExpressionPopulation pop = new ExpressionPopulation(InitialPopulationMethod.HALFHALF, maxDepth);

		for (int i = 0; i < lambda; i++) {
			// pop.add(Expression.uniformRandom());
		}

		return pop;
	}

	public ExpressionPopulation evolve(int k, double chi, TrainingData data) {
		ExpressionPopulation pop = new ExpressionPopulation(method, depth);

		pop.add(getBestExpression().clone());

		while (pop.size() < size()) {
			ArrayList<Expression> toAdd = Expression.crossOver(tournament(k, data), tournament(k, data)).stream()
			.map(e -> Expression.mutate(e, method, depth))
			.collect(Collectors.toCollection(ArrayList::new));
			
			// System.out.println("toAdd:\t" + toAdd);

			int spaceLeft = Math.min(size() - pop.size(), toAdd.size());

			for (int i = 0; i < spaceLeft; i++) {
				pop.add(toAdd.get(i));
			}
		}

		pop.generation = generation + 1;

		// System.out.println(pop);

		return pop;
		// return this;
	}

	private Expression tournament(int k, TrainingData data) {
		ArrayList<Expression> best_fighters = new ArrayList<Expression>();
		double best_fitness = Double.MAX_VALUE;
		double fitness = 0;

		for (int i = 0; i <= k; i++) {
			int rand = ThreadLocalRandom.current().nextInt(size());

			Expression fighter = get(rand);

			fitness = fighter.fitness(data);
			// System.out.println(fitness);
			if (fitness <= best_fitness) {
				if (fitness < best_fitness) {
					best_fighters.clear();
					best_fitness = fitness;
				}

				best_fighters.add(fighter);
			}

		}

		if (best_fighters.size() == 0) {
			System.err.println("Error, no best_fighters, fitness: " + fitness);
		}

		// if (best_fighters.size() > 1) System.out.println("Size: " +
		// best_fighters.size());

		return best_fighters.get(ThreadLocalRandom.current().nextInt(best_fighters.size()));
	}

	public void findBestIndividual(TrainingData data) {
		// System.out.println("a thing");
		for (Expression e : this) {
			double fitness = e.fitness(data);

			// double fitness = e.fitnessScaledToSize(data);
			if (fitness < bestFitness) {
				bestFitness = fitness;
				bestExpression = e;
			}
		}
	}

	public Expression getBestExpression() {
		return bestExpression;
	}

	public double getBestFitness() {
		return bestFitness;
	}

	public String toLogString() {
		StringBuilder sb = new StringBuilder();

		return sb.toString();
	}

	public int getGeneration() {
		return generation;
	}
}
