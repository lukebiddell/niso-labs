package lab4.helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ThreadLocalRandom;

import lab4.expressions.ExpressionType;
import lab4.geneticalgorithm.ExpressionPopulation;
import lab4.geneticalgorithm.InitialPopulationMethod;

public abstract class Expression {

	protected ExpressionType type;
	protected Expression[] e;
	private int size = -1;
	private int depth = -1;

	private static final int MAX_BRANCHES = Integer.MAX_VALUE;

	public abstract double eval(Vector v);

	protected Expression(ExpressionType type) {
		this.type = type;
		e = new Expression[type.size()];
	}

	private double fitness(TrainingLine l) {
		return l.getY() - eval(l.getX());
	}

	public double fitness(TrainingData data) {
		double fitness = data.getLines().stream().mapToDouble(l -> Math.pow(fitness(l), 2)).average()
				.orElseThrow(IllegalStateException::new);
		return Double.isFinite(fitness) ? fitness : Double.MAX_VALUE;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder("(").append(type.toString().toLowerCase());
		Arrays.stream(e).forEach(s -> sb.append(" ").append(s));
		return sb.append(")").toString();
	}

	public static double makeFinite(double value) {
		return Double.isFinite(value) ? value : 0;
	}

	public int depth() {
		//TODO
		//return calculateDepth();
		return depth < 0 ? calculateDepth() : depth;
	}

	private int calculateDepth() {
		return depth = isTerminal() ? 0
				: 1 + Arrays.stream(e).mapToInt(Expression::depth).max().orElseThrow(IllegalStateException::new);
	}

	public int size() {
		//TODO
		//return calculateSize();
		return size < 0 ? calculateSize() : size;
	}

	private int calculateSize() {
		return size = isTerminal() ? 1 : 1 + Arrays.stream(e).mapToInt(Expression::size).sum();
	}

	public int arity() {
		return e.length;
	}

	public boolean isTerminal() {
		return arity() == 0;
	}

	public static Expression mutate(Expression e_orig, InitialPopulationMethod method, int depth) {
		Expression e = e_orig.clone();
		
		Expression randParent = e.getRandomParentExpression();
		//while((randParent = e.getRandomParentExpression()).isTerminal());

		int rand1 = ThreadLocalRandom.current().nextInt(randParent.arity());
		
		randParent.e[rand1] = ExpressionFactory.random(method, depth);
		
		
		return e;
	}

	public static LinkedList<Expression> crossOver(Expression e1_orig, Expression e2_orig) {
		Expression e1 = e1_orig.clone();
		Expression e2 = e2_orig.clone();

		Expression randParent1 = e1.getRandomParentExpression();
		Expression randParent2 = e2.getRandomParentExpression();

		// System.out.println("Rand parent 1:\t" + randParent1);
		// System.out.println("Rand parent 2:\t" + randParent2);

		swapRandomChildren(randParent1, randParent2);

		LinkedList<Expression> es = new LinkedList<Expression>();
		es.add(e1);
		es.add(e2);

		/*
		 * int fullSize = e1.size(); for (Expression exp : e1.children()) { if
		 * (ThreadLocalRandom.current().nextInt(fullSize) > exp.size()) {
		 * 
		 * } }
		 */

		return es;
	}

	private static void swapRandomChildren(Expression e1, Expression e2) {
		int rand1 = ThreadLocalRandom.current().nextInt(e1.arity());
		int rand2 = ThreadLocalRandom.current().nextInt(e2.arity());

		Expression randChild1 = e1.e[rand1];
		Expression randChild2 = e2.e[rand2];

		// System.out.println("Rand child 1:\t" + randChild1);
		// System.out.println("Rand child 2:\t" + randChild2);

		e1.e[rand1] = randChild2;
		e2.e[rand2] = randChild1;
	}

	/*
	 * private Expression getRandomChild(){ return isTerminal() ? null :
	 * e[ThreadLocalRandom.current().nextInt(arity())]; }
	 */

	/*
	 * public static Expression selectRandomParent(Expression parent, int n) { if
	 * (ThreadLocalRandom.current().nextDouble() < n / parent.size()) { return
	 * parent; } else {
	 * 
	 * } for (Expression child : parent.children()) {
	 * 
	 * if (ThreadLocalRandom.current().nextInt(size) < child.size()) {
	 * 
	 * } } }
	 */

	private Expression getRandomParentExpression() {
		Queue<Expression> q = new LinkedList<Expression>();
		q.add(this);
		int count = 0;
		int ranNum = ThreadLocalRandom.current().nextInt(Integer.min(MAX_BRANCHES, countBranches()));

		// int ranNum = ThreadLocalRandom.current().nextInt(countBranches());
		// System.out.println("Branches:\t" + parent.countBranches());

		while (!q.isEmpty() && count <= ranNum) {
			Expression e = q.remove();

			if (count == ranNum) {
				return e;
			}

			e.children().stream().filter(c -> !c.isTerminal()).forEachOrdered(q::add);

			count++;
		}

		System.out.println("Count: " + count + "\tranNum: " + ranNum + "\tcountBranches: " + countBranches());
		
		throw new IllegalArgumentException("Random parent not found from " + toString());
	}
	
	private static void swapExpression(Expression e1, Expression e2){
		//Expression temp = e1.clone();
		ExpressionType temp_type = e1.type;
		Expression[] temp_e = e1.e;
		int temp_depth = e1.depth;
		int temp_size = e1.size;
		
		e1.type = e2.type;
		e1.e = e2.e;
		e1.depth = e2.depth;
		e1.size = e2.size;
		
		e2.type = temp_type;
		e2.e = temp_e;
		e2.depth = temp_depth;
		e2.size = temp_size;
	}

	private Expression replaceRandomExpression(Expression replacement) {
		Queue<Expression> q = new LinkedList<Expression>();
		q.add(this);
		int count = 0;

		int rand = ThreadLocalRandom.current().nextInt(depth);

		if (rand == 0) {
			int ranNum = ThreadLocalRandom.current().nextInt(countBranches());

			e[ranNum] = replacement;

			// return
		} else {
			// return
		}

		return null;

	}

	public Expression clone() {
		return ExpressionFactory.clone(this);
	}

	public ArrayList<Expression> children() {
		return new ArrayList<>(Arrays.asList(e));
	}

	public ExpressionType getType() {
		return type;
	}

	public int countBranches() {
		return isTerminal() ? 0 : 1 + Arrays.stream(e).mapToInt(Expression::countBranches).sum();

	}

}