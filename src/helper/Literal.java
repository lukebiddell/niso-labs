package helper;

import javax.naming.directory.InvalidAttributeValueException;

public class Literal {

	private final boolean positive;
	private final int indice;

	public Literal(int literal) throws InvalidAttributeValueException {
		positive = (literal > 0);
		indice = Math.abs(literal);
		if (indice == 0) {
			throw new InvalidAttributeValueException("Literal value can't be zero");
		}
	}

	public boolean satisfiedBy(BitString assignment) {
		if (indice <= assignment.size()) {
			return assignment.getBitSet().get(indice - 1) == positive;
		} else {
			// throw new IndexOutOfBoundsException("Indice of literal is greater
			// than bitstring length");
			return false;
		}

	}

}
