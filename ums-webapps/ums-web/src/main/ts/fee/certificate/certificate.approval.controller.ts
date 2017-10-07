module ums {

  interface IOptions {
    id: number;
    name: string;
  }

  export class CertificateApprovalController {
    public static $inject = ['CertificateFeeService', 'CertificateStatusService', 'appConstants'];
    public certificateStatusList: IOptions[];

    constructor(private certificateFeeService: CertificateFeeService,
                private certificateStatusService: CertificateStatusService,
                private appConstants: any) {

      this.certificateStatusList = appConstants.certificateStatus;
    }
  }

  UMS.controller("CertificateApprovalController", CertificateApprovalController);
}