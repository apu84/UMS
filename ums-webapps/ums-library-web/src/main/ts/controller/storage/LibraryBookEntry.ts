/**
 * Created by kawsu on 11/30/2016.
 */

module ums {
  interface ILibraryBookEntry extends ng.IScope {
    showTable: boolean;
    author: string;
    book: string;
    save: Function;
  }

  export class LibraryBookEntry {
    public static $inject = ['$scope', '$q', 'notify', 'LibraryBookEntryService'];

    constructor(private $scope: ILibraryBookEntry,
                private $q: ng.IQService, private notify: Notify,
                private libraryBookEntryService: LibraryBookEntryService) {

      $scope.showTable = false;
      $scope.save = this.save.bind(this);
    }

    private save(): void {
      this.convertToJson().then((json: any)=> {
        this.libraryBookEntryService.saveBook(json).then((message: any)=> {
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
      return defer.promise;
    }
  }

  UMS.controller('LibraryBookEntry', LibraryBookEntry);
}