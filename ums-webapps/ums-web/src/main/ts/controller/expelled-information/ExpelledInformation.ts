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
    class ExpelledInformation{
        examTypeList:Array<IConstants>;
        examType:IConstants;
        selectedExamTypeId:number;
        selectedExamTypeName:string;
        examDate:string;
        selectedCourseId:string;
        selectedRegType:number;
        selectedCourseTitle:string;
        studentId:string;
        reasonOfExpel:string;
        courseList:Array<ICourseList>;
        enableAddButton:boolean;
        showExpelReasonBox:boolean;
        showModal:boolean;

        public static $inject = ['appConstants','HttpClient', '$q', 'notify', '$sce', '$window', 'semesterService', 'facultyService', 'programService','ExpelledInformationService'];

        constructor(private appConstants: any,
                    private httpClient: HttpClient,
                    private $q: ng.IQService,
                    private notify: Notify,
                    private $sce: ng.ISCEService,
                    private $window: ng.IWindowService,
                    private semesterService: SemesterService,
                    private facultyService: FacultyService,
                    private programService: ProgramService,
                    private expelledInformationService:ExpelledInformationService){
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

        }
        private changeExamType(value:any){
            this.selectedExamTypeId=value.id;
            this.selectedExamTypeName=value.name;
            this.courseList=[];
            this.showExpelReasonBox=false;


        }

        private searchCourses(){
            if(this.studentId.length>8){
                Utils.expandRightDiv();
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