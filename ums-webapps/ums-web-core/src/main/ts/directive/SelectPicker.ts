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
        if (element.hasClass('select2-hidden-accessible'))
        {
          element.select2('destroy');
        }


       var pHolder = attributes['placeholder'];
       if(pHolder=="") pHolder =  "Select an option";
        if(attributes['page']=="true")
        {
          var dataSet= $scope.dataSet;
          var a= $("#"+attributes['parent']);
          this.$timeout(() => {
            console.log(attributes['parent']);
            element.select2({
              allowClear:true,
              placeholder: pHolder,
              dropdownParent: a,
              minimumInputLength: 2,
              query: function (options) {

                console.log("-----");
                var pageSize = 50;
                var startIndex = (options.page - 1) * pageSize;
                var endIndex = startIndex + pageSize;
                var filteredData =dataSet;
                var obj="";
                if (options.term && options.term.length > 0) {
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
