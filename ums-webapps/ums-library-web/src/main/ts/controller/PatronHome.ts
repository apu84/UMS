module ums{
  export class PatronHome{
    public static $inject = ['HttpClient','$scope','$q','notify','$http','myAppFactory'];
    constructor(private httpClient: HttpClient, private $scope: any,
                private $q:ng.IQService, private notify: Notify,private $http:ng.IHttpService,private myAppFactory:any) {

      // $scope.getOrdersData1 = this.getOrdersData1.bind(this);
      $scope.gridOptions = {
        data: [],
        getData: myAppFactory.getOrdersData,
        sort: {
          predicate: 'orderNo',
          direction: 'asc'
        }
      };
      $scope.gridActions = {};

      console.log("llllllssssslll");
   console.log($scope);
      console.log("llllllddddddlll");

    }
    /*
    public getOrdersData1(params:any, callback:any) {
      console.log(params);
      console.log(callback);
console.log(this.$scope);
      this.$http.defaults.headers.common={};
    var herokuDomain = 'https://server-pagination.herokuapp.com';

      this.$http.get(herokuDomain + '/orders').success(function (response:any) {

      callback(response.orders, response.ordersCount);
       console.log(response.orders);
      // that.gridOptions.getData=response.orders;
      // that.gridOptions.data=response.orders;


    });
  }


    public getOrdersData(params, callback):ng.IPromise<any>{
      var herokuDomain = 'https://server-pagination.herokuapp.com';
      var navList:Array<any>=[];
      var defer = this.$q.defer();
      alert("bbc")

      this.httpClient.get(herokuDomain + '/orders',"application/json",

          //https://localhost/ums-webservice-library/mainNavigation
          (json:any,etag:string)=>{
           // navList=json.entries//;[0].items;
            var bbc= {
              getOrdersData: json.orders,
              getStatuses: null
            };
            console.log(bbc);
            defer.resolve(bbc);
          },
          (response:ng.IHttpPromiseCallbackArg<any>)=>{
            console.error(response);
          });
      return defer.promise;
    }

    public getOrdersData(params, callback) {
    this.httpClient.get("https://server-pagination.herokuapp.com" + '/orders' + params,"application/json",

        //https://localhost/ums-webservice-library/mainNavigation
        (json:any,etag:string)=>{
      callback(json.orders, json.ordersCount);
    },
        (response:ng.IHttpPromiseCallbackArg<any>)=>{
          console.error(response);
        });
  }


    public getData():ng.IPromise<any>{
      var navList:Array<any>=[];
      var defer = this.$q.defer();
      this.httpClient.get("https://localhost/ums-webservice-academic/academic/course/semester/11012016/program/110500","application/json",

          //https://localhost/ums-webservice-library/mainNavigation
          (json:any,etag:string)=>{
            navList=json.entries//;[0].items;

            console.log(navList);
            defer.resolve(navList);


          },
          (response:ng.IHttpPromiseCallbackArg<any>)=>{
            console.error(response);
          });
      return defer.promise;
    }
    */
  }
  UMS.controller("PatronHome",PatronHome);
}