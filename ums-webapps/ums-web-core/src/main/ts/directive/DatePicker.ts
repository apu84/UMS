module ums {

  interface DatePickerScope extends ng.IScope {
    model: any;
    date: any;
    format: any;
    disable: boolean;
    dateChanged: Function;
    dateSelected: Function;
  }

  export class DatePickerController {

    public static $inject = ['$scope'];

    constructor(private $scope: DatePickerScope) {
      $scope.dateSelected = this.dateSelected.bind(this);
    }

    public dateSelected() {
      console.log("Date from directive");
      console.log(this.$scope.date);
      let date: Date = this.$scope.date;
      this.$scope.model = moment(date).format("dd-mm-yyyy");
      this.$scope.dateChanged();
    }
  }

  class DatePicker implements ng.IDirective {
    static $inject = ['$timeout'];

    constructor(private $timeout: ng.ITimeoutService) {

    }

    public restrict: string = "EA";
    public scope = {
      model: '=dateModel',
      format: '@dateFormat',
      disable: '@disable',
      dateChanged: '&dateChanged'
    };

    public controller = DatePickerController;
    public link = ($scope: any, element: any, attribute: any) => {

      console.log("From datepicker");
      console.log($scope);
      if ($scope.disable == undefined || $scope.disable == null)
        $scope.disable = false;
    };

    public templateUrl: string = "./views/directive/date-picker.html";
  }

  UMS.directive('datePicker', ['$timeout', ($timeout: ng.ITimeoutService) => new DatePicker($timeout)]);
}