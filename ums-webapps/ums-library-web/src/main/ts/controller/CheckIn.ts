module ums {

    export class CheckIn {
        public static $inject = ['$state', '$stateParams', '$scope', '$q', 'notify', 'userService', 'circulationService', 'catalogingService'];
        private itemId: string;
        private checkInItem: ICheckIn;
        private allCirculation: Array<ILibraryCirculation>;
        private showWarning: boolean;
        private circulation: ILibraryCirculation;
        private record: IRecord;
        private mfn: string;
        private circulationDetail: ILibraryCirculation;

        constructor(private $state: any,
                    private $stateParams: any,
                    private $scope: any,
                    private $q: ng.IQService,
                    private notify: Notify,
                    private userService: UserService,
                    private circulationService: CirculationService,
                    private catalogingService: CatalogingService) {

            this.checkInItem = <ICheckIn>{};
            this.allCirculation = [];
            this.circulation = <ILibraryCirculation>{};
            this.circulationDetail = <ILibraryCirculation>{};

            console.log($stateParams.itemId);
            if ($stateParams.itemId == null || $stateParams.itemId == "" || $stateParams.itemId == undefined) {
                this.showWarning = true;
            }
            else {
                this.itemId = $stateParams.itemId;
                this.mfn = $stateParams.mfn;
                this.showWarning = false;
                this.fetchRecord();
                this.getCirculationItem();
                this.getCheckedOutItem();
                this.addDate();
            }
        }

        private getCirculationItem(): void{
            this.circulationService.getSingleCirculation(this.itemId).then((data: any) => {
                this.circulationDetail = data;
            });
        }

        private getCheckedOutItem(): void {
            this.circulation = <ILibraryCirculation>{};
            this.circulationService.getSingleCirculation(this.itemId).then((data: any) => {
                if (data.itemCode != undefined) {
                    this.showWarning = false;
                    this.circulation = data;
                    this.checkInItem.itemCode = this.itemId;
                    this.checkInItem.dueDate = data.dueDate;
                }
                else {
                    this.showWarning = true;
                }
            });
        }

        private submitCheckIn(): void {
            this.checkInItem.itemCode = this.itemId;
            if ((this.checkInItem.itemCode != undefined && this.checkInItem.itemCode != null && this.checkInItem.itemCode != "") &&
                (this.checkInItem.returnDate != undefined && this.checkInItem.returnDate != null && this.checkInItem.returnDate != "")) {
                this.convertToJson().then((json: any) => {
                    this.circulationService.updateCirculationForCheckIn(json).then((data: any) => {
                        this.checkInItem.itemCode = "";
                        this.allCirculation.push(this.circulation);
                        this.showWarning = true;
                    });
                });
            }
            else {
                this.notify.error("Fill the form properly!");
            }
        }

        private fetchRecord(): void {
            this.catalogingService.fetchRecord(this.mfn).then((data: any) => {
                this.record = <IRecord>{};
                this.record = data;
                console.log(this.record);
            });
        }

        private convertToJson(): ng.IPromise<any> {
            let defer = this.$q.defer();
            let JsonObject = {};
            JsonObject['entries'] = this.checkInItem;
            defer.resolve(JsonObject);
            return defer.promise;
        }

        private addDate(): void {
            let date = new Date();
            let dd = date.getDate();
            let mm = date.getMonth() + 1;
            let yyyy = date.getFullYear();
            let hh = date.getHours();
            let min = date.getMinutes();
            let ampm = hh >= 12 ? 'PM' : 'AM';
            let internalThis: any = this;
            internalThis.checkInItem.returnDate = dd + "/" + mm + "/" + yyyy + " " + hh + ":" + min + " " + ampm;
            $('.datetimepicker-default').datetimepicker({
                format: 'DD/MM/YYYY hh:mm A',
                minDate: dd + "/" + mm + "/" + yyyy + " " + hh + ":" + min + " " + ampm
            }).blur(function (e) {
                internalThis.checkInItem.returnDate = $(this).val();
            });
        }
    }

    UMS.controller("CheckIn", CheckIn);
}