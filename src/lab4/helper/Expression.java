package lab4.helper;

import de.tudresden.inf.lat.jsexp.Sexp;
import de.tudresden.inf.lat.jsexp.SexpFactory;
import de.tudresden.inf.lat.jsexp.SexpParserException;

public class Expression {

	Sexp sexp;
	
	public Expression(){
		
	}
	
	public static Expression parse(String expr_str) throws SexpParserException{
		Expression e = new Expression();
		e.sexp = SexpFactory.parse(expr_str);
		return e;
	}
	
	public double evaluate(){
		System.out.println(sexp.get(1));
		
		
		return new Double(0);
	}
	
}
