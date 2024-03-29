package Actions;
public class Token {
	/**
	 * Construct a Token object given its components.
	 * 
	 * @param line the line number (starting from 1) where the token was found
	 * @param column the column number (starting from 1) where the token started
	 * @param type the TokenType of the token
	 * @param lexeme the string value of the token
	 */
	public Token(int line, int column, TokenType type, String lexeme) {
		this.line = line;
		this.column = column;
		this.type = type;
		this.lexeme = lexeme;
	}

	// Override the default toString() for use in development and debugging.
	public String toString() {
		StringBuilder result = new StringBuilder(type.toString());
		if (lexeme != null) {
			result.append(" ").append(lexeme);
		}
		result.append(" ").append(line).append(":").append(column);
		return result.toString();
	}

	public final int line, column;
	public final TokenType type;
	public final String lexeme;
}
