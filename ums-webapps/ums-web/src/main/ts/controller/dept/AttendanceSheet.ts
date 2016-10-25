module ums {
  interface IAttr extends ng.IScope {
    test123:Function;
    insertColo:Function;
    loadingVisibility:any;
    contentVisibility:any;
    data:any;
    attendanceSearchParamModel:any;
    showAttendanceSheet:Function;
    showCalendar:Function;
    setDate:Function;
    operation:Function;
    fetchCourseInfo:any;
    selectedCourseNo:any;
    entries:any;
  }
  export class AttendanceSheet {

    public static $inject = ['$scope', '$stateParams', 'appConstants', 'HttpClient','hotRegisterer'];
    private currentUser: LoggedInUser;
    private columnHeader= [
      {
        data: 'sId',
        title: 'Student Id',
        readOnly:true
      },
      {
        data: 'sName',
        title: 'Student Name',
        readOnly:true
      },
      {
        data: 'date11012016',
        title: '11 Jan, 16',
        renderer: this.imageRenderer,
        readOnly:true,
        serial:1,
        teacherName:"Nurul Amin",
        teacherShort:"nam"

      },
      {
        data: 'date21022016',
        title: '21 Feb, 16',
        renderer: this.imageRenderer,
        readOnly:true
      },
      {
        data: 'date01032016',
        title: '01 Mar, 16',
        renderer: this.imageRenderer,
        readOnly:true
      },
      {
        data: 'date10042016',
        title: '10 Apr, 16',
        renderer: this.imageRenderer
      },
      {
        data: 'date15052016',
        title: '15 Mar, 16',
        renderer: this.imageRenderer,
        readOnly:true
      },
      {
        data: 'date22052016',
        title: '22 May, 16',
        renderer: this.imageRenderer,
        readOnly:true
      },
      {
        data: 'date01062016',
        title: '01 Jun, 16',
        renderer: this.imageRenderer,
        readOnly:true
      },
      {
        data: 'date07062016',
        title: '07 Jun, 16',
        renderer: this.imageRenderer,
        readOnly:true
      },
      {
        data: 'date12062016',
        title: '12 Jun, 16',
        renderer: this.imageRenderer,
        readOnly:true
      },
      {
        data: 'date25062016',
        title: '25 Jun, 16',
        renderer: this.imageRenderer,
        readOnly:true
      },
      {
        data: 'date01072016',
        title: '01 Jul, 16',
        renderer: this.imageRenderer,
        readOnly:true
      },
      {
        data: 'date03072016',
        title: '03 Jul, 16',
        renderer: this.imageRenderer,
        readOnly:true
      },
      {
        data: 'date05072016',
        title: '05 Jul, 16',
        renderer: this.imageRenderer,
        readOnly:true
      },
      {
        data: 'date07072016',
        title: '07 Jul, 16',
        renderer: this.imageRenderer,
        readOnly:true
      },
      {
        data: 'date09072016',
        title: '09 Jul, 16',
        renderer: this.imageRenderer,
        readOnly:true
      },
      {
        data: 'date11072016',
        title: '11 Jul, 16',
        renderer: this.imageRenderer,
        readOnly:true
      },
      {
        data: 'date13072016',
        title: '13 Jul, 16',
        renderer: this.imageRenderer,
        readOnly:true
      }
    ];
private serial;
    private attendanceSearchParamModel: CourseTeacherSearchParamModel;

    constructor(private $scope: IAttr, private $stateParams: any,
                private appConstants: any, private httpClient: HttpClient,private hotRegisterer:any) {

      $scope.loadingVisibility = false;
      $scope.contentVisibility = false;
      $scope.test123=this.test123.bind(this);
      $scope.insertColo=this.insertColo.bind(this);


      $scope.showAttendanceSheet=this.showAttendanceSheet.bind(this);
      var that=this;
      $scope.data = {
        settings:{
          colWidths: [80, 230, 100, 100, 100, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90],
          colHeaders: true,
          rowHeaders: true,
          fixedColumnsLeft: 2,
          fixedRowsTop: 1,
          width: $(".page-content").width(),
          height:$( window ).height()-200,
          currentRowClassName: 'currentRow',
          currentColClassName: 'currentCol',
          fillHandle: false,
          //cells: function(r,c, prop) {
          //  var cellProperties :any={};
          //  if (c==0 || c==1 || r===0) {
          //    cellProperties.readOnly = true;
          //  }
          //
          //  return cellProperties;
          //},
          afterOnCellMouseDown: function(event, coords){
            console.log(coords.row+"==="+coords.col);
            var hot = that.hotRegisterer.getInstance("foo");
            var update = [];
            update.push([coords.row,coords.col,'I']);

            var tab = hot.getData();
            //hot.setDataAtCell(update);
          }
        },
        // items:[["160105001","Sadia Sultana","Y","N","Y","Y","N","Y","Y","Y","Y","Y","Y","Y","Y","Y","Y","Y","Y"]]
        items:[{'sId':'','sName':'','date11012016':'I','date21022016':'I','date01032016':'I','date10042016':'O-BBC-Md. Abul Malek','date15052016':'I','date22052016':'I','date01062016':'I','date07062016':'I','date12062016':'I','date25062016':'I','date01072016':'v','date03072016':'I','date05072016':'I','date07072016':'I','date09072016':'I','date11072016':'I','date13072016':'I'},
               {'sId':'160105001','sName':'Sadia Sultana','date11012016':'Y','date21022016':'Y','date01032016':'Y','date10042016':'Y','date15052016':'Y','date22052016':'Y','date01062016':'Y','date07062016':'Y','date12062016':'Y','date25062016':'Y','date01072016':'Y','date03072016':'Y','date05072016':'Y','date07072016':'Y','date09072016':'Y','date11072016':'Y','date13072016':'Y'}],
      columns:this.columnHeader
      };



      this.httpClient.get("users/current", HttpClient.MIME_TYPE_JSON,
          (response: LoggedInUser) => {
            this.attendanceSearchParamModel = new CourseTeacherSearchParamModel(this.appConstants, this.httpClient);
            this.attendanceSearchParamModel.programSelector.setDepartment(response.departmentId);
            // Program selector is not required. Setting it to null doesn't make sense,
            // probably adding some show/hide mechanism makes it more clear to understand
            this.attendanceSearchParamModel.programSelector.setProgramId(null);
            this.currentUser = response;
            this.$scope.attendanceSearchParamModel = this.attendanceSearchParamModel;

            /** Need to change this **/
            var that=this;
            setTimeout(function(){
              that.$scope.attendanceSearchParamModel.semesterId=that.$scope.attendanceSearchParamModel.programSelector.getSemesters()[1].id;
            },1000);





          });

      //this.httpClient.get("academic/classattendance/ifti", this.appConstants.mimeTypeJson,
      //    (json:any, etag:string) => {
      //      var response:any = json.entries;
      //      console.log(response);
      //    },
      //    (response:ng.IHttpPromiseCallbackArg<any>) => {
      //      console.error(response);
      //    });

      this.$scope.fetchCourseInfo = this.fetchCourseInfo.bind(this);
      this.$scope.operation=this.operation.bind(this);
      this.$scope.showCalendar=this.showCalendar.bind(this);
      this.$scope.setDate=this.setDate.bind(this);



    }
    private imageRenderer (instance, td, row, col, prop, value, cellProperties) {

      var value = Handsontable.helper.stringify(value);
      var serial=row+''+col;
      var output="";
      var nonEditModeString = '<i class="fa fa-pencil-square-o" aria-hidden="true" style="cursor:pointer;margin-left:2px;" onClick="operation(\'E\','+row+','+col+',\''+value+'\')"></i>';
      var editModeString = '<i class="fa fa-check-square-o" aria-hidden="true" style="color:green;cursor:pointer;" onClick="operation(\'EY\','+row+','+col+',\''+value+'\')";></i> ' +
                                       '<i class="fa fa-times" aria-hidden="true" style="color:red;cursor:pointer;margin-left:2px;" onClick="operation(\'EN\','+row+','+col+',\''+value+'\')";></i>'+
                                       '<i class="fa fa-undo" aria-hidden="true" style="cursor:pointer;margin-left:2px;" onClick="operation(\'R\','+row+','+col+',\''+value+'\')";></i>'+
                                       '<i class="fa fa-floppy-o" aria-hidden="true" style="cursor:pointer;margin-left:2px;"></i>'+
                                       '<i class="fa fa-trash-o" aria-hidden="true"  style="color:red;cursor:pointer;margin-left:2px;" ></i>';
      if (value == "Y")
        output = "<img src='images/attendancePresent.png' />";
      else if (value == "N")
        output="<img src='images/attendanceAbsent.png' />";
      else if (value.indexOf("E-")==0){
        if((value.split("-"))[1]=="Y")
            output="<input type='checkbox' checked/>";
        else
          output="<input type='checkbox'/>";
      }
      else if (value == "EY"){
        output="<input type='checkbox' checked/>";
      }
      else if (value == "EN"){
        output="<input type='checkbox' />";
      }
      else if(value=="I") {
        output=nonEditModeString;
      }
      else if(value=="IE") {
        output=editModeString;
      }

//      Handsontable.Dom.addEvent(img, 'mousedown', function (e){
//        e.preventDefault(); // prevent selection quirk
//      });


      Handsontable.Dom.empty(td);
      //td.appendChild(img);
      //console.log(row+"==="+ col);
      //td.insertAdjacentHTML('beforeend',strVar);
      $(td).html(output);
      td.className = 'htCenter';
      return td;
    }

    private test123(){
      var hot_instance = this.hotRegisterer.getInstance("foo");
      var data = hot_instance.getData();
      var exportPlugin = hot_instance.getPlugin('exportFile');
      console.log(data);
      exportPlugin.downloadFile('csv', {filename: 'MyFile'});
    }

    private operation(operationType,row,column,value){
      var hot = this.hotRegisterer.getInstance("foo");
      var serial=row+""+column;

      var classDate=this.columnHeader[column];




      var update = [];
      var update1=[];
      if(operationType=="R"){
        update=JSON.parse(localStorage.getItem("class_attendance_" + row+"_"+column));
        for(var  i=0;i<update.length;i++){
          var innerArray = update[i];
          var val=innerArray[2];
          innerArray[2]=val.replace("E-","");
          update1.push(innerArray)
        }
        update1[0]=[0,column,"I"];
        hot.setDataAtCell(update1);
        hot.render();

        var dateValue=$("#date_"+serial).val();
        classDate.title=dateValue;
        this.columnHeader[column]=classDate;
        hot.updateSettings({
          columns:this.columnHeader
        });
        return;
      }


      var rows = hot.countRows();
     // console.log(rows);
      for(var i = 0; i < rows; i++){
        if(i==0 && operationType=="E"){
          //console.log("-------->>");
          update.push([i,column,'IE']);
        }
        else if(i!=0 && (operationType =='Y' || operationType =='N' || operationType =='E'  || operationType=='EY' || operationType == 'EN')){

          if(operationType=='E')
              update.push([i, column,  'E'+"-"+hot.getDataAtCell(i,column)]);
          else if(operationType=='EY' )
            update.push([i, column,  "E-Y"]);
          else if(operationType=='EN' )
            update.push([i, column,  "E-N"]);
        }
      }

      hot.setDataAtCell(update);
    //  hot.render();

      if(operationType=="E"){
        localStorage["class_attendance_" + row+"_"+column] = JSON.stringify(update);
        //classDate.title="<a onclick=\"showCalendar('"+serial+"')\">c</a><input id='date_"+serial+"' type='text' style='width:80px;height:20px;border:1px solid grey;text-align:center;' value=\""+classDate.title+"\"  />";
        this.columnHeader[column]=classDate;
        classDate.title="<i class='fa fa-calendar' aria-hidden='true' onclick=\"showCalendar('"+serial+"')\" style='cursor:pointer'></i>&nbsp;"+
        "<input id='date_"+serial+"' class='date_"+serial+"'  type='text' style='width:55px;height:14px;border:1px solid grey;text-align:center;font-size:10px;' value=\""+classDate.title+"\"  />";

        hot.updateSettings({
          columns:this.columnHeader
        });
      }


    }

    private showCalendar(serial){
      $("#date_"+serial).datepicker("show");
      $('#myModal').modal('show');
      this.serial=serial;
    }

    private insertColo(){
      var hot = this.hotRegisterer.getInstance("foo");
      this.columnHeader.splice(2, 0, {
        data: 'new',
        title: 'new',
        renderer: this.imageRenderer
      });
      hot.updateSettings({
        columns:this.columnHeader
      });

      var update = [];
      var rows = hot.countRows();
      for(var i = 0; i < rows; i++){
        if(i==0){
          update.push([i,2,'I']);
        }
        else {
          update.push([i, 2, 'Y']);
        }
      }
      hot.setDataAtCell(update);
      hot.render();

    }

    private setDate(){
    console.log($("#class_date").val());
      console.log(this.serial);
      console.log($("#date_"+this.serial));

     // document.getElementById("date_"+this.serial).value="1111";
    //$("#date_"+this.serial).val($("#class_date").val());

      var elements = document.getElementsByClassName("date_"+this.serial);
      var names = '';
      for(var i=0; i<elements.length; i++) {
         $(elements[i]).val($("#class_date").val());
        $(elements[i]).attr('value',$("#class_date").val());
      }
      //alert(Number((this.serial).charAt(1)));
      var classDate=this.columnHeader[Number((this.serial).charAt(1))];
      //classDate.title=$("#class_date").val();
      classDate.title="<i class='fa fa-calendar' aria-hidden='true' onclick=\"showCalendar('"+this.serial+"')\" style='cursor:pointer'></i>&nbsp;"+
      "<input id='date_"+this.serial+"' class='date_"+this.serial+"'  type='text' style='width:55px;height:14px;border:1px solid grey;text-align:center;font-size:10px;' value=\""+$("#class_date").val()+"\"  />";

      this.columnHeader[Number((this.serial).charAt(1))]=classDate;
     $('#myModal').modal('hide');
    }
    private fetchCourseInfo(): void {

      $("#leftDiv").hide(100);
      $("#arrowDiv").show(50);

      this.$scope.loadingVisibility = true;
      this.$scope.contentVisibility = false;
      this.$scope.selectedCourseNo = '';

      this.httpClient.get("academic/courseTeacher/" + this.attendanceSearchParamModel.semesterId + "/"
          + this.currentUser.employeeId + "/course", HttpClient.MIME_TYPE_JSON,
          (response: {entries: CourseTeacherModel[]}) => {
            this.$scope.entries = this.aggregateResult(response.entries);
            this.$scope.loadingVisibility = false;
            this.$scope.contentVisibility = true;
          }
      )
    }


    private aggregateResult(courses: CourseTeacherModel[]): CourseTeacherModel[] {
      var courseList: CourseTeacherModel[] = [];
      var courseMap: {[courseId:string]: CourseTeacherModel} = {};
      courses.forEach((courseTeacher: CourseTeacherModel) => {

        if (courseTeacher.courseId in courseMap) {
          courseMap[courseTeacher.courseId].section
              = courseMap[courseTeacher.courseId].section + "," + courseTeacher.section;
        } else {
          courseList[courseList.length] = courseTeacher;
          courseMap[courseTeacher.courseId] = courseTeacher;
        }
      });



      return courseList;
    }

    private showAttendanceSheet(){
      $("#courseSelectionDiv").hide(80);
      $("#topArrowDiv").show(50);
    }
  }

  UMS.controller("AttendanceSheet", AttendanceSheet);
}