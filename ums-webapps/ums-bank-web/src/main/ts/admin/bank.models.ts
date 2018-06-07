module ums {
  interface Identifier {
    id: string;
  }

  export interface Bank extends Identifier {
    code: string;
    name: string;
    id: string;
  }

  export interface BankDesignation extends Identifier {
    code: string;
    name: string;
    id: string;
  }

  export interface BankBranch extends Identifier {
    code: string;
    bankId: string;
    name: string;
    contactNo: string;
    location: string;
    id: string;
  }

  export interface BranchUser extends Identifier {
    userId: string;
    branchId: string;
    name: string;
    bankDesignationId: string;
    email: string;
    id: string;
  }
}
