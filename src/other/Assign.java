package other;

public class Assign extends Stmt {
	private String id;
	private Expressions expression;
	/*
	 * example Assign b
	 * 				BinOp EQ
	 *
	 */
	public Assign(String id, Expressions expression) {
		this.id = id;
		this.expression = expression;
	}
	public String getId() {return id;}
	public Expressions getExpr() {return expression;}
	@Override
	public void display(String indent) {
		// TODO Auto-generated method stub
		System.out.println(indent + "Assign "+id);
		expression.display(indent+ " ");
	}

}
