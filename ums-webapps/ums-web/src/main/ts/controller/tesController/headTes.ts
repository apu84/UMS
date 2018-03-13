module ums{

    interface IFacultyList{
        teacherId:string;
        firstName:string;
        lastName:string;
        deptId:number;
        deptShortName:string;
        designation:string;
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
        comment:string;
        observationType:number;
    }
    class HeadTES{
        public facultyList:Array<IFacultyList>;
        public assignedCourses:Array<IAssignedCourses>;
        public setRivewedCoursesHistory:Array<ISetForReview>;
        public studentComments:Array<IReport>;
        public facultyName:string;
        public facultyId:string;
        public fName:string;
        public statusValue:number;
        public semesterName:string;
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

        }
   private getAllFacultyMembers(){
            this.facultyList=[];
       var appTES:Array<IFacultyList>=[];
       var defer = this.$q.defer();
       this.httpClient.get('/ums-webservice-academic/academic/applicationTES/getAllFacultyMembers', 'application/json',
           (json: any, etag: string) => {
               appTES=json.entries;
               console.log("Faculty Members!!!!");
               this.facultyList=appTES;
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
   private getAssignedCourses(teacher_id:string,firstname:string,lastName:string,deptId:string){
       this.checkBoxCounter=0;
       this.submit_Button_Disable=true;
            this.facultyId=teacher_id;
            this.fName=firstname+" "+lastName;
            this.deptId=deptId;
            console.log("Department Id"+this.deptId);
       this.assignedCourses=[];
       var appTES:Array<IAssignedCourses>=[];
       var defer = this.$q.defer();
       this.httpClient.get('/ums-webservice-academic/academic/applicationTES/getAssignedCourses/facultyId/'+this.facultyId, 'application/json',
           (json: any, etag: string) => {
               appTES=json.entries;
               console.log("Assigned Courses!!!!");
               this.assignedCourses=appTES;
               console.log(this.assignedCourses);
               defer.resolve(json.entries);
           },
           (response: ng.IHttpPromiseCallbackArg<any>) => {
               console.error(response);
           });


       return defer.promise;

   }

   private getResults(){
            this.studentComments=[];
       this.classObservationTotalSPoints=0;
       this.classObservationTotalStudent=0;
       this.classObservationAverage=0;
       this.nonClassObservationTotalSPoints=0;
       this.nonClassObservationTotalStudent=0;
       this.nonClassObservationAverage=0;
       var appTES:Array<IReport>=[];
       var defer = this.$q.defer();
       var counterObType1=0;
       var counterObType2=0;
       this.httpClient.get('/ums-webservice-academic/academic/applicationTES/getResult', 'application/json',
           (json: any, etag: string) => {
                console.log(json);
                appTES=json;
               this.studentComments=appTES;
               for(let i=0;i<this.studentComments.length;i++){
                   if(this.studentComments[i].observationType==1){
                       counterObType1++;
                       this.classObservationTotalSPoints =this.classObservationTotalSPoints+this.studentComments[i].totalScore;
                       this.classObservationTotalStudent =this.classObservationTotalStudent+this.studentComments[i].studentNo;
                       this.classObservationAverage=this.classObservationAverage+this.studentComments[i].averageScore;
                   }else{
                       counterObType2++;
                       this.nonClassObservationTotalSPoints =this.nonClassObservationTotalSPoints+this.studentComments[i].totalScore;
                       this.nonClassObservationTotalStudent=this.nonClassObservationTotalStudent+this.studentComments[i].studentNo;
                       this.nonClassObservationAverage=this.nonClassObservationAverage+this.studentComments[i].averageScore;
                   }
               }
               this.classObservationAverage=(this.classObservationAverage/counterObType1);
               this.nonClassObservationAverage=(this.nonClassObservationAverage/counterObType2)
               this.getComment();
               defer.resolve(json);
           },
           (response: ng.IHttpPromiseCallbackArg<any>) => {
               console.error(response);
           });
       return defer.promise;
   }
   private  getComment(){
       var defer = this.$q.defer();
       var counterObType1=0;
       var counterObType2=0;
       this.httpClient.get('/ums-webservice-academic/academic/applicationTES/getComment', 'application/json',
           (json: any, etag: string) => {
           console.log("comment---------");
               console.log(json);

               defer.resolve(json);
           },
           (response: ng.IHttpPromiseCallbackArg<any>) => {
               console.error(response);
           });
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