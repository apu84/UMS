module ums {
  export interface ConcludeStatus {
    accepted: boolean;
  }

  export class PaymentStatusController {
    public static $inject = ['$scope', 'PaymentStatusService', 'notify', '$modal'];
    public nextLink: string;
    public filters: Filter[];
    public selectedFilters: SelectedFilter[] = [];
    public paymentStatusList: PaymentStatus[];
    public selectedPayments: {[id: string]: boolean} = {};
    public selectedPaymentIds: string[];
    public selectedPaymentStatus: PaymentStatus[];
    public reloadReference: ReloadRef = {reloadList: false};
    public concludeStatus: ConcludeStatus = {accepted: false};

    constructor(private $scope: ng.IScope,
                private paymentStatusService: PaymentStatusService,
                private notify: Notify,
                private $modal: any) {
      this.navigate();
      this.paymentStatusService.getFilters().then((filters: Filter[]) => {
        this.filters = filters;

        $scope.$watch(() => this.selectedFilters, ()=> {
          this.navigate();
        }, true);
      });

      this.$scope.$watch(()=> {
        return this.selectedPayments;
      }, () => {
        if (this.selectedPayments) {
          this.selectedPaymentIds = Object.keys(this.selectedPayments)
              .map(
                  (key) => {
                    return this.selectedPayments[key] ? key : undefined;
                  })
              .filter((el)=> !!el);
        }
      }, true);

      $scope.$watch(() => this.reloadReference, (newVal: ReloadRef) => {
        if (newVal.reloadList) {
          this.navigate();
        }
      }, true);
    }

    public concludePayment(accepted: boolean): void {
      this.concludeStatus.accepted = accepted;
      this.selectedPaymentStatus
          = this.paymentStatusList.filter((payment: PaymentStatus)=> {
        return this.selectedPaymentIds.indexOf(payment.id) >= 0;
      });

      this.$modal.open({
        templateUrl: 'views/fee/conclude.payment.html',
        controller: ConcludePayment,
        resolve: {
          selectedPaymentStatus: () => this.selectedPaymentStatus,
          reload: () => this.reloadReference,
          concludeStatus: () => this.concludeStatus,
          loadMyCtrl: ['$ocLazyLoad', ($ocLazyLoad) => {
            return $ocLazyLoad.load({
              files: [
                'vendors/bootstrap-datepicker/css/datepicker.css',
                'vendors/bootstrap-datepicker/js/bootstrap-datepicker.js'
              ]
            });
          }]
        },
        size: 'lg'
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

  UMS.controller('PaymentStatusController', PaymentStatusController);
}
