package IDE.Actions;

import IDE.Controllers.SolutionExplorerController;
import IDE.Project.Project;
import IDE.Project.ProjectDirectory;
import IDE.Project.ProjectFile;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import java.io.File;
import java.io.IOException;

public class CreateNewFileAction implements Action {

	//The current project opened
	private Project Project_;

	//The solution explorer where the file must be added
	private SolutionExplorerController SolutionExplorerController_;

	/**
	 * @param Project_i                    The current project opened
	 * @param SolutionExplorerController_i The solution explorer where the file must be add
	 */
	public CreateNewFileAction(Project Project_i, SolutionExplorerController SolutionExplorerController_i) {
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
		return "File...";
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

			//Ask to the user the name of the file to create
			String FileName = JOptionPane.showInputDialog(null, "Insert the name of the file", "New file", JOptionPane.PLAIN_MESSAGE);
			while (FileName != null && FileName.isEmpty()) {
				//If no name has been provided, then ask again
				FileName = JOptionPane.showInputDialog(null, "Insert the name of the file", "New file", JOptionPane.PLAIN_MESSAGE);
			}

			if(FileName != null) {
				//Get the path where the file must be add
				String NewProjectFilePath = Project_.GetNewFilePath(SelectedDirectory, FileName);
				if (NewProjectFilePath != null) {
					try {
						//Create the file in the file system
						File ProjectDirectory = new File(NewProjectFilePath);
						if (ProjectDirectory.createNewFile()) {
							//If the file is correctly created, then add the file to the project
							ProjectFile NewProjectFile = new ProjectFile(NewProjectFilePath, FileName);
							int FileIndex = SelectedDirectory.AddProjectFile(NewProjectFile);
							//If the file has been correctly added to the project then add also to the solution explorer
							DefaultMutableTreeNode NewFileNode = new DefaultMutableTreeNode(NewProjectFile, false);
							SolutionExplorerController_.AddNode(SelectedNode, NewFileNode, FileIndex);
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
}
