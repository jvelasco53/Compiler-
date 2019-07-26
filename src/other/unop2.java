package other;

public class unop2 extends Expressions{
	private unOp op;
	private Expressions expr;
	public unop2 (unOp op, Expressions expr) {
		this.op = op;
		this.expr = expr;
	}
	public void display(String indentit) {
		System.out.println(indentit+ "UnOp "+op);
		expr.display(indentit+" ");
	}
}
