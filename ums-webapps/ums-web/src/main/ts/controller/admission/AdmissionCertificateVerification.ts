module ums{

  interface IAdmissionCertificateVerification extends ng.IScope{

    admissionStudent: AdmissionStudent;
    semester:Semester;
    programType:IProgramType;

    semesters:Array<Semester>;
    programTypes:Array<IProgramType>;
    certificates: Array<ICertificate>;
    academicCertificates: Array<ICertificate>;
    personalCertificates: Array<ICertificate>;

    receiptId:string;
    mainDiv: boolean;
    rightDiv: boolean;
    forEngineering:boolean;
    selected: any;

    showRightDiv: Function;
    searchByReceiptId: Function;
    getSemesters:Function;
    getCertificates: Function;
    toggleSelection : Function;
    rejected: Function;
    underTaken: Function;
    verified : Function;
  }

  interface ICertificate{
    certificateId:number;
    certificateTitle: string;
    certificateType: string;
  }

  interface  IProgramType{
    id:string;
    name:string;
  }


  class AdmissionCertificateVerification {
    public static $inject = ['appConstants', '$scope', 'notify', '$window',
      'semesterService', 'facultyService', 'admissionCertificateVerificationService'];

    constructor(private appConstants: any,
                private $scope: IAdmissionCertificateVerification,
                private notify: Notify,
                private $window: ng.IWindowService,
                private semesterService: SemesterService,
                private facultyService: FacultyService,
                private admissionCertificateVerificationService: AdmissionCertificateVerificationService) {

      $scope.receiptId="";
      $scope.programTypes = appConstants.programType;
      $scope.programType = $scope.programTypes[0];

      this.getSemesters();

      $scope.rightDiv = false;
      $scope.mainDiv = false;
      $scope.forEngineering= false;
      $scope.selected = [];

      $scope.getSemesters = this.getSemesters.bind(this);
      $scope.showRightDiv = this.showRightDiv.bind(this);
      $scope.searchByReceiptId = this.searchByReceiptId.bind(this);
      $scope.getCertificates = this.getCertificates.bind(this);
      $scope.rejected = this.rejected.bind(this);
      $scope.underTaken = this.underTaken.bind(this);
      $scope.verified = this.verified.bind(this);
      $scope.toggleSelection =this.toggleSelection.bind(this);

      Utils.setValidationOptions("form-horizontal");
    }

    private getCertificates(): void{
      this.admissionCertificateVerificationService.getCertificateLists()
          .then((certificates: Array<ICertificate>) =>{
            this.$scope.certificates = certificates;

            this.$scope.academicCertificates=[];
            this.$scope.personalCertificates=[];
            for(var i = 0; i < certificates.length; i++){
              if(certificates[i].certificateType == "ACADEMIC") {
                this.$scope.academicCertificates.push(certificates[i]);
              }
              else {
                this.$scope.personalCertificates.push(certificates[i]);
              }
            }
          });
    }

    private getSemesters(): void {
      this.semesterService.fetchSemesters(+this.$scope.programType.id, 5).then((semesters: Array<Semester>)=> {
        this.$scope.semesters = semesters;
        for (var i = 0; i < semesters.length; i++) {
          if (semesters[i].status == 2) {
            this.$scope.semester = semesters[i];
            break;
          }
        }
      });
    }

    private showRightDiv(){
      this.$scope.rightDiv = true;
      this.$scope.mainDiv = false;
      Utils.expandRightDiv();

    }

    private searchByReceiptId(semesterId:number ,receiptId:string): void {

      Utils.expandRightDiv();

      this.admissionCertificateVerificationService.getNewStudentInfo(semesterId, receiptId)
          .then((admissionStudentsInfo : Array<AdmissionStudent>)=> {

            if (admissionStudentsInfo == null) {
              this.notify.error("No Data Found");
            }
            else {
              this.$scope.getCertificates();
              this.$scope.mainDiv=true;
              this.$scope.admissionStudent = <AdmissionStudent>{};
              this.$scope.admissionStudent = admissionStudentsInfo[0];
            }
          });
    }

    private toggleSelection(studentAcadmicCertificate: any, list : any):  void{

      var idx = this.$scope.selected.indexOf(studentAcadmicCertificate.certificateId);

      if(idx > -1){
        this.$scope.selected.splice(idx, 1);
      }
      else{
        this.$scope.selected.push(studentAcadmicCertificate.certificateId);
      }
    }

    private rejected(): void{

  }

  private underTaken(): void {

  }

  private verified(): void{

  }

  }

  UMS.controller("AdmissionCertificateVerification",AdmissionCertificateVerification);
}
