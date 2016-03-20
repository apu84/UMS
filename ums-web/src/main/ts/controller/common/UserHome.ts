module ums {
  export class UserHome {
    public static $inject = ['HttpClient'];

    constructor(private httpClient: HttpClient) {

    }
  }

  UMS.controller("userHome", UserHome);
}