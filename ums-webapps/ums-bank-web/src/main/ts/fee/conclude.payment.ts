module ums {
  interface ConcludePaymentScope extends ng.IScope {
    paymentStatusList: PaymentStatus[];
    concludePayment: Function;
    rejectPayment: Function;
    ok: Function;
    completedOn: {
      value?: string
    };
    concludeStatus: ConcludeStatus;
  }
  export class ConcludePayment {
    public static $inject = ['$scope', '$modalInstance', 'PaymentStatusService', 'notify',
      'selectedPaymentStatus', 'reload', 'concludeStatus'];

    constructor(private $scope: ConcludePaymentScope,
                private $modalInstance: any,
                private paymentStatusService: PaymentStatusService,
                private notify: Notify,
                private selectedPaymentStatus: PaymentStatus[],
                private reload: ReloadRef,
                private concludeStatus: ConcludeStatus) {
      this.$scope.paymentStatusList = selectedPaymentStatus;
      this.$scope.concludePayment = this.concludePayment.bind(this);
      this.$scope.rejectPayment = this.rejectPayment.bind(this);
      this.$scope.ok = this.ok.bind(this);
      this.$scope.completedOn = {};
      this.$scope.concludeStatus = concludeStatus;
    }

    private ok(): void {
      this.$modalInstance.dismiss('cancel');
      this.reload.reloadList = true;
    }

    private concludePayment(): void {
      this.addCompletedOn();
      if (this.concludeStatus.accepted) {
        this.acceptPayment();
      }
      else {
        this.rejectPayment();
      }
    }

    private acceptPayment(): void {
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

    private rejectPayment(): void {
      this.paymentStatusService.rejectPayments(this.selectedPaymentStatus)
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

    private addCompletedOn(): void {
      this.selectedPaymentStatus.forEach((payment: PaymentStatus) => {
        payment.completedOn = this.$scope.completedOn.value;
      });
    }
  }

  UMS.controller('ConcludePayment', ConcludePayment);
}
