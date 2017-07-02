module ums {
  export class DuesStatusDirective {
    public bindToController: boolean = true;
    public controller: any = DuesStatusController;
    public controllerAs: string = 'vm';
    public scope: any = {
      status: '='
    };
    public templateUrl: string = 'views/fee/dues/dues.status.html';
  }

  class DuesStatusController {
    private status: SelectedFilterValue;

    public statuses: { label: string, value: number }[] = [
      {label: "Not paid", value: 0},
      {label: "Applied", value: 1},
      {label: "Paid", value: 2}
    ];

    constructor() {
    }
  }

  UMS.directive('duesStatus', () => new DuesStatusDirective());
}
