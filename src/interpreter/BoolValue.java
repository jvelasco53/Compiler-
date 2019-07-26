package interpreter;

public class BoolValue extends Value{

	protected boolean input;
	public BoolValue(boolean input) {
		// TODO Auto-generated constructor stub
		this.input = input;
	}

	@Override
	public int intValue() {
		// TODO Auto-generated method stub
		System.err.println("Can't use this as an input");
		System.exit(1);
		return 0;
	}

	@Override
	public boolean boolValue() {
		// TODO Auto-generated method stub
		return input;
	}

}
