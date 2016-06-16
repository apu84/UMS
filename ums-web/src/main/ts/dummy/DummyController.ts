module ums {
  export class DummyController {
    public static $inject = ['$scope', '$stateParams', '$timeout', 'HttpClient', '$window'];

    constructor($scope: any, $stateParams: any, $timeout: ng.ITimeoutService,
                private httpClient: HttpClient, private $window: ng.IWindowService,
                private $sce: ng.ISCEService) {
      $scope.amChartOptions = {
        data: [{
          year: 2005,
          income: 23.5,
          expenses: 18.1
        }, {
          year: 2006,
          income: 26.2,
          expenses: 22.8
        }, {
          year: 2007,
          income: 30.1,
          expenses: 23.9
        }, {
          year: 2008,
          income: 29.5,
          expenses: 25.1
        }, {
          year: 2009,
          income: 24.6,
          expenses: 25
        }],
        type: "serial",

        categoryField: "year",
        rotate: true,
        pathToImages: 'http://www.amcharts.com/lib/3/images/',
        legend: {
          enabled: true
        },
        chartScrollbar: {
          enabled: false
        },
        categoryAxis: {
          gridPosition: "start",
          parseDates: false
        },
        valueAxes: [{
          position: "top",
          title: "Million USD"
        }],
        graphs: [{
          type: "column",
          title: "Expense",
          valueField: "expenses",
          fillAlphas: 1
        }]
      };


      $timeout(()=> {
        var updatedData = [{
          year: 2010,
          income: 23.5,
          expenses: 18.1
        }, {
          year: 2011,
          income: 26.2,
          expenses: 22.8
        }, {
          year: 2012,
          income: 30.1,
          expenses: 23.9
        }, {
          year: 2013,
          income: 29.5,
          expenses: 25.1
        }, {
          year: 2014,
          income: 24.6,
          expenses: 25
        }];
        $scope.$broadcast("amCharts.updateData", updatedData);
      }, 5000);


      $scope.modalSettings = {};
      $scope.modalSettings.title = "Some title";
      $scope.modalSettings.body = "Some body";
      $scope.modalSettings.footer = "Some footer";
      $scope.modalSettings.header = "Some header";
      $scope.modalSettings.handler = "modal1";
      $scope.modalSettings.rightButton = () => {
        console.debug("Do something......");
      }

      this.generateXls();
    }

    private generateXls(): void {
      this.httpClient.get('dummyXls', 'application/vnd.ms-excel',
          (data: any, etag: string) => {
            var file = new Blob([data], {type: 'application/vnd.ms-excel'});
            var reader = new FileReader();
            reader.readAsDataURL(file);
            reader.onloadend = function (e) {
              window.open(reader.result, 'Excel', 'width=20,height=10,toolbar=0,menubar=0,scrollbars=no', false);
            };
          },
          (response: ng.IHttpPromiseCallbackArg<any>) => {
            console.error(response);
          }, 'arraybuffer');
    }

  }
  UMS.controller("DummyController", DummyController);
}