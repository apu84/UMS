module ums {
  interface Identifier {
    id: string;
  }

  export interface Bank extends Identifier {
    code: string;
    name: string;
  }

  export interface BankDesignation extends Identifier {
    code: string;
    name: string;
  }

  export interface BankBranch extends Identifier {
    code: string;
    bankId: string;
    name: string;
    contactNo: string;
    location: string;
  }

  export interface BranchUser extends Identifier {
    userId: string;
    branchId: string;
    name: string;
    designationId: string;
    email: string;
  }
}