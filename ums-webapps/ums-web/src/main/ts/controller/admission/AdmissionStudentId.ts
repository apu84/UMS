module ums{
  interface IAdmissionStudentId extends ng.IScope{
    semesters:Array<Semester>;
    semester:Semester;
    receiptId: string;
    programTypes:Array<IProgramType>;
    programType:IProgramType;
    admissionStudent: AdmissionStudent;

    showStudentSection: boolean;

    getSemesters:Function;
    showMainPanel:Function;
    searchByReceiptId: Function;

  }

  interface  IProgramType{
    id:string;
    name:string;
  }

  class AdmissionStudentId{
    public static $inject = ['appConstants','HttpClient','$scope','$q','notify','$sce','$window','semesterService','facultyService', 'admissionStudentService'];
    constructor(private appConstants: any,
                private httpClient: HttpClient,
                private $scope: IAdmissionStudentId,
                private $q:ng.IQService,
                private notify: Notify,
                private $sce:ng.ISCEService,
                private $window:ng.IWindowService,
                private semesterService: SemesterService,
                private facultyService: FacultyService,
                private admissionStudentService:AdmissionStudentService) {

      $scope.programTypes = appConstants.programType;
      $scope.programType = $scope.programTypes[0];
      $scope.showStudentSection=false;

      $scope.showMainPanel = this.showManePanel.bind(this);
      $scope.searchByReceiptId  = this.searchByReceiptId.bind(this);

      this.getSemesters();
      Utils.setValidationOptions("form-horizontal");

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

    private searchByReceiptId(receiptId: string) {
      this.$scope.showStudentSection=false;
      this.admissionStudentService.fetchAdmissionStudentByReceiptId(this.$scope.semester.id, +this.$scope.programType.id, receiptId).then((data) => {
        this.$scope.admissionStudent = <AdmissionStudent>{};
        this.$scope.admissionStudent = data;

        if(this.$scope.admissionStudent.studentId!=null){
          this.$scope.showStudentSection=true;
        }else{
          this.notify.error("The student is not yet admitted");
        }

      });

    }

    private showManePanel(){
      Utils.expandRightDiv();
    }
  }

  UMS.controller("AdmissionStudentId", AdmissionStudentId);
}