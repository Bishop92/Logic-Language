package IDE.Controllers;

import IDE.Log.ErrorLog;
import IDE.Log.Log;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class OutputController {

	private JList OutputList_;

	private DefaultListModel ListModel_;

	private OpenFilesController OpenFilesController_;

	public OutputController() {
		Log.SetOutputController(this);
	}

	public void SetOpenFilesController(OpenFilesController OpenFilesController_i) {
		OpenFilesController_ = OpenFilesController_i;
	}

	public JList CreateOutputList() {
		ListModel_ = new DefaultListModel();
		OutputList_ = new JList(ListModel_);

		OutputList_.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);

				if(e.getClickCount() == 2) {
					ErrorLog Error = (ErrorLog) OutputList_.getSelectedValue();
					OpenFilesController_.SetFocus(Error.GetPosition());
				}
			}
		});

		return OutputList_;
	}

	public void WriteError(ErrorLog Error_i) {
		ListModel_.addElement(Error_i);
	}
}
