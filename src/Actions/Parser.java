package Actions;

import java.beans.Expression;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Actions.Scanner;
import Actions.Token;
import Actions.TokenType;
import other.*;

public class Parser {
	private Scanner scanner;
	private Token current;

	public Parser(Scanner scanner) {
		this.scanner = scanner;
		try {
			this.current = scanner.next();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private Token match(TokenType type) {
		if (current.type == type) {
			Token result = current;
			try {
				current = scanner.next();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return result;
		} else {
			System.err.println("Expected " + type + " but found " + current);
			System.exit(1);
			return null; // never reaches this
		}
	}
	private boolean check(TokenType... types) {
		for (TokenType type : types) {
			if (current.type == type) {
				return true;
			}
		}
			return false;
	}
	/*
	 * Program --> program id ; <block>			First = program
	 */
		public Program parseProgram() {
			match(TokenType.Pro);
			Token atila = match(TokenType.ID);
			String id = atila.lexeme;
			match(TokenType.Semi);
			Block block = parseBlock();
			match(TokenType.Period);
			match(TokenType.EOF);
			return new Program(id, block);
		}
		
		
		/*
		 * <Block> --> <ValDecls><VarDecls><FunDecls><Stmt>		First = val
		 */
		public Block parseBlock() {
			List<ValDecl> vals = parseValDecls();
			List<VarDecls> vars = parseVarDecls();
			List<FunDecls> funs = parseFunDecls();
			Stmt statement = parseStmt();
			
			return new Block(vals, vars, funs, statement);
		}
		
		/*
		 * <ValDecls> --> <ValDecl><ValDecls>					First = val
		 * 				| e										Follow = var
		 */
		
		public List<ValDecl> parseValDecls(){
			List<ValDecl> list = new ArrayList<>();
			while (check(TokenType.val)) {
				list.add(parseValDecl());
			}
			return list;
		}
		
		/*
		 * <ValDecl> --> val ID = <sign> Num					First = val
		 */
		public ValDecl parseValDecl(){
			match(TokenType.val);
			Token atila = match(TokenType.ID);
			match(TokenType.EQ);
			int sign = parseSign();
			Token num = match(TokenType.NUM);
			int numm = sign * Integer.parseInt(num.lexeme); // get sign, b 1 or -1 and mult by num
			match(TokenType.Semi);
			return new ValDecl(atila.lexeme, numm);
		}
		
		/*
		 * <sign> --> -											First = -
		 * 			| e											Follow = Num
		 */
		
		public int parseSign() {
			if (check(TokenType.MINUS)){
				match(TokenType.MINUS);
				return -1;
			}
			return 1;
		}
		
		/*
		 * <VarDecls> --> <VArDecl><VarDecls>					First = var
		 * 				| e										Follow = fun
		 */
		
		public List<VarDecls> parseVarDecls(){
			List<VarDecls> list = new ArrayList<>();
			while(check(TokenType.var)) {
				list.add(parseVarDecl());
			}
			return list;
		}
		
		/*
		 * <VarDecl> = --> var ID : <Type> ;					First = var
		 */
		
		public VarDecls parseVarDecl() {
			match(TokenType.var);
			Token atila = match(TokenType.ID);
			match(TokenType.COLON);
			Type my = parseType();
			match(TokenType.Semi);
			return new VarDecls(atila.lexeme, my);
			
		}
		
		/*
		 * <Type> --> int										First = int
		 * 			| bool										First = bool
		 * 			| void										First = void
		 */	
		
		public Type parseType() {
			if (check(TokenType.Int)) {
				match(TokenType.Int);
				return Type.Int;
			}
			else if (check(TokenType.Bool)) {
				match(TokenType.Bool);
				return Type.Bool;
			}
			else if (check(TokenType.Void)) {
				match(TokenType.Void);
				return Type.Bool;
			}
			System.err.println("You goofed up... restart this now! -_-");
			System.exit(1);
			return null;
		}
		
		/*
		 * <FunDecls> --> <FunDecl><FunDecls>					First = fun
		 * 				| e										Follow = Stmt
		 */
		
		public List<FunDecls> parseFunDecls(){
			List<FunDecls> list = new ArrayList<>();
			while(check(TokenType.fun)) {
				list.add(parseFunDecl());
			}
			return list;
		}
		
		/*
		 * <FunDecl> --> fun ID ( <ParamList> ) : <Type> ; <Block> ;
		 */
		
		public FunDecls parseFunDecl() {
			match(TokenType.fun);
			Token atila = match(TokenType.ID);
			match(TokenType.LPAR);
			List<Parameter> plist = parseParameters();
			match(TokenType.RPAR);
			match(TokenType.COLON);
			Type my = parseType();
			match(TokenType.Semi);
			Block blocky = parseBlock();
			match(TokenType.Semi);
			return new FunDecls(atila.lexeme, plist , my, blocky);
		}
		
		/*
		 * <Params> --> <Param><ParamsRest>						First = ID
		 */
		
		public List<Parameter> parseParameters(){
			Parameter param = parseParameter();
			return parseParameterRest(param);
		}
		
		/*
		 * <param> --> ID : <Type>								First = ID
		 */
		
		public Parameter parseParameter() {
			Token atila = match(TokenType.ID);
			match(TokenType.COLON);
			Type me = parseType();
			match(TokenType.Semi);
			return new Parameter(atila.lexeme, me );
		}
		
		/*
		 * <ParamsRest> --> ,<params>							First = ,
		 * 					e									Follow = )
		 */
		public List<Parameter> parseParameterRest(Parameter in){
			List<Parameter> parma;
			if (check(TokenType.COMMA)) {
				match(TokenType.COMMA);
				parma = parseParameters();
				parma.add(0, in);
				return parma;
			}
			else {
				parma = new ArrayList<>();
				parma.add(in);
				return parma;
			}
		}
		
		
		public Stmt parseStmt() {
			if (check(TokenType.LET)) {
				match(TokenType.LET);
				Token atila = match(TokenType.ID);
				match(TokenType.EQ);
				Expressions expr = parseExpression();
				return new Assign(atila.lexeme, expr);
			}
			else if (check(TokenType.BEGIN)) {
				match(TokenType.BEGIN);
				List<Stmt> list = parseStmtList();
				match(TokenType.END);
				return new Sequence(list);
			}
			else if (check(TokenType.IF)) {
				match(TokenType.IF);
				Expressions expr = parseExpression();
				match(TokenType.THEN);
				Stmt statement1 = parseStmt();
				return parseStmtRest(expr, statement1);
			}
			else if(check(TokenType.WHILE)) {
				match(TokenType.WHILE);
				Expressions expr = parseExpression();
				match(TokenType.DO);
				Stmt ass = parseStmt();
				return new While(expr, ass);
			}
			else if(check(TokenType.INPUT)) {
				match(TokenType.INPUT);
				String atila = match(TokenType.STRING).lexeme;
				return parseStmtString(atila);
			}
			else if(check(TokenType.NUM, TokenType.ID,TokenType.TRUE,TokenType.FALSE,TokenType.MINUS,TokenType.NOT, TokenType.LPAR)) {
				Expressions expr = parseExpression();
				return new StatementExpression(expr);
			}
			return null;
		}
		
		private List<Stmt> parseStmtList() {
			// TODO Auto-generated method stub
			if (check(TokenType.LET, TokenType.BEGIN, TokenType.IF, TokenType.WHILE, TokenType.INPUT, TokenType.PRINT, TokenType.NUM, TokenType.ID, TokenType.TRUE, TokenType.FALSE, TokenType.MINUS, TokenType.NOT, TokenType.LPAR)) {
				return parseStmts();
			} else {
				return new ArrayList<Stmt>();
			}
		}
		
		
		private List<Stmt> parseStmts(){
			Stmt a = parseStmt();
			return parseStmtRest(a);
		}
		
		
		private List<Stmt> parseStmtRest(Stmt a) {
			// TODO Auto-generated method stub
			List<Stmt> lista;
			if(check(TokenType.Semi)) {
				match(TokenType.Semi);
				lista = parseStmts();
				lista.add(0, a);
				return lista;
			}
			else {
				lista = new ArrayList<>();
				lista.add(a);
				return lista;
			}
		}
		
		
		private Stmt parseStmtString(String atila) {
			// TODO Auto-generated method stub
			if (check(TokenType.COMMA)) {
				match(TokenType.COMMA);
				String n = match(TokenType.ID).lexeme;
				return new InputRest(atila, n);
			}
			else {
				return new Input(atila);
			}
		}
		
		
		
		
		private Expressions parseExpression() {
			Expressions expr = parseSimpleExpr();
			return ParseExpressionsRest(expr);
		}
		
		
		private Expressions ParseExpressionsRest(Expressions expr) {
			// TODO Auto-generated method stub
			if(check(TokenType.EQQ, TokenType.NEQ, TokenType.LTE,TokenType.GTE,TokenType.GT,TokenType.LT)) {
				Allop start = parseRelop();
				Expressions n = parseSimpleExpr();
				return new Binop(expr, n, start);
			}
			else {
				return expr;
			}
		}
		
		private Allop parseRelop() {
			// TODO Auto-generated method stub
			if (check(TokenType.EQQ)) {
				return Allop.EQ;
			}
			else if(check(TokenType.NEQ)) {
				match(TokenType.NEQ);
				return Allop.NE;
			}else if(check(TokenType.LTE)) {
				match(TokenType.LTE);
				return Allop.LE;
			}else if(check(TokenType.GTE)) {
				match(TokenType.GTE);
				return Allop.GT;
			}else if(check(TokenType.LT)) {
				match(TokenType.LT);
				return Allop.LT;
			}else if(check(TokenType.GT)) {
				match(TokenType.GT);
				return Allop.GT;
			}
			else{
				System.exit(1);
			}
			return null;
		}
		
		
		
		private Expressions parseSimpleExpr() {
			// TODO Auto-generated method stub
			Expressions first = parseTerm();
			while(check(TokenType.PLUS,TokenType.MINUS,TokenType.OR)) {
				Allop the = parseAddop();
				Expressions then = parseTerm();
				first = new Binop(first, then, the);
			}
			return first;
		}
		
		
		private Allop parseAddop() {
			// TODO Auto-generated method stub
			if (check(TokenType.PLUS)) {
				match(TokenType.PLUS);
				return Allop.plus;
			}
			else if(check(TokenType.MINUS)){
				match(TokenType.MINUS);
				return Allop.minus;
			}
			else if(check(TokenType.OR)){
				match(TokenType.OR);
				return Allop.or;
			}
			else {
				System.exit(1);
			}
			return null;
		}
		
		
		
		private Expressions parseTerm() {
			// TODO Auto-generated method stub
			Expressions n = parseFactor();
				while (check(TokenType.STAR,TokenType.DIV,TokenType.MOD,TokenType.AND)) {
					Allop m = parseMulOp();
					Expressions t = parseFactor();
					n = new Binop(n, t, m);
				}
				return n;
		}
		
		
		
		private Allop parseMulOp() {
			// TODO Auto-generated method stub
			if (check(TokenType.STAR)) {
				match(TokenType.STAR);
				return Allop.times;
			}
			else if (check(TokenType.DIV)) {
				match(TokenType.DIV);
				return Allop.div;
			}
			else if(check(TokenType.MOD)) {
				match(TokenType.MOD);
				return Allop.mod;
			}
			else {
				System.exit(1);
			}
			return null;
		}
		
		
		
		
		private Expressions parseFactor() {
			// TODO Auto-generated method stub
			if (check(TokenType.NUM)) {
				int value = Integer.parseInt(match(TokenType.NUM).lexeme);
				return new Numbers(value);
			} else if (check(TokenType.ID)) {
				String id = match(TokenType.ID).lexeme;
				return parseFactorRest(id);
			} else if (check(TokenType.TRUE)) {
				match(TokenType.TRUE);
				return new TrueExpression();
			} else if (check(TokenType.FALSE)) {
				match(TokenType.FALSE);
				return new FalseExpressions();
			} else if (check(TokenType.MINUS, TokenType.NOT)) {
				unOp op = parseUnOp();
				Expressions expr = parseFactor();
				return new unop2(op, expr);
			} else if (check(TokenType.LPAR)) {
				match(TokenType.LPAR);
				Expressions expr = parseExpression();
				match(TokenType.RPAR);
				return expr;
			} else {
				System.err.println("Invalid factor: " + current);
				System.exit(1);
			}
			return null; // unreachable
		}
		private unOp parseUnOp() {
			// TODO Auto-generated method stub
			if(check(TokenType.MINUS)) {
				match(TokenType.MINUS);
				return unOp.minusN;
			}
			else if (check(TokenType.NOT)) {
				match(TokenType.NOT);
				return unOp.not;
			}
			else {
				System.exit(1);
			}
			return null;
		}
		private Expressions parseFactorRest(String id) {
			// TODO Auto-generated method stub
			if (check(TokenType.LPAR)) {
				match(TokenType.LPAR);
				List<Expressions> n = parseArgList();
				match (TokenType.RPAR);
				return new CallExpressions(id, n);
			}
			return null;
		}
		private List<Expressions> parseArgList() {
			// TODO Auto-generated method stub
			if (check(TokenType.NUM, TokenType.ID, TokenType.TRUE, TokenType.FALSE, TokenType.MINUS, TokenType.NOT, TokenType.LPAR)) {
				return parseArgs();
			}
			else {
				return new ArrayList<Expressions>();
			}
		}
		private List<Expressions> parseArgs() {
			// TODO Auto-generated method stub
			Expressions n = parseExpression();
			return parseArgsRest(n);
		}
		private List<Expressions> parseArgsRest(Expressions n) {
			// TODO Auto-generated method stub
			if (check(TokenType.COMMA)) {
				match(TokenType.COMMA);
				List<Expressions> fuck = parseArgs();
				fuck.add(0, n);
				return fuck;
			}
			else {
				List<Expressions> ahhhhhhhhhhh = new ArrayList<Expressions>();
				ahhhhhhhhhhh.add(n);
				return ahhhhhhhhhhh;
			}
		}
		/*
		 * <Item> --> <Item> <ItemRest>					
		 */
		public List<Item> parseItems(){
			Item stuff = parseItem();
			return parseItemRest(stuff);
		}
		
		/*
		 * <ItemRest> --> , <Items>
		 * 				| e
		 */
		
		public List<Item> parseItemRest(Item in){
			List<Item> stuff;
			if(check(TokenType.COMMA)) {
				match (TokenType.COMMA);
				stuff = parseItems();
				stuff.add(0, in);
				return stuff;
			}
			else {
				stuff = new ArrayList<>();
				stuff.add(in);
				return stuff;
			}
		}
		
		/*
		 * <Item> --> string
		 * 			| <Expr>
		 */
		
		public Item parseItem() {
			if (check(TokenType.STRING)) {
				String string = match(TokenType.STRING).lexeme;
				return new ItemString(string);
			}
			else if (check(TokenType.NUM,TokenType.ID,TokenType.TRUE,TokenType.FALSE,TokenType.MINUS,TokenType.NOT,TokenType.LPAR)) {
				Expressions expr = parseExpression();
			}
			else {
				System.exit(1);
			}
			return null;
		}
	
		/*
		 * <StmtRest> --> else <Stmt>
		 */
		
		public Stmt parseStmtRest(Expressions in, Stmt in2) {
			if (check(TokenType.ELSE)) {
				match(TokenType.ELSE);
				Stmt in3 = parseStmt();
				return new ifthenelse(in, in2, in3);
			}
			else {
				return new IfThen(in, in2);
			}
		}
}