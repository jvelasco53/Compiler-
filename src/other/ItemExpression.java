package other;

public class ItemExpression extends Item {
	private Expressions exprI;
	public ItemExpression(Expressions exprI) {
		this.exprI = exprI;
	}
	public Expressions getExpr() {return exprI;}
	public void display(String indentit) {
		System.out.println(indentit + "ExprItem");
		exprI.display(indentit+" ");
	}

}
