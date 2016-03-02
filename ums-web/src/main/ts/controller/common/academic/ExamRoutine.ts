///<reference path="../../../service/HttpClient.ts"/>
///<reference path="../../../lib/jquery.notific8.d.ts"/>
///<reference path="../../../lib/jquery.notify.d.ts"/>
///<reference path="../../../lib/jquery.jqGrid.d.ts"/>
module ums {

  interface IExamRoutineScope extends ng.IScope {
    addNewDateTime: Function;
    removeDateTime: Function;
    addNewProgram: Function;
    removeProgram: Function;
    addNewCourse: Function;
    removeCourse: Function;
    programSelectionChanged: Function;
    courseSelectionChanged:Function;
    saveByProgram:Function;
    saveByDateTime:Function;
    editDateTime:Function;
    routine: any;
    data: any;
    rowId: any;
  }
  interface IDateTime{
    readOnly: boolean;
    index: number;
    examDate: string;
    examTime: string;
    programs: Array<IProgram>;
  }
  interface IProgram {
    readOnly: boolean;
    index:number;
    programId: number;
    courses : Array<ICourse>;
    courseArr: Array<ICourse>;
  }
  interface ICourse {
    readOnly: boolean;
    index: number;
    id: string;
    no: string;
    title: string;
    year: number;
    semester: number;
  }

  export class ExamRoutine {
    public static $inject = ['appConstants', 'HttpClient', '$scope', '$q'];

    constructor(private appConstants:any, private httpClient:HttpClient, private $scope:IExamRoutineScope, private $q:ng.IQService) {

      $scope.data = {
        examTimeOptions: appConstants.examTime,
        ugPrograms: appConstants.ugPrograms
      };
      $scope.routine = {
        date_times: Array<IDateTime>()
      };

      $scope.routine.date_times = [{"index":0,"examDate":"01/03/2016","examTime":"9:30 a.m. to 12:30 p.m","readOnly":true,"programs":[{"index":0,"programId":"110500","programName":"BSC in EEE","readOnly":true,"courses":[{"index":0,"id":"EEE2206_S2014_110500","no":"EEE 2206","title":"Energy Conversion II Lab","year":2,"semester":"2","readOnly":true}]}]},{"index":1,"examDate":"22/02/2016","examTime":"9:30 a.m. to 12:30 p.m","readOnly":true,"programs":[{"index":0,"programId":"110500","programName":"BSC in EEE","readOnly":true,"courses":[{"index":0,"id":"ME1101_S2014_110500","no":"ME 1101","title":"Mechanical Engineering Fundamentals","year":1,"semester":"1","readOnly":true},{"index":1,"id":"ME1102_S2014_110500","no":"ME 1102","title":"Mechanical Engineering Fundamentals Lab","year":1,"semester":"1","readOnly":true}]}]},{"index":2,"examDate":"23/02/2016","examTime":"9:30 a.m. to 12:30 p.m","readOnly":true,"programs":[{"index":0,"programId":"110400","programName":"BSC in CSE","readOnly":true,"courses":[{"index":0,"id":"CSE1200_F2009_110400","no":"CSE 1200","title":"Software Development-I","year":1,"semester":"2","readOnly":true}]},{"index":1,"programId":"110500","programName":"BSC in EEE","readOnly":true,"courses":[{"index":0,"id":"MATH1103_S2014_110500","no":"MATH 1103","title":"Mathematics I","year":1,"semester":"1","readOnly":true},{"index":1,"id":"MATH1103_S2014_110500","no":"MATH 1103","title":"Mathematics I","year":1,"semester":"1","readOnly":true},{"index":2,"id":"ME1101_S2014_110500","no":"ME 1101","title":"Mechanical Engineering Fundamentals","year":1,"semester":"1","readOnly":true}]}]},{"index":3,"examDate":"29/02/2016","examTime":"9:30 a.m. to 12:30 p.m","readOnly":true,"programs":[{"index":0,"programId":"110500","programName":"BSC in EEE","readOnly":true,"courses":[{"index":0,"id":"ME1102_S2014_110500","no":"ME 1102","title":"Mechanical Engineering Fundamentals Lab","year":1,"semester":"1","readOnly":true}]}]}];


      $scope.addNewDateTime = this.addNewDateTime.bind(this);
      $scope.removeDateTime = this.removeDateTime.bind(this);
      $scope.addNewProgram = this.addNewProgram.bind(this);
      $scope.removeProgram = this.removeProgram.bind(this);

      $scope.addNewCourse = this.addNewCourse.bind(this);
      $scope.removeCourse = this.removeCourse.bind(this);

      $scope.programSelectionChanged = this.programSelectionChanged.bind(this);
      $scope.courseSelectionChanged = this.courseSelectionChanged.bind(this);
      $scope.saveByProgram = this.saveByProgram.bind(this);
      $scope.saveByDateTime = this.saveByDateTime.bind(this);
      $scope.editDateTime = this.editDateTime.bind(this);


    }

    private addNewDateTime():void {
      var index = this.getAttributeMaxValueFromArray(this.$scope.routine.date_times);
      var item = this.getNewDateTimeRow(index);
      this.$scope.routine.date_times.splice(0, 0, item);

      setTimeout(function () {
        $('.datepicker-default').datepicker();
        $('.datepicker-default').on('change', function () {
          $('.datepicker').hide();
        });
      }, 200);


    }

    private removeDateTime(index:number):void {
      var date_time_Arr = eval(this.$scope.routine.date_times);
      var targetIndex = this.findIndex(date_time_Arr, index);
      this.$scope.routine.date_times.splice(targetIndex, 1);
    }

    private addNewProgram(index:number):void {
      var date_time_Arr = eval(this.$scope.routine.date_times);
      var targetIndex = this.findIndex(date_time_Arr, index);
      var index = this.getAttributeMaxValueFromArray(this.$scope.routine.date_times[targetIndex].programs);
      var item = this.getNewProgramRow(index);
      this.$scope.routine.date_times[targetIndex].programs.splice(0, 0, item);
    }

    private removeProgram(date_time_index:number, program_index:number):void {
      var date_time_Arr = eval(this.$scope.routine.date_times);
      var dateTimeTargetIndex = this.findIndex(date_time_Arr, date_time_index);
      var program_Arr = eval(this.$scope.routine.date_times[dateTimeTargetIndex].programs);
      var programTargetIndex = this.findIndex(program_Arr, program_index);
      this.$scope.routine.date_times[dateTimeTargetIndex].programs.splice(programTargetIndex, 1);
    }

    private addNewCourse(date_time_index:number, program_index:number, program_id:number):void {
      // console.log(date_time_index+"--"+program_index+"---");

      var date_time_Arr = eval(this.$scope.routine.date_times);
      var dateTimeTargetIndex = this.findIndex(date_time_Arr, date_time_index);

      var program_Arr = eval(this.$scope.routine.date_times[dateTimeTargetIndex].programs);
      var programTargetIndex = this.findIndex(program_Arr, program_index);

      var index = this.getAttributeMaxValueFromArray(this.$scope.routine.date_times[dateTimeTargetIndex].programs[programTargetIndex].courses);
      var courseRow = this.getNewCourseRow(index);
      this.$scope.routine.date_times[dateTimeTargetIndex].programs[programTargetIndex].courses.splice(0, 0, courseRow);

      this.getCourseArr(program_id).then((courseArr:Array<ICourse>)=> {
        this.$scope.routine.date_times[dateTimeTargetIndex].programs[programTargetIndex].courseArr = courseArr;


        setTimeout(function () {
          $('#' + 'course_' + date_time_index + program_index + courseRow.index).select2({
            placeholder: "Select an option",
            allowClear: true
          });
        }, 50);
      });

    }

    private removeCourse(date_time_index:number, program_index:number, course_index:number):void {
      var date_time_Arr = eval(this.$scope.routine.date_times);
      var dateTimeTargetIndex = this.findIndex(date_time_Arr, date_time_index);

      var program_Arr = eval(this.$scope.routine.date_times[dateTimeTargetIndex].programs);
      var programTargetIndex = this.findIndex(program_Arr, program_index);

      var course_Arr = eval(this.$scope.routine.date_times[dateTimeTargetIndex].programs[programTargetIndex].courses);
      var courseTargetIndex = this.findIndex(course_Arr, course_index);
      this.$scope.routine.date_times[dateTimeTargetIndex].programs[programTargetIndex].courses.splice(courseTargetIndex, 1);
    }

    private programSelectionChanged(program_obj_row:IProgram, date_time:IDateTime):void {
      console.log("------------>>" + program_obj_row.programId);
      console.log(program_obj_row);
      for (var ind in program_obj_row.courses) {
        var course:ICourse = program_obj_row.courses[ind];
        course.year = null;
        course.semester = null;
        course.title = null;
      }
      //program_obj_row.programId= $("#program_"+program_obj_row.index+date_time.index).val();
      program_obj_row.courseArr = null;

      this.getCourseArr(program_obj_row.programId).then((courseArr:Array<ICourse>)=> {
        program_obj_row.courseArr = courseArr;
        for (var ind in program_obj_row.courses) {
          $("#" + program_obj_row.index + "select" + ind).select2("destroy").select2();
        }
      });


    }


    private findIndex(source_arr:Array<any>, target_index:number):number {
      var targetIndex = -1;
      for (var i = 0; i < source_arr.length; i++) {
        if (source_arr[i].index == target_index) {
          targetIndex = i;
          break;
        }
      }
      return targetIndex;
    }

    private getNewDateTimeRow(index:number) {
      var dateTimeRow:IDateTime = {
        readOnly: false,
        index: index,
        examDate: '',
        examTime: '9:30 a.m. to 12:30 p.m',
        programs: Array<IProgram>()
      }
      return dateTimeRow;
    }

    private getNewProgramRow(index:number) {
      var programRow:IProgram = {
        readOnly: false,
        index: index,
        programId: null,
        courses: Array<ICourse>(),
        courseArr: Array<ICourse>()
      }
      return programRow;
    }

    private getNewCourseRow(index:number) {
      var courseRow:ICourse = {
        readOnly: false,
        index: index,
        id: '',
        no: '',
        title: '',
        year: null,
        semester: null
      }
      return courseRow;
    }

    private getCourseArr(program_id:number):ng.IPromise<any> {
      var defer = this.$q.defer();
      var courseArr:Array<any>;
      this.httpClient.get('academic/course/semester/11012015/program/' + program_id, 'application/json',
          (json:any, etag:string) => {
            courseArr = json.entries;
            defer.resolve(courseArr);
          },
          (response:ng.IHttpPromiseCallbackArg<any>) => {
            console.error(response);
          });

      return defer.promise;
    }

    private courseSelectionChanged(program_row:IProgram,course_row:ICourse, selected_course_id:string) {
      console.log(selected_course_id);
      var course:ICourse=this.arrayLookup(program_row.courseArr,'id',selected_course_id);
      course_row.year = course.year;
      course_row.semester = course.semester;
      course_row.title = course.title;
      course_row.id = course.id;
      course_row.no = course.no;
    }
    private arrayLookup(array, prop, val):any {
    for (var i = 0, len = array.length; i < len; i++) {
      if (array[i].hasOwnProperty(prop) && array[i][prop] === val) {
        return array[i];
      }
    }
    return null;
  }
    private saveByDateTime(date_time:IDateTime) {
      var validate:boolean = true;
      validate = this.validateExamRoutine([date_time]);
      var json:any = this.convertToJson([date_time]);
      this.httpClient.put('academic/examroutine/semester/1/examtype/1', json, 'application/json')
          .success(() => {
            //$.notific8('Successfully Updated Mapping Information.');
            alert("abc");
          }).error((data) => {
          });

    }
    private convertToJson(dateTimeArr:Array<IDateTime>):any {
      var jsonObj = [];
      for (var indx_date_time in dateTimeArr) {
        console.log("Row :" + indx_date_time);
        console.log("----------------------");
        console.log("Exam Date: " + dateTimeArr[indx_date_time].examDate);
        console.log("Exam Time: " + dateTimeArr[indx_date_time].examTime);
        for (var indx_program in dateTimeArr[indx_date_time].programs) {
          var program:IProgram = dateTimeArr[indx_date_time].programs[indx_program];
          console.log("~~~~~~~~~~~~~~~~");
          console.log("Program : " + program.programId);
          for (var indx_course in program.courses) {
            var course:ICourse = program.courses[indx_course];
            console.log("Course Id  :" + course.id);
            var item = {}
            item ["date"] = dateTimeArr[indx_date_time].examDate;
            item ["time"] = dateTimeArr[indx_date_time].examTime;
            item ["program"] = program.programId;
            item ["course"] = course.id;
            jsonObj.push(item);
          }
        }
      }

      var complete_json = {};
      complete_json["entries"] = jsonObj;
      complete_json["semesterId"] = "11012015";
      complete_json["examType"] = "1";
      complete_json["insertType"] = "date-time";
      console.log(complete_json);
      return complete_json;
    }

    private saveByProgram(date_time:IDateTime, program:IProgram) {
      //program.programId=$("#program_"+date_time.index+program.index).val()=="? undefined:undefined ?"?null:$("#program_"+date_time.index+program.index).val();

      var dateTimeRow:IDateTime = {
        readOnly: false,
        index: date_time.index,
        examDate: date_time.examDate,
        examTime: date_time.examTime,
        programs: [program]
      }


      var validate:boolean = true;
      validate = this.validateExamRoutine([dateTimeRow]);


      /*
       this.httpClient.put('academic/examroutine/semester/1/examtype/2',this.$scope.routine, 'application/json')
       .success(() => {
       //$.notific8('Successfully Updated Mapping Information.');
       alert("abc");
       }).error((data) => {
       });
       */
    }

    private validateExamRoutine(dateTimeArr:Array<IDateTime>) {

      var validate:boolean = true;
      for (var ind_date_time in dateTimeArr) {
        var dateTimeRow:IDateTime = dateTimeArr[ind_date_time];
        for (var ind_program in dateTimeRow.programs) {
          var programRow:IProgram = dateTimeArr[ind_date_time].programs[ind_program];
          programRow.programId = $("#program_" + dateTimeRow.index + programRow.index).val() == "? undefined:undefined ?" ? null : $("#program_" + dateTimeRow.index + programRow.index).val();
        }
        console.log(dateTimeRow);
        validate = this.validateSingleRow(dateTimeRow) && validate;
//        validate = this.validateProgramDuplicate(routine,date_time, program) && validate;

      }

      return validate;
    }


    private validateSingleRow(dateTime:IDateTime):boolean {
      var validate:boolean = true;
      var indx_date_time:number = dateTime.index;
      this.validateFields("date", dateTime.examDate, indx_date_time, null, null);
      for (var ind in dateTime.programs) {
        var program:IProgram = dateTime.programs[ind];
        this.validateFields("program", program.programId, indx_date_time, program.index, null);
        validate = this.validateFields("program", program.programId, indx_date_time, program.index, null) && validate;
        for (var ind in program.courses) {
          var course:ICourse = program.courses[ind];
          validate = this.validateFields("year", course.year, indx_date_time, program.index, course.index) && validate;
          validate = this.validateFields("semester", course.semester, indx_date_time, program.index, course.index) && validate;
          validate = this.validateFields("course", course.id, indx_date_time, program.index, course.index) && validate;
        }
        return validate;
      }
    }

    /*
     private validateProgramFields(program:IProgram,indx_date_time:number):boolean {
     var validate:boolean = true;
     this.validateFields("program", program.programId, indx_date_time, program.index, null);
     validate =  this.validateFields("program", program.programId, indx_date_time, program.index, null) && validate;
     for (var ind in program.courses) {
     var course:ICourse = program.courses[ind];
     validate =  this.validateFields("year", course.year, indx_date_time, program.index, course.index) && validate;
     validate = this.validateFields("semester", course.semester, indx_date_time, program.index, course.index) && validate;
     validate = this.validateFields("course", course.id, indx_date_time, program.index, course.index) && validate;
     }
     return validate;
     }
     */
    private validateFields(field_prefix:string, value:any, indx_date_time:number, indx_program:number, indx_course:number):boolean {
      var element:any;
      var validate:boolean = true;
      if (field_prefix == "date") {
        element = $("#" + field_prefix + "_" + indx_date_time);
        if (value == "") {
          this.putKoColor(element);
          validate = validate && false;
        }
        else {
          this.putOkColor(element);
          validate = validate && true;
        }
      }
      else if (field_prefix == "program") {
        element = $("#" + field_prefix + "_" + indx_date_time + indx_program);
        if (value == null) {
          this.putKoColor(element);
          validate = validate && false;
        }
        else {
          this.putOkColor(element);
          validate = validate && true;
        }
      }
      else if (field_prefix == "year") {
        console.log(field_prefix + "_" + indx_date_time + indx_program + indx_course);
        element = $("#" + field_prefix + "_" + indx_date_time + indx_program + indx_course);
        if (value == null) {
          this.putKoColor(element);
          validate = validate && false;
        }
        else {
          this.putOkColor(element);
          validate = validate && true;
        }
      }
      else if (field_prefix == "semester") {
        element = $("#" + field_prefix + "_" + indx_date_time + indx_program + indx_course);
        if (value == null) {
          this.putKoColor(element);
          validate = validate && false;
        }
        else {
          this.putOkColor(element);
          validate = validate && true;
        }
      }
      else if (field_prefix == "course") {
        element = $("#" + field_prefix + "_" + indx_date_time + indx_program + indx_course);
        if (value == "") {
          this.putKoColor(element);
          validate = validate && false;
        }
        else {
          this.putOkColor(element);
          validate = validate && true;
        }
      }

      return validate;
    }

    private putOkColor(element:any) {
      element.parent().css("background-color", "transparent");
    }

    private putKoColor(element:any) {
      element.parent().css("background-color", "#E6A9EC");
    }

    private getAttributeMaxValueFromArray(array:Array<any>):number {
      var val:number = 0;
      if (array.length != 0)
        val = Math.max.apply(Math, array.map(function (o) {
          return o.index;
        })) + 1;
      return val;
    }

    private validateProgramDuplicate(routine:any, date_time:IDateTime, program:IProgram) {
      for (var ind1 in this.$scope.routine.date_times) {
        var dateTime:IDateTime = this.$scope.routine.date_times[ind1];
        for (var ind2 in dateTime.programs) {
          //alert(dateTime.programs[ind1].programId+"-----"+program.programId+"@@@"+dateTime.examDate+"-----"+routine.examDate+"@@@"+dateTime.examTime+"-----"+routine.examTime+"@@@"+dateTime.index+"-----"+routine.index);
          if (dateTime.programs[ind2].programId == program.programId && dateTime.examDate == routine.examDate && dateTime.examTime == routine.examTime) {
            if (dateTime.index == routine.index) {
              if (dateTime.programs[ind2].index == routine.programs[0].index) {
                this.putOkColor($("#program" + dateTime.index + dateTime.programs[ind2].index));
                continue;
              }
            }
            this.putKoColor($("#program" + dateTime.index + dateTime.programs[ind2].index));
            this.putKoColor($("#program" + dateTime.index + program.index));
            alert("duplicate program name in same date found.");
            return;
          }
          else {
            this.putOkColor($("#program" + dateTime.index + dateTime.programs[ind2].index));
          }
        }
      }
    }

    private editDateTime(date_time_row_obj:IDateTime):void {
      date_time_row_obj.readOnly = false;
      for (var ind in date_time_row_obj.programs) {
        var program:IProgram = date_time_row_obj.programs[ind];

        this.getCourseArr(program.programId).then((courseArr:Array<ICourse>)=> {
          program.courseArr = courseArr;


        });

      }

setTimeout(function() {
      for (var ind1 in date_time_row_obj.programs) {
        var program:IProgram = date_time_row_obj.programs[ind];
      for (var ind2 in program.courses) {
          var course:ICourse =program.courses[ind2];
          $("#course_" + date_time_row_obj.index + program.index + course.index).select2().select2('val',course.id);
        }
      }
},
1000);

    }
  }
  UMS.controller('ExamRoutine', ExamRoutine);
}

