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
    meritSerialNumberFrom:string;
    meritSerialNumberTo:string;
    date:string;
    departmentSelectionDeadlines : Array<DepartmentSelectionDeadline>;
    meritTypes:Array<IMeritListType>;
    meritType:IMeritListType;


    showLoader:boolean;

    getDeadlines:Function;
    add:Function;
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

      $scope.programTypes=appConstants.programType;
      $scope.programType = $scope.programTypes[0];
      $scope.quotaTypes = appConstants.quotaTypes;
      $scope.quotaType = $scope.quotaTypes[0];
      $scope.date="";
      $scope.departmentSelectionDeadline={};
      $scope.showLoader = false;


      $scope.getDeadlines = this.getDeadlines.bind(this);
      $scope.add = this.add.bind(this);
      this.getFaculties();
      this.getSemesters();
      this.getMeritListTypes();

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
        for(var i=0;i<deadlines.length;i++){
          deadlines[i].editable=false;
          this.$scope.departmentSelectionDeadlines.push(deadlines[i]);
        }
        this.$scope.showLoader = false;
      });
    }


    private add(){

      this.$scope.departmentSelectionDeadline={};
      this.$scope.departmentSelectionDeadline.deadline = this.$scope.date;
      this.$scope.departmentSelectionDeadline.meritSerialNumberFrom = +this.$scope.meritSerialNumberFrom;
      this.$scope.departmentSelectionDeadline.meritSerialNumberTo = +this.$scope.meritSerialNumberTo;
      this.$scope.departmentSelectionDeadline.disable=true;
      this.$scope.departmentSelectionDeadlines.push(this.$scope.departmentSelectionDeadline);
      console.log(this.$scope.date);
      console.log(this.$scope.departmentSelectionDeadlines);
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
         item['deadline'] = this.$scope.departmentSelectionDeadline[i].deadline;
         jsonObject.push(item);
       }
       completeJson["entries"] = jsonObject;
      defer.resolve(completeJson);
      return defer.promise;
    }
  }



  UMS.controller("DepartmentSelectionDeadlineAssignment", DepartmentSelectionDeadlineAssignment);
}