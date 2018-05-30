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
        selectedCourseTitle:string;
        studentId:string;
        reasonOfExpel:string;
        courseList:Array<ICourseList>;

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
            this.examTypeList=this.appConstants.examType;
            this.examType=this.examTypeList[0];
            this.selectedExamTypeId=this.examType.id;
            this.selectedExamTypeName=this.examType.name;

        }
        private changeExamType(value:any){
            console.log(value.id+"  "+value.name);
            this.selectedExamTypeId=value.id;
            this.selectedExamTypeName=value.name;

        }

        private searchCourses(){
            Utils.expandRightDiv();
            var res: Array<ICourseList> = [];
            this.expelledInformationService.getCourses(this.studentId,this.selectedExamTypeId).then((data)=>{
                console.log("****Course/List*****");
                res=data.entries;
                this.courseList=res;
                console.log(this.courseList);
            })

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

            })


        }
        private selectAction(List:any){
            for(let i=0;i<this.courseList.length;i++){
                if(this.courseList[i].courseId==List){
                    this.courseList[i].apply=true;
                    this.selectedCourseId=this.courseList[i].courseId;
                    this.selectedCourseTitle=this.courseList[i].courseTitle+"("+this.courseList[i].courseNo+")";
                    this.examDate=this.courseList[i].examDate;
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
                    item["examType"] = this.selectedExamTypeId;
                    item["expelReason"] = this.reasonOfExpel;
                    jsonObj.push(item);
            completeJson["entries"] = jsonObj;
            console.log(completeJson);
            return completeJson;
        }

    }
    UMS.controller("ExpelledInformation",ExpelledInformation)
}