///<reference path="../../../../../../../ums-web-core/src/main/ts/service/HttpClient.ts"/>
///<reference path="../../../../../../../ums-web-core/src/main/ts/lib/jquery.notific8.d.ts"/>
///<reference path="../../../../../../../ums-web-core/src/main/ts/lib/jquery.notify.d.ts"/>
///<reference path="../../../../../../../ums-web-core/src/main/ts/lib/jquery.jqGrid.d.ts"/>
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
    saveAll:Function;
    editDateTime:Function;
    fetchSavedRoutine:Function;
    loadingVisibility:boolean;
    routine: any;
    data: any;
    rowId: any;
    courseArr:Array<ICourse>;
  }
  interface IDateTime{
    readOnly: boolean;
    index: number;
    examDate: string;
    examTime: string;
    examGroup:number;
    programs: Array<IProgram>;
  }
  interface IProgram {
    readOnly: boolean;
    index:number;
    programId: number;
    programName:string;
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
    pairCourseId:string;
  }

  export class ExamRoutine {
    public static $inject = ['appConstants', 'HttpClient', '$scope', '$q'];

    constructor(private appConstants:any, private httpClient:HttpClient, private $scope:IExamRoutineScope, private $q:ng.IQService) {

      //localStorage.clear();
      $scope.data = {
        examTimeOptions: appConstants.examTime,
        ugPrograms: appConstants.ugPrograms
      };
      $scope.routine = {
        date_times: Array<IDateTime>(),
        semester:0,
        examType:0,
        addButtonDisable:true,
        saveButtonDisable:true
      };
      $scope.loadingVisibility=false;
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
      $scope.saveAll = this.saveAll.bind(this);
      $scope.editDateTime = this.editDateTime.bind(this);
      $scope.fetchSavedRoutine=this.fetchSavedRoutine.bind(this);

    }
    private fetchSavedRoutine():void{

      this.$scope.routine.date_times=new Array<IDateTime>();
      if(this.$scope.routine.semester==0 || this.$scope.routine.examType==0)
        return;

      this.$scope.loadingVisibility=true;
      this.getRoutine(this.$scope.routine.semester,this.$scope.routine.examType).then((dateTimeArr:Array<IDateTime>)=> {
        this.$scope.routine.date_times =dateTimeArr;
        this.$scope.loadingVisibility=false;
        this.$scope.routine.addButtonDisable=false;
        this.$scope.routine.saveButtonDisable=false;
      });
    }


    private getRoutine(semester_id:number,exam_type:number):ng.IPromise<any> {
      var defer = this.$q.defer();
      this.httpClient.get("academic/examroutine/semester/"+this.$scope.routine.semester+"/examtype/"+exam_type, this.appConstants.mimeTypeJson,
          (json:any, etag:string) => {
            var tempVar = json;
            //console.log('---temp var length---');
            //console.log(tempVar.entries[0]);
            if(tempVar.entries[0]==']'){
              var dateTimeArr:Array<IDateTime> = [];
            }
            else{
              var dateTimeArr:Array<IDateTime>=eval(json.entries);
            }
          //  console.log(dateTimeArr);
            defer.resolve(dateTimeArr);
          },
          (response:ng.IHttpPromiseCallbackArg<any>) => {
            console.error(response);
          });

      return defer.promise;
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

    private addNewCourse(date_time_row_obj:IDateTime, program_row_obj:IProgram):void {
      // console.log(date_time_index+"--"+program_index+"---");

      var date_time_Arr = eval(this.$scope.routine.date_times);
      var dateTimeTargetIndex = this.findIndex(date_time_Arr, date_time_row_obj.index);

      var program_Arr = eval(this.$scope.routine.date_times[dateTimeTargetIndex].programs);
      var programTargetIndex = this.findIndex(program_Arr, program_row_obj.index);

      var index = this.getAttributeMaxValueFromArray(this.$scope.routine.date_times[dateTimeTargetIndex].programs[programTargetIndex].courses);
      var courseRow = this.getNewCourseRow(index);
      this.$scope.routine.date_times[dateTimeTargetIndex].programs[programTargetIndex].courses.splice(0, 0, courseRow);

      //console.log("program_courses_"+program_row_obj.programId);


      if (localStorage.getItem("program_courses_"+program_row_obj.programId) != null) {
        var courseArr:Array<any>=JSON.parse(localStorage.getItem("program_courses_"+program_row_obj.programId) );
        this.$scope.routine.date_times[dateTimeTargetIndex].programs[programTargetIndex].courseArr =  courseArr;
        setTimeout(function () {
          $('#' + 'course_' + date_time_row_obj.index + program_row_obj.index + courseRow.index).select2({
            placeholder: "Select an option",
            allowClear: true
          });
        }, 10);
        return;
      }

      this.getCourseArr(program_row_obj.programId).then((courseResponse:any)=> {
        this.$scope.routine.date_times[dateTimeTargetIndex].programs[programTargetIndex].courseArr = courseResponse.courseArr;
        //localStorage["program_courses_"+program_row_obj.programId] = JSON.stringify(courseResponse.courseArr);
        setTimeout(function () {
          $('#' + 'course_' + date_time_row_obj.index + program_row_obj.index + courseRow.index).select2({
            placeholder: "Select an option",
            allowClear: true
          });
        }, 10);
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

    private programSelectionChanged(program_obj_row:IProgram,date_time:IDateTime):void {
//console.clear();
 //       program_obj_row.programId = $("#program_" + date_time.index + program_obj_row.index).val() == "?" ? null : Number($("#program_" + date_time.index + program_obj_row.index).val());
     // console.log("------------>>" + program_obj_row.programId);
  //    console.log(program_obj_row.programId );

      program_obj_row.programName=

      for (var ind in program_obj_row.courses) {
        var course:ICourse = program_obj_row.courses[ind];
        course.year = null;
        course.semester = null;
        course.title = null;
        course.no=null;
      }
      //program_obj_row.programId= $("#program_"+program_obj_row.index+date_time.index).val();
      program_obj_row.courseArr = null;

      this.getCourseArr(program_obj_row.programId,program_obj_row).then((courseResponse:any)=> {

        localStorage["program_courses_"+courseResponse.program.programId] = JSON.stringify(courseResponse.courseArr);
        program_obj_row.courseArr = courseResponse.courseArr;
        for (var ind in program_obj_row.courses) {
          //$("#" + program_obj_row.index + "select" + ind).select2("destroy").select2("val","");
          console.log("#########");
          $("#course_" + date_time.index+program_obj_row.index+ind).select2("destroy").select2("val","");

        }
      });

      setTimeout(function () {
        for (var ind in program_obj_row.courses) {
          $("#course_" + date_time.index+program_obj_row.index+ind).select2(
              {
                placeholder: "Select an option",
                allowClear: true
              });
        }
      }, 100);


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
        examGroup:1,
        programs: Array<IProgram>()
      }
      return dateTimeRow;
    }

    private getNewProgramRow(index:number) {
      var programRow:IProgram= {
        readOnly: true,
        index: index,
        programId: null,
        programName:'',
        courses: Array<ICourse>(),
        courseArr: Array<ICourse>()
      }
      return programRow;
    }

    private getNewCourseRow(index:number) {
      var courseRow = {
        readOnly: false,
        index: index,
        id: '',
        no: '',
        title: '',
        year: null,
        semester: null,
        pairCourseId:''
      }
      return courseRow;
    }




    private getCourseArr(program_id:number,program?:IProgram):ng.IPromise<any> {
      var defer = this.$q.defer();
      var courseArr:Array<any>;

      if(localStorage.getItem("program_courses_"+program_id)!=null){
        var courseArr:Array<any>=JSON.parse(localStorage.getItem("program_courses_"+program_id) );
        defer.resolve({program:program,courseArr:courseArr});
        return defer.promise;
      }
      this.httpClient.get('academic/course/semester/'+ this.$scope.routine.semester+'/program/' + program_id, 'application/json',
          (json:any, etag:string) => {
            courseArr = json.entries;
            this.$scope.courseArr = courseArr;
            localStorage["program_courses_"+program_id] = JSON.stringify(courseArr);
            defer.resolve({program:program,courseArr:courseArr});
          },
          (response:ng.IHttpPromiseCallbackArg<any>) => {
            console.error(response);
          });

      return defer.promise;
    }

    private courseSelectionChanged(program_row:IProgram,course_row:ICourse, selected_course_id:string) {
     // console.log(selected_course_id);

      var courseArr:Array<any>=JSON.parse(localStorage.getItem("program_courses_"+program_row.programId) );
      console.log("~~~~~~");
      console.log(courseArr);
      console.log(selected_course_id);
      var course:ICourse=this.arrayLookup(courseArr,'id',selected_course_id);
      console.log(course);
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
      if(validate==false){
        alert("Please correct data.");
        return;
      }
      var json:any = this.convertToJson([date_time],"byDateTime");
      this.saveRoutine(json).then((message:string)=> {
        $.notific8(message);
        this.readOnlyRow([date_time]);
      });

    }
    private saveByProgram(date_time:IDateTime, program:IProgram) {
      var dateTimeRow:IDateTime = {
        readOnly: false,
        index: date_time.index,
        examDate: date_time.examDate,
        examTime: date_time.examTime,
        examGroup:date_time.examGroup,
        programs: [program]
      }
      var validate:boolean = true;
      validate = this.validateExamRoutine([dateTimeRow]);
      if(validate==false){
        alert("Please correct data.");
        return;
      }
      var json:any = this.convertToJson([date_time],"byProgram");
      this.saveRoutine(json).then((message:string)=> {
        $.notific8(message);
        this.readOnlyRow([date_time]);
      });
    }

    private saveAll(){
      var validate:boolean = true;
      var validate = this.validateExamRoutine(this.$scope.routine.date_times);
      if(validate==false){
        alert("Please correct data.");
        return;
      }
      var json:any = this.convertToJson(this.$scope.routine.date_times,"all");
      this.saveRoutine(json).then((message:string)=> {
        $.notific8(message);
        this.readOnlyRow(this.$scope.routine.date_times);
      });
    }

    private saveRoutine(json:any):ng.IPromise<any> {
      var defer = this.$q.defer();
      this.httpClient.put('academic/examroutine/semester/'+ this.$scope.routine.semester+'/examtype/'+this.$scope.routine.examType, json, 'application/json')
          .success(() => {
            defer.resolve('Successfully Saved Exam Routine.');
          }).error((data) => {
          });
      return defer.promise;
    }

    private readOnlyRow(date_time_arr:Array<IDateTime>){
      for(var ind in date_time_arr){
        var date_time_row_obj:IDateTime =date_time_arr[ind];
        date_time_row_obj.readOnly=true;

      }
    }


    private convertToJson(dateTimeArr:Array<IDateTime>,insertType:string):any {
      var jsonObj = [];
      for (var indx_date_time in dateTimeArr) {
        //console.log("Row :" + indx_date_time);
        //console.log("----------------------");
        //console.log("Exam Date: " + dateTimeArr[indx_date_time].examDate);
        //console.log("Exam Time: " + dateTimeArr[indx_date_time].examTime);
        for (var indx_program in dateTimeArr[indx_date_time].programs) {
          var program:IProgram = dateTimeArr[indx_date_time].programs[indx_program];
          //console.log("~~~~~~~~~~~~~~~~");
          //console.log("Program : " + program.programId);
          for (var indx_course in program.courses) {
            var course:ICourse = program.courses[indx_course];
            //console.log("Course Id  :" + course.id);
            var item = {}
            item ["date"] = dateTimeArr[indx_date_time].examDate;
            item ["time"] = dateTimeArr[indx_date_time].examTime;
            item ["group"] = dateTimeArr[indx_date_time].examGroup;
            item ["program"] = Number(program.programId);
            item ["course"] = course.id;
            jsonObj.push(item);
          }
        }
      }

      var complete_json = {};
      complete_json["entries"] = jsonObj;
      complete_json["semesterId"] = this.$scope.routine.semester;
      complete_json["examType"] = this.$scope.routine.examType;
      complete_json["insertType"] = insertType;
      //console.log(complete_json);
      return complete_json;
    }


    private validateExamRoutine(dateTimeArr:Array<IDateTime>):boolean {
      var validate:boolean = true;
      for (var ind_date_time in dateTimeArr) {
        var dateTimeRow:IDateTime = dateTimeArr[ind_date_time];
        for (var ind_program in dateTimeRow.programs) {
          var programRow:IProgram = dateTimeArr[ind_date_time].programs[ind_program];
          if(programRow.programId==null)
              programRow.programId = $("#program_" + dateTimeRow.index + programRow.index).val().indexOf("?")>=0  ? null : $("#program_" + dateTimeRow.index + programRow.index).val();

          validate = validate && this.validateSingleRow(dateTimeRow) ;
        }
       // console.log(dateTimeRow);

//        validate = this.validateProgramDuplicate(routine,date_time, program) && validate;

      }
      return validate;
    }


    private validateSingleRow(dateTime:IDateTime):boolean {
      var validate:boolean = true;
      var indx_date_time:number = dateTime.index;
      validate=validate && this.validateFields("date", dateTime.examDate, indx_date_time, null, null);
      for (var ind in dateTime.programs) {
        var program:IProgram = dateTime.programs[ind];
        //this.validateFields("program", program.programId, indx_date_time, program.index, null);
        validate = validate && this.validateFields("program", program.programId, indx_date_time, program.index, null) ;
        if(program.courses.length==0) validate=false;
        for (var ind in program.courses) {
          var course:ICourse = program.courses[ind];
          validate = validate && this.validateFields("year", course.year, indx_date_time, program.index, course.index) ;
          validate = validate && this.validateFields("semester", course.semester, indx_date_time, program.index, course.index) ;
          validate = validate && this.validateFields("course", course.id, indx_date_time, program.index, course.index) ;
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
          validate = false;
        }
        else {
          this.putOkColor(element);
          validate = true;
        }
      }
      else if (field_prefix == "program") {
        element = $("#" + field_prefix + "_" + indx_date_time + indx_program);
        if (value == null) {
          this.putKoColor(element);
          validate =  false;
        }
        else {
          this.putOkColor(element);
          validate =  true;
        }

      }
      else if (field_prefix == "year") {
      //  console.log(field_prefix + "_" + indx_date_time + indx_program + indx_course);
        element = $("#" + field_prefix + "_" + indx_date_time + indx_program + indx_course);
        if (value == null) {
          this.putKoColor(element);
          validate = false;
        }
        else {
          this.putOkColor(element);
          validate =  true;
        }
      }
      else if (field_prefix == "semester") {
        element = $("#" + field_prefix + "_" + indx_date_time + indx_program + indx_course);
        if (value == null) {
          this.putKoColor(element);
          validate = false;
        }
        else {
          this.putOkColor(element);
          validate = true;
        }
      }
      else if (field_prefix == "course") {
        element = $("#" + field_prefix + "_" + indx_date_time + indx_program + indx_course);
        if (value == "") {
          this.putKoColor(element);
          validate =  false;
        }
        else {
          this.putOkColor(element);
          validate = true;
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

     this.showOverlay(date_time_row_obj.index);
      date_time_row_obj.readOnly = false;
      var that=this;

      for (var ind in date_time_row_obj.programs) {
        var program:IProgram = date_time_row_obj.programs[ind];
        //console.log(program);
        this.getCourseArr(program.programId,program).then((courseResponse:any)=> {
          //console.log("---inside the thief---");
          //console.log(courseResponse);
          //console.log( courseResponse.program);
          var courseArr:Array<ICourse>=courseResponse.courseArr;


          courseResponse.program.courseArr = courseArr;
          //program.courseArr = this.$scope.courseArr;
         // console.log(program.courseArr);
         // console.log(courseArr.length);


        });

      }
      setTimeout(function(){that.setSelect2Courses(date_time_row_obj);},1000);


//alert($("#program_10").val());
    }

    private setSelect2Courses(date_time_row_obj:IDateTime):void {
      for (var ind1 in date_time_row_obj.programs) {
        var program:IProgram = date_time_row_obj.programs[ind1];
        console.log(program);
        $("#program_"+date_time_row_obj.index+program.index).val(program.programId+'');
        for (var ind2 in program.courses) {
          var course:ICourse =program.courses[ind2];
          //  console.log("#course_" + date_time_row_obj.index + program.index + course.index); /////alsdkfja
          $("#course_" + date_time_row_obj.index + program.index + course.index).select2().select2('val',course.id);
        }
      }
     this.hideOverlay(date_time_row_obj);

    }
    private showOverlay(rowIndex:number):void{
      var $divOverlay = $('#divOverlay');
      var bottomWidth = $("#row"+rowIndex).css('width');
      var bottomHeight = $("#row"+rowIndex).css('height');
      var rowPos = $("#row"+rowIndex).position();
      var bottomTop = rowPos.top;
      var bottomLeft = rowPos.left;
      $divOverlay.css({
        position: 'absolute',
        top: bottomTop,
        left: bottomLeft,
        width: '95%',
        height: bottomHeight
      });
      $('#info').text('Top: ' + bottomTop + ' Left: ' + bottomLeft);
      $divOverlay.delay(100).slideDown('fast');
    }

    private hideOverlay(date_time_row_obj:any):void{
      var $divOverlay = $('#divOverlay');
      $divOverlay.hide(100);
     // date_time_row_obj.readOnly=true;
    }
  }
  UMS.controller('ExamRoutine', ExamRoutine);
}
