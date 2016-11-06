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
    saveAttendance:Function;
    deleteAttendance:Function;
    changeCheckboxValue:Function;
    fetchAttendanceSheet:Function;
    refreshAttendanceSheet:Function;
    fetchCourseInfo:any;
    selectedCourseNo:any;
    entries:any;
    addNewColumnDisable:any;
    colWidthArray:any;

    selectedSection:string;
    selectedSemester:string;
    selectedCourse:string;
  }
  interface IClassAttendance {
    studentId:string;
    studentName:string;
    attendance:number;
  }


  //safsafsafas s asdf asf sdf
  export class AttendanceSheet {
    public static $inject = ['$scope', '$stateParams', 'appConstants', 'HttpClient','hotRegisterer','notify'];
    private currentUser: LoggedInUser;
    private columnHeader;
    private serial;
    private attendanceSearchParamModel: CourseTeacherSearchParamModel;

    constructor(private $scope: IAttr, private $stateParams: any,
                private appConstants: any, private httpClient: HttpClient,private hotRegisterer:any,private notify: Notify) {

      $scope.loadingVisibility = false;
      $scope.contentVisibility = false;
      $scope.addNewColumnDisable=false;
      $scope.downloadSectionWiseXls=this.downloadSectionWiseXls.bind(this);
      $scope.insertNewAttendanceColumn=this.insertNewAttendanceColumn.bind(this);

      $scope.showAttendanceSheet=this.showAttendanceSheet.bind(this);
      this.columnHeader=[];
      this.$scope.colWidthArray=[80, 230];
      var that=this;
      $scope.data = {
        settings:{
          colWidths: this.$scope.colWidthArray,
          colHeaders: true,
          rowHeaders: true,
          fixedColumnsLeft: 2,
          fixedRowsTop: 1,
          width: $(".page-content").width(),
          height:$( window ).height()-140,
          currentRowClassName: 'currentRow',
          currentColClassName: 'currentCol',
          fillHandle: false
        },
        items:[],
        columns:[]
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

      this.$scope.fetchCourseInfo = this.fetchCourseInfo.bind(this);
      this.$scope.operation=this.operation.bind(this);
      this.$scope.showCalendar=this.showCalendar.bind(this);
      this.$scope.setDate=this.setDate.bind(this);
      this.$scope.saveAttendance=this.saveAttendance.bind(this);
      this.$scope.deleteAttendance=this.deleteAttendance.bind(this);
      this.$scope.changeCheckboxValue=this.changeCheckboxValue.bind(this);
      this.$scope.fetchAttendanceSheet=this.fetchAttendanceSheet.bind(this);
      this.$scope.refreshAttendanceSheet=this.refreshAttendanceSheet.bind(this);

    }

    private refreshAttendanceSheet(){
      this.fetchAttendanceSheet(this.$scope.selectedCourse);
    }
    //sfasdfljdsakfl asldfjlsakdfjldsajflasdkfj
    private fetchAttendanceSheet(courseId:string){

      this.$scope.selectedSection=$("#section_"+courseId).val();
      this.$scope.selectedSemester=this.$scope.attendanceSearchParamModel.semesterId
      this.$scope.selectedCourse=courseId;

      console.log(this.$scope.selectedSection);
      console.log(this.$scope.selectedSemester);
      console.log(this.$scope.selectedCourse);

      this.httpClient.get("academic/classAttendance/semester/"+this.$scope.selectedSemester+"/course/"+this.$scope.selectedCourse+"/section/"+this.$scope.selectedSection, this.appConstants.mimeTypeJson,
          (json:any, etag:string) => {
            var response:any = json;
            var table = this.getTableInstance();
            this.columnHeader=response.columns;

            var a=this.columnHeader[1];
            a.renderer= this.nameRenderer;

            for(var i=2;i<this.columnHeader.length;i++){

              var a=this.columnHeader[i];
              a.renderer= this.imageRenderer;

              this.$scope.colWidthArray.push(100);
            }

            this.$scope.data.columns=this.columnHeader;
            this.$scope.data. items=response.attendance;
            table.updateSettings({
              colWidths:this.$scope.colWidthArray
            });




          },
          (response:ng.IHttpPromiseCallbackArg<any>) => {
            console.error(response);
          });
    }

    private nameRenderer(instance, td, row, col, prop, value, cellProperties){
      var operationString = '&nbsp;&nbsp;&nbsp;&nbsp;<i class="fa fa-plus-circle" aria-hidden="true" style="color:blue;cursor:pointer;" onClick="insertNewAttendanceColumn()";></i>&nbsp;&nbsp;&nbsp;' +
          '<i class="fa fa-download" aria-hidden="true" style="color:blue;cursor:pointer;margin-left:2px;" onClick="downloadSectionWiseXls()";></i>&nbsp;&nbsp;&nbsp;'+
          '<i class="fa fa-bolt" aria-hidden="true" style="color:blue;cursor:pointer;margin-left:2px;" onClick="downloadSectionWiseXls()";></i>&nbsp;&nbsp;&nbsp;'+
          '<i class="fa fa-user" aria-hidden="true" style="color:red;cursor:pointer;margin-left:2px;" onClick="operation()";></i>&nbsp;&nbsp;&nbsp;'+
          '<i class="fa fa-users" aria-hidden="true" style="color:grey;cursor:pointer;margin-left:2px;" onClick="operation()";></i>&nbsp;&nbsp;&nbsp;'+
          '<i id="download1" class="fa fa-refresh" aria-hidden="true" style="cursor:pointer;margin-left:2px;" onClick="refreshAttendanceSheet()";></i>';

      var value = Handsontable.helper.stringify(value);
      var output="";
      if (value == "OR")   //Operation
        output =operationString;
      else
        output=value;

      Handsontable.Dom.empty(td);
      //td.appendChild(img);

      $(td).html(output);
      //td.className = 'htCenter';
      return td;
    }

    private imageRenderer (instance, td, row, col, prop, value, cellProperties) {

      var value = Handsontable.helper.stringify(value);
      var serial=row+''+col;
      var output="";

      var nonEditModeString = '<i class="fa fa-pencil-square-o" aria-hidden="true" style="cursor:pointer;margin-left:2px;" onClick="operation(\'E\','+row+','+col+',\''+value+'\')"></i>';
      var editModeString = '<i class="fa fa-check-square-o" aria-hidden="true" style="color:green;cursor:pointer;" onClick="operation(\'EY\','+row+','+col+',\''+value+'\')";></i> ' +
          '<i class="fa fa-times" aria-hidden="true" style="color:red;cursor:pointer;margin-left:2px;" onClick="operation(\'EN\','+row+','+col+',\''+value+'\')";></i>'+
          '<i class="fa fa-undo" aria-hidden="true" style="cursor:pointer;margin-left:2px;" onClick="operation(\'R\','+row+','+col+',\''+value+'\')";></i>'+
          '<i class="fa fa-floppy-o" aria-hidden="true" style="cursor:pointer;margin-left:2px;" onClick="saveAttendance('+row+','+col+')";></i>'+
          '<i class="fa fa-trash-o" aria-hidden="true"  style="color:red;cursor:pointer;margin-left:2px;" onClick="deleteAttendance('+row+','+col+')";></i>';

      if (value == "Y")
        output = "<img src='images/attendancePresent.png' />";
      else if (value == "N")
        output="<img src='images/attendanceAbsent.png' />";
      else if (value.indexOf("E-")==0){
        if((value.split("-"))[1]=="Y")
          output="<input type='checkbox' checked id='att_"+serial+"' onclick=\"changeCheckboxValue("+row+","+col+",this.value)\"/>";
        else
          output="<input type='checkbox' id='att_"+serial+"' onclick=\"changeCheckboxValue("+row+","+col+",this.value)\"/>";
      }
      else if (value == "EY"){
        output="<input type='checkbox' checked id='att_"+row+col+"' onclick=\"changeCheckboxValue("+row+","+col+",this.value)\"/>";
      }
      else if (value == "EN"){
        output="<input type='checkbox'  id='att_"+row+col+"' onclick=\"changeCheckboxValue("+row+","+col+",this.value)\"/>";
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

      //td.insertAdjacentHTML('beforeend',strVar);
      $(td).html(output);
      td.className = 'htCenter';
      return td;
    }

    private downloadSectionWiseXls(){
      //var table = this.getTableInstance();
      //var data = table.getData();
      //var exportPlugin = table.getPlugin('exportFile');
      //exportPlugin.downloadFile('csv', {filename: 'MyFile',columnHeaders: true, rowHeaders: true});


      this.httpClient.get("classAttendanceReport/xls/semester/11012016/course/EEE1101_110500_00408", 'application/vnd.ms-excel',
          (data: any, etag: string) => {
            var file = new Blob([data], {type: 'application/vnd.ms-excel'});
            var reader = new FileReader();
            reader.readAsDataURL(file);
            reader.onloadend = function (e) {
              //as fas fsd aasd asd af as asdasd f
              //  console.log(reader.result);
              window.open(reader.result, 'Excel', 'width=20,height=10,toolbar=0,menubar=0,scrollbars=no', false);

              //var contentType = 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet';
              //var sliceSize = 512;
              //var byteCharacters = reader.result;
              //var byteArrays = [];

              //for (var offset = 0; offset < byteCharacters.length; offset += sliceSize) {
              //  var slice = byteCharacters.slice(offset, offset + sliceSize);
              //
              //  var byteNumbers = new Array(slice.length);
              //  for (var i = 0; i < slice.length; i++) {
              //    byteNumbers[i] = slice.charCodeAt(i);
              //  }
              //  var byteArray = new Uint8Array(byteNumbers);
              //
              //  byteArrays.push(byteArray);
              //}


              //var csvData = new Blob(byteArrays , {type: 'application/vnd.ms-excel'});
              //
              //var csvURL = window.URL.createObjectURL(csvData);
              //var tempLink = document.createElement('a');
              //tempLink.href = csvURL;
              //tempLink.setAttribute('download', 'ActiveEvent_datassss.xls');
              //tempLink.click();
            };
          },
          (response: ng.IHttpPromiseCallbackArg<any>) => {
            console.error(response);
          }, 'arraybuffer');

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
      else if(operationType=="SR"){
        var data=table.getData();
        update=JSON.parse(localStorage.getItem("class_attendance_" + row+"_"+column));
        for(var  i=0;i<update.length;i++){
          var innerArray = update[i];
          console.log(innerArray);
          innerArray[2]=(data[i][column]=="E-Y")?"Y":"N";
          update1.push(innerArray);
        }
        update1[0]=[0,column,"I"];
        table.setDataAtCell(update1);
        table.render();


        //var dateValue=$("#date_"+serial).val();
        classDate.title=$("#date_0"+column).val()+"<span class='badge badge-info'>"+$("#serial_0"+column).val()+"</span>";
        classDate.date=$("#date_0"+column).val();
        classDate.serial=Number($("#serial_0"+column).val());
        this.columnHeader[column]=classDate;
        table.updateSettings({
          columns:this.columnHeader
        });
        return;
      }


      var rows = table.countRows();
      var newData=table.getSourceData();
      var update1=[];
      for(var i = 0; i < rows; i++){
        if(i==0 && operationType=="E"){

          update.push([i,column,'IE']);
          newData[i][this.columnHeader[column].data]='IE';
          //update1[i]=[i,column,'IE'];
        }
        else if(i!=0 && (operationType =='Y' || operationType =='N' || operationType =='E'  || operationType=='EY' || operationType == 'EN')){

          if(operationType=='E') {
            update.push([i, column, 'E' + "-" + table.getDataAtCell(i, column)]);
            newData[i][this.columnHeader[column].data]='E' + "-" + table.getDataAtCell(i, column);
            //update1[i]=[i,column,'E' + "-" + table.getDataAtCell(i, column)];

          }
          else if(operationType=='EY' ) {
            update.push([i, column, "E-Y"]);
            newData[i][this.columnHeader[column].data]='E-Y';
            //update1[i]=[i,column,"E-Y"];
          }
          else if(operationType=='EN' ) {
            update.push([i, column, "E-N"]);
            newData[i][this.columnHeader[column].data]='E-N';
            //  update1[i]=[i,column,"E-N"];
          }

        }
      }

//
      //asfasdfsadfasdf






      //asdfaasdasdf

      console.log(newData);
      //  hot.setDataAtCell(update);
      table.loadData(newData);
      //table.setDataAtCell(update1);
      //  table.render();
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

    private saveAttendance(row,column){
      var table = this.getTableInstance();
      var complete_json=this.createCompleteJson(row,column)
      var url = "academic/classAttendance";
      var dateObject=this.columnHeader[column];
      if(dateObject.id==0) {
        this.httpClient.post(url, complete_json, 'application/json')
            .success((data) => {
              this.notify.success("Successfully Saved New Attendance.");

              dateObject.id=data;
              this.columnHeader[column]=dateObject;
              table.updateSettings({
                columns:this.columnHeader
              });

              this.operation('SR', 0, 2, 'IE');
              this.$scope.addNewColumnDisable=false;

            }).error((data) => {
            });
      }
      else{
        var url = "academic/classAttendance";
        this.httpClient.put(url, complete_json, 'application/json')
            .success((data) => {
              this.notify.success("Successfully Updated Attendance Information.");
              this.operation('SR', 0, column, 'IE');

            }).error((data) => {
            });
      }
    }

    private changeCheckboxValue(row,col,value){
      var checked=$('#att_'+row+col).is(':checked');
      var table = this.getTableInstance();

      var update1=[];
      update1[0]=[row,col,(checked==true)?"E-Y":"E-N"];
      table.setDataAtCell(update1);

    }

    private deleteAttendance(row,column){
      var table = this.getTableInstance();
      var dateObject=this.columnHeader[column];
      if(dateObject.id==0) {
        this.$scope.data.columns.splice(column, 1);
        this.$scope.addNewColumnDisable=false;

        this.$scope.colWidthArray.splice(column, 1);
        table.updateSettings({
          colWidths:this.$scope.colWidthArray
        });
      }
      else{

        this.httpClient.delete('academic/classAttendance/'+dateObject.id)
            .success(()=>{
              this.notify.success("Successfully Deleted Attendance Information.");
              this.$scope.data.columns.splice(column, 1);

              this.$scope.colWidthArray.splice(column, 1);
              table.updateSettings({
                colWidths:this.$scope.colWidthArray
              });


            }).error((data)=>{
              console.log("Deletion failure");
              console.log(data);
            })
      }


      //var complete_json=this.createCompleteJson(row,column)
      //var url = "academic/classAttendance";
      //this.httpClient.post(url, complete_json, 'application/json')
      //    .success(() => {
      //      this.notify.success("Successfully Saved New Attendance.");
      //    }).error((data) => {
      //    });
    }
    private createCompleteJson(row:number,column:number):any{
      var table = this.getTableInstance();

      var attendances=table.getData();
      var attendanceList:Array<IClassAttendance> = new Array<IClassAttendance>();
      for(var i=1;i<attendances.length;i++){
        var attendance:IClassAttendance = <IClassAttendance>{};
        var cell=attendances[i];
        attendance.studentId=cell[0];
        //console.log('att_'+i+''+column);
        //var att=$('#att_'+i+''+column).is(':checked');//document.getElementById('att_'+i+''+column).checked;
        attendance.attendance=(cell[column]=="E-Y")?1:0;
        attendanceList.push(attendance);
      }
      var dateObject=this.columnHeader[column];
      var complete_json = {};
      complete_json["attendanceList"] = attendanceList;
      complete_json["classDate"] = $("#date_0"+column).val();
      complete_json["serial"] = Number($("#serial_0"+column).val());
      complete_json["course"] =this.$scope.selectedCourse;
      complete_json["section"] = this.$scope.selectedSection;
      complete_json["semester"] =  this.$scope.selectedSemester;
      complete_json["id"] = dateObject.id+'';
      return complete_json;
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

      if(this.$scope.addNewColumnDisable==true)
        return;

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
        id:0
        //teacherId:"22"
      });

      this.$scope.colWidthArray.push(100);
      table.updateSettings({
        colWidths:this.$scope.colWidthArray,
        columns:this.columnHeader
      });

      var update = [];
      var rows = table.countRows();
      update.push([0,2,'I']);  //I New Column
      for(var i = 1; i < rows; i++){
        update.push([i, 2, 'Y']);
      }
      table.setDataAtCell(update);
      table.render();

      this.$scope.addNewColumnDisable==true;
    }

    private setDate(){

      // document.getElementById("date_"+this.serial).value="1111";
      //$("#date_"+this.serial).val($("#class_date").val());

      var elements = document.getElementsByClassName("date_"+this.serial);
      var names = '';
      for(var i=0; i<elements.length; i++) {
        $(elements[i]).val($("#class_date").val());
        $(elements[i]).attr('value',$("#class_date").val());
      }

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

    private showAttendanceSheet(courseId){
      $("#courseSelectionDiv").hide(80);
      $("#topArrowDiv").show(50);
      $("#attSheetDiv").show(100);

      console.log(courseId);
      this.fetchAttendanceSheet(courseId);
    }
  }

  UMS.controller("AttendanceSheet", AttendanceSheet);
}