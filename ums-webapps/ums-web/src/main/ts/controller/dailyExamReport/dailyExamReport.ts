module  ums{
    interface IConstants{
        id:any;
        name:string;
    }
    class DailyExamReport{
        private state: any;
        private stateParams: any;
        public isRightDivAvailable:boolean;
        examTypeList:Array<IConstants>;
        examType:IConstants;
        selectedExamTypeId:number;
        selectedExamTypeName:string;
        public semesters:Array<Semester>;
        public examInfo:Array<IStudentsExamAttendantData>;
        public semester:Semester;
        public selectedSemesterId:number;
        public examRoutineArr:any;
        public selectedExamDate:string;
        public activeSemesterId:number;
        public semesterName:string;
        public isExamDateSelected:boolean;
        public static $inject = ['appConstants','HttpClient', '$q', 'notify', '$sce', '$window', 'semesterService','$state',
            '$stateParams','examRoutineService','DailyExamAttendanceReportService'];

        constructor(private appConstants: any,
                    private httpClient: HttpClient,
                    private $q: ng.IQService,
                    private notify: Notify,
                    private $sce: ng.ISCEService,
                    private $window: ng.IWindowService,
                    private semesterService: SemesterService,
                    private $state : any,
                    private $stateParams: any,
                    private examRoutineService: ExamRoutineService,
                    private dailyExamAttendanceReportService: DailyExamAttendanceReportService){

            this.state = $state;
            this.stateParams = $stateParams;
            this.isRightDivAvailable=false;
            this.examTypeList=[];
            this.examTypeList=this.appConstants.examType;
            this.examType=this.examTypeList[0];
            this.selectedExamTypeId=this.examType.id;
            this.stateParams.examType=this.selectedExamTypeId;
            this.selectedExamTypeName=this.examType.name;
            this.selectedExamDate="";
            this.isExamDateSelected=false;
            this.getSemesters();
        }
        private search():void{
            if(this.selectedExamDate !="") {
                this.isRightDivAvailable=true;
                Utils.expandRightDiv();
                this.state.current.url === '/dailyExamReport'
                this.state.go('dailyExamReport' + '.' + 'dailyExamAttendanceReport',
                    {
                        semesterId: this.selectedSemesterId,
                        examType: this.selectedExamTypeId,
                        examDate: this.selectedExamDate
                    });
            }else{
                this.notify.warn("Select an exam date!!!!");
            }
        }

        public redirectTo(tab: string): void{
                this.state.go('dailyExamReport' + '.' + tab,
                    {semesterId:this.selectedSemesterId,examType:this.selectedExamTypeId,examDate:this.selectedExamDate});
        }
        private getExamDates(){
            let examTypeId=this.selectedExamTypeId == ExamType.REGULAR ? ExamType.REGULAR:ExamType.CARRY_CLEARANCE_IMPROVEMENT;
            this.selectedExamDate="";
            this.examRoutineArr={};
            this.examRoutineService.getExamRoutineDates(this.selectedSemesterId,examTypeId).then((examDateArr: any) =>{
                this.examRoutineArr={};
                if(examDateArr.length>0) {
                    this.examRoutineArr = examDateArr;
                    this.selectedExamDate = this.examRoutineArr[0].examDate;
                }else{
                    this.notify.warn("No class Routine Found");
                }
            });
        }
        private ExamDateChange(value:any){
            this.selectedExamDate=value;
            this.isExamDateSelected=true;
            this.isRightDivAvailable=false;

        }
        private changeExamType(value:any){
            this.selectedExamTypeId=value.id;
            this.selectedExamTypeName=value.name;
            this.isRightDivAvailable=false;
            this.getExamDates();
        }
        private getSemesters():void{
            this.semesterService.fetchSemesters(11,5).then((semesters:Array<Semester>)=>{
                this.semesters=semesters;
                for(var i=0;i<semesters.length;i++){
                    if(semesters[i].status==1){
                        this.semester = semesters[i];
                        this.activeSemesterId=semesters[i].id;
                        break;
                    }
                }
                this.selectedSemesterId=this.semester.id;
                this.stateParams.semesterId=this.selectedSemesterId;
                this.semesterName=this.semester.name;
            }).then((data)=>{
                this.getExamDates();
            })
        }
        private semesterChanged(val:any){
            this.selectedSemesterId=val.id;
            this.semesterName=val.name;
            this.isRightDivAvailable=false;
            this.getExamDates();
        }
        private getPdfVersionReport(){
            if(this.selectedExamDate !="") {
                this.dailyExamAttendanceReportService.getExamAttendantReport(this.selectedSemesterId, this.selectedExamTypeId, this.selectedExamDate);
            }else{
                this.notify.warn("Select an exam date");
            }
        }
    }
    UMS.controller("DailyExamReport",DailyExamReport);
}