package lab3.main;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Date;

import lab3.helper.AlgorithmType;
import lab3.helper.CoevolutionAlgorithm;
import lab3.helper.ElFarolBar;
import lab3.helper.State;
import lab3.helper.Strategy;
import lab3.helper.StrategyPopulation;

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
	private static int max_t;

	private static int k;
	private static double chi;
	private static double range;
	private static int precision;
	private static AlgorithmType type;
	private static PrintStream out;

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
				else if (args[i].substring(1).equals("max_t"))
					max_t = Integer.parseInt(args[++i]);
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
			ex3(lambda, h, weeks, max_t);
			;
			break;
		case 6:
			try {
				ex6();
			} catch (IOException e) {
				e.printStackTrace();
			}
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
		double[] vals = new double[3];
		vals[0] = 0.88;
		vals[1] = 0.01;
		vals[2] = 2.0;

		double[] norm = ElFarolBar.normalise(vals);

		System.out.println(Arrays.toString(vals));
		System.out.println(Arrays.toString(norm));

		System.out.println(Arrays.toString(ElFarolBar.randomDistribution(5)));

		System.out.println(State.uniformRandom(1, 4));

		System.out.println("");
		StrategyPopulation.uniformRandom(5, 2);

	}

	private static void ex1(String prob_str, int repetitions) {
		double[] probs = ElFarolBar.probsFromString(prob_str);
		for (int i = 0; i < repetitions; i++) {
			System.out.println(ElFarolBar.distributionSampler(probs));
		}
	}

	private static void ex2(String strategy_str, int state, int crowded_int, int repetitions) {
		Strategy strat = Strategy.parseStrategy(strategy_str);
		// System.out.println(strat);

		boolean crowded = crowded_int > 0;

		for (int i = 0; i < repetitions; i++) {
			strat.simulateStep(state, crowded);
			System.out.println((strat.isSimulatedDecision() ? 1 : 0) + "\t" + strat.getSimulatedState());
		}
	}

	private static void ex3(int lambda, int h, int weeks, int max_t) {
		CoevolutionAlgorithm alg = new CoevolutionAlgorithm(lambda, h, weeks, max_t);
		alg.startAlgorithm();

		/*
		 * StringBuilder sb = new StringBuilder("tw\ttg\tb\tc"); for(int i = 1;
		 * i <= lambda; i++) sb.append("\ta").append(i); System.out.println(sb);
		 */

	}

	private static void ex6() throws IOException {

		long v = new Date().getTime();

		Files.createDirectories(Paths.get("logs/lab3"));

		out = new PrintStream(new FileOutputStream("logs/lab3/" + v + "algorithmtype.tsv"));
		out.println("tw\ttg\tb\th\tlambda\tk\tchi\trange\tprec\ttype");

		weeks = 10;
		max_t = 1000;

		k = 2;

		chi = 1;
		range = 0.1;// [0.1, 10^-6]
		precision = 5; // [4,5, ..., 20]

		type = AlgorithmType.GDR_THEN_MUTATE2;

		lambda = 100;
		h = 4;

		for (AlgorithmType type : AlgorithmType.values()) {
			for (int i = 0; i < 100; i++) {
				CoevolutionAlgorithm alg = new CoevolutionAlgorithm(lambda, h, weeks, max_t, chi, k, range, precision,
						type);
				 alg.startAlgorithmAndLogBestPop(out);
				//alg.startAlgorithmAndLogFinalPop(out);
				System.out.print("\r" + type + "           " + i + " / 100              ");
			}
		}
		out.close();

		//////////////////////////////////////////////////

		out = new PrintStream(new FileOutputStream("logs/lab3/" + v + "mutationrate.tsv"));
		out.println("tw\ttg\tb\th\tlambda\tk\tchi\trange\tprec\ttype");

		weeks = 10;
		max_t = 1000;

		k = 2;

		chi = 0.5;
		range = 0.1;// [0.1, 10^-6]
		precision = 5; // [4,5, ..., 20]

		type = AlgorithmType.GDR_THEN_MUTATE2;

		lambda = 100;
		h = 4;

		while (chi < 3) {
			for (int i = 0; i < 100; i++) {
				CoevolutionAlgorithm alg = new CoevolutionAlgorithm(lambda, h, weeks, max_t, chi, k, range, precision,
						type);
				 alg.startAlgorithmAndLogBestPop(out);
				//alg.startAlgorithmAndLogFinalPop(out);
				System.out.print("\r" + chi + " / 3       " + i + " / 100              ");
			}

			chi += 0.5;
		}
		out.close();

		//////////////////////////////////////////////////

		out = new PrintStream(new FileOutputStream("logs/lab3/" + v + "k.tsv"));
		out.println("tw\ttg\tb\th\tlambda\tk\tchi\trange\tprec\ttype");

		weeks = 10;
		max_t = 1000;

		k = 2;

		chi = 1;
		range = 0.1;// [0.1, 10^-6]
		precision = 5; // [4,5, ..., 20]

		type = AlgorithmType.GDR;

		lambda = 100;
		h = 4;

		while (k <= 5) {
			for (int i = 0; i < 100; i++) {
				CoevolutionAlgorithm alg = new CoevolutionAlgorithm(lambda, h, weeks, max_t, chi, k, range, precision,
						type);
				 alg.startAlgorithmAndLogBestPop(out);
				//alg.startAlgorithmAndLogFinalPop(out);
				System.out.print("\r" + k + " / 5       " + i + " / 100              ");
			}

			k += 1;
		}
		out.close();

		//////////////////////////////////////////////

		out = new PrintStream(new FileOutputStream("logs/lab3/" + v + "h.tsv"));
		out.println("tw\ttg\tb\th\tlambda\tk\tchi\trange\tprec\ttype");

		weeks = 10;
		max_t = 1000;

		k = 2;

		chi = 1;
		range = 0.1;// [0.1, 10^-6]
		precision = 5; // [4,5, ..., 20]

		type = AlgorithmType.GDR;

		lambda = 100;
		h = 3;

		while (h <= 12) {
			for (int i = 0; i < 100; i++) {
				CoevolutionAlgorithm alg = new CoevolutionAlgorithm(lambda, h, weeks, max_t, chi, k, range, precision,
						type);
				alg.startAlgorithmAndLogBestPop(out);
				//alg.startAlgorithmAndLogFinalPop(out);
				System.out.print("\r" + h + " / 10       " + i + " / 100              ");
			}

			h += 1;
		}
		out.close();

		//////////////////////////////////////////////

		out = new PrintStream(new FileOutputStream("logs/lab3/" + v + "lambda.tsv"));
		out.println("tw\ttg\tb\th\tlambda\tk\tchi\trange\tprec\ttype");

		weeks = 10;
		max_t = 1000;

		k = 2;

		chi = 1;
		range = 0.1;// [0.1, 10^-6]
		precision = 5; // [4,5, ..., 20]

		type = AlgorithmType.GDR;

		lambda = 10;
		h = 4;

		while (lambda <= 1000) {
			for (int i = 0; i < 100; i++) {
				CoevolutionAlgorithm alg = new CoevolutionAlgorithm(lambda, h, weeks, max_t, chi, k, range, precision,
						type);
				alg.startAlgorithmAndLogBestPop(out);
				//alg.startAlgorithmAndLogFinalPop(out);
				System.out.print("\r" + lambda + " / 1000       " + i + " / 100              ");
			}

			lambda += 100;
		}
		out.close();

		//////////////////////////////////////////////

		System.out.println("Logs completed");

	}

}