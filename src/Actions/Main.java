package Actions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

//import csc426.ast.Program;
import other.*;

public class Main {
	public static void main(String[] args) throws IOException {
		Scanner scanner = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		Parser parser = new Parser(scanner);
		
		Program program = parser.parseProgram();
		program.display("");
		Token token;
		do {
			token = scanner.next();
			System.out.println(token);
		} while (token.type != TokenType.EOF);

		scanner.close();
	}
}
