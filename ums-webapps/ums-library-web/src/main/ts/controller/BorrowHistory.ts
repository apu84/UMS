module ums {

    export class BorrowHistory {
        public static $inject = ['$state', '$stateParams', '$q', 'notify', 'userService', 'circulationService'];

        private userId: string;
        private allCirculation: Array<ILibraryCirculation>;

        constructor(private $state: any,
                    private $stateParams: any,
                    private $q: ng.IQService,
                    private notify: Notify,
                    private userService: UserService,
                    private circulationService: CirculationService) {

            userService.fetchCurrentUserInfo().then((result: any) =>{
                if(result.roleId == 11){
                    this.userId = result.id;
                }
                else{
                    this.userId = result.employeeId;
                }
            }).then(() =>{
                this.getAllCirculations();
            });

        }

        private getAllCirculations(): void {
            this.allCirculation = [];
            this.circulationService.getAllCirculation(this.userId).then((data: any) => {
                this.allCirculation = data;
            });
        }
    }

    UMS.controller("BorrowHistory", BorrowHistory);
}