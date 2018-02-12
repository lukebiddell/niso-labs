package lab2;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.concurrent.ThreadLocalRandom;

import helper.BitString;
import helper.Clause;
import helper.MaxSatInstance;
import helper.Population;

public class Main {

	private static int question;
	private static String assignment;
	private static String clause;
	private static String wdimacs;
	private static int time_budget;
	private static int repetitions;

	public static void main(String[] args) {
		for (int i = 0; i < args.length; i++) {
			if (args[i].startsWith("-")) {
				if (args[i].substring(1).equals("question"))
					question = Integer.parseInt(args[++i]);
				else if (args[i].substring(1).equals("repetitions"))
					repetitions = Integer.parseInt(args[++i]);
				else if (args[i].substring(1).equals("assignment"))
					assignment = args[++i];
				else if (args[i].substring(1).equals("clause"))
					clause = args[++i];
				else if (args[i].substring(1).equals("wdimacs"))
					wdimacs = args[++i];
				else if (args[i].substring(1).equals("time_budget"))
					time_budget = Integer.parseInt(args[++i]);
				else if (args[i].substring(1).equals("repetitions"))
					repetitions = Integer.parseInt(args[++i]);
			}
		}

		switch (question) {
		case 1:
			ex1(assignment, clause);
			break;
		case 2:
			ex2(wdimacs, assignment);
			break;
		case 3:
			ex3(wdimacs, time_budget, repetitions);
			break;
		case 6:
			ex6();
			break;
		default:
			System.err.println("Question number invalid");
			break;
		}
	}

	private static void ex1(String assignment_str, String clause_str) {
		BitString assignment = new BitString(assignment_str);
		Clause clause = new Clause(clause_str);

		System.out.println(clause.satisfiedByInt(assignment));
	}

	private static void ex2(String wdimacs, String assignment_str) {
		BitString assignment = new BitString(assignment_str);
		MaxSatInstance maxsat;
		try {
			maxsat = new MaxSatInstance(wdimacs);
			System.out.println(maxsat.countClausesSatisfied(assignment));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static void ex3(String wdimacs, int time_budget, int repetitions) {
		// TODO

	}

	// old stuff

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

	// TODO: Add error checking for requirements
	private static void simpleGeneticAlgorithm(int n, double chi, int k, int lambda, int max_t, PrintStream out) {
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
		// sb.append("\t");
		// sb.append(xbest);

		System.out.println(sb);
		if (out != null && out != System.out) {
			out.println(sb);
		}
	}

	private static void simpleGeneticAlgorithm(int n, double chi, int k, int lambda) {
		simpleGeneticAlgorithm(n, chi, k, lambda, Integer.MAX_VALUE, null);
	}

	private static void ex6() {
		try {
			/*
			 * PrintStream out = new PrintStream(new
			 * FileOutputStream("logs/runtime_vs_mutationrate.txt"));
			 * 
			 * double chi = 0.125; while (chi < 3) { for (int i = 0; i < 100;
			 * i++) { simpleGeneticAlgorithm(200, chi, 2, 100, 20000, out); }
			 * 
			 * chi += 0.125; } out.close();
			 * 
			 * 
			 * out = new PrintStream(new
			 * FileOutputStream("logs/runtime_vs_problemsize.txt")); int n = 15;
			 * while (n < 200) { for (int i = 0; i < 100; i++) {
			 * simpleGeneticAlgorithm(n, 0.6, 2, 100, 20000, out); }
			 * 
			 * n += 5; } out.close();
			 * 
			 * 
			 * out = new PrintStream(new
			 * FileOutputStream("logs/runtime_vs_populationsize.txt"));
			 * 
			 * int lambda = 10; while (lambda <= 1000) { for (int i = 0; i <
			 * 100; i++) { simpleGeneticAlgorithm(200, 0.6, 2, lambda, 20000,
			 * out); }
			 * 
			 * lambda += 25; }
			 * 
			 * out.close();
			 */

			PrintStream out = new PrintStream(new FileOutputStream("logs/runtime_vs_tournamentsizen.txt"));

			int k = 2;
			while (k <= 5) {
				for (int i = 0; i < 100; i++) {
					simpleGeneticAlgorithm(200, 0.6, k, 100, 20000, out);
				}

				k += 1;
			}

			out.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println("File not found");
		}

	}

}
