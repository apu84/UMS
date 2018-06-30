module ums{
    interface IConstants{
        id:any;
        name:string;
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
     class ViewExpelledInformation{
         examTypeList:Array<IConstants>;
         examType:IConstants;
         public deptList: Array<IConstants>;
         public deptName: IConstants;
         public selectedDepartmentId:string;
         selectedExamTypeId:number;
         selectedExamTypeName:string;
         public semesters:Array<Semester>;
         public semester:Semester;
         public expelInfo:Array<IExpelledInfo>;
         public selectedSemesterId:number;
         showDeleteColumn:boolean;
         submit_Button_Disable:boolean;
         checkBoxCounter:number;
         showFilterOptions:boolean;
         studentId:string;
         examDate: string;
         examRoutineArr:any;
        public static $inject = ['appConstants','HttpClient', '$q', 'notify', '$sce', '$window', 'semesterService', 'facultyService', 'programService','ExpelledInformationService','examRoutineService'];

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
                    private examRoutineService: ExamRoutineService){
            this.examTypeList=[];
            this.examTypeList=this.appConstants.regType;
            this.examType=this.examTypeList[0];
            this.selectedExamTypeId=this.examType.id;
            this.selectedExamTypeName=this.examType.name;
            this.showDeleteColumn=false;
            this.submit_Button_Disable=true;
            this.checkBoxCounter=0;
            this.showFilterOptions=false;
            this.studentId="";
            this.deptList = [];
            this.deptList = this.appConstants.deptShort;
            this.deptName=this.deptList[0];
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
                         break;
                     }
                 }
                 this.selectedSemesterId=this.semester.id;
                 console.log("I____Id: "+this.selectedSemesterId);
             });
         }
         private semesterChanged(val:any){
             console.log("Name: "+val.name+"\nsemesterId: "+val.id);
             this.selectedSemesterId=val.id;
             this.getExamDates();
         }
         private changeExamType(value:any){
             console.log(value.id+"  "+value.name);
             this.selectedExamTypeId=value.id;
             this.selectedExamTypeName=value.name;
             this.getExamDates();

         }
        private doSomething(){
            Utils.expandRightDiv();
            this.showFilterOptions=true;
            var res: Array<IExpelledInfo> = [];
            this.expelledInformationService.getExpelledInfo(this.selectedSemesterId,this.selectedExamTypeId).then((data)=>{
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
           var json= this.convertToJson(this.expelInfo);
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

        }
        private getExamDates(){
            let examTypeId=this.selectedExamTypeId == ExamType.REGULAR ? ExamType.REGULAR:ExamType.CARRY_CLEARANCE_IMPROVEMENT;
            console.log("examTypeId: "+examTypeId);
            this.examRoutineService.getExamRoutineDates(this.selectedSemesterId,examTypeId).then((examDateArr: any) =>{
                this.examRoutineArr={};
                console.log("****Exam Dates***");
                this.examRoutineArr=examDateArr;
                console.log(this.examRoutineArr);
            })

        }
         private convertToJson(result: Array<IExpelledInfo>): any {
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

    }
    UMS.controller("ViewExpelledInformation",ViewExpelledInformation);
}
