module ums {
    export interface INewEmployee {
        id: string;
        firstName: string;
        lastName: string;
        department: object;
        designation: object;
        employmentType: object;
        joiningDate: string;
        status: object;
        shortName: string;
        email: string;
        employeeType: object;
        role: object;
        IUMSAccount: boolean;
    }

    class NewEmployee {

        public static $inject = ['appConstants', 'registrarConstants',
            '$q', 'notify', '$scope', 'HttpClient',
            'departments', 'designations', 'employmentTypes', 'employeeService'];

        private allDepartments = [];
        private allDesignations = [];
        private allEmploymentTypes = [];
        private showRightDiv: boolean = false;

        private newEmployee: INewEmployee;

        constructor(private appConstants: any, private registrarConstants: any, private $q: ng.IQService, private notify: Notify,
                    private $scope: ng.IScope, private httpClient: HttpClient, private departments: any, private designations: any,
                    private employmentTypes: any, private employeeService: EmployeeService) {

            this.newEmployee = <INewEmployee>{};
            this.allDepartments = departments;
            this.allDesignations = designations;
            this.allEmploymentTypes = employmentTypes;
        }

        public submitNewEmployeeForm(): void {
            this.convertToJson().then((result: any) => {
                console.log(result);
                this.employeeService.save(result).then((message: any) => {
                    this.notify.success("Successfully created");
                }).catch((message: any) =>{
                    this.notify.error("Error in creating new employee");
                });
            });
        }

        public createId(): void {
            if (this.newEmployee.department && this.newEmployee.employeeType) {
                this.employeeService.getNewEmployeeId(this.newEmployee.department["id"],
                    +this.newEmployee.employeeType).then((result: any) => {
                        console.log(result);
                        this.newEmployee.id = result;
                        this.showRightDiv = true;
                });
            }
            else{
                this.showRightDiv = false;
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