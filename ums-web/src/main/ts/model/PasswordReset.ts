module ums {
  export interface PasswordReset {
    userId: string;
    resetMode: string;
    singleUser:boolean;
    multipleUser:boolean;
  }
}