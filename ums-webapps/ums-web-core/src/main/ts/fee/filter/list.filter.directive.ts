module ums {
  import IIntervalService = ng.IIntervalService;
  import IQService = ng.IQService;

  export class ListFilterDirective {

    public static STATUS = "STATUS";
    public static STUDENT_ID = "STUDENT_ID";
    public static FILTER_ID = "FILTER_ID";


    public bindToController: boolean = true;
    public controller: any = ListFilterController;
    public controllerAs: string = 'vm';
    public scope: any = {
      filters: '=',
      selectedFilters: '=',
      selectedFilter: '=',
      selectedFilterValue: '='
    };
    public templateUrl: string = 'views/fee/filter/list.filter.directive.html';
  }

  class ListFilterController {
    public static $inject = ['$interval', '$q'];
    private filters: Filter[];
    private filterMap: any;
    private selectedFilters: SelectedFilter[];
    private filter: Filter;
    private filterValue: FilterOption = {
      label: null,
      value: null
    };

    private selectedFilter: string;
    private selectedFilterValue: number;
    private selectedFilterObj: Filter;


    constructor(private $interval: IIntervalService, private $q: IQService) {
      $interval(() => {
        if (this.filters !== null && this.selectedFilterObj == null) {
          this.selectedFilterObj = this.filters.filter((filter: Filter) => filter.value == this.selectedFilter)[0];
          this.setFilter();
        }
      }, 1000);
    }

    public addFilter(): void {

      if (this.selectedFilters.filter((selectedFilter: SelectedFilter) => selectedFilter.filter.value === this.filter.value).length === 0) {
        this.selectedFilters.push({
          id: Math.round(Math.random() * 1000),
          filter: this.filter,
          value: this.filterValue
        });


        console.log(this.selectedFilters);
      }
    }

    public removeFilter(removedFilterId?: number): void {
      if (removedFilterId != null) {
        for (let i = 0; i < this.selectedFilters.length; i++) {
          if (this.selectedFilters[i].id === removedFilterId) {
            this.selectedFilters.splice(i, 1);
          }
        }
      } else {
        this.selectedFilters = [];
      }

    }

    public setFilter(): void {
      console.log("Filters");
      console.log(this.filters);
      console.log("In the set filter");
      console.log("Selected filter");
      console.log(this.selectedFilters);
      this.initializeFilter().then((selectedFilter: string) => {
        if (selectedFilter) {
          this.selectedFilter = selectedFilter;
          this.filter = this.filters.filter((filter: Filter) => filter.value === selectedFilter)[0];
          this.filterValue = {
            label: null,
            value: null
          }
        }
      });

    }

    public initializeFilter(): ng.IPromise<String> {
      var defer: ng.IDeferred<String> = this.$q.defer();
      defer.resolve(this.selectedFilterObj.value);
      return defer.promise;
    }

    public setFilterValue(): void {
      console.log("In the set filter value");
      console.log("Filter options");
      console.log(this.selectedFilterValue);
      if (this.filter && this.selectedFilterValue !== null) {
        this.filterValue
            = this.filter.options.filter((filterOption: FilterOption) => filterOption.value == this.selectedFilterValue)[0];
      }

    }
  }

  UMS.directive('listFilter', () => new ListFilterDirective());
}
