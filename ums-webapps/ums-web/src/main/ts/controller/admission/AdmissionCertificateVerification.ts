module ums{

  interface IAdmissionCertificateVerification extends ng.IScope{

    admissionStudent: AdmissionStudent;
    semester:Semester;
    programType:IProgramType;

    semesters:Array<Semester>;
    programTypes:Array<IProgramType>;
    certificates: Array<ICertificate>;
    savedCertificates: Array<ISavedCertificates>
    banglaMediumAcademicCertificates: Array<ICertificate>;
    englishMediumAcademicCertificates: Array<ICertificate>;
    personalCertificates: Array<ICertificate>;
    quotaCertificates: Array<ICertificate>;
    savedComments : Array<IComment>;

    receiptId:string;
    mainDiv: boolean;
    rightDiv: boolean;
    forEngineering:boolean;
    selected: any;
    lengthOfSavedCertificates : number;
    comment: string;

    showRightDiv: Function;
    searchByReceiptId: Function;
    getSemesters:Function;
    getCertificates: Function;
    toggleSelection : Function;
    rejected: Function;
    underTaken: Function;
    verified : Function;
    getComments : Function;
  }

  interface ICertificate{
    certificateId:number;
    certificateTitle: string;
    certificateType: string;
    certificateCategory: string;
    disableChecked?:string;
  }

  interface IComment{
    semesterId: number;
    receiptId: string;
    comment: string;
  }

  interface ISavedCertificates {
    semesterId: number;
    receiptId: string;
    certificateId: number;
  }

  interface IProgramType{
    id:string;
    name:string;
  }

  class AdmissionCertificateVerification {
    public static $inject = ['appConstants', '$scope', '$q', 'notify', '$window', '$sce',
      'semesterService', 'facultyService', 'admissionCertificateVerificationService'];

    constructor(private appConstants: any,
                private $scope: IAdmissionCertificateVerification,
                private $q: ng.IQService,
                private notify: Notify,
                private $window: ng.IWindowService,
                private $sce: ng.ISCEService,
                private semesterService: SemesterService,
                private facultyService: FacultyService,
                private admissionCertificateVerificationService: AdmissionCertificateVerificationService) {


      $scope.receiptId = "";
      $scope.comment = "";
      $scope.lengthOfSavedCertificates = 0;

      $scope.programTypes=appConstants.programType;
      $scope.programType = $scope.programTypes[0];

      $scope.rightDiv = false;
      $scope.mainDiv = false;
      $scope.forEngineering = false;
      $scope.selected = [];

      $scope.getSemesters = this.getSemesters.bind(this);
      $scope.showRightDiv = this.showRightDiv.bind(this);
      $scope.searchByReceiptId = this.searchByReceiptId.bind(this);
      $scope.getCertificates = this.getCertificates.bind(this);
      $scope.rejected = this.rejected.bind(this);
      $scope.underTaken = this.underTaken.bind(this);
      $scope.verified = this.verified.bind(this);
      $scope.toggleSelection = this.toggleSelection.bind(this);
      $scope.getComments = this.getComments.bind(this);

      this.getSemesters();

      Utils.setValidationOptions("form-horizontal");
    }
    private getSemesters():void {
      this.semesterService.fetchSemesters(Number(this.$scope.programType.id), 5).then((semesters: any) => {
        this.$scope.semesters = semesters;
        for (var i = 0; i < semesters.length; i++) {
          if (semesters[i].status == 2) {
            this.$scope.semester = semesters[i];
            break;
          }
        }
      });
    }

    private searchByReceiptId(receiptId: string): void {

      Utils.expandRightDiv();
      this.$scope.receiptId = receiptId;
      this.admissionCertificateVerificationService.getNewStudentInfo(this.$scope.programType.id, this.$scope.semester.id, receiptId)
          .then((admissionStudentsInfo: Array<AdmissionStudent>) => {

            if (admissionStudentsInfo == null) {
              this.notify.error("No Data Found");
            }
            else {
              this.$scope.getCertificates();
              this.$scope.getComments();
              this.$scope.mainDiv = true;
              this.$scope.admissionStudent = <AdmissionStudent>{};
              this.$scope.admissionStudent = admissionStudentsInfo[0];
            }
          });
    }

    private getCertificates(): void {
      this.getSavedCertificatesOfStudent();
      this.getAllTypesOfCertificates();
    }

    private getComments() : void{
      var s = "";
      this.admissionCertificateVerificationService.getSavedComments(+this.$scope.semester.id, this.$scope.receiptId)
          .then((savedComments : Array<IComment>) => {
            this.$scope.savedComments = savedComments;
            for(var i=0; i<savedComments.length; i++){
              s += "Comment " + (i+1) + ": " + savedComments[i].comment + "\n";
            }
            this.$scope.comment = s;
          });
    }

    private getSavedCertificatesOfStudent() {
      this.admissionCertificateVerificationService.getSavedCertificateLists(+this.$scope.semester.id, this.$scope.receiptId)
          .then((savedCertificates: Array<ISavedCertificates>) => {
            this.$scope.savedCertificates = savedCertificates;
            this.$scope.lengthOfSavedCertificates = savedCertificates.length;
          });
    }

    private getAllTypesOfCertificates() {
      this.admissionCertificateVerificationService.getCertificateLists()
          .then((certificates: Array<ICertificate>) => {
            this.$scope.certificates = certificates;

            this.$scope.banglaMediumAcademicCertificates = [];
            this.$scope.englishMediumAcademicCertificates = [];
            this.$scope.personalCertificates = [];
            this.$scope.quotaCertificates = [];
            this.categorizeCertificates(certificates);
          });
    }

    private categorizeCertificates(certificates: Array<ICertificate>) {

      for (var i = 0; i < certificates.length; i++) {
        if (certificates[i].certificateCategory == "ACADEMIC" && certificates[i].certificateType == "Bengali") {
          this.$scope.banglaMediumAcademicCertificates.push(certificates[i]);
        }
        else if (certificates[i].certificateCategory == "ACADEMIC" && certificates[i].certificateType == "English") {
          this.$scope.englishMediumAcademicCertificates.push(certificates[i]);
        }
        else if (certificates[i].certificateCategory == "PERSONAL" && certificates[i].certificateType == "All") {
          this.$scope.personalCertificates.push(certificates[i]);
        }
        else if (certificates[i].certificateCategory == "PERSONAL" && certificates[i].certificateType == "Quota") {
          this.$scope.quotaCertificates.push(certificates[i]);
        }
      }
      //disableSavedCertifiates()
      for (var i = 0; i < +this.$scope.lengthOfSavedCertificates; i++) {
        certificates[(this.$scope.savedCertificates[i].certificateId) - 1].disableChecked = "true";
      }
    }

    private rejected(comments: string): void {
      this.$scope.comment = comments;
      this.convertStatusToJson(3).then((json:any)=> {
        this.admissionCertificateVerificationService.setVerificationStatus(json)
            .then((message:any)=>{
              this.notify.success(message);
            });
      });

      this.convertCommentsToJson(this.$scope.comment).then((json:any)=> {
        this.admissionCertificateVerificationService.saveComments(json)
            .then((message:any)=>{
              this.notify.success(message);
            });
      });
    }

    private underTaken(comments:string): void {
      this.$scope.comment = comments;
      this.convertStatusToJson(2).then((json:any)=> {
        this.admissionCertificateVerificationService.setVerificationStatus(json)
            .then((message:any)=>{
              this.notify.success(message);
            });
      });

      this.convertCommentsToJson(comments).then((json:any)=> {
        this.admissionCertificateVerificationService.saveComments(json)
            .then((message:any)=>{
              this.notify.success(message);
            });
      });

      if(this.$scope.selected.length > 0) {
        this.convertSelectedCertificatesToJson().then((json: any) => {
          this.admissionCertificateVerificationService.saveCertificates(json)
              .then((message: any) => {
                this.notify.success(message);
              });
        });
      }
      this.$scope.selected = [];
    }

    private verified(comments:string): void {
      this.$scope.comment = comments;
      this.convertStatusToJson(1).then((json:any)=> {
        this.admissionCertificateVerificationService.setVerificationStatus(json)
            .then((message:any)=>{
          this.notify.success(message);
        });
      });

      this.convertCommentsToJson(comments).then((json:any)=> {
        this.admissionCertificateVerificationService.saveComments(json)
            .then((message:any)=>{
              this.notify.success(message);
            });
      });

      if(this.$scope.selected.length > 0) {
        this.convertSelectedCertificatesToJson().then((json: any) => {
          this.admissionCertificateVerificationService.saveCertificates(json)
              .then((message: any) => {
                this.notify.success(message);
              });
        });
      }
      this.$scope.selected = [];

    }

    private convertStatusToJson(status: number): ng.IPromise<any> {
      var defer = this.$q.defer();
      var completeJson = {};
      var jsonObject = [];

      var item: any = {};
      item['programType'] = +this.$scope.programType.id;
      item['semesterId'] = this.$scope.semester.id;
      item['receiptId'] = this.$scope.receiptId;
      item['status'] = status;
      jsonObject.push(item);

      completeJson["entries"] = jsonObject;
      defer.resolve(completeJson);
      return defer.promise;
    }

    private convertSelectedCertificatesToJson(): ng.IPromise<any> {
      var defer = this.$q.defer();
      var completeJson = {};
      var jsonObject = [];

      for(var i=0; i < this.$scope.selected.length; i++) {
          var item: any = {};
          item['semesterId'] = this.$scope.semester.id;
          item['receiptId'] = this.$scope.receiptId;
          item['certificateId'] = this.$scope.selected[i];
          jsonObject.push(item);
        }

      completeJson["entries"] = jsonObject;
      defer.resolve(completeJson);
      return defer.promise;
    }

    private convertCommentsToJson(comments:string): ng.IPromise<any> {
      var defer = this.$q.defer();
      var completeJson = {};
      var jsonObject = [];

      var item: any = {};
      item['semesterId'] = this.$scope.semester.id;
      item['receiptId'] = this.$scope.receiptId;
      item['comment'] = comments;
      jsonObject.push(item);

      completeJson["entries"] = jsonObject;
      defer.resolve(completeJson);
      return defer.promise;
    }

    private showRightDiv() {
      this.$scope.rightDiv = true;
      this.$scope.mainDiv = false;
      Utils.expandRightDiv();

    }

    private toggleSelection(studentAcademicCertificate: any, list: any): void {

      var idx = this.$scope.selected.indexOf(studentAcademicCertificate.certificateId);

      if (idx > -1) {
        this.$scope.selected.splice(idx, 1);
      }
      else {
        this.$scope.selected.push(studentAcademicCertificate.certificateId);
      }
    }

  }

  UMS.controller("AdmissionCertificateVerification",AdmissionCertificateVerification);
}
