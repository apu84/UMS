module ums {
  export class Settings {
    public static $inject = ['HttpClient', '$q'];
    private _settings: {[key: string]: any};

    constructor(private httpClient: HttpClient,
                private $q: ng.IQService) {
      this._settings = {};
    }

    public get settings(): ng.IPromise<{[key: string]: any}> {
      if (UmsUtil.isEmpty(this._settings)) {
        var defer = this.$q.defer();
        this.httpClient.get("settings", HttpClient.MIME_TYPE_JSON,
            (response: {[key: string]: any}) => {
              this._settings = response;
              defer.resolve(this._settings);
            });
        return defer.promise;
      } else {
        return this.$q.when(this._settings);
      }
    }
  }

  UMS.service("Settings", Settings);
}