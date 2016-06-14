package Utils;

import Lexer.Tag;
import Lexer.Token;

public class ErrorUtils {

	public static String GetSymbolFromTag(Tag Tag_i) {
		switch (Tag_i) {
			case ADD: return "+";
			case AS: return "as";
			case ASSIGN: return "=";
			case BOOLTYPE: return "bool";
			case CALLBACK: return "callback";
			case CBRACE: return "}";
			case COLON: return ":";
			case COMMA: return ",";
			case CPAR: return ")";
			case DECREMENT: return "--";
			case DEF: return "def";
			case DIVIDE: return "/";
			case EMPTY: return "";
			case EOF: return "end of file";
			case EQ: return "==";
			case FLOAT: return "float";
			case FLOATTYPE: return "float";
			case FUNCTION: return "function";
			case GET: return ">=";
			case GT: return ">";
			case ID: return "identifier";
			case IIF: return "<=>";
			case IMPLY: return "=>";
			case INCREMENT: return "++";
			case INTEGER: return "integer";
			case INTTYPE: return "int";
			case LET: return "<=";
			case LT: return "<";
			case MINUS: return "-";
			case MULTIPLY: return "*";
			case OBRACE: return "{";
			case OPAR: return "(";
			case SEMICOLON: return ";";
			case STRINGTYPE: return "string";
		}

		return "";
	}

	public static String GetErrorLocation(Token Token_i) {
		return "line: " + Token_i.GetLinePosition() + " char: " + Token_i.GetCharPosition();
	}
}
