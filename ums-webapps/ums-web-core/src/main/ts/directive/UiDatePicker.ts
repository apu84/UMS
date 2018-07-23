module ums {
  class UiDatePicker implements ng.IDirective {
    static $inject = ['$timeout'];

    constructor(private $timeout: ng.ITimeoutService) {

    }

    public restrict: string = "A";
    public scope = {
      model: '=dateModel',
      format: '@dateFormat',
      disable: '@disable',
      dateChanged: '&dateChanged'
    };

    public link = ($scope: any, element: any, attribute: any) => {
      element.datepicker({
        dateFormat: 'dd-mm-yy',
        onSelect: (date) => {
          $scope.model = date;
          $scope.$apply();
          $scope.dateChanged();
          date.css("color", "red");
        }
      })

      element.datepicker().datepicker("setDate", new Date());

    };

  }

  UMS.directive('uiDatePicker', ['$timeout', ($timeout: ng.ITimeoutService) => new UiDatePicker($timeout)]);
}


/*
* .ui-widget-content .ui-state-default {
    border: 0;
    border-top-color: initial;
    border-top-style: initial;
    border-top-width: 0px;
    border-right-color: initial;
    border-right-style: initial;
    border-right-width: 0px;
    border-bottom-color: initial;
    border-bottom-style: initial;
    border-bottom-width: 0px;
    border-left-color: initial;
    border-left-style: initial;
    border-left-width: 0px;
    border-image-source: initial;
    border-image-slice: initial;
    border-image-width: initial;
    border-image-outset: initial;
    border-image-repeat: initial;
    background: whitesmoke;
    background-image: initial;
    background-position-x: initial;
    background-position-y: initial;
    background-size: initial;
    background-repeat-x: initial;
    background-repeat-y: initial;
    background-attachment: initial;
    background-origin: initial;
    background-clip: initial;
    background-color: whitesmoke;
    color: black;
    cursor: pointer;*/