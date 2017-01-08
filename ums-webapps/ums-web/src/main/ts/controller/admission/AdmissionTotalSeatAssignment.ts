/**
 * Created by My Pc on 04-Jan-17.
 */

module ums{
  interface IAdmissionTotalSeatAssignment extends ng.IScope{
    semesters:Array<Semester>;
    semester:Semester;
    programTypes:Array<IProgramType>;
    programType:IProgramType;
    admissionTotalSeats:Array<AdmissionTotalSeat>;
    quotaTypes:Array<IQuotaType>;
    quotaType:IQuotaType;

    searchSpinner:boolean;
    updatble:boolean;
    edit:boolean;
    showContent:boolean;

    save:Function;
    editData:Function;
    getSemesters:Function;
    getAdmissionTotalSeat:Function;
  }

  interface  IProgramType{
    id:string;
    name:string;
  }

  interface  IQuotaType{
    id:string;
    name:string;
  }

  class AdmissionTotalSeatAssignment{
    public static $inject = ['appConstants','HttpClient','$scope','$q','notify','$sce','$window','semesterService','admissionTotalSeatService','programService'];
    constructor(private appConstants: any,
                private httpClient: HttpClient,
                private $scope: IAdmissionTotalSeatAssignment,
                private $q:ng.IQService,
                private notify: Notify,
                private $sce:ng.ISCEService,
                private $window:ng.IWindowService,
                private semesterService: SemesterService,
                private admissionTotalSeatService: AdmissionTotalSeatService,
                private programService: ProgramService) {

      $scope.programTypes=appConstants.programType;
      $scope.programType = $scope.programTypes[0];
      $scope.quotaTypes = appConstants.quotaTypes;
      $scope.quotaType = $scope.quotaTypes[0];
      $scope.searchSpinner = false;
      $scope.showContent=false;
      $scope.edit=false;


      $scope.editData=this.edit.bind(this);
      $scope.save = this.save.bind(this);
      $scope.getSemesters= this.getSemesters.bind(this);
      $scope.getAdmissionTotalSeat = this.getAdmissionTotalSeat.bind(this);


      this.getSemesters();
      Utils.setValidationOptions("form-horizontal");


    }




    private getSemesters():void{
      this.semesterService.fetchSemesters(+this.$scope.programType.id,5).then((semesters:Array<Semester>)=>{
        this.$scope.semesters=semesters;
        for(var i=0;i<semesters.length;i++){
          if(semesters[i].status==2){
            this.$scope.semester = semesters[i];
            break;
          }
        }
      });
    }

    private getAdmissionTotalSeat():void{

      Utils.expandRightDiv();

      this.$scope.searchSpinner=true;

      this.admissionTotalSeatService
          .fetchAdmissionTotalSeat(this.$scope.semester.id, +this.$scope.programType.id, +this.$scope.quotaType.id)
          .then((seats:Array<AdmissionTotalSeat>)=>{
        this.$scope.showContent = true;
        this.$scope.searchSpinner=false;
        console.log("seats");
        console.log(seats);
        if(seats.length==0){
          this.$scope.edit=true;
          this.$scope.updatble=false;
          this.createEmptyAdmissionTotalDate();

        }else{
          this.$scope.edit=false;
          this.$scope.updatble=true;
          this.$scope.admissionTotalSeats = seats;
        }
      });
    }

    private createEmptyAdmissionTotalDate() {
      this.programService.fetchProgram(+this.$scope.programType.id).then((programs: Array<Program>) => {
        this.$scope.admissionTotalSeats = [];
        this.addPrograms(programs);

      });
    }

    private addPrograms(programs: Array<ums.Program>) {
      for (var i = 0; i < programs.length; i++) {
        var admissionTotalSeat: AdmissionTotalSeat = <AdmissionTotalSeat>{};
        admissionTotalSeat.programId = programs[i].id;
        admissionTotalSeat.programShortName = programs[i].shortName;
        admissionTotalSeat.programLongName = programs[i].longName;
        admissionTotalSeat.semesterId = +this.$scope.semester.id;
        admissionTotalSeat.totalSeat="";
        // admissionTotalSeat.totalSeat=0;
        this.$scope.admissionTotalSeats.push(admissionTotalSeat);
      }
    }

    private edit(){
      this.$scope.edit=true;
    }

    private save(){
      this.$scope.searchSpinner
      this.convertToJson().then((json:any)=>{
        if(this.$scope.updatble==false){
          this.admissionTotalSeatService.saveAdmissionTotalSeatInfo(json).then((message:any)=>{
            this.getAdmissionTotalSeat();
          });
        }
        else{
          this.admissionTotalSeatService.updateAdmissionTotalSeat(json).then((message:any)=>{
            this.getAdmissionTotalSeat();
          });
        }
      });
    }

    private convertToJson():ng.IPromise<any>{
      var defer = this.$q.defer();
      var completeJson = {};
      var jsonObject=[];
      var seats:Array<AdmissionTotalSeat> = this.$scope.admissionTotalSeats;

      for(var i=0;i<seats.length;i++){
        var item:any={};
        item['id'] = seats[i].id;
        item['semesterId'] = this.$scope.semester.id;
        item['programId'] = seats[i].programId;
        item['programType'] = +this.$scope.programType.id;
        item['quota'] = +this.$scope.quotaType.id;
        if(seats[i].totalSeat!=""){
          item['totalSeat'] = +seats[i].totalSeat;
        }else{
          item['totalSeat'] = 0;
        }
        jsonObject.push(item);
      }
      completeJson["entries"] = jsonObject;
      defer.resolve(completeJson);
      return defer.promise;
    }

  }

  UMS.controller("AdmissionTotalSeatAssignment", AdmissionTotalSeatAssignment)
}