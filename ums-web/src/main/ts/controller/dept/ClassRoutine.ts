///<reference path="../../service/HttpClient.ts"/>
///<reference path="../../lib/jquery.notific8.d.ts"/>
///<reference path="../../lib/jquery.notify.d.ts"/>
///<reference path="../../lib/jquery.jqGrid.d.ts"/>
///<reference path="../../grid/GridDecorator.ts"/>
module ums {
  import ISemester = ums;
  interface ICassRoutineScope extends ng.IScope {
    addNewRoutine: Function;
    updateRoutine: Function;
    getRoomNo: Function;
    getCourseId: Function;
    hideGrid: Function;
    fetchGrid: Function;
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
    courseArr:Array<ICourse>;
    sectionsOnChange: string;
    rooms:any;
    showTable:boolean;
    showGrid: boolean;
    showSelection: boolean;

    //New Implementation
    classRoutineData: Array<RowData>;
    resetDivs: void;
  }

  interface ICourse {
    readOnly: boolean;
    index: number;
    id: string;
    no: string;
    title: string;
    year: number;
    type:string;
    semester: number;
  }

  interface IClassRooms {
    roomId: string;
    roomNo: string;
  }


  export class ClassRoutine implements GridEditActions {
    program: Array<string>;
    academicYear: string;
    academicSemester: string;
    semester: string;
    semesterId: string;
    courseId: string;

    public static $inject = ['appConstants', 'HttpClient', '$scope', '$q'];

    constructor(private appConstants: any, private httpClient: HttpClient, private $scope: ICassRoutineScope, private $q: ng.IQService) {

      $scope.courseTeacherSearchParamModel = new CourseTeacherSearchParamModel(this.appConstants, this.httpClient);
      $scope.showTable = false;
      $scope.showGrid = false;
      $scope.showSelection = true;
      $scope.data = {
        ugPrograms: appConstants.ugPrograms,
        academicYear: appConstants.academicYear,
        academicSemester: appConstants.academicSemester,
        courseClass: {}
      };

      $scope.fetchGrid = this.fetchGrid.bind(this);
      $scope.getCourseId = this.getCourseId.bind(this);
      $scope.getRoomNo = this.getRoomNo.bind(this);
      $scope.hideGrid = this.hideGrid.bind(this);
      $scope.resetDivs = this.resetDivs.bind(this);
    }


    private hideGrid(): void {
      this.$scope.showGrid = false;
      this.$scope.showSelection = true;
    }

    private fetchGrid(): void {
      $("#leftDiv").hide();
      $("#arrowDiv").show();
      $("#rightDiv").removeClass("orgRightClass").addClass("newRightClass");

      this.$scope.showGrid = true;
      this.$scope.showTable = true;
      this.$scope.showSelection = true;
      this.loadData();

    }


    private getCourseId(): void {
      var defer = this.$q.defer();
      var courseArr: Array<ICourse>;
      this.httpClient.get('/ums-webservice-common/academic/course/semester/'
          + this.$scope.courseTeacherSearchParamModel.semesterId
          + '/program/'
          + this.$scope.courseTeacherSearchParamModel.programSelector.programId,
          HttpClient.MIME_TYPE_JSON,
          (json: any) => {
            courseArr = json.entries;
            var courseId: string = ':None';
            this.$scope.courseArr = courseArr;

            for (var i = 0; i < courseArr.length; i++) {
              courseId = courseId + ';' + courseArr[i].id + ':' + courseArr[i].no;
            }

            this.$scope.courses = courseId;

            defer.resolve(courseArr);
          },
          (response: ng.IHttpPromiseCallbackArg<any>) => {
            console.error(response);
          });


    }

    private getRoomNo(): void {
      var defer = this.$q.defer();
      var roomArr: Array<any>;
      this.httpClient.get('academic/classroom/all', 'application/json',
          (json: any)=> {

            roomArr = json.rows;
            var roomNumber: string = ':None';
            var roomNo: Array<IClassRooms>;

            roomNo = roomArr;

            for (var i = 0; i < roomNo.length; i++) {
              roomNumber = roomNumber + ";" + roomNo[i].roomNo + ":" + roomNo[i].roomNo;
            }

            this.$scope.rooms = roomNumber;
            defer.resolve(roomArr);
          },
          (response: ng.IHttpPromiseCallbackArg<any>)=> {
            console.error(response);
          });
    }

    private initializeGrid(): void {
      GridDecorator.decorate(this);
    }

    public insert(rowData: RowData): void {
      this.decorateScope().grid.api.toggleMessage('Saving...');
      this.httpClient.post('academic/routine', rowData, HttpClient.MIME_TYPE_JSON).success(()=> {
        this.decorateScope().grid.api.toggleMessage();
        this.loadData();
      }).error((response) => {
        console.error(response);
        this.decorateScope().grid.api.toggleMessage();
        this.loadData();
      });
    }

    public edit(rowData: RowData): void {
      this.decorateScope().grid.api.toggleMessage('Saving...');
      this.httpClient.put('academic/routine', rowData, HttpClient.MIME_TYPE_JSON)
          .success(() => {
            this.decorateScope().grid.api.toggleMessage();
          }).error((response) => {
            console.error(response);
            this.decorateScope().grid.api.toggleMessage();
            this.loadData();
          });
    }

    public remove(rowData: RowData): void {
      this.httpClient.delete('academic/routine/' + rowData)
          .success(() => {
            $.notific8("Removed entry");
          }).error((data) => {
            console.error(data);
          });
    }
    public beforeEditForm(formId: string, gridElement: JQuery): void {
    }

    public afterShowEditForm(formId: String, gridElement: JQuery): void {
    }

    public decorateScope(): GridConfig {
      return this.$scope;
    }

    public getColumnModel(): any {
      var states = {
        'A': 'A',
        'B': 'B',
        'C': 'C',
        'D': 'D',
        'A1': 'A1',
        'A2': 'A2',
        'B1': 'B1',
        'B2': 'B2',
        'C1': 'C1',
        'C2': 'C2',
        'D1': 'D1',
        'D2': 'D2'
      };
      var typesOfTheoryAndSessional = {
        1: {'A': 'A', 'B': 'B', 'C': 'C', 'D': 'D'},
        2: {'A1': 'A1', 'A2': 'A2', 'B1': 'B1', 'B2': 'B2', 'C1': 'C1', 'C2': 'C2', 'D1': 'D1', 'D2': 'D2'}
      };

      return [
        {
          label: 'Routine Id',
          name: 'id',
          hidden: true,
          key: true
        },
        {
          label: 'Semester Id',
          name: 'semesterId',
          hidden: true,
          editable: true,
          editrules: {edithidden: false},
          editoptions: {
            defaultValue: this.$scope.courseTeacherSearchParamModel.semesterId
          }
        },
        {
          label: 'Program Id',
          name: 'programId',
          hidden: true,
          editable: true,
          editrules: {edithidden: false},
          editoptions: {
            defaultValue: this.$scope.courseTeacherSearchParamModel.programSelector.programId
          }
        },
        {
          label: 'Academic Year',
          name: 'academicYear',
          hidden: true,
          editable: true,
          editrules: {edithidden: false},
          editoptions: {
            defaultValue: this.$scope.courseTeacherSearchParamModel.academicYearId
          }
        },
        {
          label: 'Academic Semester',
          name: 'academicSemester',
          hidden: true,
          editable: true,
          editrules: {edithidden: false},
          editoptions: {
            defaultValue: this.$scope.courseTeacherSearchParamModel.academicSemesterId
          }
        },
        {
          label: 'Day',
          name: 'day',
          editable: true,
          edittype: 'select',
          editoptions: {
            value: this.appConstants.days,
            defaultValue: 'None'
          },
          formatter: 'select'

        },
        {
          label: 'Course ID',
          name: 'courseId',
          editable: true,
          edittype: 'select',
          formatter: 'select',
          editoptions: {
            value: this.$scope.courses,

            dataEvents: [
              {
                type: 'change',

                fn: (e) => {
                  var sectionsOnChanges;
                  var v = $(e.target).val();
                  this.isTheory(v) ? sectionsOnChanges = 1 : sectionsOnChanges = 2;
                  var sc = typesOfTheoryAndSessional[sectionsOnChanges];

                  var newOptions = '';
                  for (var stateId in sc) {
                    if (sc.hasOwnProperty(stateId)) {
                      newOptions += '<option role="option" value="' + stateId + '">'
                      + states[stateId]
                      + '</option>';
                    }

                  }

                  if ($(e.target).is('.FormElement')) {
                    // form editing
                    var form = $(e.target).closest('form.FormGrid');
                    $("select#section.FormElement", form[0]).html(newOptions);
                  } else {
                    // inline editing
                    var row = $(e.target).closest('tr.jqgrow');
                    var rowId = row.attr('id');
                    $("select#" + rowId + "_section", row[0]).html(newOptions);
                  }
                  this.$scope.sectionsOnChange = sectionsOnChanges;
                }
              }
            ]
          }
        },
        {
          label: 'Section',
          name: 'section',
          editable: true,
          edittype: 'select',
          formatter: 'select',
          editoptions: {
            value: states
          }
        },
        {
          label: 'Start Time',
          name: 'startTime',
          editable: true,
          edittype: 'select',
          editoptions: {
            value: this.appConstants.startTime
          },
          stype: 'select',
          searchoptions: {
            sopt: ['eq', 'ne'],
            value: this.appConstants.startTime
          }
        },
        {
          label: 'End Time',
          name: 'endTime',
          editable: true,
          edittype: 'select',
          editoptions: {
            value: this.appConstants.endTime
          },
          stype: 'select',
          searchoptions: {
            sopt: ['eq', 'ne'],
            value: this.appConstants.endTime
          }
        },
        {
          label: 'Room No',
          name: 'roomNo',
          editable: true,
          edittype: 'select',
          editoptions: {
            value: this.$scope.rooms   //I want to use here with that format.
          },
          stype: 'select',
          searchoptions: {
            sopt: ['eq', 'ne'],
            value: this.$scope.rooms
          }
        }
      ];
    }


    private isTheory(id: string): boolean {
      for (var i = 0; i < this.$scope.courseArr.length; i++) {
        if (id == this.$scope.courseArr[i].id) {
          if (this.$scope.courseArr[i].type == "THEORY") {
            return true;
          }
        }
      }
      return false;
    }

    private resetDivs() {
      $("#arrowDiv").hide();
      $("#leftDiv").show();
      $("#rightDiv").removeClass("newRightClass").addClass("orgRightClass");
      //this.decoratedScope.grid.api.resize();
    }

    private loadData(): void {
      this.httpClient.get("academic/routine/routineForEmployee/semester/"
          + this.$scope.courseTeacherSearchParamModel.semesterId
          + "/program/"
          + this.$scope.courseTeacherSearchParamModel.programSelector.programId
          + "/year/"
          + this.$scope.courseTeacherSearchParamModel.academicYearId
          + "/semester/"
          + this.$scope.courseTeacherSearchParamModel.academicSemesterId, HttpClient.MIME_TYPE_JSON,
          (response) => {
            this.initializeGrid();

            this.$scope.classRoutineData = response.entries;
          });
    }

    public loadComplete():any{
      alert("ifti");
    }
  }

  UMS.controller("ClassRoutine", ClassRoutine);
}
