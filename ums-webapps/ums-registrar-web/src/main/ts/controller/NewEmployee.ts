module ums {
    class NewEmployee {

        public static $inject = [
            'appConstants',
            'registrarConstants',
            '$q',
            'notify',
            '$scope',
            'HttpClient',
            'departments',
            'designations',
            'employmentTypes',
            'employeeService',
            'roles'];

        private allDepartments = [];
        private allDesignations = [];
        private changedDesignationTypes = [];
        private allEmploymentTypes = [];
        private allEmployeeTypes = [];
        private changedEmployeeTypes = [];
        private academicEmployeeTypes = [];
        private officialEmployeeTypes = [];
        private allRoles = [];
        private showRightDiv: boolean = false;
        private newEmployee: INewEmployee;
        private isNotUniqueShortName: boolean = false;

        constructor(private appConstants: any,
                    private registrarConstants: any,
                    private $q: ng.IQService,
                    private notify: Notify,
                    private $scope: ng.IScope,
                    private httpClient: HttpClient,
                    private departments: any,
                    private designations: any,
                    private employmentTypes: any,
                    private employeeService: EmployeeService,
                    private roles: any) {

            this.newEmployee = <INewEmployee>{};
            this.allEmployeeTypes = appConstants.employeeTypes;
            this.academicEmployeeTypes = appConstants.academicEmployeeTypes;
            this.officialEmployeeTypes = appConstants.officialEmployeeTypes;
            this.allDepartments = departments;
            this.allDesignations = designations;
            this.allEmploymentTypes = employmentTypes;
            this.allRoles = roles;
        }

        public submitNewEmployeeForm(form: ng.IFormController): void {
            if (this.isNotUniqueShortName) {
                this.notify.error("Short name is not unique");
            }
            else {
                this.convertToJson().then((result: any) => {
                    this.employeeService.save(result).then((message: any) => {
                        this.resetForm(form);
                        this.notify.success(message);

                    }).catch((message: any) => {
                        this.notify.error("Error in new employee creation");
                    });
                });
            }
        }

        private resetForm(form: ng.IFormController) {
            this.showRightDiv = false;
            this.newEmployee = <INewEmployee>{};
            form.$setPristine();
        }

        public createId(): void {
            if (this.newEmployee.department && this.newEmployee.employeeType) {
                this.employeeService.getNewEmployeeId(this.newEmployee.department["id"],
                    this.newEmployee.employeeType["id"]).then((result: any) => {
                    this.newEmployee.id = result;
                    this.showRightDiv = true;
                });
            }
            else {
                this.showRightDiv = false;
            }
        }

        public validateShortName(): void {
            this.employeeService.checkDuplicate(this.newEmployee.shortName).then((result: any) => {
                this.isNotUniqueShortName = !!result;
                if (this.isNotUniqueShortName) {
                    this.notify.error("Short name already exists");
                }
            });
        }

        public changeDesignationSelection(): void {
            this.changedDesignationTypes = [];
            this.employeeService.getFilteredDesignation(this.newEmployee.department["id"], this.newEmployee.employeeType["id"]).then((response: any) => {
                if (response.length < 1) {
                    this.notify.error("No designation found");
                }
                else {
                    for (let i = 0; i < response.length; i++) {
                        for (let j = 0; j < this.allDesignations.length; j++) {
                            if (response[i].designationId == this.allDesignations[j].id) {
                                this.changedDesignationTypes.push(this.allDesignations[j]);
                            }
                        }
                    }
                }
            });
        }

        public changeEmployeeTypeSelection(): void {
            this.changedEmployeeTypes = [];
            this.changedEmployeeTypes = this.newEmployee.department["type"] == 1 ? this.academicEmployeeTypes : this.officialEmployeeTypes;
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