package interpreter;

public class IntValue extends Value{

	protected int value;
	public IntValue(int value) {
		// TODO Auto-generated constructor stub
		this.value = value;
	}

	@Override
	public int intValue() {
		// TODO Auto-generated method stub
		return value;
	}

	@Override
	public boolean boolValue() {
		System.err.println("Can't use this input");
		System.exit(1);
		return false;
	}

}
