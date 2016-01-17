///<reference path="../../service/HttpClient.ts"/>
///<reference path="../../service/FileUpload.ts"/>
///<reference path="../../model/SemesterSyllabusMapModel.ts"/>
///<reference path="../../lib/jquery.notific8.d.ts"/>

module ums {
  interface SemesterSyllabusMapScope extends ng.IScope {
    semesterSyllabusMapModel: SemesterSyllabusMapModel;
    submit: Function;
    goNext:Function;
    editAction:Function;
    editSaveAction:Function;
    fetchSSmap:Function;
    map:any;
    maps:any;
    single:any;
    mapMessage:any;
    copyDivVisiblity:boolean;
    copySemesterId:string;
    copyMapping:Function;
    syllabusOptions: any;
    syllabusId:string;
    loadingVisibility1:boolean;
    loadingVisibility2:boolean;
    mapTableVisibility:boolean;
    mapDetailVisiblity:boolean;
    syllabusSelectBoxVisibility:boolean;
    syllabusTextBoxVisibility:boolean;
    noRecordVisibility:boolean;

  }
  export class SemesterSyllabusMap {
    public static $inject = ['appConstants', '$scope', 'HttpClient'];

    constructor(private appConstants:any, private $scope:SemesterSyllabusMapScope, private httpClient:HttpClient) {

      $scope.semesterSyllabusMapModel = new SemesterSyllabusMapModel(appConstants, httpClient);
//      $scope.semesterSyllabusMapModel.programTypes.pop();
      $scope.goNext= this.goNext.bind(this);
      $scope.copyMapping= this.copyMapping.bind(this);
      $scope.fetchSSmap= this.fetchSSmap.bind(this);
      $scope.editAction= this.editAction.bind(this);
      $scope.editSaveAction= this.editSaveAction.bind(this);


      $scope.mapTableVisibility=false;
      $scope.syllabusTextBoxVisibility=true;
      $scope.syllabusSelectBoxVisibility=false;
      $scope.noRecordVisibility=false;

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
      this.$scope.noRecordVisibility=false;
      this.$scope.copyDivVisiblity=false;

        this.httpClient.get('academic/ssmap/program/'+this.$scope.semesterSyllabusMapModel.programId+'/semester/'+this.$scope.semesterSyllabusMapModel.semesterId+'', 'application/json',
            (json:any, etag:string) => {
              this.$scope.loadingVisibility1=false;
              this.$scope.maps = json.entries;
              this.$scope.mapTableVisibility=this.$scope.maps.length==0?false:true;
              this.$scope.noRecordVisibility=this.$scope.maps.length==0?true:false;
              this.$scope.mapMessage=this.$scope.maps.length==0?"No Mapping Found.":"";

              this.$scope.copyDivVisiblity=this.$scope.maps.length==0?true:false;
            },
            (response:ng.IHttpPromiseCallbackArg<any>) => {
              console.error(response);
              this.$scope.loadingVisibility1=false;
            });


    }

    private copyMapping():void{

      var postJson={"semesterId":parseInt(this.$scope.semesterSyllabusMapModel.semesterId),"copySemesterId":parseInt(this.$scope.copySemesterId),"programId":parseInt(this.$scope.semesterSyllabusMapModel.programId)};
      this.httpClient.post('academic/ssmap/',postJson, 'application/json')
          .success(() => {
            $.notific8('Successfully inserted new mapping.');
          }).error((data) => {
          });
    }

    private fetchSSmap(mapId):void {
      this.$scope.loadingVisibility2=true;
      this.$scope.mapDetailVisiblity=false;
      this.$scope.syllabusTextBoxVisibility=true;
      this.$scope.syllabusSelectBoxVisibility=false;

      this.httpClient.get('academic/ssmap/'+mapId, 'application/json',
          (json:any, etag:string) => {
            this.$scope.loadingVisibility2=false;
            this.$scope.single=json;
            this.$scope.mapDetailVisiblity=true;

          },
          (response:ng.IHttpPromiseCallbackArg<any>) => {
            this.$scope.loadingVisibility2=false;
            console.error(response);
          });

      this.httpClient.get('academic/syllabus/program-id/'+this.$scope.semesterSyllabusMapModel.programId, 'application/json',
           (json:any, etag:string) =>{

            var entries:any = json.entries;
            this.$scope.syllabusOptions = entries;
            this.$scope.single.syllabusId= entries[0].id;

          }, function (response:ng.IHttpPromiseCallbackArg<any>) {
            alert(response);
          });
    }


    private editAction():void {
      this.$scope.syllabusTextBoxVisibility=false;
      this.$scope.syllabusSelectBoxVisibility=true;
    }

    private editSaveAction():void {


      this.httpClient.put('academic/ssmap/'+this.$scope.single.id,this.$scope.single, 'application/json')
          .success(() => {
            $.notific8('Successfully Updated Mapping Information.');
          }).error((data) => {
          });
    }


  }
  UMS.controller('SemesterSyllabusMap', SemesterSyllabusMap);
}

