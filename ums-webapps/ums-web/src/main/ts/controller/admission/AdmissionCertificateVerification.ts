module ums{

  interface IAdmissionCertificateVerification extends ng.IScope{

    programType:IProgramType;
    semester:Semester;
    admissionStudent: AdmissionStudent;

    programTypes:Array<IProgramType>;
    semesters:Array<Semester>;
    admissionStudents: Array<AdmissionStudent>;
    allTypesOfCertificates: Array<ITypesOfCertificate>;
    gLCertificates: Array<ITypesOfCertificate>;
    gCECertificates: Array<ITypesOfCertificate>;
    nationalCertificates: Array<ITypesOfCertificate>;
    quotaCertificates: Array<ITypesOfCertificate>;
    previousCertificates: Array<IPreviousCertificate>;
    temp: Array<ITypesOfCertificate>;
    previousComments: Array<IPreviousComment>;

    disableCheckAllCheckbox: boolean;
    mainDiv: boolean;
    rightDiv: boolean;
    GLShow:boolean;
    GCEShow: boolean;
    quotaShow: boolean;
    disableCommentBox: boolean;
    disableApprovedButton:boolean;
    disableRejectedButton:boolean;
    disableUndertakenButton:boolean;
    disableDateField: boolean;
    lengthOfTotalCandidate: number;
    lengthOfPreviousCertificates : number;
    lengthOfTotalCertificates: number;
    countGL: number;
    countGCE:number;
    countFF: number;
    countRA: number;
    countAll:number;
    data:any;
    receiptIdMap: any;
    glSelected: any;
    gceSelected: any;
    nSelected: any;
    ffSelected: any;
    raSelected: any;
    totalSelected: any;
    studentQuota: string;
    studentStatus: string;
    userComment: string;
    receiptId:string;
    deadLine: string;

    getSemesters:Function;
    getAllCandidates: Function;
    showRightDiv: Function;
    search: Function;
    isChecked: Function;
    toggleAll: Function;
    exists: Function;
    toggleSelection : Function;
    UnderTakenDeadline: Function;
    underTaken: Function;
    rejected: Function;
    Approved : Function;
  }

  interface IProgramType{
    id:string;
    name:string;
  }

  interface IPreviousCertificate{
    id : number;
    name: string;
    type: string;
  }

  interface IPreviousComment{
    comment: string;
    commentedOn:string;
  }

  interface ITypesOfCertificate{
    id: number;
    name: string;
    type: string;
    checked: string;
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


      $scope.programTypes = appConstants.programType;
      $scope.programType = $scope.programTypes[0];
      $scope.data = {
        userComment: "",
        deadLine: ""
      };

      $scope.getSemesters = this.getSemesters.bind(this);
      $scope.getAllCandidates = this.getAllCandidates.bind(this);
      $scope.showRightDiv = this.showRightDiv.bind(this);
      $scope.search = this.search.bind(this);
      $scope.isChecked = this.isChecked.bind(this);
      $scope.toggleAll = this.toggleAll.bind(this);
      $scope.exists = this.exists.bind(this);
      $scope.toggleSelection = this.toggleSelection.bind(this);
      $scope.UnderTakenDeadline = this.UnderTakenDeadline.bind(this);
      $scope.underTaken = this.underTaken.bind(this);
      $scope.rejected = this.rejected.bind(this);
      $scope.Approved = this.Approved.bind(this);

      this.getSemesters();
      this.getAllTypesOfCertificates();
      this.categorizeAllTypesOfCertificates();
      Utils.setValidationOptions("form-horizontal");
    }

    private getSemesters(): void {
      this.semesterService.fetchSemesters(Number(this.$scope.programType.id), 5, Utils.SEMESTER_FETCH_WITH_NEWLY_CREATED).then((semesters: any) => {
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
      this.admissionCertificateVerificationService.getCandidatesList(+this.$scope.programType.id, this.$scope.semester.id)
          .then((students: Array<AdmissionStudent>) => {

            this.$scope.admissionStudents = [];
            for (var i = 0; i < students.length; i++) {
              if (students[i].meritSlNo != 0) {
                this.$scope.admissionStudents.push(students[i]);
                this.$scope.receiptIdMap[students[i].receiptId] = students[i];
              }
            }
            this.$scope.lengthOfTotalCandidate = this.$scope.admissionStudents.length;
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
      if (receiptId == null || receiptId == "") {
        this.notify.error("Please Enter a Receipt Id");
      }
      else {
        this.$scope.receiptId = receiptId;
        this.$scope.admissionStudent = null;

        this.admissionCertificateVerificationService.getCandidate(+this.$scope.programType.id, this.$scope.semester.id, this.$scope.receiptId)
            .then((admissionStudentsInfo: Array<AdmissionStudent>) => {

              if (admissionStudentsInfo == null) {
                this.notify.error("No Data Found");
              }
              else {
                this.$scope.admissionStudent = admissionStudentsInfo[0];
                this.$scope.studentQuota = this.$scope.admissionStudent.quota;
                this.$scope.studentStatus = this.$scope.admissionStudent.verificationStatus;
                
                this.initializeVariables();
                this.resetCertificatesProperties();
                this.getPreviousCertificates();
                this.getPreviousComments();
                this.addDate();
                this.$scope.mainDiv = true;
              }
            });
      }
    }


    private initializeVariables() {
      if (this.$scope.studentStatus == "Verified" || this.$scope.studentStatus == "Rejected") {
        this.$scope.disableApprovedButton = true;
        this.$scope.disableRejectedButton = true;
        this.$scope.disableUndertakenButton = true;
        this.$scope.disableCommentBox = true;
        this.$scope.disableDateField = true;
        this.$scope.disableCheckAllCheckbox = false;
      }
      else {
        this.$scope.disableApprovedButton = true;
        this.$scope.disableRejectedButton = false;
        this.$scope.disableUndertakenButton = true;
        this.$scope.disableCommentBox = false;
        this.$scope.disableDateField = false;
      }

      this.$scope.gLCertificates = [];
      this.$scope.gCECertificates = [];
      this.$scope.nationalCertificates = [];
      this.$scope.quotaCertificates = [];
      this.$scope.glSelected = [];
      this.$scope.gceSelected = [];
      this.$scope.nSelected = [];
      this.$scope.ffSelected = [];
      this.$scope.raSelected = [];
      this.$scope.totalSelected = [];
      this.$scope.lengthOfTotalCandidate = 0;
      this.$scope.lengthOfPreviousCertificates = 0;
      // this.$scope.lengthOfTotalCertificates = 0;
      this.$scope.countGL = 0;
      this.$scope.countGCE = 0;
      this.$scope.countFF = 0;
      this.$scope.countRA = 0;
      this.$scope.countAll = 0;
      this.$scope.data.userComment = "";
      this.$scope.data.deadLine = "";
      this.$scope.disableCheckAllCheckbox = true;
    }


    private getAllTypesOfCertificates() {
      this.admissionCertificateVerificationService.getAllTypesOfCertificates()
          .then((allTypesOfCertificates: Array<ITypesOfCertificate>) => {
            this.$scope.allTypesOfCertificates = allTypesOfCertificates;
            this.$scope.lengthOfTotalCertificates = allTypesOfCertificates.length;
          });
    }


    private resetCertificatesProperties(){
      for(var i = 0; i < this.$scope.lengthOfTotalCertificates; i++){
        this.$scope.allTypesOfCertificates[i].disableChecked = "false";
      }
    }


    private getPreviousCertificates() {
      this.admissionCertificateVerificationService.getSubmittedCertificates(this.$scope.semester.id, this.$scope.receiptId)
          .then((previousCertificates: Array<IPreviousCertificate>) => {
            this.$scope.previousCertificates = previousCertificates;
            this.$scope.lengthOfPreviousCertificates = previousCertificates.length;
            this.organizeCertificates();
          });
    }


    private organizeCertificates(): void {
      this.disableCertificates();
      this.categorizeAllTypesOfCertificates();
    }


    private disableCertificates() {
      if(this.$scope.studentStatus == "Verified" || this.$scope.studentStatus == "Rejected"){
        this.$scope.disableCheckAllCheckbox = false;
        for(var i = 0; i < this.$scope.lengthOfTotalCertificates; i++) {
          this.$scope.allTypesOfCertificates[i].disableChecked = "true";
        }
      }
      else if (this.$scope.lengthOfPreviousCertificates > 0) {
        this.$scope.disableCheckAllCheckbox = false;
        this.increaseCountForPreviousCertificates();
        for (var p = 0; p < this.$scope.lengthOfPreviousCertificates; p++) {
          for (var q = 0; q < this.$scope.lengthOfTotalCertificates; q++) {
            if (this.$scope.previousCertificates[p].id == this.$scope.allTypesOfCertificates[q].id) {
              this.$scope.allTypesOfCertificates[q].disableChecked = "true";
              break;
            }
          }
        }
      }
    }


    private increaseCountForPreviousCertificates() {
      for (var i = 0; i < this.$scope.previousCertificates.length; i++) {
        if (this.$scope.previousCertificates[i].type == "GL") {
          this.$scope.countGL++;
        }
        else if (this.$scope.previousCertificates[i].type == "GCE") {
          this.$scope.countGCE++;
        }
        else if (this.$scope.previousCertificates[i].type = "ALL") {
          this.$scope.countAll++;
        }
        else if (this.$scope.previousCertificates[i].type == "FF") {
          this.$scope.countFF++;
        }
        else if (this.$scope.previousCertificates[i].type == "RA") {
          this.$scope.countRA++;
        }
      }
    }


    private categorizeAllTypesOfCertificates() {
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
      this.admissionCertificateVerificationService.getAllPreviousComments(this.$scope.semester.id, this.$scope.receiptId)
          .then((previousComments: Array<IPreviousComment>) => {
            this.$scope.previousComments = previousComments;
          });
    }


    private addDate(): void {
      setTimeout(function () {
        $('.datepicker-default').datepicker();
        $('.datepicker-default').on('change', function () {
          $('.datepicker').hide();
        });
      }, 100);
    }


    private UnderTakenDeadline(deadLine: string) {
      this.$scope.data.deadLine = deadLine;
    }


    private underTaken(): void {
      if(this.$scope.data.deadLine == null || this.$scope.data.deadLine == ""){
        this.notify.warn("Please assign a date");
      }
      else{
        this.convertToJson(2).then((json: any) => {
          this.admissionCertificateVerificationService.saveAll(json)
              .then((message: any) => {
                this.search(this.$scope.receiptId);
                this.admissionCertificateVerificationService.getUndertakenForm(+this.$scope.programType.id, this.$scope.semester.id, this.$scope.receiptId);
              });
        });
      }
    }


    private rejected(): void {
      this.convertToJson(3).then((json: any) => {
        this.admissionCertificateVerificationService.saveAll(json)
            .then((message: any) => {
              this.search(this.$scope.receiptId);
            });
      });
    }


    private Approved(): void {
      this.convertToJson(1)
          .then((json: any) => {
            this.admissionCertificateVerificationService.saveAll(json)
                .then((message: any) => {
                  this.search(this.$scope.receiptId);
                });
          });
  }


    private isChecked(allTheCertificates: Array<ITypesOfCertificate>): boolean {
      if (allTheCertificates == null) {
        return false;
      }
      for(var i = 0; i < allTheCertificates.length; i++) {
        if(allTheCertificates[i].type == "GL"){
          return this.$scope.glSelected.length == allTheCertificates.length;
        }
        if(allTheCertificates[i].type == "GCE"){
          return this.$scope.gceSelected.length == allTheCertificates.length;
        }
        if(allTheCertificates[i].type == "ALL"){
          return this.$scope.nSelected.length == allTheCertificates.length;
        }
        if(allTheCertificates[i].type == "FF"){
          return this.$scope.ffSelected.length == allTheCertificates.length;
        }
        if(allTheCertificates[i].type == "RA"){
          return this.$scope.raSelected.length == allTheCertificates.length;
        }
      }
    }


    private toggleAll(allTheCertificates: Array<ITypesOfCertificate>): void {
      for (var i = 0; i < allTheCertificates.length; i++) {
        if (allTheCertificates[i].type == "GL") {
          if (this.$scope.glSelected.length == allTheCertificates.length) {
            for (var i = 0; i < this.$scope.glSelected.length; i++) {
              this.decreaseCountForCurrentlySelectedCertificates(this.$scope.glSelected[i].type);
            }
            this.$scope.glSelected = [];
          }
          else if (this.$scope.glSelected.length == 0 || this.$scope.glSelected.length > 0) {
            this.$scope.countGL = 0;
            this.$scope.glSelected = allTheCertificates.slice(0);
            for (var i = 0; i < this.$scope.glSelected.length; i++) {
              this.increaseCountForCurrentlySelectedCertificates(this.$scope.glSelected[i]);
            }
          }
        }
        else if (allTheCertificates[i].type == "GCE") {
          if (this.$scope.gceSelected.length == allTheCertificates.length) {
            for (var i = 0; i < this.$scope.gceSelected.length; i++) {
              this.decreaseCountForCurrentlySelectedCertificates(this.$scope.gceSelected[i].type);
            }
            this.$scope.gceSelected = [];
          }
          else if (this.$scope.gceSelected.length == 0 || this.$scope.gceSelected.length > 0) {
            this.$scope.countGCE = 0;
            this.$scope.gceSelected = allTheCertificates.slice(0);
            for (var i = 0; i < this.$scope.gceSelected.length; i++) {
              this.increaseCountForCurrentlySelectedCertificates(this.$scope.gceSelected[i]);
            }
          }
        }
        else if (allTheCertificates[i].type == "ALL") {
          if (this.$scope.nSelected.length == allTheCertificates.length) {
            for (var i = 0; i < this.$scope.nSelected.length; i++) {
              this.decreaseCountForCurrentlySelectedCertificates(this.$scope.nSelected[i].type);
            }
            this.$scope.nSelected = [];
          }
          else if (this.$scope.nSelected.length == 0 || this.$scope.nSelected.length > 0) {
            this.$scope.countAll = 0;
            this.$scope.nSelected = allTheCertificates.slice(0);
            for (var i = 0; i < this.$scope.nSelected.length; i++) {
              this.increaseCountForCurrentlySelectedCertificates(this.$scope.nSelected[i]);
            }
          }
        }
        else if (allTheCertificates[i].type == "FF") {
          if (this.$scope.ffSelected.length == allTheCertificates.length) {
            for (var i = 0; i < this.$scope.ffSelected.length; i++) {
              this.decreaseCountForCurrentlySelectedCertificates(this.$scope.ffSelected[i].type);
            }
            this.$scope.ffSelected = [];
          }
          else if (this.$scope.ffSelected.length == 0 || this.$scope.ffSelected.length > 0) {
            this.$scope.countFF = 0;
            this.$scope.ffSelected = allTheCertificates.slice(0);
            for (var i = 0; i < this.$scope.ffSelected.length; i++) {
              this.increaseCountForCurrentlySelectedCertificates(this.$scope.ffSelected[i]);
            }
          }
        }
        else if (allTheCertificates[i].type == "RA") {
          if (this.$scope.raSelected.length == allTheCertificates.length) {
            for (var i = 0; i < this.$scope.raSelected.length; i++) {
              this.decreaseCountForCurrentlySelectedCertificates(this.$scope.raSelected[i].type);
            }
            this.$scope.raSelected = [];
          }
          else if (this.$scope.raSelected.length == 0 || this.$scope.raSelected.length > 0) {
            this.$scope.countRA = 0;
            this.$scope.raSelected = allTheCertificates.slice(0);
            for (var i = 0; i < this.$scope.raSelected.length; i++) {
              this.increaseCountForCurrentlySelectedCertificates(this.$scope.raSelected[i]);
            }
          }
        }
        this.enableOrDisableButtons();
      }
    }


    private exists(allCertificates: ITypesOfCertificate): boolean {
      if (allCertificates.type == "GL") {
        for (var i = 0; i < this.$scope.previousCertificates.length; i++) {
          if (allCertificates.id == this.$scope.previousCertificates[i].id) {
            return true;
          }
        }
        return this.$scope.glSelected.indexOf(allCertificates) > -1;
      }
      else if (allCertificates.type == "GCE") {
        for (var i = 0; i < this.$scope.previousCertificates.length; i++) {
          if (allCertificates.id == this.$scope.previousCertificates[i].id) {
            return true;
          }
        }
        return this.$scope.gceSelected.indexOf(allCertificates) > -1;
      }
      else if (allCertificates.type == "ALL") {
        for (var i = 0; i < this.$scope.previousCertificates.length; i++) {
          if (allCertificates.id == this.$scope.previousCertificates[i].id) {
            return true;
          }
        }
        return this.$scope.nSelected.indexOf(allCertificates) > -1;
      }
      else if (allCertificates.type == "FF") {
        for (var i = 0; i < this.$scope.previousCertificates.length; i++) {
          if (allCertificates.id == this.$scope.previousCertificates[i].id) {
            return true;
          }
        }
        return this.$scope.ffSelected.indexOf(allCertificates) > -1;
      }
      else if (allCertificates.type == "RA") {
        for (var i = 0; i < this.$scope.previousCertificates.length; i++) {
          if (allCertificates.id == this.$scope.previousCertificates[i].id) {
            return true;
          }
        }
        return this.$scope.raSelected.indexOf(allCertificates) > -1;
      }
    }


    private toggleSelection(allCertificates: ITypesOfCertificate): void {

      if (allCertificates.type == "GL") {

        var idx = this.$scope.glSelected.indexOf(allCertificates);

        if (idx > -1) {
          this.$scope.temp = this.$scope.glSelected.splice(idx, 1);
          this.decreaseCountForCurrentlySelectedCertificates(this.$scope.temp[0].type);
        }
        else {
          this.$scope.glSelected.push(allCertificates);
          this.increaseCountForCurrentlySelectedCertificates(allCertificates);
        }
      }
      else if (allCertificates.type == "GCE") {

        var idx = this.$scope.gceSelected.indexOf(allCertificates);

        if (idx > -1) {
          this.$scope.temp = this.$scope.gceSelected.splice(idx, 1);
          this.decreaseCountForCurrentlySelectedCertificates(this.$scope.temp[0].type);
        }
        else {
          this.$scope.gceSelected.push(allCertificates);
          this.increaseCountForCurrentlySelectedCertificates(allCertificates);
        }
      }
      else if (allCertificates.type == "ALL") {

        var idx = this.$scope.nSelected.indexOf(allCertificates);

        if (idx > -1) {
          this.$scope.temp = this.$scope.nSelected.splice(idx, 1);
          this.decreaseCountForCurrentlySelectedCertificates(this.$scope.temp[0].type);
        }
        else {
          this.$scope.nSelected.push(allCertificates);
          this.increaseCountForCurrentlySelectedCertificates(allCertificates);
        }
      }
      else if (allCertificates.type == "FF") {

        var idx = this.$scope.ffSelected.indexOf(allCertificates);

        if (idx > -1) {
          this.$scope.temp = this.$scope.ffSelected.splice(idx, 1);
          this.decreaseCountForCurrentlySelectedCertificates(this.$scope.temp[0].type);
        }
        else {
          this.$scope.ffSelected.push(allCertificates);
          this.increaseCountForCurrentlySelectedCertificates(allCertificates);
        }
      }
      else if (allCertificates.type == "RA") {

        var idx = this.$scope.raSelected.indexOf(allCertificates);

        if (idx > -1) {
          this.$scope.temp = this.$scope.raSelected.splice(idx, 1);
          this.decreaseCountForCurrentlySelectedCertificates(this.$scope.temp[0].type);
        }
        else {
          this.$scope.raSelected.push(allCertificates);
          this.increaseCountForCurrentlySelectedCertificates(allCertificates);
        }
      }
      this.enableOrDisableButtons();
    }


    private increaseCountForCurrentlySelectedCertificates(allSelectedCertificates: ITypesOfCertificate) {
      if (allSelectedCertificates.type == "GL") {
        this.$scope.countGL++;
      }
      else if (allSelectedCertificates.type == "GCE") {
        this.$scope.countGCE++;
      }
      else if (allSelectedCertificates.type == "ALL") {
        this.$scope.countAll++;
      }
      else if (allSelectedCertificates.type == "FF") {
        this.$scope.countFF++;
      }
      else if (allSelectedCertificates.type == "RA") {
        this.$scope.countRA++;
      }
    }

    private decreaseCountForCurrentlySelectedCertificates(allDeselectedCertificates: string) {
      if (allDeselectedCertificates == "GL") {
        this.$scope.countGL--;
      }
      else if (allDeselectedCertificates == "GCE") {
        this.$scope.countGCE--;
      }
      else if (allDeselectedCertificates == "ALL") {
        this.$scope.countAll--;
      }
      else if (allDeselectedCertificates == "FF") {
        this.$scope.countFF--;
      }
      else if (allDeselectedCertificates == "RA") {
        this.$scope.countRA--;
      }
    }


    private enableOrDisableButtons() {
      if (this.$scope.studentQuota == "GL") {
        if (this.$scope.countGL >= 5 && this.$scope.countAll >= 1) {
          this.$scope.disableApprovedButton = false;
          this.$scope.disableRejectedButton = false;
          this.$scope.disableUndertakenButton = true;
          this.$scope.disableDateField = true;
        }
        else{
          this.$scope.disableApprovedButton = true;
          this.$scope.disableRejectedButton = false;
          this.$scope.disableUndertakenButton = false;
          this.$scope.disableDateField = false;
        }
      }
      else if (this.$scope.studentQuota == "FF") {
        if (this.$scope.countGL >= 5 && this.$scope.countAll >= 1 && this.$scope.countFF >= 3) {
          this.$scope.disableApprovedButton = false;
          this.$scope.disableRejectedButton = false;
          this.$scope.disableUndertakenButton = true;
          this.$scope.disableDateField = true;
        }
        else{
          this.$scope.disableApprovedButton = true;
          this.$scope.disableRejectedButton = false;
          this.$scope.disableUndertakenButton = false;
          this.$scope.disableDateField = false;
        }
      }
      else if (this.$scope.studentQuota == "RA") {
        if (this.$scope.countGL >= 5 && this.$scope.countAll >= 1 && this.$scope.countRA >= 2) {
          this.$scope.disableApprovedButton = false;
          this.$scope.disableRejectedButton = false;
          this.$scope.disableUndertakenButton = true;
          this.$scope.disableDateField = true;
        }
        else{
          this.$scope.disableApprovedButton = true;
          this.$scope.disableRejectedButton = false;
          this.$scope.disableUndertakenButton = false;
          this.$scope.disableDateField = false;
        }
      }
      else if (this.$scope.studentQuota == "GCE") {
        if (this.$scope.countGCE >= 5 && this.$scope.countAll >= 1) {
          this.$scope.disableApprovedButton = false;
          this.$scope.disableRejectedButton = false;
          this.$scope.disableUndertakenButton = true;
          this.$scope.disableDateField = true;
        }
        else{
          this.$scope.disableApprovedButton = true;
          this.$scope.disableRejectedButton = false;
          this.$scope.disableUndertakenButton = false;
          this.$scope.disableDateField = false;
        }
      }
    }


    private convertToJson(status: number): ng.IPromise<any> {
      var defer = this.$q.defer();
      var completeJson = {};
      var jsonObject = [];
      this.pushAllCertificatesInOneArray();

      var item: any = {};
      item['programType'] = +this.$scope.programType.id;
      item['semesterId'] = this.$scope.semester.id;
      item['receiptId'] = this.$scope.receiptId;
      item['status'] = status;
      item['comment'] = this.$scope.data.userComment;
      if(status == 2) {
        item['undertakeDeadLine'] = this.$scope.data.deadLine;
      }
      else{
        item['undertakeDeadLine'] = "";
      }

      var certificateJsonObject=[];
      for(var i = 0; i < this.$scope.totalSelected.length; i++) {
        var certificates: any = {};
        certificates['id'] = this.$scope.totalSelected[i].id;
        certificateJsonObject.push(certificates);
      }
      item['certificateIds'] = certificateJsonObject;

      jsonObject.push(item);
      completeJson["entries"] = jsonObject;
      defer.resolve(completeJson);
      return defer.promise;
    }


    private pushAllCertificatesInOneArray() {
      for (var i = 0; i < this.$scope.glSelected.length; i++) {
        this.$scope.totalSelected.push(this.$scope.glSelected[i]);
      }
      for (var i = 0; i < this.$scope.gceSelected.length; i++) {
        this.$scope.totalSelected.push(this.$scope.gceSelected[i]);
      }
      for (var i = 0; i < this.$scope.nSelected.length; i++) {
        this.$scope.totalSelected.push(this.$scope.nSelected[i]);
      }
      for (var i = 0; i < this.$scope.ffSelected.length; i++) {
        this.$scope.totalSelected.push(this.$scope.ffSelected[i]);
      }
      for (var i = 0; i < this.$scope.raSelected.length; i++) {
        this.$scope.totalSelected.push(this.$scope.raSelected[i]);
      }
    }


    private showRightDiv() {
      this.$scope.rightDiv = true;
      this.$scope.mainDiv = false;
      Utils.expandRightDiv();
    }
  }

  UMS.controller("AdmissionCertificateVerification",AdmissionCertificateVerification);
}
