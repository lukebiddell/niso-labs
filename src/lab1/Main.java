package lab1;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.concurrent.ThreadLocalRandom;

import helper.BitString;
import helper.GeneticAlgorithm;
import helper.Population;

public class Main {

	private static double chi;
	private static int repetitions;
	private static String bits_x;
	private static String bits_y;
	private static int k;
	private static String population;
	private static int lambda;
	private static int question;
	private static int n;

	public static void main(String[] args) {
		for (int i = 0; i < args.length; i++) {
			if (args[i].startsWith("-")) {
				if (args[i].substring(1).equals("question"))
					question = Integer.parseInt(args[++i]);
				else if (args[i].substring(1).equals("chi"))
					chi = Double.parseDouble(args[++i]);
				else if (args[i].substring(1).equals("repetitions"))
					repetitions = Integer.parseInt(args[++i]);
				else if (args[i].substring(1).equals("bits_x"))
					bits_x = args[++i];
				else if (args[i].substring(1).equals("bits_y"))
					bits_y = args[++i];
				else if (args[i].substring(1).equals("k"))
					k = Integer.parseInt(args[++i]);
				else if (args[i].substring(1).equals("population"))
					population = args[++i];
				else if (args[i].substring(1).equals("lambda"))
					lambda = Integer.parseInt(args[++i]);
				else if (args[i].substring(1).equals("n"))
					n = Integer.parseInt(args[++i]);
			}
		}

		switch (question) {
		case 1:
			ex1(bits_x, chi, repetitions);
			break;
		case 2:
			ex2(bits_x, bits_y, repetitions);
			break;
		case 3:
			ex3(bits_x);
			break;
		case 4:
			ex4(population, k, repetitions);
			break;
		case 5:
			ex5(chi, n, lambda, k, repetitions);
			break;
		case 6:
			ex6();
			break;
		default:
			System.err.println("Question number invalid");
			break;
		}
		/**
		 * String bits_x = "00000"; double chi = 2.5; int repetitions = 20;
		 * ex1(bits_x, chi, repetitions); System.out.println("---------");
		 * ex2("00000", "11111", 4); System.out.println("---------");
		 * ex3("01010"); System.out.println("---------"); ex4("0000 1111 1110
		 * 1100 1000 0000", 2, 4); System.out.println("---------"); ex5(0.2, 10,
		 * 10, 2, 4); System.out.println("---------");
		 **/

		// ex6();
	}

	private static void ex1(String bits_x_string, double chi, int repetitions) {
		BitString bits_x = new BitString(bits_x_string);

		for (int r = 0; r < repetitions; r++) {
			System.out.println(GeneticAlgorithm.mutate(bits_x, chi));
		}

	}

	private static void ex2(String bits_x_string, String bits_y_string, int repetitions) {
		BitString bits_x = new BitString(bits_x_string);
		BitString bits_y = new BitString(bits_y_string);

		for (int r = 0; r < repetitions; r++) {
			System.out.println(GeneticAlgorithm.uniformCrossover(bits_x, bits_y));
		}

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
			GeneticAlgorithm.simpleGeneticAlgorithm(n, chi, k, lambda);
	}

	private static void ex6() {
		try {
			/*PrintStream out = new PrintStream(new FileOutputStream("logs/runtime_vs_mutationrate.txt"));

			double chi = 0.125;
			while (chi < 3) {
				for (int i = 0; i < 100; i++) {
					simpleGeneticAlgorithm(200, chi, 2, 100, 20000, out);
				}
				
				chi += 0.125;
			}
			out.close();
			
			
			out = new PrintStream(new FileOutputStream("logs/runtime_vs_problemsize.txt"));
			int n = 15;
			while (n < 200) {
				for (int i = 0; i < 100; i++) {
					simpleGeneticAlgorithm(n, 0.6, 2, 100, 20000, out);
				}

				n += 5;
			}
			out.close();

			
			out = new PrintStream(new FileOutputStream("logs/runtime_vs_populationsize.txt"));
			
			int lambda = 10;
			while (lambda <= 1000) {
				for (int i = 0; i < 100; i++) {
					simpleGeneticAlgorithm(200, 0.6, 2, lambda, 20000, out);
				}

				lambda += 25;
			}

			out.close();*/

			
			PrintStream out = new PrintStream(new FileOutputStream("logs/runtime_vs_tournamentsizen.txt"));
			
			int k = 2;
			while (k <= 5) {
				for (int i = 0; i < 100; i++) {
					GeneticAlgorithm.simpleGeneticAlgorithm(200, 0.6, k, 100, 20000, out, 1);
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
