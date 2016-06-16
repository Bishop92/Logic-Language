package IDE.Controllers;

import javax.swing.*;

public class OpenFilesController {

	//The widget with the open files
	private JTabbedPane OpenFiles_;

	public OpenFilesController() {
	}

	/**
	 * Create the widget that show the open files
	 *
	 * @return The widget that show the open files
	 */
	public JTabbedPane CreateOpenFiles() {
		OpenFiles_ = new JTabbedPane();
		return OpenFiles_;
	}

	/**
	 *
	 * @return
	 */
	public JTabbedPane GetOpenFiles() {
		return OpenFiles_;
	}

	public void SetFocus(int Position_i) {
		JTextPane TextEditor = (JTextPane) OpenFiles_.getSelectedComponent();
		TextEditor.requestFocus();
		TextEditor.setCaretPosition(Position_i);
	}
}
