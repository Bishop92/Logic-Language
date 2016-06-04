package IDE.CustomComponents;

import javax.swing.*;
import java.awt.event.MouseEvent;

public class ContextualMenu {

	//The contextual menu widget
	protected JPopupMenu ContextualMenu_;

	/**
	 * @param MouseEvent_i The mouse event that trigger the contextual menu
	 */
	public ContextualMenu(MouseEvent MouseEvent_i) {
		ContextualMenu_ = new JPopupMenu();
	}
}