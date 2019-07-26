package other;

public class Numbers extends Expressions{
	private int num;
	public Numbers(int num) {
		this.num = num;
	}
	public void display(String indent) {
		System.out.println(indent + "num" + num);
	}
}
