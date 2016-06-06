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
    REQUESTED_FOR_RECHECK_BY_SCRUTINIZER(2,"Requested for recheck by Scrutinizer"),
    WAITING_FOR_HEAD_APPROVAL(3,"Waiting for Head's Approval"),
    REQUESTED_FOR_RECHECK_BY_HEAD(4,"Requested for recheck by Head"),
    WAITING_FOR_COE_APPROVAL(5,"Waiting for CoE's Approval"),
    REQUESTED_FOR_RECHECK_BY_COE(6,"Requested for recheck by CoE"),
    ACCEPTED_BY_COE(7,"Accepted by CoE");


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
