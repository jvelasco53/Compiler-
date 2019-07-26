package other;

public class ifthenelse extends Stmt{
	private Expressions expr;
	private Stmt a;
	private Stmt b;
	public ifthenelse(Expressions expr, Stmt a, Stmt b) {
		this.expr = expr;
		this.a = a;
		this.b = b;
	}
	public void display(String indentit) {
		System.out.println(indentit + "ifthenelse");
		expr.display(indentit+" ");
		a.display(indentit +" ");
		b.display(indentit +" ");
	}
}
