module ums{
    interface IConstantsDept {
        id: any;
        name: string;
    }
    interface IConstantsYear {
        id: any;
        name: string;
    }
    interface IConstantsSemester{
        id: any;
        name: string;
    }
    interface IQuestions{
        questionId:number;
        questionDetails:string;
        point:number;
        comment:string;
        observationType:number;
    }
    interface IReport{
        teacherName:string;
        courseNo:string;
        courseTitle:string;
        programName:string;
        score:number;
        percentage:number;
    }
    class IndividualQuestionTesReport{
        public semesters:Array<Semester>;
        public semester:Semester;
        public deptList: Array<IConstantsDept>;
        public deptName: IConstantsDept;
        public yearList: Array<IConstantsYear>;
        public yearName: IConstantsYear;
        public academicSemesterList: Array<IConstantsSemester>;
        public academicSemesterName: IConstantsSemester;
        public questionListAndReview:Array<IQuestions>;
        public searchReport:Array<IReport>;
        public selectedYear:number;
        public selectedSemester:number;
        public selectedSemesterId:number;
        public selectedDepartmentId:string;
        public searchResult:boolean;
        public departmentName:string;
        public year:string;
        public acaSemester:string;
        public semesterName:string;
        public questionDetails:string;
        public questionId:any;
        public selectedRow:any;
        public static $inject = ['appConstants', 'HttpClient', '$q', 'notify', '$sce', '$window', 'semesterService', 'facultyService', 'programService', '$timeout', 'leaveTypeService', 'leaveApplicationService', 'leaveApplicationStatusService', 'employeeService', 'additionalRolePermissionsService', 'userService', 'commonService', 'attachmentService'];
        constructor(private appConstants: any,
                    private httpClient: HttpClient,
                    private $q: ng.IQService,
                    private notify: Notify,
                    private $sce: ng.ISCEService,
                    private $window: ng.IWindowService,
                    private semesterService: SemesterService,
                   ){
            this.searchResult=false;
            this.deptList = [];
            this.deptList = this.appConstants.deptShortName;
            this.deptName=this.deptList[0];
            this.selectedDepartmentId=this.deptName.id;
            this.departmentName=this.deptName.name;
            this.yearList=[];
            this.yearList=this.appConstants.year;
            this.yearName=this.yearList[0];
            this.selectedYear=this.yearName.id;
            this.year=this.yearName.name;
            this.academicSemesterList=[];
            this.academicSemesterList=this.appConstants.semester;
            this.academicSemesterName=this.academicSemesterList[0];
            this.selectedSemester=this.academicSemesterName.id;
            this.acaSemester=this.academicSemesterName.name;
                    this.getSemesters();
        }
        private yearChanged(value:any){
            console.log(value.name);
            this.year=value.name;
            this.selectedYear=value.id;
        }
        private academicSemester(value:any){
            console.log(value.name);
            this.acaSemester=value.name;
            this.selectedSemester=value.id;
        }
        private deptChanged(deptId:any){
            this.selectedDepartmentId=deptId.id;
            this.departmentName=deptId.name;
            console.log(this.selectedDepartmentId);
            this.selectedRow=null;
            if(deptId.id!="01"){
                this.yearList=[];
                this.yearList=this.appConstants.year;
                this.yearName=this.yearList[0];
                this.yearList=this.yearList.filter(a=>a.id !=5);
                this.selectedYear=this.yearName.id;
                this.year=this.yearName.name;
            }else{
                this.yearList=[];
                this.yearList=this.appConstants.year;
                this.yearName=this.yearList[0];
                this.selectedYear=this.yearName.id;
                this.year=this.yearName.name;
            }
        }

        private semesterChanged(val:any){
            console.log("Name: "+val.name+"\nsemesterId: "+val.id);
            this.selectedSemesterId=val.id;
            this.semesterName=val.name;
            this.getSemesterWiseQuestions();
            this.selectedRow=null;

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
                this.semesterName=this.semester.name;
                console.log("Id: "+this.selectedSemesterId);
                this.getSemesterWiseQuestions();
            });
        }
        private getSemesterWiseQuestions(){
            var appTES:Array<IQuestions>=[];
            var defer = this.$q.defer();
            this.httpClient.get('/ums-webservice-academic/academic/applicationTES/getSemesterWiseQuestions/semesterId/'+this.selectedSemesterId, 'application/json',
                (json: any, etag: string) => {
                    appTES=json.entries;
                    console.log("SemesterWise Questions!!!!");
                    this.questionListAndReview=appTES;
                    this.questionListAndReview=this.questionListAndReview.filter(a=>a.observationType!=3);
                    console.log(this.questionListAndReview);
                    defer.resolve(json.entries);
                },
                (response: ng.IHttpPromiseCallbackArg<any>) => {
                    console.error(response);
                });
            return defer.promise;
        }
        private getInfo(pQuestionId:any,pQuestionDetails:any,id:any){
            this.selectedRow=null;
            console.log("Question_Id:"+pQuestionId+"\nQuestion_Details:"+pQuestionDetails+"\nRow_ID:"+id);
            this.selectedRow = id;
            this.questionDetails=pQuestionDetails;
            this.questionId=pQuestionId;


        }
        private clear(){
            this.questionId=null;
            this.questionDetails="";
            console.log("----"+this.questionDetails);
            this.searchResult=false;
        }
        private search(){
            if(this.selectedRow==null){
                this.notify.warn("You Must Select a Question to Perform Search operation");
            }else{
                Utils.expandRightDiv();
                this.searchResult=true;
                this.selectedRow=null;
                var app:Array<IReport>=[];
                var defer = this.$q.defer();
                this.httpClient.get('/ums-webservice-academic/academic/applicationTES/getQuestionWiseReport/deptId/'+this.selectedDepartmentId+'/year/'+
                    this.selectedYear+'/semester/'+this.selectedSemester+'/semesterId/'+this.selectedSemesterId+'/questionId/'+this.questionId, 'application/json',
                    (json: any, etag: string) => {
                        console.log("SemesterWise Questions!!!!");
                        this.searchReport=json;
                        if(this.searchReport.length <=0){
                            this.notify.info("No Courses Found")
                        }
                        console.log(this.searchReport);
                        defer.resolve(json);
                    },
                    (response: ng.IHttpPromiseCallbackArg<any>) => {
                        console.error(response);
                    });
                return defer.promise;
            }

        }
        private  getReport(){
            console.log("hello");
            let contentType: string = UmsUtil.getFileContentType("pdf");
            let fileName = "Evaluation Report_"+this.departmentName+"_"+this.year+"_"+this.acaSemester+"_"+this.semesterName;
            var defer = this.$q.defer();
            this.httpClient.get('/ums-webservice-academic/academic/applicationTES/getQuestionWisePDFReport/deptId/'+this.selectedDepartmentId+'/year/'+
                this.selectedYear+'/semester/'+this.selectedSemester+'/semesterId/'+this.selectedSemesterId+'/questionId/'+this.questionId, 'application/pdf',
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
    UMS.controller("IndividualQuestionTesReport",IndividualQuestionTesReport)
}