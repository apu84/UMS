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
      var url="/ums-webservice-common/users/current";
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
      if (total_marks >= 80 && reg_type==1) return "A+";
      else if (total_marks >= 75 && total_marks < 80 && reg_type==1) return "A";
      else if (total_marks >= 70 && total_marks < 75 && reg_type==1) return "A-";
      else if (total_marks >= 65 && total_marks < 70 && reg_type==1) return "B+";
      else if (total_marks >= 60 && total_marks < 65 && reg_type==1) return "B";
      else if (total_marks >= 60 &&  reg_type==5) return "B"; // Improvement
      else if (total_marks >= 55 && total_marks < 60 && (reg_type==1 || reg_type==5)) return "B-";
      else if (total_marks >= 50 && total_marks < 55 && (reg_type==1 || reg_type==5)) return "C+";
      else if (total_marks >= 45 && total_marks < 50) return "C";
      else if (total_marks >= 45 && (reg_type==2 || reg_type==3  || reg_type==4)) return "C";  //Carry,Clearance, Special Carry
      else if (total_marks >= 40 && total_marks < 45) return "D";
      else if (total_marks < 40) return "F";

    }
  }

  UMS.service('commonService',CommonService);
}