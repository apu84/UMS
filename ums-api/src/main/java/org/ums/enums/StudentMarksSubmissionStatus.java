package org.ums.enums;

/**
 * Created by ikh on 4/29/2016.
 */
public enum StudentMarksSubmissionStatus {
    NONE(0,"None"),
    SUBMIT(1,"Submit"),
    SUBMITTED(2,"Submitted"),
    SCRUTINIZE(3,"Scrutiny"),
    SCRUTINIZED(4,"Scrutinized"),
    APPROVE(5,"Approve"),
    APPROVED(6,"Approved"),
    ACCEPT(7,"Accept"),
    ACCEPTED(8,"Accepted");

    private String label;
    private int id;

    private StudentMarksSubmissionStatus(int id, String label){
        this.id  = id;
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public int getId() {
        return id;
    }
}
