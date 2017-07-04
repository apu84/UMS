module ums {
  interface ConcludePaymentScope extends ng.IScope {
    paymentStatusList: PaymentStatus[];
    concludePayment: Function;
    ok: Function;
    completedOn: {
      value?: string
    };
  }
  export class ConcludePayment {
    public static $inject = ['$scope', '$modalInstance', 'PaymentStatusService', 'notify',
      'selectedPaymentStatus', 'reload'];

    constructor(private $scope: ConcludePaymentScope,
                private $modalInstance: any,
                private paymentStatusService: PaymentStatusService,
                private notify: Notify,
                private selectedPaymentStatus: PaymentStatus[],
                private reload: ReloadRef) {
      this.$scope.paymentStatusList = selectedPaymentStatus;
      this.$scope.concludePayment = this.concludePayment.bind(this);
      this.$scope.ok = this.ok.bind(this);
      this.$scope.completedOn = {};
    }

    private ok(): void {
      this.$modalInstance.dismiss('cancel');
      this.reload.reloadList = true;
    }

    private concludePayment(): void {
      this.selectedPaymentStatus.forEach((payment: PaymentStatus) => {
        payment.completedOn = this.$scope.completedOn.value;
      });
      this.paymentStatusService.concludePayments(this.selectedPaymentStatus)
          .then((result: boolean) => {
            if (result) {
              this.ok();
              this.reload.reloadList = true;
            }
            else {
              this.notify.error('Failed to save');
            }
          });
    }
  }

  UMS.controller('ConcludePayment', ConcludePayment);
}
