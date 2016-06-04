package IDE.CustomComponents;

import IDE.Actions.AddFileAction;
import IDE.Actions.CreateNewDirectoryAction;
import IDE.Actions.CreateNewFileAction;
import IDE.Actions.ShowInExplorerAction;
import IDE.Controllers.SolutionExplorerController;
import IDE.Project.Project;
import IDE.Project.ProjectFile;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

public class SolutionExplorerContextualMenu extends ContextualMenu {

	//The action to create a new file and at it to the current project
	private CreateNewFileAction CreateNewFileAction_;

	//The action to add a file to the current project
	private AddFileAction AddFileAction_;

	//The action to create a new directory and at it to the current project
	private CreateNewDirectoryAction CreateNewDirectoryAction_;

	//The action to show the selected file in the explorer
	private ShowInExplorerAction ShowInExplorerAction_;

	/**
	 * @param Project_i                    The current opened project
	 * @param SolutionExplorerController_i The controller of the solution explorer
	 * @param MouseEvent_i                 The mouse event that trigger the contextual menu
	 */
	public SolutionExplorerContextualMenu(Project Project_i, SolutionExplorerController SolutionExplorerController_i, MouseEvent MouseEvent_i) {
		super(MouseEvent_i);

		boolean IsAFile = false;

		TreePath SelectedPath = SolutionExplorerController_i.GetSolutionExplorer().getSelectionPath();

		if(SelectedPath != null) {
			DefaultMutableTreeNode SelectedNode = (DefaultMutableTreeNode)SelectedPath.getLastPathComponent();
			IsAFile = SelectedNode != null && SelectedNode.getUserObject() instanceof ProjectFile;
		}

		//Create the item for creating a new file
		CreateNewFileAction_ = new CreateNewFileAction(Project_i, SolutionExplorerController_i);
		JMenuItem CreateNewFileActionItem = new JMenuItem(CreateNewFileAction_.GetActionName());
		CreateNewFileActionItem.addActionListener(e -> CreateNewFileAction_.Execute());

		//Create the item for adding an existent file not in the project
		AddFileAction_ = new AddFileAction(Project_i, SolutionExplorerController_i);
		JMenuItem AddFileActionItem = new JMenuItem(AddFileAction_.GetActionName());
		AddFileActionItem.addActionListener(e -> AddFileAction_.Execute());

		//Create the item for creating a new directory
		CreateNewDirectoryAction_ = new CreateNewDirectoryAction(Project_i, SolutionExplorerController_i);
		JMenuItem CreateNewDirectoryActionItem = new JMenuItem(CreateNewDirectoryAction_.GetActionName());
		CreateNewDirectoryActionItem.addActionListener(e -> CreateNewDirectoryAction_.Execute());

		//Create the item for creating a new directory
		ShowInExplorerAction_ = new ShowInExplorerAction(SolutionExplorerController_i);
		JMenuItem ShowInExplorerActionItem = new JMenuItem(ShowInExplorerAction_.GetActionName());
		ShowInExplorerActionItem.addActionListener(e -> ShowInExplorerAction_.Execute());
		ShowInExplorerActionItem.setEnabled(IsAFile);

		JMenu NewElementMenu = new JMenu("New");
		NewElementMenu.add(CreateNewFileActionItem);
		NewElementMenu.add(AddFileActionItem);
		NewElementMenu.add(CreateNewDirectoryActionItem);

		ContextualMenu_.add(NewElementMenu);
		ContextualMenu_.addSeparator();
		ContextualMenu_.add(ShowInExplorerActionItem);

		//Visualize the contextual menu
		ContextualMenu_.show(MouseEvent_i.getComponent(), MouseEvent_i.getX(), MouseEvent_i.getY());
	}
}