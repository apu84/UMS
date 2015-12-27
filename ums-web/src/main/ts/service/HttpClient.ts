/// <reference path='../ums.ts' />
/// <reference path='BaseUri.ts' />
/// <reference path='CookieService.ts' />

module ums {
  export class HttpClient {

    private credentials:string;

    public static $inject = [
      '$http',
      'BaseUri',
      'CookieService'
    ];

    constructor(private $http:ng.IHttpService,
                private baseURI:BaseUri,
                private cookieService:CookieService) {
      this.resetAuthenticationHeader();
    }

    public get(url:string,
               contentType:string,
               success:(json:any, etag:string) => void,
               error?:(response:ng.IHttpPromiseCallbackArg<any>) => void):void {
      var promise = this.$http.get(this.baseURI.toAbsolute(url), {
        headers: {
          'Accept': contentType
        }
      });
      promise.then((response:ng.IHttpPromiseCallbackArg<any>) => {
        success(response.data, response.headers('Etag'));
      }, error);
    }


    public post(url:string,
                data:any,
                contentType:string,
                fileName?:string):ng.IHttpPromise<any> {
      var requestHeaders = {
        'Content-Type': contentType
      };

      return this.$http({
        url: this.baseURI.toAbsolute(url),
        method: 'POST',
        data: data,
        headers: requestHeaders
      });
    }

    public put(url:string,
               stream:any,
               contentType:string,
               etag?:string):ng.IHttpPromise<any> {
      return this.$http.put(this.baseURI.toAbsolute(url), stream, {
        headers: {
          'Content-Type': contentType,
          'If-Match': etag == null ? "*" : etag
        }
      });
    }

    public delete(url:string):ng.IHttpPromise<any> {
      return this.$http.delete(this.baseURI.toAbsolute(url));
    }

    public options(url:string):ng.IPromise<string[]> {
      return this.$http({
        method: 'OPTIONS',
        url: this.baseURI.toAbsolute(url)
      });
    }

    public static offline(status:number):boolean {
      return status == 0 || (status >= 502 && status <= 504);
    }

    public resetAuthenticationHeader(authetication?: string) {
      this.credentials = !authetication ? this.cookieService.getCookieByKey(CookieService.CREDENTIAL_KEY) : authetication;
      if (this.credentials != null && this.credentials != '') {
        this.$http.defaults.headers.common.Authorization = this.credentials;
      }
    }
  }

  UMS.service('HttpClient', HttpClient);
}