package other;

public class StatementExpression extends Stmt{
	private Expressions expression;
	public StatementExpression(Expressions expression) {
		this.expression = expression;
	}
	@Override
	public void display(String indent) {
		// TODO Auto-generated method stub
		System.out.println(indent + "ExprStmt");
		expression.display(indent + " ");
	}
}
