package other;

public class FactorId extends Expressions {
	private String id;
	public FactorId(String id) {
		this.id=id;
	}
	public void display(String indent) {
		System.out.println(indent + "ID" + id);
	}
}
