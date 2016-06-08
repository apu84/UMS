///<reference path="../../ts/lib/amCharts.ts"/>
module ums {
  export class AmChart implements ng.IDirective {

    constructor() {
    }
    public restrict: string = 'A';
    //public require: string = 'ngModel';

    public link = (scope, element, attrs)=> {
      /*
      return {
        restrict: 'E',
        scope: {
          type: '@',
          source: '=',
          chartId: '@'
        },
        template: '<div id="{{chartId}}" style="min-width: 310px; height: 400px; margin: 0 auto"></div>',
        link: function (scope, element, attrs) {
          var chart:any = false;

          var initChart = function () {
            if (chart) chart.destroy();
            var config = scope.config || {};

            chart = AmCharts.makeChart(scope.chartId, {
              "type": scope.type,
              "theme": "none",
              "marginLeft": 20,
              "pathToImages": "http://www.amcharts.com/lib/3/images/",
              "dataProvider": scope.source,
              "valueAxes": [{
                "axisAlpha": 0,
                "inside": true,
                "position": "left",
                "ignoreAxisWidth": true
              }],
              "graphs": [{
                "balloonText": "[[category]]<br><b><span style='font-size:14px;'>[[value]]</span></b>",
                "bullet": "round",
                "bulletSize": 6,
                "lineColor": "#d1655d",
                "lineThickness": 2,
                "negativeLineColor": "#637bb6",
                "type": "smoothedLine",
                "valueField": "value"
              }],
              "chartScrollbar": {},
              "chartCursor": {
                "categoryBalloonDateFormat": "YYYY",
                "cursorAlpha": 0,
                "cursorPosition": "mouse"
              },
              "dataDateFormat": "YYYY",
              "categoryField": "year",
              "categoryAxis": {
                "minPeriod": "YYYY",
                "parseDates": true,
                "minorGridAlpha": 0.1,
                "minorGridEnabled": true
              }
            });


          };

          initChart();


        }//end watch
      }*/
    };

  }

  UMS.directive("amChart", [() => new AmChart()]);
}