module ums {
    export class Circulation {
        public static $inject = ['HttpClient', '$scope', '$q', 'notify'];

        private text: string = "Hello World";
        constructor(private httpClient: HttpClient, private $scope: any,
                    private $q: ng.IQService, private notify: Notify) {
        }

        public checkAll(): void{
            console.log("Hey I am Clicked !");
        }


    }

    UMS.controller("Circulation", Circulation);
}