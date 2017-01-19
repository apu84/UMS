module ums{

  interface IAdmissionCertificateVerification extends ng.IScope{

    programType:IProgramType;
    semester:Semester;
    admissionStudent: AdmissionStudent;

    programTypes:Array<IProgramType>;
    semesters:Array<Semester>;
    allTypesOfCertificates: Array<ITypesOfCertificate>;
    gLCertificates: Array<ITypesOfCertificate>;
    gCECertificates: Array<ITypesOfCertificate>;
    nationalCertificates: Array<ITypesOfCertificate>;
    quotaCertificates: Array<ITypesOfCertificate>;
    previousCertificates: Array<IPreviousCertificates>;
    previousComments : Array<IPreviousComment>;

    mainDiv: boolean;
    rightDiv: boolean;
    forEngineering:boolean;
    GLShow:boolean;
    GCEShow: boolean;
    quotaShow: boolean;
    studentQuota: string;
    receiptId:string;
    selected: any;
    lengthOfSavedCertificates : number;
    lengthOfTotalCertificates: number;

    showRightDiv: Function;
    search: Function;
    getSemesters:Function;
    getAllCertificates: Function;
    toggleSelection : Function;
    rejected: Function;
    underTaken: Function;
    verified : Function;
    getPreviousComments : Function;
  }

  interface IPreviousComment{
    comment: string;
    commentedOn:string;
  }

  interface IProgramType{
    id:string;
    name:string;
  }

  interface IPreviousCertificates{
    id : number;
  }

  interface ITypesOfCertificate{
    id: number;
    name: string;
    type: string;
    disableChecked: string;
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
      $scope.studentQuota = "";
      $scope.lengthOfSavedCertificates = 0;
      $scope.lengthOfTotalCertificates = 0;

      $scope.programTypes=appConstants.programType;
      $scope.programType = $scope.programTypes[0];

      $scope.rightDiv = false;
      $scope.mainDiv = false;
      $scope.forEngineering = false;
      $scope.GLShow = false;
      $scope.GCEShow = false;
      $scope.quotaShow = false;
      $scope.selected = [];

      $scope.getSemesters = this.getSemesters.bind(this);
      $scope.showRightDiv = this.showRightDiv.bind(this);
      $scope.search = this.search.bind(this);
      $scope.getAllCertificates = this.getAllCertificates.bind(this);
      $scope.rejected = this.rejected.bind(this);
      $scope.underTaken = this.underTaken.bind(this);
      $scope.verified = this.verified.bind(this);
      $scope.toggleSelection = this.toggleSelection.bind(this);
      $scope.getPreviousComments = this.getPreviousComments.bind(this);

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

    private search(receiptId: string): void {

      Utils.expandRightDiv();

      this.$scope.receiptId = receiptId;

      this.admissionCertificateVerificationService.getCandidateInformation(this.$scope.programType.id, this.$scope.semester.id, this.$scope.receiptId)
          .then((admissionStudentsInformation: Array<AdmissionStudent>) => {

            if (admissionStudentsInformation == null) {
              this.notify.error("No Data Found");
            }
            else {
              this.$scope.mainDiv = true;
              this.$scope.admissionStudent = admissionStudentsInformation[0];
              this.$scope.studentQuota = this.$scope.admissionStudent.quota;
              this.$scope.getAllCertificates();
              this.$scope.getPreviousComments();
            }
          });
    }

    private getAllCertificates(): void {
      this.getPreviousCertificates();
      this.getAllTypesOfCertificates();
    }

    private getPreviousCertificates() {

      this.admissionCertificateVerificationService.getSavedCertificates(+this.$scope.semester.id, this.$scope.receiptId)
          .then((previousCertificates: Array<IPreviousCertificates>) => {
            this.$scope.previousCertificates = previousCertificates;
            this.$scope.lengthOfSavedCertificates = previousCertificates.length;
          });
    }

    private getAllTypesOfCertificates() {
      this.admissionCertificateVerificationService.getAllTypesOfCertificates()
          .then((allTypesOfCertificates: Array<ITypesOfCertificate>) => {
            this.$scope.allTypesOfCertificates = allTypesOfCertificates;
            this.$scope.lengthOfTotalCertificates = allTypesOfCertificates.length;

            this.disablePreviouslySelectedCertificates();
            this.categorizeAllTypesOfCertificates();
          });
    }

    private disablePreviouslySelectedCertificates() {

      if (this.$scope.lengthOfSavedCertificates > 0) {

        for (var p = 0; p < this.$scope.lengthOfSavedCertificates; p++) {
          for (var q = 0; q < this.$scope.lengthOfTotalCertificates; q++) {
            if (this.$scope.previousCertificates[p].id == this.$scope.allTypesOfCertificates[q].id) {
              this.$scope.allTypesOfCertificates[q].disableChecked = "true";
              break;
            }
          }
        }
      }
    }

    private categorizeAllTypesOfCertificates() {

      this.$scope.gLCertificates = [];
      this.$scope.gCECertificates = [];
      this.$scope.nationalCertificates = [];
      this.$scope.quotaCertificates = [];

      if(this.$scope.studentQuota == "GCE"){

        this.$scope.GLShow = false;
        this.$scope.quotaShow = false;
        this.$scope.GCEShow = true;

        for (var i = 0; i < this.$scope.lengthOfTotalCertificates; i++) {
          if (this.$scope.allTypesOfCertificates[i].type == "GCE") {
            this.$scope.gCECertificates.push(this.$scope.allTypesOfCertificates[i]);
          }
          else if (this.$scope.allTypesOfCertificates[i].type == "ALL") {
            this.$scope.nationalCertificates.push(this.$scope.allTypesOfCertificates[i]);
          }
        }
      }
      else{
        this.$scope.GCEShow = false;
        this.$scope.quotaShow = false;
        this.$scope.GLShow = true;
        for (var i = 0; i < this.$scope.lengthOfTotalCertificates; i++) {
          if (this.$scope.allTypesOfCertificates[i].type == "GL") {
            this.$scope.gLCertificates.push(this.$scope.allTypesOfCertificates[i]);
          }
          else if (this.$scope.allTypesOfCertificates[i].type == "ALL") {
            this.$scope.nationalCertificates.push(this.$scope.allTypesOfCertificates[i]);
          }
          else if(this.$scope.admissionStudent.quota == "FF" && this.$scope.allTypesOfCertificates[i].type == "FF"){
            this.$scope.quotaShow = true;
            this.$scope.quotaCertificates.push(this.$scope.allTypesOfCertificates[i]);
          }
          else if(this.$scope.admissionStudent.quota == "RA" && this.$scope.allTypesOfCertificates[i].type == "RA"){
            this.$scope.quotaShow = true;
            this.$scope.quotaCertificates.push(this.$scope.allTypesOfCertificates[i]);
          }
        }
      }
    }

    private getPreviousComments() : void {
      var s = "";
      this.admissionCertificateVerificationService.getAllPreviousComments(this.$scope.semester.id, this.$scope.receiptId)
          .then((previousComments: Array<IPreviousComment>) => {
            this.$scope.previousComments = previousComments;
          });
    }


    private rejected(comments: string): void {
      this.convertStatusToJson(0).then((json:any)=> {
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

    private underTaken(comments:string): void {


    }

    private verified(comments:string): void {
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

      console.log("selected Certificates ");
      console.log(this.$scope.selected);

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

    private toggleSelection(allCertificates: ITypesOfCertificate): void {

      var idx = this.$scope.selected.indexOf(allCertificates.id);

      if (idx > -1) {
        this.$scope.selected.splice(idx, 1);
      }
      else {
        this.$scope.selected.push(allCertificates.id);
      }
    }

  }

  UMS.controller("AdmissionCertificateVerification",AdmissionCertificateVerification);
}
