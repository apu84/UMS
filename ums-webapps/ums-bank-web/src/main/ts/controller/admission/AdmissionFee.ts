/**
 * Created by Monjur-E-Morshed on 18-Jan-17.
 */

module ums {
  interface IAdmissionFee extends ng.IScope {
    semesters: Array<Semester>;
    semester: Semester;
    programTypes: Array<IProgramType>;
    programType: IProgramType;
    receiptId: string;
    admissionStudent: AdmissionStudent;
    paymentInfo: Array<PaymentInfo>;
    paymentModes: Array<IConstants>;
    paymentMode: IConstants;
    paymentType: number;
    amount: number;
    exceptionMessage: string;

    showDescriptionSection: boolean;
    showAdmissionFeeSection: boolean;
    showMigrationFeeSection: boolean;
    paid: boolean;
    showException: boolean;
    disableButton: boolean;
    disableContent: boolean;

    searchByReceiptId: Function;
    expandDiv: Function;
    save: Function;
    changeCheckBoxValue: Function;
  }

  interface IConstants {
    id: any;
    name: string;
  }

  interface  IProgramType {
    id: string;
    name: string;
  }


  class AdmissionFee {
    public static $inject = ['appConstants', 'HttpClient', '$scope', '$q', 'notify', '$sce', '$window', 'semesterService', 'admissionStudentService', 'paymentInfoService'];

    constructor(private appConstants: any,
                private httpClient: HttpClient,
                private $scope: IAdmissionFee,
                private $q: ng.IQService,
                private notify: Notify,
                private $sce: ng.ISCEService,
                private $window: ng.IWindowService,
                private semesterService: SemesterService,
                private admissionStudentService: AdmissionStudentService,
                private paymentInfoService: PaymentInfoService) {


      $scope.programTypes = appConstants.programType;

      $scope.programType = $scope.programTypes[0];
      $scope.paymentModes = appConstants.paymentMode;

      $scope.paymentMode = $scope.paymentModes[0];
      $scope.showDescriptionSection = false;
      $scope.showAdmissionFeeSection = false;
      $scope.showMigrationFeeSection = false;
      $scope.disableButton = true;
      $scope.paid = false;

      $scope.searchByReceiptId = this.searchByReceiptId.bind(this);
      $scope.expandDiv = this.expandDiv.bind(this);
      $scope.save = this.save.bind(this);
      $scope.changeCheckBoxValue = this.changeCheckBoxValue.bind(this);

      this.getSemesters();


    }


    private getSemesters() {
      this.semesterService.fetchSemesters(+this.$scope.programType.id).then((semesters: Array<Semester>) => {
        this.$scope.semesters = [];
        for (var i = 0; i < semesters.length; i++) {
          if (semesters[i].status == 2) {
            this.$scope.semester = semesters[i];
          }
          this.$scope.semesters.push(semesters[i]);
        }

      });
    }


    private expandDiv() {
      Utils.expandRightDiv();
      this.$scope.showDescriptionSection = false;
    }

    private searchByReceiptId(receiptId: string) {
      console.log("Working");
      this.$scope.showDescriptionSection = false;
      this.admissionStudentService.fetchAdmissionStudentByReceiptId(this.$scope.semester.id, +this.$scope.programType.id, receiptId).then((data) => {
        this.$scope.admissionStudent = <AdmissionStudent>{};
        this.$scope.admissionStudent = data;
        console.log(this.$scope.admissionStudent);
        this.$scope.showDescriptionSection = true;
        if (this.$scope.admissionStudent.migrationStatus == Utils.NOT_MIGRATED || this.$scope.admissionStudent.migrationStatus==Utils.MIGRATED) {
          this.$scope.disableButton = true;
        } else {
          this.$scope.disableButton = false;
        }

        this.checkDeadline(this.$scope.admissionStudent.deadline);
      });

      this.fetchPaymentInfo(receiptId);
    }

    private checkDeadline(deadline:string){
      var date = this.stringToDate(deadline,'dd/MM/yyyy','/');
      var today = new Date();
      if(date<today){
       this.notify.error("Deadline over");
       this.$scope.showDescriptionSection=false;
       this.$scope.disableButton=true;
      }else{
        this.$scope.showDescriptionSection=true;
      }

    }

    private fetchPaymentInfo(receiptId: string) {
      console.log("In the payment info section");

      this.paymentInfoService.fetchAdmissionPaymentInfo(receiptId, this.$scope.semester.id).then((data) => {
        this.$scope.paymentInfo = [];
        this.$scope.paymentInfo = data;
        this.configurePaymentViewSection();
      });

    }

    private configurePaymentViewSection() {
      console.log("In the payment configuration section");
      if (this.$scope.paymentInfo.length === 0) {
        this.$scope.showAdmissionFeeSection = true;
        this.$scope.showMigrationFeeSection = false;
        this.$scope.paymentType = Utils.ADMISSION_FEE;
        this.configureAmount();

      } else {
        this.$scope.showMigrationFeeSection = true;
        this.$scope.showAdmissionFeeSection = false;
        this.$scope.paymentType = Utils.MIGRATION_FEE;
        this.configureAmount();
      }
    }

    private configureAmount() {
      console.log(this.$scope.admissionStudent.unit);
      if (this.$scope.showAdmissionFeeSection) {
        if (this.$scope.admissionStudent.unit == 'ENGINEERING') {
          this.$scope.amount = 104800;
        } else {
          this.$scope.amount = 80360;
        }
      } else {
        this.$scope.amount = 4000;
      }
    }

    /*
     *
     * stringToDate("17/9/2014","dd/MM/yyyy","/");
     stringToDate("9/17/2014","mm/dd/yyyy","/")
     stringToDate("9-17-2014","mm-dd-yyyy","-")
     */

    private stringToDate(_date, _format, _delimiter): any {
      var formatLowerCase = _format.toLowerCase();
      var formatItems = formatLowerCase.split(_delimiter);
      var dateItems = _date.split(_delimiter);
      var monthIndex = formatItems.indexOf("mm");
      var dayIndex = formatItems.indexOf("dd");
      var yearIndex = formatItems.indexOf("yyyy");
      var month = parseInt(dateItems[monthIndex]);
      month -= 1;
      var formatedDate = new Date(dateItems[yearIndex], month, dateItems[dayIndex]);
      return formatedDate;
    }


    private save() {
      this.convertToJson().then((data) => {
        this.paymentInfoService.saveAdmissionPaymentInfo(data).then((message) => {

        })
      });


    }

    private changeCheckBoxValue() {
      if (this.$scope.paid == false) {
        this.$scope.paid = true;
      } else {
        this.$scope.paid = false;
      }

      console.log("Check box value");
      console.log(this.$scope.paid);
    }

    private convertToJson(): ng.IPromise<any> {

      var defer = this.$q.defer();
      var completeJson = {};
      var jsonObject = [];
      var item: any = {};
      item['referenceId'] = this.$scope.admissionStudent.receiptId;
      item['semesterId'] = this.$scope.semester.id;
      item['paymentType'] = this.$scope.paymentType;
      item['amount'] = this.$scope.amount;
      item['paymentMode'] = this.$scope.paymentMode.id;
      jsonObject.push(item);
      completeJson['entries'] = jsonObject;
      defer.resolve(completeJson);

      return defer.promise;

    }


  }

  UMS.controller('AdmissionFee', AdmissionFee);

}