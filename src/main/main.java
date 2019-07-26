package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

import Actions.*;
//import csc426.ast.Program;
import other.*;

public class main {
	public static void main(String[] args) throws IOException {
		/*Scanner scanner = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		Parser parser = new Parser(scanner);
		
		Program program = parser.parseProgram();
		program.display("");
		Token token;
		do {
			token = scanner.next();
			System.out.println(token);
		} while (token.type != TokenType.EOF);

		scanner.close();*/
		if(args.length !=1 ) {
			System.out.println("Run the file from Command prompt by going into the bin file of the program and using the test file name");
			
		}
		
		else {
			String filename = args[0];
			Scanner scanner = new Scanner(new FileReader(System.getProperty("user.dir") + "/test/" + filename));
			Parser parser = new Parser(scanner);
			Program program = parser.parseProgram();
			program.interpret();
		}
	}
}
