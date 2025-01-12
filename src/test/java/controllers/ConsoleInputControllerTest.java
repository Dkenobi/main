package controllers;


import org.junit.jupiter.api.Test;
import repositories.ProjectRepository;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class ConsoleInputControllerTest {
    private ProjectRepository projectRepository;
    private ConsoleInputController consoleInputController;

    //@@author seanlimhx
    public ConsoleInputControllerTest() {
        this.projectRepository = new ProjectRepository();
        this.consoleInputController = new ConsoleInputController(this.projectRepository);
    }

    /**
     * Always true test just to test if jUnit is working.
     */
    @Test
    void alwaysTrue() {
        assertEquals(2, 2);
    }

    @Test
    void commandListTest_noProjects() {
        while (projectRepository.getAll().size() != 0) {
            projectRepository.deleteItem(1);
        }
        String[] expectedOutput = new String[] {"You currently have no projects!"};
        String[] actualOutput = consoleInputController.onCommandReceived("list");
        assertArrayEquals(expectedOutput, actualOutput);
    }

    @Test
    void commandListTest_haveOneEmptyProject() {
        while (projectRepository.getAll().size() != 0) {
            projectRepository.deleteItem(1);
        }
        projectRepository.addToRepo("create Avengers");

        String[] expectedOutput = new String[] {
            "+----------------------------------------------------------------------+",
            "|Here are all the Projects you are managing:                           |",
            "+----------------------------------------------------------------------+",
            "| +------------------------------------------------------------------+ |",
            "| |Project 1: Avengers                                               | |",
            "| +------------------------------------------------------------------+ |",
            "| |Members:                                                          | |",
            "| | --                                                               | |",
            "| |Next Deadline:                                                    | |",
            "| | --                                                               | |",
            "| |Overall Progress:                                                 | |",
            "| | --                                                               | |",
            "| +------------------------------------------------------------------+ |",
            "+----------------------------------------------------------------------+"};
        String[] actualOutput = consoleInputController.onCommandReceived("list");
        assertArrayEquals(expectedOutput, actualOutput);
    }

    @Test
    void commandListTest_haveTwoEmptyProject() {
        while (projectRepository.getAll().size() != 0) {
            projectRepository.deleteItem(1);
        }
        projectRepository.addToRepo("create Avengers");
        projectRepository.addToRepo("create Justice League");

        String[] expectedOutput = new String[] {
            "+----------------------------------------------------------------------+",
            "|Here are all the Projects you are managing:                           |",
            "+----------------------------------------------------------------------+",
            "| +------------------------------------------------------------------+ |",
            "| |Project 1: Avengers                                               | |",
            "| +------------------------------------------------------------------+ |",
            "| |Members:                                                          | |",
            "| | --                                                               | |",
            "| |Next Deadline:                                                    | |",
            "| | --                                                               | |",
            "| |Overall Progress:                                                 | |",
            "| | --                                                               | |",
            "| +------------------------------------------------------------------+ |",
            "| +------------------------------------------------------------------+ |",
            "| |Project 2: Justice League                                         | |",
            "| +------------------------------------------------------------------+ |",
            "| |Members:                                                          | |",
            "| | --                                                               | |",
            "| |Next Deadline:                                                    | |",
            "| | --                                                               | |",
            "| |Overall Progress:                                                 | |",
            "| | --                                                               | |",
            "| +------------------------------------------------------------------+ |",
            "+----------------------------------------------------------------------+"};
        String[] actualOutput = consoleInputController.onCommandReceived("list");
        assertArrayEquals(expectedOutput, actualOutput);
    }

    @Test
    void commandManageTest_correctInputs() {
        while (projectRepository.getAll().size() != 0) {
            projectRepository.deleteItem(1);
        }
        projectRepository.addToRepo("create Avengers");

        String[] expectedOutput = new String[] {"Now managing Avengers"};
        String[] actualOutput = consoleInputController.onCommandReceived("manage 1");
        assertArrayEquals(expectedOutput, actualOutput);
    }

    @Test
    void commandManageTest_incompleteInput() {
        while (projectRepository.getAll().size() != 0) {
            projectRepository.deleteItem(1);
        }
        projectRepository.addToRepo("create Avengers");

        String[] expectedOutput = new String[] {"Please enter a project number!"};
        String[] actualOutput = consoleInputController.onCommandReceived("manage");
        assertArrayEquals(expectedOutput, actualOutput);
        String[] actualOutput2 = consoleInputController.onCommandReceived("manage ");
        assertArrayEquals(expectedOutput, actualOutput2);
    }

    @Test
    void commandManageTest_invalidIndex() {
        while (projectRepository.getAll().size() != 0) {
            projectRepository.deleteItem(1);
        }
        projectRepository.addToRepo("create Avengers");

        String[] expectedOutput = new String[] {"Please enter the correct index of an existing Project!"};
        String[] actualOutput = consoleInputController.onCommandReceived("manage 2");
        assertArrayEquals(expectedOutput, actualOutput);
        String[] actualOutput2 = consoleInputController.onCommandReceived("manage 4");
        assertArrayEquals(expectedOutput, actualOutput2);
    }

    @Test
    void commandDeleteTest_correctInputs() {
        while (projectRepository.getAll().size() != 0) {
            projectRepository.deleteItem(1);
        }
        projectRepository.addToRepo("create Avengers");
        String[] expectedOutput = new String[] {"Project 1 has been deleted"};
        String[] actualOutput = consoleInputController.onCommandReceived("delete 1");
        assertArrayEquals(expectedOutput, actualOutput);
    }

    @Test
    void commandDeleteTest_invalidIndex() {
        while (projectRepository.getAll().size() != 0) {
            projectRepository.deleteItem(1);
        }
        projectRepository.addToRepo("create Avengers");
        String[] expectedOutput = new String[] {"Error occurred! There could be three possibilities:",
            "You could have attempted to delete a Project after renaming it's JSON file",
            "You could have entered a Project index is out of bounds.",
            "You could have attempted to delete the default Project loaded immediately. "
                + "Do not panic if this was you. The default Project is deleted correctly"};
        String[] actualOutput = consoleInputController.onCommandReceived("delete 2");
        assertArrayEquals(expectedOutput, actualOutput);
        String[] actualOutput2 = consoleInputController.onCommandReceived("delete 0");
        assertArrayEquals(expectedOutput, actualOutput2);
    }

    @Test
    void commandCreateTest_correctInputs() {
        while (projectRepository.getAll().size() != 0) {
            projectRepository.deleteItem(1);
        }
        String[] expectedOutput = new String[] {"Project created!"};
        String[] actualOutput = consoleInputController.onCommandReceived("create Avengers");
        assertArrayEquals(expectedOutput, actualOutput);

    }

    @Test
    void commandCreateTest_wrongInputs() {
        while (projectRepository.getAll().size() != 0) {
            projectRepository.deleteItem(1);
        }
        String[] expectedOutput = new String[] {"Creation of Project failed. Please ensure that Project name "
                + "doesn't have any special characters"};
        String[] actualOutput = consoleInputController.onCommandReceived("create");
        assertArrayEquals(expectedOutput, actualOutput);
        String[] actualOutput2 = consoleInputController.onCommandReceived("create ");
        assertArrayEquals(expectedOutput, actualOutput2);
    }
}
