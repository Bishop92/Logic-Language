package IDE.Log;

import Lexer.Token;

public class ErrorLog {

	private ErrorType Type_;

	private int Line_;

	private int Char_;

	private int Position_;

	private String Description_;

	public ErrorLog(ErrorType Type_i, Token Token_i, String Description_i) {
		Type_ = Type_i;
		Line_ = Token_i.GetLinePosition();
		Char_ = Token_i.GetCharPosition();
		Position_ = Token_i.GetPosition();
		Description_ = Description_i;
	}

	public String toString() {
		return GetErrorType() + " (" + (Line_ + 1) + "," + (Char_ + 1) + "): " + Description_;
	}

	private String GetErrorType() {
		switch (Type_) {
			case LEXICAL: return "Lexical error";
			case SYNTAX: return "Sintax error";
		}

		return "";
	}

	public int GetChar() {
		return Char_;
	}

	public int GetLine() {
		return Line_;
	}

	public int GetPosition() {
		return Position_;
	}
}
