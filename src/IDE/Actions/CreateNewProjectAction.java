package IDE.Actions;

import IDE.Project.Project;

import javax.swing.*;
import java.io.File;

//Action for creating a new project
public class CreateNewProjectAction implements Action {

	//The current project opened
	private Project Project_;

	//The parent of the file chooser in order to be correctly opened
	private JComponent Parent_;

	/**
	 * @param Project_i The current project opened
	 * @param Parent_i  The current component used to parenting the file chooser
	 */
	public CreateNewProjectAction(Project Project_i, JComponent Parent_i) {
		Project_ = Project_i;
		Parent_ = Parent_i;
	}

	@Override
	public String GetActionName() {
		return "Project...";
	}

	@Override
	public void Execute() {
		//Create the file chooser in order to know the location of the new project
		JFileChooser ProjectLocationChooser = new JFileChooser();

		//Set the file selection mode for selecting only directory
		ProjectLocationChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		if (ProjectLocationChooser.showSaveDialog(Parent_) == JFileChooser.APPROVE_OPTION) {
			//Get the selected directory
			File SelectedDirectory = ProjectLocationChooser.getSelectedFile();

			//Ask the name of the project
			String ProjectName = JOptionPane.showInputDialog(null, "Insert the name of the project", "New project", JOptionPane.PLAIN_MESSAGE);
			while (ProjectName != null && ProjectName.isEmpty()) {
				//Continue to ask the name of the project until it is not empty
				ProjectName = JOptionPane.showInputDialog(null, "Insert the name of the project", "New project", JOptionPane.PLAIN_MESSAGE);
			}

			if (ProjectName != null) {
				//Create the new project directory using the name of the project as the name of the directory
				File ProjectDirectory = new File(SelectedDirectory.getPath() + "/" + ProjectName);
				if (ProjectDirectory.mkdir()) {
					//If the directory is correctly created, then create the base structure of the project
					Project_.CreateProject(ProjectName, ProjectDirectory.getPath());
				}
			}
		}
	}
}
