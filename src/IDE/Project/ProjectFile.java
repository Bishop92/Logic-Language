package IDE.Project;

public class ProjectFile {

	//The path of the file
	private String Path_;

	//The name of the file
	private String Name_;

	//True if the file is open in the editor, false otherwise
	private boolean OpenInEditor_;

	/**
	 * @param Path The path where the file is located
	 * @param Name The name of the file
	 */
	public ProjectFile(String Path, String Name) {
		Path_ = Path;
		Name_ = Name;
		OpenInEditor_ = false;
	}

	/**
	 * Transform the object into a string
	 * @return The string representing the object
	 */
	public String toString() {
		return GetName();
	}

	/**
	 * Return the name of the file
	 *
	 * @return The name of the file
	 */
	public String GetName() {
		return Name_;
	}

	/**
	 * Return the path of the file
	 *
	 * @return The path of the file
	 */
	public String GetPath() {
		return Path_;
	}

	/**
	 * Set if the file is open in editor
	 * @param OpenInEditor_i True if the file is open in editor, false otherwise
	 */
	public void SetOpenInEditor(boolean OpenInEditor_i) {
		OpenInEditor_ = OpenInEditor_i;
	}

	/**
	 * Return true if the file is open in editor, false otherwise
	 * @return True if the file is open in editor, false otherwise
	 */
	public boolean IsOpenInEditor() {
		return OpenInEditor_;
	}
}
