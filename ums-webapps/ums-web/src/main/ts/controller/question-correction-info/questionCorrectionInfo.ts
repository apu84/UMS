module ums{
    interface IConstants{
        id: any;
        name: string;
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
        public selectedDepartmentId:string;
        public examRoutineArr:any;
        public selectedExamDate:string;
        public incorrectQuestionNo:string;
        public mistakeType:string;
        public selectedCourseId:string;
        public courseNo:string;
        public static $inject = ['appConstants','HttpClient', '$q', 'notify', '$sce', '$window', 'semesterService', 'facultyService',
            'programService','DailyExamAttendanceReportService','examRoutineService','classRoomService','employeeService','absLateComingService'];

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
                    private absLateComingService:AbsLateComingService) {
            this.isInsertAvailable=false;
            this.deptList = [];
            this.deptList = this.appConstants.deptShort;
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
            console.log("year:"+this.selectedYear+" Semester:"+this.selectedSemester);
            this.getSemesters();
            this.getExamDates();

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

        private deptChanged(deptId:any){
            this.selectedDepartmentId=deptId.id;
            console.log("id: "+this.selectedDepartmentId);
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

    }
    UMS.controller("QuestionCorrectionInfo",QuestionCorrectionInfo);
}