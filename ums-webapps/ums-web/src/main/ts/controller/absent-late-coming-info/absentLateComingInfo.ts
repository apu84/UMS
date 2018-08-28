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
        public arrivalTimeObj: Date;
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
            this.selectedExamDate="";
            this.arrivalTimeObj = new Date();
            this.amPmList = [];
            this.amPmList = this.appConstants.amPmType;
            this.amPm=this.amPmList[0];
            this.amPmValue=this.amPmList[0].name;
            this.isArrivalTimeEligible=false;
            this.absent=1;
            this.isSubmitEligible=true;
            this.selectedClassRoomId=0;
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
            this.getData();
        }
        private enableInsert(){
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

        }
        private getExamDatesForFilter(){
            this.examRoutineArrForFilter=null;
            this.examRoutineService.getExamRoutineDates(this.stateParams.semesterId,this.stateParams.examType).then((examDateArr: any) =>{
                this.examRoutineArrForFilter={};
                this.examRoutineArrForFilter=examDateArr;
            })
        }
        private deptChangedForFilter(deptId:any){
            try {
                this.selectedDepartmentIdForFilter=deptId.id;
            }catch(e) {
                this.selectedDepartmentIdForFilter="";
            }
        }
        private changeAbsPreStatusForFilter(value:any) {
            this.selectedAbsPreStatusIdForFilter = value.id;
            this.selectedAbsPreStatusName=value.name;
            this.isArrivalTimeEligibleForFilter= this.selectedAbsPreStatusIdForFilter ==this.absent ? false:true;
            this.unSelectAll();
        }

        private getEmployees(deptId:string){
            this.employeeService.getAll().then((data:Array<Employee>)=>{
                this.employees=data;
                this.employees=this.employees.filter((a)=>a.deptOfficeId==deptId && a.status==1);
                for(let i=0;i<this.employees.length;i++){
                    this.employees[i].employeeName=this.employees[i].employeeName+"("+this.employees[i].designationName+")";
                }
            });
            this.selectedEmployeeId=""
        }

        public assignArrivalTime():string{
            this.arrivalTime = moment(this.arrivalTimeObj).format("hh:mm A").toString();
            console.log("inside method:"+this.arrivalTime.toString());
            return this.arrivalTime.toString();
        }
        private employeeChanged(value:any){
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
               this.classRooms=data;
            });
        }
        public classRoomChanged(value:any){
            try{
                this.selectedClassRoomId = value.id;
                this.selectedClassRoomNo = value.roomNo;
            }catch (e){
                this.selectedClassRoomId=null;
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
            this.examRoutineArr={};
            this.examRoutineService.getExamRoutineDates(this.stateParams.semesterId,this.stateParams.examType).then((examDateArr: any) =>{
                this.selectedExamDate="";
                this.examRoutineArr={};
                if(examDateArr.length>0) {
                    this.examRoutineArr = examDateArr;
                    this.selectedExamDate = this.examRoutineArr[0].examDate;
                }else{
                    this.notify.warn("No class Routine Found");
                }
            });
        }
        private deptChanged(deptId:any){
            try{
                this.selectedDepartmentId=deptId.id;
                this.selectedEmployeeName="";
                this.selectedEmployeeType="";
                this.selectedDeptName="";
                this.selectedEmployeeName="";
                this.getEmployees(this.selectedDepartmentId);
            }catch (e){
                this.selectedDepartmentId="";
                this.getEmployees(this.selectedDepartmentId);
            }
        }
        private changeExamType(value:any){
            this.selectedExamTypeId=value.id;
            this.selectedExamTypeName=value.name;
            this.getExamDates();
        }
        private changeAbsPreStatus(value:any) {
            this.selectedAbsPreStatusId = value.id;
            this.selectedAbsPreStatusName=value.name;
            this.arrivalTime=this.assignArrivalTime();
           this.isArrivalTimeEligible= this.selectedAbsPreStatusId ==this.absent ? false:true;
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
                this.semesterName=this.semester.name;
                this.hideInsertMode=this.activeSemesterId==this.stateParams.semesterId ? true:false;
                this.showDeleteColumn=this.activeSemesterId==this.stateParams.semesterId ? true:false;
            }).then((data)=>{
                this.getExamDates();
            })
        }
        private ExamDateChange(value:any){
            this.selectedExamDate=value;
        }
        private save():void{
         var json:any=this.convertToJson();
         this.absLateComingService.addAbsLateComingInfo(json).then((data)=>{
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
           console.log("Submit Eligible: "+this.isSubmitEligible);
        }
        private getData():void{
            var reg:Array<IAbsLateComeInfo>=[];
            this.absLateComingService.getAbsLateComeInfo(this.stateParams.semesterId,this.stateParams.examType).then((data)=>{
                reg=data;
                this.absLateComeInfo=reg;
                console.log("Data");
                console.log(data);
            });

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
            });
            this.checkBoxCounter=0;
            this.submit_Button_Disable=true;
        }
        private convertToJsonForDelete(result: Array<IAbsLateComeInfo>): any {
            var completeJson = {};
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
            return completeJson;
        }
        public convertToJson(): any {
            var completeJson = {};
            var jsonObj = [];
            var item = {};
            item["employeeId"] =this.selectedEmployeeId;
            item["examType"] =this.selectedExamTypeId;
            item["presentType"]=this.selectedAbsPreStatusId;
            item["remarks"]=this.remarks;
            item["invigilatorRoomId"]=this.selectedClassRoomId==null? "0":this.selectedClassRoomId.toString();
            item["examDate"]=this.selectedExamDate;
            item["arrivalTime"]=(this.arrivalTime==undefined || this.arrivalTime==null) ? 'null':this.arrivalTime;
            jsonObj.push(item);
            completeJson["entries"] = jsonObj;
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