package lab4.helper;

import de.tudresden.inf.lat.jsexp.Sexp;
import de.tudresden.inf.lat.jsexp.SexpFactory;
import de.tudresden.inf.lat.jsexp.SexpParserException;
import lab4.expressions.AddExpression;
import lab4.expressions.AvgExpression;
import lab4.expressions.DataExpression;
import lab4.expressions.DiffExpression;
import lab4.expressions.DivExpression;
import lab4.expressions.ExpExpression;
import lab4.expressions.IfleqExpression;
import lab4.expressions.LogExpression;
import lab4.expressions.MaxExpression;
import lab4.expressions.MulExpression;
import lab4.expressions.NumberExpression;
import lab4.expressions.PowExpression;
import lab4.expressions.SqrtExpression;
import lab4.expressions.SubExpression;

public class ExpressionFactory {

	public static Expression parse(String sexp_string) throws SexpParserException {
		Sexp sexp = SexpFactory.parse(sexp_string);
		return parse(sexp);
	}

	public static Expression parse(Sexp sexp) {
		if(sexp.isAtomic()){
			double value = Double.parseDouble(sexp.toString());
			return new NumberExpression(value);
		} else {
			String operator = sexp.get(0).toString();

			switch (operator) {
			case "add":
				return new AddExpression(parse(sexp.get(1)), parse(sexp.get(2)));
			case "sub":
				return new SubExpression(parse(sexp.get(1)), parse(sexp.get(2)));
			case "mul":
				return new MulExpression(parse(sexp.get(1)), parse(sexp.get(2)));	
			case "div":
				return new DivExpression(parse(sexp.get(1)), parse(sexp.get(2)));
			case "pow":
				return new PowExpression(parse(sexp.get(1)), parse(sexp.get(2)));
			case "sqrt":
				return new SqrtExpression(parse(sexp.get(1)));
			case "log":
				return new LogExpression(parse(sexp.get(1)));
			case "exp":
				return new ExpExpression(parse(sexp.get(1)));
			case "max":
				return new MaxExpression(parse(sexp.get(1)), parse(sexp.get(2)));
			case "ifleq":
				return new IfleqExpression(parse(sexp.get(1)), parse(sexp.get(2)), parse(sexp.get(3)), parse(sexp.get(4)));
			case "data":
				return new DataExpression(parse(sexp.get(1)));
			case "diff":
				return new DiffExpression(parse(sexp.get(1)), parse(sexp.get(2)));
			case "avg":
				return new AvgExpression(parse(sexp.get(1)), parse(sexp.get(2)));
			default:
				throw new Error("Error parsing expression: " + operator);
			}
		}
		
		
	}

}
