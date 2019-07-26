package other;

import java.util.List;

public class FunDecls extends ASTNode {
	private String id;
	private Type type;
	private Block block;
	private List<Parameter> params;
	/*
	 * <FunDecl> --> fun ID ( <ParamList> ) : <Type> ; <Block>
	 * fun id <paramlist> type block 
	 */
	public FunDecls (String id, List<Parameter> params, Type type, Block block) {
		this.id = id;
		this.params = params;
		this.type = type;
		this.block = block;
	}
	public String getId() {
		return id;
	}
	public Type getType() {
		return type;
	}
	public Block getBlock() {
		return block;
	}
	public List<Parameter> getParams() {
		return params;
	}
	public void display(String indentit) {
		System.out.println(indentit + "fun " + id + " : " + type);
		for(Parameter parameterss: params) {
			parameterss.display(indentit + params);
		}
		block.display(indentit + " ");
	}
}
