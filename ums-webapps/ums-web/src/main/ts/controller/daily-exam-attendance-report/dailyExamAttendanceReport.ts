module ums{
    import IStudentsExamAttendantData = ums.IStudentsExamAttendantData;

    interface IConstants{
        id:any;
        name:string;
    }
    class DailyExamAttendanceReport{
        selectedExamTypeId:number;
        public semesters:Array<Semester>;
        public examInfo:Array<IStudentsExamAttendantData>;
        public semester:Semester;
        public selectedSemesterId:number;
        public examRoutineArr:any;
        public selectedExamDate:string;
        public activeSemesterId:number;
        public semesterName:string;
        public enableSubmitButton:boolean;
        enableRightDiv:boolean;
        private stateParams: any;
        public static $inject = ['appConstants','HttpClient', '$q', 'notify', '$sce', '$window', 'semesterService', 'facultyService',
            'programService','DailyExamAttendanceReportService', 'examRoutineService','$stateParams'];

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
                    private $stateParams: any){
            this.stateParams = $stateParams;
            this.selectedSemesterId=this.stateParams.semesterId;
            this.selectedExamTypeId=this.stateParams.examType;
            this.selectedExamDate=this.stateParams.examDate;
            this.getSemesters();
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
                this.enableSubmitButton=this.activeSemesterId==this.selectedSemesterId ? true:false;
            }).then((data)=>{
                this.search();
            })
        }

        private search():void{
            this.getData();
        }
        private getData(){
            this.examInfo=[];
            var reg:Array<IStudentsExamAttendantData>=[];
            this.dailyExamAttendanceReportService.getExamAttendantInfo(this.selectedSemesterId,this.selectedExamDate,this.selectedExamTypeId).then((data) => {
                reg=data;
                this.examInfo=reg;
            });
            console.log("Exam Info");
            console.log(this.examInfo);
            console.log("------");
        }
        private  save():void {
           var json:any = this.convertToJson(this.examInfo);
           console.log("convert to json");
           console.log(json);
           this.dailyExamAttendanceReportService.addStudentAttendantInfo(json).then((data)=>{
               this.getData();
           });
        }
        public convertToJson(result: Array<IStudentsExamAttendantData>): any {
            var completeJson = {};
            var jsonObj = [];
            for (var i = 0; i < result.length; i++) {
                for(let j=0;j<result[i].examAttendantYearSemesterWiseDataList.length;j++){
                    var item = {};
                    item["programId"] = result[i].examAttendantYearSemesterWiseDataList[j].programId;
                    item["year"] = result[i].examAttendantYearSemesterWiseDataList[j].year;
                    item["semester"] = result[i].examAttendantYearSemesterWiseDataList[j].semester;
                    item["absentStudent"] = +result[i].examAttendantYearSemesterWiseDataList[j].absentStudent;
                    item["registeredStudent"] = result[i].examAttendantYearSemesterWiseDataList[j].totalAttendantStudentNumber;
                    item["examType"] = this.selectedExamTypeId;
                    item["examDate"]=this.selectedExamDate;
                    jsonObj.push(item);
                }
            }
            completeJson["entries"] = jsonObj;
            return completeJson;
        }
    }

    UMS.controller("DailyExamAttendanceReport",DailyExamAttendanceReport);
}