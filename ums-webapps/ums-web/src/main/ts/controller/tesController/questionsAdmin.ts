module ums{
    interface IConstantsObservationType {
        id: any;
        name: string;
    }
    interface IQuestions{
        questionId:number;
        questionDetails:string;
        observationType:number;
        apply:boolean;
        status:boolean;
    }
    interface  IDeleteQuestions{
        questionId:number;
        questionDetails:string;
        observationType:number;
        apply:boolean;
    }
    class QuestionsAdmin{
        public observationTypeList: Array<IConstantsObservationType>;
        public observationTypeStatus: IConstantsObservationType;
        public questionsList:Array<IQuestions>;
        public questionsMigrationList:Array<IQuestions>;
        public MigrationModalList:Array<IQuestions>;
        public deleteQuestionList:Array<IDeleteQuestions>;
        public semesters:Array<Semester>;
        public semester:Semester;
        public selectedSemesterName:string;
        public selectedSemesterId:number;
        public addNewQuestionStatus:boolean;
        public setQuestionsForEvaluationStatus:boolean;
        public migrateQuestionStatus:boolean;
        public selectedObTypeId:number;
        public questionDetails:string;
        public observationTypeName:string;
        public submit_Button_Disable:boolean;
        public checkBoxCounter:number;
        public semesterName:string;
        public startDate:string;
        public endDate:string;
        public deadLine:boolean;
        public initialStatus:boolean;
        public deadLineStatus:string;
        public setQuestionShowStatus:boolean;
        public deleteQuestionStatus:boolean;
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
            this.initialStatus=true;
            this.setQuestionShowStatus=true;
            this.setQuestionsForEvaluationStatus=false;
            this.addNewQuestionStatus=false;
            this.migrateQuestionStatus=false;
            this.deleteQuestionStatus=false;
            this.observationTypeList=[];
            this.observationTypeList = this.appConstants.observationTypeTes;
            console.log("***")
            console.log(this.observationTypeList);
            this.observationTypeStatus=<IConstantsObservationType>{};
            this.observationTypeStatus=this.observationTypeList[0];
            console.log(this.observationTypeStatus);
            this.selectedObTypeId=this.observationTypeStatus.id;
            this.observationTypeName=this.observationTypeStatus.name;
            console.log("****selected-id****");
            console.log(this.selectedObTypeId);
            this.questionDetails="";
            this.submit_Button_Disable=true;
            this.checkBoxCounter=0;
            this.startDate="";
            this.endDate="";
            this.deadLine=false;
          this.getInitialSemesterParameter();

        }
        private getInitialSemesterParameter(){
            console.log("-------");
            var defer = this.$q.defer();
            this.httpClient.get('/ums-webservice-academic/academic/applicationTES/getInitialSemesterParameter', 'application/json',
                (json: any, etag: string) => {
                    this.startDate=json.startDate;
                    this.endDate=json.endDate;
                    this.deadLine=json.deadLine;
                    this.semesterName=json.semesterName;
                    this.deadLineStatus=this.deadLine==true ?'Available':'Not Allowed';
                    defer.resolve(json.entries);
                },
                (response: ng.IHttpPromiseCallbackArg<any>) => {
                    console.error(response);
                });
            return defer.promise;

        }
        private getBack(){
            this.deleteQuestionStatus=false;
            this.setQuestionShowStatus=true;
        }
        private getDeleteEligibleQuestions(){
            this.setQuestionShowStatus=false;
            this.deleteQuestionStatus=true;
            var app:Array<IDeleteQuestions>=[];
            this.deleteQuestionList=[];
            var defer = this.$q.defer();
            this.httpClient.get('/ums-webservice-academic/academic/applicationTES/getDeleteEligibleQuestions', 'application/json',
                (json: any, etag: string) => {
                    app=json.entries;
                    console.log("Delete Questions!!!!");
                    console.log(json.entries);
                    this.deleteQuestionList=app;
                    defer.resolve(json.entries);
                },
                (response: ng.IHttpPromiseCallbackArg<any>) => {
                    console.error(response);
                });
            return defer.promise;
        }
        private deleteQuestion(){
            let setQuestionStatus=1;
            this.delete(this.deleteQuestionList,setQuestionStatus);
        }

        private addQuestionSubmit(){
            //this.questionDetails.length>0 && this.questionDetails.length<10
            if(this.questionDetails=="" || this.questionDetails==null){

                this.notify.warn("Question details is Empty!")
            }else {
                if(this.questionDetails.length>0 && this.questionDetails.length<=200) {
                    this.save();
                }else {
                    this.notify.warn("Maximum length for question details is 200")
                }
            }

        }
      //  private getQuestion
      private changeObservationStatus(value:any){
          this.selectedObTypeId=value.id;
          this.observationTypeName=value.name;
          console.log(this.selectedObTypeId);

      }
        private setQuestionsForEvaluation(){
            Utils.expandRightDiv();
            this.setQuestionsForEvaluationStatus=true;
            this.addNewQuestionStatus=false;
            this.migrateQuestionStatus=false;
            this.initialStatus=false;
            this.checkBoxCounter=0;
            this.submit_Button_Disable=true;
            this.getQuestions();


        }
        private addNewQuestions(){
            Utils.expandRightDiv();
            this.addNewQuestionStatus=true;
            this.migrateQuestionStatus=false;
            this.setQuestionsForEvaluationStatus=false;
            this.initialStatus=false;

        }
        private migrateQuestions(){
            Utils.expandRightDiv();
            this.migrateQuestionStatus=true;
            this.setQuestionsForEvaluationStatus=false;
            this.addNewQuestionStatus=false;
            this.checkBoxCounter=0;
            this.submit_Button_Disable=true;
            this.initialStatus=false;
            this.getSemesters();

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
                console.log("---->"+this.selectedSemesterId);
                this.getMigrationQuestionList();
            });
        }
        private semesterChanged(val:any){
            console.log("Name: "+val.name+"\nsemesterId: "+val.id);
            this.selectedSemesterId=val.id;
            this.getMigrationQuestionList();

            // this.getStudentSubmitDeadLine();

        }
        private getMigrationQuestionList(){
            var app:Array<IQuestions>=[];
            this.questionsMigrationList=[];
            var defer = this.$q.defer();
            console.log("Semester_Id:"+this.selectedSemesterId);
            this.httpClient.get('/ums-webservice-academic/academic/applicationTES/getMigrateQuestions/semesterId/'+this.selectedSemesterId, 'application/json',
                (json: any, etag: string) => {
                    app=json.entries;
                    console.log("Migration Questions!!!!");
                    console.log(json.entries);
                    this.questionsMigrationList=app;
                    defer.resolve(json.entries);
                },
                (response: ng.IHttpPromiseCallbackArg<any>) => {
                    console.error(response);
                });
            return defer.promise;
        }

        private submitSelectedQuestions(){
            let setQuestionStatus=1;
            this.setQuestions(this.questionsList,setQuestionStatus);


        }
       private submitSelectedMigrationQuestions(){
            let setMigrationQuestionStatus=2;
            this.setQuestions(this.questionsMigrationList,setMigrationQuestionStatus);
        }
        private delete(result:Array<any>,parameter:number){
            console.log(result);
            this.convertToJsonSetQuestion(result).then((app: any) =>{
                console.log("Bye From Another Side!!!")
                console.log(app);

               this.httpClient.put('academic/applicationTES/deleteQuestion', app, 'application/json')
                    .success((data, status, header, config) => {
                        this.notify.success("Data deleted Successfully");
                        this.checkBoxCounter=0;
                        this.submit_Button_Disable=true;
                        this.getDeleteEligibleQuestions();
                        if(parameter==1){
                            this.getQuestions();
                        }else{
                            this.getMigrationQuestionList();
                        }

                    }).error((data) => {
                    this.notify.error("Error in Saving Data");
                });

            });
        }


        private setQuestions(result:Array<any>,parameter:number){
            console.log(result);
            this.convertToJsonSetQuestion(result).then((app: any) =>{
                console.log("hello From Another Side!!!")
                console.log(app);

                this.httpClient.post('academic/applicationTES/setQuestion', app, 'application/json')
                    .success((data, status, header, config) => {
                        this.notify.success("Data saved successfully");
                        this.checkBoxCounter=0;
                        this.submit_Button_Disable=true;
                        if(parameter==1){
                            this.getQuestions();
                        }else{
                         this.getMigrationQuestionList();
                        }

                    }).error((data) => {
                    this.notify.error("Error in Saving Data");
                });

            });
        }

        private getQuestions(){
            var app:Array<IQuestions>=[];
            this.questionsList=[];
            var defer = this.$q.defer();
            this.httpClient.get('/ums-webservice-academic/academic/applicationTES/getQuestions', 'application/json',
                (json: any, etag: string) => {
                app=json.entries;
                    console.log("Questions!!!!");
                    console.log(json.entries);
                    this.questionsList=app;
                    defer.resolve(json.entries);
                },
                (response: ng.IHttpPromiseCallbackArg<any>) => {
                    console.error(response);
                });
            return defer.promise;
        }
        private  save(){
            this.convertToJson(this.questionDetails,this.selectedObTypeId).then((app: any) =>{
                console.log("hello From Another Side!!!")
                console.log(app);

                this.httpClient.post('academic/applicationTES/addQuestion', app, 'application/json')
                    .success((data, status, header, config) => {
                        this.notify.success("Data saved successfully");
                       this.questionDetails="";
                    }).error((data) => {
                    this.notify.error("Error in Saving Data");
                });

            });
        }

        private convertToJson(questionDetails,observationType): ng.IPromise<any> {
            let defer:ng.IDeferred<any> = this.$q.defer();
            var completeJson = {};

            var jsonObj = [];

                var item = {};
                item["questionDetails"]=questionDetails;
                item["observationType"]=observationType;
                console.log("Items");
                console.log(item);
                jsonObj.push(item);
            completeJson["entries"] = jsonObj;
            console.log("Complete json!!!!!!!!!!!!!!!")
            console.log(completeJson);
            defer.resolve(completeJson);
            return defer.promise;
        }
        private convertToJsonSetQuestion(result: Array<IQuestions>): ng.IPromise<any> {
            let defer:ng.IDeferred<any> = this.$q.defer();
            var completeJson = {};
            console.log("result in convert to Json");
            console.log(result);
            console.log(result.length);
            let selectedQuestionStatus=1;
            var jsonObj = [];
            for(var i=0;i<result.length;i++){
                var item = {};
                if(result[i].apply==true) {
                    item["questionId"] = result[i].questionId;
                    item["status"] = selectedQuestionStatus;
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

        private checkMoreThanOneSelectionSubmit(result:IQuestions){
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
    UMS.controller("QuestionsAdmin",QuestionsAdmin);
}