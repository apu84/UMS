module ums {
    export class CirculationHome {
        public static $inject = ['$state', '$scope', '$q', 'notify', 'userService', 'circulationService'];

        private searchValue: string;
        private user: UserView;
        private circulation: ILibraryCirculation;
        private state: any;

        constructor(private $state: any,
                    private $scope: any,
                    private $q: ng.IQService,
                    private notify: Notify,
                    private userService: UserService,
                    private circulationService: CirculationService) {

            this.state = $state;
        }

        private doSearch(circulationType: string): void {
            if (circulationType == 'checkOut') {
                this.checkout(this.searchValue);
            }
            else if (circulationType == 'checkIn') {
                this.checkIn(this.searchValue);
            }
            else if (circulationType == 'searchPatron') {
                this.searchPatron(this.searchValue);
            }
            else {
                this.notify.error("Something went wrong!");
            }
        }

        private checkout(patronId: string): void {
            this.userService.getUser(patronId).then((data: any) => {
                this.user = data;
                if(this.user.userId != undefined){
                    this.state.go('circulation.checkOut', {userId: this.user.userId});
                }
            }).catch((data: any) => {
               this.notify.error(data);
            })
        }

        private checkIn(itemId: string): void {
            if(itemId != undefined && itemId != null && itemId != "") {
                this.circulationService.getSingleCirculation(itemId).then((data: any) => {
                    if (data.itemCode) {
                        this.state.go('circulation.checkIn', {itemId: itemId});
                    }
                    else{
                        this.state.go('circulation.checkIn', {itemId: null});
                    }
                });
            }
            else{
                this.notify.error("Check Item Code or Barcode");
            }
        }

        private searchPatron(patronId: string): void {
            this.userService.getUser(patronId).then((data: any) => {
                if(data.userId != undefined){
                    this.state.go('circulation.searchPatron', {patronId: data.userId});
                }
            });
        }

    }

    UMS.controller("CirculationHome", CirculationHome);
}