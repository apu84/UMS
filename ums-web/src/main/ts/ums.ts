module ums {
  export var UMS = angular.module('UMS', [
    'ngRoute', 'ui.bootstrap', 'ui.router', 'oc.lazyLoad'
  ]);

  UMS.config(($stateProvider, $urlRouterProvider) => {
    //
    // For any unmatched url, redirect to /state1
    $urlRouterProvider.otherwise("/index");
    //
    // Now set up the states
    $stateProvider
        .state('index', {
          url: "/index",
          templateUrl: 'templates/states/main.html',
          controller: 'MainController',
          resolve: {
            loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
              return $ocLazyLoad.load({
                files: [
                  'vendors/jquery-tablesorter/jquery.tablesorter.min.js',
                  'vendors/calendar/zabuto_calendar.min.js',
                  'vendors/flot-chart/jquery.flot.categories.js',
                  'vendors/flot-chart/jquery.flot.pie.js',
                  'vendors/flot-chart/jquery.flot.tooltip.js',
                  'vendors/flot-chart/jquery.flot.resize.js',
                  'vendors/flot-chart/jquery.flot.fillbetween.js',
                  'vendors/flot-chart/jquery.flot.stack.js',
                  'vendors/flot-chart/jquery.flot.spline.js']
              });
            }]
          }
        })
  });
}