package lab4.main;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;

import de.tudresden.inf.lat.jsexp.SexpParserException;
import lab4.geneticalgorithm.GeneticProgrammingAlgorithm;
import lab4.helper.Expression;
import lab4.helper.ExpressionFactory;
import lab4.helper.TrainingData;
import lab4.helper.Vector;

public class Main {

	private static int question;//

	private static String expr;//
	private static int n;//
	private static String x;//

	private static int m;
	private static String data;

	private static int lambda;//
	private static int time_budget;//

	// private static int repetitions;//

	public static void main(String[] args) {
		for (int i = 0; i < args.length; i++) {
			if (args[i].startsWith("-")) {
				if (args[i].substring(1).equals("question"))
					question = Integer.parseInt(args[++i]);
				// else if (args[i].substring(1).equals("repetitions"))
				// repetitions = Integer.parseInt(args[++i]);
				else if (args[i].substring(1).equals("expr"))
					expr = args[++i];
				else if (args[i].substring(1).equals("x"))
					x = args[++i];
				else if (args[i].substring(1).equals("m"))
					m = Integer.parseInt(args[++i]);
				else if (args[i].substring(1).equals("lambda"))
					lambda = Integer.parseInt(args[++i]);
				else if (args[i].substring(1).equals("n"))
					n = Integer.parseInt(args[++i]);
				else if (args[i].substring(1).equals("data"))
					data = args[++i];
				else if (args[i].substring(1).equals("time_budget"))
					time_budget = Integer.parseInt(args[++i]);
				else {
					System.err.println("Invalid argument(s): " + args[i]);
				}
			}
		}

		switch (question) {
		case 1:
			ex1(expr, n, x);
			break;
		case 2:
			ex2(expr, n, m, data);
			break;
		case 3:
			ex3(lambda, n, m, data, time_budget);
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
		try {
			TrainingData data = TrainingData.parseFile("training/bitcoin-price-full.tsv");
			Expression expr = ExpressionFactory.parse("(mul (add 1 2) (log 8))");
			System.out.println(expr);
			System.out.println(expr.clone());
			System.out.println("Depth:\t" + expr.depth());
			System.out.println("Size:\t" + expr.size());

		} catch (IOException | SexpParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void ex1(String expr_str, int n, String x_str) {
		try {
			// OldExpression oldExpr = OldExpression.parse(expr_str);
			// oldExpr.evaluate();

			Expression expr = ExpressionFactory.parse(expr_str);
			System.out.println(expr.eval(Vector.parse(x_str, n)));

		} catch (SexpParserException e) {
			e.printStackTrace();
		}
	}

	private static void ex2(String expr_str, int n, int m, String data_str) {
		try {
			TrainingData data = TrainingData.parseFile(data_str, n, m);
			Expression expr = ExpressionFactory.parse(expr_str);
			
			System.out.println(expr.fitness(data));
		} catch (IOException e) {
			System.err.println("Error reading training data.");
			e.printStackTrace();
		} catch (SexpParserException e) {
			System.err.println("Error parsing s-expression.");
			e.printStackTrace();
		}
	}

	private static void ex3(int lambda, int n, int m, String data_str, int time_budget) {
		try {
			TrainingData data = TrainingData.parseFile(data_str, n, m);
			GeneticProgrammingAlgorithm alg = new GeneticProgrammingAlgorithm(lambda, n, m, data, time_budget);
			alg.startAlgorithm();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void ex6() throws IOException {
		long v = new Date().getTime();

		Files.createDirectories(Paths.get("logs/lab4"));

		//////////////////////////////////////////////////

		PrintStream out = new PrintStream(new FileOutputStream("logs/lab3/" + v + "mutationrate.tsv"));
		out.println("tw\ttg\tb\th\tlambda\tk\tchi\trange\tprec\ttype");

		out.close();

		//////////////////////////////////////////////////

		System.out.println("Logs completed");

	}

}