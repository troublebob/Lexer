import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Vector;

public class Lexer {
	final int NOTINITIALISED =-1;
	final int IDENTIFIER=1;
	final int SINGLECHARTOKEN=3;
	final int WHITESPACE=4;
	final int INTEGER=5;
	final int STRING=6;
	final int OPENSTRING=7;
	final int ERROR=8;

	Vector<Token> tokens = new Vector<Token>();
	private int state=NOTINITIALISED;
	private int characterPointer = 0;
	private int finito = -1;
	private int intBuffer = 0;
	private boolean finitoFound = false;
	private boolean openQuote = false;
	private SymbolTable map;
	private Vector<Character> buffer = new Vector<Character>();

	public Lexer() {
		map = new SymbolTable();
		state = NOTINITIALISED;

		map.Method1('F');
		map.Method1('i');
		map.Method1('n');
		map.Method1('i');
		map.Method1('t');
		map.Method1('o');
		finito = map.Method2();		
	}

	public void run(char[] input) {

		while (characterPointer >= 0 && characterPointer < input.length) {
			char currentCharacter = input[characterPointer];
			evaluateCharacter(currentCharacter);			
			if(finitoFound==true){
				break;
			}
			characterPointer++;
		}
		if(state==STRING||state==OPENSTRING){
			state=ERROR;
			System.out.println("String Not Closed!");
		}
		
	}
	void chooseState(char c){
		if(Character.isAlphabetic(c)){
			state=IDENTIFIER;
		} else if (c=='"'){
			state=STRING;
		} else if (Character.isDigit(c)){
			state=INTEGER;
		} else if (c=='('||c=='['||c=='{'||c==')'||c==']'||c=='}'){
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
		if(state==NOTINITIALISED){
			chooseState(c);
		}
		switch (state) {
		case IDENTIFIER: {
			if((c>='A' && c<='Z')||(c>='a'&&c<='z')){
				//System.out.println("AZaz:"+c);
				map.Method1(c);
			} else if (c>='0' && c<='9'){
				map.Method1(c);
			} else {
				//System.out.println("Else:"+c);
				state=NOTINITIALISED;
				pushBackCharacter();
				hash=map.Method2();
				//System.out.println(hash);
				if(hash==finito){
					finitoFound=true;
				} else {
					tokens.add(new Token("ID", hash));
				}
			}
			//System.out.println("<"+c+"><"+state+">ID");
			break;
		}
		case INTEGER:{
			if(c>='0' && c<='9'){
				if(intBuffer*10>11335577){
					state=ERROR;
					break;
				}
				intBuffer*=10;
				intBuffer+=Character.getNumericValue(c);
			} else {
				tokens.add(new Token("INT", intBuffer));
				state=NOTINITIALISED;
				pushBackCharacter();
				intBuffer=0;
			}
			//System.out.println("<"+c+"><"+state+">INT");
			break;
		}
		case WHITESPACE:{
			if(Character.isWhitespace(c)){

			} else {
				state=NOTINITIALISED;
				pushBackCharacter();
			}		
			//System.out.println("<"+c+"><"+state+">WHITESPACE");
			break;
		}
		case SINGLECHARTOKEN:{
			if(c=='('||c=='['||c=='{'){
				tokens.add(new Token("LPAR", 0));
			} else if (c==')'||c==']'||c=='}'){
				tokens.add(new Token("RPAR", 0));
			} else if (c==','){
				tokens.add(new Token("COMMA", 0));
			} else{
				state=NOTINITIALISED;
				pushBackCharacter();
			}
			//System.out.println("<"+c+"><"+state+">SINGLECHAR");
			break;
		}
		case STRING:{
			if(c=='\"' && openQuote==false){
				openQuote = true;
			} else if (c=='\"' && openQuote == true){
				openQuote=false;
				tokens.add(new StringToken("STRING",buffer));
				state=NOTINITIALISED;
			} else if (c=='\\'){
				state=OPENSTRING;
			} else{
				buffer.add(c);
			}
			
			//System.out.println("<"+c+"><"+state+">STRING "+openQuote+ " "+buffer.toString());
			break;
		}
		case OPENSTRING:{
			//System.out.println("<"+c+"><"+state+">OPENSTRING "+openQuote+ " "+buffer.toString());
			buffer.add(c);
			state=STRING;
			break;
		}

		case ERROR:{
			System.out.println("<"+c+"><"+state+">stateError! Unable to complete");
		}
		}

	}

	public static void main(String[] args) throws FileNotFoundException {

		String input="";
		File inputFile=new File("input.txt");
		Scanner inputScanner = new Scanner(inputFile);
		input=inputScanner.nextLine();
		Lexer lexer = new Lexer();
		System.out.println(input);
		lexer.run(input.toCharArray());
		for(int i=0;i<lexer.tokens.size();i++){
			System.out.println(lexer.tokens.elementAt(i).toString());
		}
		if(inputScanner!=null){
			inputScanner.close();
			}
	}	
}
	