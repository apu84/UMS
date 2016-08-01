module ums{
  class ConfirmationDIalog implements ng.IDirective{
    constructor(){

    }
    public restrict:string='EA';
    public scope={
      recreate:'&deleteAndRecreate',
      cancelRecreation:'&cancelRecreation'
    };

    public link=($scope:any,element:JQuery,attributes:ng.IAttributes)=>{
      var currentScope=this;
      element.dialog({
        resizable:false,
        height:"auto",
        modal:true
        /*buttons:{
          "Delete All & Recreate":function(){
            console.debug("In the recreate %o", $scope.recreate);
            $scope.recreate.call();
            element.dialog("close");
            //currentScope.scope.recreate;
          },
          "Cancel":function(){
            $scope.cancelRecreation.call();
            element.dialog("close");
          }
        }*/
      });
    }
  }

  UMS.directive("confirmationDialog",()=>new ConfirmationDIalog())
}