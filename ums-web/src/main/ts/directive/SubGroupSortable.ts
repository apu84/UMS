module ums{
  export class SubGroupSortable implements ng.IDirective{
    constructor(){

    }

    public restrict:string="A";
    public scope={
      tempGroupList:"="
    };

    public link = (scope:any, element:JQuery, attributes:any)=>{
        element.find('ul').sortable();
    };
    public templateUrl:string = "./views/directive/sub-group-sortable.html";
  }

  UMS.directive("subGroupSortable",()=>new SubGroupSortable());
}