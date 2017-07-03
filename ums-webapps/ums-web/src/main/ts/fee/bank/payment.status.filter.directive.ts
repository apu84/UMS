module ums {
  interface SelectionProperty {
    label: string;
    value: any;
  }
  export class PaymentStatusFilterDirective implements ng.IDirective {
    public bindToController: boolean = true;
    public controller: any = PaymentStatusFilterController;
    public controllerAs: string = 'vm';
    public scope: any = {
      filters: '='
    };
    public templateUrl: string = 'views/fee/bank/payment.status.filter.html';
  }

  class PaymentStatusFilterController {
    private filters: PaymentStatusFilter[];
    private selectedFilter: string;
    private selectedFilterValue: SelectedFilterValue = {};

    public filterCriteria: { label: string, value: string }[] = [
      {label: 'Start', value: 'RECEIVED_START'},
      {label: 'End', value: 'RECEIVED_END'},
      {label: 'Transaction Id', value: 'TRANSACTION_ID'},
      {label: 'Account', value: 'ACCOUNT'},
      {label: 'Payment status', value: 'PAYMENT_COMPLETED'},
      {label: 'Method of Payment', value: 'METHOD_OF_PAYMENT'}
    ];

    public methodOfPaymentOptions: SelectionProperty[]
        = [{label: 'Select', value: '0'}, {label: 'Cash', value: '1'}, {label: 'Payorder', value: '2'},
      {label: 'Cheque', value: '3'}];

    public paymentCompletedOptions: SelectionProperty[]
        = [{label: 'Yes', value: true}, {label: 'No', value: false}];

    constructor() {
    }

    public addFilter(): void {
      this.deleteExisting();
      this.filters.push({
        id: Math.round(Math.random() * 1000),
        key: this.selectedFilter,
        value: this.selectedFilterValue.value,
        label: this.getCriteriaLabel(this.selectedFilter),
        labelValue: (this.selectedFilterValue.label ? this.selectedFilterValue.label : this.selectedFilterValue.value)
      });

    }

    public removeFilter(removedFilterId: number): void {
      for (let i = 0; i < this.filters.length; i++) {
        if (this.filters[i].id === removedFilterId) {
          this.filters.splice(i, 1);
        }
      }
    }

    public reset(): void {
      this.selectedFilterValue = {};
    }

    public selectOption(options: SelectionProperty[]): void {
      for (let i = 0; i < options.length; i++) {
        if (options[i].value == this.selectedFilterValue.value) {
          this.selectedFilterValue.label = options[i].label;
          break;
        }
      }
    }

    private deleteExisting(): void {
      for (let i = 0; i < this.filters.length; i++) {
        if (this.filters[i].key === this.selectedFilter) {
          this.filters.splice(i, 1);
          break;
        }
      }
    }

    private getCriteriaLabel(key: string): string {
      for (let i = 0; i < this.filterCriteria.length; i++) {
        if (this.filterCriteria[i].value === key) {
          return this.filterCriteria[i].label;
        }
      }
    }
  }

  UMS.directive('paymentStatusFilter', () => new PaymentStatusFilterDirective());
}
