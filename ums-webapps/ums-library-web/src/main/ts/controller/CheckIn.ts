module ums{

    export class CheckIn{
        public static $inject = ['$state', '$stateParams', '$scope', '$q', 'notify', 'userService', 'circulationService'];
        private itemId: string;
        private checkInItem: ICheckIn;
        private allCirculation: Array<ILibraryCirculation>;
        private showWarning: boolean;
        private circulation: ILibraryCirculation;

        constructor(private $state: any,
                    private $stateParams: any,
                    private $scope: any,
                    private $q: ng.IQService,
                    private notify: Notify,
                    private userService: UserService,
                    private circulationService: CirculationService){

            this.itemId = $stateParams.itemId;
            this.getCheckedOutItem();
            this.checkInItem = <ICheckIn>{};
            this.allCirculation = [];
            this.circulation = <ILibraryCirculation>{};
        }

        private getCheckedOutItem(): void{
            this.circulation = <ILibraryCirculation>{};
            this.circulationService.getSingleCirculation(this.itemId).then((data: any) => {
               console.log(data.itemCode);
                if(data.itemCode != undefined){
                   this.showWarning = false;
                    console.log("Here");
                    console.log(this.itemId);
                    this.circulation = data;
                    this.checkInItem.itemCode = this.itemId;
               }
               else{
                   this.showWarning = true;
               }
            });
        }

        private submitCheckIn(): void {
            this.checkInItem.itemCode = this.itemId;
            if (this.checkInItem.itemCode != undefined && this.checkInItem.itemCode != null && this.checkInItem.itemCode != "") {
                this.convertToJson('checkInUpdate').then((json: any) => {
                    this.circulationService.updateCirculationForCheckIn(json).then((data: any) => {
                        this.checkInItem.itemCode = "";
                        this.allCirculation.push(this.circulation);
                        this.showWarning = false;
                    });
                });
            }
            else {
                this.notify.error("No item code or barcode found!");
            }
        }

        // private getCirculationCheckIns(): void {
        //     this.allCirculation = [];
        //     this.circulationService.getCirculationCheckInItems('051001').then((data: any) => {
        //         this.allCirculation = data;
        //     });
        // }


        private convertToJson(type: string): ng.IPromise<any> {
            let defer = this.$q.defer();
            let JsonObject = {};
            if (type == 'checkInUpdate') {
                JsonObject['entries'] = this.checkInItem;
            }
            defer.resolve(JsonObject);
            return defer.promise;
        }
    }

    UMS.controller("CheckIn", CheckIn);
}