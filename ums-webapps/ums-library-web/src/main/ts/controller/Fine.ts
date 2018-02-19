module ums{

    export class Fine{
        public static $inject = ['$state', '$stateParams', '$q', 'notify', 'userService', 'circulationService'];

        private userId: string;
        private fines: Array<IFine>;
        private updateFines: Array<IFine>;

        constructor(private $state: any,
                    private $stateParams:any,
                    private $q: ng.IQService,
                    private notify: Notify,
                    private userService: UserService,
                    private circulationService: CirculationService) {

            this.userId = $stateParams.patronId;
            this.getFines();

        }

        private getFines(): void{
            this.circulationService.getFine(this.userId).then((data: any) =>{
                this.fines = data;
                console.log(this.fines);
            });
        }

        private forgiveFines(): void{
            console.log("hello world");
            this.getForgivenFineList().then((data: any) => {
                if(data.length > 0) {
                    this.convertToJson().then((json) => {
                        this.circulationService.updateFines(json).then(() => {
                            this.getFines();
                        });
                    })
                }
                else{
                    this.notify.error("Nothing Selected");
                }
            });
        }

        private getForgivenFineList(): ng.IPromise<any>{
            var defer = this.$q.defer();
            this.updateFines = [];
            for(var i = 0; i < this.fines.length; i++){
                console.log(this.fines[i]);
                if(this.fines[i].checkBoxStatus) {
                    this.updateFines.push(this.fines[i]);
                }
            }
            defer.resolve(this.updateFines);
            return defer.promise;
        }

        private convertToJson(): ng.IPromise<any>{
            let defer = this.$q.defer();
            let JsonObject = {};
            console.log(this.updateFines);
            JsonObject['entries'] = this.updateFines;
            defer.resolve(JsonObject);
            return defer.promise;
        }

    }

    UMS.controller("Fine", Fine);
}