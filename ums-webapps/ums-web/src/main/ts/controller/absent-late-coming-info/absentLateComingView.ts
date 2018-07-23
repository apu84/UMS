module ums{
    import IAbsLateComeInfo= ums.IAbsLateComeInfo;
    interface IConstants{
        id:any;
        name:string;
    }
    class AbsentLateComingView{
        public examTypeList:Array<IConstants>;
        public examType:IConstants;
        public selectedExamTypeId:number;
        public selectedExamTypeName:string;
        public semesters:Array<Semester>;
        public semester:Semester;
        public selectedSemesterId:number;
        public examRoutineArr:any;
        public selectedExamDate:string;
        public activeSemesterId:number;
        public semesterName:string;
        public absPreStatusList:Array<IConstants>;
        public absPreStatus:IConstants;
        public selectedAbsPreStatusId:number;
        examDate: string;
        public deptList: Array<IConstants>;
        public deptName: IConstants;
        public selectedDepartmentId:string;
        public absLateComeInfo:Array<IAbsLateComeInfo>;
        public isArrivalTimeAllowed:boolean;
        public showDeleteColumn:boolean;
        public submit_Button_Disable:boolean;
        public checkBoxCounter:number;
        public showFilterOptions:boolean;
        public showRightDiv:boolean;
        public selectedAbsPreStatusName:string;
        public static $inject = ['appConstants','HttpClient', '$q', 'notify', '$sce', '$window', 'semesterService', 'facultyService',
            'programService','DailyExamAttendanceReportService','examRoutineService','classRoomService','employeeService','absLateComingService'];

        constructor(private appConstants: any,
                    private httpClient: HttpClient,
                    private $q: ng.IQService,
                    private notify: Notify,
                    private $sce: ng.ISCEService,
                    private $window: ng.IWindowService,
                    private semesterService: SemesterService,
                    private facultyService: FacultyService,
                    private programService: ProgramService,
                    private dailyExamAttendanceReportService: DailyExamAttendanceReportService,
                    private examRoutineService: ExamRoutineService,
                    private classRoomService:ClassRoomService,
                    private employeeService:EmployeeService,
                    private absLateComingService:AbsLateComingService){
            this.examTypeList=[];
            this.examTypeList=this.appConstants.examType;
            this.examType=this.examTypeList[0];
            this.selectedExamTypeId=this.examType.id;
            this.selectedExamTypeName=this.examType.name;
            this.absPreStatusList=[];
            this.absPreStatusList=this.appConstants.absentPresentStatus;
            this.absPreStatus=this.absPreStatusList[0];
            this.selectedAbsPreStatusId=this.absPreStatus.id;
            this.selectedAbsPreStatusName=this.absPreStatus.name;
            console.log("****"+this.selectedAbsPreStatusName);
            this.deptList = [];
            this.deptList = this.appConstants.deptShort;
            this.deptName=this.deptList[0];
            this.isArrivalTimeAllowed=false;
            this.showDeleteColumn=true;
            this.submit_Button_Disable=true;
            this.checkBoxCounter=0;
            this.showFilterOptions=false;
            this.showRightDiv=false;
            this.getSemesters();
        }
        private changeExamType(value:any){
            this.showRightDiv=false;
            console.log(value.id+"  "+value.name);
            this.selectedExamTypeId=value.id;
            this.selectedExamTypeName=value.name;
            this.getExamDates();

        }
        private deptChanged(deptId:any){
            this.selectedDepartmentId=deptId.id;
            console.log("id: "+this.selectedDepartmentId);

        }
        private changeAbsPreStatus(value:any) {
            console.log(value.id + "  " + value.name);
            this.selectedAbsPreStatusId = value.id;
            this.selectedAbsPreStatusName=value.name;
            this.isArrivalTimeAllowed=this.selectedAbsPreStatusId==1? false:true;
            this.unSelectAll();

        }
        private unSelectAll():void{
            this.submit_Button_Disable=true;
            for(let i=0;i<this.absLateComeInfo.length;i++){
                this.absLateComeInfo[i].apply=false;
            }
        }
        private getExamDates(){
            let examTypeId=this.selectedExamTypeId == ExamType.REGULAR ? ExamType.REGULAR:ExamType.CARRY_CLEARANCE_IMPROVEMENT;
            console.log("examTypeId: "+examTypeId);
            this.examRoutineArr=null;
            this.examRoutineService.getExamRoutineDates(this.selectedSemesterId,examTypeId).then((examDateArr: any) =>{
                this.examRoutineArr={};
                console.log("****Exam Dates***");
                this.examRoutineArr=examDateArr;
                console.log(this.examRoutineArr);
            })
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
                this.semesterName=this.semester.name;
                this.activeSemesterId=this.semester.id
                console.log("I____Id: "+this.selectedSemesterId);
            });
            this.getExamDates();
        }
        private semesterChanged(val:any){
            this.showRightDiv=false;
            console.log("Name: "+val.name+"\nsemesterId: "+val.id);
            this.selectedSemesterId=val.id;
            this.semesterName=val.name;
            this.showDeleteColumn=this.activeSemesterId==this.selectedSemesterId ? true:false;
            this.getExamDates();
        }
        private ExamDateChange(value:any){
            console.log(""+value);
        }
        private doSomething():void{
            this.showRightDiv=true;
            Utils.expandRightDiv();
            this.getData();
        }
        private getData():void{
            var reg:Array<IAbsLateComeInfo>=[];
            this.absLateComingService.getAbsLateComeInfo(this.selectedSemesterId,this.selectedExamTypeId).then((data)=>{
                reg=data;
                this.absLateComeInfo=reg;
                console.log("*********");
                console.log(this.absLateComeInfo);
            })
    }
        private checkMoreThanOneSelectionSubmit(result:any) {
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
            var json= this.convertToJson(this.absLateComeInfo);
            this.absLateComingService.deleteAbsLateComeInfo(json).then((data)=>{
                this.getData();
                console.log(data);
            });
            this.checkBoxCounter=0;
            this.submit_Button_Disable=true;
            console.log("Rumi");
        }
        private convertToJson(result: Array<IAbsLateComeInfo>): any {
            var completeJson = {};
            console.log("result");
            console.log(result);
            var jsonObj = [];
            for (var i = 0; i < result.length; i++) {
                var item = {};
                if (result[i].apply == true) {
                   item["semesterId"] = result[i].semesterId;
                    item["examType"] =result[i].examType;
                    item["employeeId"] = result[i].employeeId;
                    item["examDate"] =result[i].examDate;
                    jsonObj.push(item);
                }
            }
            completeJson["entries"] = jsonObj;
            console.log(completeJson);
            return completeJson;
        }

    }
    UMS.controller("AbsentLateComingView",AbsentLateComingView);
}