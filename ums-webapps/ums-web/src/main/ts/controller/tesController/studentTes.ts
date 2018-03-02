/*
* Rumi-21-1-1028
* carry Approval Controller
* */
module ums{
    interface IQuestions{
        questionId:number;
        questionDetails:string;
        point:number;
        comment:string;
        observationType:number;
    }
    interface IConstants {
        id: any;
        name: string;
    }
    interface IReviewEligibleCourses{
        courseName:string;
        courseTitle:string;
        courseNo:string;
    }
    interface  IteacherInfo{
        teacherId:string;
        section:string;
        deptId:string;
        deptShortName:string;
        firstName:string;
        lastName:string;
    }
    interface IAlreadyReviwed{
        teacherId:string;
        courseName:string;
        courseTitle:string;
        studentId:string;
        semesterId:number;
        firstName:string;
        lastName:string;
    }

    class studentTES{
        public courseTypeList: Array<IConstants>;
        public courseApprovalStatus: IConstants;
        public  questionListAndReview:Array<IQuestions>;
        public allReviewEligibleCourses:Array<IReviewEligibleCourses>;
        public reviewedCourses:Array<IAlreadyReviwed>;
        public courseType:string;
        public semesterNameCurrent:string;
        public selectedCourseByStudent:string;
        public getFacultyInfo:Array<IteacherInfo>;
        public enrolledCourse: IReviewEligibleCourses;
        public selectedRow:any;
        public selectedFacultyid:string;
        public selectedCourseId:string;
        public checkSelectTeacher:boolean;
        public checkComment:boolean;
        public checkCourseTeacher:boolean;
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
                    private attachmentService: AttachmentService) {

            //this.enrolledCourse=<IReviewEligibleCourses>{};
            this.courseTypeList = [];
            this.courseTypeList = this.appConstants.courseTypeTES;
            this.courseApprovalStatus = this.courseTypeList[0];
            this.allReviewEligibleCourses=[];
            this.getFacultyInfo=[];
            this.courseType="";
            this.selectedRow=null;
            this.checkCourseTeacher=false;
            this.checkComment=true;
            this.checkSelectTeacher=true;
           this.statusChanged(this.courseApprovalStatus).then((a:any)=>{
               this.courseChanged();
           })
        }
        private statusChanged(courseApplicationStatus: IConstants){
            this.courseApprovalStatus= courseApplicationStatus;
            console.log(this.courseApprovalStatus);
            if(this.courseApprovalStatus.name.match("Lab")){
           this.courseType="Lab";
            }else{
           this.courseType="Theory";
            }
            this.getFacultyInfo=[];
            //this.checkSelectTeacher=false;
            var appTES:Array<IReviewEligibleCourses>=[];
            var defer = this.$q.defer();
            this.httpClient.get('/ums-webservice-academic/academic/applicationTES/getReviewEligibleCourses/courseType/'+this.courseType, 'application/json',
                (json: any, etag: string) => {
                    appTES=json.entries;
                    console.log("****Q1Q****");
                    console.log("Applicatino TES Get Questions!!!!");
                    this.allReviewEligibleCourses=appTES;
                    this.enrolledCourse=this.allReviewEligibleCourses[0];
                    //this.selectedCourseByStudent=this.enrolledCourse.courseTitle;
                    this.semesterNameCurrent=json.semesterName;
                    this.checkCourseTeacher=false;
                    console.log(this.semesterNameCurrent);
                    console.log(this.selectedCourseByStudent);
                    console.log(this.allReviewEligibleCourses);
                    this.courseChanged();
                    //
                    defer.resolve(json);

                },
                (response: ng.IHttpPromiseCallbackArg<any>) => {
                    console.error(response);
                });
            return defer.promise;
        }


        private getAllQuestions(){
         var appTES:Array<IQuestions>=[];
            var defer = this.$q.defer();
            this.httpClient.get('/ums-webservice-academic/academic/applicationTES/getAllQuestions', 'application/json',
                (json: any, etag: string) => {
                   appTES=json.entries;
                    console.log("****Q1Q****");
                    console.log("Applicatino TES Get Questions!!!!");
                  this.questionListAndReview=appTES;
                    console.log(this.questionListAndReview);
                    defer.resolve(json.entries);

                },
                (response: ng.IHttpPromiseCallbackArg<any>) => {
                    console.error(response);
                });
            return defer.promise;
        }
        private getReviewedCourses(){
            var appTES:Array<IAlreadyReviwed>=[];
            this.reviewedCourses=[];
            var defer = this.$q.defer();
            this.httpClient.get('/ums-webservice-academic/academic/applicationTES/alreadyReviewedCourses', 'application/json',
                (json: any, etag: string) => {
                    appTES=json.entries;
                    console.log("****Z1Z****");
                    console.log("Applicatino TES Reviewed Courses!!!!");
                    this.reviewedCourses=appTES;
                    console.log(this.reviewedCourses);
                    console.log(this.reviewedCourses.length);
                    defer.resolve(json.entries);

                },
                (response: ng.IHttpPromiseCallbackArg<any>) => {
                    console.error(response);
                });
            return defer.promise;
        }
        private courseChanged(){
            console.log("selected course");
            if(this.enrolledCourse !=null) {
                this.selectedCourseByStudent = this.enrolledCourse.courseName;
                console.log(this.selectedCourseByStudent);
                var appTES: Array<IteacherInfo> = [];
                this.getFacultyInfo = [];
                this.selectedRow = null;
                var defer = this.$q.defer();
                this.httpClient.get('/ums-webservice-academic/academic/applicationTES/getFacultyInfo/courseId/' + this.selectedCourseByStudent + '/courseType/' + this.courseType, 'application/json',
                    (json: any, etag: string) => {
                        appTES = json.entries;
                        console.log("****T1T****");
                        console.log("Applicatin TES Get FacultyInfo!!!!");
                        this.getFacultyInfo = appTES;
                        console.log(this.getFacultyInfo);
                        this.checkCourseTeacher = this.getFacultyInfo.length >= 1 ? true : false;
                        this.checkCourseTeacher=false;
                        defer.resolve(json.entries);
                    },
                    (response: ng.IHttpPromiseCallbackArg<any>) => {
                        console.error(response);
                    });
                return defer.promise;
            }else{
                this.notify.success("No specific course found!!");
                this.checkCourseTeacher=true;
                this.checkSelectTeacher=true;
            }
        }
        private getInfo(pFacultyId:any,pCourseId:any,id:any){
            this.selectedRow=null;
            console.log("Faculty_Id:"+pFacultyId+"\nCourse_ID:"+pCourseId+"\nRow_ID:"+id);
            this.selectedRow=id;
            this.selectedFacultyid=pFacultyId;
            this.selectedCourseId=pCourseId;
            this.checkSelectTeacher=false;

        }



        private submit() {
            console.log("Submit");
            for (let i = 0; i < this.questionListAndReview.length; i++) {
                if (this.questionListAndReview[i].point == 1 && this.questionListAndReview[i].comment == "write your reason") {
                    this.checkComment = false;
                }
            }
            if (this.checkComment) {
                console.log(this.questionListAndReview);
                this.convertToJson(this.questionListAndReview).then((app: any) => {
                    console.log("hello From Another Side!!!")
                    console.log(app);
                    this.httpClient.post('academic/applicationTES/saveTES', app, 'application/json')
                        .success((data, status, header, config) => {
                            this.notify.success("Data saved successfully");
                        }).error((data) => {

                    });
                })
                this.checkSelectTeacher = true;
                this.statusChanged(this.courseApprovalStatus);
                this.getAllQuestions();
            }else{
                this.notify.error("If you rate poor then you must write the reason");
                this.checkComment=true;
        }

    }

        private convertToJson(result: Array<IQuestions>): ng.IPromise<any> {
            let defer:ng.IDeferred<any> = this.$q.defer();
            var completeJson = {};
            console.log("result in converto Json");
            console.log(result);
            console.log(result.length);
            var jsonObj = [];
            for(var i=0;i<result.length;i++){
                var item = {};
                item["questionId"]=result[i].questionId ;
                item["point"]=result[i].point;
                item["comment"]=result[i].comment=="write your reason" ? 'null':result[i].comment;
                item["observationType"]=result[i].observationType;
                item["courseId"]=this.selectedCourseId;
                item["teacherId"]=this.selectedFacultyid;
                    console.log("Items");
                    console.log(item);
                    //this.notify.success("sending./.....");
                    jsonObj.push(item);

            }
            completeJson["entries"] = jsonObj;
            console.log("Complete json!!!!!!!!!!!!!!!")
            console.log(completeJson);
            defer.resolve(completeJson);
            return defer.promise;

        }
    }
    UMS.controller("studentTES",studentTES);
}