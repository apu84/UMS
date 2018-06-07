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

            if(this.stateParams.id && this.state.current.name.includes('employeeInformation')){

                // when registrar tries to access all employee profile
                this.tab = 'employeeInformation.employeeProfile';
                this.employeeId = this.stateParams.id;
                this.initialRouting();
            }
            else if(!this.stateParams.id && this.state.current.name.includes('employeeInformation')){

                // when registrar tries to access all employee profile
                this.state.go('employeeInformation');
            }
            else if(!this.stateParams.id && this.state.current.name.includes('employeeProfile')){
                userService.fetchCurrentUserInfo().then((user: any) => {
                    this.tab = 'employeeProfile';
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
            this.verifyEditPermission(tab).then((canEdit: boolean) => {
                this.state.go(this.tab + '.' + tab, {id: this.employeeId, edit: canEdit});
            }).catch((reason) =>{
                this.notify.error(reason);
            });
        }

        private verifyEditPermission(tab: string): ng.IPromise<any>{
            let defer = this.$q.defer();
            if(this.tab === 'employeeInformation.employeeProfile') {
                defer.resolve(true);
            }
            else if(this.tab === 'employeeProfile'){
                if(tab === 'service') {
                    defer.resolve(false);
                }
                else{
                    defer.resolve(true);
                }
            }
            else{
                defer.reject("Can't determine permission. Please contact to IUMS.");
            }
            return defer.promise;
        }
    }

    UMS.controller("EmployeeProfile", EmployeeProfile);
}