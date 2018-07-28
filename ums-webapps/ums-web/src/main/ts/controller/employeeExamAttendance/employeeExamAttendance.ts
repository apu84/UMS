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
        public static $inject = ['appConstants','HttpClient', '$q', 'notify', '$sce', '$window', 'semesterService', 'facultyService',
            'programService','examRoutineService','classRoomService','employeeService'];

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
                    private employeeService:EmployeeService){
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
            this.selectedExamTypeName=this.examType.name;
            this.selectedExamDate="";
            this.deptList = [];
            this.deptList = this.appConstants.departmentOffice;
            this.examTypeList=[];
            this.examTypeList=this.appConstants.examType;
            this.examType=this.examTypeList[0];
            this.selectedExamTypeId=this.examType.id;
            this.selectedExamTypeName=this.examType.name
            this.roomInCharge=false;
            this.getSemesters();
            this.getExamDates();
        }
        private search():void{
            Utils.expandRightDiv();
            this.getEmployees(this.selectedDepartmentId);
            this.getClassRoomInfo();
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
             this.selectedEmployeeType=value.name;
             this.isExamDateSelected=true;
             console.log("EmployeeType: "+this.employeeTypeId+" "+this.selectedEmployeeType);

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

             console.log("**"+this.selectedClassRoomId);
         }
         private initializeDatePickers() {
             setTimeout(function () {
                 $('.datepicker-default').datepicker();
                 $('.datepicker-default').on('change', function () {
                     $('.datepicker').hide();
                 });
             }, 200);
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

    }
    UMS.controller("EmployeeExamAttendance",EmployeeExamAttendance);
}
