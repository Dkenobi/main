package models.member;

import util.ParserHelper;

import java.util.ArrayList;

public class MemberList implements IMemberList {
    private ArrayList<Member> memberList;
    private ParserHelper parserHelper;

    /**
     * Class representing a list with all members and their details.
     */
    public MemberList() {
        this.memberList = new ArrayList<>();
        this.parserHelper = new ParserHelper();
    }

    /**
     * Adds a new member to the member list of this project.
     * @param newMember A new member to be added to the project.
     */
    @Override
    public void addMember(Member newMember) {
        newMember.setIndexNumber(this.memberList.size() + 1);
        this.memberList.add(newMember);
    }

    /**
     * Updates the member details of an existing member using the index number of the member.
     * @param memberIndexNumber The index number of the member whose details are to be updated.
     * @param updatedMemberDetails The updated member details.
     */
    public void editMember(int memberIndexNumber, String updatedMemberDetails) {
        String [] memberDetails = parserHelper.parseMemberDetails(updatedMemberDetails);
        String name = memberDetails[0];
        String phone = memberDetails[1];
        String email = memberDetails[2];

        for (Member currentMember : memberList) {
            if (currentMember.getIndexNumber() == memberIndexNumber) {
                currentMember.updateDetails(name, phone, email);
                break;
            }
        }
    }

    /**
     * Removes a member from the list.
     * Shifts the index numbers of all members up if required.
     * @param toBeRemoved The Member that needs to be removed
     */
    @Override
    public void removeMember(Member toBeRemoved) {
        /*
            Implement methods to ensure that task assignments are updated,
            credits are redistributed, etc.
            1) Scroll through all tasks of this member. Remove them from the ListOfMemebersAssignedToTask.
            2) Recalculate the credits for other members in the task.
            3) Change task state if necessary (if only 1 member was DOING, now it is OPEN)
            4) Update index number of other members if necessary.
         */
        if (toBeRemoved.getIndexNumber() < memberList.size()) { //if need to reassign index numbers after removal
            this.memberList.remove(toBeRemoved);
            for (int i = 1; i <= memberList.size(); i++) {
                memberList.get(i - 1).setIndexNumber(i);
            }
        } else {
            this.memberList.remove(toBeRemoved);
        }
    }

    /**
     * Returns an ArrayList with String descriptions of members details.
     * @return An ArrayList with String descriptions of members details.
     */
    @Override
    public ArrayList<String> getAllMemberDetails() {
        ArrayList<String> memberDetails = new ArrayList<>();
        for (Member member : this.memberList) {
            memberDetails.add(member.getDetails());
        }
        return memberDetails;
    }

    @Override
    public Member getMember(int i) {
        return this.memberList.get(i - 1);
    }

    /**
     * Returns the size of the current member list.
     * @return The size of the current member list.
     */
    public int getNumOfMembers() {
        return memberList.size();
    }

}