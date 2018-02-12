package helper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class MaxSatInstance {

	private int n, m, x;

	// private LinkedList<Clause> clauses;

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
			if (length == 4)
				x = Integer.parseInt(values[4]);
		}
	}

	private Clause getNextClause() throws IOException {
		String line = br.readLine();

		while (line != null && (line.startsWith("c") || line.startsWith("p")))
			line = br.readLine();

		if (line == null)
			return null;
		else
			return new Clause(line);

	}

	public int countClausesSatisfied(BitString assignment) throws IOException {
		int count = 0;
		Clause clause = getNextClause();
		while (clause != null) {
			count += clause.satisfiedByInt(assignment);
			clause = getNextClause();
		}
		return count;

	}

}
