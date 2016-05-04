package org.ums.persistent.model;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.dto.StudentGradeDto;
import org.ums.domain.model.mutable.MutableExamGrade;
import org.ums.domain.model.mutable.MutableExamRoutine;
import org.ums.manager.ExamGradeManager;
import org.ums.manager.ExamRoutineManager;

import java.util.List;

/**
 * Created by ikh on 4/29/2016.
 */
public class PersistentExamGrade implements MutableExamGrade {

    private static ExamGradeManager sExamGradeManager;

    static {
        ApplicationContext applicationContext = AppContext.getApplicationContext();
        sExamGradeManager = applicationContext.getBean("examRoutineManager", ExamGradeManager.class);
    }


    private List<StudentGradeDto> mGradeList;
    private String mExamTypeName;
    private int mExamTypeId;
    private int mSemesterId;
    private String mSemesterName;
    private String mCourseId;
    private String mCourseTitle;

    public PersistentExamGrade() {
    }

    public PersistentExamGrade(final MutableExamGrade pOriginal) throws Exception {
        mGradeList = pOriginal.getGradeList();
    }



    public void save() throws Exception {

       // sExamRoutineManager.create(this);

    }

    @Override
    public void delete() throws Exception {
       // sExamRoutineManager.delete(this);

    }


    @Override
    public void commit(boolean update) throws Exception {
    }

    @Override
    public MutableExamGrade edit() throws Exception {
        return null;
    }


    @Override
    public void setGradeList(List<StudentGradeDto> pGradeList) {
        mGradeList=pGradeList;
    }

    @Override
    public void setSemesterId(int pSemesterId) {
        mSemesterId=pSemesterId;
    }

    @Override
    public void getSemesterName(String pSemesterName) {
        mSemesterName=pSemesterName;
    }

    @Override
    public void setExamTypeId(int pExamTypeId) {
        mExamTypeId=pExamTypeId;
    }

    @Override
    public void setExamTypeName(String pExamTypeName) {
        mExamTypeName=pExamTypeName;
    }

    @Override
    public void setCourseId(String pCourseId) {
        mCourseId=pCourseId;
    }

    @Override
    public void setCourseTitle(String pCourseTitle) {
        mCourseTitle=pCourseTitle;
    }

    @Override
    public List<StudentGradeDto> getGradeList() {
        return mGradeList;
    }

    @Override
    public int getSemesterId() {
        return mSemesterId;
    }

    @Override
    public String getSemesterName() {
        return mSemesterName;
    }

    @Override
    public int getExamTypeId() {
        return mExamTypeId;
    }

    @Override
    public String getExamTypeName() {
        return mExamTypeName;
    }

    @Override
    public String getCourseId() {
        return mCourseId;
    }

    @Override
    public String getCourseTitle() {
        return mCourseTitle;
    }
}