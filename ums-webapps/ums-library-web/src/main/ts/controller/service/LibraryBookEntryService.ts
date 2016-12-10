module ums{

    export class LibraryBookEntryService{

        private Url:string='/ums-webservice-library/library';
        public static $inject = ['HttpClient','$q','notify'];
        constructor( private httpClient: HttpClient,
                    private $q:ng.IQService,
                     private notify: Notify) {

        }

        public saveBook(json:any):ng.IPromise<any>{
            var defer=this.$q.defer();
            this.httpClient.post(this.Url,json,'application/json')
                .success(()=>{
                    defer.resolve("success");
                }).error((data)=>{
                console.log(data);
                defer.resolve("error");
            });

            console.log("I am in LibraryService");
            return defer.promise;
        }
    }
    UMS.service("LibraryBookEntryService",LibraryBookEntryService);
}