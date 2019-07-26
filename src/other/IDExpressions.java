package other;

public class IDExpressions extends Expressions {
	private String id;
	public IDExpressions(String id) {
		this.id = id;
	}
	@Override
	public void display(String indent) {
		// TODO Auto-generated method stub
		System.out.println(indent + "ID "+id);
	}
	
	
}
