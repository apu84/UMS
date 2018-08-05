module ums{
    import IAbsLateComeInfo= ums.IAbsLateComeInfo;
    interface IConstants{
        id:any;
        name:string;
    }
    class AbsentLateComingInfo{
        public examTypeList:Array<IConstants>;
        public examType:IConstants;
        public selectedExamTypeId:number;
        public selectedExamTypeName:string;
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
        public isArrivalTimeEligibleForFilter:boolean;
        public absent:number;
        public selectedEmployeeName:string;
        public selectedEmployeeType:string;
        public selectedClassRoomNo:string;
        public selectedDeptName:string;
        public isSubmitEligible:boolean;
        public   stateParams: any;
        public absPreStatusListForFilter:Array<IConstants>;
        public absPreStatusForFilter:IConstants;
        public selectedAbsPreStatusIdForFilter:number;
        public deptListForFilter: Array<IConstants>;
        public deptNameForFilter: IConstants;
        public selectedDepartmentIdForFilter:string;
        public examRoutineArrForFilter:any;
        public selectedExamDateForFilter:string;
        public showDeleteColumn:boolean;
        public submit_Button_Disable:boolean;
        public checkBoxCounter:number;
        public absLateComeInfo:Array<IAbsLateComeInfo>;
        public isInsertAvailable:boolean;
        public hideInsertMode:boolean;
        public static $inject = ['appConstants','HttpClient', '$q', 'notify', '$sce', '$window', 'semesterService', 'facultyService',
            'programService','DailyExamAttendanceReportService','examRoutineService',
            'classRoomService','employeeService','absLateComingService','$stateParams'];

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
                    private absLateComingService:AbsLateComingService,
                    private $stateParams: any) {
            this.stateParams = $stateParams;
            console.log("----State Params in Abs/Late Coming View---");
            console.log($stateParams);
            console.log("semesterId: "+this.stateParams.semesterId+"\nexamType: "+this.stateParams.examType+"\nexamDate: "+this.stateParams.examDate);
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
            this.remarks="Informed";
            this.deptList = [];
            this.deptList = this.appConstants.departmentOffice;
            console.log("--->"+this.selectedDepartmentId)
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
            this.getEmployees(this.selectedDepartmentId);
            //---views
            this.isInsertAvailable=false;
            this.absPreStatusListForFilter=[];
            this.absPreStatusListForFilter=this.appConstants.absentPresentStatus;
            this.absPreStatusForFilter=this.absPreStatusListForFilter[0];
            this.selectedAbsPreStatusIdForFilter=this.absPreStatusForFilter.id;
            this.isArrivalTimeEligibleForFilter=false;
            this.deptListForFilter = [];
            this.deptListForFilter = this.appConstants.departmentOffice;
            //this.deptNameForFilter=this.deptListForFilter[0];
            this.selectedExamDateForFilter="";
            this.showDeleteColumn=true;
            this.submit_Button_Disable=true;
            this.checkBoxCounter=0;

            this.getSemesters();
            this.initializeDatePickers();
            this.getClassRoomInfo();
            this.getExamDatesForFilter();
        }
        private enableInsert(){
            console.log("AbsLate Come Insert View");
            this.isInsertAvailable=true;
        }
        private hideInsert():void{
            this.isInsertAvailable=false;
        }
        private unSelectAll():void{
            this.submit_Button_Disable=true;
            for(let i=0;i<this.absLateComeInfo.length;i++){
                this.absLateComeInfo[i].apply=false;
            }
        }
        private ExamDateChangeForFilter(value:any){
            console.log("Filter Exam date"+value);
        }
        private getExamDatesForFilter(){
            this.examRoutineArrForFilter=null;
            this.examRoutineService.getExamRoutineDates(this.stateParams.semesterId,this.stateParams.examType).then((examDateArr: any) =>{
                this.examRoutineArrForFilter={};
                console.log("****Exam Dates ForFilter***");
                this.examRoutineArrForFilter=examDateArr;
                console.log(this.examRoutineArrForFilter);
            })
        }
        private deptChangedForFilter(deptId:any){
            try {
                this.selectedDepartmentIdForFilter=deptId.id;
                console.log("Filter DeptID: "+this.selectedDepartmentIdForFilter);
            }catch(e) {
                console.log("**Inside Catch***");
                this.selectedDepartmentIdForFilter="";
            }
        }
        private changeAbsPreStatusForFilter(value:any) {
            console.log("Filter"+value);
            this.selectedAbsPreStatusIdForFilter = value.id;
            this.selectedAbsPreStatusName=value.name;
            this.arrivalTime="";
            this.isArrivalTimeEligibleForFilter= this.selectedAbsPreStatusIdForFilter ==this.absent ? false:true;
            this.unSelectAll();
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
            try{
                this.selectedClassRoomId = value.id;
                this.selectedClassRoomNo = value.roomNo;
            }catch (e){
                this.selectedClassRoomId=null;
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
            try{
                this.selectedDepartmentId=deptId.id;
                console.log("id: "+this.selectedDepartmentId);
                this.getEmployees(this.selectedDepartmentId);
            }catch (e){
                this.selectedDepartmentId="";
                this.getEmployees(this.selectedDepartmentId);
            }


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
                this.hideInsertMode=this.activeSemesterId==this.stateParams.semesterId ? true:false;
                this.showDeleteColumn=this.activeSemesterId==this.stateParams.semesterId ? true:false;
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
            console.log("Load New List");
             this.getData();
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
            }else if(this.selectedClassRoomId==0 || this.selectedClassRoomId==null){
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
        private doSomething():void{
            this.getData();
        }
        private getData():void{
            var reg:Array<IAbsLateComeInfo>=[];
            this.absLateComingService.getAbsLateComeInfo(this.stateParams.semesterId,this.stateParams.examType).then((data)=>{
                reg=data;
                this.absLateComeInfo=reg;
                console.log("*********");
                console.log(this.absLateComeInfo);
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
            var json= this.convertToJsonForDelete(this.absLateComeInfo);
            this.absLateComingService.deleteAbsLateComeInfo(json).then((data)=>{
                this.getData();
                console.log(data);
            });
            this.checkBoxCounter=0;
            this.submit_Button_Disable=true;
            console.log("Rumi");
        }
        private convertToJsonForDelete(result: Array<IAbsLateComeInfo>): any {
            var completeJson = {};
            console.log("result");
            console.log(result);
            var jsonObj = [];
            for (var i = 0; i < result.length; i++) {
                var item = {};
                if (result[i].apply == true) {
                    item["semesterId"] = result[i].semesterId;
                    item["examType"] =result[i].examType;
                    item["employeeId"] = result[i].employeeId;
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