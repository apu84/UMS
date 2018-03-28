module ums{

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
    class HeadTES{
        public facultyList:Array<IFacultyList>;
        public facultyListResultEvaluation:Array<IFacultyList>;
        public assignedCourses:Array<IAssignedCourses>;
        public setRivewedCoursesHistory:Array<ISetForReview>;
        public assignedCoursesForReview:Array<IAssignedCoursesForReview>;
        public studentResult:Array<IReport>;
        public studentComments:Array<IComment>;
        public semesterNameList:Array<ISemesterName>;
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
            this.getStudentSubmitDeadLine();
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

        private select2ini(selectBoxId, studentIds, placeHolderText) {
            console.log("studentIds ---------------------------------------");
            console.log(studentIds);
            var data = studentIds;
            $("#" + selectBoxId).select2({
                minimumInputLength: 0,
                query: function (options) {
                    var pageSize = 100;
                    var startIndex = (options.page - 1) * pageSize;
                    var filteredData = data;
                    if (options.term && options.term.length > 0) {
                        if (!options.context) {
                            var term = options.term.toLowerCase();
                            options.context = data.filter(function (metric: any) {
                                return ( metric.name.indexOf(term) !== -1 );
                            });
                        }
                        filteredData = options.context;
                    }
                    options.callback({
                        context: filteredData,
                        results: filteredData.slice(startIndex, startIndex + pageSize),
                        more: (startIndex + pageSize) < filteredData.length
                    });
                },
                placeholder: placeHolderText
            });
        }
        private teacherChanged(val:any){
            console.log("Name: "+val.firstName+"\nId: "+val.teacherId);
            this.selectedTeacherId=val.teacherId;
            this.assignedCoursesForReview=[];

        }
        private getAllFacultyMembers(){
            this.facultyList=[];
       this.facultyListResultEvaluation=[];
       var appTES:Array<IFacultyList>=[];
       var defer = this.$q.defer();
       this.httpClient.get('/ums-webservice-academic/academic/applicationTES/getAllFacultyMembers', 'application/json',
           (json: any, etag: string) => {
               appTES=json.entries;
               console.log("Faculty Members!!!!");
               this.facultyList=appTES;
             //  this.facultyListResultEvaluation=appTES;
              // this.getEligibleFacultyMembers();
               this.getTotalRecords=json.totalRecords;
               console.log(this.getTotalRecords);
               console.log(this.facultyList);
               defer.resolve(json.entries);

           },
           (response: ng.IHttpPromiseCallbackArg<any>) => {
               console.error(response);
           });
       return defer.promise;
   }

        private getEligibleFacultyMembers(){
            this.facultyListResultEvaluation=[];
            this.selectedTeacherId=null;
            var appTES:Array<IFacultyList>=[];
            var defer = this.$q.defer();
            this.httpClient.get('/ums-webservice-academic/academic/applicationTES/getEligibleFacultyMembers/semesterId/'+this.selectedSemesterId, 'application/json',
                (json: any, etag: string) => {
                    appTES=json.entries;
                    console.log("Eligible Faculty Members!!!!");
                    this.facultyListResultEvaluation=appTES;
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

   private getRecordsOfAssignedCoursesByHead(){
     var  appTES:Array<ISetForReview>=[];
       var defer = this.$q.defer();
       this.httpClient.get('/ums-webservice-academic/academic/applicationTES/getRecordsOfAssignedCoursesByHead', 'application/json',
           (json: any, etag: string) => {
               appTES=json.entries;//semesterName
               console.log("Set Evaluation Courses!!!!");
               this.setRivewedCoursesHistory=appTES;
               console.log(this.setRivewedCoursesHistory);
               this.semesterName=json.semesterName;
               defer.resolve(json.entries);
           },
           (response: ng.IHttpPromiseCallbackArg<any>) => {
               console.error(response);
           });
       return defer.promise;

   }
   private getAssignedCourses(teacher_id:string,firstname:string,lastName:string,deptId:string,designation:string){
       this.checkBoxCounter=0;
       this.submit_Button_Disable=true;
       this.facultyId=teacher_id;
       this.fName=firstname+" "+lastName;
       this.deptId=deptId;
       this.designationStatus=designation;
       this.assignedCourses=[];
       this.startDate="";
       this.endDate="";
       this.deadLine=false;
       var appTES:Array<IAssignedCourses>=[];
       var defer = this.$q.defer();
       this.httpClient.get('/ums-webservice-academic/academic/applicationTES/getAssignedCourses/facultyId/'+this.facultyId, 'application/json',
           (json: any, etag: string) => {
               appTES=json.entries;
               console.log("Assigned Courses!!!!");
               this.startDate=json.startDate;
               this.endDate=json.endDate;
               this.deadLine=json.deadLine;
               this.semesterName=json.semesterName;
               console.log(this.startDate+"\n"+this.endDate+"\n"+this.deadLine);
               this.assignedCourses=appTES;
               console.log(this.assignedCourses);
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

   private submit(){
            this.convertToJson(this.assignedCourses).then((app)=>{
                console.log("hello from another side");
                console.log(app);
                this.httpClient.post('academic/applicationTES/saveAssignedCoursesByHead', app, 'application/json')
                    .success((data, status, header, config) => {
                        this.notify.success("Data saved successfully");
                        this.checkBoxCounter=0;
                        this.submit_Button_Disable=true;
                    }).error((data) => {
                    this.notify.error("Error in Saving Data");
                });
       });
   }

        private convertToJson(result: Array<IAssignedCourses>): ng.IPromise<any> {
            let defer:ng.IDeferred<any> = this.$q.defer();
            var completeJson = {};
            console.log("result in convert to Json");
            console.log(result);
            console.log(result.length);
            var jsonObj = [];
            for(var i=0;i<result.length;i++){
                var item = {};
                if(result[i].apply==true) {
                    item["courseId"] = result[i].courseName;
                    item["teacherId"] = result[i].teacherId;
                    item["semesterid"] = result[i].semesterId;
                    item["section"]=result[i].section;
                    item["status"] = this.statusValue;
                    item["deptId"]=this.deptId;
                    console.log("Items");
                    console.log(item);
                    //this.notify.success("sending./.....");
                    jsonObj.push(item);
                }

            }
            completeJson["entries"] = jsonObj;
            console.log("Complete json!!!!!!!!!!!!!!!")
            console.log(completeJson);
            defer.resolve(completeJson);
            return defer.promise;

        }
        private checkMoreThanOneSelectionSubmit(result:IAssignedCourses){
            if(result.apply){
                this.checkBoxCounter++;
                this.enableOrDisableSubmitButton();
            }
            else{
                this.checkBoxCounter--;
                this.enableOrDisableSubmitButton();
            }

            console.log("value: "+this.submit_Button_Disable);
        }
        private enableOrDisableSubmitButton(): void{
            if( this.checkBoxCounter > 0){
                this.submit_Button_Disable=false;
            }else{
                this.submit_Button_Disable=true;
            }
        }
        private close(){
            this.checkBoxCounter=0;
            this.submit_Button_Disable=true;
        }


    }
    UMS.controller("HeadTES",HeadTES)
}