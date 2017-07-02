module ums {
  export class ReceivePaymentController {
    public static $inject = ['$scope', 'ReceivePaymentService', '$modal'];
    public studentId: string = '160105001';
    public appliedPayments: PaymentFeeTypeGroup[];
    public selectedPayments: {[key: string]: boolean} = {};
    public selectedTransactions: string[];
    public selectedPaymentsGroup: PaymentFeeTypeGroup[];
    public reloadReference: ReloadRef = {reloadList: false};

    constructor($scope: ng.IScope, private receivePaymentService: ReceivePaymentService, private $modal: any) {
      this.getAppliedPayments();
      $scope.$watch(()=> {
        return this.selectedPayments;
      }, () => {
        if (this.selectedPayments) {
          this.selectedTransactions = Object.keys(this.selectedPayments)
              .map(
                  (key) => {
                    return this.selectedPayments[key] ? key : undefined;
                  })
              .filter((el)=> !!el);
        }
      }, true);

      $scope.$watch(() => this.reloadReference, (newVal: ReloadRef) => {
        if (newVal.reloadList) {
          this.getAppliedPayments();
        }
      }, true);
    }

    public getAppliedPayments(): void {
      this.receivePaymentService.getPaymentsByStudent(this.studentId)
          .then((payments: PaymentFeeTypeGroup[]) => {
            this.appliedPayments = payments;
            this.reloadReference.reloadList = false;
          });
    }

    public totalAmount(payments: ReceivePayment[]): number {
      return this.receivePaymentService.totalAmount(payments);
    }

    public next(): void {
      let selectedReceivePayment: ReceivePayment[]
          = this.receivePaymentService.getPayments(this.appliedPayments, this.selectedTransactions);
      this.selectedPaymentsGroup = this.receivePaymentService.groupPayments(selectedReceivePayment);

      this.$modal.open({
        templateUrl: 'views/fee/bank/payment.confirmation.html',
        controller: PaymentConfirmation,
        resolve: {
          selectedPaymentsGroup: () => this.selectedPaymentsGroup,
          selectedReceivePayment: () => selectedReceivePayment,
          studentId: () => this.studentId,
          reload: () => this.reloadReference
        },
        size: 'lg'
      });
    }
  }
  UMS.controller('ReceivePaymentController', ReceivePaymentController);
}
