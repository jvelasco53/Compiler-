package interpreter;

import java.util.HashMap;
import java.util.Scanner;
import java.util.Stack;

public class SymbolTable {

	
		// TODO Auto-generated constructor stub
		private Stack<HashMap<String, Value>> scope;
		public static Scanner scanit = new Scanner(System.in);
	public SymbolTable() {
		scope = new Stack<>();
		goin();
	}
	public void bind(String id, Value value) {
		HashMap<String, Value> hash = scope.peek();
		if(hash.containsKey(id)) {
			System.err.println("This var already exist");
		}
		else {
			hash.put(id, value);
		}
	}
	public Value lookUp(String id) {
		for (int i = scope.size() - 1; i>=0;i--) {
			if(scope.get(i).containsKey(id)) {
				return scope.get(i).get(id);
			}
		}
		Value setVAl = new IntCell(0);
		bind(id, setVAl);
		return setVAl;
	}
	public void goin() {
		scope.push(new HashMap<>());
	}
	public void out() {
		scope.pop();
		}

}
