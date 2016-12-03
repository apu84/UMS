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
    attendanceColumnOperation:Function;
    saveAttendance:Function;
    deleteAttendance:Function;
    changeCheckboxValue:Function;
    fetchAttendanceSheet:Function;
    refreshAttendanceSheet:Function;
    downloadAttendanceSheet:Function;
    showAttendanceSheetByStudentCategory:Function;
    fetchCourseInfo:any;
    entries:any;
    addNewColumnDisable:any;
    colWidthArray:any;

    selectedCourse:CourseTeacherModel;
    selectedStudentCategory:string;
  }
  interface IClassAttendance {
    studentId:string;
    studentName:string;
    attendance:number;
  }

  export class ClassAttendance {
    public static $inject = ['$scope', '$stateParams', 'appConstants', 'HttpClient','hotRegisterer','notify','$window','$sce'];
    private currentUser: LoggedInUser;
    private columnHeader;
    private serial;
    private abc;
    private attendanceSearchParamModel: CourseTeacherSearchParamModel;

    constructor(private $scope: IAttr, private $stateParams: any,
                private appConstants: any, private httpClient: HttpClient,private hotRegisterer:any,private notify: Notify, private $window: ng.IWindowService, private $sce:ng.ISCEService) {

      $scope.loadingVisibility = false;
      $scope.contentVisibility = false;
      $scope.addNewColumnDisable=false;
      //$scope.downloadSectionWiseXls=this.downloadSectionWiseXls.bind(this);
      $scope.insertNewAttendanceColumn=this.insertNewAttendanceColumn.bind(this);
      $scope.showAttendanceSheet=this.showAttendanceSheet.bind(this);

      this.columnHeader=[];
      this.$scope.colWidthArray=[80, 230]; // Student Id and Student Name column width
      var that=this;
      $scope.data = {
        settings:{
          colWidths: this.$scope.colWidthArray,
          colHeaders: true,
          rowHeaders: true,
          fixedColumnsLeft: 2, // Fixed StudentId and Student Name column
          fixedRowsTop: 1, //Fixed to row for operations like edit, save, delete ,back, check all and uncheck all
          width: $(".page-content").width(),
          height:$( window ).height()-140,
          currentRowClassName: 'currentRow',
          currentColClassName: 'currentCol',
          fillHandle: false
        },
        items:[],
        columns:[]
      };
      this.$scope.selectedCourse=new CourseTeacherModel();

      this.httpClient.get("users/current", HttpClient.MIME_TYPE_JSON,
          (response: LoggedInUser) => {
            this.attendanceSearchParamModel = new CourseTeacherSearchParamModel(this.appConstants, this.httpClient);
            this.attendanceSearchParamModel.programSelector.setDepartment(response.departmentId);
            this.attendanceSearchParamModel.programSelector.setProgramId(null);
            this.currentUser = response;
            this.$scope.attendanceSearchParamModel = this.attendanceSearchParamModel;
          });

      this.$scope.fetchCourseInfo = this.fetchCourseInfo.bind(this);
      this.$scope.attendanceColumnOperation=this.attendanceColumnOperation.bind(this);
      this.$scope.showCalendar=this.showCalendar.bind(this);
      this.$scope.setDate=this.setDate.bind(this);
      this.$scope.saveAttendance=this.saveAttendance.bind(this);
      this.$scope.deleteAttendance=this.deleteAttendance.bind(this);
      this.$scope.changeCheckboxValue=this.changeCheckboxValue.bind(this);
      this.$scope.fetchAttendanceSheet=this.fetchAttendanceSheet.bind(this);
      this.$scope.refreshAttendanceSheet=this.refreshAttendanceSheet.bind(this);
      $scope.downloadAttendanceSheet = this.downloadAttendanceSheet.bind(this);
      $scope.showAttendanceSheetByStudentCategory=this.showAttendanceSheetByStudentCategory.bind(this);

    }

    private refreshAttendanceSheet(){
      this.fetchAttendanceSheet(this.$scope.selectedCourse,this.$scope.selectedStudentCategory);
    }

    private fetchAttendanceSheet(course:CourseTeacherModel,studentCategory:string){
      console.log(course);
      console.log(studentCategory);
      //Selected values
      this.$scope.selectedCourse.section=$("#section_"+course.courseId).val();
      this.$scope.selectedCourse.semester=this.$scope.attendanceSearchParamModel.semesterId
      this.$scope.selectedCourse.courseId=course.courseId;
      this.$scope.selectedCourse.courseNo=course.courseNo;
      this.$scope.selectedCourse.courseTitle=course.courseTitle;
      this.$scope.selectedStudentCategory=studentCategory;

      console.log(this.$scope.selectedCourse);

      var table = this.getTableInstance();
      this.httpClient.get("classAttendance/semester/"+this.$scope.selectedCourse.semester+"/course/"+this.$scope.selectedCourse.courseId+"/section/"+this.$scope.selectedCourse.section+
          "/studentCategory/"+studentCategory, this.appConstants.mimeTypeJson,
          (response:any, etag:string) => {
            this.columnHeader=response.columns;
            var columnHeader=this.columnHeader[1]; //name column is in 1 index
            columnHeader.renderer= this.nameColumnRenderer;

            //Attendance column start from 2 index
            for(var i=2;i<this.columnHeader.length;i++){
              var columnHeader=this.columnHeader[i];
              columnHeader.renderer= this.attendanceColumnRenderer;
              //Setting width for Attendance columns
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

    //Student Name Column Renderer
    private nameColumnRenderer(instance, td, row, col, prop, value, cellProperties){
      var operationString = '&nbsp;&nbsp;&nbsp;&nbsp;'+
          '<i class="fa fa-plus-circle" aria-hidden="true" title="Add New Attendance Column" style="color:#E74C3C;cursor:pointer;" onClick="insertNewAttendanceColumn()";></i>&nbsp;&nbsp;&nbsp;' +
          '<i class="fa fa-file-excel-o" aria-hidden="true" title="Download Excel" style="color:#273746;cursor:pointer;margin-left:2px;" onClick="downloadAttendanceSheet(\'xls\')";></i>&nbsp;&nbsp;&nbsp;'+
          '<i class="fa fa-file-pdf-o" aria-hidden="true" title="Download PDF" style="color:#273746;cursor:pointer;margin-left:2px;" onClick="downloadAttendanceSheet(\'pdf\')";></i>&nbsp;&nbsp;&nbsp;'+
          '<i class="fa fa-es" id="icon_enrolled" aria-hidden="true" title="Enrolled Students" style="color:#716b7a;cursor:pointer;margin-left:2px;" onClick="showAttendanceSheetByStudentCategory(\'Enrolled\')"></i>&nbsp;&nbsp;&nbsp;'+
          '<i class="fa fa-as" id="icon_all" aria-hidden="true" title="All Students" style="color:#52BE80;cursor:pointer;margin-left:2px;"  onClick="showAttendanceSheetByStudentCategory(\'All\')"></i>&nbsp;&nbsp;&nbsp;'+
          '<i id="download1" class="fa fa-refresh" aria-hidden="true" title="Refresh" style="color:black;cursor:pointer;margin-left:2px;" onClick="refreshAttendanceSheet()";></i>';

      var value = Handsontable.helper.stringify(value);
      var output=value;
      if (value == "GOI")   //Global Operation Icons
        output =operationString;
      Handsontable.Dom.empty(td);
      $(td).html(output);
      return td;
    }

    private showAttendanceSheetByStudentCategory(studentCategory:string){
      if(studentCategory!=this.$scope.selectedStudentCategory)
          this.showAttendanceSheet(this.$scope.selectedCourse,studentCategory);
    }

    private attendanceColumnRenderer (instance, td, row, col, prop, value, cellProperties) {
      var value = Handsontable.helper.stringify(value);
      var serial=row+''+col;
      var output="";

      //E= "Edit" , EY ="Edit and show all checkbox in Checked Mode", EN="Edit and show all checkbox in UnChecked Mode",
      //R="Reset", Y = Positive Attendance , N= Negative Attendance
      //E-Y="Edit and show checkbox in Check Mode"
      //E-N="Edit and show checkbox in Un-Check Mode"
      //I= "Show edit icon for supporting edit of a column"
      //IE= "Showing Check All, Un-Check All, Save, Delete, Back Icon
      //T- = "Showing teacher Id for a column for which the current user has not any right to edit "

      var nonEditModeString = '<i class="fa fa-pencil-square-o" title="Edit" aria-hidden="true" style="cursor:pointer;margin-left:2px;" onClick="attendanceColumnOperation(\'E\','+row+','+col+',\''+value+'\')"></i>';
      var editModeString = '<i class="fa fa-check-square-o" title="Check All" aria-hidden="true" style="color:green;cursor:pointer;" onClick="attendanceColumnOperation(\'EY\','+row+','+col+',\''+value+'\')";></i> ' +
          '<i class="fa fa-times" aria-hidden="true" title="Un-Check All" style="color:red;cursor:pointer;margin-left:2px;" onClick="attendanceColumnOperation(\'EN\','+row+','+col+',\''+value+'\')";></i>'+
          '<i class="fa fa-undo" aria-hidden="true" title="Reset" style="cursor:pointer;margin-left:2px;" onClick="attendanceColumnOperation(\'R\','+row+','+col+',\''+value+'\')";></i>'+
          '<i class="fa fa-floppy-o" aria-hidden="true" title="Save" style="cursor:pointer;margin-left:2px;" onClick="saveAttendance('+row+','+col+')";></i>'+
          '<i class="fa fa-trash-o" aria-hidden="true" title="Delete"  style="color:red;cursor:pointer;margin-left:2px;" onClick="deleteAttendance('+row+','+col+')";></i>';

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
      Handsontable.Dom.empty(td);
      $(td).html(output);
      td.className = 'htCenter';
      return td;
    }

    private downloadAttendanceSheet(fileType:string):any{
      var fileName= this.$scope.selectedCourse.semester+"_"+this.$scope.selectedCourse.courseNo;

      var contentType:string=Utils.getFileContentType(fileType);
      this.httpClient.get("classAttendanceReport/"+fileType+"/semester/"+this.$scope.attendanceSearchParamModel.semesterId+
          "/course/"+this.$scope.selectedCourse.courseId+"/section/"+this.$scope.selectedCourse.section+"/studentCategory/"+this.$scope.selectedStudentCategory,contentType,
          (data:any, etag:string) => {
            Utils.writeFileContent(data,contentType,fileName);
          },
          (response:ng.IHttpPromiseCallbackArg<any>) => {
            console.error(response);
          },'arraybuffer');
    }


    private attendanceColumnOperation(operationType,row,column,value){
      var table = this.getTableInstance();
      var serial=row+""+column;
      var classDate=this.columnHeader[column];

      var localStorageAttendance = [];
      var updatedAttendance=[];
      //Column Reset Operation
      if(operationType=="R"){
        localStorageAttendance=JSON.parse(localStorage.getItem("class_attendance_" + row+"_"+column));
        for(var  i=0;i<localStorageAttendance.length;i++){
          var innerArray = localStorageAttendance[i];
          var val=innerArray[2];

          //Convert "E-Y" to "Y" and "E-N" to "N"
          innerArray[2]=val.replace("E-","");
          updatedAttendance.push(innerArray)
        }
        //Set the header Column (Column wise edit Icons)
        updatedAttendance[0]=[0,column,"I"];
        table.setDataAtCell(updatedAttendance);
        table.render();

        classDate.title=classDate.date+"&nbsp;<span class='badge badge-info'>"+classDate.serial+"</span>";
        this.columnHeader[column]=classDate;
        table.updateSettings({
          columns:this.columnHeader
        });
        return;
      }
      //SR = "Save and show in ReadOnly Mode"
      //Called after saving the changes of  a column. The target is to show the column in Read Only Mode.
      else if(operationType=="SR"){
        var data=table.getData();
        localStorageAttendance=JSON.parse(localStorage.getItem("class_attendance_" + row+"_"+column));
        for(var  i=0;i<localStorageAttendance.length;i++){
          var innerArray = localStorageAttendance[i];
          innerArray[2]=(data[i][column]=="E-Y")?"Y":"N";
          updatedAttendance.push(innerArray);
        }
        updatedAttendance[0]=[0,column,"I"];
        table.setDataAtCell(updatedAttendance);
        table.render();

        classDate.title=$("#date_0"+column).val()+"&nbsp;<span class='badge badge-info'>"+$("#serial_0"+column).val()+"</span>";
        classDate.date=$("#date_0"+column).val();
        classDate.serial=Number($("#serial_0"+column).val());
        this.columnHeader[column]=classDate;
        table.updateSettings({
          columns:this.columnHeader
        });
        return;
      }


      var totalRows = table.countRows();
      var newData=table.getSourceData();
      var updatedAttendance=[];

      //When user Click Edit Icon
      if(totalRows>0 && operationType=="E"){
        localStorageAttendance.push([i,column,'IE']);
        newData[0][this.columnHeader[column].data]='IE';
      }

      for(var i = 1; i < totalRows; i++){
        //Need to think about it operationType =='Y' || operationType =='N' ||
        if((operationType =='E'  || operationType=='EY' || operationType == 'EN')){
          //When user click Edit Icon
          if(operationType=='E') {
            localStorageAttendance.push([i, column, 'E' + "-" + table.getDataAtCell(i, column)]);
            newData[i][this.columnHeader[column].data]='E' + "-" + table.getDataAtCell(i, column);
          }
          //When user Click Check All icon
          else if(operationType=='EY' ) {
            localStorageAttendance.push([i, column, "E-Y"]);
            newData[i][this.columnHeader[column].data]='E-Y';
          }
          //When user Click Un-Check All icon
          else if(operationType=='EN' ) {
            localStorageAttendance.push([i, column, "E-N"]);
            newData[i][this.columnHeader[column].data]='E-N';
          }
        }
      }
      table.loadData(newData);

      //If the user click on Edit icon then we need to do some other task.
      //We need to show date and serial TextBox in header cell
      if(operationType=="E"){
        localStorage["class_attendance_" + row+"_"+column] = JSON.stringify(localStorageAttendance);
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
      var url = "classAttendance";
      var dateObject=this.columnHeader[column];
      //This block is for Newly added Attendance Column
      if(dateObject.id==0) {
        this.httpClient.post(url, complete_json, 'application/json')
            .success((data) => {
              this.notify.success("Successfully Saved New Attendance.");
              dateObject.id=data;
              this.columnHeader[column]=dateObject;
              table.updateSettings({
                columns:this.columnHeader
              });
              //Saved and reset for Column 2
              this.attendanceColumnOperation('SR', 0, 2, 'IE');
              this.$scope.addNewColumnDisable=false;
            }).error((data) => {
            });
      }
      //This block is for existing attendance column
      else{
        var url = "classAttendance";
        this.httpClient.put(url, complete_json, 'application/json')
            .success((data) => {
              this.notify.success("Successfully Updated Attendance Information.");
              //Saved and reset for current operated column
              this.attendanceColumnOperation('SR', 0, column, 'IE');
            }).error((data) => {
            });
      }
    }

    private changeCheckboxValue(row,col,value){
      var checked=$('#att_'+row+col).is(':checked');
      var table = this.getTableInstance();
      var updatedValue=[];
      updatedValue[0]=[row,col,(checked==true)?"E-Y":"E-N"];
      table.setDataAtCell(updatedValue);
    }

    private deleteAttendance(row,column){

      var confirmation = confirm("Are you sure you want to deleted the selected attendance column?");
      if (confirmation == false)
        return;

      var table = this.getTableInstance();
      var dateObject=this.columnHeader[column];
      //If the selected attendance column is a newly added column
      if(dateObject.id==0) {
        this.$scope.data.columns.splice(column, 1);
        this.$scope.addNewColumnDisable=false;

        this.$scope.colWidthArray.splice(column, 1);
        table.updateSettings({
          colWidths:this.$scope.colWidthArray
        });
      }
      //If the selected attendance column is an existing  column
      else{
        this.httpClient.delete('classAttendance/'+dateObject.id)
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
    }

    private createCompleteJson(row:number,column:number):any{
      var table = this.getTableInstance();
      var attendances=table.getData();
      var attendanceList:Array<IClassAttendance> = new Array<IClassAttendance>();

      for(var i=1;i<attendances.length;i++){
        var attendance:IClassAttendance = <IClassAttendance>{};
        var cell=attendances[i];
        attendance.studentId=cell[0];
        attendance.attendance=(cell[column]=="E-Y")?1:0;
        attendanceList.push(attendance);
      }

      var dateObject=this.columnHeader[column];
      var complete_json = {};
      complete_json["attendanceList"] = attendanceList;
      complete_json["classDate"] = $("#date_0"+column).val();
      complete_json["serial"] = Number($("#serial_0"+column).val());
      complete_json["course"] =this.$scope.selectedCourse.courseId;
      complete_json["section"] = this.$scope.selectedCourse.section;
      complete_json["semester"] =  this.$scope.selectedCourse.semester;
      complete_json["id"] = dateObject.id+'';
      return complete_json;
    }

    private showCalendar(serial){
      $('#class_date').val($('#date_'+serial).val());
      $('#class_serial').val($('#serial_'+serial).val());
      $('#dateSerialModal').modal('show');
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
        serial:nextSerial,
        renderer: this.attendanceColumnRenderer,
        readOnly:true,
        id:0
      });

      this.$scope.colWidthArray.push(100);
      table.updateSettings({
        colWidths:this.$scope.colWidthArray,
        columns:this.columnHeader
      });

      var defaultValues = [];
      var rows = table.countRows();
      defaultValues.push([0,2,'I']);  //I New Column
      for(var i = 1; i < rows; i++){
        defaultValues.push([i, 2, 'Y']); //By default we will mark present for all students
      }
      table.setDataAtCell(defaultValues);
      table.render();

      this.$scope.addNewColumnDisable==true;
    }

    private setDate(){
      var elements = document.getElementsByClassName("date_"+this.serial);
      var names = '';

      for(var i=0; i<elements.length; i++) {
        $(elements[i]).val($("#class_date").val());
        $(elements[i]).attr('value',$("#class_date").val());
      }

      var classDate=this.columnHeader[Number((this.serial).charAt(1))];
      classDate.title="<i class='fa fa-calendar' aria-hidden='true' onclick=\"showCalendar('"+this.serial+"')\" style='cursor:pointer'></i>&nbsp;"+
      "<input id='date_"+this.serial+"' class='date_"+this.serial+"'  type='text' style='width:55px;height:14px;border:1px solid grey;text-align:center;font-size:10px;' value=\""+$("#class_date").val()+"\"  />&nbsp;"+
      "<input id='serial_"+this.serial+"' class='serial_"+this.serial+"'  type='text' style='width:15px;height:14px;border:1px solid grey;text-align:center;font-size:10px;' value=\""+$("#class_serial").val()+"\"  />";

      this.columnHeader[Number((this.serial).charAt(1))]=classDate;
      $('#dateSerialModal').modal('hide');
    }

    private fetchCourseInfo(): void {
      $("#leftDiv").hide(100);
      $("#arrowDiv").show(50);

      this.$scope.loadingVisibility = true;
      this.$scope.contentVisibility = false;
      this.$scope.selectedCourse.courseNo = '';

      this.httpClient.get("academic/courseTeacher/" + this.attendanceSearchParamModel.semesterId + "/"
          + this.currentUser.employeeId + "/course", HttpClient.MIME_TYPE_JSON,
          (response: {entries: CourseTeacherModel[]}) => {
            this.$scope.entries = this.aggregateResult(response.entries);
            this.$scope.loadingVisibility = false;
            this.$scope.contentVisibility = true;
          }
      )
      $("#attendanceSheetBlock").hide();

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

    private showAttendanceSheet(course:CourseTeacherModel, studentCategory:string){
      $("#courseSelectionDiv").hide(80);
      $("#topArrowDiv").show(50);
      $("#attendanceSheetBlock").show(100);
      this.fetchAttendanceSheet(course, studentCategory);
    }
  }

  UMS.controller("ClassAttendance", ClassAttendance);
}