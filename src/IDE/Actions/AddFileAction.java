package IDE.Actions;

import IDE.Controllers.SolutionExplorerController;
import IDE.Project.ProjectDirectory;
import IDE.Project.ProjectFile;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import java.io.File;

//An action to add an existing file to the project
public class AddFileAction implements Action {

	//The solution explorer where the file must be added
    private SolutionExplorerController SolutionExplorerController_;

    /**
     * @param SolutionExplorerController_i The solution explorer where the file must be added
     */
    public AddFileAction(SolutionExplorerController SolutionExplorerController_i) {
		SolutionExplorerController_ = SolutionExplorerController_i;
    }

    @Override
    public String GetActionName() {
        return "Add Existing File...";
    }

    @Override
    public void Execute() {
        JTree SolutionExplorer = SolutionExplorerController_.GetSolutionExplorer();
        //Get the path selected
        TreePath PathSelected = SolutionExplorer.getSelectionPath();

        //Transform the path selected in a sequence of string in order to retrieve the correct folder in the project
        DefaultMutableTreeNode SelectedNode = (DefaultMutableTreeNode) PathSelected.getLastPathComponent();
        if (!SelectedNode.getAllowsChildren()) {
            SelectedNode = (DefaultMutableTreeNode) SelectedNode.getParent();
        }

        ProjectDirectory SelectedDirectory = (ProjectDirectory) SelectedNode.getUserObject();
        if (SelectedDirectory != null) {

            //Create the file chooser to select the location of the existing file
            JFileChooser fileChooser_ = new JFileChooser();

            if (fileChooser_.showOpenDialog(SolutionExplorer) == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser_.getSelectedFile();
                //Get the file name and its path
                String FileName = file.getName();
                FileName = FileName.substring(0, FileName.lastIndexOf('.'));
                String FilePath = file.getPath();

                if (SelectedDirectory.GetProjectFile(FileName) == null) {
                    ProjectFile NewProjectFile = new ProjectFile(FilePath, FileName);
                    int FileIndex = SelectedDirectory.AddProjectFile(NewProjectFile);
                    //If the file has been correctly added to the project then add also to the solution explorer
                    DefaultMutableTreeNode NewFileNode = new DefaultMutableTreeNode(FileName, false);

                    //Add the node in the widget
                    SolutionExplorerController_.AddNode(SelectedNode, NewFileNode, FileIndex);
                }
            }
        }
    }
}
