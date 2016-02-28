module ums {
  export class TrustAsHtml {

    static $inject: string[] = ['$sce'];

    static filter($sce: ng.ISCEService) {

      return (value)=> {
        return $sce.trustAsHtml(value);
      };
    }
  }

  UMS.filter('trustAsHtml', TrustAsHtml.filter);
}