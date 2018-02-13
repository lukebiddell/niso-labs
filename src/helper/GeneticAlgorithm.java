package helper;

import java.io.IOException;
import java.io.PrintStream;
import java.util.concurrent.ThreadLocalRandom;

public class GeneticAlgorithm {

	// fitness functions
	public static final int ONE_MAX = 1;
	public static final int MAX_SAT = 2;

	public static BitString mutate(BitString bits_x, double chi) {
		BitString bits = bits_x.clone();

		int n = bits.size();
		// System.out.println("Original? : " + bits.toString());
		double rate = chi / n;
		// mutation rate X/n (chi)
		// X = chi * n

		for (int i = 0; i < n; i++) {
			double rand = ThreadLocalRandom.current().nextDouble(1.0);
			// System.out.println("Rand = " + rand + " Rate = " + rate);
			if (rand < rate) {
				bits.getBitSet().flip(i);
			}
		}

		return bits;
	}

	public static BitString uniformCrossover(BitString bits_x, BitString bits_y) {
		int n = bits_x.size();
		BitString bits_z = new BitString(n);

		for (int i = 0; i < n; i++) {
			// if Xi != Yi
			if (bits_x.getBitSet().get(i) != bits_y.getBitSet().get(i)) {
				// Zi = rand bool
				bits_z.getBitSet().set(i, ThreadLocalRandom.current().nextBoolean());
			} else {
				// Zi = Xi
				bits_z.getBitSet().set(i, bits_x.getBitSet().get(i));
			}
		}

		return bits_z;

	}

	// TODO: Add error checking for requirements
	public static void simpleGeneticAlgorithm(int n, double chi, int k, int lambda, int max_t, PrintStream out,
			int fitnessFunction) {
		MaxSatInstance maxsat;

		Population pop = new Population();
		pop.populateUniformly(lambda, n);
		// System.out.println(pop);

		BitString xbest = new BitString(n);
		int t = 0;
		boolean found = false;
		while (!found && t < max_t) {
			Population next_pop = new Population();
			for (int i = 0; i < lambda; i++) {
				BitString x = pop.tournament(k);
				BitString y = pop.tournament(k);
				BitString new_bitstr = uniformCrossover(mutate(x, chi), mutate(y, chi));
				next_pop.add(new_bitstr);

				int fitness = 0;

				switch (fitnessFunction) {
				case ONE_MAX:
					fitness = new_bitstr.oneMax();
					break;
				case MAX_SAT:
					try {
						maxsat = new MaxSatInstance("wcnf/mul_8_3.wcnf");
						fitness = maxsat.countClausesSatisfied(new_bitstr);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				default:
					break;
				}
				if (fitness == n) {
					found = true; // exit while loop
					xbest = new_bitstr;
					break;
				}
			}

			pop = next_pop;

			t++;
		}

		int fbest = xbest.oneMax();

		StringBuilder sb = new StringBuilder();

		sb.append(n);
		sb.append("\t");
		sb.append(chi);
		sb.append("\t");
		sb.append(lambda);
		sb.append("\t");
		sb.append(k);
		sb.append("\t");
		sb.append(t);
		sb.append("\t");
		sb.append(fbest);
		// sb.append("\t");
		// sb.append(xbest);

		System.out.println(sb);
		if (out != null && out != System.out) {
			out.println(sb);
		}
	}

	public static void simpleGeneticAlgorithm(int n, double chi, int k, int lambda) {
		simpleGeneticAlgorithm(n, chi, k, lambda, Integer.MAX_VALUE, null, 1);
	}

	public static void simpleGeneticAlgorithmMaxSat(int n, double chi, int k, int lambda) {
		simpleGeneticAlgorithm(n, chi, k, lambda, 1000, null, 2);
	}

	public static void simpleGeneticAlgorithmMaxSat(int n, double chi, int k, int lambda, int max_t, PrintStream out,
			MaxSatInstance maxsat) {

		Population pop = new Population();
		pop.populateUniformly(lambda, n);
		// System.out.println(pop);

		BitString xbest = new BitString(n);
		int fbest = 0;
		int t = 0;
		boolean found = false;
		while (!found && t < max_t) {
			Population next_pop = new Population();
			for (int i = 0; i < lambda; i++) {
				BitString x = pop.tournament(k);
				BitString y = pop.tournament(k);
				BitString new_bitstr = uniformCrossover(mutate(x, chi), mutate(y, chi));
				next_pop.add(new_bitstr);

				int fitness = maxsat.countClausesSatisfied(new_bitstr);

				if (fitness > fbest) {
					xbest = new_bitstr;
					fbest = maxsat.countClausesSatisfied(xbest);
					System.out.println("New best - " + fbest + " / " + maxsat.clauseCount());
				}

				if (fitness >= maxsat.clauseCount()) {
					found = true; // exit while loop
					break;
				}
			}
			
			System.out.println(pop);
			
			pop = next_pop;

			t++;
		}

		StringBuilder sb = new StringBuilder();

		sb.append(n);
		sb.append("\t");
		sb.append(chi);
		sb.append("\t");
		sb.append(lambda);
		sb.append("\t");
		sb.append(k);
		sb.append("\t");
		sb.append(t);
		sb.append("\t");
		sb.append(fbest);
		// sb.append("\t");
		// sb.append(xbest);

		System.out.println(sb);
		if (out != null && out != System.out) {
			out.println(sb);
		}
	}
}
