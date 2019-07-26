package Actions;

public enum TokenType {
	
	Const, // const
	BEGIN, // begin keyword
	DIV, // divide
	MOD, // Modulo 
	NUM, // numeric literal
	ID, // identifier
	Int, // integer
	Bool, // Boolean
	Void, // void
	COLON, // colon (:)
	COMMA, // comma (,)
	Period, // period(.)
	LPAR, // left parenthesis (()
	RPAR, // right parenthesis ())
	EQ, // equal (=) assignment
	LT, // less than (<)
	GT, // greater than (>)
	PLUS, // plus operator (+)
	MINUS, // minus operator (-)
	STAR, // times operator (*)
	SLASH, // divide operator (/)
	PRINT, // print keyword
	INPUT, // input keyword
	WHILE, // while keyword
	ELSE, // else keyword
	OR, // or keyword
	AND, // and keyword
	DO, // do keyword
	TRUE, // true keyword
	FALSE, // false keyword
	NOT, // not keyword
	STRING, // String
	END, // end keyword
	IF, // if keyword
	Pro, // program keyword
	THEN, // then keyword
	LET, // let keyword
	GOTO, // goto keyword
	GOSUB, // gosub keyword
	RETURN, // return keyword
	FOR, // for keyword
	TO, // to keyword
	NEXT, // next keyword
	EOL, // end-of-line
	EOF, // end-of-file
	LTE, // less than or equal to (<=)
	GTE, // greater than or equal to (>=)
	NEQ, // not equal (<>)
	EQQ, // equal comparison (==)
	Semi, // semicolon (;)
	val, // value
	var, // var
	fun, // function
}
