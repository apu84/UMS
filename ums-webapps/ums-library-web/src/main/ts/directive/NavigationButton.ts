module ums {
  export class NavigationButton implements ng.IDirective {

    constructor() {
    }

    public restrict:string = "E";
    public scope = {
      callbacks : '=',
      defaultselected : '='
    };
    public iconsArr = Array<String>();
    public idsArr  = Array<String>();
    public containersArr  = Array<String>();


    public link = (scope:any, element:JQuery, attributes:any) => {

      scope.iObjects  = new Array<string>();

      this.iconsArr = attributes.icons.split(",");
      this.idsArr = attributes.ids.split(",");
      this.containersArr = attributes.containers.split(",");


      scope.manageUI = function(navId : string){
        this.idsArr = attributes.ids.split(",");
        for(var i=0; i < this.idsArr.length; i++) {
          $("#"+ this.idsArr[i]).css({"background-color": "white"});
          $("#"+ this.idsArr[i]+" > i").css({"color": "black"});

          $("#container_"+ this.idsArr[i]).hide();
        }

        $("#"+ navId).css({"background-color": "black"});
        $("#"+ navId+" > i").css({"color": "white"});
        $("#container_"+ navId).show();
      }


      for(var i=0; i < this.iconsArr.length; i++) {
        var obj = {"icon" : this.iconsArr[i], "id" :  this.idsArr[i], "callback": scope.callbacks == undefined || scope.callbacks.length != this.iconsArr.length ? {} : scope.callbacks[i]}
        scope.iObjects.push(obj);
      }

      // console.log($("#"+ scope.defaultselected));
      // $("#"+ scope.defaultselected).css({"background-color": "black"});
      // $("#"+ scope.defaultselected+" > i").css({"color": "white"});
    };

    public templateUrl:string = "./views/directive/navigation-buttons.html";
  }

  UMS.directive("navigationButton", () => new NavigationButton());
}
