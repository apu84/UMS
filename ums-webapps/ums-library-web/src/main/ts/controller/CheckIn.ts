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

            this.checkInItem = <ICheckIn>{};
            this.allCirculation = [];
            this.circulation = <ILibraryCirculation>{};

            if($stateParams.itemId == null || $stateParams.itemId == "" || $stateParams.itemId == undefined){
                this.showWarning = true;
            }
            else {
                this.itemId = $stateParams.itemId;
                this.showWarning = false;
                this.getCheckedOutItem();
            }
        }

        private getCheckedOutItem(): void{
            this.circulation = <ILibraryCirculation>{};
            this.circulationService.getSingleCirculation(this.itemId).then((data: any) => {
                if(data.itemCode != undefined){
                   this.showWarning = false;
                    this.circulation = data;
                    this.checkInItem.itemCode = this.itemId;
                    this.checkInItem.dueDate = data.dueDate;
                    console.log(this.checkInItem.dueDate);
               }
               else{
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

        private convertToJson(): ng.IPromise<any> {
            let defer = this.$q.defer();
            let JsonObject = {};
            JsonObject['entries'] = this.checkInItem;
            defer.resolve(JsonObject);
            return defer.promise;
        }
    }

    UMS.controller("CheckIn", CheckIn);
}