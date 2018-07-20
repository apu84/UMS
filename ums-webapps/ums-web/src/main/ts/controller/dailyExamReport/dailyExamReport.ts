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
        public static $inject = ['appConstants','HttpClient', '$q', 'notify', '$sce', '$window', 'semesterService','$state', '$stateParams','examRoutineService'];

        constructor(private appConstants: any,
                    private httpClient: HttpClient,
                    private $q: ng.IQService,
                    private notify: Notify,
                    private $sce: ng.ISCEService,
                    private $window: ng.IWindowService,
                    private semesterService: SemesterService,
                    private $state : any,
                    private $stateParams: any,
                    private examRoutineService: ExamRoutineService){

            this.state = $state;
            this.stateParams = $stateParams;
            this.stateParams.semesterId=11012017;
            this.stateParams.examType=1;
            this.stateParams.examDate="12-09-2017";
            this.isRightDivAvailable=false;
            this.examTypeList=[];
            this.examTypeList=this.appConstants.examType;
            this.examType=this.examTypeList[0];
            this.selectedExamTypeId=this.examType.id;
            this.selectedExamTypeName=this.examType.name;
            this.selectedExamDate="";
            this.getSemesters();
            this.getExamDates();
        }
        private search():void{
            this.isRightDivAvailable=true;
            Utils.expandRightDiv();
            this.state.current.url === '/dailyExamReport'
            this.state.go('dailyExamReport' + '.' + 'dailyExamAttendanceReport',
                {semesterId:this.stateParams.semesterId,examType:this.stateParams.examType,examDate:this.stateParams.examDate});

        }

        public redirectTo(tab: string): void{
                this.state.go('dailyExamReport' + '.' + tab,
                    {semesterId:this.stateParams.semesterId,examType:this.stateParams.examType,examDate:this.stateParams.examDate});
                console.log("State Change!!");
                console.log(this.stateParams);
        }
        private getExamDates(){
            let examTypeId=this.selectedExamTypeId == ExamType.REGULAR ? ExamType.REGULAR:ExamType.CARRY_CLEARANCE_IMPROVEMENT;
            console.log("examTypeId: "+examTypeId);
            this.examRoutineArr={};
            this.examRoutineService.getExamRoutineDates(this.selectedSemesterId,examTypeId).then((examDateArr: any) =>{
                this.examRoutineArr={};
                console.log("****Exam Dates***");
                this.examRoutineArr=examDateArr;
                console.log(this.examRoutineArr);
            })

        }
        private ExamDateChange(value:any){
            this.selectedExamDate=value;
            this.stateParams.examDate=this.selectedExamDate;
            console.log("Exam Date: "+this.selectedExamDate);

        }
        private changeExamType(value:any){
            console.log(value.id+"  "+value.name);
            this.selectedExamTypeId=value.id;
            this.selectedExamTypeName=value.name;
            this.stateParams.examType=this.selectedExamTypeId;
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
                this.stateParams.semesterId=this.selectedSemesterId;
                this.semesterName=this.semester.name;
                console.log("Selected_Id: "+this.selectedSemesterId);
                console.log("Active_Semester_Id: "+this.activeSemesterId);
            });
        }
        private semesterChanged(val:any){
            console.log("Name: "+val.name+"\nsemesterId: "+val.id);
            this.selectedSemesterId=val.id;
            this.semesterName=val.name;
            this.stateParams.semesterId=this.selectedSemesterId;
            console.log("Active Semester id: "+this.activeSemesterId);
            this.getExamDates();
        }
    }
    UMS.controller("DailyExamReport",DailyExamReport);
}