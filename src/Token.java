import java.util.Vector;

class Token {
	int value;
	String type;
	
	public Token(String t, int v) {
		type = t;
		value = v;
	}
	
	public String toString() {
		return "<" + type + " : " + value + ">";
	}
}

class StringToken extends Token {
	Vector<Character> value = new Vector<Character>();

	public StringToken(String t, Vector<Character> v) {
		super(t, 0);
		type = t;
		value = v;
	}
	
	public String toString() {
		return "<" + type + " : " + value.toString() + ">";
	}
}
