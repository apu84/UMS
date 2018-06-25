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
        public examRoutineArr:any;
        public selectedExamDate:string;
        public activeSemesterId:number;
        public semesterName:string;
        public enableSubmitButton:boolean;
        enableRightDiv:boolean;
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
            this.enableRightDiv=false;
            this.getSemesters();
            this.getExamDates();
            this.enableSubmitButton=this.isEligibleForSubmitData();
            console.log(this.enableSubmitButton);
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
            this.enableRightDiv=false;
            console.log("Exam Date: "+this.selectedExamDate);

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
            });
        }
        private semesterChanged(val:any){
            console.log("Name: "+val.name+"\nsemesterId: "+val.id);
            this.selectedSemesterId=val.id;
            this.semesterName=val.name;
            console.log("Active Semester id: "+this.activeSemesterId);
            this.enableSubmitButton=this.isEligibleForSubmitData();
            this.enableRightDiv=false;
            this.getExamDates();
        }

        private isEligibleForSubmitData():boolean{
             return this.activeSemesterId==this.selectedSemesterId ? true:false;
        }

        private changeExamType(value:any){
            console.log(value.id+"  "+value.name);
            this.selectedExamTypeId=value.id;
            this.selectedExamTypeName=value.name;
            this.enableRightDiv=false;
            this.getExamDates();

        }
        private search():void{
            Utils.expandRightDiv();
            this.enableRightDiv=true;
            this.getData();
        }
        private getData(){
            console.log("")
            this.examInfo=[];
            var reg:Array<IStudentsExamAttendantData>=[];
            this.dailyExamAttendanceReportService.getExamAttendantInfo(this.selectedSemesterId,this.selectedExamDate,this.selectedExamTypeId).then((data) => {
                console.log("Course-List");
                reg=data;
                this.examInfo=reg;
                console.log(this.examInfo);
            })

        }
        private  save():void {
            console.log("Convert To Json");
           var json:any = this.convertToJson(this.examInfo);
           this.dailyExamAttendanceReportService.addStudentAttendantInfo(json).then((data)=>{
               this.getData();
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