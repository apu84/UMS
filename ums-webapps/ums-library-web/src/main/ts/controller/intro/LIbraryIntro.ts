
/**
 * Created by kawsu on 11/30/2016.
 */

module ums {
    interface ILibraryIntro extends ng.IScope {
        showTable: boolean;
        author:string;
        book:string;
        save:Function;
    }

    export class LibraryIntro {
        public static $inject = ['$scope', '$q', 'notify',  'LibraryService'];

        constructor(private $scope: ILibraryIntro,
                    private $q: ng.IQService, private notify: Notify,
                     private libraryService: LibraryService) {

            $scope.showTable = false;
            $scope.save = this.save.bind(this);

            console.log("This is constructor");
        }

        private save() : void {
            console.log("In the save method");
            this.convertToJson().then((json: any)=> {
                this.libraryService.saveBook(json).then((message: any)=> {
                    this.notify.success(message);

                });
            });

            this.$scope.showTable = true;
        }

        private convertToJson(): ng.IPromise<any> {


            var defer = this.$q.defer();
            var completeJson = {};
            var jsonObject = [];


            var item: any = {};
            item['book'] = this.$scope.book;
            item['author'] = this.$scope.author;
            jsonObject.push(item);


            completeJson["entries"] = jsonObject;

            defer.resolve(completeJson);
            console.log("In the converTOJSON method");
            return defer.promise;
        }
    }

    UMS.controller('LibraryIntro', LibraryIntro);
}