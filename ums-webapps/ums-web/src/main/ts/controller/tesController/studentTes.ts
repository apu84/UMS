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

    class studentTES{
        public courseTypeList: Array<IConstants>;
        public courseApprovalStatus: IConstants;
        public  questionListAndReview:Array<IQuestions>;
        public allReviewEligibleCourses:Array<IReviewEligibleCourses>;
        public courseType:string;
        public semesterNameCurrent:string;
        public selectedCourseByStudent:string;
        public getFacultyInfo:Array<IteacherInfo>;
        public enrolledCourse: IReviewEligibleCourses;
        public selectedRow:any;
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
            var appTES:Array<IReviewEligibleCourses>=[];
            var defer = this.$q.defer();
            this.httpClient.get('/ums-webservice-academic/academic/applicationTES/getReviewEligibleCourses/courseType/'+this.courseType, 'application/json',
                (json: any, etag: string) => {
                    appTES=json.entries;
                    console.log("****Q1Q****");
                    console.log("Applicatino TES Get Questions!!!!");
                    this.allReviewEligibleCourses=appTES;
                    this.enrolledCourse=this.allReviewEligibleCourses[0];
                    this.selectedCourseByStudent=this.enrolledCourse.courseTitle;
                    this.semesterNameCurrent=json.semesterName;
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
        private courseChanged(){
            console.log("selected course");
            this.selectedCourseByStudent=this.enrolledCourse.courseName;
           console.log(this.selectedCourseByStudent);
            var appTES:Array<IteacherInfo>=[];
            this.getFacultyInfo=[];
            this.selectedRow=null;
            var defer = this.$q.defer();
            this.httpClient.get('/ums-webservice-academic/academic/applicationTES/getFacultyInfo/courseId/'+this.selectedCourseByStudent+'/courseType/'+this.courseType,'application/json',
                (json: any, etag: string) => {
                    appTES=json.entries;
                    console.log("****T1T****");
                    console.log("Applicatin TES Get FacultyInfo!!!!");
                    this.getFacultyInfo=appTES;
                    console.log(this.getFacultyInfo);
                    defer.resolve(json.entries);
                },
                (response: ng.IHttpPromiseCallbackArg<any>) => {
                    console.error(response);
                });
            return defer.promise;
        }
        private getInfo(point:any,cmn:any,id:any){
            this.selectedRow=null;
            console.log("Faculty_Id:"+point+"\nCourse_ID:"+cmn+"\nRow_ID:"+id);
            this.selectedRow=id;

        }


        private submit(){
         console.log("Submit");
         console.log(this.questionListAndReview);
        }
    }
    UMS.controller("studentTES",studentTES);
}