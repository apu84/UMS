module ums {
  export class ListFilterDirective {
    public bindToController: boolean = true;
    public controller: any = ListFilterController;
    public controllerAs: string = 'vm';
    public scope: any = {
      filters: '=',
      selectedFilters: '='
    };
    public templateUrl: string = 'views/fee/filter/list.filter.directive.html';
  }

  class ListFilterController {
    private filters: Filter[];
    private selectedFilters: SelectedFilter[];
    private filter: Filter;
    private filterValue: FilterOption = {
      label: null,
      value: null
    };
    private selectedFilter: string;
    private selectedFilterValue: any;

    constructor() {
    }

    public addFilter(): void {
      console.log("In the add filter");

      if (this.selectedFilters.filter((selectedFilter: SelectedFilter) => selectedFilter.filter.value === this.filter.value).length === 0) {
        this.selectedFilters.push({
          id: Math.round(Math.random() * 1000),
          filter: this.filter,
          value: this.filterValue
        });


        console.log(this.selectedFilters);
      }
    }

    public removeFilter(removedFilterId: number): void {
      for (let i = 0; i < this.selectedFilters.length; i++) {
        if (this.selectedFilters[i].id === removedFilterId) {
          this.selectedFilters.splice(i, 1);
        }
      }
    }

    public setFilter(): void {
      console.log("In the set filter");
      if (this.selectedFilter) {
        this.filter = this.filters.filter((filter: Filter) => filter.value === this.selectedFilter)[0];
        this.filterValue = {
          label: null,
          value: null
        }
      }
    }

    public setFilterValue(): void {
      console.log("In the set filter value");
      if (this.filter && this.selectedFilterValue !== null) {
        this.filterValue
            = this.filter.options.filter((filterOption: FilterOption) => filterOption.value == this.selectedFilterValue)[0];
      }

    }
  }

  UMS.directive('listFilter', () => new ListFilterDirective());
}
