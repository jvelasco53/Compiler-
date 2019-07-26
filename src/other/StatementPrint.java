package other;

import java.util.List;

public class StatementPrint extends Stmt{
	private List<Item> items;
	public StatementPrint(List<Item> items) {
		this.items = items;
	}
	@Override
	public void display(String indent) {
		// TODO Auto-generated method stub
		System.out.println(indent + "print");
		for(Item now: items) {
			now.display(indent + " ");
		}
	}
}
