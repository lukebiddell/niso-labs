package helper;

import java.util.LinkedList;
import javax.naming.directory.InvalidAttributeValueException;

public class Clause {

	private LinkedList<Literal> literals;
	//private float weight;

	public Clause(String clause_str) {
		// TODO Auto-generated constructor stub
		fromString(clause_str);
	}
	
	public LinkedList<Literal> getLiterals(){
		return literals;
	}

	private void fromString(String clause_str) {
		String[] values = clause_str.split(" ");

		//weight = Float.parseFloat(values[0]);
		literals = new LinkedList<Literal>();

		for (int i = 1; i < values.length; i++) {
			if (values[i].equals("0")) {
				return;
			} else {
				try {
					literals.add(new Literal(Integer.parseInt(values[i])));
				} catch (NumberFormatException | InvalidAttributeValueException e) {
					e.printStackTrace();
				}
			}

		}

	}

	public boolean satisfiedByBool(BitString assignment) {
		for (Literal literal : literals) {
			if (literal.satisfiedBy(assignment)) {
				return true;
			}
		}
		return false;
	}

	public int satisfiedByInt(BitString assignment) {
		if (satisfiedByBool(assignment))
			return 1;
		else
			return 0;
	}
	
}
