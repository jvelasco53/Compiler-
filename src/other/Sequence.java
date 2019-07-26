package other;

import java.util.List;

public class Sequence extends Stmt{
	private List<Stmt> statements;
	public Sequence(List<Stmt> statements) {
		this.statements = statements;
	}
	@Override
	public void display(String indent) {
		// TODO Auto-generated method stub
		System.out.println(indent + "Sequence");
		for (Stmt stemet: statements) {
			stemet.display(indent + " ");
		}
	}
}
