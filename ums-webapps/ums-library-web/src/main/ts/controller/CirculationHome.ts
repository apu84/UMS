module ums {
    interface ICirculationHome extends ng.IScope {
        checkoutSubmit: Function;
    }

    export class CirculationHome {
        public static $inject = ['$state', '$scope', '$q', 'notify', 'userService', 'circulationService'];

        private searchValue: string;
        private user: UserView;
        private circulation: ILibraryCirculation;
        private circulations: Array<ILibraryCirculation>;
        private allCirculation: Array<ILibraryCirculation>;
        private checkInItem: ICheckIn;
        private circulationType: string;
        private state: any;

        constructor(private $state: any,
                    private $scope: any,
                    private $q: ng.IQService,
                    private notify: Notify,
                    private userService: UserService,
                    private circulationService: CirculationService) {

            $scope.checkoutSubmit = this.checkoutSubmit.bind(this);
            // this.addDate();
            console.log("In Home");
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
            });
        }

        private checkIn(itemId: string): void {
            this.state.go('circulation.checkIn', {itemId: itemId});
        }



        private searchPatron(patronId: string): void {
            this.userService.getUser(patronId).then((data: any) => {
                if(data.userId != undefined){
                    this.state.go('circulation.searchPatron', {patronId: data.userId});
                }
            });
        }

        private checkoutSubmit(): void {
            if (this.validateUser()) {
                if ((this.circulation.mfn != undefined && this.circulation.mfn != null && this.circulation.mfn != "")
                    && (this.circulation.dueDate != undefined && this.circulation.dueDate != null && this.circulation.dueDate != "")) {
                    this.convertToJson('save')
                        .then((json: any) => {
                            this.circulationService.saveCirculation(json)
                                .then((message: any) => {
                                    if (message == "Error") {
                                    }
                                    else {
                                        this.getCirculation();
                                        this.circulation.mfn = "";
                                    }
                                });
                        });
                }
                else {
                    this.notify.error("Please fill the form properly");
                }
            }
            else {
                this.notify.error("No user available! Search Patron Again.");
            }
        }

        private getCirculation(): void {
            if (this.validateUser()) {
                this.circulationService.getCirculation(this.user.userId).then((data: any) => {
                    this.circulations = data;
                });
            }

        }

        private getAllCirculations(): void {
            if (this.validateUser()) {
                this.allCirculation = [];
                this.circulationService.getAllCirculation(this.user.userId).then((data: any) => {
                    this.allCirculation = data;
                });
            }
        }

        private getCirculationCheckIns(patronId: string): void {
            this.allCirculation = [];
            this.circulationService.getCirculationCheckInItems(patronId).then((data: any) => {
                this.allCirculation = data;
            });
        }

        private checkInSelectedItem(): void {
            let flag = 0;
            for (let i = 0; i < this.circulations.length; i++) {
                if (this.circulations[i].checkBoxStatus == true) {
                    flag = 1;
                    break;
                }
            }
            if (flag == 1) {
                this.convertToJson('update').then((json: any) => {
                    this.circulationService.updateCirculation(json).then((data: any) => {
                        this.getCirculation();
                        flag = 0;
                    });
                });
            }
            else {
                this.notify.error("No item selected");
            }
        }

        private validateUser(): boolean {
            return this.user.userId != undefined && this.user.userId != null;
        }

        private convertToJson(type: string): ng.IPromise<any> {
            let defer = this.$q.defer();
            let JsonObject = {};
            if (type == 'save') {
                this.circulation.patronId = this.user.userId;
                JsonObject['entries'] = this.circulation;
            }
            else if (type == 'update') {
                JsonObject['entries'] = this.circulations;
            }
            else if (type == 'checkInUpdate') {
                JsonObject['entries'] = this.checkInItem;
            }
            defer.resolve(JsonObject);
            return defer.promise;
        }

        // private addDate(): void {
        //     let internalThis: any = this;
        //     setTimeout(function () {
        //         $('#datetimepicker-default').datetimepicker();
        //         $('#datetimepicker-default').blur(function (e) {
        //             internalThis.circulation.dueDate = $(this).val();
        //             console.log(internalThis.circulation.dueDate );
        //         });
        //     }, 10);
        // }

    }

    UMS.controller("CirculationHome", CirculationHome);
}