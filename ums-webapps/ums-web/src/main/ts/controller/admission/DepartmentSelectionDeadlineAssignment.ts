module ums{
  interface IDepartmentSelectionDeadlineAssignment extends ng.IScope{
    semesters:Array<Semester>;
    semester:Semester;
    programTypes:Array<IProgramType>;
    programType:IProgramType;
    quotaTypes:Array<IQuotaType>;
    quotaType:IQuotaType;
    faculty:Faculty;
    faculties:Array<Faculty>;
    departmentSelectionDeadline: any;
    meritSerialNumberFrom:number;
    meritSerialNumberTo:number;
    date:string;
    departmentSelectionDeadlines : Array<DepartmentSelectionDeadline>;
    meritTypes:Array<IMeritListType>;
    meritType:IMeritListType;


    showLoader:boolean;
    showAddSection:boolean;

    getDeadlines:Function;
    add:Function;
    edit:Function;
    delete:Function;
    save:Function;
    showHide:Function;
  }

  interface  IProgramType{
    id:string;
    name:string;
  }

  interface  IQuotaType{
    id:string;
    name:string;
  }


  interface IMeritListType{
    id:string;
    name:string;
  }

  class DepartmentSelectionDeadlineAssignment{
    public static $inject = ['appConstants','HttpClient','$scope','$q','notify','$sce','$window','semesterService','facultyService','departmentSelectionDeadlineService'];
    constructor(private appConstants: any,
                private httpClient: HttpClient,
                private $scope: IDepartmentSelectionDeadlineAssignment,
                private $q:ng.IQService,
                private notify: Notify,
                private $sce:ng.ISCEService,
                private $window:ng.IWindowService,
                private semesterService: SemesterService,
                private facultyService: FacultyService,
                private departmentSelectionDeadlineService: DepartmentSelectionDeadlineService
      ) {

      $scope.showAddSection=false;
      $scope.programTypes=appConstants.programType;
      $scope.programType = $scope.programTypes[0];
      $scope.quotaTypes = appConstants.quotaTypes;
      $scope.quotaType = $scope.quotaTypes[0];
      $scope.meritSerialNumberFrom=0;
      $scope.meritSerialNumberTo=0;
      $scope.departmentSelectionDeadline={};
      $scope.showLoader = false;


      $scope.getDeadlines = this.getDeadlines.bind(this);
      $scope.add = this.add.bind(this);
      $scope.edit = this.edit.bind(this);
      $scope.delete = this.delete.bind(this);
      $scope.save = this.save.bind(this);
      $scope.showHide = this.showHide.bind(this);

      this.getFaculties();
      this.getSemesters();
      this.getMeritListTypes();

    }

    private showHide(){
      this.$scope.showAddSection=!this.$scope.showAddSection;
    }

    private getFaculties(){
      this.facultyService.getAllFaculties().then((faculties:Array<Faculty>)=>{
        this.$scope.faculties=[];
        for(var i=0;i<faculties.length;i++){
          this.$scope.faculties.push(faculties[i]);
        }
        this.$scope.faculty=faculties[0];
      });
    }

    private getMeritListTypes():void{
      this.$scope.meritTypes = [];
      this.$scope.meritTypes = this.appConstants.meritListTypes;
      this.$scope.meritType = this.$scope.meritTypes[0];
    }


    private getSemesters():void{
      this.semesterService.fetchSemesters(+this.$scope.programType.id,5, Utils.SEMESTER_FETCH_WITH_NEWLY_CREATED).then((semesters:Array<Semester>)=>{
        this.$scope.semesters=semesters;
        for(var i=0;i<semesters.length;i++){
          if(semesters[i].status==2){
            this.$scope.semester = semesters[i];
            break;
          }
        }
      });
    }

    private getDeadlines(){
      Utils.expandRightDiv();
      this.$scope.showLoader = true;
      this.$scope.departmentSelectionDeadlines=[];
      this.departmentSelectionDeadlineService.getDeadlines(this.$scope.semester.id, this.$scope.meritType.name, this.$scope.faculty.shortName).then((deadlines)=>{
        console.log("Fetched deadlines-");
        console.log(deadlines);
        for(var i=0;i<deadlines.length;i++){
          deadlines[i].disable=true;
          this.$scope.departmentSelectionDeadlines.push(deadlines[i]);
        }
        this.$scope.showLoader = false;
      });
    }


    private add(){

      this.$scope.departmentSelectionDeadline={};
      this.$scope.departmentSelectionDeadline.deadline = angular.copy(this.$scope.date);
      this.$scope.departmentSelectionDeadline.meritSerialNumberFrom = angular.copy(+this.$scope.meritSerialNumberFrom);
      this.$scope.departmentSelectionDeadline.meritSerialNumberTo = angular.copy(+this.$scope.meritSerialNumberTo);
      this.$scope.departmentSelectionDeadline.disable=true;
      this.$scope.departmentSelectionDeadlines.push(this.$scope.departmentSelectionDeadline);
      this.$scope.meritSerialNumberFrom=0;
      this.$scope.meritSerialNumberTo=0;
      this.$scope.date = "";

    }


    private edit(departmentSelectionDeadline: DepartmentSelectionDeadline){
      departmentSelectionDeadline.disable=false;
    }

    private delete(departmentSelectionDeadline: DepartmentSelectionDeadline){
      for(var i=0;i<this.$scope.departmentSelectionDeadlines.length;i++){
        if(this.$scope.departmentSelectionDeadlines[i] == departmentSelectionDeadline){
          this.$scope.departmentSelectionDeadlines.splice(i,1);
          break;
        }
      }

      if(departmentSelectionDeadline.id==null){
        this.notify.success("Sucessfully Deleted");
      }else{
        this.departmentSelectionDeadlineService.delete(departmentSelectionDeadline.id);
      }
    }

    private save(){
      this.convertToJson().then((json:any)=>{
        this.departmentSelectionDeadlineService.saveOrUpdateDeadline(json).then((status:string)=>{
          this.getDeadlines();
        });
      });
    }
    private convertToJson():ng.IPromise<any>{
    
      var defer = this.$q.defer();
      var completeJson={};
      var jsonObject = [];
       for(var i=0;i<this.$scope.departmentSelectionDeadlines.length;i++){
         var item:any={};
         item['id']=this.$scope.departmentSelectionDeadlines[i].id;
         item['semesterId'] = this.$scope.semester.id;
         item['unit']= this.$scope.faculty.shortName;
         item['quota'] = this.$scope.meritType.name;
         item['fromMeritSerialNumber'] = this.$scope.departmentSelectionDeadlines[i].meritSerialNumberFrom;
         item['toMeritSerialNumber'] = this.$scope.departmentSelectionDeadlines[i].meritSerialNumberTo;
         item['deadline'] = this.$scope.departmentSelectionDeadlines[i].deadline;
         jsonObject.push(item);
       }
       completeJson["entries"] = jsonObject;
      defer.resolve(completeJson);
      return defer.promise;
    }
  }



  UMS.controller("DepartmentSelectionDeadlineAssignment", DepartmentSelectionDeadlineAssignment);
}