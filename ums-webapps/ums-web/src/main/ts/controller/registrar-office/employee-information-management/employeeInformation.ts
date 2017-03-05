module ums{
  interface IEmployeeInformation extends ng.IScope{
    personal: boolean;
    academic: boolean;
    publication: boolean;
    training: boolean;
    award: boolean;
    experience: boolean;
    changeNav: Function;
  }

  class employeeInformation {

    public static $inject = ['appConstants', '$scope', '$q', 'notify', '$window', '$sce'];

    constructor(private appConstants: any,
                private $scope: IEmployeeInformation,
                private $q: ng.IQService,
                private notify: Notify,
                private $window: ng.IWindowService,
                private $sce: ng.ISCEService) {

      $scope.personal = true;
      $scope.changeNav = this.changeNav.bind(this);

    }

    private changeNav(navTitle: number){

      this.$scope.personal = false;
      this.$scope.academic = false;
      this.$scope.publication = false;
      this.$scope.training = false;
      this.$scope.award = false;
      this.$scope.experience = false;

      if(navTitle == null){

      }
      else if(navTitle == 1){
        this.$scope.personal = true;
      }
      else if(navTitle == 2){
        this.$scope.academic = true;
      }
      else if(navTitle == 3){
        this.$scope.publication = true;
      }
      else if(navTitle == 4){
        this.$scope.training = true;
      }
      else if(navTitle == 5){
        this.$scope.award = true;
      }
      else if(navTitle == 6){
        this.$scope.experience = true;
      }
    }
  }

  UMS.controller("employeeInformation",employeeInformation);
}