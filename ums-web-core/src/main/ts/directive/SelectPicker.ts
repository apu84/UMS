module ums {


  export class SelectPicker implements ng.IDirective {
    constructor(private $timeout: ng.ITimeoutService) {

    }

    public restrict: string = 'A';

    public require: string = 'ngModel';

    private elementInitialized: boolean;

    public link = ($scope: any, element: JQuery, attributes: any, ngModelCtrl: ng.INgModelController) => {

      if (attributes['select'] == 'select2') {
        this.$timeout(()=> {
          element.select2({
            placeholder: "Select an option",
            allowClear: true
          });
          this.elementInitialized = true;
        });

        var refreshSelect = () => {
          if (!this.elementInitialized) {
            // check after some interval for select2 to initialize
            this.$timeout(()=> {
              refreshSelect();
            }, 250);
            return;
          }
          this.$timeout(() => {
            element.trigger('change');
          });

        };

        $scope.$watch(attributes.ngModel, refreshSelect);

      } else if (attributes['select'] == 'select-picker') {
        this.$timeout(()=> {
          $(element).selectpicker({
            iconBase: 'fa',
            tickIcon: 'fa-check'
          });
          this.elementInitialized = true;
        });
      }
    };
  }


  UMS.directive("select", ['$timeout', ($timeout) => new SelectPicker($timeout)]);
}
