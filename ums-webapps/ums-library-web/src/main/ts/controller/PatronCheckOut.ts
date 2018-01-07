module ums {
    export class PatronCheckOut {
        public static $inject = ['$stateParams', '$q', 'notify', 'userService', 'circulationService', 'catalogingService'];

        private userId: string;
        private circulation: ILibraryCirculation;
        private circulations: Array<ILibraryCirculation>;
        private showCheckedOutItems: boolean = false;
        private record: IRecord;
        private item: IItem;
        private showBookInformation: boolean;

        constructor(private $stateParams:any,
                    private $q: ng.IQService,
                    private notify: Notify,
                    private userService: UserService,
                    private circulationService: CirculationService,
                    private catalogingService: CatalogingService) {

            this.userId = $stateParams.patronId;
            console.log(this.userId);
            this.circulation = <ILibraryCirculation>{};
        }


        private getCirculation(): void {
            this.showCheckedOutItems = true;
            this.circulations = [];
            this.circulationService.getCirculation(this.userId).then((data: any) => {
                this.circulations = data;
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


        private checkoutSubmit(): void {
            if ((this.circulation.itemCode != undefined && this.circulation.itemCode != null && this.circulation.itemCode != "")
                && (this.circulation.dueDate != undefined && this.circulation.dueDate != null && this.circulation.dueDate != "")) {
                if(this.circulation.itemCode == this.item.accessionNumber) {
                    if(this.item.circulationStatus == 0) {
                        this.convertToJson('save')
                            .then((json: any) => {
                                this.circulationService.saveCirculation(json)
                                    .then((message: any) => {
                                        if (message == "Error") {
                                        }
                                        else {
                                            this.getCirculation();
                                            this.showCheckedOutItems = true;
                                            this.circulation.itemCode = "";
                                            this.fetchRecord();
                                        }
                                    });
                            });
                    }
                    else{
                        this.notify.error("Item already checkedOut");
                    }
                }
                else{
                    this.notify.error("Wrong Item Code");
                }

            }
            else {
                this.notify.error("Please fill the form properly");
            }
        }

        private fetchRecord(): void{
            if(this.circulation.itemCode != undefined && this.circulation.itemCode != null && this.circulation.itemCode != "") {
                this.catalogingService.getItem(this.circulation.itemCode).then((data: any) => {
                    console.log(data);
                    this.item = data;

                    console.log(this.item);
                    if(this.item.mfnNo) {
                        this.showBookInformation = true;
                        this.catalogingService.fetchRecord(this.item.mfnNo).then((data: any) => {
                            this.record = data;
                        });
                    }
                    else {
                        console.log("here in else");
                        this.showBookInformation = false;
                    }
                });
            }
            else{
                this.showBookInformation = false;
            }
        }

        private convertToJson(type: string): ng.IPromise<any> {
            let defer = this.$q.defer();
            let JsonObject = {};
            if (type == 'save') {
                this.circulation.patronId = this.userId;
                this.circulation.mfn = this.item.mfnNo;
                JsonObject['entries'] = this.circulation;
            }
            else if (type == 'update') {
                JsonObject['entries'] = this.circulations;
            }
            defer.resolve(JsonObject);
            return defer.promise;
        }
    }

    UMS.controller("PatronCheckOut", PatronCheckOut);
}