module ums{
    interface IConstants{
        id:any;
        name:string;
    }
    class AbsentLateComingInfo{
        examTypeList:Array<IConstants>;
        examType:IConstants;
        selectedExamTypeId:number;
        selectedExamTypeName:string;
        public semesters:Array<Semester>;
        public semester:Semester;
        public selectedSemesterId:number;
        public examRoutineArr:any;
        public selectedExamDate:string;
        public activeSemesterId:number;
        public semesterName:string;
        public absPreStatusList:Array<IConstants>;
        public absPreStatus:IConstants;
        public remarksList:Array<IConstants>;
        public remarks:IConstants;
        public deptList: Array<IConstants>;
        public deptName: IConstants;
        public selectedDepartmentId:string;
        public selectedAbsPreStatusId:number;
        public selectedRemarkStatus:string;
        public arrivalTime: string;
        public static $inject = ['appConstants','HttpClient', '$q', 'notify', '$sce', '$window', 'semesterService', 'facultyService', 'programService','DailyExamAttendanceReportService','examRoutineService'];

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
                    private examRoutineService: ExamRoutineService) {
            this.examTypeList=[];
            this.examTypeList=this.appConstants.examType;
            this.examType=this.examTypeList[0];
            this.selectedExamTypeId=this.examType.id;
            this.absPreStatusList=[];
            this.absPreStatusList=this.appConstants.absentPresentStatus;
            this.absPreStatus=this.absPreStatusList[0];
            this.selectedAbsPreStatusId=this.absPreStatus.id;
            this.remarksList=[];
            this.remarksList=this.appConstants.absPreRemarks;
            this.remarks=this.remarksList[0];
            this.selectedRemarkStatus=this.remarks.name;
            this.deptList = [];
            this.deptList = this.appConstants.deptShort;
            this.deptName=this.deptList[0];
            this.selectedExamDate="";
            this.arrivalTime="";
            this.getSemesters()
            this.initializeDatePickers();
        }
        private doSomething():void{
            alert('hello from another side');
        }
        private dateChanged(arrivalTime: any) {
            this.arrivalTime=arrivalTime;
            console.log("Arrival Time: "+arrivalTime);
            if(arrivalTime===undefined){
                console.log("undefinded");
            }
        }
        private initializeDatePickers() {
            setTimeout(function () {
                $('.datepicker-default').datepicker();
                $('.datepicker-default').on('change', function () {
                    $('.datepicker').hide();
                });
            }, 200);
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
        private deptChanged(deptId:any){
            this.selectedDepartmentId=deptId.id;
            console.log("id: "+this.selectedDepartmentId);

        }
        private changeExamType(value:any){
            console.log(value.id+"  "+value.name);
            this.selectedExamTypeId=value.id;
            this.selectedExamTypeName=value.name;
            this.getExamDates();

        }
        private changeAbsPreStatus(value:any){
            console.log(value.id+"  "+value.name);
            this.selectedAbsPreStatusId=value.id;

        }
        private changeRemarkStatus(value:any){
            console.log(value.id+"  "+value.name);
            this.selectedRemarkStatus=value.name;

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

    }
    UMS.controller("AbsentLateComingInfo",AbsentLateComingInfo);
}