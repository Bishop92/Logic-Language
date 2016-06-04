package IDE.Project;

import java.util.Vector;

public class ProjectDirectory {

	//The parent directory of this directory
	private ProjectDirectory ParentDirectory_;

	//The name of the directory
	private String Name_;

	//The files contained in the directory
	private Vector<ProjectFile> Files_;

	//The files contained in the directory
	private Vector<ProjectDirectory> Directories_;

	/**
	 * @param Name_i The name of the directory
	 */
	public ProjectDirectory(String Name_i) {
		this(Name_i, null);
	}

	/**
	 * @param Name_i            The name of the directory
	 * @param ParentDirectory_i The parent directory
	 */
	public ProjectDirectory(String Name_i, ProjectDirectory ParentDirectory_i) {
		ParentDirectory_ = ParentDirectory_i;
		Name_ = Name_i;
		Files_ = new Vector<>();
		Directories_ = new Vector<>();
	}

	/**
	 * Transform the object into a string
	 *
	 * @return The string representing the object
	 */
	public String toString() {
		return GetName();
	}

	/**
	 * Add the project file to the directory
	 *
	 * @param File_i The file to add to the directory
	 * @return The index where the file has been added
	 */
	public int AddProjectFile(ProjectFile File_i) {
		//Insert the file in lexicographically order
		for (int FileIndex = 0; FileIndex < Files_.size(); ++FileIndex) {
			if (Files_.get(FileIndex).GetName().compareToIgnoreCase(File_i.GetName()) > 0) {
				//The current file is lexicographically greater than the file to add, so add the file in the current position
				Files_.insertElementAt(File_i, FileIndex);
				return Directories_.size() + FileIndex;
			}
		}

		//The file is the last in the list of the files, so add it add the end
		Files_.add(File_i);
		return Directories_.size() + Files_.size() - 1;
	}

	/**
	 * Add the project directory to the directory
	 *
	 * @param Directory_i The directory to add to the directory
	 * @return The index where the directory has been added
	 */
	public int AddProjectDirectory(ProjectDirectory Directory_i) {
		//Insert the directory in lexicographically order
		for (int DirectoryIndex = 0; DirectoryIndex < Directories_.size(); ++DirectoryIndex) {
			if (Directories_.get(DirectoryIndex).GetName().compareToIgnoreCase(Directory_i.GetName()) > 0) {
				//The current directory is lexicographically greater than the file to add, so add the directory in the current position
				Directories_.insertElementAt(Directory_i, DirectoryIndex);
				return DirectoryIndex;
			}
		}

		//The directory is the last in the list of the directories, so add it add the end
		Directories_.add(Directory_i);
		return Directories_.size() - 1;
	}

	/**
	 * Return the directories in the current directory
	 *
	 * @return the directories in the current directory
	 */
	public Vector<ProjectDirectory> GetProjectDirectories() {
		return Directories_;
	}

	/**
	 * Return the files in the current directory
	 *
	 * @return the files in the current directory
	 */
	public Vector<ProjectFile> GetProjectFiles() {
		return Files_;
	}

	/**
	 * Return the parent directory
	 *
	 * @return The parent directory
	 */
	public ProjectDirectory GetParentDirectory() {
		return ParentDirectory_;
	}

	/**
	 * Return the name of the directory
	 *
	 * @return The name of the directory
	 */
	public String GetName() {
		return Name_;
	}

	/**
	 * Return the directory in the current directory with the given name
	 *
	 * @param Name_i The name of the directory to return
	 * @return The directory found, null otherwise
	 */
	public ProjectDirectory GetProjectDirectory(String Name_i) {
		for (ProjectDirectory CurrentDirectory : Directories_) {
			//For each sub directory check if it has the same name as requested
			if (CurrentDirectory.GetName().equals(Name_i)) {
				//Return the directory found
				return CurrentDirectory;
			}
		}

		//No directory has been found
		return null;
	}

	/**
	 * Return the file in the current directory with the given name
	 *
	 * @param Name_i The name of the file to return
	 * @return The file found, null otherwise
	 */
	public ProjectFile GetProjectFile(String Name_i) {
		for (ProjectFile CurrentFile : Files_) {
			//For each sub file check if it has the same name as requested
			if (CurrentFile.GetName().equals(Name_i)) {
				//Return the file found
				return CurrentFile;
			}
		}

		//No file has been found
		return null;
	}
}