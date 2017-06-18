module ums {
  export class DuesController {
    public static $inject = ['StudentDuesService'];

    constructor(private studentDuesService: StudentDuesService) {

    }
  }

  UMS.controller('DuesController', DuesController);
}
