module ums {
  export var TOKEN_KEY: string = 'ums.token';
  export interface Token {
    access_token: string;
    expires_in: number;
  }
}
