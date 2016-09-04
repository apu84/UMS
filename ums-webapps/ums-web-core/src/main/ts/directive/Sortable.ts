module ums{
  import ITimeoutService = ng.ITimeoutService;
  export interface SortableScope extends ng.IScope{
    seatPlanGroup:Array<ISeatPlanGroup>;
    connectWith:any;
    tempGroupList:any;
    subGroupListChanged:Function;
    subGroupNumber:number;
    items:any;

    initialize:Function;
  }

  interface ISeatPlanGroup{
    id:any;
    semesterId:number;
    programId:number;
    programName:string;
    year:number;
    semester:number;
    groupNo:number;
    type:number;
    studentNumber:number;
    lastUpdated:string;
    showSubPortion:boolean;
    splitOccuranceNumber:number;
    subGroupNumber:number;
    baseId:number;
  }

/*  export class SortableDirective{
    public static $inject = ['appConstants','HttpClient','$scope','$q','notify','$timeout','$sce','$window'];
    constructor(private appConstants: any, private httpClient: HttpClient, private $scope: SortableScope,
                private $q:ng.IQService, private notify: Notify,private $timeout:ITimeoutService,
                private $sce:ng.ISCEService,private $window:ng.IWindowService) {

      $scope.seatPlanGroup=[];
      $scope.initialize = this.initialize.bind(this);
    }

    private initialize():void{

      var currentScope = this;

      $("#sortable9").sortable({
        connectWith:".connectedSortable"
      }).disableSelection();

      $('#sortable9').sortable({
        cursor: "move",
        connectWith:".connectedSortable",
        items:"> li",
        over:function(event,ui){
          $("#sortable9").css("background-color","aqua");
        },
        receive:function(event,ui){
          $("#sortable9").css("background-color","antiquewhite");
        },

        out:function(event,ui){
          $("#sortable9").css("background-color","antiquewhite");
        },
        update:function(event,ui){
          var result = $(this).sortable('toArray');
          this.subGroupListChanged()(this.subGroupNumber,result);
        },

        change:function(event,ui){

        }
      });
    }

  }*/

  export class Sortable implements ng.IDirective{
    public scope={
      seatPlanGroup:'=',
      connectWith:'=',
      subGroupListChanged:'&',
      subGroupNumber:'='
    };

    public templateUrl:string ="./views/directive/sub-group-sortable.html";

    //public controller = SortableDirective;



    public link = ($scope:SortableScope, element: any, attrs:any)=>{


      console.log("~~~~~ inside directive ~~~~~~~");

      console.log(attrs.seatPlanGroup);
      $scope.seatPlanGroup = attrs.seatPlanGroup;
      console.log($scope.seatPlanGroup);

      $scope.$watchCollection('seatPlanGroup',makeSortable);

      function makeSortable(){
        $("#sortable9").sortable({
          connectWith:".connectedSortable"
        }).disableSelection();

        $('#sortable9').sortable({
          cursor: "move",
          connectWith:".connectedSortable",
          items:"> li",
          over:function(event,ui){
            $("#sortable9").css("background-color","aqua");
          },
          receive:function(event,ui){
            $("#sortable9").css("background-color","antiquewhite");
          },

          out:function(event,ui){
            $("#sortable9").css("background-color","antiquewhite");
          },
          update:function(event,ui){
            var result = $(this).sortable('toArray');
            this.subGroupListChanged()(this.subGroupNumber,result);
          },

          change:function(event,ui){

          }
        });
      }


    }
  }

  UMS.directive("sortableSeatPlan", () => new Sortable());
}