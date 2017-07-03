module ums {
  export class PaymentStatusController {
    public static $inject = ['$scope', 'PaymentStatusService', 'notify'];
    public nextLink: string;
    public filters: PaymentStatusFilter[] = [];
    public paymentStatusList: PaymentStatus[];

    constructor(private $scope: ng.IScope,
                private paymentStatusService: PaymentStatusService,
                private notify: Notify) {
      this.navigate();
      $scope.$watch(() => this.filters, ()=> {
        this.navigate();
      }, true);
    }

    public navigate(url?: string): void {
      this.paymentStatusService.getPaymentStatus(this.filters, url).then((status: PaymentStatusResponse) => {
        if (url && this.paymentStatusList && this.paymentStatusList.length > 0) {
          this.paymentStatusList.push.apply(status.entries);
        }
        else {
          this.paymentStatusList = status.entries;
        }
        this.nextLink = status.next;
      });
    }
  }

  UMS.controller('PaymentStatusController', PaymentStatusController);
}
