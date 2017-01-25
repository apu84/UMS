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

   showDescriptionSection:boolean;

   searchByReceiptId:Function;
   expandDiv:Function;
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
     $scope.showDescriptionSection=false;

     $scope.searchByReceiptId = this.searchByReceiptId.bind(this);
     $scope.expandDiv = this.expandDiv.bind(this);

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
      this.paymentInfoService.fetchAdmissionPaymentInfo(receiptId, this.$scope.semester.id).then((data)=>{
        this.$scope.paymentInfo=[];
        this.$scope.paymentInfo = data;
      });
    }
 }

 UMS.controller('AdmissionFee', AdmissionFee);

}