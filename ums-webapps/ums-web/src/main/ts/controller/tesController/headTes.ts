module ums{

    interface IFacultyList{
        teacherId:string;
        firstName:string;
        lastName:string;
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

    }
    class HeadTES{
        public facultyList:Array<IFacultyList>;
        public assignedCourses:Array<IAssignedCourses>;
        public facultyName:string;
        public facultyId:string;
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
               console.log(this.facultyList);
               defer.resolve(json.entries);

           },
           (response: ng.IHttpPromiseCallbackArg<any>) => {
               console.error(response);
           });
       return defer.promise;
   }
   private getAssignedCourses(teacher_id:string){
            this.facultyId=teacher_id;
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


    }
    UMS.controller("HeadTES",HeadTES)
}