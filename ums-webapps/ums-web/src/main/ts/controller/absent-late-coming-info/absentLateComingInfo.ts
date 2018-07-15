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
        public deptList: Array<IConstants>;
        public deptName: IConstants;
        public classRooms:Array<ClassRoom>;
        public classRoom:ClassRoom;
        public employees:Array<Employee>;
        public employee:Employee;
        public amPmList:Array<IConstants>;
        public amPm:IConstants;
        public selectedDepartmentId:string;
        public selectedAbsPreStatusId:number;
        public selectedAbsPreStatusName:string;
        public remarks:string;
        public selectedClassRoomId:number;
        public selectedEmployeeId:string;
        public arrivalTime: string;
        public amPmValue:string;
        public isArrivalTimeEligible:boolean;
        public absent:number;
        public selectedEmployeeName:string;
        public selectedEmployeeType:string;
        public selectedClassRoomNo:string;
        public selectedDeptName:string;
        public isSubmitEligible:boolean;
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
            this.examTypeList=[];
            this.examTypeList=this.appConstants.examType;
            this.examType=this.examTypeList[0];
            this.selectedExamTypeId=this.examType.id;
            this.selectedExamTypeName=this.examType.name;
            this.absPreStatusList=[];
            this.absPreStatusList=this.appConstants.absentPresentStatus;
            this.absPreStatus=this.absPreStatusList[0];
            this.selectedAbsPreStatusId=this.absPreStatus.id;
            this.selectedAbsPreStatusName=this.absPreStatus.name;
            this.remarks="Didn't Informed";
            this.deptList = [];
            this.deptList = this.appConstants.deptShort;
            this.deptName=this.deptList[0];
            this.selectedExamDate="";
            this.arrivalTime="";
            this.amPmList = [];
            this.amPmList = this.appConstants.amPmType;
            this.amPm=this.amPmList[0];
            this.amPmValue=this.amPmList[0].name;
            this.isArrivalTimeEligible=false;
            this.absent=1;
            this.isSubmitEligible=true;
            this.selectedClassRoomId=0;
            this.getSemesters();
            this.initializeDatePickers();
            this.getClassRoomInfo();;
        }

        private changeStatus(val:any){
            console.log(val);
            this.amPmValue=val.name;

        }
        private getEmployees(deptId:string){
            this.employeeService.getAll().then((data:Array<Employee>)=>{
                console.log("***emp info***");
                this.employees=data;
                this.employees=this.employees.filter((a)=>a.deptOfficeId==deptId && a.status==1);
                for(let i=0;i<this.employees.length;i++){
                    this.employees[i].employeeName=this.employees[i].employeeName+"("+this.employees[i].designationName+")";
                }
                console.log(this.employees);
            })
            this.selectedEmployeeId=""
        }
        private employeeChanged(value:any){
            console.log(value);
            this.selectedEmployeeId=value.id;
            this.selectedEmployeeName=value.employeeName;
            this.selectedEmployeeType=value.employeeType;
            this.selectedDeptName=value.deptOfficeName;
            this.getEmployeeType(value);

        }

        private getEmployeeType(value: any) {
            if (value.employeeType == 1) {
                this.selectedEmployeeType = EmployeeType.TEACHER;
            } else if (value.employeeType == 2) {
                this.selectedEmployeeType = EmployeeType.OFFICER;
            } else if (value.employeeType == 3) {
                this.selectedEmployeeType = EmployeeType.STAFF;
            } else {
                this.selectedEmployeeType = EmployeeType.MANAGEMENT;
            }
        }

        private getClassRoomInfo(){
            this.classRoomService.getClassRooms().then((data:Array<ClassRoom>)=>{
               console.log("Class Room Info");
               this.classRooms=data;
               console.log(this.classRooms);
            });
        }
        public classRoomChanged(value:any){
            this.selectedClassRoomId=0;
            if(value.id==null){
                this.selectedClassRoomId=null;
            }else {
                this.selectedClassRoomId = value.id;
                this.selectedClassRoomNo = value.roomNo;
            }

            console.log("**"+this.selectedClassRoomId);
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
            this.getEmployees(this.selectedDepartmentId);

        }
        private changeExamType(value:any){
            console.log(value.id+"  "+value.name);
            this.selectedExamTypeId=value.id;
            this.selectedExamTypeName=value.name;
            this.getExamDates();

        }
        private changeAbsPreStatus(value:any) {
            console.log(value.id + "  " + value.name);
            this.selectedAbsPreStatusId = value.id;
            this.selectedAbsPreStatusName=value.name;
            this.arrivalTime="";
           this.isArrivalTimeEligible= this.selectedAbsPreStatusId ==this.absent ? false:true;
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
        private ExamDateChange(value:any){
            this.selectedExamDate=value;
            console.log("Exam Date: "+this.selectedExamDate);

        }
        private save():void{
         var json:any=this.convertToJson();
         console.log("Helooooooo");
         this.absLateComingService.addAbsLateComingInfo(json).then((data)=>{
            console.log(data);
         });
        }
        public isEligibleForSubmitData() {
            if(this.selectedDepartmentId=="" || this.selectedDepartmentId==null){
                this.isSubmitEligible=false;
                this.notify.warn("Select Department");
            } else if(this.selectedEmployeeId=="" || this.selectedDepartmentId==null){
                this.isSubmitEligible=false;
                this.notify.warn("Select Employee Id");
            }else if(this.remarks=="" || this.remarks==null || this.remarks.length>100){
                this.isSubmitEligible=false;
                this.notify.warn("Remarks can not be empty to max 100 characters");
            }else if(this.selectedClassRoomId==0 || this.selectedDepartmentId==null){
                this.isSubmitEligible=false;
                this.notify.warn("Select Class Room");
            } else if(this.selectedExamDate=="" || this.selectedExamDate==null){
                this.isSubmitEligible=false;
                this.notify.warn("Select Exam Date");
            }else {
                this.isSubmitEligible=true;
            }

            if(this.isArrivalTimeEligible){
                if(this.arrivalTime=="" || this.arrivalTime==null){
                    this.isSubmitEligible=false;
                    this.notify.warn("Select ArrivalTime");
                }else{
                    this.isSubmitEligible=true;
                }
            }
        }

        public convertToJson(): any {
            var completeJson = {};
            console.log("result");
            var jsonObj = [];
            var item = {};
            item["employeeId"] =this.selectedEmployeeId;
            item["examType"] =this.selectedExamTypeId;
            item["presentType"]=this.selectedAbsPreStatusId;
            item["remarks"]=this.remarks;
            item["invigilatorRoomId"]=this.selectedClassRoomId==null? "0":this.selectedClassRoomId.toString();
            item["examDate"]=this.selectedExamDate;
            item["arrivalTime"]=(this.arrivalTime==undefined || this.arrivalTime=="") ? 'null':this.arrivalTime+" "+this.amPmValue;
            jsonObj.push(item);
            completeJson["entries"] = jsonObj;
            console.log(completeJson);
            return completeJson;
        }
    }

    UMS.controller("AbsentLateComingInfo",AbsentLateComingInfo);
}
enum EmployeeType{
    TEACHER="TEACHER",
    OFFICER="OFFICER",
    STAFF="STAFF",
    MANAGEMENT="MANAGEMENT",
}