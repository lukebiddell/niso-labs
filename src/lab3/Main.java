package lab3;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.concurrent.ThreadLocalRandom;

import helper.ElFarolBar;
import helper.GeneticAlgorithm;
import helper.MaxSatInstance;
import helper.Strategy;

public class Main {

	private static int question;

	private static String prob;
	private static int repetitions;

	private static String strategy;
	private static int state;
	private static int crowded;
	// repetitions

	private static int lambda;
	private static int h;
	private static int weeks;
	private static int time_budget;

	public static void main(String[] args) {
		for (int i = 0; i < args.length; i++) {
			if (args[i].startsWith("-")) {
				if (args[i].substring(1).equals("question"))
					question = Integer.parseInt(args[++i]);
				else if (args[i].substring(1).equals("repetitions"))
					repetitions = Integer.parseInt(args[++i]);
				else if (args[i].substring(1).equals("strategy"))
					strategy = args[++i];
				else if (args[i].substring(1).equals("repetitions"))
					repetitions = Integer.parseInt(args[++i]);
				else if (args[i].substring(1).equals("prob"))
					prob = args[++i];
				else if (args[i].substring(1).equals("state"))
					state = Integer.parseInt(args[++i]);
				else if (args[i].substring(1).equals("lambda"))
					lambda = Integer.parseInt(args[++i]);
				else if (args[i].substring(1).equals("h"))
					h = Integer.parseInt(args[++i]);
				else if (args[i].substring(1).equals("weeks"))
					weeks = Integer.parseInt(args[++i]);
				else if (args[i].substring(1).equals("time_budget"))
					time_budget = Integer.parseInt(args[++i]);
				else if (args[i].substring(1).equals("crowded"))
					crowded = Integer.parseInt(args[++i]);
			}
		}

		switch (question) {
		case 1:
			ex1(prob, repetitions);
			break;
		case 2:
			ex2(strategy, state, crowded, repetitions);
			break;
		case 3:
			ex3(lambda, h, weeks, time_budget);
			;
			break;
		case 6:
			ex6();
			break;
		case 0:
			test();
			break;
		default:
			System.err.println("Question number invalid");
			break;
		}
	}

	private static void test() {

	}

	private static void ex1(String prob_str, int repetitions) {
		double[] probs = ElFarolBar.probsFromString(prob_str);
		for (int i = 0; i < repetitions; i++) {
			System.out.println(ElFarolBar.distributionSampler(probs));
		}
	}

	private static void ex2(String strategy_str, int state, int crowded_int, int repetitions) {
		Strategy strat = new Strategy(strategy_str);

		boolean crowded = crowded_int > 0 ? true : false;

		for (int i = 0; i < repetitions; i++) {
			System.out.println(strat.simulateStep(state, crowded, repetitions));
		}

	}

	private static void ex3(int lambda, int h, int weeks, int time_budget) {

	}

	private static void ex6() {
		try {
			PrintStream out;
			LinkedList<String> wdimacsList = new LinkedList<String>();
			double chi;
			int lambda;
			int max_t = Integer.MAX_VALUE;
			int k;
			int time_budget = 5;

			int v = ThreadLocalRandom.current().nextInt(100000, 1000000);

			wdimacsList.add("wcnf/3col80_5_6.shuffled.cnf.wcnf");
			wdimacsList.add("wcnf/SAT11__application__fuhs__AProVE11__AProVE11-09.cnf.wcnf.10.wcnf");
			wdimacsList.add("wcnf/teams24_l4a.cnf.wcnf");

			for (String wdimacs : wdimacsList) {
				Files.createDirectories(Paths.get("logs/lab2/" + wdimacs));

				out = new PrintStream(
						new FileOutputStream("logs/lab2/" + wdimacs + "/runtime_vs_mutationrate_" + v + ".tsv"));
				out.println("t\tnsat\tm\tn\tchi\tlambda\tk\ttimebudget");

				chi = 0.1;
				lambda = 50;
				k = 2;
				while (chi < 3) {
					for (int i = 0; i < 100; i++) {
						MaxSatInstance maxsat = new MaxSatInstance(wdimacs);
						GeneticAlgorithm.simpleGeneticAlgorithmMaxSat(chi, k, lambda, max_t, out, maxsat, time_budget,
								true);
					}

					chi += 0.3;
				}
				out.close();

				out = new PrintStream(
						new FileOutputStream("logs/lab2/" + wdimacs + "/runtime_vs_populationsize_" + v + ".tsv"));
				out.println("t\tnsat\tm\tn\tchi\tlambda\tk\ttimebudget");

				chi = 1;
				lambda = 10;
				k = 2;
				while (lambda <= 1000) {
					for (int i = 0; i < 100; i++) {
						MaxSatInstance maxsat = new MaxSatInstance(wdimacs);
						GeneticAlgorithm.simpleGeneticAlgorithmMaxSat(chi, k, lambda, max_t, out, maxsat, time_budget,
								true);

					}

					lambda += 100;
				}

				out.close();

				out = new PrintStream(
						new FileOutputStream("logs/lab2/" + wdimacs + "/runtime_vs_tournamentsize_" + v + ".tsv"));
				out.println("t\tnsat\tm\tn\tchi\tlambda\tk\ttimebudget");

				chi = 1;
				lambda = 50;
				k = 2;
				while (k <= 5) {
					for (int i = 0; i < 100; i++) {
						MaxSatInstance maxsat = new MaxSatInstance(wdimacs);
						GeneticAlgorithm.simpleGeneticAlgorithmMaxSat(chi, k, lambda, max_t, out, maxsat, time_budget,
								true);
					}

					k += 1;
				}

				out.close();

				System.out.println("Logs completed");
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println("File not found");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}