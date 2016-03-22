///<reference path="../../service/HttpClient.ts"/>
///<reference path="../../lib/jquery.notific8.d.ts"/>
///<reference path="../../lib/jquery.notify.d.ts"/>
///<reference path="../../lib/jquery.jqGrid.d.ts"/>
module ums{
  import ISemester = ums;
  interface ICassRoutineScope extends ng.IScope{
    addNewRoutine: Function;
    updateRoutine: Function;
    getRoomNo: Function;
    getCourseId: Function;
    hideGrid: Function;
    fetchProgramTypeAndDeptInfo: Function;
    data: any;
    rowId: any;
    program:string;
    academicYear: string;
    academicSemester: string;
    courseTeacherSearchParamModel: CourseTeacherSearchParamModel;
    newSyllabusModel: NewSyllabusModel;
    course:Array<ICourse>;
    room:Array<IClassRooms>;
    courseData:any;
    courses:any;
    rooms:any;
    showTable:boolean;
    showGrid: boolean;
    showSelection: boolean;
  }
  interface IPrograms{
    index: number;
    programId: string;
    courses: Array<ICourse>;
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

  interface ISemester{
    semesterId: number;
    semesterName: string;
    startDate:string;
    endDate: string;
    programType: number;
    status: number;
  }

  interface IClassRooms{
    roomId: string;
    roomNo: string;
  }



  export class ClassRoutine{
    program: Array<string>;
    academicYear: string;
    academicSemester: string;
    getPrograms: Function;
    semesters:ISemester[];
    semester: string;
    semesterId: string;
    courseId;
    constantRoomNo: string;
    showGrid:boolean=true;



    public static $inject = ['appConstants','HttpClient','$scope','$q'];
    constructor(private appConstants: any, private httpClient: HttpClient, private $scope: ICassRoutineScope,private $q:ng.IQService ){

      $scope.courseTeacherSearchParamModel = new CourseTeacherSearchParamModel(this.appConstants, this.httpClient);
      $scope.showTable = false;
      $scope.showGrid = false;
      $scope.showSelection = true;
      $scope.data={
        ugPrograms : appConstants.ugPrograms,
        academicYear: appConstants.academicYear,
        academicSemester: appConstants.academicSemester,

        courseClass:{}
      }

      $scope.fetchProgramTypeAndDeptInfo = this.fetchProgramTypeAndDeptInfo.bind(this);
      $scope.getCourseId = this.getCourseId.bind(this);
      $scope.getRoomNo = this.getRoomNo.bind(this);
      $scope.hideGrid = this.hideGrid.bind(this);



    }


    private hideGrid():void{
      this.$scope.showGrid = false;
      this.$scope.showSelection = true;
    }
    private fetchProgramTypeAndDeptInfo():void{
      this.$scope.showGrid = true;
      this.$scope.showTable= true;
      this.$scope.showSelection = false;

      var lastSel,
          cancelEditing = function(myGrid) {
            var lrid;
            if (typeof lastSel !== "undefined") {
              myGrid.jqGrid('restoreRow',lastSel);
              lrid = $.jgrid.jqID(lastSel);
              $("tr#" + lrid + " div.ui-inline-edit, " + "tr#" + lrid + " div.ui-inline-del").show();
              $("tr#" + lrid + " div.ui-inline-save, " + "tr#" + lrid + " div.ui-inline-cancel").hide();
            }
          };

      var hideDelIcon = function(rowid) {
        setTimeout(function() {
          $("tr#"+$.jgrid.jqID(rowid)+ " div.ui-inline-edit").show();
          $("tr#"+$.jgrid.jqID(rowid)+ " div.ui-inline-del").show();
        },50);
      };


      var thisGrid = $("#jqGrid").jqGrid({


        url: 'https://localhost/ums-webservice-common/academic/routine/routineForEmployee/semester/'+this.$scope.courseTeacherSearchParamModel.semesterId+"/program/"+this.$scope.courseTeacherSearchParamModel.programSelector.programId+"/year/"+this.$scope.courseTeacherSearchParamModel.academicYearId+"/semester/"+this.$scope.courseTeacherSearchParamModel.academicSemesterId,

        editurl: 'https://localhost/ums-webservice-common/academic/routine',
        loadBeforeSend: function(jqXHR) {
          jqXHR.setRequestHeader("Authorization", 'Basic ZHByZWdpc3RyYXI6MTIzNDU=');
        },
        mtype: "GET",
        datatype: "json",
        jsonReader: {repeatitems:false,root:"entries"},
        colModel:[
          {
            label: 'Routine Id',
            name: 'id',
            width:10,
            hidden: true,
            key: true
          },
          {
            label:'Semester Id',
            name:'semesterId',
            width:100,
            hidden:true,
            editable:true,
            editoptions:{
              defaultValue:this.$scope.courseTeacherSearchParamModel.semesterId
            }
          },
          {
            label:'Program Id',
            name:'programId',
            width:0,
            hidden:true,
            editable:true,
            editoptions:{
              defaultValue:this.$scope.courseTeacherSearchParamModel.programSelector.programId
            }
          },
          {
            label:'Academic Year',
            name:'academicYear',
            width:0,
            hidden:true,
            editable:true,
            editoptions:{
              defaultValue:this.$scope.courseTeacherSearchParamModel.academicYearId
            }
          },
          {
            label:'Academic Semester',
            name:'academicSemester',
            width:0,
            hidden:true,
            editable:true,
            editoptions:{
              defaultValue:this.$scope.courseTeacherSearchParamModel.academicSemesterId
            }
          },
          {
            label:'Day',
            name: 'day',
            width:120,
            editable:true,
            //width:100,align:'center',formatter:'select',
            edittype:'select',
            editoptions:{
              value: this.appConstants.days,
              defaultValue:'None'
            },
            stype:'select',
            searchoptions:{
              sopt:['eq','ne'],
              value:this.appConstants.days
            },
            formatter:function (cellValue, opts, rowObject) {
              var temp = "";
              if(cellValue==1){
                temp="Saturday";
              }
              else if(cellValue==2){
                temp="Sunday";
              }
              else if(cellValue ==3){
                temp="Monday";
              }
              else if(cellValue ==4){
                temp="Tuesday";
              }
              else if(cellValue ==5){
                temp = "Wednesday";
              }
              else if(cellValue ==6){
                temp = "Thursday";
              }
              else{
                temp = "Friday";
              }
              return temp;
            }

          },

          {
            label:'Start Time',
            name:'startTime',
            width:150,
            editable:true,
            edittype:'select',
            editoptions:{
              value:this.appConstants.startTime
            },
            stype:'select',
            searchoptions:{
              sopt:['eq','ne'],
              value:this.appConstants.startTime
            }
          },
          {
            label:'End Time',
            name:'endTime',
            width:150,
            editable:true,
            edittype:'select',
            editoptions:{
              value:this.appConstants.endTime
            },
            stype:'select',
            searchoptions:{
              sopt:['eq','ne'],
              value:this.appConstants.endTime
            }
          },
          {
            label:'Course ID',
            name: 'courseId',
            width: 150,
            editable: true,
            edittype:'select',
            editoptions:{
              value:this.$scope.courses
            },
            stype:'select',
            searchoptions:{
              sopt:['eq','ne'],
              value:this.$scope.courses

            }
          },
          {
            label: 'Section',
            name:'section',
            width: 150,
            editable: true,
            edittype:'select',
            editoptions:{
              value:this.appConstants.theorySectionsGrid+this.appConstants.sessionalSectionsGrid
            }
          },
          {
            label:  'Room No',
            name: 'roomNo',
            width: 150,
            editable: true,
            edittype:'select',
            editoptions:{
              value:this.$scope.rooms   //I want to use here with that format.
            },
            stype:'select',
            searchoptions:{
              sopt:['eq','ne'],
              value: this.$scope.rooms
            }
          },
          {
            label: "Edit Actions",
            name: "actions",
            width: 100,
            formatter: "actions",
            formatoptions: {
              keys: true,
              mtype: "PUT",
              delOptions: {
                mtype: 'DELETE',
                onclickSubmit: function(rp_ge) {
                  var selrow_id = thisGrid.getGridParam('selrow');
                  var rowdata = thisGrid.getRowData(selrow_id);
                  rp_ge.url = "https://localhost/ums-webservice-common/academic/routine" + '/' + selrow_id ;
                },
                ajaxDelOptions: {
                  contentType: "application/json",
                  beforeSend: function(jqXHR) {
                    jqXHR.setRequestHeader("Authorization", 'Basic ZHByZWdpc3RyYXI6MTIzNDU=');
                  }
                },
                serializeDelData: function(postdata) {
                  return JSON.stringify(postdata);
                }
              },
              onEdit: function (id) {
                if (typeof (lastSel) !== "undefined" && id !== lastSel) {
                  cancelEditing(thisGrid);
                }
                lastSel = id;
                $("#jqGrid").setGridParam({ editurl: "https://localhost/ums-webservice-common/academic/classroom/" + encodeURIComponent(id)});
              }

            }
          }
        ],



        sortname: 'id',
        loadonce: true,
        autowidth: true,
        pager: "#jqGridPager",
        rownumbers: true,
        height:500,

        rowList: [],        // disable page size dropdown
        pgbuttons: false,     // disable page control like next, back button
        pgtext: null,

        ondblClickRow: function(id, ri, ci,e) {
          if(typeof (lastSel) !== "undefined" && id !== lastSel) {
            cancelEditing($(this));
          }
          lastSel = id;
          var lrid = $.jgrid.jqID(lastSel);
          if (!e) e = window.event; // get browser independent object
          var element = e.target || e.srcElement;


          $("#jqGrid").jqGrid('editRow',id,true,function() {
            var colModel = jQuery("#jqGrid").jqGrid ('getGridParam', 'colModel');
            var colName = colModel[ci].name;
            var input = $('#' + id + '_' + colName);

            setTimeout(function(){  input.get(0).focus(); }, 300);
          },null,"https://localhost/ums-webservice-common/academic/routine/"+ encodeURIComponent(id));
          $("tr#" + lrid + " div.ui-inline-edit, " + "tr#" + lrid + " div.ui-inline-del").hide();
          $("tr#" + lrid + " div.ui-inline-save, " + "tr#" + lrid + " div.ui-inline-cancel").show();
        }


      });


      var addOptions = {
        keys: true,
        mtype: "POST",
        url: "https://localhost/ums-webservice-common/academic/routine/",
        successfunc: function () {
          //Fire hoy na
          var $self = $(this);
          setTimeout(function () {
            alert("abc");
            $self.trigger("reloadGrid");
            alert("def");
          }, 50);
        },
        oneditfunc: function(){
          hideDelIcon("empty");
        }

      };
      $("#jqGrid").jqGrid("inlineNav", "#jqGridPager", {
        addParams: {
          position: "last",
          rowID: 'empty',
          useDefValues: true,
          addRowParams: addOptions
        }
      });

    }


    private getCourseId():void{
      var defer = this.$q.defer();
      var courseArr:Array<any>;
      this.httpClient.get('/ums-webservice-common/academic/course/semester/'+'11012015'+'/program/'+'110500', 'application/json',
          (json:any, etag:string) => {
            courseArr = json.entries;
            var courseId: string = ':None';

            for (var i in courseArr) {

              courseId = courseId + ';' + courseArr[i].no + ':' + courseArr[i].no;

            }

            this.$scope.courses = courseId;
            defer.resolve(courseArr);
          },
          (response:ng.IHttpPromiseCallbackArg<any>) => {
            console.error(response);
          });


    }

    private getRoomNo(): void{
      var defer = this.$q.defer();
      var roomArr: Array<any>;
      this.httpClient.get('academic/classroom/all','application/json',(json:any,etag:string)=>{

            roomArr = json.rows;
            var roomNumber:string=':None';
            var roomNo: Array<IClassRooms>;
            var roomN2:Array<IClassRooms>;

            roomNo = roomArr;
            var count:number=0;

            for(var i in roomNo){


              roomNumber = roomNumber+";"+roomNo[i].roomNo+":"+roomNo[i].roomNo;


            }

            this.$scope.rooms=roomNumber;
            defer.resolve(roomArr);
          },
          (response:ng.IHttpPromiseCallbackArg<any>)=>{
            console.error(response);
          });
    }

  }

  UMS.controller("ClassRoutine",ClassRoutine);
}
