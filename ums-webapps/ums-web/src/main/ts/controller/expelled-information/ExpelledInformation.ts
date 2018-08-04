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
            console.log("----State Params---");
            console.log($stateParams);
            console.log("semesterId: "+this.stateParams.semesterId+"\nexamType: "+this.stateParams.examType+"\nexamDate: "+this.stateParams.examDate);
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
            this.getExamDates();
        }
        private deptChanged(deptId:any){
            this.selectedDepartmentId=deptId.id;
            console.log("id: "+this.selectedDepartmentId);

        }
        private getSemesters():void{
            this.semesterService.fetchSemesters(11,5).then((semesters:Array<Semester>)=>{
                this.semesters=semesters;
                console.log(this.semesters);
                for(var i=0;i<semesters.length;i++){
                    if(semesters[i].status==1){
                        this.semester = semesters[i];
                        this.activeSemesterId=semesters[i].id;
                        break;
                    }
                }
                this.selectedSemesterId=this.semester.id;
                console.log("SemesterId: "+this.selectedSemesterId);
                console.log("Active_Semester_Id: "+this.activeSemesterId);
                 this.hideInsertMode=this.activeSemesterId==this.stateParams.semesterId ? true:false;
            });
        }
        private changeExamTypeForFilter(value:any){
            console.log("For Filter"+value.id+"  "+value.name);
            this.selectedExamTypeIdForFilter=value.id;
            this.selectedExamTypeNameForFilter=value.name;
        }
        private doSomething(){
            var res: Array<IExpelledInfo> = [];
            this.expelledInformationService.getExpelledInfo(this.stateParams.semesterId,this.selectedExamTypeIdForFilter).then((data)=>{
                console.log("*******Data*********");
                res=data.entries;
                this.expelInfo=res;
                console.log(this.expelInfo);
                for(let i=0;i<this.expelInfo.length;i++){
                    this.expelInfo[i].examDate = this.expelInfo[i].examDate.replace("/","-");
                    this.expelInfo[i].examDate = this.expelInfo[i].examDate.replace("/","-");
                    if(this.expelInfo[i].status==1){
                        this.showDeleteColumn=true;
                    }else{
                        this.showDeleteColumn=false;
                    }
                }
            })
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

            console.log("value:"+this.submit_Button_Disable);

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
                console.log(data);
            });
            this.doSomething();
            this.checkBoxCounter=0;
            this.submit_Button_Disable=true;
            console.log("Rumi");
        }
        private ExamDateChange(value:any){
            console.log(value);
            this.examDateForFilter=value;
        }
        private getExamDates(){
            let examTypeId=this.stateParams.examType == ExamType.REGULAR ? ExamType.REGULAR:ExamType.CARRY_CLEARANCE_IMPROVEMENT;
            console.log("examTypeId: "+examTypeId);
            this.examRoutineService.getExamRoutineDates(this.stateParams.semesterId,examTypeId).then((examDateArr: any) =>{
                this.examRoutineArr={};
                console.log("****Exam Dates***");
                this.examRoutineArr=examDateArr;
                console.log(this.examRoutineArr);
            })

        }

        private enableInsert(){
            console.log("*************************");
            this.isInsertAvailable=true;
        }
        private hideInsert():void{
            this.isInsertAvailable=false;
        }
        private changeExamType(value:any){
            console.log("Exam Type For Insert: "+value.id);
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
                    console.log("Course/List");
                    res = data.entries;
                    this.courseList = res;
                    console.log(this.courseList);
                })
            }else {
                this.notify.warn("Invalid Student Id");
            }

        }
        private checkExpelReason(){
            console.log("i am in")
            if(this.reasonOfExpel.length >=5 && this.reasonOfExpel.length <200){
                console.log("Length: "+this.reasonOfExpel.length);
                this.showModal=true;
            }else{
                this.notify.warn("Reason of Expulsion Must be between 5 to 200 Characters");
                this.showModal=false;
            }
        }
        private reset(){
            console.log("reset");
            this.reasonOfExpel="";
            for(let i=0;i<this.courseList.length;i++){
                this.courseList[i].apply=false;
            }
            this.enableAddButton=false;
        }

        private addRecords(){
            console.log("Student Id: "+this.studentId+"\nExamType: "+this.selectedExamTypeId);
            console.log("Expel Reason: "+this.reasonOfExpel);
            console.log("Course Id: "+this.selectedCourseId);
            var json: any = this.convertToJson();
            this.expelledInformationService.addExpelledStudentsRecord(json).then((data)=>{
                console.log(data);
                this.searchCourses();
                this.doSomething();
                this.reasonOfExpel="";
                this.enableAddButton=false;
            })


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
            console.log("result");
            console.log(result);
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
            console.log(completeJson);
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