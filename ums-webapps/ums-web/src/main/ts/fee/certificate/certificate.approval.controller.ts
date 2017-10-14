module ums {

  interface IOptions {
    id: number;
    name: string;
  }

  export class CertificateApprovalController {
    public static $inject = ['CertificateFeeService', 'CertificateStatusService', 'userService', 'appConstants'];
    public certificateStatusList: IOptions[];
    public certificateStatus: IOptions;
    public user: User;

    constructor(private certificateFeeService: CertificateFeeService,
                private certificateStatusService: CertificateStatusService,
                private userService: UserService,
                private appConstants: any) {

      this.certificateStatusList = appConstants.certificateStatus;
      this.certificateStatus = appConstants.certificateStatus[0];

      this.getLoggedUser();
    }

    private getLoggedUser() {
      this.userService.fetchCurrentUserInfo().then((user: User) => {
        this.user = user;
        console.log("Logged user");
        console.log(this.user);
      });
    }

  }

  UMS.controller("CertificateApprovalController", CertificateApprovalController);
}