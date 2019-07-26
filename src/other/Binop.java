package other;

public class Binop extends Expressions{
	private Expressions a;
	private Expressions b;
	private Allop middle;
	public Binop(Expressions a, Expressions b, Allop middle) {
		this.a = a;
		this.b = b;
		this.middle = middle;
	}
	@Override
	public void display(String indent) {
		// TODO Auto-generated method stub
		System.out.println(indent + "Binop" + middle);
		a.display(indent+" ");
		b.display(indent+" ");
	}
}
