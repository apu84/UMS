module ums{
     class OptStudentCourseSelection{
         public studentInfo:Student;
         public studentId:string;
         public departmentId: string;
         public departmentName:string;
         public programId: number;
         public academicYear:number;
         public academicSemester:number;
         public opCourseYear:number;
         public opCourseSemester:number;
         public semesters: Array<Semester>;
         public semester: Semester;
         public selectedSemesterId: number;
         public activeSemesterId: number;
         public semesterName:string;
         public isSubGroupAvailable: boolean;
         public isEligibleForApply:boolean;
         public isApprovedGroupAvailable:boolean;
         public optOfferedCourseList: Array<any>;
         public approvedOptOfferedCourseList: Array<any>;
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
             this.isEligibleForApply=false;
             this.isApprovedGroupAvailable=false;
             this.StudentInfoService.getStudent().then((student)=>{
                this.studentInfo = student;
                console.log(this.studentInfo);
                this.studentId=this.studentInfo.id;
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
                 this.setYearSemester(this.academicYear,this.academicSemester);
            }).then((data)=>{
                if(this.isEligibleForApply){
                    this.optOfferedCourseList=[];
                    this.optStudentCourseSelectionService.getOfferedCourses(this.selectedSemesterId,this.programId,this.opCourseYear,this.opCourseSemester).then((data)=>{
                        console.log("********");
                        this.optOfferedCourseList=data;
                        this.approvedOptOfferedCourseList=this.optOfferedCourseList.filter(f=>f.isMandatory==true);
                        this.optOfferedCourseList=this.optOfferedCourseList.filter(f=>f.isMandatory==false);
                      this.isApprovedGroupAvailable=  this.approvedOptOfferedCourseList.length>0 ? true:false;
                    })
                }
             });

         }
         public setYearSemester(year:number,semester:number){
            if(this.departmentId=="03" || this.departmentId=="04"){
                console.log("CSE,CIVIL");
                if(year==4 && semester==1){
                    this.isEligibleForApply=true;
                }
            }else{
                if((year==3 && semester==2) || (year==4 && semester==1)){
                    this.isEligibleForApply=true;
                }
            }
            if(this.isEligibleForApply){
             this.yearSemesterMap(year+"-"+semester);
            }else {
               // this.notify.error("You are not Allowed to Apply for Optional Course");
            }
         }
         public yearSemesterMap(yearSem:string):void{
             console.log("YS: "+yearSem);
             yearSem=(yearSem=="3-2") ? "4-1":"4-2";

              let  YS =yearSem.split("-");
             this.opCourseYear=+YS[0];
             this.opCourseSemester=+YS[1];
             console.log("Y: "+this.opCourseYear+" S: "+this.opCourseSemester);
         }
         public setSelection(id:number,choice:number,subGroupId:number):void{
             console.log("id: "+id+" choice: "+choice+" subGrpId: "+subGroupId);
             if (this.departmentId != "05") {
                 for (let i = 0; i < this.optOfferedCourseList.length; i++) {
                     if (this.optOfferedCourseList[i].choice == choice) {
                         this.optOfferedCourseList[i].choice=0;
                     }
                 }
                 for (let i = 0; i < this.optOfferedCourseList.length; i++) {
                         if (this.optOfferedCourseList[i].groupId == id) {
                             this.optOfferedCourseList[i].choice=choice;
                             break;
                         }
                 }
             }else {
                 for (let i = 0; i < this.optOfferedCourseList.length; i++) {
                     if (this.optOfferedCourseList[i].groupId == id) {
                         for (let j = 0; j < this.optOfferedCourseList[i].subGrpCourses.length; j++) {
                             if (this.optOfferedCourseList[i].subGrpCourses[j].choice == choice) {
                                 this.optOfferedCourseList[i].subGrpCourses[j].choice = 0;
                             }
                         }
                         break;
                     }
                 }
                 for (let i = 0; i < this.optOfferedCourseList.length; i++) {
                     for (let j = 0; j < this.optOfferedCourseList[i].subGrpCourses.length; j++) {
                         if (this.optOfferedCourseList[i].subGrpCourses[j].groupId == subGroupId) {
                             this.optOfferedCourseList[i].subGrpCourses[j].choice=choice;
                             break;
                         }
                         }
                     }
             }
             console.log(this.optOfferedCourseList);
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