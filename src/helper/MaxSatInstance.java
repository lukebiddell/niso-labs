package helper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.BitSet;
import java.util.HashMap;
import java.util.LinkedList;

public class MaxSatInstance {

	private int n, m, x;

	private HashMap<BitSet, Integer> fitnessCache = new HashMap<BitSet, Integer>();

	private LinkedList<Clause> clauses;

	private BufferedReader br;

	public MaxSatInstance(String file) throws IOException {
		loadFile(file);
	}

	private void loadFile(String file) throws IOException {
		br = new BufferedReader(new FileReader(file));
		String line = null;
		while ((line = br.readLine()) != null) {
			if (line.startsWith("p")) {
				setParameters(line);
				br = new BufferedReader(new FileReader(file));
				loadClauses();
				return;
			}
		}

		throw new IllegalArgumentException("File doesn't contain p line");
	}

	private void setParameters(String line) {
		String[] values = line.split(" ");
		int length = values.length;

		if (length > 5 || length < 4) {
			throw new IllegalArgumentException("p line doesn't contain 4-5 arguments");
		} else {
			n = Integer.parseInt(values[2]);
			m = Integer.parseInt(values[3]);
			if (length == 5)
				x = Integer.parseInt(values[4]);
		}
	}

	private void loadClauses() throws IOException {
		clauses = new LinkedList<Clause>();
		Clause clause = getNextClause();
		while (clause != null) {
			clauses.add(clause);
			clause = getNextClause();
		}
	}

	// public int allNegatives = 0;

	private Clause getNextClause() throws IOException {
		String line = br.readLine();

		while (line != null && (line.startsWith("c") || line.startsWith("p")))
			line = br.readLine();

		if (line == null)
			return null;
		else {
			/*
			 * String[] lits = line.split(" "); for (int i = 1; i < lits.length
			 * - 1; i++) { if (!lits[i].startsWith("-")) { return new
			 * Clause(line); } } allNegatives++;
			 */
			return new Clause(line);
		}
	}

	public int countClausesSatisfied(BitString assignment) {
		Integer count = fitnessCache.get(assignment.getBitSet());

		if (count == null) {
			count = 0;
			for (Clause clause : clauses)
				count += clause.satisfiedByInt(assignment);
			fitnessCache.put(assignment.getBitSet(), count);
		}

		return count;

	}

	public int clauseCount() {
		return m;
	}

	public int variableCount() {
		return n;
	}

	public LinkedList<Clause> getClauses() {
		return clauses;
	}

	public int imp(int indice, boolean positive, BitString bs) {
		bs.getBitSet().set(indice, !positive);
		return countClausesSatisfied(bs) * 2 - clauseCount();
	}

}
