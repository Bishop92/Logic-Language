package IDE.Actions;

import IDE.Project.Project;

public class SaveProjectAction implements Action {

	//The current opened project
	private Project Project_;

	/**
	 * @param Project_i The current opened projects
	 */
	public SaveProjectAction(Project Project_i) {
		Project_ = Project_i;
	}

	@Override
	public String GetActionName() {
		return "Save Project";
	}

	@Override
	public void Execute() {
		//Save the project
		Project_.Save();
	}
}