package Actions;

import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

/**
 * A Lexical Analyzer for a subset of BASIC. Uses a (Mealy) state machine to
 * extract the next available token from the input each time next() is called.
 * Input comes from a Reader, which will generally be a BufferedReader wrapped
 * around a FileReader or InputStreamReader (though for testing it may also be
 * simply a StringReader).
 * 
 * Note that this is essentially the same as the YASL scanner, except the lists
 * of keywords, and operators and punctuation are different, and the BASIC subset
 * does not have a comment mechanism.
 * 
 * @author bhoward
 */
public class Scanner {
	/**
	 * Construct the Scanner ready to read tokens from the given Reader.
	 * 
	 * @param in
	 */
	public Scanner(Reader in) {
		source = new Source(in);

		keywords = new HashMap<>();
		keywords.put("print", TokenType.PRINT);
		keywords.put("input", TokenType.INPUT);
		keywords.put("end", TokenType.END);
		keywords.put("if", TokenType.IF);
		keywords.put("then", TokenType.THEN);
		keywords.put("let", TokenType.LET);
		keywords.put("goto", TokenType.GOTO);
		keywords.put("void", TokenType.Void);
		keywords.put("int", TokenType.Int);
		keywords.put("bool", TokenType.Bool);
		keywords.put("gosub", TokenType.GOSUB);
		keywords.put("else", TokenType.ELSE);
		keywords.put("return", TokenType.RETURN);
		keywords.put("or", TokenType.OR);
		keywords.put("and", TokenType.AND);
		keywords.put("for", TokenType.FOR);
		keywords.put("to", TokenType.TO);
		keywords.put("next", TokenType.NEXT);
		keywords.put("not",  TokenType.NOT);
		keywords.put("true", TokenType.TRUE);
		keywords.put("false", TokenType.FALSE);
		keywords.put("program", TokenType.Pro);
		keywords.put("const", TokenType.Const);
		keywords.put("div", TokenType.DIV);
		keywords.put("mod", TokenType.MOD);
		keywords.put("begin", TokenType.BEGIN);
		keywords.put("var", TokenType.var);
		keywords.put("var", TokenType.var);
		keywords.put("fun", TokenType.fun);
		keywords.put("while", TokenType.WHILE);
		keywords.put("do", TokenType.DO);

		opsAndPunct = new HashMap<>();
		opsAndPunct.put("+", TokenType.PLUS);
		opsAndPunct.put("-", TokenType.MINUS);
		opsAndPunct.put("*", TokenType.STAR);
		opsAndPunct.put("/", TokenType.SLASH);
		opsAndPunct.put("=", TokenType.EQ);
		opsAndPunct.put("<", TokenType.LT);
		opsAndPunct.put(">", TokenType.GT);
		opsAndPunct.put(",", TokenType.COMMA);
		opsAndPunct.put(";", TokenType.Semi);
		opsAndPunct.put(".", TokenType.Period);
		opsAndPunct.put(":", TokenType.COLON);
		opsAndPunct.put("(", TokenType.LPAR);
		opsAndPunct.put(")", TokenType.RPAR);
		opsAndPunct.put("\n", TokenType.EOL);
		opsAndPunct.put("==", TokenType.EQQ);
		opsAndPunct.put("<>", TokenType.NEQ);
		opsAndPunct.put("<=", TokenType.LTE);
		opsAndPunct.put(">=", TokenType.GTE);
		
	}

	/**
	 * Extract the next available token. When the input is exhausted, it will return
	 * an EOF token on all future calls.
	 * 
	 * @return the next Token object
	 * @throws IOException 
	 */
	public Token next() throws IOException {
		int state = 0;
		StringBuilder lexeme = new StringBuilder();
		int startLine = source.line;
		int startColumn = source.column;

		while (true) {
			switch (state) {
			case 0: // Start of a token
				if (source.atEOF) {
					// Token is EOF
					return new Token(source.line, source.column, TokenType.EOF, null);
				} else if (source.current == '0') {
					// Token is zero
					startLine = source.line;
					startColumn = source.column;
					lexeme.append(source.current);
					source.advance();
					state = 1;
				} else if (Character.isDigit(source.current)) {
					// Token is non-zero integer literal
					startLine = source.line;
					startColumn = source.column;
					lexeme.append(source.current);
					source.advance();
					state = 2;
				} else if (Character.isLetter(source.current)) {
					// Token is identifier or keyword
					startLine = source.line;
					startColumn = source.column;
					lexeme.append(source.current);
					source.advance();
					state = 3;
				} else if ("+-*=<>:,()\n".contains(String.valueOf(source.current))) {
					// Token is operator or punctuation
					startLine = source.line;
					startColumn = source.column;
					lexeme.append(source.current);
					source.advance();
					state = 4;
				} else if (Character.isWhitespace(source.current)) {
					// Skip whitespace -- note that \n was already caught as "punctuation"
					source.advance();
				} else if (source.current == '/') {
					source.advance();
					state = 5;
				} 
				else if (source.current == '"') {
					startLine = source.line;
					startColumn = source.column;
					state = 9;
					source.advance();
				}else {
					// Unexpected character; print error message and skip
					System.err.println("Illegal character: " + source.current);
					System.err.println("  at " + source.line + ":" + source.column);
					source.advance();
				}
				break;

			case 1: // Token is NUM(0)
				source.advance();
				return new Token(startLine, startColumn, TokenType.NUM, lexeme.toString());

			case 2: // Accept rest of a (non-zero) NUM token
				if (source.atEOF || !Character.isDigit(source.current)) {
					return new Token(startLine, startColumn, TokenType.NUM, lexeme.toString());
				} else {
					lexeme.append(source.current);
					source.advance();
				}
				break;

			case 3: // Accept rest of an ID or keyword token
				if (source.atEOF || !Character.isLetterOrDigit(source.current)) {
					String lex = lexeme.toString();
					if (keywords.containsKey(lex)) {
						return new Token(startLine, startColumn, keywords.get(lex), null);
					} else {
						return new Token(startLine, startColumn, TokenType.ID, lex);
					}
				} else {
					lexeme.append(source.current);
					source.advance();
				}
				break;

			case 4: // Token is an operator or punctuation
				String  lex = lexeme.toString();
				if ("=".equals(lex)) {
					String n = lex + source.current;
					if (opsAndPunct.containsKey(n)) {
						source.advance();
						return new Token(startLine, startColumn, opsAndPunct.get(n), null);
					}
				}
				if (lex.equals("<")) {
					String n = lex + source.current;
					if (opsAndPunct.containsKey(n)) {
						
						source.advance();
						return new Token(startLine, startColumn, opsAndPunct.get(n), null);
					}
				}
				if (lex.equals(">")) {
					String n = lex + source.current;
					if (opsAndPunct.containsKey((n))) {
						
						source.advance();
						return new Token(startLine, startColumn, opsAndPunct.get(n), null);
					}
				}
					return new Token(startLine, startColumn, opsAndPunct.get(lex), null);
			case 5: // Skip the second character of a comment
				if (source.atEOF || (source.current != '*' && source.current != '/')) {
					// Illegal start of comment; print error message but leave current character
					System.err.println("Malformed comment: found " + source.current + " after /");
					System.err.println("  at " + source.line + ":" + source.column);
					state = 0;
				} else if (source.current == '*') {
					// Slash-star comment -- look for closing star-slash
					source.advance();
					state = 6;
				} else if (source.current == '/') {
					// Slash-slash comment -- look for end of line
					source.advance();
					state = 8;
				}
				break;

			case 6: // Skip the rest of a slash-star comment
				if (source.atEOF) {
					// Unclosed comment at EOF; print error message
					System.err.println("Unclosed comment at end of file");
					state = 0;
				} else if (source.current == '*') {
					// Possible ending star-slash
					source.advance();
					state = 7;
				} else {
					// Still in comment
					source.advance();
				}
				break;

			case 7: // Just saw a star in a slash-star comment
				if (source.atEOF) {
					// Unclosed comment at EOF; print error message
					System.err.println("Unclosed comment at end of file");
					state = 0;
				} else if (source.current == '/') {
					// End of comment
					source.advance();
					state = 0;
				} else if (source.current == '*') {
					// Maybe _this_ is the ending star; stay in this state
					source.advance();
				} else {
					// Still in comment
					source.advance();
					state = 6;
				}
				break;

			case 8: // Skip the rest of a slash-slash comment
				if (source.atEOF || source.current == '\n') {
					// End of comment
					state = 0;
				} else {
					// Still in comment
					source.advance();
				}
				break;
			case 9: 
				if (source.current == '"') {
					state = 10;
				}
				else if (source.current == '\n'){System.out.println("missing an expexted quotation mark");source.close();}
				else {lexeme.append(source.current);}
				source.advance();
				break;
			case 10: 
				if (source.current == '"') {
					state = 9;
					lexeme.append('"');
					source.advance();
				}
				else {return new Token(startLine, startColumn, TokenType.STRING, lexeme.toString());}
				break;
			}
		}
	}

	/**
	 * Close the underlying Reader.
	 * 
	 * @throws IOException
	 */
	public void close() throws IOException {
		source.close();
	}

	private Source source;
	private Map<String, TokenType> keywords;
	private Map<String, TokenType> opsAndPunct;
}
