/**
 * Created by Monjur-E-Morshed on 18-Jan-17.
 */

module ums{
 interface IAdmissionFee extends ng.IScope{
   semesters:Array<Semester>;
   semester:Semester;
   programTypes:Array<IProgramType>;
   programType:IProgramType;
 }


  interface  IProgramType{
    id:string;
    name:string;
  }


  class AdmissionFee{
   public static $inject = ['appConstants','HttpClient','$scope','$q','notify','$sce','$window','semesterService', 'admissionStudentService'];
   constructor(private appConstants: any,
               private httpClient: HttpClient,
               private $scope: IAdmissionFee,
               private $q:ng.IQService,
               private notify: Notify,
               private $sce:ng.ISCEService,
               private $window:ng.IWindowService,
               private semesterService: SemesterService,
               private admissionStudentService:AdmissionStudentService) {


     $scope.programTypes = appConstants.programType;
     $scope.programType = $scope.programTypes[0];

     this.getSemesters();

   }


    private getSemesters(){
      this.semesterService.fetchSemesters(+this.$scope.programType.id).then((semesters:Array<Semester>)=>{
        this.$scope.semesters=[];
        for(var i=0;i<semesters.length;i++){
          if(semesters[i].status==2){
            this.$scope.semester = semesters[i];
          }
          this.$scope.semesters.push(semesters[i]);
        }
      });
    }
 }

 UMS.controller('AdmissionFee', AdmissionFee);

}