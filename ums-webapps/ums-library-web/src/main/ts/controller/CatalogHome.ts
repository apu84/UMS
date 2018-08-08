module ums{
    export class CatalogHome{
        public static $inject = [
            '$state',
            '$stateParams'
        ];

        private state: any;
        private stateParams: any;

        constructor(private $state : any,
                    private $stateParams: any){

            this.state = $state;
            this.stateParams = $stateParams;
        }
    }

    UMS.controller("CatalogHome", CatalogHome);
}