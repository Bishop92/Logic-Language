package IDE;

import IDE.Controllers.OpenFilesController;
import IDE.Controllers.SolutionExplorerController;
import IDE.Controllers.MenuBarController;
import IDE.Project.Project;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class IDE extends JFrame {
    private JPanel MainPanel;
    private JTree SolutionExplorer;
    private JTabbedPane OpenFiles;
	private JPanel MenuBar;

    //The opened project
    private Project Project_;

    //The controller of the solution explorer
    private SolutionExplorerController SolutionExplorerController_;

    //The controller of the menu bar
    private MenuBarController MenuBarController_;

	//The controller of the open files
	private OpenFilesController OpenFilesController_;

	public IDE() {

        setSize(1200, 800);

        add(MainPanel);

        setVisible(true);

		//Save if the IDE is closing
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				super.windowClosing(e);
				Project_.Save();
			}
		});
	}

    private void createUIComponents() {

	    //Create the project and load it
	    Project_ = new Project("Project", "C:\\TestProject");
	    Project_.Load();

	    OpenFilesController_ = new OpenFilesController();
	    OpenFiles = OpenFilesController_.CreateOpenFiles();

        SolutionExplorerController_ = new SolutionExplorerController(Project_);
	    SolutionExplorerController_.SetOpenFiles(OpenFiles);

		SolutionExplorer = SolutionExplorerController_.CreateSolutionExplorer();

        MenuBarController_ = new MenuBarController(Project_, SolutionExplorerController_);
	    MenuBar = new JPanel();
	    MenuBar.add(MenuBarController_.CreateMenuBar());
    }
}
