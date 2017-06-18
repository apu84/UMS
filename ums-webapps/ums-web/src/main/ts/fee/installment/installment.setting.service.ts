module ums {
  export interface InstallmentSetting {
    semesterId: number;
    semesterName: string;
    enabled: boolean;
    lastModified: string;
  }

  export class InstallmentSettingService {
    public static $inject = ['$q', 'HttpClient'];

    constructor(private $q: ng.IQService,
                private httpClient: HttpClient) {

    }

    public getInstallmentSetting(semesterId: string): ng.IPromise<InstallmentSetting> {
      let defer: ng.IDeferred<InstallmentSetting> = this.$q.defer();
      this.httpClient.get(`installment-setting/${semesterId}`, HttpClient.MIME_TYPE_JSON,
          (response: InstallmentSetting) => defer.resolve(response));
      return defer.promise;
    }
  }
}
