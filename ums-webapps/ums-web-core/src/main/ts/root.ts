module ums {
  export var UMS = angular.module('UMS', [
    'ngRoute',
    'ngAnimate',
    'ui.bootstrap',
    'ui.router',
    'oc.lazyLoad',
    'LocalStorageModule',
    'ngCookies',
    'ngSanitize',
    'scrollable-table',
    'ui.sortable',
    'ngHandsontableApp',
    "dirPaginationApp",
    'ui.select',
      'ngMessages',
    'smart-table'
  ]).run(function(ExpireToken){

  });
}
