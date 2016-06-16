package IDE.Actions;

import IDE.Controllers.OpenFilesController;
import IDE.Controllers.SolutionExplorerController;
import IDE.PrettyPrinter.PrettyPrinter;
import IDE.Project.Project;
import IDE.Project.ProjectFile;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

//Action for open the file in the editor
public class OpenFileInEditorAction implements Action {

	//The current project opened
	private Project Project_;

	//The controller of the solution explorer of the project
	private SolutionExplorerController SolutionExplorerController_;

	//The widget with the list of the opened files
	private OpenFilesController OpenFilesController_;

	/**
	 * @param Project_i                    The current project opened
	 * @param SolutionExplorerController_i The controller of the solution explorer of the project
	 * @param OpenFilesController_i        The controller with the list of the opened files
	 */
	public OpenFileInEditorAction(Project Project_i, SolutionExplorerController SolutionExplorerController_i, OpenFilesController OpenFilesController_i) {
		Project_ = Project_i;
		SolutionExplorerController_ = SolutionExplorerController_i;
		OpenFilesController_ = OpenFilesController_i;
	}

	/**
	 * Return the name of the action
	 *
	 * @return The name of the action
	 */
	@Override
	public String GetActionName() {
		return "Open in Editor";
	}

	/**
	 * Execute the action
	 */
	@Override
	public void Execute() {
		//Get the path selected
		TreePath PathSelected = SolutionExplorerController_.GetSolutionExplorer().getSelectionPath();

		//Transform the path selected in a sequence of string in order to retrieve the correct folder in the project
		DefaultMutableTreeNode SelectedNode = (DefaultMutableTreeNode) PathSelected.getLastPathComponent();
		if (SelectedNode != null && !SelectedNode.getAllowsChildren()) {

			JTabbedPane OpenFiles = OpenFilesController_.GetOpenFiles();

			//Get the file selected in the project
			ProjectFile SelectedProjectFile = (ProjectFile) SelectedNode.getUserObject();

			if (SelectedProjectFile != null) {
				//If the file has been found then open it
				if (!SelectedProjectFile.IsOpenInEditor()) {
					//The file is not already present in the list of the opened files, so it must be add
					SelectedProjectFile.SetOpenInEditor(true);

					//Create the text editor and bind the pretty printer
					JTextPane TextEditor = new JTextPane();
					new PrettyPrinter(TextEditor);

					//Open the file and set the text of the file in the editor
					File SelectedFile = new File(SelectedProjectFile.GetPath());
					try {
						TextEditor.setText(new String(Files.readAllBytes(SelectedFile.toPath())));
					} catch (IOException e) {
						e.printStackTrace();
					}

					//Add the tab and set the title with the name of the files
					OpenFiles.addTab(SelectedProjectFile.GetName(), TextEditor);

					//Set as the current selected component
					OpenFiles.setSelectedComponent(TextEditor);
				} else {
					//The file is already open so simply visualize the related tab
					for (int TabIndex = 0; TabIndex < OpenFiles.getTabCount(); ++TabIndex) {
						//Find the tab with the same name of the file and visualize it
						if (OpenFiles.getTitleAt(TabIndex).equals(SelectedProjectFile.GetName())) {
							OpenFiles.setSelectedIndex(TabIndex);
							return;
						}
					}
				}
			}
		}
	}
}