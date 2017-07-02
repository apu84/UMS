module ums {
  interface PaymentConfirmationScope extends ng.IScope {
    selectedPaymentsGroup: PaymentFeeTypeGroup[];
    ok: Function;
    receivePayment: Function;
    totalAmount: Function;
    save: Function;
  }
  export class PaymentConfirmation {
    public static $inject = ['$scope', '$modalInstance', 'ReceivePaymentService', 'selectedPaymentsGroup',
      'selectedReceivePayment', 'notify', 'reload'];

    constructor(private $scope: PaymentConfirmationScope,
                private $modalInstance: any,
                private receivePaymentService: ReceivePaymentService,
                private selectedPaymentsGroup: PaymentFeeTypeGroup[],
                private selectedReceivePayment: ReceivePayment[],
                private notify: Notify,
                private reload: ReloadRef) {
      this.$scope.selectedPaymentsGroup = selectedPaymentsGroup;
      this.$scope.ok = this.ok.bind(this);
      this.$scope.receivePayment = this.receivePayment.bind(this);
      this.$scope.totalAmount = this.totalAmount.bind(this);
    }

    private ok(): void {
      this.$modalInstance.dismiss('cancel');
      this.reload.reloadList = true;
    }

    private receivePayment(): void {
      this.reload.reloadList = true;
    }

    private totalAmount(payments: ReceivePayment[]): number {
      return this.receivePaymentService.totalAmount(payments);
    }

    private save(): void {

    }
  }

  UMS.controller('PaymentConfirmation', PaymentConfirmation);
}
