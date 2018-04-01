module ums{
    interface IConstantsDept {
        id: any;
        name: string;
    }
    interface IConstantsDeptTes {
        id: any;
        name: string;
    }

    interface IFacultyList{
        teacherId:string;
        firstName:string;
        lastName:string;
        deptId:number;
        deptShortName:string;
        designation:string;
        fullName:string
    }

    interface IAssignedCourses{
        teacherId:string;
        courseName:string;
        courseNo:string;
        courseTitle:string;
        section:string;
        semesterId:number;
        apply:boolean;
        status:number;
        programShortName:string;
        deadLineStatus:boolean;
    }

    interface IReport{
        questionId:number;
        questionDetails:string;
        totalScore:number;
        studentNo:number;
        averageScore:number;
        observationType:number;
    }

    interface ISetForReview{
        firstName:string;
        lastName:string;
        courseNo:string;
        courseTitle:string;
        section:string;
        date:string;
    }

    interface IComment{
        questionId:number;
        questionDetails:string;
        comment:string[];
        observationType:number;
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
    class AdminVcTes{
        public deptList: Array<IConstantsDept>;
        public deptName: IConstantsDept;
        public selectedDepartmentId:string;
        public deptTesList: Array<IConstantsDeptTes>;
        public deptTesName: IConstantsDeptTes;
        public selectedDepartmentTesId:string;
        public facultyList:Array<IFacultyList>;
        public facultyListResultEvaluation:Array<IFacultyList>;
        public assignedCourses:Array<IAssignedCourses>;
        public setRivewedCoursesHistory:Array<ISetForReview>;
        public assignedCoursesForReview:Array<IAssignedCoursesForReview>;
        public studentResult:Array<IReport>;
        public studentComments:Array<IComment>;
        public semesters:Array<Semester>;
        public semester:Semester;
        public facultyName:string;
        public facultyId:string;
        public fName:string;
        public statusValue:number;
        public semesterName:string;
        public selectedTeacherName:IFacultyList;
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
            this.deptList = this.appConstants.deptShortName;
            this.deptName=this.deptList[0];
            this.selectedDepartmentId=this.deptName.id;
            console.log("-----"+this.selectedDepartmentId);
            this.getStudentSubmitDeadLine();
            this.getSemester();
        }
        private deptChanged(deptId:any){
            this.selectedDepartmentId=deptId.id;
            this.checkEvaluationResult=true;
            this.assignedCoursesForReview=[];
            this.getEligibleFacultyMembers();
            console.log(this.selectedDepartmentId);


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
            });
        }

        private teacherChanged(val:any){
            console.log("Name: "+val.firstName+"\nId: "+val.teacherId);
            this.checkEvaluationResult=true;
            this.selectedTeacherId=val.teacherId;
            this.assignedCoursesForReview=[];

        }


        private getEligibleFacultyMembers(){
            this.facultyListResultEvaluation=[];
            this.selectedTeacherId=null;
            var appTES:Array<IFacultyList>=[];
            var defer = this.$q.defer();
            this.httpClient.get('/ums-webservice-academic/academic/applicationTES/getEligibleFacultyMembers/semesterId/'+this.selectedSemesterId+'/deptId/'+this.selectedDepartmentId, 'application/json',
                (json: any, etag: string) => {
                    appTES=json.entries;
                    console.log("Eligible Faculty Members!!!!");
                    this.facultyListResultEvaluation=appTES;
                    for(let i=0;i<this.facultyListResultEvaluation.length;i++){
                        this.facultyListResultEvaluation[i].fullName=this.facultyListResultEvaluation[i].firstName+" "+
                            this.facultyListResultEvaluation[i].lastName;
                    }
                    console.log(this.facultyListResultEvaluation);
                    if(this.facultyListResultEvaluation.length>0) {
                        this.selectedTeacherName = this.facultyListResultEvaluation[0];
                        this.selectedTeacherId = this.selectedTeacherName.teacherId;
                        console.log("id-------");
                        console.log("F_Id: " + this.selectedTeacherId + "\nS_Id: " + this.selectedSemesterId);
                    }
                    defer.resolve(json.entries);
                },
                (response: ng.IHttpPromiseCallbackArg<any>) => {
                    console.error(response);
                });
            return defer.promise;
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


        private getResults(){
            this.classObservationTotalSPoints=0;
            this.classObservationTotalStudent=0;
            this.classObservationAverage=0;
            this.nonClassObservationTotalSPoints=0;
            this.nonClassObservationTotalStudent=0;
            this.nonClassObservationAverage=0;
            this.finalScore=0;
            this.studentComments=[];
            this.studentResult=[];
            var appTES:Array<IReport>=[];
            var defer = this.$q.defer();
            var counterObType1=0;
            var counterObType2=0;
            this.httpClient.get('/ums-webservice-academic/academic/applicationTES/getResult/courseId/'+this.selectedCourseId+'/teacherId/'+this.selectedTeacherId+'/semesterId/'+this.selectedSemesterId, 'application/json',
                (json: any, etag: string) => {
                    console.log(json);
                    appTES=json;
                    this.studentResult=appTES;
                    for(let i=0;i<this.studentResult.length;i++){
                        if(this.studentResult[i].observationType==1){
                            counterObType1++;
                            this.classObservationTotalSPoints =this.classObservationTotalSPoints+this.studentResult[i].totalScore;
                            this.classObservationTotalStudent =this.classObservationTotalStudent+this.studentResult[i].studentNo;
                            this.classObservationAverage=this.classObservationAverage+this.studentResult[i].averageScore;
                        }else{
                            counterObType2++;
                            this.nonClassObservationTotalSPoints =this.nonClassObservationTotalSPoints+this.studentResult[i].totalScore;
                            this.nonClassObservationTotalStudent=this.nonClassObservationTotalStudent+this.studentResult[i].studentNo;
                            this.nonClassObservationAverage=(this.nonClassObservationAverage+this.studentResult[i].averageScore);
                        }
                    }
                    this.classObservationAverage=(this.classObservationAverage/counterObType1);
                    this.classObservationAverage=Number(this.classObservationAverage.toFixed(2));
                    this.nonClassObservationAverage=(this.nonClassObservationAverage/counterObType2);
                    this.nonClassObservationAverage=Number(this.nonClassObservationAverage.toFixed(2));
                    this.finalScore=(this.classObservationAverage+this.nonClassObservationAverage)/2;
                    this.finalScore=Number(this.finalScore.toFixed(2))
                    this.getComment();
                    defer.resolve(this.studentResult);
                },
                (response: ng.IHttpPromiseCallbackArg<any>) => {
                    console.error("No Records Found");
                });
            return defer.promise;
        }
        private  getComment(){
            this.studentComments=[];
            var appTES:Array<IComment>=[];
            var defer = this.$q.defer();
            this.httpClient.get('/ums-webservice-academic/academic/applicationTES/getComment/courseId/'+this.selectedCourseId+'/teacherId/'+this.selectedTeacherId+'/semesterId/'+this.selectedSemesterId, 'application/json',
                (json: any, etag: string) => {
                    console.log("comment---------");
                    appTES=json;
                    this.studentComments=appTES;
                    this.commentPgTotalRecords=this.studentComments.length;
                    console.log(this.studentComments);
                    defer.resolve(json);
                },
                (response: ng.IHttpPromiseCallbackArg<any>) => {
                    console.error("No Records Found");
                });
            return defer.promise;
        }
        private getAssignedCoursesForReview(){
            if(this.selectedTeacherId !=null){
                Utils.expandRightDiv();
                this.checkEvaluationResult=true;
                this.assignedCoursesForReview=[];
                this.studentComments=[];
                this.staticTeacherName=this.selectedTeacherName.firstName;
                this.staticSessionName=this.semester.name;
                this.selectRow=null;
                console.log("eeeeeeeeeeee");
                console.log(""+this.selectedTeacherId+"\n"+this.selectedSemesterId);
                var appTES:Array<IAssignedCoursesForReview>=[];
                var defer = this.$q.defer();
                this.httpClient.get('/ums-webservice-academic/academic/applicationTES/getAssignedCoursesForReview/teacherId/'+this.selectedTeacherId+'/semesterId/'+this.selectedSemesterId, 'application/json',
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
            }else{
                this.notify.info("No teacher id Found");
            }

        }
        private getInfo(pTeacherId:any,pCourseId:any,id:any,pSemesterId:number,pCourseNo:any,pCourseTitle:any,pDeptName:string) {
            this.selectRow = id;
            this.selectedCourseId = pCourseId;
            this.selectedTeacherId = pTeacherId;
            this.selectedSemesterId = pSemesterId;
            this.selectedCourseNo = pCourseNo;
            this.selectedCourseTitle = pCourseTitle;
            this.departmentName = pDeptName;
            if(this.currentSemesterId==pSemesterId){
                if(this.studentSubmitDeadLine){
                    this.checkEvaluationResult = false;
                    this.getResults();
                }else{
                    this.notify.info("Result is under Process.You Can access the result of this Semester on "+this.studentSubmitEndDate);
                }
            }else{
                this.checkEvaluationResult = false;
                this.getResults();
            }



        }

        private semesterChanged(val:any){
            console.log("Name: "+val.name+"\nsemesterId: "+val.id);
            this.selectedSemesterId=val.id;
            this.checkEvaluationResult=true;
            this.assignedCoursesForReview=[];
            this.getEligibleFacultyMembers();
            // this.getStudentSubmitDeadLine();

        }
        private getBackToMainView(){
            this.evaluationResultStatus=true;
        }
        private  getSemester(){
            this.evaluationResultStatus=false;
            console.log(""+this.evaluationResultStatus);
            this.assignedCoursesForReview=[];
            this.studentComments=[];
            this.studentResult=[];
            this.selectedCourseNo="";
            this.checkEvaluationResult=true;
            this.selectedSemesterId=null;
            this.selectedTeacherId=null;
            this.selectedSemesterName=null;
            this.selectedTeacherName=null;
            var appTES:Array<ISemesterName>=[];
            //----
            this.semesterService.fetchSemesters(11,5, Utils.SEMESTER_FETCH_WITH_NEWLY_CREATED).then((semesters:Array<Semester>)=>{
                this.semesters=semesters;
                console.log(this.semesters);
                for(var i=0;i<semesters.length;i++){
                    if(semesters[i].status==2){
                        this.semester = semesters[i];
                        break;
                    }
                }
                this.selectedSemesterId=this.semester.id
                console.log("I____Id: "+this.selectedSemesterId);
                this.getEligibleFacultyMembers();

            });

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
    UMS.controller("AdminVcTes",AdminVcTes)
}