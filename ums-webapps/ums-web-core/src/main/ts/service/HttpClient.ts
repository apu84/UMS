/// <reference path='BaseUri.ts' />
module ums {
  export class HttpClient {
    static MIME_TYPE_JSON: string = 'application/json';
    static MIME_TYPE_PDF: string = 'application/pdf';
    static MIME_TYPE_TEXT: string = 'text/html';

    private credentials: Token;
    private location: string;

    public static $inject = [
      '$http',
      'BaseUri',
      '$window'
    ];

    constructor(private $http: ng.IHttpService,
                private baseURI: BaseUri,
                private $window: ng.IWindowService) {
      this.resetAuthenticationHeader();
      this.getCurrentLocation();
    }

    private getCurrentLocation() {
      navigator.geolocation.getCurrentPosition((position) => {
        var currentPosition: any = 'latitude:' + position.coords.latitude + ', longitude:' + position.coords.longitude;
        this.location = currentPosition;
      });
    }

    public get(url: string,
               contentType: string,
               success: (json: any, etag: string) => void,
               error?: (response: ng.IHttpPromiseCallbackArg<any>) => void,
               responseType?: string): void {

      var config: ng.IRequestShortcutConfig = {
        headers: {
          'Accept': contentType,
          'Location': this.location
        }
      };

      if (responseType) {
        config.responseType = responseType;
      }

      var promise = this.$http.get(this.baseURI.toAbsolute(url), config);

      promise.then((response: ng.IHttpPromiseCallbackArg<any>) => {
        success(response.data, response.headers('Etag'));
      }, error);
    }

    public post(url: string,
                data: any,
                contentType: string,
                fileName?: string): ng.IHttpPromise<any> {
      var requestHeaders = {
        'Content-Type': contentType
      };
      if (fileName) {
        requestHeaders['X-ums-media-filename'] = fileName;
      }
      return this.$http({
        url: this.baseURI.toAbsolute(url),
        method: 'POST',
        data: data,
        headers: requestHeaders
      });
    }

    public put(url: string,
               stream: any,
               contentType: string,
               etag?: string): ng.IHttpPromise<any> {
      return this.$http.put(this.baseURI.toAbsolute(url), stream, {
        headers: {
          'Content-Type': contentType,
          'If-Match': etag == null ? "*" : etag,
          'Location': this.location
        }
      });
    }

    public doDelete(url: string): ng.IHttpPromise<any> {
      return this.$http['delete'](this.baseURI.toAbsolute(url));
    }

    public options(url: string): ng.IPromise<any> {
      return this.$http({
        method: 'OPTIONS',
        url: this.baseURI.toAbsolute(url)
      });
    }

    public static offline(status: number): boolean {
      return status == 0 || (status >= 502 && status <= 504);
    }

    public poll(url: string,
                contentType: string,
                success: (json: any, etag: string) => void,
                error?: (response: ng.IHttpPromiseCallbackArg<any>) => void,
                responseType?: string): void {
      var token = this.$http.defaults.headers.common['Authorization'];
      $.ajax(this.baseURI.toAbsolute(url), {
        beforeSend: function (xhr) {
          xhr.setRequestHeader("Authorization", token);
          xhr.setRequestHeader("Accept", contentType);
        },
        error: function (response) {
          if (error) {
            error(response);
          }
        },
        success: function (data) {
          success(data, "");
        },
        type: 'GET'
      });
    }


    public resetAuthenticationHeader() {
      this.credentials = this.$window.sessionStorage.getItem(TOKEN_KEY) ?
          JSON.parse(this.$window.sessionStorage.getItem(TOKEN_KEY)) : null;
      if (this.credentials != null) {
        this.$http.defaults.headers.common['Authorization'] = this.credentials.access_token;
      }
    }
  }

  UMS.service('HttpClient', HttpClient);
}
