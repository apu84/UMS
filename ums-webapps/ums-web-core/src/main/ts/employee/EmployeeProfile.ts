module ums {
    class EmployeeProfile {
        public static $inject = ['$q', 'notify', '$state', '$stateParams', 'userService'];

        private state: any;
        private stateParams: any;
        private employeeId: string;
        private tab: string;
        private canEdit: boolean = false;

        constructor(private $q: ng.IQService,
                    private notify: Notify,
                    private $state : any,
                    private $stateParams: any,
                    private userService: UserService) {


            this.state = $state;
            this.stateParams = $stateParams;

            if(this.stateParams.id){

                // when registrar tries to access all employee profile
                this.tab = 'employeeInformation.employeeProfile';
                this.employeeId = this.stateParams.id;
            }
            else if(!this.stateParams.id && this.state.current.name.includes('employeeProfile')){
                userService.fetchCurrentUserInfo().then((user: any) => {
                    this.tab = 'employeeProfile';
                    this.canEdit = true;
                    this.employeeId = user.employeeId;
                    this.initialRouting();
                });
            }
            else{
                this.notify.error("Can't determine the state. Please contact to IUMS for help");
            }
        }

        private initialRouting() : void{
            if (this.state.current.url === '/employeeProfile') {
                this.redirectTo('personal');
            }
            else {
                this.redirectTo(this.state.current.url.split('/')[1]);
            }
        }

        public redirectTo(tab: string): void{
            this.state.go(this.tab + '.' + tab, {id: this.employeeId, edit: (this.tab === 'employeeProfile' && tab === 'service') ? false : this.canEdit});
        }
    }

    UMS.controller("EmployeeProfile", EmployeeProfile);
}