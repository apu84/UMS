
module ums{

  interface IEmployeeSearchScope extends ng.IScope{
    initialize:Function;
  }
  class EmployeeSearchController{

    private employees:Employee[];
    private employeesTmp: Employee[];
    private gridOptions:any;

    public static $inject = ['$scope','employeeService', 'departmentService', '$q'];
    constructor(private $scope:IEmployeeSearchScope, private employeeService: EmployeeService, private departmentService: DepartmentService, private $q: ng.IQService){

      $scope.initialize = this.initialize.bind(this);
    }

    private initialize(){
      console.log("In the initializations");
        this.employees = [];
        this.employeesTmp=[];
        this.employeeService.getActiveTeachers().then((employees:Employee[])=>{
          this.employees = employees;
          this.employeesTmp = angular.copy(this.employees);
          this.gridOptions.data=this.employees;
          console.log("Employees");
          console.log(employees);
        })

    }

  }

  class EmployeeSearchDir implements ng.IDirective{
    constructor(){

    }

    public restrict: string='EA';
    public scope={

    }
    public controller=EmployeeSearchController;
    public controllerAs='vm';

    public link = (scope:any, element:any, attributes:any)=>{
      scope.initialize();
    }
    public templateUrl:string='./views/directive/employee-search-directive/employee-search-dir.html';
  }


  UMS.directive("employeeSearchDir", [()=>{
    return new EmployeeSearchDir();
  }])
}