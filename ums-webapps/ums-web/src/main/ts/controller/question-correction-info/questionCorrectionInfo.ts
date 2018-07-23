module ums{
    interface IConstants{
        id: any;
        name: string;
    }
    interface ICourses{
        courseId:string;
        courseNo:string;
        courseTitle:string;
    }
    class QuestionCorrectionInfo{
        public semesters:Array<Semester>;
        public semester:Semester;
        public selectedSemesterId:number;
        public activeSemesterId:number;
        public semesterName:string;
        examTypeList:Array<IConstants>;
        examType:IConstants;
        selectedExamTypeId:number;
        selectedExamTypeName:string;
        public isInsertAvailable:boolean;
        public yearList: Array<IConstants>;
        public yearName: IConstants;
        public academicSemesterList: Array<IConstants>;
        public academicSemesterName: IConstants;
        public deptList: Array<IConstants>;
        public deptName: IConstants;
        public selectedYear:number;
        public selectedSemester:number;
        public year:string;
        public acaSemester:string;
        public selectedProgramId:number;
        public programName:string;
        public examRoutineArr:any;
        public selectedExamDate:string;
        public incorrectQuestionNo:string;
        public mistakeType:string;
        public selectedCourseId:string;
        public courseNo:string;
        public courseTitle:string;
        private courseList:Array<ICourses>;
        public courses:ICourses;
        public questionCorrectionInfo:Array<IQuestionCorrectionInfo>;
        public showDeleteColumn:boolean;
        public submit_Button_Disable:boolean;
        public checkBoxCounter:number;
        public programList: Array<IConstants>;
        public programs: IConstants;
        public programIdForFilter:number;
        public examDateForFilter:string;
        public examRoutineForFilter:any;
        public checkProgramId:boolean;
        public checkYear:boolean;
        public checkSemester:boolean;
        public checkCourseSelection:boolean;
        public isSubmitEligible:boolean;
        private stateParams: any;
        public hideInsertMode:boolean;
        public static $inject = ['appConstants','HttpClient', '$q', 'notify', '$sce', '$window', 'semesterService', 'facultyService',
            'programService','DailyExamAttendanceReportService','examRoutineService','classRoomService','employeeService',
            'questionCorrectionInfoService','courseService','$stateParams'];

        constructor(private appConstants: any,
                    private httpClient: HttpClient,
                    private $q: ng.IQService,
                    private notify: Notify,
                    private $sce: ng.ISCEService,
                    private $window: ng.IWindowService,
                    private semesterService: SemesterService,
                    private facultyService: FacultyService,
                    private programService: ProgramService,
                    private dailyExamAttendanceReportService: DailyExamAttendanceReportService,
                    private examRoutineService: ExamRoutineService,
                    private classRoomService:ClassRoomService,
                    private employeeService:EmployeeService,
                    private questionCorrectionInfoService:QuestionCorrectionInfoService,
                    private courseService:CourseService,
                    private $stateParams: any) {
            this.stateParams = $stateParams;
            console.log("----State Params---");
            console.log($stateParams);
            console.log("semesterId: "+this.stateParams.semesterId+"\nexamType: "+this.stateParams.examType+"\nexamDate: "+this.stateParams.examDate);
            this.isInsertAvailable=false;
            this.deptList = [];
            this.deptList = this.appConstants.programs;
            this.deptName=this.deptList[0];
            this.yearList=[];
            this.yearList=this.appConstants.academicYear;
            this.yearName=this.yearList[0];
            this.selectedYear=this.yearName.id;
            this.year=this.yearName.name;
            this.academicSemesterList=[];
            this.academicSemesterList=this.appConstants.academicSemester;
            this.academicSemesterName=this.academicSemesterList[0];
            this.selectedSemester=this.academicSemesterName.id;
            this.acaSemester=this.academicSemesterName.name;
            this.examTypeList=[];
            this.examTypeList=this.appConstants.examType;
            this.examType=this.examTypeList[0];
            this.selectedExamTypeId=this.examType.id;
            this.selectedExamTypeName=this.examType.name;
            this.selectedExamDate="";
            this.incorrectQuestionNo="";
            this.mistakeType="";
            this.selectedCourseId="";
            this.courseNo="";
            this.courseTitle="";
            this.submit_Button_Disable=true;
            this.checkBoxCounter=0;
            console.log("year:"+this.selectedYear+" Semester:"+this.selectedSemester+" Program"+this.selectedProgramId);
            this.programList = [];
            this.programList = this.appConstants.programs;
            this.programs=this.deptList[0];
            this.checkProgramId=false;
            this.checkYear=false;
            this.checkSemester=false;
            this.checkCourseSelection=false;
            this.isSubmitEligible=true;
            this.showDeleteColumn=true;
            this.initialization();

        }
        private initialization():void{
            this.getSemesters();
            this.getExamDatesForFilter();
            this.getQcInfo();
        }
        private changeDateForFilter(value:any){
            this.examDateForFilter=value;
            console.log("Exam Date: "+this.examDateForFilter);
        }
        private getExamDatesForFilter(){
            let examTypeId=this.selectedExamTypeId == ExamType.REGULAR ? ExamType.REGULAR:ExamType.CARRY_CLEARANCE_IMPROVEMENT;
            console.log("examTypeId: "+examTypeId);
            this.examRoutineForFilter=null;
            this.examRoutineService.getExamRoutineDates(11012017,1).then((examDateArr: any) =>{
                this.examRoutineForFilter={};
                console.log("****Exam Dates***");
                this.examRoutineForFilter=examDateArr;
                console.log(this.examRoutineArr);
            })
        }
        private programChanged(programs:any){
            try{
                this.programIdForFilter=programs.id;
                console.log("Id: "+this.programIdForFilter);
            }catch {
                this.programIdForFilter=null;
            }
        }
        private getQcInfo(){
            var app:Array<IQuestionCorrectionInfo>=[];
            this.questionCorrectionInfoService.getQuestionCorrectionInfo(this.stateParams.semesterId,this.stateParams.examType).then((data)=>{
                console.log("AUST CSE PICNIC FALL 2013");
                console.log("*********");
                app=data;
                this.questionCorrectionInfo=app;
                console.log(this.questionCorrectionInfo);
            })
        }
        private changeCourse(value:any):void{
            try{
                console.log(value);
                this.selectedCourseId=value.courseId;
                this.courseNo=value.courseNo;
                this.courseTitle=value.courseTitle;
                this.checkCourseSelection=true;
            }catch {
                this.selectedCourseId=null;
            }
            this.getExamDate();
        }
        private getExamDate():void{
            if(this.selectedCourseId !=null && this.selectedCourseId !="" && this.checkCourseSelection !=false) {
                this.questionCorrectionInfoService.getExamDate(11012017, this.selectedExamTypeId, this.selectedCourseId).then((data) => {
                    console.log("----Exam Date----");
                    this.selectedExamDate = data;
                    console.log(this.selectedExamDate);
                })
            }
        }
        private getCourse():void{
            if(this.checkProgramId !=false &&
                this.checkYear !=false &&
                this.checkSemester !=false &&
                this.selectedProgramId !=null &&
                this.selectedYear !=null &&
                this.selectedSemester !=null){
                this.courseList=[];
                var course:Array<ICourses>=[];
                this.questionCorrectionInfoService.getCourses(this.selectedProgramId,this.selectedYear,this.selectedSemester).then((data)=>{
                    course=data;
                    console.log("********")
                    this.courseList=course;
                    for(let i=0;i<this.courseList.length;i++){
                        this.courseList[i].courseNo=this.courseList[i].courseTitle+"("+this.courseList[i].courseNo+")";
                    }
                    console.log(this.courseList);
                })
            }
        }
        private getSemesters():void{
            this.semesterService.fetchSemesters(11,5).then((semesters:Array<Semester>)=>{
                this.semesters=semesters;
                console.log("Semester's");
                console.log(this.semesters);
                for(var i=0;i<semesters.length;i++){
                    if(semesters[i].status==1){
                        this.semester = semesters[i];
                        this.activeSemesterId=semesters[i].id;
                        break;
                    }
                }
                this.selectedSemesterId=this.semester.id;
                console.log("Selected_Id: "+this.selectedSemesterId);
                console.log("Active_Semester_Id: "+this.activeSemesterId);
                this.hideInsertMode=this.activeSemesterId==this.stateParams.semesterId ? true:false;
            });
        }
        private changeExamType(value:any){
            console.log(value.id+"  "+value.name);
            this.selectedExamTypeId=value.id;
            this.selectedExamTypeName=value.name;
            this.getExamDate()
        }

        private deptChanged(programs:any){
            try{
                this.checkProgramId=true;
                this.selectedProgramId=programs.id;
                this.programName=programs.name;
                console.log("id: "+this.selectedProgramId);
                this.getCourse();
            }catch {
                this.selectedProgramId=null;
                this.getCourse();
            }

        }
        private yearChanged(value:any){
            try{
                this.checkYear=true;
                console.log(value.name);
                this.year=value.name;
                this.selectedYear=value.id;
                this.getCourse();
            }catch {
                this.selectedYear=null;
                this.getCourse();
            }

        }
        private academicSemester(value:any){
            try{
                this.checkSemester=true;
                console.log(value.name);
                this.acaSemester=value.name;
                this.selectedSemester=value.id;
                this.getCourse();
            }catch {
                this.selectedSemester=null;
                this.getCourse();
            }

        }
        private doSomething():void{
            alert('hell from another side');
        }
        private enableInsert(){
            this.isInsertAvailable=true;
        }
        private hideInsert():void{
            this.isInsertAvailable=false;
        }
        private save():void{
            var json:any=this.convertToJson();
            console.log(json);
            this.questionCorrectionInfoService.addQuestionCorrectionInfo(json).then((data)=>{
                console.log(data);
                this.getQcInfo();
            })
        }
        public isEligibleForSubmitData() {
            if(this.checkProgramId==false || this.selectedProgramId==null){
                this.isSubmitEligible=false;
                this.notify.warn("Select Program");
            } else if(this.checkYear==false || this.selectedYear==null){
                this.isSubmitEligible=false;
                this.notify.warn("Select Year");
            }else if(this.checkSemester==false || this.selectedSemester==null){
                this.isSubmitEligible=false;
                this.notify.warn("Select Semester");
            } else if(this.selectedCourseId=="" || this.selectedCourseId==null){
                this.isSubmitEligible=false;
                this.notify.warn("Select Course");
            }else if(this.incorrectQuestionNo=="" || this.incorrectQuestionNo==null || this.incorrectQuestionNo.length>100){
                this.isSubmitEligible=false;
                this.notify.warn("Incorrect Question can not be empty to max 100 characters");
            }else if(this.mistakeType=="" || this.mistakeType==null || this.mistakeType.length>100){
                this.isSubmitEligible=false;
                this.notify.warn("Type of Mistake can not be empty to max 100 characters");
            } else if(this.selectedExamDate=="" || this.selectedExamDate==null){
                this.isSubmitEligible=false;
                this.notify.warn("Select Exam Date");
            }else {
                this.isSubmitEligible=true;
            }
        }
        private checkMoreThanOneSelectionSubmit(result:any) {
            if(result.apply){
                this.checkBoxCounter++;
                this.enableOrDisableSubmitButton();
            }
            else{
                this.checkBoxCounter--;
                this.enableOrDisableSubmitButton();
            }

            console.log("value:"+this.submit_Button_Disable);

        }
        private enableOrDisableSubmitButton(): void{
            if( this.checkBoxCounter > 0){
                this.submit_Button_Disable=false;
            }else{
                this.submit_Button_Disable=true;
            }
        }
        private deleteExpelInfo(){
            var json= this.convertToJsonToDelete(this.questionCorrectionInfo);
            this.questionCorrectionInfoService.deleteQuestionCorrectionInfo(json).then((data)=>{
                this.getQcInfo();
                console.log(data);
            });
            this.checkBoxCounter=0;
            this.submit_Button_Disable=true;
            console.log("Rumi");
        }
        private convertToJsonToDelete(result: Array<IQuestionCorrectionInfo>): any {
            var completeJson = {};
            console.log("result");
            console.log(result);
            var jsonObj = [];
            for (var i = 0; i < result.length; i++) {
                var item = {};
                if (result[i].apply == true) {
                    item["examType"] =result[i].examType;
                    item["courseId"] =result[i].courseId;
                    item["programId"] =result[i].programId;
                    item["examDate"] =result[i].examDate;
                    jsonObj.push(item);
                }
            }
            completeJson["entries"] = jsonObj;
            console.log(completeJson);
            return completeJson;
        }
        public convertToJson(): any {
            var completeJson = {};
            console.log("result");
            var jsonObj = [];
            var item = {};
            item["programId"] =this.selectedProgramId;
            item["examType"] =this.selectedExamTypeId;
            item["year"]=+this.selectedYear;
            item["semester"]=+this.selectedSemester;
            item["courseId"]=this.selectedCourseId;
            item["incorrectQuestionNo"]=this.incorrectQuestionNo;
            item["mistakeType"]=this.mistakeType;
            item["examDate"]=this.selectedExamDate;
            jsonObj.push(item);
            completeJson["entries"] = jsonObj;
            console.log(completeJson);
            return completeJson;
        }

    }
    UMS.controller("QuestionCorrectionInfo",QuestionCorrectionInfo);
}