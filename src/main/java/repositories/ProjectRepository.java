package repositories;

import exceptions.DukeException;
import models.project.IProject;
import models.project.Project;
import util.factories.ProjectFactory;
import util.json.JsonConverter;
import util.log.DukeLogger;

import java.util.ArrayList;

public class ProjectRepository implements IRepository<Project> {
    private ArrayList<Project> allProjects;
    private ProjectFactory projectFactory;
    private JsonConverter jsonConverter = new JsonConverter();

    public ProjectRepository() {
        allProjects = jsonConverter.loadAllProjectsData();
        this.projectFactory = new ProjectFactory();
    }

    @Override
    public ArrayList<Project> getAll() {
        return allProjects;
    }

    @Override
    public boolean addToRepo(String input) {
        IProject newProject = projectFactory.create(input);
        DukeLogger.logDebug(ProjectRepository.class, "New project created with name: '"
                + newProject.getName() + "'");
        if (newProject.getName() == null || newProject.getMembers() == null) {
            return false;
        }
        Project newlyCreatedProject = (Project) newProject;
        allProjects.add(newlyCreatedProject);
        jsonConverter.saveProject(newlyCreatedProject);
        return true;
    }

    /**
     * Method to retrieve a Project from ArrayList of Projects.
     * @param projectNumber : Index of Project that user wishes to retrieve
     * @return Returns the Project object desired by user
     */
    public Project getItem(int projectNumber) {
        return this.allProjects.get(projectNumber - 1);
    }

    /**
     * Method for deletion of projects.
     * @param projectNumber : Index of project that user wishes to delete
     * @return Returns a boolean that states whether the project is deleted successfully
     */
    public boolean deleteItem(int projectNumber) {
        try {
            jsonConverter.deleteProject(allProjects.get(projectNumber - 1));
            this.allProjects.remove(projectNumber - 1);
            return true;
        } catch (IndexOutOfBoundsException | DukeException err) {
            return false;
        }
    }

    /**
     * Method to get all project details in a suitable form for CLIView to print in a table form.
     * @return ArrayList of details to be presented in each table, with each element as an ArrayList
     *         containing each row in the table.
     */
    public ArrayList<ArrayList<String>> getAllProjectsDetailsForTable() {
        ArrayList<ArrayList<String>> toPrintAll = new ArrayList<>();
        for (int projNum = 0; projNum < allProjects.size(); projNum++) {
            ArrayList<String> toPrint = new ArrayList<>();
            toPrint.add("Project " + (projNum + 1) + ": " + allProjects.get(projNum).getName());
            toPrint.add("Members: ");
            if (allProjects.get(projNum).getNumOfMembers() == 0) {
                toPrint.add(" --");
            } else {
                for (int memberIndex = 1; memberIndex <= allProjects.get(projNum).getNumOfMembers();memberIndex++) {
                    toPrint.add(" " + allProjects.get(projNum).getMembers().getMember(memberIndex).getDetails());
                }
                toPrint.add("");
            }
            if (allProjects.get(projNum).getNumOfTasks() == 0) {
                toPrint.add("Next Deadline: ");
                toPrint.add(" --");
            } else {
                String[] detailsClosestDeadlineTask = allProjects.get(projNum).getTasks().getClosestDeadlineTask();
                toPrint.add("Next Deadline: " + detailsClosestDeadlineTask[0]);
                for (int i = 1; i < detailsClosestDeadlineTask.length; i++) {
                    toPrint.add(" - " + detailsClosestDeadlineTask[i]);
                }
                toPrint.add("");
            }
            toPrint.add("Overall Progress: ");
            if (allProjects.get(projNum).getNumOfTasks() == 0) {
                toPrint.add(" --");
            } else {
                String[] detailsOverallProgress = allProjects.get(projNum).getTasks().getOverallProgress();
                for (String detail : detailsOverallProgress) {
                    toPrint.add(" - " + detail);
                }
            }
            toPrintAll.add(toPrint);
        }
        return toPrintAll;
    }
}
