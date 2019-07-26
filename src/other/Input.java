package other;

public class Input extends Stmt{
	private String string;
	public Input(String string) {
		this.string = string;
	}
	public void display(String indentit) {
		System.out.println(indentit + "Input " + string);
	}
}
