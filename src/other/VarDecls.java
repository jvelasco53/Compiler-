package other;

public class VarDecls extends ASTNode{
	private String id;
	private Type type;
	public VarDecls(String id, Type type) {
		this.id = id;
		this.type = type;
	}
	public String getID() {return id; }
	public Type getType() {return type;	}
	public void display(String indentit) {
		System.out.println(indentit + "var " + id + " : " + type);
	}
}
