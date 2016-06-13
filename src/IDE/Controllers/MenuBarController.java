package IDE.Controllers;

import IDE.Actions.CreateNewFileAction;
import IDE.Actions.CreateNewProjectAction;
import IDE.Actions.SaveProjectAction;
import IDE.Project.Project;

import javax.swing.*;

//The controller of the menu bar
public class MenuBarController {

	//The widget that represents the menu bar
	private JMenuBar MenuBar_;

	//The controller of the solution explorer
	private SolutionExplorerController SolutionExplorerController_;

	//The current opened project
	private Project Project_;

	//The action to create a new project
	private CreateNewProjectAction CreateNewProjectAction_;

	//The action to create a new file
	private CreateNewFileAction CreateNewFileAction_;

	//The action to save the project
	private SaveProjectAction SaveProjectAction_;

	/**
	 * @param Project_i                    The current opened project
	 * @param SolutionExplorerController_i The widget that represents the solution explorer
	 */
	public MenuBarController(Project Project_i, SolutionExplorerController SolutionExplorerController_i) {
		Project_ = Project_i;
		SolutionExplorerController_ = SolutionExplorerController_i;
	}

	/**
	 * Create the menu bar
	 *
	 * @return The created menu bar
	 */
	public JMenuBar CreateMenuBar() {
		//Create the menu bar
		MenuBar_ = new JMenuBar();

		//Create the action and the item for creating a new project
		CreateNewProjectAction_ = new CreateNewProjectAction(Project_, MenuBar_);
		JMenuItem CreateNewProjectActionItem = new JMenuItem(CreateNewProjectAction_.GetActionName());
		CreateNewProjectActionItem.addActionListener(e -> CreateNewProjectAction_.Execute());

		//Create the action and the item for creating a new project
		CreateNewFileAction_ = new CreateNewFileAction(Project_, SolutionExplorerController_);
		JMenuItem CreateNewFileActionItem = new JMenuItem(CreateNewFileAction_.GetActionName());
		CreateNewFileActionItem.addActionListener(e -> CreateNewFileAction_.Execute());

		//Create the action and the item for saving the current project
		SaveProjectAction_ = new SaveProjectAction(Project_);
		JMenuItem SaveProjectActionItem = new JMenuItem(SaveProjectAction_.GetActionName());
		SaveProjectActionItem.addActionListener(e -> SaveProjectAction_.Execute());

		//Create the New menu and add the related items
		JMenu NewMenu = new JMenu("New");
		NewMenu.add(CreateNewProjectActionItem);
		NewMenu.addSeparator();
		NewMenu.add(CreateNewFileActionItem);

		//Create the File menu and add the related sub menu and items
		JMenu FileMenu = new JMenu("File");
		FileMenu.add(NewMenu);
		FileMenu.add(SaveProjectActionItem);

		//Add the sub menu to the menu bar
		MenuBar_.add(FileMenu);

		return MenuBar_;
	}
}
