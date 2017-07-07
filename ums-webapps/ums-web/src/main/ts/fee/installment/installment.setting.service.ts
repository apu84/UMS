module ums {
  export interface InstallmentSetting {
    id: string;
    semesterId: number;
    semesterName: string;
    enabled: boolean;
    lastModified: string;
  }

  export interface AdmissionType {
    admissionType: string;
    admissionTypeLabel: string;
    entries: InstallmentDateSetting[];
  }

  export interface InstallmentDateSetting {
    typeId: string;
    typeName: string;
    lateFeeType: number;
    lateFee: LateFeeDateSetting[];
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
          (response: InstallmentSetting) => defer.resolve(response));
      return defer.promise;
    }

    public getDateSetting(semesterId: string): ng.IPromise<AdmissionType[]> {
      let defer: ng.IDeferred<AdmissionType[]> = this.$q.defer();
      this.httpClient.get(`installment-setting/${semesterId}/date-setting`, HttpClient.MIME_TYPE_JSON,
          (response: AdmissionType[]) => defer.resolve(response));
      return defer.promise;
    }
  }

  UMS.service('InstallmentSettingService', InstallmentSettingService);
}
