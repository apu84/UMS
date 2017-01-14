/**
 * Created by Monjur-E-Morshed on 17-Dec-16.
 */

module ums{
  export class AdmissionStudentService{



    public static $inject = ['appConstants','HttpClient','$q','notify','$sce','$window'];
    constructor(private appConstants: any, private httpClient: HttpClient,
                private $q:ng.IQService, private notify: Notify,
                private $sce:ng.ISCEService,private $window:ng.IWindowService) {

    }


    public fetchTaletalkData(semesterId:number, programType:number):ng.IPromise<any>{
      console.log("in the service");
      console.log(programType);
      var url="academic/admission/taletalkData/semester/"+semesterId+"/programType/"+programType;
      var defer = this.$q.defer();

      this.httpClient.get(url, this.appConstants.mimeTypeJson,
          (json:any, etag:string)=>{
            var admissionStudents:any = json.entries;
            defer.resolve(admissionStudents);
          },
          (response:ng.IHttpPromiseCallbackArg<any>)=>{
            console.error(response);
          });

      return defer.promise;
    }

    public fetchAdmissionStudentByReceiptId(semesterId:number, programType:number, receiptId:string):ng.IPromise<any>{
      var url="academic/admission/semester/"+semesterId+"/programType/"+programType+"/receiptId/"+receiptId;
      var defer = this.$q.defer();

      this.httpClient.get(url, this.appConstants.mimeTypeJson,
          (json:any, etag:string)=>{
            var admissionStudent:any = json.entries;
            console.log(admissionStudent);
            defer.resolve(admissionStudent[0]);
          },
          (response:ng.IHttpPromiseCallbackArg<any>)=>{
            console.error(response);
          });

      return defer.promise;
    }

    public fetchTaletalkDataWithMeritType(semesterId:number, programType:number, meritTypeId:number, unit:string):ng.IPromise<any>{
      var url="academic/admission/taletalkData/semester/"+semesterId+"/programType/"+programType+"/unit/"+unit+"/meritType/"+meritTypeId;
      var defer = this.$q.defer();

      this.httpClient.get(url, this.appConstants.mimeTypeJson,
          (json:any, etag:string)=>{
            var admissionStudents:any = json.entries;
            defer.resolve(admissionStudents);
          },
          (response:ng.IHttpPromiseCallbackArg<any>)=>{
            console.error(response);
          });

      return defer.promise;
    }


    public fetchMeritList(semesterId:number, programType:number, meritTypeId:number, unit:string):ng.IPromise<any>{
      var url="academic/admission/meritList/semester/"+semesterId+"/programType/"+programType+"/unit/"+unit+"/meritType/"+meritTypeId;
      var defer = this.$q.defer();

      this.httpClient.get(url, this.appConstants.mimeTypeJson,
          (json:any, etag:string)=>{
            var admissionStudents:any = json.entries;
            defer.resolve(admissionStudents);
          },
          (response:ng.IHttpPromiseCallbackArg<any>)=>{
            console.error(response);
          });

      return defer.promise;
    }

    public fetchStatistics(semesterId:number, programType:number, meritTypeId:number, unit:string):ng.IPromise<any>{
      var url="academic/admission/statistics/semester/"+semesterId+"/programType/"+programType+"/unit/"+unit+"/meritType/"+meritTypeId;
      var defer = this.$q.defer();

      this.httpClient.get(url, this.appConstants.mimeTypeJson,
          (json:any, etag:string)=>{
            var admissionStudents:any = json.entries;
            defer.resolve(admissionStudents);
          },
          (response:ng.IHttpPromiseCallbackArg<any>)=>{
            console.error(response);
          });

      return defer.promise;
    }

    public downloadTaletalkDataExcelFile(semesterId: number):any{

      var fileName = "Taletalk_data_"+semesterId;
      var contentType:string=UmsUtil.getFileContentType('xls');
      var url="admission/xlx/taletalkData/semester/"+semesterId;

      this.httpClient.get(url, contentType,
          (data:any, etag:string)=>{
        console.log(data);
        console.log(contentType);
            UmsUtil.writeFileContent(data, contentType, fileName);
            console.log("got the file");
          },
          (response:ng.IHttpPromiseCallbackArg<any>)=>{
            console.error(response);
          },'arraybuffer');
    }


    public downloadMeritListExcelFile(semesterId: number):any{

      var fileName = "merit_list_data";
      var contentType:string=UmsUtil.getFileContentType('xls');
      var url="admission/xlx/meritList/semester/"+semesterId;

      this.httpClient.get(url, contentType,
          (data:any, etag:string)=>{
            console.log(data);
            console.log(contentType);
            UmsUtil.writeFileContent(data, contentType, fileName);
            console.log("got the file");
          },
          (response:ng.IHttpPromiseCallbackArg<any>)=>{
            console.error(response);
          },'arraybuffer');
    }

    public saveTaletalkData(json:any, semesterId:number, programType:number):ng.IPromise<any>{
      var defer=this.$q.defer();
      var url="academic/admission/taletalkData/semester/"+semesterId+"/programType/"+programType;
      this.httpClient.post(url,json,'application/json')
          .success(()=>{
            defer.resolve("success");
          }).error((data)=>{
        defer.resolve("error");
      });

      return defer.promise;
    }

    public saveMeritList(json:any):ng.IPromise<any>{
      var defer=this.$q.defer();
      var url="academic/admission/meritListUpload";
      this.httpClient.put(url,json,'application/json')
          .success(()=>{
            defer.resolve("success");
          }).error((data)=>{
        defer.resolve("error");
      });

      return defer.promise;
    }

    public saveAndFetchNextStudentForDepartmentSelection(departmentSelectionStatus:number, json:any):ng.IPromise<any>{
      var defer = this.$q.defer();
      var url = "academic/admission/departmentSelectionStatus/"+departmentSelectionStatus;

      this.httpClient.put(url, json, 'application/json')
          .success((data)=>{
              this.notify.success("Successfully Saved");
              console.log(data);
              defer.resolve(data.entries);
          }).error((data)=>{

          });
      return defer.promise;
    }
  }

  UMS.service("admissionStudentService", AdmissionStudentService);
}