module ums {
  export class BankService {
    public static $inject = ['BankHttpService'];
    constructor(private bankHttpService: BankHttpService) {
      
    }
    
    public getBanks(): ng.IPromise<Bank[]> {
      const bankResource: BankResource<Bank[]> = new BankResource<Bank[]>('bank/all');
      const bankConverter: IdentityConverter<Bank[]> = new IdentityConverter<Bank[]>();
      return this.bankHttpService.getResource(bankResource, bankConverter);
    }
  }

  UMS.service('BankService', BankService);
}
