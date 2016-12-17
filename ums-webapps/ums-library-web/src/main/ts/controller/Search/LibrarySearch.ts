/**
 * Created by kawsu on 12/10/2016.
 */

module ums {
    interface ILibrarySearch extends ng.IScope {
        showTable: boolean;
        book : any;
        search : Function;
        foundbooks : Array<ILibraryBook>;
    }

    interface ILibraryBook{
        bookName: string;
        authorName : string;
    }

    export class LibrarySearch {
        public static $inject = ['$scope', '$q', 'notify', 'LibrarySearchService'];

        constructor(private $scope: ILibrarySearch,
                    private $q: ng.IQService, private notify: Notify,
                    private librarySearchService: LibrarySearchService) {

            $scope.showTable = false;
            $scope.search = this.search.bind(this);

            console.log("This is constructor");
        }

        private search(): void {

            this.$scope.showTable = true;
            console.log(this.$scope.book);
            this.librarySearchService.getDesiredBook(this.$scope.book)
                .then((outputs: Array<ILibraryBook>) => {
                    if (outputs == null) {
                        this.$scope.showTable = false;
                        this.notify.error("No Data Found");
                    }
                    else {
                        this.$scope.foundbooks = outputs;
                    }
                    console.log(outputs);

                });
        }

    }

    UMS.controller('LibrarySearch', LibrarySearch);
}