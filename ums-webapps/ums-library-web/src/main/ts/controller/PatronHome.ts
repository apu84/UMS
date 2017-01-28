module ums{
  export class PatronHome{
    public static $inject = ['HttpClient','$scope','$q','notify'];
    constructor(private httpClient: HttpClient, private $scope: any,
                private $q:ng.IQService, private notify: Notify) {
      $scope.courses = [];
      $scope.totalCourses = 0;
      $scope.orderBy = " Order by course_no asc";
      $scope.data={
        coursePerPage : 5
      }; // this should match however many results your API puts on one page
      this.getResultsPage(1);
      $scope.pageChanged = this.pageChanged.bind(this);
      $scope.sort = this.sort.bind(this);

      $scope.pagination = {
        current: 1
      };

      // var that=this;
      // $scope.$watch('orderBy', function() {
      //   console.log("orderBy value changed.....");
      //   this.pageChanged(1);
      // });

    }

    private sort(field: string):any {
      // var that=this;
      return (order:string) => {
        console.log(field, order);

        this.$scope.orderBy=" Order by "+field+" " +order;
        console.log(this);
        this.$scope.pageChanged(1);

      }
    }


    private pageChanged (pageNumber) {
      this.getResultsPage(pageNumber);
    }


    private getResultsPage(pageNumber) {
      this.getCourseData(pageNumber).then((courseData:any)=> {
console.log(courseData.entries);
        this.$scope.courses = courseData.entries;
        this.$scope.totalCourses = 100;
      });
  }

    private getCourseData(pageNumber:string):ng.IPromise<any> {
      var url="https://localhost//ums-webservice-academic/academic/course/all/ipp/"+this.$scope.data.coursePerPage+"/page/"+pageNumber+"/order/"+this.$scope.orderBy;
      var defer = this.$q.defer();
      this.httpClient.get(url, "application/json",
          (json:any, etag:string) => {
            var courseData:any = json;
            defer.resolve(courseData);
          },
          (response:ng.IHttpPromiseCallbackArg<any>) => {
            console.error(response);
          });
      return defer.promise;
    }

  }
  UMS.controller("PatronHome",PatronHome);
}