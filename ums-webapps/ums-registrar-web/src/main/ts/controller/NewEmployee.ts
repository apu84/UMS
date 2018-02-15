module ums {
    import IFormController = ng.IFormController;

    class NewEmployee {

        public static $inject = ['appConstants', 'registrarConstants',
            '$q', 'notify', '$scope', 'HttpClient',
            'departments', 'designations', 'employmentTypes', 'employeeService', 'roles'];

        private allDepartments = [];
        private allDesignations = [];
        private allEmploymentTypes = [];
        private allRoles = [];
        private showRightDiv: boolean = false;

        private newEmployee: INewEmployee;
        private isNotUniqueShortName: boolean;

        constructor(private appConstants: any, private registrarConstants: any, private $q: ng.IQService, private notify: Notify,
                    private $scope: ng.IScope, private httpClient: HttpClient, private departments: any, private designations: any,
                    private employmentTypes: any, private employeeService: EmployeeService, private roles: any) {

            this.newEmployee = <INewEmployee>{};
            this.allDepartments = departments;
            this.allDesignations = designations;
            this.allEmploymentTypes = employmentTypes;
            this.allRoles = roles;
        }

        public submitNewEmployeeForm(form: IFormController): void {
            this.convertToJson().then((result: any) => {
                this.employeeService.save(result).then((message: any) => {
                    console.log(message);
                    this.resetForm(form);
                    this.notify.success(message);
                }).catch((message: any) =>{
                    this.notify.error("Error in new employee creation");
                });
            });
        }

        private resetForm(form: ng.IFormController) {
            this.showRightDiv = false;
            this.newEmployee = <INewEmployee>{};
            form.$setPristine();
        }

        public createId(): void {
            if (this.newEmployee.department && this.newEmployee.employeeType) {
                this.employeeService.getNewEmployeeId(this.newEmployee.department["id"],
                    +this.newEmployee.employeeType).then((result: any) => {
                        this.newEmployee.id = result;
                        this.showRightDiv = true;
                });
            }
            else{
                this.showRightDiv = false;
            }
        }

        public validateShortName(): void{
            this.employeeService.validate(this.newEmployee.shortName).then((result: any) =>{
                this.isNotUniqueShortName = !!result;
            });
        }

        private convertToJson(): ng.IPromise<any> {
            let defer = this.$q.defer();
            let JsonObject = {};
            JsonObject['entries'] = this.newEmployee;
            defer.resolve(JsonObject);
            return defer.promise;
        }
    }

    UMS.controller("NewEmployee", NewEmployee);
}