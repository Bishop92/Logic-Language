package IDE.Actions;

public interface Action {

	/**
	 * Return the name of the action
	 *
	 * @return The name of the action
	 */
	String GetActionName();

	/**
	 * Execute the action
	 */
	void Execute();
}