import java.util.Vector;

public class Lexer {
	private static Vector<Token> tokens = new Vector<Token>();
	private int state;
	private int characterPointer = 0;
	private int finito = -1;
	private SymbolTable map;
	private int buffer = 0;

	final int IDENTIFIER=1;
	final int IDENTIFIERENDINGWITHNUMBER=2;
	final int SINGLECHARTOKEN=3;
	final int WHITESPACE=4;
	final int INTEGER=5;
	final int STRING=6;
	final int ERROR=7;

	public Lexer() {
		map = new SymbolTable();
		state = 0;

		map.Method1('F');
		map.Method1('i');
		map.Method1('n');
		map.Method1('i');
		map.Method1('t');
		map.Method1('o');
		finito = map.Method2();		
	}

	public void run(char[] input) {
		//Set Initial state
		chooseState(input[0]);

		while (characterPointer >= 0 && characterPointer < input.length) {
			char currentCharacter = input[characterPointer];
			System.out.println("<"+currentCharacter+"> Going in:State: "+state);
			evaluateCharacter(currentCharacter);			
			System.out.println("<"+currentCharacter+"> Coming out:State: "+state);
			characterPointer++;
			System.out.println(characterPointer);
		}		
	}
	void chooseState(char c){
		if(((c >= 'A') && (c <='Z')) || ((c >= 'a') && (c <='z'))){
			state=IDENTIFIER;
		} else if (c=='"'){
			state=STRING;
		} else if ((c>= '0') && (c<= '9')){
			state=INTEGER;
		} else if ((c=='(')||(c==')')||(c==',')){
			state=SINGLECHARTOKEN;
		} else if (Character.isWhitespace(c)){
			state=WHITESPACE;
		} else {
			state=ERROR;
		}
	}

	void pushBackCharacter() {
		characterPointer--;
	}

	void evaluateCharacter(char c){
		int hash=-1;
		switch (state) {
		case IDENTIFIER: {
			if((c>='A' && c<='Z')||(c>='a'&&c<='z')){
				System.out.println("AZaz:"+c);
				map.Method1(c);
			} else if (c>='0' && c<='9'){
				state=IDENTIFIERENDINGWITHNUMBER;
				map.Method1(c);
			} else {
				System.out.println("Else:"+c);
				chooseState(c);
				pushBackCharacter();
				hash=map.Method2();
				System.out.println(hash);
				if(hash==finito){
					System.exit(0);
				} else {
					tokens.add(new Token("ID", hash));
				}
				
			}
			break;

		}
		case IDENTIFIERENDINGWITHNUMBER:{
			if (c>='0' && c<='9'){
				map.Method1(c);
			} else {
				System.out.println("State 2 Else:"+c);
				chooseState(c);
				pushBackCharacter();
				hash=map.Method2();
				System.out.println(hash);
				tokens.add(new Token("ID", hash));
			}
			break;
		}
//		case WHITESPACE:{
//			if(Character.isWhitespace(c)){
//				
//			} else {
//				chooseState(c);
//				pushBackCharacter();
//			}
//		}
//		case SINGLECHARTOKEN:{
//			if(c=='('){
//				tokens.add(new Token("LPAR", 0));
//			} else if (c==')'){
//				tokens.add(new Token("RPAR", 0));
//			} else if (c==','){
//				tokens.add(new Token("COMMA", 0));
//			} else{
//				chooseState(c);
//				pushBackCharacter();
//			}
//		}
//		case STRING:{
//			
//		}
//		case ERROR:{
//			System.out.println("Error! Unable to complete");
//		}
		}



		if (hash == finito) {
			// exit gracefully on finito
			System.exit(0);
		}

		System.out.println(c);
	}

	public static void main(String[] args) {

		String input = "tom17 ";
		System.out.println(input);
		Lexer lexer = new Lexer();
		lexer.run(input.toCharArray());
	}	
}
