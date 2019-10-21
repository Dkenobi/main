package models.task;

import models.member.ListOfMembersAssignedToTask;
import models.member.Member;
import util.date.DateTimeHelper;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;

public class Task implements ITask {
    private String taskName;
    private int taskPriority;
    private Date dueDate;
    private int taskCredit;
    private TaskState taskState;
    private ListOfMembersAssignedToTask listOfMembersAssignedToTask;
    private ArrayList<String> taskRequirements;
    DateTimeHelper dateTimeHelper;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (!(obj instanceof Task)) {
            return false;
        } else {
            Task other = (Task) obj;
            return this.taskName.equals(other.taskName)
                && this.taskPriority == other.taskPriority
                && this.taskCredit == other.taskCredit;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.taskName, this.taskPriority, this.taskCredit);
    }

    /**
     * Class representing a task in a project.
     * @param taskName The name of the task.
     * @param taskPriority The priority value of the task.
     * @param dueDate The date that the task is due. [Optional]
     * @param taskCredit The amount of credit a person would receive for completing the task.
     *                   A more difficult task would receive more credit.
     * @param taskState taskState refers to whether the task is in OPEN, TO-DO, DOING, DONE.
     * @param taskRequirements ArrayList of Strings containing specific requirements of a task. [Optional]
     */
    public Task(String taskName, int taskPriority, Date dueDate, int taskCredit, TaskState taskState,
                ArrayList<String> taskRequirements) {
        this.taskName = taskName;
        this.taskPriority = taskPriority;
        this.dueDate = dueDate;
        this.taskCredit = taskCredit;
        this.taskState = taskState;
        this.listOfMembersAssignedToTask = new ListOfMembersAssignedToTask();
        this.taskRequirements = taskRequirements;
        this.dateTimeHelper = new DateTimeHelper();
    }

    public String getTaskName() {
        return taskName;
    }

    public int getTaskPriority() {
        return taskPriority;
    }

    /**
     * Gets the details of the task in a String format in the correct layout.
     * @return String containing all the details of the task.
     */
    public String getDetails() {
        if (this.dueDate != null) {
            return this.taskName + " | Priority: "
                    + this.taskPriority + " | Due: " + dateTimeHelper.formatDateForDisplay(this.dueDate) + " | Credit: "
                    + this.taskCredit + " | State: " + this.taskState;
        } else {
            return this.taskName + " | Priority: "
                    + this.taskPriority + " | Due: -- | Credit: " + this.taskCredit + " | State: "
                    + this.taskState;
        }
    }

    public Date getDueDate() {
        return this.dueDate;
    }

    public TaskState getTaskState() {
        return this.taskState;
    }

    public int getTaskCredit() {
        return this.taskCredit;
    }

    public ListOfMembersAssignedToTask getAssignedMembers() {
        return listOfMembersAssignedToTask;
    }

    public HashSet<Integer> getAssignedIndexes() {
        return this.listOfMembersAssignedToTask.getAssignedMembersIndexNumbers();
    }

    public void assignMember(Member member) {
        this.listOfMembersAssignedToTask.addMember(member);
    }

    public void removeMember(Member memberToRemove) {
        this.listOfMembersAssignedToTask.removeMember(memberToRemove);
    }

    /**
     * Adds index labels to task requirements for clearer viewing.
     * @return ArrayList of String of task requirements with labelled indexes.
     */
    public ArrayList<String> getTaskRequirements() {
        ArrayList<String> taskRequirements = new ArrayList<>();
        taskRequirements.add(getDetails());
        int index = 1;
        for (String s : this.taskRequirements) {
            taskRequirements.add(index + ". " + s);
            index++;
        }
        return taskRequirements;
    }

    public int getNumOfTaskRequirements() {
        return this.taskRequirements.size();
    }

    public void setTaskName(String newTaskName) {
        this.taskName = newTaskName;
    }

    public void setTaskPriority(int newTaskPriority) {
        this.taskPriority = newTaskPriority;
    }

    /**
     * Converts String input into Date object to be set as the new dueDate.
     * @param newDueDateString String form of the new dueDate to be set.
     */
    public void setDueDate(String newDueDateString) {
        try {
            this.dueDate = dateTimeHelper.formatDate(newDueDateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * Edits current task credit to new input task credit.
     * @param newTaskCredit new task credit to be set.
     */
    public void setTaskCredit(int newTaskCredit) {
        this.taskCredit = newTaskCredit;
    }

    /**
     * Converts input String into TaskState and edits current task state to new task state.
     * @param newTaskStateString String form of new task state.
     */
    public void setTaskState(String newTaskStateString) {
        String newTaskStateLowerCase = newTaskStateString.toLowerCase();
        switch (newTaskStateLowerCase) {
        case "done":
            this.taskState = TaskState.DONE;
            break;
        case "todo":
            this.taskState = TaskState.TODO;
            break;
        case "doing":
            this.taskState = TaskState.DOING;
            break;
        default:
            this.taskState = TaskState.OPEN;
            break;
        }
    }

    public void removeTaskRequirement(int indexOfTaskRequirement) {
        this.taskRequirements.remove(indexOfTaskRequirement - 1);
    }

    public void addTaskRequirement(String newTaskRequirement) {
        this.taskRequirements.add(newTaskRequirement);
    }
}
