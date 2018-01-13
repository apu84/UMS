module ums {


  export class NarrationController {

    public static $inject = ['$scope', '$modal', 'notify', 'NarrationService', '$timeout', 'VoucherService'];

    public narrations: INarration[];
    public narration: INarration;
    public disable: boolean;


    constructor($scope: ng.IScope, private $modal: any, private notify: Notify, private narrationService: NarrationService, private $timeout: ng.ITimeoutService, private voucherService: VoucherService) {
      this.disable = true;
      this.initialize();
    }

    private getAllVouchers() {
      this.voucherService.getAllVoucher().then((vouchers: IVoucher[]) => {
        this.narrations = [];
        vouchers.forEach((v: IVoucher) => {
          let narration: INarration = <INarration>{};
          narration.voucher = v;
          narration.voucherId = v.id;
          narration.statFlag = " ";
          narration.statUpFlag = " ";
          narration.narration = " ";
          this.narrations.push(narration);
        });
        console.log("Narrations");
        console.log(this.narrations);
        this.narration = this.narrations[0];
      });
    }

    private edit() {
      this.disable = false;
    }

    private narrationChanged() {
      console.log("In the narration changed");
      console.log(this.narration);
    }

    private cancel() {
      this.initialize();
    }

    private save() {
      this.narrationService.save(this.narrations).then((narrations: INarration[]) => {
        this.narrations = [];
        this.narrations = narrations;
        this.disable = true;
      });
    }

    private initialize() {
      this.narration = <INarration>{};
      this.narrationService.getAll().then((narrations: INarration[]) => {
        if (narrations.length > 0) {
          this.disable = true;
          this.narrations = [];
          this.narrations = narrations;
          console.log("narrations");
          console.log(narrations);
          this.narration = narrations[0];
          console.log("Narration");
          console.log(this.narration);
        } else {
          this.disable = false;
          this.getAllVouchers();
        }
      });


    }
  }

  UMS.controller("NarrationController", NarrationController);
}