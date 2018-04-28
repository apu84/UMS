module ums {
  export class BankController {
    public static $inject = ['BankService'];
    private banks: Bank[];
    constructor(private bankService: BankService) {
      this.populateBankList();
    }

    private populateBankList() {
      this.bankService.getBanks().then((banks: Bank[]) => {
        this.banks = banks;
      });
    }
  }

  UMS.controller('BankController', BankController);
}
