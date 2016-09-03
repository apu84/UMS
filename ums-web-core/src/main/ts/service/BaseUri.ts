module ums {
  export interface IBaseUri {
    toRelative(url: string): string;
    toAbsolute(url: string): string;
    getBaseURI(): URI;
  }

  export class BaseUri implements IBaseUri {
    baseURI:URI;

    constructor(private servicePath: string) {
      this.baseURI = new URI().pathname(this.getServicePath()).username(null).password(null).search(null).fragment(null);
    }

    public toRelative(url:string):string {
      if (!url) {
        return null;
      }
      var uri = new URI(url);
      if (!uri.is('relative')) {
        uri = uri.username(null).password(null).relativeTo(this.baseURI)
      }
      return uri.toString();
    }

    public toAbsolute(url:string):string {
      if (!url) {
        return null;
      }
      var uri = new URI(url);
      if (!uri.is('absolute')) {
        uri = uri.username(null).password(null).absoluteTo(this.baseURI)
      }
      return uri.toString();
    }

    public getBaseURI():URI{
      return this.baseURI;
    }


    public getServicePath(): string {
      return this.servicePath;
    }
  }

  export class BaseUriProvider implements ng.IServiceProvider {
    private servicePath = "/";

    public setServicePath(path: string): void {
      this.servicePath = path;
    }

    public $get() {
      return new BaseUri(this.servicePath);
    }
  }

  UMS.provider('BaseUri', BaseUriProvider);
}