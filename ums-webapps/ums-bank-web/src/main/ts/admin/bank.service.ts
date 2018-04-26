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

    public updateBank(bank: Bank): ng.IPromise<Bank> {
      const bankResource: BankResource<Bank> = new BankResource<Bank>(`bank/${bank.id}`, bank);
      const bankConverter: IdentityConverter<Bank> = new IdentityConverter<Bank>();
      return this.bankHttpService.putResource(bankResource, bankConverter);
    }

    public getBank(bankId: string): ng.IPromise<Bank> {
      const bankResource: BankResource<Bank> = new BankResource<Bank>(`bank/${bankId}`);
      const bankConverter: IdentityConverter<Bank> = new IdentityConverter<Bank>();
      return this.bankHttpService.getResource(bankResource, bankConverter);
    }

    public getDesignations(): ng.IPromise<BankDesignation[]> {
      const designationResource: BankResource<BankDesignation[]>
          = new BankResource<BankDesignation[]>('bank/designation/all');
      const designationConverter: IdentityConverter<BankDesignation[]> = new IdentityConverter<BankDesignation[]>();
      return this.bankHttpService.getResource(designationResource, designationConverter);
    }

    public updateDesignation(bankDesignation: BankDesignation): ng.IPromise<BankDesignation> {
      const designationResource: BankResource<BankDesignation>
          = new BankResource<BankDesignation>(`bank/designation/${bankDesignation.id}`);
      const designationConverter: IdentityConverter<BankDesignation> = new IdentityConverter<BankDesignation>();
      return this.bankHttpService.putResource(designationResource, designationConverter);
    }

    public deleteDesignation(bankDesignation: BankDesignation): ng.IPromise<boolean> {
      const designationResource: BankResource<BankDesignation>
          = new BankResource<BankDesignation>(`bank/designation/${bankDesignation.id}`);
      return this.bankHttpService.deleteResource(designationResource);
    }

    public getBranches(bankId: string): ng.IPromise<BankBranch[]> {
      const branchResource: BankResource<BankBranch[]>
          = new BankResource<BankBranch[]>(`bank/branch/${bankId}/all`);
      const branchConverter: IdentityConverter<BankBranch[]> = new IdentityConverter<BankBranch[]>();
      return this.bankHttpService.getResource(branchResource, branchConverter);
    }

    public createBranch(branch: BankBranch): ng.IPromise<BankBranch> {
      const branchResource: BankResource<BankBranch>
          = new BankResource<BankBranch>(`bank/branch`);
      const branchConverter: IdentityConverter<BankBranch> = new IdentityConverter<BankBranch>();
      return this.bankHttpService.postResource(branchResource, branchConverter);
    }

    public updateBranch(branch: BankBranch): ng.IPromise<BankBranch> {
      const branchResource: BankResource<BankBranch>
          = new BankResource<BankBranch>(`bank/branch/${branch.id}`);
      const branchConverter: IdentityConverter<BankBranch> = new IdentityConverter<BankBranch>();
      return this.bankHttpService.putResource(branchResource, branchConverter);
    }

    public deleteBranch(branch: BankBranch): ng.IPromise<boolean> {
      const branchResource: BankResource<BankBranch>
          = new BankResource<BankBranch>(`bank/branch/${branch.id}`);
      return this.bankHttpService.deleteResource(branchResource);
    }

    public getUsers(branchId: string): ng.IPromise<BranchUser[]> {
      const userResource: BankResource<BranchUser[]>
          = new BankResource<BranchUser[]>(`bank/branch/user/${branchId}/all`);
      const userConverter: IdentityConverter<BranchUser[]> = new IdentityConverter<BranchUser[]>();
      return this.bankHttpService.getResource(userResource, userConverter);
    }

    public updateUser(user: BranchUser): ng.IPromise<BranchUser> {
      const userResource: BankResource<BranchUser>
          = new BankResource<BranchUser>(`bank/branch/user/${user.id}`);
      const userConverter: IdentityConverter<BranchUser> = new IdentityConverter<BranchUser>();
      return this.bankHttpService.putResource(userResource, userConverter);
    }

    public deleteUser(user: BranchUser): ng.IPromise<boolean> {
      const userResource: BankResource<BranchUser>
          = new BankResource<BranchUser>(`bank/branch/user/${user.id}`);
      return this.bankHttpService.deleteResource(userResource);
    }
  }

  UMS.service('BankService', BankService);
}
