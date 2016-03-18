module ums {
  export class Utils {

    static APPROVED : string = "#FFFFCC";
    static APPLICATION : string = "#CCFFCC";
    static APPROVED_APPLICATION : string = "#E0FFFF";
    static NONE : string = "none";

    public static findIndex(source_arr:Array<any>, element_value:string):number {
      var targetIndex = -1;
      for (var i = 0; i < source_arr.length; i++) {
        if (source_arr[i].id == element_value) {
          targetIndex = i;
          break;
        }
      }
      return targetIndex;
    }
    public static  arrayMaxIndex(array:Array<any>):number {
      var val:number = 0;
      if (array.length != 0)
        val = Math.max.apply(Math, array.map(function (o) {
          return o.index;
        })) + 1;
      return val;
    }

  }
}