package interpreter;

public class VoidValue extends Value{

	public int intValue() {
		System.err.println("This doesn't match");
		System.exit(1);
		return 0;
	}
	public boolean boolValue() {
		System.err.println("This doesn't match");
		System.exit(1);
		return false;
	}

}
