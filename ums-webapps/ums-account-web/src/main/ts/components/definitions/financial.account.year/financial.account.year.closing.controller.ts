module ums{
    export class FinancialAccountYearClosingController{

        public static $inject = ['$scope', 'notify', 'FinancialAccountYearService', '$q', '$state'];

        constructor(private $scope: ng.IScope, private notify: Notify, private financialAccountYearService: FinancialAccountYearService, private $q: ng.IQService, private $state:any) {

        }

        public cancel(){
            this.$state.go("financialAccountYear");
        }
    }

    UMS.controller("FinancialAccountYearClosingController", FinancialAccountYearClosingController);
}