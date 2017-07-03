module ums {
  export interface SelectedFilterValue {
    label?: string;
    value?: string | number | boolean;
  }

  export class DuesFilterDirective {
    public bindToController: boolean = true;
    public controller: any = DuesFilterController;
    public controllerAs: string = 'vm';
    public scope: any = {
      filters: '='
    };
    public templateUrl: string = 'views/fee/dues/dues.filter.html';
  }

  class DuesFilterController {
    private filters: Filter[];
    private selectedFilter: string;
    private selectedFilterValue: SelectedFilterValue = {};

    public filterCriteria: { label: string, value: string }[] = [
      {label: "Student ID", value: "STUDENT_ID"},
      {label: "Due status", value: "DUE_STATUS"},
      {label: "Due type", value: "DUE_TYPE"}
    ];

    constructor() {
    }

    public addFilter(): void {
      this.filters.push({
        id: Math.round(Math.random() * 1000),
        key: this.selectedFilter,
        value: this.selectedFilterValue.value,
        label: this.selectedFilterValue.label
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
  }

  UMS.directive('duesFilter', () => new DuesFilterDirective());
}
