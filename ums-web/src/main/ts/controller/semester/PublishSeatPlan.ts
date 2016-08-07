module ums{
  interface IPublishSeatPlan extends ng.IScope{
    semesterList:Array<Semester>;
    semesterId:number;
    seatPlanPublishArr:Array<ISeatPlanPublish>;


    showTable:boolean;

    //Methods
    getActiveSemesters:Function;
    getCivilExamRoutine:Function;
    getCCIExamRoutine:Function;
    getSeatPlanPublish:Function;
    postSeatPlanPublish:Function;
    putSeatPlanPublish:Function;
    jsonConverter:Function;
    showPublishInfo:Function;
    saveData:Function;
  }

  interface ISeatPlanPublish{
    id:number;
    semesterId:number;
    examType:number;
    examDate:string;
    published:any;
  }



  class PublishSeatPlan{
    public static $inject = ['appConstants','HttpClient','$scope','$q','notify','$sce','$window'];
    constructor(private appConstants: any, private httpClient: HttpClient, private $scope: IPublishSeatPlan,
                private $q:ng.IQService, private notify: Notify,
                private $sce:ng.ISCEService,private $window:ng.IWindowService) {

      $scope.showTable=false;

      $scope.getActiveSemesters = this.getAcrtiveSemesters.bind(this);
      $scope.getSeatPlanPublish = this.getSeatPlanPublish.bind(this);
      $scope.postSeatPlanPublish = this.postSeatPlanPublish.bind(this);
      $scope.putSeatPlanPublish = this.putSeatPlanPublish.bind(this);
      $scope.jsonConverter = this.jsonConverter.bind(this);
      $scope.showPublishInfo=this.showPublishInfo.bind(this);
      $scope.saveData = this.saveData.bind(this);
    }

    private showPublishInfo(){

      this.$scope.showTable=true;
      this.getSeatPlanPublish().then((seatPlanPublishArr:Array<ISeatPlanPublish>)=>{
        if(seatPlanPublishArr.length==0){
          this.$scope.seatPlanPublishArr=[];
          var seatPlanPublish:any={};
          seatPlanPublish.published=false;
          seatPlanPublish.semesterId=this.$scope.semesterId;
          seatPlanPublish.examDate='11/11/2999';
          seatPlanPublish.examType=1;
          this.$scope.seatPlanPublishArr.push(seatPlanPublish);

          this.getCivilExamRoutine().then((civilExamInfoArr:ISeatPlanPublish)=>{

            this.$scope.seatPlanPublishArr.push(civilExamInfoArr);


            this.getCCIExamRoutine().then((cciArr:Array<ISeatPlanPublish>)=>{
                for(var i=0;i<cciArr.length;i++){
                  this.$scope.seatPlanPublishArr.push(cciArr[i]);
                }
            });
          });

        }
        else if(seatPlanPublishArr.length==2){
          this.getCCIExamRoutine().then((cciArr:Array<ISeatPlanPublish>)=>{
            for(var i=0;i<cciArr.length;i++){
              this.$scope.seatPlanPublishArr.push(cciArr[i]);
            }
          });
        }
        else{
          this.$scope.seatPlanPublishArr=[];
          for(var i=0;i<seatPlanPublishArr.length;i++){
            this.$scope.seatPlanPublishArr.push(seatPlanPublishArr[i]);
          }
        }
      });
    }


    private saveData():void{
      console.log("in the dave data");
      this.jsonConverter().then((json:any)=>{
          this.putSeatPlanPublish(json);
      });

    }



    private getAcrtiveSemesters():ng.IPromise<any>{
      var defer = this.$q.defer();
      this.$scope.semesterList=[];
      this.httpClient.get('/ums-webservice-common/academic/semester/all', 'application/json',
          (json:any, etag:string) => {
            this.$scope.semesterList = json.entries;
           // console.log(this.$scope.semesterList);

            defer.resolve(this.$scope.semesterList);
          },
          (response:ng.IHttpPromiseCallbackArg<any>) => {
            console.error(response);
          });
      return defer.promise;
    }

    private getCivilExamRoutine():ng.IPromise<any>{
      var defer = this.$q.defer();
      this.httpClient.get("/ums-webservice-common/academic/examroutine/forPublish/civil/semester/"+this.$scope.semesterId,'application/json',
          (json:any,etag:string)=>{
            var seatPlanPublishArr:ISeatPlanPublish=json.entries;
            console.log("~~~~~~~~~~~~~");
            console.log(seatPlanPublishArr);
            seatPlanPublishArr[0].examType=3;
            seatPlanPublishArr[0].published=false;

            console.log("###############");
            console.log(seatPlanPublishArr);

            defer.resolve(seatPlanPublishArr[0]);
          });

      return defer.promise;
    }
    private getCCIExamRoutine():ng.IPromise<any>{
      var defer = this.$q.defer();
      this.httpClient.get("/ums-webservice-common/academic/examroutine/forPublish/cci/semester/"+this.$scope.semesterId,'application/json',
          (json:any,etag:string)=>{
            var seatPlanPublishArr:Array<ISeatPlanPublish>=json.entries;
            for(var i=0;i<seatPlanPublishArr.length;i++){
              seatPlanPublishArr[i].published=false;
              seatPlanPublishArr[i].examType=2;
            }
            defer.resolve(seatPlanPublishArr);
          });

      return defer.promise;
    }

    private getSeatPlanPublish():ng.IPromise<any>{
      console.log(this.$scope.semesterId);
      var defer = this.$q.defer();
      this.httpClient.get("/ums-webservice-common/academic/seatPlanPublish/semester/"+this.$scope.semesterId,'application/json',
          (json:any,etag:string)=>{
              var seatPlanPubLish:Array<ISeatPlanPublish>=json.entries;
            console.log("**********************");
            console.log(seatPlanPubLish);
            for(var i=0;i<seatPlanPubLish.length;i++){
              if(seatPlanPubLish[i].published=="true"){
                  seatPlanPubLish[i].published=true;
              }else{
                seatPlanPubLish[i].published=false;
              }
            }

            defer.resolve(seatPlanPubLish);
          },
          (response:ng.IHttpPromiseCallbackArg<any>)=>{
            this.$window.alert("Error in getting publish data");
          });

      return defer.promise;
    }

    private postSeatPlanPublish(){
      var json = this.jsonConverter();
      this.httpClient.post('/ums-webservice-common/academic/seatPlanPublish/semester/'+this.$scope.semesterId,json,'application/json')
        .success(()=>{
            this.notify.success("Successfully saved data");
        })
      .error(()=>{
        this.$window.alert("Error in saving data");
      })
    }

    private putSeatPlanPublish(json:any){

      this.httpClient.put('/ums-webservice-common/academic/seatPlanPublish/semester/'+this.$scope.semesterId,json,'application/json')
          .success(()=>{
            this.notify.success("Successfully saved data");
          })
          .error(()=>{
            this.$window.alert("Error in saving data");
          })
    }

    private jsonConverter():ng.IPromise<any>{
      var defer = this.$q.defer();
      var completeJson={};
      var jsonObject=[];

      for(var i=0;i<this.$scope.seatPlanPublishArr.length;i++){
        var item={};
        if(this.$scope.seatPlanPublishArr[i].id!=null){
          item["id"]=this.$scope.seatPlanPublishArr[i].id;
        }else{
          item["id"]=0;
        }
        item["semesterId"]=this.$scope.semesterId;
        item["examType"]=this.$scope.seatPlanPublishArr[i].examType;
        if(this.$scope.seatPlanPublishArr[i].examDate!=null)
          item["examDate"]=this.$scope.seatPlanPublishArr[i].examDate;
        if(this.$scope.seatPlanPublishArr[i].published==false){
          item["published"]=0;
        }else{
          item["published"]=1;
        }

        jsonObject.push(item);
      }

      completeJson["entries"]=jsonObject;
      defer.resolve(completeJson);
      return defer.promise;
    }
  }

  UMS.controller("PublishSeatPlan",PublishSeatPlan);
}