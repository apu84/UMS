module ums {
    class VerifyEmployee {

        public static $inject = [
            'appConstants',
            'registrarConstants',
            '$q',
            'notify',
            '$scope',
            'HttpClient',
            'employeeService'];

        private waitingForApproval : INewEmployee[] = [];

        constructor(private appConstants: any,
                    private registrarConstants: any,
                    private $q: ng.IQService,
                    private notify: Notify,
                    private $scope: ng.IScope,
                    private httpClient: HttpClient,
                    private employeeService: EmployeeService) {

            this.init();
        }

        private init(): void{
            this.employeeService.getEmployeeListWaitingForAccountVerification().then((test:any) => {
                this.waitingForApproval = test;
            });
        }

        public approve(index: number): void{
            this.convertToJson(this.waitingForApproval[index]).then((json: any) => {
                this.employeeService.updateEmployee(json).then((response: any) =>{
                    if(response == "Error"){
                        this.notify.error("Error occured.")
                    }
                    else{
                        this.waitingForApproval.splice(index, 1);
                        this.notify.success("Successful");
                    }
                });
            });
        }

        public decline(index: number): void{
            this.employeeService.deleteEmployee(this.waitingForApproval[index].id).then((response: any) =>{
                this.waitingForApproval.splice(index, 1);
            })
        }

        private convertToJson(employee: INewEmployee): ng.IPromise<any> {
            let defer = this.$q.defer();
            let JsonObject = {};
            JsonObject['entries'] = employee;
            defer.resolve(JsonObject);
            return defer.promise;
        }

    }

    UMS.controller("VerifyEmployee", VerifyEmployee);
}