module ums{
    export interface INewEmployee{
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

    class NewEmployee{

        public static $inject = ['appConstants', 'registrarConstants',
            '$q', 'notify', '$scope', 'HttpClient',
            'departments', 'designations', 'employmentTypes', 'employeeService'];

        private allDepartments = [];
        private allDesignations = [];
        private allEmploymentTypes = [];

        private newEmployee: INewEmployee;

        constructor(private appConstants: any, private registrarConstants: any, private $q: ng.IQService, private notify: Notify,
                    private $scope: ng.IScope, private httpClient: HttpClient, private departments: any, private designations: any,
                    private employmentTypes: any, private employeeService: EmployeeService){

            this.newEmployee = <INewEmployee>{};
            this.allDepartments = departments;
            this.allDesignations = designations;
            this.allEmploymentTypes = employmentTypes;
        }

        public submitNewEmployeeForm(): void{
            this.convertToJson().then((result: any) => {
                console.log(result);
                this.employeeService.save(result).then((message: any) => {
                    console.log(message);
                });
            });
        }

        public createId(): void{
            if (this.newEmployee.department && this.newEmployee.employeeType){
                console.log(this.newEmployee.department);
                console.log(this.newEmployee.employeeType);
            }
        }

        private convertToJson() : ng.IPromise<any>{
            let defer = this.$q.defer();
            let JsonObject = {};
            JsonObject['entries'] = this.newEmployee;
            defer.resolve(JsonObject);
            return defer.promise;
        }
    }

    UMS.controller("NewEmployee", NewEmployee);
}