package IDE.Actions;

import IDE.Controllers.SolutionExplorerController;
import IDE.Project.ProjectFile;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import java.io.IOException;

public class ShowInExplorerAction implements Action {

	//
	SolutionExplorerController SolutionExplorerController_;

	/**
	 * @param SolutionExplorerController_i
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

		TreePath SelectedPath = SolutionExplorerController_.GetSolutionExplorer().getSelectionPath();

		DefaultMutableTreeNode SelectedNode = (DefaultMutableTreeNode)SelectedPath.getLastPathComponent();
		if(SelectedNode != null && !SelectedNode.getAllowsChildren()) {

			ProjectFile SelectedProjectFile = (ProjectFile)SelectedNode.getUserObject();

			try {
				new ProcessBuilder("explorer.exe", "/select," + SelectedProjectFile.GetPath()).start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
