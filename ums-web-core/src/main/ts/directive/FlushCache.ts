module ums {
  export class FlushCache implements ng.IDirective {

    constructor(private httpClient: HttpClient) {

    }

    public restrict: string = 'A';
    public link = ($scope: any, element: JQuery, attributes: any) => {
      element.click(() => {
        this.httpClient.post('flushCache', {}, 'application/json')
            .success((data) => {
            }).error((data) => {
              console.error(data);
            });
      });
    };
  }
  UMS.directive("flushCache", ['HttpClient', (httpClient) => {
    return new FlushCache(httpClient);
  }]);
}