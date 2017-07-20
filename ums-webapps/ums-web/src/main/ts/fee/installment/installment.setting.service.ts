module ums {
  export interface InstallmentSetting {
    id: string;
    semesterId: string;
    semesterName: string;
    enabled: boolean;
    lastModified: string;
  }

  export interface DateSetting {
    admissionType: string;
    admissionTypeLabel: string;
    entries: InstallmentDateSetting[];
  }

  export interface InstallmentDateSetting {
    typeId: string;
    typeName: string;
    lateFeeType: number;
    lateFee: LateFeeDateSetting[];
    edit: boolean;
  }

  export interface LateFeeDateSetting {
    start: string;
    end: string;
    fee: string;
  }

  export class InstallmentSettingService {
    public static $inject = ['$q', 'HttpClient'];

    constructor(private $q: ng.IQService,
                private httpClient: HttpClient) {

    }

    public getInstallmentSetting(semesterId: string): ng.IPromise<InstallmentSetting> {
      let defer: ng.IDeferred<InstallmentSetting> = this.$q.defer();
      this.httpClient.get(`installment-setting/${semesterId}`, HttpClient.MIME_TYPE_JSON,
          (response: InstallmentSetting) => {
            if (!response.semesterId) {
              response.semesterId = semesterId;
              response.enabled = false;
            }
            defer.resolve(response);
          });
      return defer.promise;
    }

    public getDateSetting(semesterId: string): ng.IPromise<DateSetting[]> {
      let defer: ng.IDeferred<DateSetting[]> = this.$q.defer();
      this.httpClient.get(`installment-setting/${semesterId}/date-setting`, HttpClient.MIME_TYPE_JSON,
          (response: DateSetting[]) => defer.resolve(response));
      return defer.promise;
    }

    public updateInstallmentSetting(instalmentSetting: InstallmentSetting): ng.IPromise<boolean> {
      let defer: ng.IDeferred<boolean> = this.$q.defer();
      if (instalmentSetting.id) {
        this.httpClient.put(`installment-setting/${instalmentSetting.id}`, instalmentSetting, HttpClient.MIME_TYPE_JSON)
            .success(() => {
              defer.resolve(true);
            })
            .error(() => defer.resolve(false));

      }
      else {
        this.httpClient.post(`installment-setting`, instalmentSetting, HttpClient.MIME_TYPE_JSON)
            .success(() => {
              defer.resolve(true);
            })
            .error(() => defer.resolve(false));
      }
      return defer.promise;
    }

    public updateDateSetting(semesterId: string, dateSetting: DateSetting[]): ng.IPromise<boolean> {
      let defer: ng.IDeferred<boolean> = this.$q.defer();
      this.httpClient.post(`installment-setting/${semesterId}/date-setting`, dateSetting, HttpClient.MIME_TYPE_JSON)
          .success(() => {
            defer.resolve(true);
          })
          .error(() => defer.resolve(false));

      return defer.promise;
    }
  }

  UMS.service('InstallmentSettingService', InstallmentSettingService);
}
