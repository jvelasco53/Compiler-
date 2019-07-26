package interpreter;

import java.util.List;
import other.Parameter;
import other.Type;
import other.Block;

public class FunctionValue extends Value{
	private List<Parameter> parameters;
	private Type first;
	private Block block;
	
	public FunctionValue(List<Parameter> parameters, Type first, Block block) {
		// TODO Auto-generated constructor stub
		this.parameters = parameters;
		this.first = first;
		this.block = block;
	}

	@Override
	public int intValue() {
		System.exit(1);
		return 0;
	}

	@Override
	public boolean boolValue() {
		// TODO Auto-generated method stub
		System.exit(1);
		return false;
	}
	public String toString() {
		String show = "function val" + '\n';
		show += "Parameters: " + parameters.toString() + '\n';
		show += "Type: " + first + '\n';
		return show;
	}
	
	public List<Parameter> getParameter(){return parameters;}
	public Type getType() {return first;}
	public Block getBlock() {return block;}

}
