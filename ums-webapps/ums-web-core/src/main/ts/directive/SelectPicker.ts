module ums {


  export class SelectPicker implements ng.IDirective {
    constructor(private $timeout: ng.ITimeoutService) {

    }

    public restrict: string = 'A';
    public scope={
      dataSet: '=datas'
    };

    public require: string = 'ngModel';

    private elementInitialized: boolean;

    public link = ($scope: any, element: JQuery, attributes: any, ngModelCtrl: ng.INgModelController) => {

      if (attributes['select'] == 'select2') {
console.log(attributes['page']);
        var placeHolder = attributes['placeholder'];
        if(placeHolder=="") placeHolder =  "Select an option";
        if(attributes['page']=="true")
        {
          var dataSet= $scope.dataSet;
          this.$timeout(() => {
            element.select2({
              placeholder: placeHolder,
              minimumInputLength: 2,
              query: function (options) {
                var pageSize = 50;
                var startIndex = (options.page - 1) * pageSize;
                var endIndex = startIndex + pageSize;
                var filteredData =dataSet;
                var obj="";
                if (options.term && options.term.length > 0) {
                  console.log(options.context);
                  if (!options.context) {
                    var term = options.term.toLowerCase();
                    options.context = dataSet.filter(function (metric:any) {
                      obj=metric.text.toLowerCase();
                      return ( obj.indexOf(term) !== -1 );
                    });
                  }
                  filteredData = options.context;
                }
                options.callback({
                  context: filteredData,
                  results: filteredData.slice(startIndex, endIndex),
                  more: (startIndex + pageSize) < filteredData.length
                });

              }
            });
            this.elementInitialized = true;
          });
        }
        else {
          this.$timeout(() => {
            element.select2({
              placeholder: "Select an option",
              allowClear: true
            });
            this.elementInitialized = true;
          });
        }

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
