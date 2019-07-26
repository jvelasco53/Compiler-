package other;

public class Parameter extends ASTNode {
	public String id;
	Type type;
	public Parameter (String id, Type type) {
		this.id = id;
		this.type = type;
	}
	public String getId() {
		return id;
	}
	public Type getType() {
		return type;
	}
	public void display(String indentit) {
		System.out.println(id + " : "+ type);
	}
	
}
