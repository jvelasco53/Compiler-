package other;

import java.util.List;

public class Parameters extends ASTNode{
	private List<Parameter> parameters;
	public Parameters(List<Parameter> params) {this.parameters = params; }
	public List<Parameter> getListOfParams(){ return parameters; }
	public void display(String indentit) {
		for (Parameter parameters2:parameters) {
			parameters2.display(indentit + " ");
		}
	}
}
