module ums {
  export class DuesController {
    public static $inject = ['StudentDuesService'];
    public dues: StudentDue[];
    public filter: Filter[] = [];
    public selectedFilter;
    public selectedFilterValue;

    constructor(private studentDuesService: StudentDuesService) {
      this.navigate(null);
    }

    public navigate(url: string): void {
      this.studentDuesService.listDues(url).then((dues: StudentDue[]) => {
        this.dues = dues;
      });
    }

    public addFilter(): void {
      this.filter.push({key: this.selectedFilter, value: this.selectedFilterValue});
    }

    public removeFilter(removedFilter): void {
      for (let i = 0; i < this.filter.length; i++) {
        if (this.filter[i].key === removedFilter) {
          this.filter.splice(i, 1);
        }
      }
    }
  }

  UMS.controller('DuesController', DuesController);
}
