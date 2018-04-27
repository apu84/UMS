module ums {
    class EmployeeProfile {
        public static $inject = ['$q', 'notify', 'userService', '$state', '$stateParams'];

        private state: any;
        private employeeId: string;

        constructor(private $q: ng.IQService,
                    private notify: Notify,
                    private userService: UserService,
                    private $state : any,
                    private $stateParams: any) {


            console.log("In Employee Profile------<---->>---");

            this.state = $state;

            console.log("this.state");
            console.log(this.state);

            console.log("id", this.state.id);

            if(this.state.id == undefined || this.state.id == null || this.state.id == ""){
                console.log("Printing From here");
                userService.fetchCurrentUserInfo().then((user: any) => {
                    this.employeeId = user.employeeId;
                    this.state.go('employeeProfile.personal', {id1: this.employeeId});
                });
            }
            else {
                console.log("Printing From there");
                this.employeeId = this.state.id;
                this.state.go('employeeInformation.employeeProfile.personal', {id1: this.employeeId });
            }
        }
    }
    UMS.controller("EmployeeProfile", EmployeeProfile);
}