module ums {
  export interface MethodOfPaymentOption {
    label: string;
    value: string;
  }

  interface PaymentConfirmationScope extends ng.IScope {
    selectedPaymentsGroup: PaymentFeeTypeGroup[];
    ok: Function;
    receivePayment: Function;
    totalAmount: Function;
    save: Function;
    methodOfPaymentOptions: MethodOfPaymentOption[];
    receiveParams: ReceivedPayment;
  }

  export class PaymentConfirmation {
    public static $inject = ['$scope', '$modalInstance', 'ReceivePaymentService', 'selectedPaymentsGroup',
      'selectedReceivePayment', 'notify', 'reload', 'studentId'];
    private methodOfPaymentOptions: MethodOfPaymentOption[]
        = [{label: 'Select', value: '0'}, {label: 'Cash', value: '1'}, {label: 'Payorder', value: '2'},
      {label: 'Cheque', value: '3'}];

    private receiveParams: ReceivedPayment = {
      methodOfPayment: '0',
      receiptNo: undefined,
      paymentDetails: undefined,
      entries: undefined
    };

    constructor(private $scope: PaymentConfirmationScope,
                private $modalInstance: any,
                private receivePaymentService: ReceivePaymentService,
                private selectedPaymentsGroup: PaymentFeeTypeGroup[],
                private selectedReceivePayment: ReceivePayment[],
                private notify: Notify,
                private reload: ReloadRef,
                private studentId: string) {
      this.$scope.selectedPaymentsGroup = selectedPaymentsGroup;
      this.$scope.ok = this.ok.bind(this);
      this.$scope.receivePayment = this.receivePayment.bind(this);
      this.$scope.totalAmount = this.totalAmount.bind(this);
      this.$scope.receiveParams = this.receiveParams;
      this.$scope.methodOfPaymentOptions = this.methodOfPaymentOptions;
    }

    private ok(): void {
      console.log("closed");
      this.convertToJson();
     // alert("i am alert");
      this.$modalInstance.dismiss('cancel');
      this.reload.reloadList = true;
    }

    private receivePayment(): void {
      this.$scope.receiveParams.entries = this.selectedReceivePayment;
      this.receivePaymentService.receivePayment(this.studentId, this.$scope.receiveParams)
          .then((result) => {
            if (result) {
              this.ok();
               // this.convertToJson();
              this.reload.reloadList = true;
            }
            else {
              this.notify.error('Failed to save');
            }
          });
    }
      private convertToJson() {

          //var json= this.selectedReceivePayment.filter(p=>p.feeCategory=="Carry over exam fee" || p.feeCategory=="Improvement exam fee");
          var result= this.selectedReceivePayment.filter(p=>p.feeCategory=="Carry over exam fee");
          var completeJson = {};
          console.log("result in converto Json Just carry Courses");
          console.log(result);
          console.log(result.length);
          var jsonObj = [];
          for(var i=0;i<result.length;i++){
              var item = {};
              item["transactionId"] =result[i].transactionId;

              console.log("Items");
              console.log(item);
              //this.notify.success("sending./.....");
              jsonObj.push(item);
          }
          completeJson["entries"] = jsonObj;
          console.log("Complete json cci Bank section!!!!!!!!!!!!!!!");
          console.log(completeJson);

      }
    private totalAmount(payments: ReceivePayment[]): number {
      return this.receivePaymentService.totalAmount(payments);
    }
  }

  UMS.controller('PaymentConfirmation', PaymentConfirmation);
}
