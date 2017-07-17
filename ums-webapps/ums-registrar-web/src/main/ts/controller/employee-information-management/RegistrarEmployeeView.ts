module ums{
    interface IRegistrarEmployeeView extends ng.IScope{
        searchBy: string;
        userId: string;
        userName: string;
        showSearchByUserId: boolean;
        showSearchByUserName: boolean;
        showSearchByDepartment: boolean;
        department: IDepartment;
        departments: Array<IDepartment>;
        getSearchFields: Function;
        getEmployeeInformation: Function;
    }
    class RegistrarEmployeeView{
        public static $inject = ['registrarConstants', '$scope', '$q', 'notify', '$window', '$sce', 'departmentService'];

        constructor(private registrarConstants: any,
                    private $scope: IRegistrarEmployeeView,
                    private $q: ng.IQService,
                    private notify: Notify,
                    private $window: ng.IWindowService,
                    private $sce: ng.ISCEService,
                    private departmentService: DepartmentService){

            $scope.getSearchFields = this.getSearchFields.bind(this);
            $scope.getEmployeeInformation = this.getEmployeeInformation.bind(this);
            Utils.setValidationOptions("form-horizontal");
            console.log("controller is ok");
        }

        private getDepartment(): void{
            this.$scope.departments = Array<IDepartment>();
            this.departmentService.getAll().then((departments: any)=> {
                this.$scope.departments = departments;
                console.log(this.$scope.departments);
            });
        }

        private getSearchFields(): void{
            if(this.$scope.searchBy == "1"){
                this.$scope.showSearchByUserName = false;
                this.$scope.showSearchByDepartment = false;
                this.$scope.showSearchByUserId = true;
            } else if(this.$scope.searchBy == "2"){
                this.$scope.showSearchByUserId = false;
                this.$scope.showSearchByDepartment = false;
                this.$scope.showSearchByUserName = true;
            } else if(this.$scope.searchBy == "3"){
                this.getDepartment();
                this.$scope.showSearchByUserId = false;
                this.$scope.showSearchByUserName = false;
                this.$scope.showSearchByDepartment = true;
            }
        }

        private getEmployeeInformation(): void{
            this.notify.success("Hello");
        }
    }

    UMS.controller("RegistrarEmployeeView", RegistrarEmployeeView);
}