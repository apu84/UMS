///<reference path="../../service/HttpClient.ts"/>
///<reference path="../../service/FileUpload.ts"/>
///<reference path="../../model/SemesterSyllabusMapModel.ts"/>
///<reference path="../../lib/jquery.notific8.d.ts"/>

module ums {
  interface SemesterSyllabusMapScope extends ng.IScope {
    semesterSyllabusMapModel: SemesterSyllabusMapModel;
    submit: Function;
    goNext:Function;
    fetchSSmap:Function;
    map:any;
    maps:any;
    single:any;
    mapMessage:any;
    copyDivVisiblity:boolean;
    copySemesterId:string;
    copyMapping:Function;
    syllabusOptions: any;
    loadingVisibility1:boolean;
    loadingVisibility2:boolean;
    mapTableVisibility:boolean;
    mapDetailVisiblity:boolean;
    syllabusSelectBoxVisibility:boolean;

  }
  export class SemesterSyllabusMap {
    public static $inject = ['appConstants', '$scope', 'HttpClient'];

    constructor(private appConstants:any, private $scope:SemesterSyllabusMapScope, private httpClient:HttpClient) {

      $scope.semesterSyllabusMapModel = new SemesterSyllabusMapModel(appConstants, httpClient);
//      $scope.semesterSyllabusMapModel.programTypes.pop();
      $scope.goNext= this.goNext.bind(this);
      $scope.copySemesterId= this.copySemesterId.bind(this);
      $scope.fetchSSmap= this.fetchSSmap.bind(this);

      $scope.mapTableVisibility=false;
      $scope.syllabusSelectBoxVisibility=false;

      $scope.$watch(
          () => {return $scope.semesterSyllabusMapModel.departmentId;},
          (newValue, oldValue) => {if (newValue !== oldValue) {this.clearMap();}}
      );

      $scope.$watch(
          () => {return $scope.semesterSyllabusMapModel.programId;},
          (newValue, oldValue) => {if (newValue !== oldValue) {this.clearMap();}}
      );
      $scope.$watch('semesterSyllabusMapModel.semesters', function() { $scope.semesterSyllabusMapModel.semesterId=appConstants.Empty; }, true);
    }

    private clearMap():void{
      this.$scope.maps=null;
      this.$scope.mapTableVisibility=false;
      this.$scope.mapDetailVisiblity=false;
    }
    private goNext():void {
      this.$scope.loadingVisibility1=true;
      this.$scope.mapTableVisibility=false;
      this.$scope.mapDetailVisiblity=false;
        this.httpClient.get('academic/ssmap/program/'+this.$scope.semesterSyllabusMapModel.programId+'/semester/'+this.$scope.semesterSyllabusMapModel.semesterId+'', 'application/json',
            (json:any, etag:string) => {
              this.$scope.loadingVisibility1=false;
              this.$scope.maps = json.entries;
              this.$scope.mapTableVisibility=this.$scope.maps.length==0?false:true;
              this.$scope.mapMessage=this.$scope.maps.length==0?"No Mapping Found.":"";
              this.$scope.copyDivVisiblity=this.$scope.maps.length==0?true:false;
            },
            (response:ng.IHttpPromiseCallbackArg<any>) => {
              console.error(response);
              this.$scope.loadingVisibility1=false;
            });


    }

    private copySemesterId():void{

      //var abc={"sourceSemesterId":'';"targetSemesterId":'',programId=''};
      this.httpClient.post('academic/ssmap/',{}, 'application/json')
          .success(() => {
          }).error((data) => {
          });


    }

    private fetchSSmap(mapId):void {
      this.$scope.loadingVisibility2=true;
      this.$scope.mapDetailVisiblity=false;

      this.httpClient.get('academic/ssmap/'+mapId, 'application/json',
          (json:any, etag:string) => {
            this.$scope.loadingVisibility2=false;
            this.$scope.single=json;
            this.$scope.mapDetailVisiblity=true;

            //Should be removed from here :
            this.$scope.semesterSyllabusMapModel.syllabuses=this.appConstants.ugDept;
          },
          (response:ng.IHttpPromiseCallbackArg<any>) => {
            this.$scope.loadingVisibility2=false;
            console.error(response);
          });


    }


  }
  UMS.controller('SemesterSyllabusMap', SemesterSyllabusMap);
}

