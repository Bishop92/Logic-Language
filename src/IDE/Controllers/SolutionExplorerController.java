package IDE.Controllers;

import IDE.Actions.OpenFileInEditorAction;
import IDE.CustomComponents.SolutionExplorerContextualMenu;
import IDE.Project.Project;
import IDE.Project.ProjectDirectory;
import IDE.Project.ProjectFile;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

public class SolutionExplorerController {

	//The project to visualize in the solution explorer
	private Project Project_;

	//The widget that represents the solution explorer
	private JTree SolutionExplorer_;

	//The model of the solution explorer
	private DefaultTreeModel SolutionExplorerModel_;

	//The controller that keeps the open files
	private OpenFilesController OpenFilesController_;

	/**
	 * @param Project_i The project used for filling the solution explorer
	 */
	public SolutionExplorerController(Project Project_i) {
		Project_ = Project_i;
	}

	/**
	 * Create the widget for the solution explorer
	 *
	 * @return The widget for solution explorer
	 */
	public JTree CreateSolutionExplorer() {
		//Get the root of the project
		ProjectDirectory Root = Project_.GetRoot();
		//Create the root of the tree using the project root name
		DefaultMutableTreeNode CurrentNode = new DefaultMutableTreeNode(Root);

		SolutionExplorerModel_ = new DefaultTreeModel(CurrentNode);

		//Fill all the tree with the structure of the project
		FillSolutionExplorerElement(Root, CurrentNode);

		//Create the solution explorer
		SolutionExplorer_ = new JTree(SolutionExplorerModel_);

		//Create the listeners for the mouse events
		CreateMouseListener();

		SolutionExplorer_.setLargeModel(true);

		return SolutionExplorer_;
	}

	/**
	 * Return the solution explorer widget
	 * @return The solution explorer widget
	 */
	public JTree GetSolutionExplorer() {
		return SolutionExplorer_;
	}

	/**
	 * Set the controller for the open files
	 *
	 * @param OpenFilesController_i The controller for the open files
	 */
	public void SetOpenFilesController(OpenFilesController OpenFilesController_i) {
		OpenFilesController_ = OpenFilesController_i;
	}

	/**
	 *
	 * @param ParentNode_i
	 * @param NewNode_i
	 * @param NodePosition_i
	 */
	public void AddNode(DefaultMutableTreeNode ParentNode_i, DefaultMutableTreeNode NewNode_i, int NodePosition_i) {
		//Insert the node in the model coherently to the position
		SolutionExplorerModel_.insertNodeInto(NewNode_i, ParentNode_i, NodePosition_i);

		//Scroll the path in the solution explorer to the new node
		SolutionExplorer_.scrollPathToVisible(new TreePath(NewNode_i.getPath()));
	}

	/**
	 * Create each element of the solution explorer
	 *
	 * @param ProjectDirectory_i The current directory in the project to add to the solution explorer
	 * @param Parent_i           The element that represents the current directory
	 */
	private void FillSolutionExplorerElement(ProjectDirectory ProjectDirectory_i, DefaultMutableTreeNode Parent_i) {
		//At first, add all the directories to the solution explorer
		Vector<ProjectDirectory> Directories = ProjectDirectory_i.GetProjectDirectories();
		for (ProjectDirectory CurrentDirectory : Directories) {
			//Create the node of the current directory
			DefaultMutableTreeNode CurrentNode = new DefaultMutableTreeNode(CurrentDirectory);

			//Add it to the current parent
			Parent_i.add(CurrentNode);

			//Fill the children of the current directory
			FillSolutionExplorerElement(CurrentDirectory, CurrentNode);
		}

		//Add all the files in the current directory to the solution explorer
		Vector<ProjectFile> Files = ProjectDirectory_i.GetProjectFiles();
		for (ProjectFile CurrentFile : Files) {
			//Create the node of the current file but a file node could not accept children
			DefaultMutableTreeNode CurrentNode = new DefaultMutableTreeNode(CurrentFile, false);
			Parent_i.add(CurrentNode);
		}
	}

	/**
	 * Create the listener for the solution explorer
	 */
	private void CreateMouseListener() {

		SolutionExplorerController OuterThis = this;

		SolutionExplorer_.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);

				if (e.getButton() == MouseEvent.BUTTON3) {

					//Set the selected path on the path in relation to the position of the click
					SolutionExplorer_.setSelectionPath(SolutionExplorer_.getPathForLocation(e.getX(), e.getY()));

					//The right click button has been pressed, so open the contextual menu
					new SolutionExplorerContextualMenu(Project_, OuterThis, e);
				} else if (e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
					//The double click has been done, so the selected file must be shown in the editor
					OpenFileInEditorAction OpenFileInEditorAction = new OpenFileInEditorAction(Project_, OuterThis, OpenFilesController_);
					OpenFileInEditorAction.Execute();
				}
			}
		});
	}
}
