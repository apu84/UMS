module ums{
    interface IConstants{
        id:any;
        name:string;
    }
    class OptCourseOffer{
        public departmentId:string;
        public programId:number;
        public programs:Array<Program>;
        public program:Program;
        public semesters:Array<Semester>;
        public semester:Semester;
        public selectedSemesterId:number;
        public activeSemesterId:number;
        yearSemList:Array<IConstants>;
        yearSem:IConstants;
        yearSemName:string;
        public optCourseList:Array<IOptCourseList>;
        public tempList:Array<IOptCourseList>;
        public optOfferedCourseList:Array<any>;
        public isSubGroupAvailable:boolean;
        public isMandatory:boolean;
        public groupName:string;
        public subGroupName:string;
        public parentGrpName:string;
        public indexValue:number;
        draggables:Array<any>;
        selectedComponents:any;
        selectedComponents1:any;
        sortingLog:any;
        draggableOptions:any;
        sortableOptions:any;
        sortableOptions1:any;
        connectWithList:any;
        pushDataIntoList:boolean;
        destinationDiv:string;
        public static $inject = ['appConstants','HttpClient', '$q', 'notify', '$sce', '$window', 'semesterService', 'facultyService',
            'programService','commonService','optCourseOfferService'];

        constructor(private appConstants: any,
                    private httpClient: HttpClient,
                    private $q: ng.IQService,
                    private notify: Notify,
                    private $sce: ng.ISCEService,
                    private $window: ng.IWindowService,
                    private semesterService: SemesterService,
                    private facultyService: FacultyService,
                    private programService: ProgramService,
                    private commonService:CommonService,
                    private optCourseOfferService:OptCourseOfferService
                    ){
            this.isMandatory=false;
            this.groupName="";
            this.subGroupName="";
            this.parentGrpName="";
            this.optOfferedCourseList=[];
            this.connectWithList=[];
            this.commonService.fetchCurrentUser().then((departmentJson: any) => {
                this.departmentId = departmentJson.id;
                this.isSubGroupAvailable= this.departmentId=="05" ? true:false;
                console.log("Dept Id: ");
                console.log(this.departmentId);
                this.getSemesters();
            }).then((data)=>{
                this.programService.fetchProgram(11).then((data)=>{
                    var app: Array<Program> = [];
                    app=data;
                    this.programs=app;
                    this.programs=this.programs.filter(f=>f.departmentId==this.departmentId);
                    this.program=this.programs[0];
                    this.programId=this.program.id;
                }).then((data)=>{
                    this.yearSemList=[];
                    this.yearSemList=this.appConstants.opYearSemester;
                    console.log(this.yearSemList);
                    if(this.departmentId=="04" || this.departmentId=="03"){
                        this.yearSemList=this.yearSemList.filter(f=>f.id=="42");
                    }
                    this.yearSem=this.yearSemList[0];
                    this.yearSemName=this.yearSem.name;
                    console.log("Y-S: "+this.yearSemName);
                });
            });
            this.pushDataIntoList=false;
            this.selectedComponents1=[];
           // this.initialization();
            this.drag();
            this.sortingLog=[];
            this.sortableOptions={};
            this.sortableOptions1={};
        }

        public drag(){
            console.log(this.connectWithList);
            this.connectWithList=['.connected-drop-target-sortable1'];
            let tmpThis = this;
            let gpName=this;
                this.draggableOptions = {
                    connectWith: this.connectWithList,
                    stop: function (e, ui) {
                        // if the element is removed from the first container
                        if (ui.item.sortable.source.hasClass('draggable-element-container') &&
                            ui.item.sortable.droptarget &&
                            ui.item.sortable.droptarget != ui.item.sortable.source) {
                            // restore the removed item
                            console.log("push");
                            ui.item.sortable.sourceModel.push(ui.item.sortable.model);
                            console.log(ui.item.sortable.droptarget[0].classList[0]);
                            gpName.destinationDiv=ui.item.sortable.droptarget[0].classList[0].toString();
                            tmpThis.insert(ui.item.sortable.model);
                        }
                    },
                };
        }

        public insert(data:any){
            console.log("Group Name: "+this.destinationDiv);
            console.log(data);
           var index = this.optOfferedCourseList.map(e => e.groupName).indexOf(this.destinationDiv);
           console.log(index);
            this.optOfferedCourseList[index].courses.push({
                id:data.id,
                no:data.no,
                title:data.title,
                crHr:data.crHr,
                courseType:data.courseType
            });
            console.log(this.optOfferedCourseList);
         //  this.draggables=[];
            //this.draggables=this.tempList;
            console.log("***insert***");
          //  this.search();
        }
        public logModels() {
            console.log("-----hello----");
            this.sortingLog=[];
            console.log(this.selectedComponents1);
        }

        private subGroup():void{
            console.log("Sub Group");
          this.optOfferedCourseList[this.indexValue].subGrpCourses.push({
              groupId:null,
              groupName:this.subGroupName
          });
          console.log(this.optOfferedCourseList);
        }
       private getParentGrpId(data:any,index:number,course:any):void{
            this.parentGrpName=data;
            this.indexValue=this.optOfferedCourseList.indexOf(course);
       }
        private addOfferedCourse():void{
            console.log("Main Group");
            var items,isNameExists=0;

            if(this.optOfferedCourseList.length>0){
                isNameExists=this.optOfferedCourseList.filter(f=>f.groupName==this.groupName).length;
            }
            if(isNameExists==0){
                items=
                    {   groupId:null,
                        groupName:this.groupName==null ? null:this.groupName,
                        isMandatory:this.isMandatory,
                        courses:[],
                        subGrpCourses:[]
                    };
                this.optOfferedCourseList.push(items);
            }else {
                this.notify.error("Name Already exists")
            }
            this.connectWithList.push("."+this.groupName);
            console.log("*posh*")
            console.log(this.connectWithList);
            this.groupName="";
            this.isMandatory=false;
            console.log(this.optOfferedCourseList);
        }

        private search():void{
            Utils.expandRightDiv();
            let YS=this.yearSemName.split("-");
            let year = +YS[0];
            let sem= +YS[1];
            this.optCourseOfferService.getOptCourses(this.selectedSemesterId,this.programId,year,sem).then((data)=>{
                this.optCourseList=[];
                this.tempList=[];
                this.optCourseList=data;
                this.tempList=this.optCourseList;
                console.log("**Courses**");
                console.log(this.optCourseList);
                this.draggables=this.optCourseList;
            });
        }


        private getSemesters():void{
            this.semesterService.fetchSemesters(11,5).then((semesters:Array<Semester>)=>{
                this.semesters=semesters;
                for(var i=0;i<semesters.length;i++){
                    if(semesters[i].status==1){
                        this.semester = semesters[i];
                        this.activeSemesterId=semesters[i].id;
                        break;
                    }
                }
                this.selectedSemesterId=semesters[i].id;
            }).then((data)=>{

            });
        }
        private semesterChanged(val:any){
            this.selectedSemesterId=val.id;
        }
        private changeYearSem(value:any){
            this.yearSemName=value.name;
            console.log("Y-S: "+this.yearSemName);
        }
        private changeProgram(data:any):void{
            this.programId=data.id;
            console.log("Program Id: "+this.programId);
        }
    }
    UMS.controller("OptCourseOffer",OptCourseOffer);
}