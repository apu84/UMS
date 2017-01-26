/**
 * Created by Monjur-E-Morshed on 18-Jan-17.
 */

module ums{
 interface IAdmissionFee extends ng.IScope{
   semesters:Array<Semester>;
   semester:Semester;
   programTypes:Array<IProgramType>;
   programType:IProgramType;
   receiptId:string;
   admissionStudent:AdmissionStudent;
   paymentInfo:Array<PaymentInfo>;
   paymentModes:Array<IConstants>;
   paymentMode:IConstants;
   paymentType:number;
   amount: number;

   showDescriptionSection:boolean;
   showAdmissionFeeSection:boolean;
   showMigrationFeeSection:boolean;
   paid:boolean;

   searchByReceiptId:Function;
   expandDiv:Function;
   save:Function;
 }

 interface IConstants{
   id:any;
   name:string;
 }

  interface  IProgramType{
    id:string;
    name:string;
  }


  class AdmissionFee{
   public static $inject = ['appConstants','HttpClient','$scope','$q','notify','$sce','$window','semesterService', 'admissionStudentService','paymentInfoService'];
   constructor(private appConstants: any,
               private httpClient: HttpClient,
               private $scope: IAdmissionFee,
               private $q:ng.IQService,
               private notify: Notify,
               private $sce:ng.ISCEService,
               private $window:ng.IWindowService,
               private semesterService: SemesterService,
               private admissionStudentService:AdmissionStudentService,
               private paymentInfoService: PaymentInfoService) {


     $scope.programTypes = appConstants.programType;

     $scope.programType = $scope.programTypes[0];
     $scope.paymentModes = appConstants.paymentMode;

     $scope.paymentMode = $scope.paymentModes[0];
     $scope.showDescriptionSection=false;
     $scope.showAdmissionFeeSection = false;
     $scope.showMigrationFeeSection = false;
     $scope.paid = false;

     $scope.searchByReceiptId = this.searchByReceiptId.bind(this);
     $scope.expandDiv = this.expandDiv.bind(this);
     $scope.save = this.save.bind(this);

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


    private expandDiv(){
      Utils.expandRightDiv();
      this.$scope.showDescriptionSection=false;
    }

    private searchByReceiptId(receiptId:string){
      console.log("Working");
      this.$scope.showDescriptionSection=false;
      this.admissionStudentService.fetchAdmissionStudentByReceiptId(this.$scope.semester.id, +this.$scope.programType.id, receiptId).then((data)=>{
        this.$scope.admissionStudent = <AdmissionStudent>{};
        this.$scope.admissionStudent = data;
        console.log(this.$scope.admissionStudent);
        this.$scope.showDescriptionSection=true;
      });

      this.fetchPaymentInfo(receiptId);
    }

    private fetchPaymentInfo(receiptId:string){
      console.log("In the payment info section");

      this.paymentInfoService.fetchAdmissionPaymentInfo(receiptId, this.$scope.semester.id).then((data)=>{
        this.$scope.paymentInfo=[];
        this.$scope.paymentInfo = data;
        this.configurePaymentViewSection();
      });

    }

    private configurePaymentViewSection(){
      console.log("In the payment configuration section");
      if(this.$scope.paymentInfo.length===0){
        this.$scope.showAdmissionFeeSection = true;
        this.$scope.showMigrationFeeSection = false;
        this.$scope.paymentType = Utils.ADMISSION_FEE;
        this.configureAmount();

      }else{
        this.$scope.showMigrationFeeSection=true;
        this.$scope.showAdmissionFeeSection=false;
        this.$scope.paymentType = Utils.MIGRATION_FEE;
        this.configureAmount();
      }
    }

    private configureAmount(){
      if(this.$scope.showAdmissionFeeSection){
        if(this.$scope.admissionStudent.unit=='ENGINEERING'){
          this.$scope.amount=104800;
        }else{
          this.$scope.amount=80360;
        }
      }else{
        this.$scope.amount=4000;
      }
    }

    private save(){
      if(this.$scope.paid){
        this.convertToJson().then((data)=>{
          this.paymentInfoService.saveAdmissionPaymentInfo(data).then((message)=>{

          })
        });
      }else{
        this.notify.error("Paid checkbox is not selected");
      }

    }

    private convertToJson():ng.IPromise<any>{

      var defer = this.$q.defer();
      var completeJson={};
      var jsonObject=[];
      var item:any={};
      item['referenceId'] = this.$scope.admissionStudent.receiptId;
      item['semesterId'] = this.$scope.semester.id;
      item['paymentTYpe'] = this.$scope.paymentType;
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