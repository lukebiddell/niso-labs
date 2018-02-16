package lab2;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import helper.BitString;
import helper.Clause;
import helper.GeneticAlgorithm;
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
		
		for(int i = 0; i < repetitions; i++){
			x.uniformRandomise();
			y.uniformRandomise();
			System.out.println(x + " " + y);
			
			ArrayList<BitString> bs = GeneticAlgorithm.kPointCrossover(x, y, k);
			for(BitString b : bs){
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

		try {
			for (int i = 0; i < repetitions; i++) {
				MaxSatInstance maxsat = new MaxSatInstance(wdimacs);
				if (i == 0)
					//System.out.println("All negatives: " + maxsat.allNegatives);
					System.out.println("Total clauses: " + maxsat.clauseCount());

				GeneticAlgorithm.simpleGeneticAlgorithmMaxSat(maxsat.variableCount(), 0.5, 2, 1000, Integer.MAX_VALUE,
						System.out, maxsat, time_budget);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// old stuff

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
					GeneticAlgorithm.simpleGeneticAlgorithm(200, 0.6, k, 100, 20000, out, 2);
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
