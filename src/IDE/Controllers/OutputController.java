package IDE.Controllers;

import IDE.Log.Log;

import javax.swing.*;

public class OutputController {

	private JList OutputList_;

	private DefaultListModel ListModel_;

	public OutputController() {
		Log.SetOutputController(this);
	}

	public JList CreateOutputList() {
		ListModel_ = new DefaultListModel();
		OutputList_ = new JList(ListModel_);
		return OutputList_;
	}

	public void WriteOutput(String Output_i) {
		ListModel_.addElement(Output_i);
	}
}
