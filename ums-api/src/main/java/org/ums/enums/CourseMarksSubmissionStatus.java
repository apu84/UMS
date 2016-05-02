package org.ums.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ikh on 4/29/2016.
 */
public enum CourseMarksSubmissionStatus {
    NOT_SUBMITTED(0,"Not Submitted"),
    WAITING_FOR_SCRUTINY(1,"Waiting for Scrutiny"),
    WAITING_FOR_HEAD_APPROVAL(2,"Waiting for Head's Approval"),
    REQUESTED_FOR_RECHECK_BY_HEAD(2,"Requested for recheck by Head"),
    WAITING_FOR_COE_APPROVAL(3,"Waiting for CoE's Approval"),
    REQUESTED_FOR_RECHECK_BY_COE(4,"Requested for recheck by CoE"),
    ACCEPTED_BY_COE(5,"Accepted by CoE");


    private String label;
    private int id;

    private CourseMarksSubmissionStatus(int id, String label){
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
