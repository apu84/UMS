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
         public empExamAttendantInfoForUpdateDelete:IEmployeeExamAttendance;
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
            console.log("EmpTypeName: "+this.employeeTypeName);
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
            this.getSemesters();
            this.getExamDates();
            this.initializeDatePickers();
        }
        private search():void{
            Utils.expandRightDiv();
            this.initializeData();
            this.getEmployees(this.selectedDepartmentId);
            this.getClassRoomInfo();
            this.getData();
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
                 console.log("****Exam Dates***");
                 this.examRoutineArr=examDateArr;
                 console.log(this.examRoutineArr);
             })
         }
         private ExamDateChange(value:any){
             this.selectedExamDate=value;
             this.isExamDateSelected=true;
             console.log("Exam Date: "+this.selectedExamDate);
         }
         private changeEmployeeType(value:any){
             this.employeeTypeId=value.id;
             this.employeeTypeName=value.name;
             this.isStaffSelected=this.employeeTypeId==this.staffId?true:false;
             console.log("EmployeeType-> "+this.employeeTypeId+" "+this.employeeTypeName);
         }
         private changeExamType(value:any){
             console.log(value.id+"  "+value.name);
             this.selectedExamTypeId=value.id;
             this.selectedExamTypeName=value.name;
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
             this.getExamDates();
         }
         private getEmployees(deptId:string){
             this.employeeService.getAll().then((data:Array<Employee>)=>{
                 console.log("***emp info***");
                 console.log(this.employeeTypeId);
                 this.employees=data;
                 this.employees=this.employees.filter((a)=>a.deptOfficeId==deptId && a.status==1 && a.employeeType==this.employeeTypeId);
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
             }catch {
                 this.selectedClassRoomId=null;
             }

             console.log("ClassRoomId: "+this.selectedClassRoomId);
         }
         private initializeDatePickers() {
             setTimeout(function () {
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
             console.log("In date changed"+examDate);

         }
         private deptChanged(deptId:any){
             try{
                 this.selectedDepartmentId=deptId.id;
                 console.log("id: "+this.selectedDepartmentId);
                 //this.getEmployees(this.selectedDepartmentId);
             }catch {
                 this.selectedDepartmentId="";
               //  this.getEmployees(this.selectedDepartmentId);
             }
         }
         private save():void{
             if(this.selectedClassRoomId!=null) {
                 var json: any = this.convertToJson();
                 this.employeeExamAttendanceService.addEmpExamAttendanceInfo(json).then((data) => {
                     console.log("I am Changing >>>>>>>>>>>&&&&&&&&&&&&&&&&&&&&&>>>>>>>>>>>>>>>>>>");
                     this.initializeData();
                 });
             }else {
                 this.notify.warn("Select classroom Id");
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
            console.log("Update p Info");
             this.upEmpExamInvDate="";
             this.upEmpExamReserveDate="";
             this.empExamAttendantInfoForUpdateDelete=null;
             console.log("See Null: "+this.empExamAttendantInfoForUpdateDelete);
             this.empExamAttendantInfoForUpdateDelete=value;
             console.log(this.empExamAttendantInfoForUpdateDelete);
             this.upEmpExamInvDate=this.empExamAttendantInfoForUpdateDelete.invigilatorDate;
             this.upEmpExamReserveDate=this.empExamAttendantInfoForUpdateDelete.reserveDate;
             this.isDeleteAvailable=false;
         }
         private deleteInfo(value:any):void{
             console.log("Inside Delete");
             this.upEmpExamInvDate="";
             this.upEmpExamReserveDate="";
            /* this.empExamAttendantInfoForUpdateDelete=null;
             console.log("See Null: "+this.empExamAttendantInfoForUpdateDelete);
             console.log(this.empExamAttendantInfoForUpdateDelete);*/
             this.empExamAttendantInfoForUpdateDelete=value;
             this.upEmpExamInvDate=this.empExamAttendantInfoForUpdateDelete.invigilatorDate;
             this.upEmpExamReserveDate=this.empExamAttendantInfoForUpdateDelete.reserveDate;
             this.isDeleteAvailable=true;
         }
         private delete(){

                var json = this.convertToJsonForDelete(this.empExamAttendantInfoForUpdateDelete);
                this.employeeExamAttendanceService.deleteEmpExamAttendanceInfo(json).then((data) => {
                    console.log(data);
                    this.getData();
                });
                console.log("--" + json);

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
