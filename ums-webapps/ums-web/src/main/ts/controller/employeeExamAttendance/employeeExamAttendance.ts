module ums{
    interface IConstants{
        id:any;
        name:any;
    }
     class EmployeeExamAttendance{
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
         public deptList: Array<IConstants>;
         public deptName: IConstants;
         public classRooms:Array<ClassRoom>;
         public classRoom:ClassRoom;
         public employees:Array<Employee>;
         public employee:Employee;
         public selectedClassRoomId:number;
         public selectedEmployeeId:string;
         public selectedDepartmentId:string;
         public selectedDeptName:string;
         public selectedEmployeeName:string;
         public selectedEmployeeType:string;
         public selectedClassRoomNo:string;
         public isExamDateSelected:boolean;
         public employeeTypeList:Array<IConstants>;
         public employeeType:IConstants;
         public employeeTypeId:number;
         public roomInCharge:boolean;
         public empExamDate:string;
         public empReserveDate:string;
         public isStaffSelected:boolean;
         public staffId:number;
         public employeeTypeName:string;
         private isDeleteAvailable:boolean;
         public upEmpExamInvDate:string;
         public upEmpExamReserveDate:string;
         public empExamAttendantInfo:Array<IEmployeeExamAttendance>;
         public roomNo:string;
         public empName:string;
         public empExamAttendantInfoForDelete:IEmployeeExamAttendance;
         public empExamAttendantInfoForUpdate:IEmployeeExamAttendance;
         public isRightDivAvailable:boolean;
         public isDeptSelected:boolean;
         public departmentName:string;
         public isSubmitModalAvailable:boolean;
         public isInsertAvailable:boolean;
         public hideInsertMode:boolean;
        public static $inject = ['appConstants','HttpClient', '$q', 'notify', '$sce', '$window', 'semesterService', 'facultyService',
            'programService','examRoutineService','classRoomService','employeeService','employeeExamAttendanceService'];

        constructor(private appConstants: any,
                    private httpClient: HttpClient,
                    private $q: ng.IQService,
                    private notify: Notify,
                    private $sce: ng.ISCEService,
                    private $window: ng.IWindowService,
                    private semesterService: SemesterService,
                    private facultyService: FacultyService,
                    private programService: ProgramService,
                    private examRoutineService: ExamRoutineService,
                    private classRoomService:ClassRoomService,
                    private employeeService:EmployeeService,
                    private employeeExamAttendanceService:EmployeeExamAttendanceService){
            this.examTypeList=[];
            this.examTypeList=this.appConstants.examType;
            this.examType=this.examTypeList[0];
            this.selectedExamTypeId=this.examType.id;
            this.selectedExamTypeName=this.examType.name;
            this.selectedExamTypeName=this.examType.name;
            this.employeeTypeList=[];
            this.employeeTypeList=this.appConstants.employeeTypes;
            this.employeeType=this.employeeTypeList[0];
            this.employeeTypeId=this.employeeType.id;
            this.employeeTypeName=this.employeeType.name;
            this.selectedExamDate="";
            this.deptList = [];
            this.deptList = this.appConstants.departmentOffice;
            this.examTypeList=[];
            this.examTypeList=this.appConstants.examType;
            this.examType=this.examTypeList[0];
            this.selectedExamTypeId=this.examType.id;
            this.selectedExamTypeName=this.examType.name
            this.roomInCharge=false;
            this.empExamDate="";
            this.empReserveDate="";
            this.staffId=3;
            this.isStaffSelected=false;
            this.isDeleteAvailable=false;
            this.selectedEmployeeName="";
            this.selectedClassRoomNo="";
            this.departmentName="";
            this.selectedEmployeeId="";
            this.isRightDivAvailable=false;
            this.isExamDateSelected=false;
            this.isDeptSelected=false;
            this.isSubmitModalAvailable=false;
            this.isInsertAvailable=false;

            this.getSemesters();
            this.getExamDates();
        }
         private enableInsert(){
             this.isInsertAvailable=true;
         }
         private hideInsert():void{
             this.isInsertAvailable=false;
         }
        private search():void{
            if(this.isDeptSelected==true){
                if(this.isExamDateSelected==true){
                    this.isRightDivAvailable = true;
                    Utils.expandRightDiv();
                    this.initializeData();
                    this.getEmployees(this.selectedDepartmentId);
                    this.getClassRoomInfo();
                    this.getData();
                }else{
                    this.notify.warn("Select an Exam Date ");
                }
            }else{
                this.notify.warn("Select Department");
            }
        }
        private getData():void{
            var info:Array<IEmployeeExamAttendance>=[];
            this.employeeExamAttendanceService.getEmpExamAttendanceInfo(this.selectedSemesterId,this.selectedExamTypeId).then((data)=>{
                info=data;
                this.empExamAttendantInfo=info;
                console.log("*****List****");
             console.log(this.empExamAttendantInfo);
            });
        }
         private getExamDates(){
             this.examRoutineArr={};
             this.examRoutineService.getExamRoutineDates(this.selectedSemesterId,this.selectedExamTypeId).then((examDateArr: any) =>{
                 this.examRoutineArr={};
                 this.examRoutineArr=examDateArr;
             })
         }
         private ExamDateChange(value:any){
             this.selectedExamDate=value;
             this.isExamDateSelected=true;
             this.isRightDivAvailable=false;
         }
         private changeEmployeeType(value:any){
             this.employeeTypeId=value.id;
             this.employeeTypeName=value.name;
             this.isRightDivAvailable=false;
             this.isStaffSelected=this.employeeTypeId==this.staffId?true:false;
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
                 this.activeSemesterId=this.semester.id;
                 this.semesterName=this.semester.name;
             });
             this.hideInsertMode=this.selectedSemesterId==this.activeSemesterId ? true:false;
             console.log(this.hideInsertMode)
         }
         private semesterChanged(val:any){
             this.selectedSemesterId=val.id;
             this.semesterName=val.name;
             this.isRightDivAvailable=false;
             this.hideInsertMode=this.selectedSemesterId==this.activeSemesterId ? true:false;
             console.log(this.hideInsertMode);
             this.getExamDates();
         }
         private getEmployees(deptId:string){
             this.employeeService.getAll().then((data:Array<Employee>)=>{
                 this.employees=data;
                 this.employees=this.employees.filter((a)=>a.deptOfficeId==deptId && a.status==1 && a.employeeType==this.employeeTypeId);
                 for(let i=0;i<this.employees.length;i++){
                     this.employees[i].employeeName=this.employees[i].employeeName+"("+this.employees[i].designationName+")";
                 }
             })
             this.selectedEmployeeId=""
         }
         private employeeChanged(value:any){
             console.log(value);
             this.selectedEmployeeId=value.id;
             this.selectedEmployeeName=value.employeeName;
             this.selectedEmployeeType=value.employeeType;
             this.selectedDeptName=value.deptOfficeName;
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
             }catch {
                 this.selectedClassRoomId=null;
             }
         }
         private initializeDatePickers() {
             setTimeout(function () {
                 $('.datepicker').datepicker('remove');
                 $('.datepicker-default').datepicker({
                     inputs: undefined,
                     todayHighlight:true,
                     multidate: true,
                     clearBtn: true
                 });
                 $('.datepicker-default').on('change', function () {
                 });
             }, 200);
         }
         private dateChanged(examDate: any) {
             console.log(""+examDate);
         }
         private deptChanged(deptId:any){
             this.isRightDivAvailable=false;
             this.isDeptSelected=true;
             try{
                 this.selectedDepartmentId=deptId.id;
                 this.departmentName=deptId.name;
             }catch {
                 this.selectedDepartmentId="";
             }
         }
         private save():void{
                    var json: any = this.convertToJson();
                    this.employeeExamAttendanceService.addEmpExamAttendanceInfo(json).then((data) => {
                        if(data=="success" || data.equals("success")){
                            this.notify.success("Successfully Saved");
                        }
                        this.getData();
                        this.initializeData();
                    });
         }
         public isEligibleForSubmitData():void{
             if(this.selectedEmployeeId!="" || this.selectedEmployeeId.length>5){
                 if(this.selectedClassRoomId!=null) {
                     this.isSubmitModalAvailable=true;
                 }else {
                     this.isSubmitModalAvailable=false;
                     this.notify.warn("Select classroom");
                 }
             }else{
                 this.isSubmitModalAvailable=false;
                 this.notify.warn("Select Employee Name");
             }

         }
         public initializeData():void{
             this.empExamDate="";
             this.empReserveDate="";
             this.selectedEmployeeId="";
             this.selectedEmployeeName="select Employee";
             this.selectedClassRoomId=null;
             this.selectedClassRoomNo="select Room No";
             this.initializeDatePickers();
         }
         private updateInfo(value:any):void{
             this.upEmpExamInvDate="";
             this.upEmpExamReserveDate="";
             this.empExamAttendantInfoForUpdate=value;
             this.upEmpExamInvDate=this. empExamAttendantInfoForUpdate.invigilatorDateForUpdate;
             this.upEmpExamReserveDate=this. empExamAttendantInfoForUpdate.reserveDateForUpdate;
             this.isDeleteAvailable=false;
         }
         private deleteInfo(value:any):void{
             this.upEmpExamInvDate="";
             this.upEmpExamReserveDate="";
             this. empExamAttendantInfoForDelete=value;
             this.upEmpExamInvDate=this. empExamAttendantInfoForDelete.invigilatorDate;
             this.upEmpExamReserveDate=this. empExamAttendantInfoForDelete.reserveDate;
             this.isDeleteAvailable=true;
         }

         private delete():void{
                var json = this.convertToJsonForDelete(this. empExamAttendantInfoForDelete);
                this.employeeExamAttendanceService.deleteEmpExamAttendanceInfo(json).then((data) => {
                    console.log(data);
                    this.notify.success("Successfully deleted");
                    this.getData();
                });
         }
         private update():void{
             var up = this.convertToJsonForUpdate(this. empExamAttendantInfoForUpdate);
             console.log(up);
            this.employeeExamAttendanceService.updateEmpExamAttendanceInfo(up).then((data)=>{
                this.upEmpExamReserveDate="";
                this.upEmpExamInvDate="";
                this.initializeDatePickers();
                this.getData();
            });

         }
         public  getClassRoomReport():void{
            this.employeeExamAttendanceService.getMemorandumReport(this.selectedSemesterId,this.selectedExamTypeId).then((data)=>{
                console.log("Success");
            });
         }
         public getAttendantReport():void{
           this.employeeExamAttendanceService.getStaffAttendantReport(
               this.selectedSemesterId,
               this.selectedExamTypeId,
               this.selectedExamDate).then((data)=>{
               console.log("Staff");
           });
         }
         public getEmployeeReport():void{
          this.employeeExamAttendanceService.getEmployeeAttendantReport(
              this.selectedSemesterId,
              this.selectedExamTypeId,
              this.selectedExamDate,
              this.selectedDepartmentId
          ).then((data)=>{
              console.log("Invigilator Employee");
          });
         }
         getReserveEmployeeReport():void{
             this.employeeExamAttendanceService.getReserveEmployeeAttendantReport(
                 this.selectedSemesterId,
                 this.selectedExamTypeId,
                 this.selectedExamDate,
                 this.selectedDepartmentId
             ).then((data)=>{
                 console.log("Reserve Employee");
             });
         }
         private convertToJsonForDelete(result: IEmployeeExamAttendance): any {
             var completeJson = {};
             console.log("result");
             console.log(result);
             var jsonObj = [];
                 var item = {};
                     item["id"] = result.id.toString();
                     jsonObj.push(item);
             completeJson["entries"] = jsonObj;
             return completeJson;
         }
         public convertToJsonForUpdate(result: IEmployeeExamAttendance): any {
             var completeJson = {};
             console.log("Update Json");
             var jsonObj = [];
             var item = {};
             item["id"] =result.id.toString();
             item["employeeId"] =result.employeeId;
             item["examType"] =result.examType;
             item["roomInCharge"] =result.roomInCharge;
             item["invigilatorRoomId"]=result.invigilatorRoomId.toString();
             item["invigilatorDate"]=this.upEmpExamInvDate;
             item["reserveDate"]=this.upEmpExamReserveDate;
             jsonObj.push(item);
             completeJson["entries"] = jsonObj;
             return completeJson;
         }

         public convertToJson(): any {
             var completeJson = {};
             console.log("result");
             var jsonObj = [];
             var item = {};
             item["employeeId"] =this.selectedEmployeeId;
             item["examType"] =this.selectedExamTypeId;
             item["roomInCharge"] =this.roomInCharge==true? 1:0;
             item["invigilatorRoomId"]=this.selectedClassRoomId==null? "0":this.selectedClassRoomId.toString();
             item["invigilatorDate"]=this.empExamDate;
             item["reserveDate"]=this.empReserveDate;
             jsonObj.push(item);
             completeJson["entries"] = jsonObj;
             console.log(completeJson);
             return completeJson;
         }
    }
    UMS.controller("EmployeeExamAttendance",EmployeeExamAttendance);
}
