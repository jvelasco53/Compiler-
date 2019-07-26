package other;

public class Program extends ASTNode{
	private String name;
	private Block block;
		
	public Program(String name, Block block) {
		this.name = name;
		this.block = block;
	}
	
	public String getName() { return name;}
	public Block getBlock() {return block;}
	
	public void display (String indent) {
		System.out.println(indent + "program "+name);
		block.display(indent + " ");
	}
}
