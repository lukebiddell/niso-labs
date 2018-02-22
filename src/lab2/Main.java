package lab2;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.ThreadLocalRandom;

import helper.BitString;
import helper.Clause;
import helper.GeneticAlgorithm;
import helper.MaxSatInstance;

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
		case 0:
			test();
			break;
		default:
			System.err.println("Question number invalid");
			break;
		}
	}

	private static void test() {
		int n = 10;
		int k = 3;
		int repetitions = 10;

		BitString x = new BitString(n);
		BitString y = new BitString(n);

		for (int i = 0; i < repetitions; i++) {
			x.uniformRandomise();
			y.uniformRandomise();
			System.out.println(x + " " + y);

			ArrayList<BitString> bs = GeneticAlgorithm.kPointCrossover(x, y, k);
			for (BitString b : bs) {
				System.out.print(b + " ");
			}
			System.out.println();
			System.out.println();
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
		double chi = 1;
		int k = 2;
		int lambda = 50;
		int max_t = Integer.MAX_VALUE;

		try {
			MaxSatInstance maxsat = new MaxSatInstance(wdimacs);

			for (int i = 0; i < repetitions; i++) {

				// String bs = "-1 2 -3 -4 5 6 7 8 9 -10 -11 -12 -13 14 15 -16
				// -17 18 -19 -20 -21 22 23 -24 -25 26 27 -28 -29 -30 -31 -32 33
				// -34 -35 36 -37 38 39 40 41 -42 -43 44 -45 -46 47 48 49 -50
				// -51 -52 -53 -54 55 -56 57 -58 59 60 61 62 63 -64 65 66 67 68
				// 69 70 -71 72 -73 -74 75 76 77 -78 -79 80 -81 82 83 -84 85 -86
				// 87 -88 -89 90 -91 -92 -93 94 -95 96 97 98 99 -100 101 102 103
				// -104 105 -106 107 108 -109 -110 -111 -112 113 114 -115 -116
				// -117 -118 -119 -120 121 -122 123 124 125 126 -127 128 -129
				// 130 131 132 133 134 135 136 137 138 139 -140 141 -142 -143
				// 144 -145 -146 -147 -148 -149 150 151 152 -153 154 -155 -156
				// 157 158 -159 160";
				// System.out.println(maxsat.countClausesSatisfied(BitString.fromClauseForm(bs)));

				if (i == 0) {
					// System.out.println("All negatives: " +
					// maxsat.allNegatives);
					// System.out.println("Total clauses: " +
					// maxsat.clauseCount());
				}

				GeneticAlgorithm.simpleGeneticAlgorithmMaxSat(chi, k, lambda, max_t, System.out, maxsat, time_budget, false);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// old stuff

	private static void ex6() {
		try {
			PrintStream out;
			LinkedList<String> wdimacsList = new LinkedList<String>();
			double chi;
			int lambda;
			int max_t = Integer.MAX_VALUE;
			int k;
			int time_budget = 10;
			
			int v = ThreadLocalRandom.current().nextInt(100000, 1000000);

			wdimacsList.add("wcnf/3col80_5_6.shuffled.cnf.wcnf");
			wdimacsList.add("wcnf/SAT11__application__fuhs__AProVE11__AProVE11-09.cnf.wcnf.10.wcnf");

			
			
			for (String wdimacs : wdimacsList) {
				Files.createDirectories(Paths.get("logs/lab2/" + wdimacs));

				
				
				out = new PrintStream(new FileOutputStream("logs/lab2/" + wdimacs + "/runtime_vs_mutationrate_" + v + ".tsv"));
				out.println("t\tnsat\tm\tn\tchi\tlambda\tk");
				
				chi = 0.125;
				lambda = 50;
				k = 2;
				while (chi < 3) {
					for (int i = 0; i < 100; i++) {
						MaxSatInstance maxsat = new MaxSatInstance(wdimacs);
						GeneticAlgorithm.simpleGeneticAlgorithmMaxSat(chi, k, lambda, max_t, out, maxsat, time_budget, true);
					}

					chi += 0.125;
				}
				out.close();
				
				
				
				out = new PrintStream(new FileOutputStream("logs/lab2/" + wdimacs + "/runtime_vs_populationsize_" + v + ".tsv"));
				out.println("t\tnsat\tm\tn\tchi\tlambda\tk");
				
				chi = 1;
				lambda = 10;
				k = 2;
				while (lambda <= 1000) {
					for (int i = 0; i < 100; i++) {
						MaxSatInstance maxsat = new MaxSatInstance(wdimacs);
						GeneticAlgorithm.simpleGeneticAlgorithmMaxSat(chi, k, lambda, max_t, out, maxsat, time_budget, true);

					}

					lambda += 25;
				}

				out.close();

				
				
				
				out = new PrintStream(new FileOutputStream("logs/lab2/" + wdimacs + "/runtime_vs_tournamentsize_" + v + ".tsv"));
				out.println("t\tnsat\tm\tn\tchi\tlambda\tk");
				
				chi = 1;
				lambda = 50;
				k = 2;
				while (k <= 5) {
					for (int i = 0; i < 100; i++) {
						MaxSatInstance maxsat = new MaxSatInstance(wdimacs);
						GeneticAlgorithm.simpleGeneticAlgorithmMaxSat(chi, k, lambda, max_t, out, maxsat, time_budget, true);
					}

					k += 1;
				}

				out.close();
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
