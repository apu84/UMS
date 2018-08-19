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
            'roles',
            'additionalService'];

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
        private isNotUniqueAcademicInitial: boolean = false;
        private similarUsers = [];
        private showSimilarUsersPortion: boolean = false;
        private isStaff: boolean = false;

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
                    private roles: any,
                    private additionalService: AdditionalService) {

            this.newEmployee = <INewEmployee>{};
            this.allEmployeeTypes = appConstants.employeeTypes;
            this.academicEmployeeTypes = appConstants.academicEmployeeTypes;
            this.officialEmployeeTypes = appConstants.officialEmployeeTypes;
            this.allDepartments = departments;
            this.allDesignations = designations;
            this.allEmploymentTypes = employmentTypes;
            this.allRoles = roles;
            this.isStaff = false;
        }

        public submitNewEmployeeForm(form: ng.IFormController): void {
            if (this.isNotUniqueAcademicInitial) {
                this.notify.error("Academic initial is not unique");
            }
            else {
                this.newEmployee.status = 1;
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
                    this.newEmployee.IUMSAccount = true;
                });
            }
            else {
                this.showRightDiv = false;
            }
        }

        public validateAcademicInitial(): void {
            this.additionalService.checkDuplicateAcademicInitial(this.newEmployee.academicInitial, this.newEmployee.department['id']).then((result: any) => {
                this.isNotUniqueAcademicInitial = !!result;
                if (this.isNotUniqueAcademicInitial) {
                    this.notify.error("Academic initial already exists");
                }
            });
        }

        public changeDesignationSelection(): void {
            this.changedDesignationTypes = [];
            if (this.newEmployee.department != undefined && this.newEmployee.employeeType != undefined) {
                this.employeeService.getFilteredDesignation(this.newEmployee.department["id"], this.newEmployee.employeeType["id"]).then((response: any) => {
                    if (response.length < 1) {
                        this.notify.error("No designation found");
                    }
                    else {
                        this.newEmployee.employeeType["id"] == 3 ? this.isStaff = true : this.isStaff = false;
                        for (let i = 0; i < response.length; i++) {
                            for (let j = 0; j < this.allDesignations.length; j++) {
                                if (response[i].designationId == this.allDesignations[j].id) {
                                    this.allDesignations[j].roleId = response[i].roleId;
                                    this.changedDesignationTypes.push(this.allDesignations[j]);
                                }
                            }
                        }
                    }
                });
            }
        }

        public changeEmployeeTypeSelection(): void {
            this.changedEmployeeTypes = [];
            this.changedEmployeeTypes = this.newEmployee.department["type"] == 1 ? this.academicEmployeeTypes : this.officialEmployeeTypes;
        }

        public findSimilarUsers(): void {
            this.similarUsers = [];
            this.showSimilarUsersPortion = false;
            if (this.newEmployee.name != undefined) {
                this.employeeService.getSimilarUsers(this.newEmployee.name,).then((data: any) => {
                    this.similarUsers = data;
                    if(data.length > 0) {
                        this.showSimilarUsersPortion = true;
                    }
                });
            }
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