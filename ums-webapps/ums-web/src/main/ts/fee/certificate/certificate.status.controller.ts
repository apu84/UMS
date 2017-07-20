module ums {
  export class CertificateStatusController {
    public static $inject = ['$scope', 'CertificateStatusService', '$modal', 'notify'];
    public certificateStatusList: CertificateStatus[];
    public filters: Filter[];
    public selectedFilters: SelectedFilter[] = [];
    public reloadReference: ReloadRef = {reloadList: false};
    public nextLink: string;
    public selectedCertificates: {[id: string]: boolean} = {};
    public selectedCertificateIds: string[];
    public selected: CertificateStatus[];

    constructor($scope: ng.IScope, private certificateStatusService: CertificateStatusService, private $modal: any,
                private notify: Notify) {
      this.navigate();
      this.certificateStatusService.getFilters().then((filters: Filter[]) => {
        this.filters = filters;

        $scope.$watch(() => this.selectedFilters, ()=> {
          this.navigate();
        }, true);
      });

      $scope.$watch(()=> {
        return this.selectedCertificates;
      }, () => {
        if (this.selectedCertificates) {
          this.selectedCertificateIds = Object.keys(this.selectedCertificates)
              .map(
                  (key) => {
                    return this.selectedCertificates[key] ? key : undefined;
                  })
              .filter((el)=> !!el);
        }
      }, true);

      $scope.$watch(() => this.reloadReference, (newVal: ReloadRef) => {
        if (newVal.reloadList) {
          this.navigate();
        }
      }, true);
    }

    public navigate(url?: string): void {
      this.certificateStatusService.listCertificateStatus(this.selectedFilters, url)
          .then((response: CertificateStatusResponse) => {
            if (url && this.certificateStatusList && this.certificateStatusList.length > 0) {
              this.certificateStatusList.push.apply(response.entries);
            }
            else {
              this.certificateStatusList = response.entries;
            }
            this.nextLink = response.next;
            this.reloadReference.reloadList = false;
          });
    }

    public process(): void {
      this.selected
          = this.certificateStatusList.filter((certificate: CertificateStatus)=> {
        return this.selectedCertificateIds.indexOf(certificate.id) >= 0;
      });

      this.certificateStatusService.processCertificates(this.selected)
          .then((response) => {
            if (response) {
              this.navigate();
            }
            else {
              this.notify.error('Failed to process');
            }
          });
    }
  }

  UMS.controller('CertificateStatusController', CertificateStatusController);
}
