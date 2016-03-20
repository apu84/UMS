/// <reference path='../ums.ts' />
/// <reference path='BaseUri.ts' />
/// <reference path='CookieService.ts' />

module ums {
  export class HttpClient {
    static MIME_TYPE_JSON: string = 'application/json';
    static MIME_TYPE_PDF: string = 'application/pdf';

    private credentials:any;

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
               error?:(response:ng.IHttpPromiseCallbackArg<any>) => void,
               responseType?: string):void {

      var config: ng.IRequestShortcutConfig = {
        headers: {
          'Accept': contentType
        }
      };

      if(responseType) {
        config.responseType = responseType;
      }

      var promise = this.$http.get(this.baseURI.toAbsolute(url), config);

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
      if(fileName) {
        requestHeaders['X-ums-media-filename'] = fileName;
      }
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

    public resetAuthenticationHeader() {
      this.credentials = this.cookieService.getCookieAsJson(CookieService.CREDENTIAL_KEY);
      if (this.credentials != null && this.credentials != '') {
        this.$http.defaults.headers.common['X-Authorization'] = this.credentials.credential;
      }
    }
  }

  UMS.service('HttpClient', HttpClient);
}