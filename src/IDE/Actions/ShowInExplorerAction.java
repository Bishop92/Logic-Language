package IDE.Actions;

import IDE.Controllers.SolutionExplorerController;
import IDE.Project.ProjectFile;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import java.io.IOException;

//Action for show the file in the explorer
public class ShowInExplorerAction implements Action {

    //The controller of the solution explorer
    private SolutionExplorerController SolutionExplorerController_;

    /**
     * @param SolutionExplorerController_i The controller of the solution explorer
     */
    public ShowInExplorerAction(SolutionExplorerController SolutionExplorerController_i) {
        SolutionExplorerController_ = SolutionExplorerController_i;
    }

    /**
     * Return the name of the action
     *
     * @return The name of the action
     */
    @Override
    public String GetActionName() {
        return "Show in Explorer";
    }

    /**
     * Execute the action
     */
    @Override
    public void Execute() {

        //Retrieve the selected path in the solution explorer
        TreePath SelectedPath = SolutionExplorerController_.GetSolutionExplorer().getSelectionPath();

        DefaultMutableTreeNode SelectedNode = (DefaultMutableTreeNode) SelectedPath.getLastPathComponent();
        if (SelectedNode != null && !SelectedNode.getAllowsChildren()) {

            //The element selected is a file
            ProjectFile SelectedProjectFile = (ProjectFile) SelectedNode.getUserObject();

            try {
                //Open the file in the solution explorer
                new ProcessBuilder("explorer.exe", "/select," + SelectedProjectFile.GetPath()).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}