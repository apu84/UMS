module ums{
     class OptStudentCourseSelection{
         public studentInfo:Student;
         public departmentId: string;
         public departmentName:string;
         public programId: number;
         public academicYear:number;
         public academicSemester:number;
         public semesters: Array<Semester>;
         public semester: Semester;
         public selectedSemesterId: number;
         public activeSemesterId: number;
         public semesterName:string;
         public isSubGroupAvailable: boolean;
         public optOfferedCourseList: Array<any>;
         public static $inject = ['appConstants', 'HttpClient', '$q', 'notify', '$sce', '$window', 'semesterService', 'facultyService',
             'programService', 'commonService', 'optCourseOfferService','studentService','StudentInfoService','optStudentCourseSelectionService'];

         constructor(private appConstants: any,
                     private httpClient: HttpClient,
                     private $q: ng.IQService,
                     private notify: Notify,
                     private $sce: ng.ISCEService,
                     private $window: ng.IWindowService,
                     private semesterService: SemesterService,
                     private facultyService: FacultyService,
                     private programService: ProgramService,
                     private commonService: CommonService,
                     private optCourseOfferService: OptCourseOfferService,
                     private studentService:StudentService,
                     private StudentInfoService:StudentInfoService,
                     private optStudentCourseSelectionService:OptStudentCourseSelectionService) {
             this.isSubGroupAvailable=false;
             this.StudentInfoService.getStudent().then((student)=>{
                this.studentInfo = student;
                console.log(this.studentInfo);
                this.programId=+this.studentInfo.programId;
                this.academicYear=this.studentInfo.year;
                this.academicSemester=this.studentInfo.academicSemester;
                this.departmentId=this.studentInfo.departmentId;
                this.selectedSemesterId=this.studentInfo.currentEnrolledSemesterId;
                this.departmentName=this.studentInfo.departmentName;
                this.semesterName=this.studentInfo.currentEnrolledSemesterName;
                 if(this.departmentId=="05"){
                     this.isSubGroupAvailable=true;
                 }else{
                     this.isSubGroupAvailable=false;
                 }
            }).then((data)=>{
                console.log("sub group: "+this.isSubGroupAvailable);
                this.optOfferedCourseList=[];
                this.optStudentCourseSelectionService.getOfferedCourses(this.selectedSemesterId,this.programId,4,2).then((data)=>{
                    console.log("********");
                    this.optOfferedCourseList=data;
                    console.log(this.optOfferedCourseList);

                })
             });

         }
         public  setSelection(id:number,choice:number):void{
             console.log("Id: "+id+" choice: "+choice);

         };
         private getSemesters():number{
             this.semesterService.fetchSemesters(11,5).then((semesters:Array<Semester>)=>{
                 this.semesters=semesters;
                 for(var i=0;i<semesters.length;i++){
                     if(semesters[i].status==1){
                         this.semester = semesters[i];
                         this.activeSemesterId=semesters[i].id;
                         break;
                     }
                 }
                 this.selectedSemesterId=semesters[i].id;
                 console.log("Semster id"+ this.selectedSemesterId);
             });
             return this.selectedSemesterId;
         }
         public alertBox():void{
             alert('Hello!! I am an Alert');
         }

     }
     UMS.controller("OptStudentCourseSelection",OptStudentCourseSelection);
}