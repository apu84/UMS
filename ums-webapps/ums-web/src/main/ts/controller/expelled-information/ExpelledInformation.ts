module ums{
    interface IConstants{
        id:number;
        name:string;
    }
    interface ICourseList{
        courseId:string;
        courseNo:string;
        courseTitle:string;
        examDate:string;
        regType:number;
        status:number;
        apply:boolean;
    }
    interface IExpelledInfo{
        studentId:string;
        studentName:string;
        semesterId:number;
        semesterName:string;
        deptId:string;
        deptName:string;
        programName:string;
        examType:number;
        examTypeName:string;
        courseId:string;
        courseNo:string;
        courseTitle:string;
        examDate:string;
        expelledReason:string;
        status:number;
        apply:boolean;
    }
    class ExpelledInformation{
      public  examTypeList:Array<IConstants>;
      public  examType:IConstants;
      public  selectedExamTypeId:number;
      public  selectedExamTypeName:string;
      public  examDate:string;
      public  selectedCourseId:string;
      public  selectedRegType:number;
      public  selectedCourseTitle:string;
      public  studentId:string;
      public  reasonOfExpel:string;
      public  courseList:Array<ICourseList>;
      public  enableAddButton:boolean;
      public  showExpelReasonBox:boolean;
      public  showModal:boolean;
      public   stateParams: any;
      public isInsertAvailable:boolean;
      public examTypeListForFilter:Array<IConstants>;
      public examTypeForFilter:IConstants;
      public selectedExamTypeIdForFilter:number;
      public selectedExamTypeNameForFilter:string;
      public semesters:Array<Semester>;
      public semester:Semester;
      public expelInfo:Array<IExpelledInfo>;
      public selectedSemesterId:number;
      public activeSemesterId:number;
      public showDeleteColumn:boolean;
      public submit_Button_Disable:boolean;
      public checkBoxCounter:number;
      public  studentIdForFilter:string;
      public  examDateForFilter: string;
      public  examRoutineArr:any;
      public deptList: Array<IConstants>;
      public deptName: IConstants;
      public selectedDepartmentId:string;
      public hideInsertMode:boolean;

        public static $inject = ['appConstants','HttpClient', '$q', 'notify', '$sce', '$window', 'semesterService', 'facultyService',
            'programService','ExpelledInformationService','$stateParams','examRoutineService'];

        constructor(private appConstants: any,
                    private httpClient: HttpClient,
                    private $q: ng.IQService,
                    private notify: Notify,
                    private $sce: ng.ISCEService,
                    private $window: ng.IWindowService,
                    private semesterService: SemesterService,
                    private facultyService: FacultyService,
                    private programService: ProgramService,
                    private expelledInformationService:ExpelledInformationService,
                    private $stateParams: any,
                    private examRoutineService: ExamRoutineService){
            this.stateParams = $stateParams;
            this.examTypeList=[];
            this.studentId="";
            this.reasonOfExpel="";
            this.examTypeList=this.appConstants.regType;
            this.examType=this.examTypeList[0];
            this.selectedExamTypeId=this.examType.id;
            this.selectedExamTypeName=this.examType.name;
            this.enableAddButton=false;
            this.showExpelReasonBox=false;
            this.showModal=false;
            this.examTypeListForFilter=[];
            this.examTypeListForFilter=this.appConstants.regType;
            this.examTypeForFilter=this.examTypeListForFilter[0];
            this.selectedExamTypeIdForFilter=this.examTypeForFilter.id;
            this.selectedExamTypeNameForFilter=this.examTypeForFilter.name;
            this.showDeleteColumn=false;
            this.submit_Button_Disable=true;
            this.checkBoxCounter=0;
            this.studentIdForFilter="";
            this.deptList = this.appConstants.deptShort;
            this.deptName=this.deptList[0];
            this.isInsertAvailable=false;
            this.getSemesters();
            this.doSomething();
        }
        private deptChanged(deptId:any){
            this.selectedDepartmentId=deptId.id;
        }
        private getSemesters():void{
            this.semesterService.fetchSemesters(11,5).then((semesters:Array<Semester>)=>{
                for(var i=0;i<semesters.length;i++){
                    if(semesters[i].status==1){
                        this.semester = semesters[i];
                        this.activeSemesterId=semesters[i].id;
                        break;
                    }
                }
                this.selectedSemesterId=this.semester.id;
                 this.hideInsertMode=this.activeSemesterId==this.stateParams.semesterId ? true:false;
            }).then((data)=>{
                this.getExamDates();
            })
        }
        private changeExamTypeForFilter(value:any){
            this.selectedExamTypeIdForFilter=value.id;
            this.selectedExamTypeNameForFilter=value.name;
        }
        private doSomething(){
            var res: Array<IExpelledInfo> = [];
            this.expelledInformationService.getExpelledInfo(this.stateParams.semesterId,this.selectedExamTypeIdForFilter).then((data)=>{
                res=data.entries;
                this.expelInfo=res;
                for(let i=0;i<this.expelInfo.length;i++){
                    this.expelInfo[i].examDate = this.expelInfo[i].examDate.replace("/","-");
                    this.expelInfo[i].examDate = this.expelInfo[i].examDate.replace("/","-");
                    if(this.expelInfo[i].status==1){
                        this.showDeleteColumn=true;
                    }else{
                        this.showDeleteColumn=false;
                    }
                }
            });
        }
        private checkMoreThanOneSelectionSubmit(result:IExpelledInfo) {
            if(result.apply){
                this.checkBoxCounter++;
                this.enableOrDisableSubmitButton();
            }
            else{
                this.checkBoxCounter--;
                this.enableOrDisableSubmitButton();
            }
        }

        private enableOrDisableSubmitButton(): void{
            if( this.checkBoxCounter > 0){
                this.submit_Button_Disable=false;
            }else{
                this.submit_Button_Disable=true;
            }
        }

        private deleteExpelInfo(){
            var json= this.convertToJsonForDelete(this.expelInfo);
            this.expelledInformationService.deleteExpelInfo(json).then((data)=>{
            });
            this.doSomething();
            this.checkBoxCounter=0;
            this.submit_Button_Disable=true;
        }
        private ExamDateChange(value:any){
            this.examDateForFilter=value;
        }
        private getExamDates(){
            let examTypeId=this.stateParams.examType == ExamType.REGULAR ? ExamType.REGULAR:ExamType.CARRY_CLEARANCE_IMPROVEMENT;
            this.examRoutineService.getExamRoutineDates(this.stateParams.semesterId,examTypeId).then((examDateArr: any) =>{
                this.examRoutineArr={};
                this.examRoutineArr=examDateArr;
            });
        }

        private enableInsert(){
            this.isInsertAvailable=true;
        }
        private hideInsert():void{
            this.isInsertAvailable=false;
        }
        private changeExamType(value:any){
            this.selectedExamTypeId=value.id;
            this.selectedExamTypeName=value.name;
            this.courseList=[];
            this.showExpelReasonBox=false;
        }
        private searchCourses(){
            if(this.studentId.length>8){
                this.enableAddButton=false;
                this.showExpelReasonBox=true;
                var res: Array<ICourseList> = [];
                this.expelledInformationService.getCourses(this.studentId, this.selectedExamTypeId).then((data) => {
                    res = data.entries;
                    this.courseList = res;
                });
            }else {
                this.notify.warn("Invalid Student Id");
            }

        }
        private checkExpelReason(){
            if(this.reasonOfExpel.length >=5 && this.reasonOfExpel.length <200){
                this.showModal=true;
            }else{
                this.notify.warn("Reason of Expulsion Must be between 5 to 200 Characters");
                this.showModal=false;
            }
        }
        private reset(){
            this.reasonOfExpel="";
            for(let i=0;i<this.courseList.length;i++){
                this.courseList[i].apply=false;
            }
            this.enableAddButton=false;
        }

        private addRecords(){
            var json: any = this.convertToJson();
            this.expelledInformationService.addExpelledStudentsRecord(json).then((data)=>{
                this.searchCourses();
                this.doSomething();
                this.reasonOfExpel="";
                this.enableAddButton=false;
            });
        }
        private selectAction(List:any){
            this.enableAddButton=true;
            for(let i=0;i<this.courseList.length;i++){
                if(this.courseList[i].courseId==List){
                    this.courseList[i].apply=true;
                    this.selectedCourseId=this.courseList[i].courseId;
                    this.selectedCourseTitle=this.courseList[i].courseTitle+"("+this.courseList[i].courseNo+")";
                    this.examDate=this.courseList[i].examDate;
                    this.selectedRegType=this.courseList[i].regType;
                }else{
                    this.courseList[i].apply=false;
                }
            }
        }
        private convertToJsonForDelete(result: Array<IExpelledInfo>): any {
            var completeJson = {};
            var jsonObj = [];
            for (var i = 0; i < result.length; i++) {
                var item = {};
                if (result[i].apply == true) {
                    item["semesterId"] = result[i].semesterId;
                    item["studentId"] = result[i].studentId;
                    item["courseId"] =result[i].courseId;
                    item["examType"] =result[i].examType;
                    jsonObj.push(item);
                }
            }
            completeJson["entries"] = jsonObj;
            return completeJson;
        }


        private convertToJson(): any {
            var defer = this.$q.defer();
            var completeJson = {};
            var jsonObj = [];
                var item = {};
                    item["studentId"] = this.studentId
                    item["courseId"] = this.selectedCourseId;
                    item["examType"] = this.selectedExamTypeId !=ExamType.REGULAR ? ExamType.CARRY_CLEARANCE_IMPROVEMENT:ExamType.REGULAR;
                    item["regType"] = this.selectedRegType;
                    item["expelReason"] = this.reasonOfExpel;
                    item["examDate"]=this.examDate;
                    jsonObj.push(item);
            completeJson["entries"] = jsonObj;
            console.log(completeJson);
            return completeJson;
        }

    }
    UMS.controller("ExpelledInformation",ExpelledInformation)
}
 enum ExamType{
    REGULAR=1,
    CARRY_CLEARANCE_IMPROVEMENT=2,
}