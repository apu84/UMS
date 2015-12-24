module ums {
  export class BaseUri {

    baseURI:URI;

    constructor() {
      this.baseURI = new URI().pathname(this.getServicePath()).username(null).password(null).search(null).fragment(null);
    }

    public getServicePath() {
      return '/ums-webservice-common/';
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
  }

  UMS.service('BaseUri', BaseUri);
}