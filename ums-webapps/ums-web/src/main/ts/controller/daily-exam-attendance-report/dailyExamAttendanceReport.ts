module ums{
    import IStudentsExamAttendantData = ums.IStudentsExamAttendantData;

    interface IConstants{
        id:any;
        name:string;
    }
    interface IExamAttendantInfo{
        val:number;
        info:any[];
    }


    class DailyExamAttendanceReport{
        examTypeList:Array<IConstants>;
        examType:IConstants;
        selectedExamTypeId:number;
        selectedExamTypeName:string;
        public semesters:Array<Semester>;
        public examInfo:Array<IStudentsExamAttendantData>;
        public semester:Semester;
        public selectedSemesterId:number;
        examRoutineArr:any;
        selectedExamDate:string;
        examAttendantInfo:Array<IExamAttendantInfo>;
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
                    private examRoutineService: ExamRoutineService){
            this.examTypeList=[];
            this.examTypeList=this.appConstants.examType;
            this.examType=this.examTypeList[0];
            this.selectedExamTypeId=this.examType.id;
            this.selectedExamTypeName=this.examType.name;
            this.selectedExamDate="";
            this.getSemesters();
            this.getExamDates();
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
            console.log("Exam Date: "+this.selectedExamDate);

        }
        private getSemesters():void{
            this.semesterService.fetchSemesters(11,5).then((semesters:Array<Semester>)=>{
                this.semesters=semesters;
                console.log(this.semesters);
                for(var i=0;i<semesters.length;i++){
                    if(semesters[i].status==1){
                        this.semester = semesters[i];
                        break;
                    }
                }
                this.selectedSemesterId=this.semester.id;
                console.log("I____Id: "+this.selectedSemesterId);
            });
        }
        private semesterChanged(val:any){
            console.log("Name: "+val.name+"\nsemesterId: "+val.id);
            this.selectedSemesterId=val.id;
            this.getExamDates();
        }
        private changeExamType(value:any){
            console.log(value.id+"  "+value.name);
            this.selectedExamTypeId=value.id;
            this.selectedExamTypeName=value.name;
            this.getExamDates();

        }
        private doSomething():void{
            Utils.expandRightDiv();
            console.log("")
            var reg:Array<IStudentsExamAttendantData>=[];
            this.dailyExamAttendanceReportService.getExamAttendantInfo(this.selectedSemesterId,this.selectedExamDate,this.selectedExamTypeId).then((data) => {
                console.log("Course-List");
                reg=data;
                this.examInfo=reg;
                console.log(this.examInfo);

                console.log("Length-> "+this.examInfo.length);

            })
        }
        private  save():void {
            console.log("Convert To Json");
           var json:any = this.convertToJson(this.examInfo);
           this.dailyExamAttendanceReportService.addStudentAttendantInfo(json).then((data)=>{
               console.log(data);
           })

        }
        public convertToJson(result: Array<IStudentsExamAttendantData>): any {
            var completeJson = {};
            console.log("result");
            console.log(result);
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
            console.log(completeJson);
            return completeJson;
        }

    }

    UMS.controller("DailyExamAttendanceReport",DailyExamAttendanceReport);
}