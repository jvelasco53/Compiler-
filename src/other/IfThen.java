package other;

public class IfThen extends Stmt{
	private Expressions expr;
	private Stmt stament;
	
	public IfThen(Expressions expr, Stmt stament) {
		this.expr = expr;
		this.stament = stament;
	}
	public void display(String indentit) {
		System.out.println(indentit + "ifthen");
		expr.display(indentit +" ");
		stament.display(indentit+" ");
	}
}
