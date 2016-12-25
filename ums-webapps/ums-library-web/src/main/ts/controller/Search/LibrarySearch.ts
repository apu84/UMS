module ums {
    interface ILibrarySearch extends ng.IScope {
        showTable: boolean;
        book : any;
        search : Function;
        remove: Function;
        msg_book : any;
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

            console.log("Hello I am Constructor");
            $scope.showTable = false;
            $scope.search = this.search.bind(this);
            $scope.remove = this.remove.bind(this);
        }

        private search(): void {
            console.log("My Name is Search()");
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

        private getAll(): void {
            this.$scope.showTable = true;
            this.librarySearchService.getAllBooks()
                .then((outputs: Array<ILibraryBook>)=> {
                   if(outputs == null){
                       this.$scope.showTable = false;
                       this.notify.error("No Data Available");
                   }
                   else {
                       this.$scope.foundbooks = outputs;
                   }
                });
        }

        private remove(bookName: string, authorName: string): void {
            console.log("Hello remove() "+ bookName + "  " + authorName);
            this.librarySearchService.removeBooks(bookName, authorName);
            this.search();
        }


    }

    UMS.controller('LibrarySearch', LibrarySearch);
}
