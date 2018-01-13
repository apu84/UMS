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

        constructor(private $stateParams: any,
                    private $q: ng.IQService,
                    private notify: Notify,
                    private userService: UserService,
                    private circulationService: CirculationService,
                    private catalogingService: CatalogingService) {

            this.userId = $stateParams.patronId;
            this.circulation = <ILibraryCirculation>{};
            this.addDate();
        }


        private getCheckedOutItems(): void {
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
                        this.getCheckedOutItems();
                        flag = 0;
                    });
                });
            }
            else {
                this.notify.error("No item selected");
            }
        }


        private checkoutSubmit(): void {
            if (this.verifyCheckOutSubmitForm()) {
                if (this.circulation.itemCode == this.item.accessionNumber) {
                    if (this.item.circulationStatus == 0) {
                        this.convertToJson('save')
                            .then((json: any) => {
                                this.circulationService.saveCirculation(json)
                                    .then((message: any) => {
                                        if (message == "Error") {
                                        }
                                        else {
                                            this.getCheckedOutItems();
                                            this.circulation.itemCode = "";
                                        }
                                    });
                            });
                    }
                    else {
                        this.notify.error("Item already checkedOut");
                    }
                }
                else {
                    this.notify.error("Wrong Item Code");
                }
            }
            else {
                this.notify.error("Please fill the form properly");
            }
        }

        private verifyCheckOutSubmitForm() {
            return (this.circulation.itemCode != undefined && this.circulation.itemCode != null && this.circulation.itemCode != "")
                && (this.circulation.dueDate != undefined && this.circulation.dueDate != null && this.circulation.dueDate != "");
        }

        private fetchRecord(): void {
            if (this.circulation.itemCode != undefined && this.circulation.itemCode != null && this.circulation.itemCode != "") {
                this.catalogingService.getItem(this.circulation.itemCode).then((data: any) => {
                    this.item = data;
                    if (this.item.mfnNo) {
                        this.showBookInformation = true;
                        this.catalogingService.fetchRecord(this.item.mfnNo).then((data: any) => {
                            this.record = data;
                            console.log(data);
                            this.circulation.totalItems = this.record.totalItems;
                            this.circulation.totalAvailable = this.record.totalAvailable;
                            this.circulation.totalCheckedOut = this.record.totalCheckedOut;
                            this.circulation.totalOnHold =  this.record.totalOnHold;
                        });
                    }
                    else {
                        this.showBookInformation = false;
                    }
                });
            }
            else {
                this.showBookInformation = false;
            }
        }

        private convertToJson(operationType: string): ng.IPromise<any> {
            let defer = this.$q.defer();
            let JsonObject = {};
            if (operationType == 'save') {
                this.circulation.patronId = this.userId;
                this.circulation.mfn = this.item.mfnNo;
                JsonObject['entries'] = this.circulation;
            }
            else if (operationType == 'update') {
                JsonObject['entries'] = this.circulations;
            }
            defer.resolve(JsonObject);
            return defer.promise;
        }

        private addDate(): void {
            let date = new Date();
            let dd = date.getDate();
            let mm = date.getMonth() + 1;
            let yyyy = date.getFullYear();
            let internalThis: any = this;
            internalThis.circulation.dueDate = mm + "/" + dd + "/" + yyyy + ' 05:00 PM';
            $('.datetimepicker-default').datetimepicker({
                format:'DD/MM/YYYY hh:mm A',
                minDate: mm + "/" + dd + "/" + yyyy + ' 05:00 PM'
            }).blur(function (e) {
                 internalThis.circulation.dueDate = $(this).val();
            });
        }
    }

    UMS.controller("PatronCheckOut", PatronCheckOut);
}