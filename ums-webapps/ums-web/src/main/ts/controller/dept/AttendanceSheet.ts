module ums {
  interface IAttr extends ng.IScope {
    downloadSectionWiseXls:Function;
    insertNewAttendanceColumn:Function;
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
    addNewColumnDisable:any;
  }
  export class AttendanceSheet {

    public static $inject = ['$scope', '$stateParams', 'appConstants', 'HttpClient','hotRegisterer'];
    private currentUser: LoggedInUser;
    private columnHeader;
    private serial;
    private attendanceSearchParamModel: CourseTeacherSearchParamModel;

    constructor(private $scope: IAttr, private $stateParams: any,
                private appConstants: any, private httpClient: HttpClient,private hotRegisterer:any) {

      $scope.loadingVisibility = false;
      $scope.contentVisibility = false;
      $scope.addNewColumnDisable=false;
      $scope.downloadSectionWiseXls=this.downloadSectionWiseXls.bind(this);
      $scope.insertNewAttendanceColumn=this.insertNewAttendanceColumn.bind(this);

      $scope.showAttendanceSheet=this.showAttendanceSheet.bind(this);
      this.columnHeader=[];

      var that=this;
      $scope.data = {
        settings:{
          colWidths: [80, 230, 100, 100],
          colHeaders: true,
          rowHeaders: true,
          fixedColumnsLeft: 2,
          fixedRowsTop: 1,
          width: $(".page-content").width(),
          height:$( window ).height()-200,
          currentRowClassName: 'currentRow',
          currentColClassName: 'currentCol',
          fillHandle: false
        },
        items:[],
        columns:[]
        /*
        items:[{'sId':'','sName':'','date11012016':'I','date21022016':'T-ARM','date01032016':'I'},
          {'sId':'160105001','sName':'Sadia Sultana','date11012016':'Y','date21022016':'Y','date01032016':'Y'},
          {'sId':'160105002','sName':'Md. Ferdous Wahed','date11012016':'Y','date21022016':'Y','date01032016':'N'},
          {'sId':'160105003','sName':'Tahsin Sarwar','date11012016':'Y','date21022016':'Y','date01032016':'Y'}],
        columns:this.columnHeader
        */
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

      this.httpClient.get("academic/classattendance/ifti", this.appConstants.mimeTypeJson,
          (json:any, etag:string) => {
                var response:any = json;
            console.log(response);
                var table = this.getTableInstance();
                this.columnHeader=response.columns;
            for(var i=2;i<this.columnHeader.length;i++){

              var a=this.columnHeader[i];
              a.renderer= this.imageRenderer;
            }
                this.$scope.data.columns=this.columnHeader;
                this.$scope.data. items=response.attendance;
          },
          (response:ng.IHttpPromiseCallbackArg<any>) => {
            console.error(response);
          });

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
      else if (value.indexOf("T-")==0){
        output=value;
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

    private downloadSectionWiseXls(){
      var table = this.getTableInstance();
      var data = table.getData();
      var exportPlugin = table.getPlugin('exportFile');
      console.log(data);
      exportPlugin.downloadFile('csv', {filename: 'MyFile'});
    }

    private operation(operationType,row,column,value){
      var table = this.getTableInstance();
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
        table.setDataAtCell(update1);
        table.render();

        //var dateValue=$("#date_"+serial).val();
        classDate.title=classDate.date+" "+"<span class='badge badge-info'>"+classDate.serial+"</span>";
        this.columnHeader[column]=classDate;
        table.updateSettings({
          columns:this.columnHeader
        });
        return;
      }


      var rows = table.countRows();
      var newData=table.getSourceData();
      // console.log(rows);
      for(var i = 0; i < rows; i++){
        if(i==0 && operationType=="E"){
          //console.log("-------->>");
          update.push([i,column,'IE']);
          newData[i][this.columnHeader[column].data]='IE';
        }
        else if(i!=0 && (operationType =='Y' || operationType =='N' || operationType =='E'  || operationType=='EY' || operationType == 'EN')){

          if(operationType=='E') {
            update.push([i, column, 'E' + "-" + table.getDataAtCell(i, column)]);
            newData[i][this.columnHeader[column].data]='E' + "-" + table.getDataAtCell(i, column);


          }
          else if(operationType=='EY' ) {
            update.push([i, column, "E-Y"]);
            newData[i][this.columnHeader[column].data]='E-Y';
          }
          else if(operationType=='EN' ) {
            update.push([i, column, "E-N"]);
            newData[i][this.columnHeader[column].data]='E-N';
          }
        }
      }


      console.log(newData);
      //  hot.setDataAtCell(update);
      table.loadData(newData);
      //  hot.render();
      //
      if(operationType=="E"){
        localStorage["class_attendance_" + row+"_"+column] = JSON.stringify(update);
        //classDate.title="<a onclick=\"showCalendar('"+serial+"')\">c</a><input id='date_"+serial+"' type='text' style='width:80px;height:20px;border:1px solid grey;text-align:center;' value=\""+classDate.title+"\"  />";
        this.columnHeader[column]=classDate;
        classDate.title="<i class='fa fa-calendar' aria-hidden='true' onclick=\"showCalendar('"+serial+"')\" style='cursor:pointer'></i>&nbsp;"+
        "<input id='date_"+serial+"' class='date_"+serial+"'  type='text' style='width:55px;height:14px;border:1px solid grey;text-align:center;font-size:10px;' value=\""+classDate.date+"\"  readonly/>&nbsp;"+
        "<input id='serial_"+serial+"' class='serial_"+serial+"'  type='text' style='width:15px;height:14px;border:1px solid grey;text-align:center;font-size:10px;' value=\""+classDate.serial+"\"  readonly/>";

        table.updateSettings({
          columns:this.columnHeader
        });
      }



    }

    private showCalendar(serial){
      $('#class_date').val($('#date_'+serial).val());
      $('#class_serial').val($('#serial_'+serial).val());
      $('#myModal').modal('show');
      this.serial=serial;
    }

    private getTableInstance():any{
    return this.hotRegisterer.getInstance("attendanceHandsOnTable");
  }
    private insertNewAttendanceColumn(){

      var maxSerial = Math.max.apply(null, this.columnHeader.map(function(a:any){return a.serial;})) ; //Get maximum serial  from json object array columnHeader
      var nextSerial=maxSerial+1;
      var today=new Date();
      var year:string=today.getFullYear()+'';
      var dateFormat1 = today.getDate()+''+(today.getMonth()+1)+''+ today.getFullYear()+''+nextSerial;
      var dateFormat2 = today.getDate()+' '+Utils.SHORT_MONTH_ARR[today.getMonth()] +', '+year.substring(2,4);
      this.$scope.addNewColumnDisable = true;



      var table = this.getTableInstance();
      this.columnHeader.splice(2, 0, {
        data: 'date'+dateFormat1,
        title: dateFormat2+ "&nbsp;<span class='badge badge-info'>"+nextSerial+"</span>",
        date:dateFormat2,
        serial:nextSerial, //new to find out
        renderer: this.imageRenderer,
        readOnly:true,
        teacherId:"22"
      });

      table.updateSettings({
        columns:this.columnHeader
      });

      var update = [];
      var rows = table.countRows();
      for(var i = 0; i < rows; i++){
        if(i==0){
          update.push([i,2,'I']);
        }
        else {
          update.push([i, 2, 'Y']);
        }
      }
      table.setDataAtCell(update);
      table.render();

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
      "<input id='date_"+this.serial+"' class='date_"+this.serial+"'  type='text' style='width:55px;height:14px;border:1px solid grey;text-align:center;font-size:10px;' value=\""+$("#class_date").val()+"\"  />&nbsp;"+
      "<input id='serial_"+this.serial+"' class='serial_"+this.serial+"'  type='text' style='width:15px;height:14px;border:1px solid grey;text-align:center;font-size:10px;' value=\""+$("#class_serial").val()+"\"  />";

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