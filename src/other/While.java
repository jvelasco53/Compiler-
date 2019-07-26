package other;

public class While extends Stmt{
	private Expressions expr;
	private Stmt stmt;
	
	/*
	 * While <expr> do <stmt> 
	 */
	public While(Expressions expr, Stmt stmt) {
		this.expr = expr;
		this.stmt = stmt;
	}
	public Expressions getExpressions() {return expr;}
	public Stmt getStmt() {return stmt;}
	public void display(String indentit) {
		System.out.println(indentit+"While");
		expr.display(indentit+" ");
		stmt.display(indentit+" ");
	}
}