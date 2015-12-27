module ums {
  export interface ChangePasswordModel {
    currentPassword : string;
    newPassword: string;
    confirmNewPassword: string;
  }
}