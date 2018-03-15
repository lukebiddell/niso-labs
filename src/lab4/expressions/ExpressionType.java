package lab4.expressions;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public enum ExpressionType {
	ADD, SUB, MUL, DIV, POW, SQRT, LOG, EXP, MAX, IFLEQ, DATA, DIFF, AVG, NUMBER;

	private static final List<ExpressionType> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
	private static final int SIZE = VALUES.size() - 1;
	private static final int NUMBER_WEIGHT = 2;

	public static ExpressionType randomType(boolean includeTerminal) {
		int rand = ThreadLocalRandom.current().nextInt(includeTerminal ? SIZE + NUMBER_WEIGHT : SIZE);
		return  rand >= SIZE ? NUMBER : VALUES.get(rand);
	}

	public int size() {
		switch (this) {
		case ADD:
		case SUB:
		case MUL:
		case DIV:
		case POW:
		case MAX:
		case DIFF:
		case AVG:
			return 2;
		case SQRT:
		case LOG:
		case EXP:
		case DATA:
			return 1;
		case IFLEQ:
			return 4;
		case NUMBER:
			return 0;
		default:
			return -1;
		}
	}
}
