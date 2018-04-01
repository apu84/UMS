module ums{
    interface IConstantsDept {
        id: any;
        name: string;
    }


    interface ISemesterName{
        semesterId:number;
        semesterName:string;
    }

    interface IAssignedCoursesForReview{
        teacherId:string;
        semesterId:number;
        courseName:string;
        courseNo:string;
        courseTitle:string;
        deptName:string;
    }
    class AdminVcTesComparison{
        public deptList: Array<IConstantsDept>;
        public deptName: IConstantsDept;
        public selectedDepartmentId:string;
        public assignedCoursesForReview:Array<IAssignedCoursesForReview>;
        public semesters:Array<Semester>;
        public semester:Semester;
        public facultyName:string;
        public facultyId:string;
        public fName:string;
        public statusValue:number;
        public semesterName:string;
        public selectedTeacherId:string;
        public selectedSemesterName:ISemesterName;
        public selectedSemesterId:number;
        public getTotalRecords:any;
        public itemPerPage:number;
        public currentPageNumber:number;
        public deptId:string;
        public submit_Button_Disable:boolean;
        public checkBoxCounter:number;
        public resultView:boolean;
        public classObservationTotalSPoints:number;
        public classObservationTotalStudent:number;
        public classObservationAverage:number;
        public nonClassObservationTotalSPoints:number;
        public nonClassObservationTotalStudent:number;
        public nonClassObservationAverage:number;
        public commentPgCurrentPage:number;
        public commentPgItemsPerPage:number;
        public innerCommentPgCurrentPage:number;
        public innerCommentPgItemsPerPage:number;
        public commentPgTotalRecords:number;
        public finalScore:number;
        public selectRow:number;
        public selectedCourseId:string;
        public selectedCourseNo:string;
        public selectedCourseTitle:string;
        public checkEvaluationResult:boolean;
        public evaluationResultStatus:boolean;
        public staticTeacherName:string;
        public staticSessionName:string;
        public departmentName:string;
        public startDate:string;
        public endDate:string;
        public deadLine:boolean;
        public designationStatus:string;
        public studentSubmitDeadLine:boolean;
        public studentSubmitEndDate:string;
        public currentSemesterId:number;
        public static $inject = ['appConstants', 'HttpClient', '$q', 'notify', '$sce', '$window', 'semesterService', 'facultyService', 'programService', '$timeout', 'leaveTypeService', 'leaveApplicationService', 'leaveApplicationStatusService', 'employeeService', 'additionalRolePermissionsService', 'userService', 'commonService', 'attachmentService'];
        constructor(private appConstants: any,
                    private httpClient: HttpClient,
                    private $q: ng.IQService,
                    private notify: Notify,
                    private $sce: ng.ISCEService,
                    private $window: ng.IWindowService,
                    private semesterService: SemesterService,
                    private facultyService: FacultyService,
                    private programService: ProgramService,
                    private $timeout: ng.ITimeoutService,
                    private leaveTypeService: LeaveTypeService,
                    private leaveApplicationService: LeaveApplicationService,
                    private leaveApplicationStatusService: LeaveApplicationStatusService,
                    private employeeService: EmployeeService,
                    private additionalRolePermissionsService: AdditionalRolePermissionsService,
                    private userService: UserService,
                    private commonservice: CommonService,
                    private attachmentService: AttachmentService){
            this.facultyName="";
            this.facultyId="";
            this.statusValue=1;
            this.itemPerPage=10;
            this.currentPageNumber=1;
            this.submit_Button_Disable=true;
            this.resultView=true;
            this.checkBoxCounter=0;
            this.commentPgCurrentPage=1
            this.commentPgItemsPerPage=1;
            this.innerCommentPgCurrentPage=1;
            this.innerCommentPgItemsPerPage=2;
            this.commentPgTotalRecords=0;
            this.checkEvaluationResult=true;
            this.evaluationResultStatus=true;
            this.selectedSemesterId=11;
            this.startDate="";
            this.endDate="";
            this.deadLine=false;
            this.deptList = [];
            this.deptList = this.appConstants.deptForTes;
            this.deptName=this.deptList[0];
            this.selectedDepartmentId=this.deptName.id;
            console.log("-----"+this.selectedDepartmentId);
            this.getStudentSubmitDeadLine();
            this.getSemesters();
        }
        private deptChanged(deptId:any){
            this.selectedDepartmentId=deptId.id;
            this.checkEvaluationResult=true;
            this.assignedCoursesForReview=[];
            console.log(this.selectedDepartmentId);


        }

        private semesterChanged(val:any){
            console.log("Name: "+val.name+"\nsemesterId: "+val.id);
            this.selectedSemesterId=val.id;
            this.checkEvaluationResult=true;
            this.assignedCoursesForReview=[];
            // this.getStudentSubmitDeadLine();

        }
        private getSemesters():void{
            this.semesterService.fetchSemesters(11,5, Utils.SEMESTER_FETCH_WITH_NEWLY_CREATED).then((semesters:Array<Semester>)=>{
                this.semesters=semesters;
                console.log(this.semesters);
                for(var i=0;i<semesters.length;i++){
                    if(semesters[i].status==2){
                        this.semester = semesters[i];
                        break;
                    }
                }
                this.selectedSemesterId=this.semester.id;
                console.log("I____Id: "+this.selectedSemesterId);
            });
        }

        private getStudentSubmitDeadLine(){
            var defer = this.$q.defer();
            this.httpClient.get('/ums-webservice-academic/academic/applicationTES/getStudentSubmitDeadLineInfo', 'application/json',
                (json: any, etag: string) => {
                    console.log("Assigned Courses!!!!");
                    this.studentSubmitDeadLine=json.studentSubmitDeadLine;
                    this.studentSubmitEndDate=json.endDate;
                    this.currentSemesterId=json.currentSemesterId;
                    console.log(this.studentSubmitDeadLine+"\n"+this.studentSubmitEndDate+"\n"+this.currentSemesterId);
                    defer.resolve(json.entries);
                },
                (response: ng.IHttpPromiseCallbackArg<any>) => {
                    console.error(response);
                });
            return defer.promise;
        }



        private getComparisionResult(){
            if(this.currentSemesterId==this.selectedSemesterId){
                if(this.studentSubmitDeadLine){
                    this.notify.info("result available")
                }else{
                    this.notify.info("Result is under Process.You Can access the result of this Semester on "+this.studentSubmitEndDate);
                }
            }else {
                //this.checkEvaluationResult = false;
                //this.getResults();
            }


                Utils.expandRightDiv();
                this.assignedCoursesForReview=[];
                this.staticSessionName=this.semester.name;
                console.log("Stat.....");
                console.log("Dept: "+this.selectedDepartmentId+"\nSemesterId: "+this.selectedSemesterId);
              var defer = this.$q.defer();
                var appTES:Array<IAssignedCoursesForReview>=[];
                this.httpClient.get('/ums-webservice-academic/academic/applicationTES/getComparisionResult/deptId/'+this.selectedDepartmentId+'/semesterId/'+this.selectedSemesterId, 'application/json',
                    (json: any, etag: string) => {
                        console.log("List of Courses");
                        appTES=json.entries;
                        this.assignedCoursesForReview=appTES;
                        console.log(this.assignedCoursesForReview);
                        defer.resolve(json);
                    },
                    (response: ng.IHttpPromiseCallbackArg<any>) => {
                        console.error(response);
                    });
                return defer.promise;


        }



        private  getReport(){
            let contentType: string = UmsUtil.getFileContentType("pdf");
            let fileName = "Evaluation Report";
            console.log("QWERTYUIIOOOPPPPP");
            console.log(""+this.selectedTeacherId+"\n"+this.selectedSemesterId+"\n"+this.selectedCourseId);
            var defer = this.$q.defer();
            this.httpClient.get('/ums-webservice-academic/academic/applicationTES/getReport/courseId/'+this.selectedCourseId+'/teacherId/'+this.selectedTeacherId+'/semesterId/'+this.selectedSemesterId, 'application/pdf',
                (data: any, etag: string) => {
                    console.log("pdf");
                    UmsUtil.writeFileContent(data, contentType, fileName);
                },
                (response: ng.IHttpPromiseCallbackArg<any>) => {
                    console.error(response);
                }, 'arraybuffer');
            return defer.promise;
        }


    }
    UMS.controller("AdminVcTesComparison",AdminVcTesComparison)
}