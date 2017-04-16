/**
 * Created by My Pc on 25-Jan-17.
 */
module ums{

  export class PaymentInfoService{
    public static $inject = ['appConstants','HttpClient','$q','notify','$sce','$window'];
    constructor(private appConstants: any, private httpClient: HttpClient,
                private $q:ng.IQService, private notify: Notify,
                private $sce:ng.ISCEService,private $window:ng.IWindowService) {

    }

    public fetchAdmissionPaymentInfo(receiptId:string, semesterId:number, quota:string):ng.IPromise<any>{
      var url:string = 'core/payment/semester/'+semesterId+'/receiptId/'+receiptId+'/quota/'+quota;
      var defer = this.$q.defer();
      this.httpClient.get(url, this.appConstants.mimeTypeJson,
          (json:any, etag:string)=>{
            var paymentInfo:any = json.entries;
            defer.resolve(paymentInfo);
          },
          (response:ng.IHttpPromiseCallbackArg<any>)=>{
            console.error(response);
          });

      return defer.promise;
    }

    public saveAdmissionPaymentInfo(json:any):ng.IPromise<any>{
      var defer = this.$q.defer();
      var url:string = 'core/payment/admission';
      this.httpClient.post(url, json, 'application/json')
          .success(()=>{
            this.notify.success("Successfully Saved");
            defer.resolve("success");
          }).error((data)=>{
          console.log(data);
          this.notify.error("Failed to save data");
      });
      return defer.promise;
    }


  }



  UMS.service("paymentInfoService", PaymentInfoService);
}