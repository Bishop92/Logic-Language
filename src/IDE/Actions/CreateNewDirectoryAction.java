package IDE.Actions;

import IDE.Controllers.SolutionExplorerController;
import IDE.Project.Project;
import IDE.Project.ProjectDirectory;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import java.io.File;

//Action for creating a new directory in the project
public class CreateNewDirectoryAction implements Action {

	//The current project opened
	private Project Project_;

	//The solution explorer where the file must be added
	private SolutionExplorerController SolutionExplorerController_;

	/**
	 * @param Project_i                    The current project opened
	 * @param SolutionExplorerController_i The solution explorer where the directory must be add
	 */
	public CreateNewDirectoryAction(Project Project_i, SolutionExplorerController SolutionExplorerController_i) {
		Project_ = Project_i;
		SolutionExplorerController_ = SolutionExplorerController_i;
	}

	/**
	 * Return the name of the action
	 *
	 * @return The name of the action
	 */
	@Override
	public String GetActionName() {
		return "Directory...";
	}

	/**
	 * Execute the action
	 */
	@Override
	public void Execute() {
		JTree SolutionExplorer = SolutionExplorerController_.GetSolutionExplorer();
		//Get the path selected
		TreePath PathSelected = SolutionExplorer.getSelectionPath();

		//Transform the path selected in a sequence of string in order to retrieve the correct folder in the project
		DefaultMutableTreeNode SelectedNode = (DefaultMutableTreeNode) PathSelected.getLastPathComponent();
		if (SelectedNode != null) {

			if (!SelectedNode.getAllowsChildren()) {
				SelectedNode = (DefaultMutableTreeNode) SelectedNode.getParent();
			}

			ProjectDirectory SelectedDirectory = (ProjectDirectory) SelectedNode.getUserObject();

			//Ask to the user the name of the directory to create
			String DirectoryName = JOptionPane.showInputDialog(null, "Insert the name of the directory", "New directory", JOptionPane.PLAIN_MESSAGE);
			while (DirectoryName != null && DirectoryName.isEmpty()) {
				//If no name has been provided, then ask again
				DirectoryName = JOptionPane.showInputDialog(null, "Insert the name of the directory", "New directory", JOptionPane.PLAIN_MESSAGE);
			}

			if (DirectoryName != null) {
				//Get the path where the directory must be add
				String NewProjectDirectoryPath = Project_.GetNewDirectoryPath(SelectedDirectory, DirectoryName);
				if (NewProjectDirectoryPath != null) {
					//Create the directory in the file system
					File ProjectDirectory = new File(NewProjectDirectoryPath);
					if (ProjectDirectory.mkdir()) {
						//If the directory is correctly created, then add the directory to the project
						ProjectDirectory NewProjectDirectory = new ProjectDirectory(DirectoryName, SelectedDirectory);
						int DirectoryIndex = SelectedDirectory.AddProjectDirectory(NewProjectDirectory);
						//If the directory has been correctly added to the project then add also to the solution explorer
						DefaultMutableTreeNode NewDirectoryNode = new DefaultMutableTreeNode(NewProjectDirectory);
						SolutionExplorerController_.AddNode(SelectedNode, NewDirectoryNode, DirectoryIndex);
					}
				}
			}
		}
	}
}