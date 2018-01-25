package ex1;

import java.util.concurrent.ThreadLocalRandom;

public class niso_lab1 {

	public static void main(String[] args) {
		String bits_x = "00000";
		double chi = 2.5;
		int repetitions = 20;
		ex1(bits_x, chi, repetitions);
		System.out.println("---------");
		ex2("00000", "11111", 4);
		System.out.println("---------");
		ex3("01010");
		System.out.println("---------");
		ex4("0000 1111 1110 1100 1000 0000", 2, 4);
		System.out.println("---------");
		ex5(0.2, 10, 10, 2, 4);
		System.out.println("---------");
		ex6();
	}

	private static void ex1(String bits_x_string, double chi, int repetitions) {
		BitString bits_x = new BitString(bits_x_string);

		for (int r = 0; r < repetitions; r++) {
			System.out.println(mutate(bits_x, chi));
		}

	}

	private static BitString mutate(BitString bits_x, double chi) {
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

	private static void ex2(String bits_x_string, String bits_y_string, int repetitions) {
		BitString bits_x = new BitString(bits_x_string);
		BitString bits_y = new BitString(bits_y_string);

		for (int r = 0; r < repetitions; r++) {
			System.out.println(uniformCrossover(bits_x, bits_y));
		}

	}

	private static BitString uniformCrossover(BitString bits_x, BitString bits_y) {
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

	private static void ex3(String bits_x_str) {
		System.out.println(new BitString(bits_x_str).oneMax());
	}

	private static void ex4(String population_str, int k, int repetitions) {
		Population population = new Population(population_str);
		// System.out.print(population);
		for (int i = 0; i < repetitions; i++) {
			BitString winner = population.tournament(k);

			System.out.println(winner);
		}
	}

	private static void ex5(double chi, int n, int lambda, int k, int repetitions) {
		for (int i = 0; i < repetitions; i++)
			simpleGeneticAlgorithm(n, chi, k, lambda);
	}

	// TODO: Add error checking for requirements
	private static void simpleGeneticAlgorithm(int n, double chi, int k, int lambda, int max_t) {
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
				if (new_bitstr.oneMax() == n) {
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
		sb.append("\t");
		sb.append(xbest);

		System.out.println(sb);

	}

	private static void simpleGeneticAlgorithm(int n, double chi, int k, int lambda) {
		simpleGeneticAlgorithm(n, chi, k, lambda, Integer.MAX_VALUE);
	}

	private static void ex6() {
		double chi = 0.03125;

		while (chi < 3) {
			simpleGeneticAlgorithm(200, chi, 2, 100, 5000);
			chi += 0.03125;
		}
	}

}
