module ums {
  export class DuesCategoriesDirective {
    public bindToController: boolean = true;
    public controller: any = DuesCategoriesController;
    public controllerAs: string = 'vm';
    public scope: any = {
      category: '='
    };
    public templateUrl: string = 'views/fee/dues/dues.categories.html';
  }

  class DuesCategoriesController {
    public static $inject: string[] = ['StudentDuesService'];
    private duesCategories: SelectedFilterValue[];
    private category: SelectedFilterValue;

    constructor(studentDuesService: StudentDuesService) {
      studentDuesService.getFeeCategories().then(
          (feeCategories: FeeCategory[]) => {
            this.duesCategories = feeCategories.map(
                (category: FeeCategory) => {
                  return {label: category.description, value: category.id};
                })
          });
    }
  }

  UMS.directive('duesCategories', () => new DuesCategoriesDirective());
}
