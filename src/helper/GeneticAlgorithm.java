package helper;

import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class GeneticAlgorithm {

	// fitness functions

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

	public static ArrayList<BitString> kPointCrossover(BitString bits_x, BitString bits_y, int k) {
		int n = bits_x.size();

		BitString bits_z1 = new BitString(n);
		BitString bits_z2 = new BitString(n);

		ArrayList<Integer> crossoverPoints = new ArrayList<Integer>();

		if (k > n)
			k = n - 1;

		for (int i = 0; i < k; i++) {
			int r = ThreadLocalRandom.current().nextInt(n);
			while (crossoverPoints.contains(r = ThreadLocalRandom.current().nextInt(n - 1)))
				;
			crossoverPoints.add(r);
		}

		boolean cross = false;

		for (int i = 0; i < n; i++) {
			if (!cross) {
				bits_z1.getBitSet().set(i, bits_x.getBitSet().get(i));
				bits_z2.getBitSet().set(i, bits_y.getBitSet().get(i));
			} else {
				bits_z1.getBitSet().set(i, bits_y.getBitSet().get(i));
				bits_z2.getBitSet().set(i, bits_x.getBitSet().get(i));
			}

			if (crossoverPoints.contains(i)) {
				cross = !cross;
				// System.out.println("Cross: " + i + " " + cross);
			}

		}

		ArrayList<BitString> bits_z = new ArrayList<BitString>();
		bits_z.add(bits_z1);
		bits_z.add(bits_z2);

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

				int fitness = new_bitstr.oneMax();

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

	/*public static void simpleGeneticAlgorithmMaxSat(int n, double chi, int k, int lambda) {
		simpleGeneticAlgorithm(n, chi, k, lambda, 1000, null, 2);
	}*/

	public static void simpleGeneticAlgorithmMaxSat(int n, double chi, int k, int lambda, int max_t, PrintStream out,
			MaxSatInstance maxsat, int time_budget) {

		long endTime = System.currentTimeMillis() + time_budget * 1000;

		Population pop = new Population(maxsat);
		pop.populateUniformly(lambda, n);
		// System.out.println(pop);
		BitString xbest = new BitString(n);
		int nsat = Integer.MIN_VALUE;
		int t = 0;
		boolean end = false;
		while (!end && t < max_t) {
			Population next_pop = new Population(maxsat);
			for (int i = 0; i < lambda; i++) {
				BitString x = pop.tournament(k);
				BitString y = pop.tournament(k);
				
				//Uniform Crossover
				BitString new_bitstr = uniformCrossover(mutate(x, chi),
				mutate(y, chi));
				next_pop.add(new_bitstr);
				int new_nsat = maxsat.countClausesSatisfied(new_bitstr);
				//
				
				//k Point Crossover
				/*ArrayList<BitString> new_bitstrs = kPointCrossover(mutate(x, chi), mutate(y, chi), 20);
				next_pop.addAll(new_bitstrs);
				int nsat0 = maxsat.countClausesSatisfied(new_bitstrs.get(0));
				int nsat1 = maxsat.countClausesSatisfied(new_bitstrs.get(1));
				int new_nsat = Integer.max(nsat0, nsat1);
				BitString new_bitstr;

				if (nsat0 >= nsat1) {
					new_bitstr = new_bitstrs.get(0);
				} else {
					new_bitstr = new_bitstrs.get(1);
				}*/
				//
				
				if (new_nsat > nsat) {
					xbest = new_bitstr;
					nsat = new_nsat;
					//System.out.println();

					System.out.println(nsat + " ");
				}
				
				//System.out.print(new_nsat + " ");

				if (new_nsat >= maxsat.clauseCount() || System.currentTimeMillis() >= endTime) {
					end = true; // exit while loop
					break; // exit for loop
				}
			}

			pop = next_pop;

			t++;
		}
		System.out.println();
		StringBuilder sb = new StringBuilder();
		sb.append(t * lambda);
		sb.append("\t");
		sb.append(nsat);
		sb.append("\t");
		//sb.append(xbest);

		System.out.println(sb);
		if (out != null && out != System.out) {
			out.println(sb);
		}
	}
}
