module ums{
    interface IConstants{
        id:any;
        name:string;
    }
    class OptCourseOffer {
        public departmentId: string;
        public programId: number;
        public programs: Array<Program>;
        public program: Program;
        public semesters: Array<Semester>;
        public semester: Semester;
        public selectedSemesterId: number;
        public activeSemesterId: number;
        yearSemList: Array<IConstants>;
        yearSem: IConstants;
        yearSemName: string;
        public optCourseList: Array<IOptCourseList>;
        public tempList: Array<IOptCourseList>;
        public optOfferedCourseList: Array<any>;
        public isSubGroupAvailable: boolean;
        public isMandatory: boolean;
        public groupName: string;
        public subGroupName: string;
        public parentGrpName: string;
        public indexValue: number;
        draggables: Array<any>;
        selectedComponents: any;
        selectedComponents1: any;
        sortingLog: any;
        draggableOptions: any;
        sortableOptions: any;
        sortableOptions1: any;
        connectWithList: any;
        pushDataIntoList: boolean;
        destinationDiv: string;
        courseIdMap: any;
        pairCourseIdMapWithCourseId: any;
        public static $inject = ['appConstants', 'HttpClient', '$q', 'notify', '$sce', '$window', 'semesterService', 'facultyService',
            'programService', 'commonService', 'optCourseOfferService'];

        constructor(private appConstants: any,
                    private httpClient: HttpClient,
                    private $q: ng.IQService,
                    private notify: Notify,
                    private $sce: ng.ISCEService,
                    private $window: ng.IWindowService,
                    private semesterService: SemesterService,
                    private facultyService: FacultyService,
                    private programService: ProgramService,
                    private commonService: CommonService,
                    private optCourseOfferService: OptCourseOfferService) {
            this.isMandatory = false;
            this.groupName = "";
            this.subGroupName = "";
            this.parentGrpName = "";
            this.optOfferedCourseList = [];
            this.connectWithList = [];
            this.commonService.fetchCurrentUser().then((departmentJson: any) => {
                this.departmentId = departmentJson.id;
                this.isSubGroupAvailable = this.departmentId == "05" ? true : false;
                console.log("Dept Id: ");
                console.log(this.departmentId);
                this.getSemesters();
            }).then((data) => {
                this.programService.fetchProgram(11).then((data) => {
                    var app: Array<Program> = [];
                    app = data;
                    this.programs = app;
                    this.programs = this.programs.filter(f => f.departmentId == this.departmentId);
                    this.program = this.programs[0];
                    this.programId = this.program.id;
                }).then((data) => {
                    this.yearSemList = [];
                    this.yearSemList = this.appConstants.opYearSemester;
                    console.log(this.yearSemList);
                    if (this.departmentId == "04" || this.departmentId == "03") {
                        this.yearSemList = this.yearSemList.filter(f => f.id == "42");
                    }
                    this.yearSem = this.yearSemList[0];
                    this.yearSemName = this.yearSem.name;
                    console.log("Y-S: " + this.yearSemName);
                });
            });
            this.pushDataIntoList = false;
            this.selectedComponents1 = [];
            // this.initialization();
            this.drag();
            this.sortingLog = [];
            this.sortableOptions = {};
            this.sortableOptions1 = {};
        }


        public drag() {
            console.log(this.connectWithList);
            let tmpThis = this;
            let gpName = this;
            let checkSource = "draggable-element-container";
            let changeColorToGreen = 1;
            this.draggableOptions = {
                connectWith: this.connectWithList,
                stop: function (e, ui) {
                    // if the element is removed from the first container
                    if (ui.item.sortable.source.hasClass('draggable-element-container') &&
                        ui.item.sortable.droptarget &&
                        ui.item.sortable.droptarget != ui.item.sortable.source) {
                        // restore the removed item
                        //draggable-element-container
                        gpName.destinationDiv = ui.item.sortable.droptarget[0].classList[0].toString();
                        if (checkSource != gpName.destinationDiv) {
                            ui.item.sortable.sourceModel.push(ui.item.sortable.model);
                            tmpThis.insert(ui.item.sortable.model);
                            tmpThis.changeColor(ui.item.sortable.model.id, changeColorToGreen);
                        } else {
                            console.log("Same Source");
                        }
                    }
                }
            };
        }

        public changeColor(courseId: any, setColor: number) {
            console.log(courseId + " :" + setColor);
            var parentIndex;
            for (let i = 0; i < this.draggables.length; i++) {
                for (let j = 0; j < this.draggables[i].courses.length; j++) {
                    if (this.draggables[i].courses[j].id == courseId) {
                        this.draggables[i].courses[j].statusId = setColor;
                        parentIndex = i;
                        break;
                    }
                }
            }
            this.draggables[parentIndex].courses.sort(function (a, b) {
                if (a.id > b.id) {
                    return 1;
                }
                if (a.id < b.id) {
                    return -1;
                }
                return 0;
            });
        }

        public insert(data: any) {
            var counter = 0;
            if (this.departmentId != "05") {
                console.log("NOT EEE");
                for (let i = 0; i < this.optOfferedCourseList.length; i++) {
                    for (let j = 0; j < this.optOfferedCourseList[i].courses.length; j++) {
                        if (this.optOfferedCourseList[i].courses[j].id == data.id) {
                            counter = 1;
                            break;
                        }
                    }
                }
            } else {
                console.log("EEE");
                console.log(this.optOfferedCourseList);
                for (let i = 0; i < this.optOfferedCourseList.length; i++) {
                    for (let j = 0; j < this.optOfferedCourseList[i].subGrpCourses.length; j++) {
                        for (let k = 0; k < this.optOfferedCourseList[i].subGrpCourses[j].courses.length; k++) {
                            if (this.optOfferedCourseList[i].subGrpCourses[j].courses[k].id == data.id) {
                                counter = 1;
                                break;
                            }
                        }
                    }
                }
            }
            console.log("Counter: "+counter);
            if (counter != 1) {
            var items = {
                id: data.id,
                no: data.no,
                title: data.title,
                crHr: data.crHr,
                courseType: data.courseType,
                year: data.year,
                semester: data.semester,
                pairCourseId: data.pairCourseId,
                statusId: data.statusId
            };
            var index = 0, parentIndex = 0, childIndex = 0;
            if (this.departmentId == "05") {
                for (let i = 0; i < this.optOfferedCourseList.length; i++) {
                    for (let j = 0; j < this.optOfferedCourseList[i].subGrpCourses.length; j++) {
                        if (this.optOfferedCourseList[i].subGrpCourses[j].groupName == this.destinationDiv) {
                            parentIndex = i;
                            childIndex = j;
                            break;
                        }
                    }
                }
                this.optOfferedCourseList[parentIndex].subGrpCourses[childIndex].courses.push(items);
                if (items.pairCourseId != null) {
                    this.insertPairCourse(items.pairCourseId, parentIndex, childIndex);
                }
            } else {
                index = this.optOfferedCourseList.map(e => e.groupName).indexOf(this.destinationDiv);
                this.optOfferedCourseList[index].courses.push(items);
                if (items.pairCourseId != null) {
                    this.insertPairCourse(items.pairCourseId, index);
                }
            }
            console.log("***insert***");
            console.log(this.optOfferedCourseList);
        } else {
         this.notify.error("This Course is Already Assigned");
    }
}
        public insertPairCourse(courseId:string,parentIndex:number,childIndex?:number):void{
            console.log("Inside Pair Course");
            var data=this.courseIdMap[courseId];
            var items={
                id: data.id,
                no: data.no,
                title: data.title,
                crHr:data.crHr,
                courseType:data.courseType,
                year: data.year,
                semester: data.semester,
                pairCourseId:data.pairCourseId,
                statusId:data.statusId
            }
            console.log("pair CourseDetails: "+parentIndex);
            console.log(items);
            if(this.departmentId !="05"){
                this.optOfferedCourseList[parentIndex].courses.push(items);
            }else{
                this.optOfferedCourseList[parentIndex].subGrpCourses[childIndex].courses.push(items);
            }
            this.changeColor(courseId,1);
        }
        public deleteItems(groupName:string,index:number,courseId:string):void{
            console.log("courseId: "+courseId);
            var changeColor=0,isCourseFound=0,pairCourseId;
           var parentIndex = this.optOfferedCourseList.map(e => e.groupName).indexOf(groupName);
            this.optOfferedCourseList[parentIndex].courses.splice(index,1);
            this.changeColor(courseId,changeColor);

          pairCourseId=this.pairCourseIdMapWithCourseId[courseId];
          console.log("pair course: "+pairCourseId);
          if(pairCourseId !=null){
              this.deletePairCourses(pairCourseId,parentIndex);
          }

        }
        public deletePairCourses(pairCourseId:string,parentIndex:number,childIndex?:number){
            console.log("delete pair course")
            var isCourseFound=0,index=0,changeColor=0;
            if(this.departmentId !="05"){
                for(let i=0;i<this.optOfferedCourseList[parentIndex].courses.length;i++){
                    if( this.optOfferedCourseList[parentIndex].courses[i].id==pairCourseId){
                        index=i;
                        break;
                    }
                }
                this.optOfferedCourseList[parentIndex].courses.splice(index,1);
            }else {
                for(let k=0;k<this.optOfferedCourseList[parentIndex].subGrpCourses[childIndex].courses.length;k++){
                    if( this.optOfferedCourseList[parentIndex].subGrpCourses[childIndex].courses[k].id==pairCourseId){
                        index=k;
                        break;
                    }
                }
                this.optOfferedCourseList[parentIndex].subGrpCourses[childIndex].courses.splice(index,1);
            }
            this.changeColor(pairCourseId,changeColor);

        }
        public deleteSubItems(groupName:string,subGroupName:string,index:number,courseId:string){
            var changeColor=0,isCourseFound=0,pairCourseId;
            var parentIndex = this.optOfferedCourseList.map(e => e.groupName).indexOf(groupName);
            var childIndex=this.optOfferedCourseList[parentIndex].subGrpCourses.map(e=>e.groupName).indexOf(subGroupName);
            this.optOfferedCourseList[parentIndex].subGrpCourses[childIndex].courses.splice(index,1);
                this.changeColor(courseId,changeColor);
            pairCourseId=this.pairCourseIdMapWithCourseId[courseId];
            console.log("pair course: "+pairCourseId);
            if(pairCourseId !=null){
                this.deletePairCourses(pairCourseId,parentIndex,childIndex);
            }

        }
        private subGroup():void{
            if(this.subGroupName.length>0){
                var name=this.subGroupName.replace(/[^\w]/gi,'');
                console.log(name);
                var isNameExists=0;
                for(let i=0;i<this.optOfferedCourseList.length;i++){
                    isNameExists= this.optOfferedCourseList[i].subGrpCourses.filter(f=>f.groupName==name).length;
                    if(isNameExists==1){
                        break;
                    }
                }
                if(isNameExists==0){
                    this.optOfferedCourseList[this.indexValue].subGrpCourses.push({
                        groupId:null,
                        groupName:name,
                        totalApplied:0,
                        totalSeats:0,
                        courses:[]
                    });
                    if(this.departmentId=="05"){
                        this.connectWithList.push("."+name);
                    }
                }else {
                    this.notify.error("Name Already Exists");
                }
            }else{
                this.notify.warn("Name can not be Empty");
            }
          this.subGroupName="";
            console.log(this.connectWithList);
        }
       private getParentGrpId(data:any,index:number,course:any):void{
            this.parentGrpName=data;
            this.indexValue=this.optOfferedCourseList.indexOf(course);
       }
        private addOfferedCourse():void{
            var items,isNameExists=0;
            if(this.groupName.length>0){
                var name=this.groupName.replace(/[^\w]/gi,'');
                if(this.optOfferedCourseList.length>0){
                    isNameExists=this.optOfferedCourseList.filter(f=>f.groupName==name).length;
                }
                if(isNameExists==0){
                    items=
                        {   groupId:null,
                            groupName:name==null ? null:name,
                            isMandatory:this.isMandatory,
                            totalApplied:0,
                             totalSeats:0,
                            courses:[],
                            subGrpCourses:[]
                        };
                    this.optOfferedCourseList.push(items);
                    if(this.departmentId!="05"){
                        this.connectWithList.push("."+name);
                    }
                }else {
                    this.notify.error("Name Already Exists")
                }
            }else{
                this.notify.warn("Name can be Empty");
            }
            this.groupName="";
            this.isMandatory=false;
        }
        public removeMainGroup(groupName:string){
            var value = confirm("Are you Confirm?");
            if( value == true ){
                var parentIndex = this.optOfferedCourseList.map(e => e.groupName).indexOf(groupName);
                console.log(parentIndex);
                if(this.departmentId!="05"){
                    for(let j=0;j<this.optOfferedCourseList[parentIndex].courses.length;j++){
                        this.changeColor(this.optOfferedCourseList[parentIndex].courses[j].id,0);
                    }
                }else {
                    console.log("EEE");
                    for(let j=0;j<this.optOfferedCourseList[parentIndex].subGrpCourses.length;j++){
                        for(let k=0;k<this.optOfferedCourseList[parentIndex].subGrpCourses[j].courses.length;k++){
                            console.log("course id :"+this.optOfferedCourseList[parentIndex].subGrpCourses[j].courses[k].id);
                            this.changeColor(this.optOfferedCourseList[parentIndex].subGrpCourses[j].courses[k].id,0);
                        }
                    }
                }
                this.optOfferedCourseList.splice(parentIndex,1);
            }
        }
        public removeSubGroup(groupName:string,subGroupName:string){
            var value = confirm("Are you Confirm?");
            if( value == true ) {
                var parentIndex = this.optOfferedCourseList.map(e => e.groupName).indexOf(groupName);
                var childIndex = this.optOfferedCourseList[parentIndex].subGrpCourses.map(e => e.groupName).indexOf(subGroupName);
                for (let k = 0; k < this.optOfferedCourseList[parentIndex].subGrpCourses[childIndex].courses.length; k++) {
                    this.changeColor(this.optOfferedCourseList[parentIndex].subGrpCourses[childIndex].courses[k].id, 0);
                }
                this.optOfferedCourseList[parentIndex].subGrpCourses.splice(childIndex, 1);
            }
        }

        private search():void{
            Utils.expandRightDiv();
            let YS=this.yearSemName.split("-");
            let year = +YS[0];
            let sem= +YS[1];
            this.optCourseOfferService.getOptCourses(this.selectedSemesterId,this.programId,year,sem).then((data)=>{
                this.optCourseList=[];
                this.tempList=[];
                this.draggables=[];
                this.optCourseList=data;
                this.tempList=this.optCourseList;
                console.log("**Courses**");
                console.log(this.optCourseList);
                this.draggables=this.optCourseList;
                this.createMap(this.draggables);
            }).then((a)=>{
                console.log("******************");
                this.getOfferedCoursesList();
            });

        }
        public getOfferedCoursesList():void{
            let YS=this.yearSemName.split("-");
            let year = +YS[0];
            let sem= +YS[1];
            this.optCourseOfferService.getOfferedCourses(this.selectedSemesterId,this.programId,year,sem).then((data)=>{
                console.log("**data**");
                this.optOfferedCourseList=[];
                this.optOfferedCourseList=data;
                console.log(this.optOfferedCourseList);
            }).then((data)=>{
                if(this.departmentId !="05"){
                    for(let i=0;i<this.optOfferedCourseList.length;i++){
                        this.connectWithList.push("."+this.optOfferedCourseList[i].groupName);
                    }
                    for(let i=0;i<this.optOfferedCourseList.length;i++){
                        for(let j=0;j<this.optOfferedCourseList[i].courses.length;j++){
                            this.changeColor(this.optOfferedCourseList[i].courses[j].id,1);
                            console.log("Course Id: "+this.optOfferedCourseList[i].courses[j].id+"");
                        }
                    }
                }else {
                    for(let i=0;i<this.optOfferedCourseList.length;i++){
                        for(let j=0;j<this.optOfferedCourseList[i].subGrpCourses.length;j++){
                            this.connectWithList.push("."+this.optOfferedCourseList[i].subGrpCourses[j].groupName);
                        }
                    }
                    for(let i=0;i<this.optOfferedCourseList.length;i++){
                        for(let j=0;j<this.optOfferedCourseList[i].subGrpCourses.length;j++){
                            for(let k=0;k<this.optOfferedCourseList[i].subGrpCourses[j].courses.length;k++){
                                this.changeColor(this.optOfferedCourseList[i].subGrpCourses[j].courses[k].id,1);
                            }
                        }
                    }
                }
            });
        }
        public createMap(data:any){
            var map: {[key:string]: object[]} = {};
            var pairCourseIdMap: {[key:string]: string} = {};
            for(let i=0;i<data.length;i++){
                for(let j=0;j<data[i].courses.length;j++){
                    map[data[i].courses[j].id] = data[i].courses[j];
                    pairCourseIdMap[data[i].courses[j].id]=data[i].courses[j].pairCourseId;
                }
            }
            this.courseIdMap=map;
            console.log("**courseIdMap**");
            console.log(this.courseIdMap);
            this.pairCourseIdMapWithCourseId=pairCourseIdMap;
            console.log("**pairCourseIdMapWithCourseId**");
            console.log(this.pairCourseIdMapWithCourseId);
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
        private save():void{
            console.log("**Is Eligible for Insertation**");
            if(this.optOfferedCourseList.length>0){
                var courseCounter=true;
                if(this.departmentId!="05"){
                    for(let i=0;i<this.optOfferedCourseList.length;i++){
                        console.log("length: "+this.optOfferedCourseList[i].courses.length);
                        if(this.optOfferedCourseList[i].courses.length==0){
                            this.notify.error("No Courses Assigned In Group "+this.optOfferedCourseList[i].groupName);
                            courseCounter=false;
                            break;
                        }
                    }

                }else{
                    for(let i=0;i<this.optOfferedCourseList.length;i++) {
                        if (this.optOfferedCourseList[i].subGrpCourses.length == 0) {
                            this.notify.error("No SubGroups Created In Group " + this.optOfferedCourseList[i].groupName);
                            courseCounter = false;
                            break;
                        }
                    }
                    if(courseCounter==true){
                        for(let i=0;i<this.optOfferedCourseList.length;i++) {
                            for(let j=0;j<this.optOfferedCourseList[i].subGrpCourses.length;j++){
                                if(this.optOfferedCourseList[i].subGrpCourses[j].courses.length==0){
                                    this.notify.error("No Courses Assigned In Group: " + this.optOfferedCourseList[i].groupName+" Subgroup: "+this.optOfferedCourseList[i].subGrpCourses[j].groupName);
                                    courseCounter = false;
                                    break;
                                }
                            }
                        }
                    }
                }
                if(courseCounter==true){
                    let YS=this.yearSemName.split("-");
                    let year = +YS[0];
                    let sem= +YS[1];
                    var json:any=this.optOfferedCourseList;
                    this.optCourseOfferService.addInfo(this.programId,year,sem,json).then((data)=>{
                        console.log(data);
                    });
                    console.log(json);
                }

            }else {
                this.notify.error("No Records Found");
            }

        }
    }
    UMS.controller("OptCourseOffer",OptCourseOffer);
}