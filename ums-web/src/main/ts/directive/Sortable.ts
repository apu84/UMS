module ums{
  export interface SortableScope extends ng.IScope{
    seatPlanGroup:any;
    connectWith:any;
    subGroupListChanged:Function;
    subGroupNumber:number;
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

  export class Sortable implements ng.IDirective{
    public scope={
      seatPlanGroup:'=',
      connectWith:'=',
      subGroupListChanged:'&',
      subGroupNumber:'='
    };

  public templateUrl:string ="./views/directive/sub-group-sortable.html";

    public link = ($scope:SortableScope, element: any, attrs:any)=>{


      //element.click(alert("YOu Clicked me!"));

      console.log("~~~~~ inside directive ~~~~~~~");
      console.log($scope.seatPlanGroup);

      $("#sortable9").sortable({
          connectWith:".connectedSortable"
      }).disableSelection();

      $('#sortable9').sortable({
        cursor: "move",
        connectWith:".connectedSortable",
        items:"> li",
        over:function(event,ui){
          $("#droppable1").css("background-color","aqua");
        },
        receive:function(event,ui){
          $("#droppable1").css("background-color","antiquewhite");
        },

        out:function(event,ui){
          $("#droppable1").css("background-color","antiquewhite");
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

  UMS.directive("uiSortable", () => new Sortable());
}