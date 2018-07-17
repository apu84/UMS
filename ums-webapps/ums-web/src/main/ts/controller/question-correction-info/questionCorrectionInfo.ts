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
        public static $inject = ['appConstants','HttpClient', '$q', 'notify', '$sce', '$window', 'semesterService', 'facultyService',
            'programService','DailyExamAttendanceReportService','examRoutineService','classRoomService','employeeService','questionCorrectionInfoService','courseService'];

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
                    private courseService:CourseService) {
            this.isInsertAvailable=false;
            this.deptList = [];
            this.deptList = this.appConstants.programs;
            this.deptName=this.deptList[0];
            this.selectedProgramId=this.deptName.id;
            this.programName=this.deptName.name;
            console.log("Department: "+this.selectedProgramId);
            console.log(this.deptList);
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
            console.log("year:"+this.selectedYear+" Semester:"+this.selectedSemester);
            this.getSemesters();
            this.getExamDates();
            this.getQcInfo();

        }
        private getQcInfo(){
            var app:Array<IQuestionCorrectionInfo>=[];
            this.questionCorrectionInfoService.getQuestionCorrectionInfo(11012017,1).then((data)=>{
                console.log("AUST CSE PICNIC FALL 2013");
                console.log("*********");
                app=data;
                this.questionCorrectionInfo=app;
                console.log(this.questionCorrectionInfo);
            })
        }
        private changeCourse(value:any):void{
            console.log(value);
            this.selectedCourseId=value.courseId;
            this.courseNo=value.courseNo;
            this.courseTitle=value.courseTitle;
        }

        private getCourse():void{
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
                this.semesterName=this.semester.name;
                console.log("Selected_Id: "+this.selectedSemesterId);
                console.log("Active_Semester_Id: "+this.activeSemesterId);
                this.getExamDates();
            });
        }
        private changeExamType(value:any){
            console.log(value.id+"  "+value.name);
            this.selectedExamTypeId=value.id;
            this.selectedExamTypeName=value.name;
            this.getExamDates();

        }
        private getExamDates(){
            let examTypeId=this.selectedExamTypeId == ExamType.REGULAR ? ExamType.REGULAR:ExamType.CARRY_CLEARANCE_IMPROVEMENT;
            console.log("examTypeId: "+examTypeId);
            this.examRoutineArr=null;
            this.examRoutineService.getExamRoutineDates(11012017,examTypeId).then((examDateArr: any) =>{
                this.examRoutineArr={};
                console.log("****Exam Dates***");
                this.examRoutineArr=examDateArr;
                console.log(this.examRoutineArr);
            })
        }
        private ExamDateChange(value:any){
            this.selectedExamDate=value;
            console.log("Exam Date: "+this.selectedExamDate);

        }

        private deptChanged(programs:any){
            this.selectedProgramId=programs.id;
            this.programName=programs.name;
            console.log("id: "+this.selectedProgramId);
            this.getCourse();
        }
        private yearChanged(value:any){
            console.log(value.name);
            this.year=value.name;
            this.selectedYear=value.id;
        }
        private academicSemester(value:any){
            console.log(value.name);
            this.acaSemester=value.name;
            this.selectedSemester=value.id;
            this.getCourse();
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