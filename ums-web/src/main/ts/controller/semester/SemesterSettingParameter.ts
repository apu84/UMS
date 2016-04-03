///<reference path="../../model/ParameterSetting.ts"/>
///<reference path="../../model/master_data/Parameter.ts"/>
module ums{
//  import ParameterSetting = ums.IParameterSetting;
  interface ISemesterSettingParameterScope extends ng.IScope{
    semesterSelector:Array<Semester>;
    parameterSelector:Array<IParameter>
    parameterId:string;
    semesterTypeId:number;
    programTypeSelector:any;
    programType:number;
    getSemesterInfo:Function;
    searchAndEdit:Function;
    addData:Function;
    editData:Function;
    cancel:Function;
    deleteData:Function;
    addNewRow:Function;
    getParameterSettingData:Function;
    getParameterInfo:Function;
    showData:Function;
    addDate:Function;
    showValue:Function; //for test purpose
    convertToJson: Function;
    getAllData:Function;
    addAndReloadData:Function;
    saveAll:Function;
    getDummyParameterSetting: Function;
    semesterSettingParameterData:Array<IParameterSetting>;
    semesterSettingParameter: IParameterSetting;
    dummySemesterSetting: Array<DummySemesterSetting>;
    inner : DummySemesterSetting;
    addRow:boolean;
    showTable:boolean;
    semesterSettingSize:number;
    endDate:string;
    beginningDate:string;
   // startDate  : string;
    contentEdit:boolean;
    editId:string;
    data:any;
    semesterSettingStore:any;
  }

  class DummySemesterSetting{
    srl:number;
    id: any;
    parameterId: any;
    longDescription: any;
    semesterId: any;
    startDate: any;
    endDate: any;
    startDateTmp:any;
    endDateTmp: any;
    editData:boolean;
    updatable:boolean;
  }


  export class SemesterSettingParameter{
    public static $inject = ['appConstants','HttpClient','$scope','$q'];
    constructor(private appConstants: any, private httpClient: HttpClient, private $scope: ISemesterSettingParameterScope,private $q:ng.IQService ){
      $scope.contentEdit=false;
      $scope.addRow=false;
      $scope.showTable = false;
      $scope.programTypeSelector = appConstants.programType;
      $scope.getSemesterInfo = this.getSemesterInfo.bind(this);
      $scope.searchAndEdit = this.searchAndEdit.bind(this);
      $scope.addNewRow = this.addNewRow.bind(this);
      $scope.getParameterSettingData = this.getParameterSettingData.bind(this);
      $scope.getParameterInfo = this.getParameterInfo.bind(this);
      $scope.editData = this.editData.bind(this);
      $scope.addDate = this.addDate.bind(this);
      $scope.addData = this.addData.bind(this);
      $scope.showValue = this.showValue.bind(this);
      $scope.cancel = this.cancel.bind(this);
      $scope.getAllData = this.getAllData.bind(this);
      $scope.addAndReloadData = this.addAndReloadData.bind(this);
      $scope.saveAll = this.saveAll.bind(this);
      $scope.getDummyParameterSetting = this.getDummyParameterSetting.bind(this);

      $scope.data = {
        startDate:"",
        endDate:"",
        foundData:false
      };
    }

    private addAndReloadData(parameter:DummySemesterSetting):void{
      this.addData(parameter);

    }
    private getAllData():void{
      this.getParameterSettingData().then((parameterSettingArr:Array<IParameterSetting>)=>{
        this.getDummyParameterSetting();
      });

    }
    private cancel(srl:number,parameterId:string):void{
      this.$scope.semesterSettingStore[srl].editData=false;
    }

    private getDummyParameterSetting():void{
      this.$scope.showTable = true;
      console.log("~~~~~~~~~~~~~~~~In dummy parameter setting------>>>")
      var gotValue:boolean;
      var parameterSettingArr: DummySemesterSetting[] = [];
      gotValue = false;

      var count:number = 0;
      for(var i=0;i<this.$scope.parameterSelector.length; i++){

        gotValue = false;

          for(var j=0;j< this.$scope.semesterSettingParameterData.length; j++){


            if(this.$scope.semesterSettingParameterData[j].parameterId == this.$scope.parameterSelector[i].id && this.$scope.semesterSettingParameterData[j].semesterId == this.$scope.semesterTypeId){
              gotValue = true;
              var inners = new DummySemesterSetting();
              inners.srl = count;
              inners.id = this.$scope.semesterSettingParameterData[j].id;
              inners.parameterId = this.$scope.semesterSettingParameterData[j].parameterId;
              inners.semesterId = this.$scope.semesterSettingParameterData[j].semesterId;
              inners.longDescription =this.$scope.parameterSelector[i].longDescription ;
              inners.startDate = this.$scope.semesterSettingParameterData[j].startDate;
              inners.endDate = this.$scope.semesterSettingParameterData[j].endDate;
              inners.startDateTmp = this.$scope.semesterSettingParameterData[j].startDate;
              inners.endDateTmp = this.$scope.semesterSettingParameterData[j].endDate;
              inners.editData = false;
              inners.updatable = true;
              parameterSettingArr.push(inners);
              count++;
            }
          }
        if(gotValue == false){
          var inners = new DummySemesterSetting();
          inners.srl = count;
          inners.id = '';
          inners.parameterId = this.$scope.parameterSelector[i].id;
          inners.semesterId = this.$scope.semesterTypeId;
          inners.longDescription =this.$scope.parameterSelector[i].longDescription;
          inners.startDate = '';
          inners.endDate = '';
          inners.startDateTmp = '';
          inners.endDateTmp = '';
          inners.editData = false;
          inners.updatable = false;
          parameterSettingArr.push(inners);
          count++;

        }
      }
      this.$scope.semesterSettingStore = parameterSettingArr;
      console.log("-------dummy parameter setting -------------");
      console.log(parameterSettingArr);
      console.log('Array value: '+this.$scope.semesterSettingStore.length);
    }

    private addDate():void{
      console.log("this is inside date");
      setTimeout(function () {
        $('.datepicker-default').datepicker();
        $('.datepicker-default').on('change', function () {
          $('.datepicker').hide();
        });
      }, 200);

      //BEGIN PLUGINS DATETIME PICKER
      //$('.datetimepicker-default').datetimepicker();



    }

    private showValue():void{
      console.log('MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM-----------');
      console.log(this.$scope.data.startDate+"@@@@@");
    }
    private showData():void{
        this.$scope.showTable= true;
    }

    private editData(srl:number,editId:string):void{

      this.$scope.editId = editId;

      this.$scope.contentEdit = true;

      this.$scope.semesterSettingStore[srl].editData = true;

    }

    private getParameterSettingData():ng.IPromise<any>{
      var defer = this.$q.defer();
      //this.$scope.showTable= true;
      var semesterSettingParameterArr:Array<any>;
      this.httpClient.get('academic/parameterSetting/all', 'application/json',
          (json:any, etag:string) => {
            semesterSettingParameterArr = json.entries;
            console.log("semester parameter Data------------->");

            this.$scope.semesterSettingSize = semesterSettingParameterArr.length;

            this.$scope.semesterSettingParameterData = semesterSettingParameterArr;

            defer.resolve(semesterSettingParameterArr);

          },
          (response:ng.IHttpPromiseCallbackArg<any>) => {
            console.error(response);
          });
      return defer.promise;
    }

    private getSemesterInfo():void{
      var semesterArr:Array<any>;
      this.httpClient.get('academic/semester/program-type/' + this.$scope.programType+'/limit/40', 'application/json',
          (json:any, etag:string) => {
            semesterArr = json.entries;
            this.$scope.semesterSelector = semesterArr;
          },
          (response:ng.IHttpPromiseCallbackArg<any>) => {
            console.error(response);
          });
    }
    private getParameterInfo():void{
      var parameterArr:Array<any>;
      this.httpClient.get('academic/academicCalenderParameter/all', 'application/json',
          (json:any, etag:string) => {
            console.log("-----------parameter---------");
            console.log(json);
            parameterArr = json.entries;
            this.$scope.parameterSelector = parameterArr;
          },
          (response:ng.IHttpPromiseCallbackArg<any>) => {
            console.error(response);
          });
    }
    private searchAndEdit():void{
      this.$scope.showTable = true;
    }
    private addNewRow():void{
      if(this.$scope.addRow==true){
        this.$scope.addRow = false;
        console.log(this.$scope.addRow);
      }

      this.$scope.addRow = true;
      console.log(this.$scope.addRow);
    }
    private addData(parameter:DummySemesterSetting):void{

     console.log("-----------------#######_________-");
      console.log(parameter);

      if (parameter.updatable == true) {
          this.$scope.semesterSettingStore[parameter.srl].startDate = parameter.startDateTmp;
          this.$scope.semesterSettingStore[parameter.srl].endDate = parameter.endDateTmp;
          ////
          var json = this.convertToJsonForUpdate(this.$scope.semesterSettingStore[parameter.srl].id,this.$scope.semesterTypeId, parameter.parameterId, parameter.startDate, parameter.endDate);

          this.httpClient.put('academic/parameterSetting/'+this.$scope.semesterSettingStore[parameter.srl].id, json, 'application/json')
              .success(() => {


              }).error((data) => {
          });

         this.$scope.semesterSettingStore[parameter.srl].startDate=parameter.startDateTmp;
         this.$scope.semesterSettingStore[parameter.srl].endDate=parameter.endDateTmp;
        this.$scope.semesterSettingStore[parameter.srl].updatable = true;
         this.$scope.semesterSettingStore[parameter.srl].editData = false;
        console.log("-----updated data---");
        console.log(this.$scope.semesterSettingStore);

      }
      else {


      if (parameter.startDate == '' || parameter.endDate == '') {
          alert("parameterId:"+parameter.parameterId+", startDate:"+ parameter.startDate+",endDate:"+parameter.endDate);
        }
      else {
        var jsons = this.convertToJson(this.$scope.semesterTypeId, parameter.parameterId, parameter.startDateTmp, parameter.endDateTmp);
        console.log("----------------json----------------");
        console.log(json);
        this.$scope.semesterSettingStore[parameter.srl].editData = false;
        this.httpClient.post('academic/parameterSetting/', jsons, 'application/json')
            .success(() => {
              this.getParameterSettingData().then((parameterSettingArr:Array<IParameterSetting>)=>{
                this.getDummyParameterSetting();
              });
            }).error((data) => {
        });


      }
    }


    }
    private convertToJson(semesterTypeId:number,parameterId:string,startDate:string,endDate:string):any{
      var jsonObj=[];
      var item={};
      item["id"] = "";
      item["semesterId"] = this.$scope.semesterTypeId;
      item["parameterId"] = parameterId;
      item["startDate"] =startDate;
      item["endDate"] = endDate;
      jsonObj.push(item);

      return item;
    }
    private convertToJsonForUpdate(id:string,semesterTypeId:number,parameterId:string,startDate:string,endDate:string){
      var item={};
      item["id"]= id;
      item["semesterId"] = this.$scope.semesterTypeId;
      item["parameterId"] = parameterId;
      item["startDate"] =startDate;
      item["endDate"] = endDate;
      return item;
    }
    private saveAll():void{
      console.log("--well--");
      var finished=false;
      console.log("^^^^^^^^^^^^^^^^^^");
      console.log(this.$scope.semesterSettingStore);
      var count = 0;
      var store:DummySemesterSetting[]=[];
      for(var i=0;i<this.$scope.semesterSettingStore.length;i++){
        store.push(this.$scope.semesterSettingStore[i]);
      }
      var length = this.$scope.semesterSettingStore.length;
      for(var i=0; i<store.length;i++){

        this.addData(store[i]);
        console.log("----");
        console.log(store[i]);
        count++;
      }



    }
  }
  UMS.controller("SemesterSettingParameter",SemesterSettingParameter);
}