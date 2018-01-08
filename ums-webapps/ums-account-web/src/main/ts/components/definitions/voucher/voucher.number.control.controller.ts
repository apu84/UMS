module ums {

  class VoucherNumberControlController {
    public static $inject = ['$scope', '$modal', 'notify', 'VoucherService', '$timeout'];

    constructor($scope: ng.IScope, private $modal: any, private notify: Notify, private voucherService: VoucherService, private $timeout: ng.ITimeoutService) {

    }

    
  }

  UMS.controller("VoucherNumberController", VoucherNumberControlController);
}