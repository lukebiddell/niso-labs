package lab4.helper;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import de.tudresden.inf.lat.jsexp.Sexp;
import de.tudresden.inf.lat.jsexp.SexpFactory;
import de.tudresden.inf.lat.jsexp.SexpParserException;
import lab4.expressions.AddExpression;
import lab4.expressions.AvgExpression;
import lab4.expressions.DataExpression;
import lab4.expressions.DiffExpression;
import lab4.expressions.DivExpression;
import lab4.expressions.ExpExpression;
import lab4.expressions.ExpressionType;
import lab4.expressions.IfleqExpression;
import lab4.expressions.LogExpression;
import lab4.expressions.MaxExpression;
import lab4.expressions.MulExpression;
import lab4.expressions.NumberExpression;
import lab4.expressions.PowExpression;
import lab4.expressions.SqrtExpression;
import lab4.expressions.SubExpression;

public class ExpressionFactory {

	private static final double MAX_RAND = 100;

	public static Expression parse(String sexp_string) throws SexpParserException {
		Sexp sexp = SexpFactory.parse(sexp_string);
		return parse(sexp);
	}

	public static Expression parse(Sexp sexp) {
		if (sexp.isAtomic()) {
			double value = Double.parseDouble(sexp.toString());
			return new NumberExpression(value);
		} else {
			ExpressionType type = ExpressionType.valueOf(sexp.get(0).toString().toUpperCase());
			//System.out.println("Type : " + type + "\tLength: " + sexp.getLength());
			// return fromType(type, sexp.)
			return fromType(type, sexp);
			/*
			 * switch (type) { case ADD: return new
			 * AddExpression(parse(sexp.get(1)), parse(sexp.get(2))); case SUB:
			 * return new SubExpression(parse(sexp.get(1)), parse(sexp.get(2)));
			 * case MUL: return new MulExpression(parse(sexp.get(1)),
			 * parse(sexp.get(2))); case DIV: return new
			 * DivExpression(parse(sexp.get(1)), parse(sexp.get(2))); case POW:
			 * return new PowExpression(parse(sexp.get(1)), parse(sexp.get(2)));
			 * case SQRT: return new SqrtExpression(parse(sexp.get(1))); case
			 * LOG: return new LogExpression(parse(sexp.get(1))); case EXP:
			 * return new ExpExpression(parse(sexp.get(1))); case MAX: return
			 * new MaxExpression(parse(sexp.get(1)), parse(sexp.get(2))); case
			 * IFLEQ: return new IfleqExpression(parse(sexp.get(1)),
			 * parse(sexp.get(2)), parse(sexp.get(3)), parse(sexp.get(4))); case
			 * DATA: return new DataExpression(parse(sexp.get(1))); case DIFF:
			 * return new DiffExpression(parse(sexp.get(1)),
			 * parse(sexp.get(2))); case AVG: return new
			 * AvgExpression(parse(sexp.get(1)), parse(sexp.get(2))); default:
			 * throw new Error("Error parsing expression: " + type); }
			 */
		}

	}

	private static Expression fromType(ExpressionType type, Sexp sexp) {
		ArrayList<Expression> expressions = new ArrayList<Expression>();
		for (int i = 1; i < sexp.getLength(); i++) {
			expressions.add(parse(sexp.get(i)));
		}
		return fromType(type, expressions);
	}

	private static Expression fromType(ExpressionType type, ArrayList<Expression> exp) {
		switch (type) {
		case ADD:
			return new AddExpression(exp.get(0), exp.get(1));
		case SUB:
			return new SubExpression(exp.get(0), exp.get(1));
		case MUL:
			return new MulExpression(exp.get(0), exp.get(1));
		case DIV:
			return new DivExpression(exp.get(0), exp.get(1));
		case POW:
			return new PowExpression(exp.get(0), exp.get(1));
		case SQRT:
			return new SqrtExpression(exp.get(0));
		case LOG:
			return new LogExpression(exp.get(0));
		case EXP:
			return new ExpExpression(exp.get(0));
		case MAX:
			return new MaxExpression(exp.get(0), exp.get(1));
		case IFLEQ:
			return new IfleqExpression(exp.get(0), exp.get(1), exp.get(2), exp.get(3));
		case DATA:
			return new DataExpression(exp.get(0));
		case DIFF:
			return new DiffExpression(exp.get(0), exp.get(1));
		case AVG:
			return new AvgExpression(exp.get(0), exp.get(1));
		default:
			throw new Error("Error parsing expression: " + type);
		}
	}

	public static Expression random(int depth, boolean includeTerminal) {
		ExpressionType type = depth > 1 ? ExpressionType.randomType(includeTerminal) : ExpressionType.NUMBER;

		if (type == ExpressionType.NUMBER) {
			return new NumberExpression(ThreadLocalRandom.current().nextDouble(-MAX_RAND, MAX_RAND));
		} else {
			ArrayList<Expression> expressions = new ArrayList<Expression>();

			for (int i = 0; i < type.size(); i++)
				expressions.add(random(depth - 1, includeTerminal));

			return fromType(type, expressions);
		}
		
	}
	
	public static Expression clone(Expression e){
		if(!e.isTerminal()){
			return fromType(e.getType(), e.children());
		} else{
			return new NumberExpression(e.eval(null));
		}
	}

}
