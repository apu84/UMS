package org.ums.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ikh on 4/29/2016.
 */
public enum ExamType {
    SEMESTER_FINAL(1),
    CLEARANCE_CARRY_IMPROVEMENT(2);

    private static final Map<Integer, ExamType> lookup
            = new HashMap<>();

    static {
        for (ExamType c : EnumSet.allOf(ExamType.class)) {
            lookup.put(c.getValue(), c);
        }
    }


    private int pEamTypeCode;

    private ExamType(int pEamTypeCode) {
        this.pEamTypeCode = pEamTypeCode;
    }

    public static ExamType get(final int pEamTypeCode) {
        return lookup.get(pEamTypeCode);
    }

    public int getValue() {
        return this.pEamTypeCode;
    }
}