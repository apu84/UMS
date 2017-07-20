module ums {
  export class PaymentStatusList {
    public static $inject = ['$scope', 'PaymentStatusService'];
    public nextLink: string;
    public filters: Filter[];
    public selectedFilters: SelectedFilter[] = [];
    public paymentStatusList: PaymentStatus[];

    constructor(private $scope: ng.IScope,
                private paymentStatusService: PaymentStatusService) {
      this.navigate();
      this.paymentStatusService.getFilters().then((filters: Filter[]) => {
        this.filters = filters;

        $scope.$watch(() => this.selectedFilters, ()=> {
          this.navigate();
        }, true);
      });
    }

    public navigate(url?: string): void {
      this.paymentStatusService.getPaymentStatus(this.selectedFilters, url).then((status: PaymentStatusResponse) => {
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

  UMS.controller('PaymentStatusList', PaymentStatusList);
}
