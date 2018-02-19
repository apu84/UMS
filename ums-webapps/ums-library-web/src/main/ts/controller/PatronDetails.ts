module ums{

    export class PatronDetails{
        public static $inject = ['$state', '$stateParams', '$q', 'notify', 'userService', 'circulationService'];


        private userId: string;
        private user: UserView;

        constructor(private $state: any,
                    private $stateParams:any,
                    private $q: ng.IQService,
                    private notify: Notify,
                    private userService: UserService,
                    private circulationService: CirculationService) {

            this.userId = $stateParams.patronId;

            this.userService.getUser(this.userId).then((data: any) =>{
               this.user = data;
               console.log(this.user);
            });
        }
    }

    UMS.controller("PatronDetails", PatronDetails);
}