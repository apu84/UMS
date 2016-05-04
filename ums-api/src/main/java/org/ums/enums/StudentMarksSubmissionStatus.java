package org.ums.enums;

/**
 * Created by ikh on 4/29/2016.
 */
public enum StudentMarksSubmissionStatus {
    NONE(0,"None"),
    SAVED(1,"Saved"),
    SUBMITTED(2,"Submitted"),
    APPROVED(3,"Approved"),
    ACCEPTED(4,"Accepted"),
    RECHECK(5,"Recheck"),
    RECHECKED(6,"Rechecked");


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
