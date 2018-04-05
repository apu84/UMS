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
    interface IQuestionsReadOnly{
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
        teacherId:string;
        section:string;
        firstName:string;
        lastName:string;
        status:number;
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
        public  questionListAndReviewReadOnly:Array<IQuestionsReadOnly>;
        public allReviewEligibleCourses:Array<IReviewEligibleCourses>;
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
        public totalReviewEligibleCourseLength:number;
        public readOnlyViewCheck:boolean;
        public startDate:string;
        public endDate:string;
        public deadLine:boolean;
        public designationStatus:string;
        public studentSubmitDeadLine:boolean;
        public studentSubmitEndDate:string;
        public currentSemesterId:number;
        public startingDeadline:boolean;
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
            this.readOnlyViewCheck=true;
           this.statusChanged(this.courseApprovalStatus);
        }

        private statusChanged(courseApplicationStatus: IConstants){
            this.courseApprovalStatus= courseApplicationStatus;
            console.log(this.courseApprovalStatus);
            if(this.courseApprovalStatus.name.match("Lab")){
           this.courseType="Lab";
            }else{
           this.courseType="Theory";
            }
            this.selectedRow=null;
            this.checkSelectTeacher=true;
            this.readOnlyViewCheck=true;
            var appTES:Array<IReviewEligibleCourses>=[];
            var defer = this.$q.defer();
            this.httpClient.get('/ums-webservice-academic/academic/applicationTES/getReviewEligibleCourses/courseType/'+this.courseType, 'application/json',
                (json: any, etag: string) => {
                    appTES=json.entries;
                    console.log("****Q1Q****");
                    console.log("Applicatino TES Get Questions!!!!");
                    this.allReviewEligibleCourses=appTES;
                    this.totalReviewEligibleCourseLength=this.allReviewEligibleCourses.length;
                    this.semesterNameCurrent=json.semesterName;
                    this.checkCourseTeacher=false;
                    console.log(this.semesterNameCurrent);
                    console.log(this.allReviewEligibleCourses);
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
                    console.log("****p1p****");
                    console.log("Applicatino TES   get Questions!!!!");
                    this.questionListAndReview=appTES;
                    this.startDate=json.startDate;
                    this.endDate=json.endDate;
                    this.deadLine=json.deadLine;
                    this.startingDeadline=json.startingDeadline;
                    console.log(""+this.startDate+"\n"+this.endDate+"\n"+this.deadLine+"\n"+this.startingDeadline);
                    console.log(this.questionListAndReview);
                    defer.resolve(json.entries);
                },
                (response: ng.IHttpPromiseCallbackArg<any>) => {
                    console.error(response);
                });
            return defer.promise;
        }

        private getInfo(pFacultyId:any,pCourseId:any,id:any,status:number){
            this.selectedRow=null;
            console.log("Faculty_Id:"+pFacultyId+"\nCourse_ID:"+pCourseId+"\nRow_ID:"+id);
            this.selectedRow = id;
            this.selectedFacultyid = pFacultyId;
            this.selectedCourseId = pCourseId;
            if(status!=1) {
                this.readOnlyViewCheck=true;
                this.checkSelectTeacher = false;
            }else{
                this.notify.info("This course has already been Evaluated")
                this.checkSelectTeacher = true;
                this.readOnlyViewCheck=false;
                this.getReviewedCourseInfo();
            }

        }
        private  getReviewedCourseInfo(){
            var appTES:Array<IQuestions>=[];
            this.questionListAndReviewReadOnly=[];
            var defer = this.$q.defer();
            this.httpClient.get('/ums-webservice-academic/academic/applicationTES/getAllQuestions/courseId/'+this.selectedCourseId+'/teacherId/'+this.selectedFacultyid, 'application/json',
                (json: any, etag: string) => {
                    appTES=json.entries;
                    console.log(appTES);
                    this.questionListAndReviewReadOnly=appTES;
                    //console.log(this.questionListAndReviewReadOnly);
                    defer.resolve(json.entries);
                },
                (response: ng.IHttpPromiseCallbackArg<any>) => {
                    console.error(response);
                });
            return defer.promise;
        }

        private checkNullComments(){
            this.checkComment=true;
            console.log("Submit");
            for (let i = 0; i < this.questionListAndReview.length; i++) {
                if (this.questionListAndReview[i].comment !=null && this.questionListAndReview[i].comment.length >100) {
                    this.checkComment = false;
                }
            }
            if (!this.checkComment) {
                this.notify.error("Write your reason between 100 words");
            }
    }

        private submit() {
            if(this.deadLine){
                console.log(this.questionListAndReview);
                this.convertToJson(this.questionListAndReview).then((app: any) => {
                    console.log("hello From Another Side!!!")
                    console.log(app);
                    this.httpClient.post('academic/applicationTES/saveTES', app, 'application/json')
                        .success((data, status, header, config) => {
                            this.notify.success("Data saved successfully");
                            this.checkSelectTeacher = true;
                            this.selectedRow=null;
                            this.statusChanged(this.courseApprovalStatus);
                            this.getAllQuestions();

                        }).error((data) => {
                        this.notify.error("Error in Saving Data");
                    });
                })
            }else{
                this.notify.info("Date over!!!Your submit request can not be processed!!! ")
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
                item["questionId"]=result[i].questionId;
                item["point"]=result[i].point;
                item["comment"]=result[i].comment;
                item["observationType"]=result[i].observationType;
                item["courseId"]=this.selectedCourseId;
                item["teacherId"]=this.selectedFacultyid;
                    console.log("Items");
                    console.log(item);
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