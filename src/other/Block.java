package other;

import java.util.List;

public class Block extends ASTNode {
	public List<ValDecl> valdecl;
	public List<VarDecls> vardecl;
	public List<FunDecls> fundecl;
	public Stmt stmt;
	
	public Block (List<ValDecl> valdecl, List<VarDecls> vardecl, List<FunDecls> fundecl, Stmt stmt) {
		this.valdecl = valdecl;
		this.vardecl = vardecl;
		this.fundecl = fundecl;
		this.stmt = stmt;
	}
	
	public List<ValDecl> Valdget() { return valdecl;}
	public List<VarDecls> Vardget() { return vardecl;}
	public List<FunDecls> fundecl() { return fundecl;}
	public Stmt Stmtget() { return stmt;}
	
	public void display(String indent) {
		System.out.println(indent + "Block");
		for (ValDecl value: valdecl) {
			value.display(indent + " ");
		}
		for (VarDecls variable: vardecl) {
			variable.display(indent + " ");
		}
		for (FunDecls function: fundecl) {
			function.display(indent + " ");
		}
	}
}
