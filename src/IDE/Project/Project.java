package IDE.Project;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Vector;

public class Project {

	//The name of the project
	private String Name_;

	//The path of the root of the project
	private String RootPath_;

	//The path of the project
	private String Path_;

	//The directory root of the project
	private ProjectDirectory Root_;

	/**
	 * @param Path_i The path of the project
	 */
	public Project(String ProjectName_i, String Path_i) {
		RootPath_ = Path_i;
		Name_ = ProjectName_i;
		Path_ = RootPath_ + "\\" + Name_ + ".xml";
	}

	/**
	 * Create a new project
	 *
	 * @param ProjectName_i The name of the project to create
	 * @param Path_i        The location of the project to creates
	 */
	public void CreateProject(String ProjectName_i, String Path_i) {
		RootPath_ = Path_i;
		Name_ = ProjectName_i;
		Path_ = RootPath_ + "/" + Name_ + ".xml";

		//Create the root of the project
		Root_ = new ProjectDirectory("Source");

		//Save the the project
		Save();
	}

	/**
	 * Return the root of the project
	 *
	 * @return The root of the project
	 */
	public ProjectDirectory GetRoot() {
		return Root_;
	}

	/**
	 * Add a file to the project
	 *
	 * @param PathToAdd_i The list of directory where to add the file
	 * @param FileName_i  The name of the file
	 * @param FilePath_i  The path of the file
	 */
	public boolean AddProjectFile(Vector<String> PathToAdd_i, String FileName_i, String FilePath_i) {

		//Start from searching from the root
		ProjectDirectory CurrentDirectory = Root_;
		for (int CurrentDirectoryIndex = 1; CurrentDirectoryIndex < PathToAdd_i.size(); ++CurrentDirectoryIndex) {
			if (CurrentDirectory != null) {
				//Get the next directory
				CurrentDirectory = CurrentDirectory.GetProjectDirectory(PathToAdd_i.get(CurrentDirectoryIndex));
			}
		}

		if (CurrentDirectory != null && CurrentDirectory.GetProjectFile(FileName_i) == null) {
			//If the directory has been found and file does not exists, then add the new file
			ProjectFile NewFile = new ProjectFile(FilePath_i, FileName_i);
			CurrentDirectory.AddProjectFile(NewFile);

			//The file has been correctly added, so return true
			return true;
		}

		//The file has not been added, return false
		return false;
	}

	/**
	 * Get the project file corresponding to the given path
	 *
	 * @param FilePath_i The path of the file in the projects
	 * @return The project file requested, null if the path does not correspond to a file
	 */
	public ProjectFile GetProjectFile(Vector<String> FilePath_i) {
		ProjectDirectory CurrentDirectory = Root_;
		for (int CurrentDirectoryIndex = 1; CurrentDirectoryIndex < FilePath_i.size() - 1; ++CurrentDirectoryIndex) {
			if (CurrentDirectory != null) {
				//Get the next directory
				CurrentDirectory = CurrentDirectory.GetProjectDirectory(FilePath_i.get(CurrentDirectoryIndex));
			}
		}

		if (CurrentDirectory != null) {
			//Return the file in the last directory found
			return CurrentDirectory.GetProjectFile(FilePath_i.lastElement());
		}

		//The path is wrong so no file could be returned
		return null;
	}

	/**
	 * Load the project creating the entire tree of files and directories
	 */
	public void Load() {

		try {
			//Load the file to parse
			File ProjectFile = new File(Path_);

			//Create the factory that load the Document
			DocumentBuilderFactory Factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder DocumentBuilder = Factory.newDocumentBuilder();

			//Parse the document
			Document ProjectDocument = DocumentBuilder.parse(ProjectFile);
			//Get the root of the project
			Element ProjectRoot = ProjectDocument.getDocumentElement();
			//Normalize the root of the project and so all the document
			ProjectRoot.normalize();

			//Set the root of the project
			Root_ = new ProjectDirectory(ProjectRoot.getAttribute("Name"));

			//Parse the elements of the xml in order to build the project tree
			ReadProjectTree(Root_, ProjectDocument.getDocumentElement());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Save the project
	 */
	public void Save() {

		try {
			//Create the factory that load the Document
			DocumentBuilderFactory Factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder DocumentBuilder = Factory.newDocumentBuilder();

			//Parse the document
			Document ProjectDocument = DocumentBuilder.newDocument();
			//Get the root of the project
			Element ProjectRoot = ProjectDocument.createElement("Directory");
			ProjectRoot.setAttribute("Name", Root_.GetName());

			//Parse the elements of the xml in order to build the project tree
			WriteProjectTree(Root_, ProjectRoot, ProjectDocument);

			//add the root to the document
			ProjectDocument.appendChild(ProjectRoot);

			//Create the document transformer in order to save the file
			Transformer DocumentTransformer = TransformerFactory.newInstance().newTransformer();
			DocumentTransformer.setOutputProperty(OutputKeys.INDENT, "yes");
			DocumentTransformer.setOutputProperty(OutputKeys.METHOD, "xml");
			DocumentTransformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			DocumentTransformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

			//Save the file
			DocumentTransformer.transform(new DOMSource(ProjectDocument), new StreamResult(new FileOutputStream(Path_)));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Parse the document that contains the structure of the project and create the relative element
	 *
	 * @param Parent_i         The directory parent
	 * @param ProjectElement_i The current element in the document
	 */
	private void ReadProjectTree(ProjectDirectory Parent_i, Element ProjectElement_i) {
		//Get the directories in the document
		NodeList DirectoryElements = ProjectElement_i.getElementsByTagName("Directory");
		for (int DirectoryElementIndex = 0; DirectoryElementIndex < DirectoryElements.getLength(); ++DirectoryElementIndex) {
			//Get the directory element in the document
			Node CurrentNode = DirectoryElements.item(DirectoryElementIndex);
			if (CurrentNode.getNodeType() == Node.ELEMENT_NODE && CurrentNode.getParentNode() == ProjectElement_i) {
				//The current node must be an element and a child of the current element
				Element DirectoryElement = (Element) CurrentNode;

				ProjectDirectory CurrentDirectory = new ProjectDirectory(DirectoryElement.getAttribute("Name"), Parent_i);
				Parent_i.AddProjectDirectory(CurrentDirectory);
				ReadProjectTree(CurrentDirectory, DirectoryElement);
			}
		}

		//Get the files in the document
		NodeList FilesElements = ProjectElement_i.getElementsByTagName("File");
		for (int FileElementIndex = 0; FileElementIndex < FilesElements.getLength(); ++FileElementIndex) {
			//Get the file element in the document
			Node CurrentNode = FilesElements.item(FileElementIndex);
			if (CurrentNode.getNodeType() == Node.ELEMENT_NODE && CurrentNode.getParentNode() == ProjectElement_i) {
				//The current node must be an element and a child of the current element
				Element FileElement = (Element) CurrentNode;

				//Create the current file and
				ProjectFile CurrentFile = new ProjectFile(FileElement.getTextContent(), FileElement.getAttribute("Name"));
				Parent_i.AddProjectFile(CurrentFile);
			}
		}
	}

	/**
	 * Parse the document that contains the structure of the project and create the relative element
	 *
	 * @param Parent_i         The directory parent
	 * @param ProjectElement_i The current element in the document
	 */
	private void WriteProjectTree(ProjectDirectory Parent_i, Element ProjectElement_i, Document ProjectDocument_i) {
		//Get the directories in the project
		Vector<ProjectDirectory> Directories = Parent_i.GetProjectDirectories();
		for (ProjectDirectory CurrentDirectory : Directories) {

			//Create an element for each directory in the project
			Element DirectoryElement = ProjectDocument_i.createElement("Directory");
			DirectoryElement.setAttribute("Name", CurrentDirectory.GetName());

			//Add the new element to the parent element
			ProjectElement_i.appendChild(DirectoryElement);

			//Add all the child of the current directory
			WriteProjectTree(CurrentDirectory, DirectoryElement, ProjectDocument_i);
		}

		//Get the files in the project
		Vector<ProjectFile> Files = Parent_i.GetProjectFiles();
		for (ProjectFile CurrentFile : Files) {
			//Create an element for each file in the project
			Element FileElement = ProjectDocument_i.createElement("File");
			FileElement.setAttribute("Name", CurrentFile.GetName());
			FileElement.setTextContent(CurrentFile.GetPath());

			//Add the new element to the parent element
			ProjectElement_i.appendChild(FileElement);
		}
	}

	/**
	 * Get the path of the file in the file system considering a partial path in the projects
	 *
	 * @param TargetDirectory_i The target directory where the file must be add
	 * @param FileName_i        The name of the file
	 * @return The path in the file system, null if the given path is wrong or the file already exists
	 */
	public String GetNewFilePath(ProjectDirectory TargetDirectory_i, String FileName_i) {

		if (TargetDirectory_i.GetProjectFile(FileName_i) != null) {
			//The file already exists in the target directory, so return null
			return null;
		}

		//Build the path from the last directory to the root
		String FilePath = FileName_i + ".ext";
		ProjectDirectory CurrentDirectory = TargetDirectory_i;
		do {
			FilePath = CurrentDirectory.GetName() + "\\" + FilePath;
			CurrentDirectory = CurrentDirectory.GetParentDirectory();
		} while (CurrentDirectory != null);

		//When the root is reached add the path of the project
		return RootPath_ + "\\" + FilePath;
	}

	/**
	 * Get the path of the directory in the file system considering a partial path in the projects
	 *
	 * @param TargetDirectory_i The target directory where the directory must be add
	 * @param DirectoryName_i        The name of the directory
	 * @return The path in the file system, null if the given path is wrong or the directory already exists
	 */
	public String GetNewDirectoryPath(ProjectDirectory TargetDirectory_i, String DirectoryName_i) {

		if (TargetDirectory_i.GetProjectDirectory(DirectoryName_i) != null) {
			//The directory already exists in the target directory, so return null
			return null;
		}

		//Build the path from the last directory to the root
		String DirectoryPath = DirectoryName_i;
		ProjectDirectory CurrentDirectory = TargetDirectory_i;
		do {
			DirectoryPath = CurrentDirectory.GetName() + "\\" + DirectoryPath;
			CurrentDirectory = CurrentDirectory.GetParentDirectory();
		} while (CurrentDirectory != null);

		//When the root is reached add the path of the project
		return RootPath_ + "\\" + DirectoryPath;
	}
}