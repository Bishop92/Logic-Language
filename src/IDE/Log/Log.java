package IDE.Log;

import IDE.Controllers.OutputController;

import java.util.Vector;

public class Log {

	private static Log Instance_ = new Log();

	private Vector<String> Log_;

	private OutputController OutputController_;

	public static Log GetInstance() {
		return Instance_;
	}

	private Log() {
		Log_ = new Vector<>();
	}

	public static void WriteError(ErrorLog Error_i) {
		GetInstance().Log_.addElement(Error_i.toString());
		GetInstance().OutputController_.WriteError(Error_i);
	}

	public static void SetOutputController(OutputController OutputController_i) {
		GetInstance().OutputController_ = OutputController_i;
	}

}
