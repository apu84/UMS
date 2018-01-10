module ums {

  class VoucherNumberControlController {
    public static $inject = ['$scope', '$modal', 'notify', 'VoucherService', '$timeout', 'VoucherNumberControlService'];

    private voucherNumberControls: IVoucherNumberControl[];
    private voucherNumberControlsCopy: IVoucherNumberControl[];
    private disable: boolean;

    constructor($scope: ng.IScope,
                private $modal: any,
                private notify: Notify,
                private voucherService: VoucherService,
                private $timeout: ng.ITimeoutService,
                private voucherNumberControlService: VoucherNumberControlService) {

      this.initialize();

    }

    public initialize() {
      this.voucherNumberControlService.getAllByCurrentFinancialYear().then((voucherNumberControlList: IVoucherNumberControl[]) => {
        this.voucherNumberControls = [];
        voucherNumberControlList.length === 0 ? this.getAllVouchers() : this.voucherNumberControls = voucherNumberControlList;
        if (voucherNumberControlList.length > 0)
          this.disable = true;
      });
    }

    public getAllVouchers() {
      this.voucherService.getAllVoucher().then((vouchers: IVoucher[]) => {
        this.voucherNumberControls = [];
        vouchers.forEach((v: IVoucher) => {
          let voucherNumberControl: IVoucherNumberControl = <IVoucherNumberControl>{};
          voucherNumberControl.resetBasis = ResetBasis.Yearly;
          voucherNumberControl.voucherId = v.id;
          voucherNumberControl.voucher = v;
          voucherNumberControl.startVoucherNo = 1;
          this.voucherNumberControls.push(voucherNumberControl);
        });
        this.voucherNumberControlsCopy = angular.copy(this.voucherNumberControls);
      });
    }

    public cancel() {
      this.initialize();
      this.notify.success("Cancelled");
    }

    public edit() {
      this.disable = false;
    }

    public save() {
      this.voucherNumberControlService.saveAndReturnList(this.voucherNumberControls).then((voucherNumberControls: IVoucherNumberControl[]) => {
        if (voucherNumberControls === undefined)
          this.notify.error("Error in saving data");
        else {
          this.notify.success("Data Successfully Saved");
          this.voucherNumberControls = [];
          this.voucherNumberControls = voucherNumberControls;
        }
      });
    }


  }

  UMS.controller("VoucherNumberController", VoucherNumberControlController);
}