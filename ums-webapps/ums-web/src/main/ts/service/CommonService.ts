module ums{
  export class CommonService{
    public static $inject = ['appConstants','HttpClient','$q'];
    constructor(private appConstants: any, private httpClient: HttpClient,
                private $q:ng.IQService) {
    }

    public padLeft(nr:number,  n :number, str:string):string{
        return Array(n-String(nr).length+1).join(str)+nr;

      //examples
      /*
      console.log(padLeft(23,5));       //=> '00023'
      console.log((23).padLeft(5));     //=> '00023'
      console.log((23).padLeft(5,' ')); //=> '   23'
      console.log(padLeft(23,5,'>>'));  //=> '>>>>>>23' */
    }
    public fetchCurrentUser():ng.IPromise<any> {
      var url="/ums-webservice-academic/users/current";
      var defer = this.$q.defer();
      this.httpClient.get(url, this.appConstants.mimeTypeJson,
          (json:any, etag:string) => {
            defer.resolve({id:json.departmentId,name:json.departmentName});
          },
          (response:ng.IHttpPromiseCallbackArg<any>) => {
            console.error(response);
          });
      return defer.promise;
    }
    public getGradeLetter(total_marks:number,reg_type:number):string {
      var regType:any=this.appConstants.courseRegType;
      if (total_marks >= 80 && reg_type==regType.REGULAR) return "A+";
      else if (total_marks >= 75 && total_marks < 80 && reg_type==regType.REGULAR) return "A";
      else if (total_marks >= 70 && total_marks < 75 && reg_type==regType.REGULAR) return "A-";
      else if (total_marks >= 65 && total_marks < 70 && reg_type==regType.REGULAR) return "B+";
      else if (total_marks >= 60 && total_marks < 65 && reg_type==regType.REGULAR) return "B";
      else if (total_marks >= 60 &&  reg_type==regType.IMPROVEMENT) return "B"; // Improvement
      else if (total_marks >= 55 && total_marks < 60 && (reg_type==regType.REGULAR || reg_type==regType.IMPROVEMENT)) return "B-";
      else if (total_marks >= 50 && total_marks < 55 && (reg_type==regType.REGULAR || reg_type==regType.IMPROVEMENT)) return "C+";
      else if (total_marks >= 45 && total_marks < 50) return "C";
      else if (total_marks >= 45 && (reg_type==regType.CLEARANCE  || reg_type==regType.CARRY  || reg_type==regType.SPECIAL_CARRY)) return "C";  //Carry,Clearance, Special Carry
      else if (total_marks >= 40 && total_marks < 45) return "D";
      else if (total_marks < 40) return "F";

    }
  }

  UMS.service('commonService',CommonService);
}