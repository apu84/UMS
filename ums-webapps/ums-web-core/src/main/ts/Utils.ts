module ums {
  export class Utils {

    static APPROVED : string = "#FFFFCC";
    static APPLICATION : string = "#CCFFCC";
    static APPROVED_APPLICATION : string = "#E0FFFF";
    static NONE : string = "none";
    static UG : number =11;
    static PG : number =22;
    static DEFAULT_SEMESTER_COUNT: number = 10;

    static SHORT_MONTH_ARR = new Array("Jan", "Feb", "Mar",
        "Apr", "May", "Jun", "Jul", "Aug", "Sep",
        "Oct", "Nov", "Dec");

    /*
      *Status code for Optional Course Status Fields
      * Database Table: Optional_Course_Application
      * Field: Status
     */
    static SCODE_APPLIED:number = 0;
    static SCODE_APPROVED:number = 1;
    static SCODE_REJECTED:number = 2;
    static SCODE_REJECTED_AND_SHIFTED : number = 3;

    /**
     * Exam Type
     * Regular 1
     * CCI 2
     */
    static EXAM_TYPE_REGULAR:number = 1;
    static EXAM_TYPE_CCI: number =2;
    /*
    * Course Type*/
    static COURSE_TYPE_THEORY:number=1;
    static COURSE_TYPE_SESSIONAL:number=2;
    /**
     * Semester Status
     */
    static SEMESTER_STATUS_INACTIVE: number=0;
    static SEMESTER_STATUS_ACTIVE: number=1;
    static SEMESTER_STATUS_NEWLY_CREATED: number=2;
    static SEMESTER_FETCH_ALL: number=3;
    static SEMESTER_FETCH_WITH_NEWLY_CREATED: number=4;

    /*
    * Present status during department selection
    *
    * */
    static ABSENT:number=0;
    static PRESENT:number=1;

    /*
    * Department selection type
    * */
    static MERIT_PROGRAM_SELECTED=1;
    static MERIT_WAITING_PROGRAMS_SELECTED=2;
    static WAITING_PROGRAM_SELECTED=3;

    /*
    * Migration status*/
    static NOT_MIGRATED=1;
    static MIGRATION_ABLE=2;
    static MIGRATED=2;

    /*
    * Quota type for admission*/
    static GENERAL=1;
    static FREEDOM_FIGHTER=2;
    static REMOTE_AREA=3;
    static ENGLISH_MEDIUM=4;

    /*
    *Payment Types
    * */
    static ADMISSION_FEE=1;
    static MIGRATION_FEE=2;

    /*
    * Payment Modes*/
    static CASH=1;
    static DEMAND_NOTE=2;
    static PAY_ORDER=3;

    /**
     * Student Id block colors (Advisor Assignment from Admin Officer Account)
     */
    static DEFAULT_COLOR: string="#DEF";
    static SELECTED_COLOR: string="#FADBD8";

    /**
     * Grade Submission Yellow Error Row Color
     */
    static ERROR_ROW: string="#FCDC3B";
    static SEARCH_ROW: string="#A2D9CE";







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

    /*
    This is how you should use this function.
     var index = findIndexByKeyValue(studentsArray, "name", "tanmay");
     alert(index);
     */
    public static  findIndexByProperty(arrayToSearch:Array<any>, property, valueToSearch) {
    for (var i = 0; i < arrayToSearch.length; i++) {
      if (arrayToSearch[i][property] == valueToSearch) {
        return i;
      }
    }
    return null;
  }

    public static  arrayMaxIndex(array:Array<any>):number {
      var val:number = 0;
      if (array.length != 0)
        val = Math.max.apply(Math, array.map(function (o) {
          return o.index;
        })) + 1;
      return val;
    }

    public static expandRightDiv(){
      $("#leftDiv").hide(100);
      $("#arrowDiv").show(50);
    }

    public static setValidationOptions(formClass:string):void{
      $("."+formClass).validate({
        errorPlacement: function(error, element){
          error.insertAfter(element);
        },
        unhighlight: function(element, errorClass) {
          var $element = $(element);
          $element.siblings(".error_status").removeClass("check");
        },
        success: function(label,element) {
          label.parent().removeClass('state-error');
          label.remove();
        }
      });
    }



  }
}