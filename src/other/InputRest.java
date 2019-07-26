package other;

public class InputRest extends Stmt{
	private String string;
	private String string2;
	public InputRest(String string, String string2) {
		this.string = string;
		this.string2 = string2;
	}
	public void display(String indentit) {
		System.out.println(indentit + "InputRest" + string + ", "+string2);
	}
}

