package IDE.Actions;

import IDE.Controllers.SolutionExplorerController;
import IDE.PrettyPrinter.PrettyPrinter;
import IDE.Project.Project;
import IDE.Project.ProjectDirectory;
import IDE.Project.ProjectFile;

import javax.swing.*;
import javax.swing.text.Highlighter;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Vector;
import java.util.stream.Stream;

public class OpenFileInEditorAction implements Action {

	//The current project opened
	private Project Project_;

	//The controller of the solution explorer of the project
	private SolutionExplorerController SolutionExplorerController_;

	//The widget with the list of the opened files
	private JTabbedPane OpenFiles_;

	/**
	 * @param Project_i                    The current project opened
	 * @param SolutionExplorerController_i The controller of the solution explorer of the project
	 * @param OpenFiles_i                  The widget with the list of the opened files
	 */
	public OpenFileInEditorAction(Project Project_i, SolutionExplorerController SolutionExplorerController_i, JTabbedPane OpenFiles_i) {
		Project_ = Project_i;
		SolutionExplorerController_ = SolutionExplorerController_i;
		OpenFiles_ = OpenFiles_i;
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

			//Get the file selected in the project
			ProjectFile SelectedProjectFile = (ProjectFile) SelectedNode.getUserObject();

			if (SelectedProjectFile != null) {
				//If the file has been found then open it
				if (!SelectedProjectFile.IsOpenInEditor()) {
					//The file is not already present in the list of the opened files, so it must be add
					SelectedProjectFile.SetOpenInEditor(true);

					//Create the tab that will contain the text editor
					JPanel TextEditorPanel = new JPanel();

					//Add the tab and set the title with the name of the files
					OpenFiles_.addTab(SelectedProjectFile.GetName(), TextEditorPanel);
					OpenFiles_.setSelectedComponent(TextEditorPanel);

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

					//Add the text editor as child in the tab
					TextEditorPanel.add(TextEditor);
				} else {
					//The file is already open so simply visualize the related tab
					for (int TabIndex = 0; TabIndex < OpenFiles_.getTabCount(); ++TabIndex) {
						//Find the tab with the same name of the file and visualize it
						if (OpenFiles_.getTitleAt(TabIndex).equals(SelectedProjectFile.GetName())) {
							OpenFiles_.setSelectedIndex(TabIndex);
							return;
						}
					}
				}
			}
		}
	}
}