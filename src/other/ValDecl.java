package other;

public class ValDecl extends ASTNode{
	private String id;
	private int num;
	public ValDecl(String id, int num) {
		this.id = id;
		this.num = num;
	}
	public String getID() { return id;}
	public int getNum() {return num;}
	public void display(String indentit) {
		System.out.println(indentit + "val " + id + " = " + num);
	}
}	
