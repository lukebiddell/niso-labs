package helper;

import javax.naming.directory.InvalidAttributeValueException;

public class Literal {

	private final boolean positive;
	private final int indice;

	public boolean isPositive() {
		return positive;
	}

	public int getIndice() {
		return indice;
	}

	public Literal(int literal) throws InvalidAttributeValueException {
		if (literal == 0) {
			throw new InvalidAttributeValueException("Literal value can't be zero");
		}

		positive = (literal > 0);
		indice = Math.abs(literal) - 1;
	}

	public boolean satisfiedBy(BitString assignment) {
		if (indice <= assignment.size()) {
			return assignment.getBitSet().get(indice) == positive;
		} else {
			// throw new IndexOutOfBoundsException("Indice of literal is greater
			// than bitstring length");
			return false;
		}

	}

}
