module ums{

  interface IAdmissionCertificateVerification extends ng.IScope{

    programType:IProgramType;
    semester:Semester;
    admissionStudent: AdmissionStudent;

    admissionStudents: Array<AdmissionStudent>;
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
    undertake:boolean;
    studentQuota: string;
    receiptId:string;
    thisComment: string;
    verify:boolean;
    reject:boolean;
    deadLine: string;
    selected: any;
    receiptIdMap: any;
    lengthOfSavedCertificates : number;
    lengthOfTotalCertificates: number;
    countGL: number;
    countGCE:number;
    countFF: number;
    countRA: number;
    countAll:number;

    showRightDiv: Function;
    search: Function;
    getSemesters:Function;
    getAllCertificates: Function;
    toggleSelection : Function;
    rejected: Function;
    underTaken: Function;
    verified : Function;
    getPreviousComments : Function;
    getAllCandidates: Function;
    getCurrentComment: Function;
  }

  interface IProgramType{
    id:string;
    name:string;
  }

  interface IPreviousCertificates{
    id : number;
  }

  interface IPreviousComment{
    comment: string;
    commentedOn:string;
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
      $scope.thisComment = "";
      $scope.deadLine = "";
      $scope.countGL = 0;
      $scope.countGCE = 0;
      $scope.countFF = 0;
      $scope.countRA = 0;
      $scope.countAll = 0;
      $scope.lengthOfSavedCertificates = 0;
      $scope.lengthOfTotalCertificates = 0;

      $scope.programTypes = appConstants.programType;
      $scope.programType = $scope.programTypes[0];

      $scope.rightDiv = false;
      $scope.mainDiv = false;
      $scope.forEngineering = false;
      $scope.GLShow = false;
      $scope.GCEShow = false;
      $scope.quotaShow = false;
      $scope.verify = true;
      $scope.reject = true;
      $scope.undertake = true;
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
      $scope.getAllCandidates = this.getAllCandidates.bind(this);
      $scope.getCurrentComment = this.getCurrentComment.bind(this);

      this.getSemesters();
      Utils.setValidationOptions("form-horizontal");
    }

    private getSemesters(): void {
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

    private getAllCandidates() {
      this.$scope.receiptIdMap = {};
      this.admissionCertificateVerificationService.getCandidateList(this.$scope.programType.id, this.$scope.semester.id)
          .then((students: Array<AdmissionStudent>) => {

            this.$scope.admissionStudents = [];
            for (var i = 0; i < students.length; i++) {
              this.$scope.admissionStudents.push(students[i]);
              this.$scope.receiptIdMap[students[i].receiptId] = students[i];
            }
            this.initializeSelect2("searchByReceiptId", this.$scope.admissionStudents, "Enter Receipt ID");
          });
    }

    private initializeSelect2(selectBoxId, studentIds, placeHolderText) {
      var data = studentIds;
      $("#" + selectBoxId).select2({
        minimumInputLength: 0,
        query: function (options) {
          var pageSize = 100;
          var startIndex = (options.page - 1) * pageSize;
          var filteredData = data;
          if (options.term && options.term.length > 0) {
            if (!options.context) {
              var term = options.term.toLowerCase();
              options.context = data.filter(function (metric: any) {
                return ( metric.id.indexOf(term) !== -1 );
              });
            }
            filteredData = options.context;
          }
          options.callback({
            context: filteredData,
            results: filteredData.slice(startIndex, startIndex + pageSize),
            more: (startIndex + pageSize) < filteredData.length
          });
        },
        placeholder: placeHolderText
      });
    }

    private search(receiptId: string): void {

      this.$scope.countGL = 0;
      this.$scope.countGCE = 0;
      this.$scope.countFF = 0;
      this.$scope.countRA = 0;
      this.$scope.countAll = 0;

      this.$scope.verify = true;
      this.$scope.reject = true;
      this.$scope.undertake = false;


      if (receiptId == null || receiptId == "") {
      }
      else {
        Utils.expandRightDiv();

        this.$scope.thisComment = "";
        this.$scope.selected = [];
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
                this.addDate();

              }
            });
      }
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

      if (this.$scope.studentQuota == "GCE") {

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
      else {
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
          else if (this.$scope.admissionStudent.quota == "FF" && this.$scope.allTypesOfCertificates[i].type == "FF") {
            this.$scope.quotaShow = true;
            this.$scope.quotaCertificates.push(this.$scope.allTypesOfCertificates[i]);
          }
          else if (this.$scope.admissionStudent.quota == "RA" && this.$scope.allTypesOfCertificates[i].type == "RA") {
            this.$scope.quotaShow = true;
            this.$scope.quotaCertificates.push(this.$scope.allTypesOfCertificates[i]);
          }
        }
      }
    }

    private getPreviousComments(): void {
      var s = "";
      this.admissionCertificateVerificationService.getAllPreviousComments(this.$scope.semester.id, this.$scope.receiptId)
          .then((previousComments: Array<IPreviousComment>) => {
            this.$scope.previousComments = previousComments;
          });
    }

    private getCurrentComment(comments: string): void {

      this.$scope.thisComment = comments;
    }

    private verified(): void {

      if (this.$scope.thisComment == null || this.$scope.thisComment == "") {
        this.$scope.thisComment = "";
      }

      this.convertToJson(1, this.$scope.thisComment).then((json: any) => {
        this.admissionCertificateVerificationService.saveAll(json)
            .then((message: any) => {
              this.notify.success(message);
              this.search(this.$scope.receiptId);
            });
      });
    }

    private underTaken(): void {
      if (this.$scope.thisComment == null || this.$scope.thisComment == "") {
        this.$scope.thisComment = "";
      }

      this.convertToJson(2, this.$scope.thisComment).then((json: any) => {
        this.admissionCertificateVerificationService.saveAll(json)
            .then((message: any) => {
              this.notify.success(message);
              this.search(this.$scope.receiptId);
            });
      });
    }

    private rejected(): void {

      if (this.$scope.thisComment == null || this.$scope.thisComment == "") {
        this.$scope.thisComment = "";
      }

      this.convertToJson(3, this.$scope.thisComment).then((json: any) => {
        this.admissionCertificateVerificationService.saveAll(json)
            .then((message: any) => {
              this.notify.success(message);
              this.search(this.$scope.receiptId);
            });
      });
    }


    private convertToJson(status: number, comments: string): ng.IPromise<any> {
      var defer = this.$q.defer();
      var completeJson = {};
      var jsonObject = [];

      if (this.$scope.selected.length > 0) {

        for (var i = 0; i < this.$scope.selected.length; i++) {
          var item: any = {};
          item['programType'] = +this.$scope.programType.id;
          item['semesterId'] = this.$scope.semester.id;
          item['receiptId'] = this.$scope.receiptId;
          item['certificateId'] = this.$scope.selected[i];
          item['status'] = status;
          item['comment'] = comments;
          jsonObject.push(item);
        }
      }
      else {
        var item: any = {};
        item['programType'] = +this.$scope.programType.id;
        item['semesterId'] = this.$scope.semester.id;
        item['receiptId'] = this.$scope.receiptId;
        item['certificateId'] = 0;
        item['status'] = status;
        item['comment'] = comments;
        jsonObject.push(item);

      }

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


        this.$scope.countGL = 0;
        this.$scope.countGCE = 0;
        this.$scope.countAll = 0;
        this.$scope.countFF = 0;
        this.$scope.countRA = 0;
        this.$scope.verify = true;
        this.$scope.reject = true;
        this.$scope.undertake = false;
      }
      else {
        this.$scope.selected.push(allCertificates.id);

        this.trackAndIncreaseCountForPreviousCertificates();
        this.increaseCountForCurrentlySelectedCertificates();
        this.enableOrDisableButtons();
      }
    }

    private trackAndIncreaseCountForPreviousCertificates() {
      for (var i = 0; i < this.$scope.previousCertificates.length; i++) {
        if (this.$scope.previousCertificates[i].id >= 1 && this.$scope.previousCertificates[i].id <= 4) {
          this.$scope.countGL++;
        }
        else if (this.$scope.previousCertificates[i].id >= 5 && this.$scope.previousCertificates[i].id <= 8) {
          this.$scope.countGCE++;
        }
        else if (this.$scope.previousCertificates[i].id >= 9 && this.$scope.previousCertificates[i].id <= 11) {
          this.$scope.countAll++;
        }
        else if (this.$scope.previousCertificates[i].id >= 12 && this.$scope.previousCertificates[i].id <= 14) {
          this.$scope.countFF++;
        }
        else if (this.$scope.previousCertificates[i].id >= 15 && this.$scope.previousCertificates[i].id <= 16) {
          this.$scope.countRA++;
        }
      }
    }

    private increaseCountForCurrentlySelectedCertificates() {
      for (var i = 0; i < this.$scope.selected.length; i++) {
        if (this.$scope.selected[i] >= 1 && this.$scope.selected[i] <= 4) {
          this.$scope.countGL++;
        }
        else if (this.$scope.selected[i] >= 5 && this.$scope.selected[i] <= 8) {
          this.$scope.countGCE++;
        }
        else if (this.$scope.selected[i] >= 9 && this.$scope.selected[i] <= 11) {
          this.$scope.countAll++;
        }
        else if (this.$scope.selected[i] >= 12 && this.$scope.selected[i] <= 14) {
          this.$scope.countFF++;
        }
        else if (this.$scope.selected[i] >= 15 && this.$scope.selected[i] <= 16) {
          this.$scope.countRA++;
        }
      }
    }

    private enableOrDisableButtons() {
      if (this.$scope.studentQuota == "GCE") {
        if (this.$scope.countGCE == 4 && this.$scope.countAll >= 1) {
          this.$scope.verify = false;
          this.$scope.reject = false;
          this.$scope.undertake = true;
        }
      }
      if (this.$scope.studentQuota == "GL") {
        if (this.$scope.countGL == 4 && this.$scope.countAll >= 1) {
          this.$scope.verify = false;
          this.$scope.reject = false;
          this.$scope.undertake = true;
        }
      }
      if (this.$scope.studentQuota == "FF") {
        if (this.$scope.countGL == 4 && this.$scope.countAll >= 1 && this.$scope.countFF == 3) {
          this.$scope.verify = false;
          this.$scope.reject = false;
          this.$scope.undertake = true;
        }
      }
      if (this.$scope.studentQuota == "RA") {
        if (this.$scope.countGL == 4 && this.$scope.countAll >= 1 && this.$scope.countRA == 2) {
          this.$scope.verify = false;
          this.$scope.reject = false;
          this.$scope.undertake = true;
        }
      }

      this.$scope.countGL = 0;
      this.$scope.countGCE = 0;
      this.$scope.countAll = 0;
      this.$scope.countFF = 0;
      this.$scope.countRA = 0;
    }

    private UnderTakenDeadline(deadLine : string){
      this.$scope.deadLine = deadLine;
      console.log("Deadline");
      console.log(this.$scope.deadLine);
    }

    private addDate(): void{
      setTimeout(function () {
        $('.datepicker-default').datepicker();
        $('.datepicker-default').on('change', function () {
          $('.datepicker').hide();
        });
      }, 100);

    }

  }

  UMS.controller("AdmissionCertificateVerification",AdmissionCertificateVerification);
}
