module ums {
  interface IAttr extends ng.IScope {
    test123:Function;
    loadingVisibility:any;
    contentVisibility:any;
    data:any;
    attendanceSearchParamModel:any;
    showAttendanceSheet:Function;
    fetchCourseInfo:any;
    selectedCourseNo:any;
    entries:any;
  }
  export class AttendanceSheet {

    public static $inject = ['$scope', '$stateParams', 'appConstants', 'HttpClient','hotRegisterer'];
    private currentUser: LoggedInUser;

    private attendanceSearchParamModel: CourseTeacherSearchParamModel;

    constructor(private $scope: IAttr, private $stateParams: any,
                private appConstants: any, private httpClient: HttpClient,private hotRegisterer:any) {

      $scope.loadingVisibility = false;
      $scope.contentVisibility = false;
      $scope.test123=this.test123.bind(this);
      $scope.showAttendanceSheet=this.showAttendanceSheet.bind(this);
      $scope.data = {
       settings:{
         colHeaders: true,
         rowHeaders: true,
         fixedColumnsLeft: 2,
         width: $(".page-content").width(),
         height:$( window ).height()-200
       },
        items:[{'sId':'160105001','sName':'Sadia Sultana','date11012016':'Y','date21022016':'Y','date01032016':'Y','date10042016':'Y','date15052016':'Y','date22052016':'Y','date01062016':'Y','date07062016':'Y','date12062016':'Y','date25062016':'Y','date01072016':'Y','date03072016':'Y','date05072016':'Y','date07072016':'Y','date09072016':'Y','date11072016':'Y','date13072016':'Y'},
          {'sId':'160105002','sName':'Md. Ferdous Wahed','date11012016':'Y','date21022016':'Y','date01032016':'N','date10042016':'Y','date15052016':'N','date22052016':'Y','date01062016':'Y','date07062016':'Y','date12062016':'Y','date25062016':'Y','date01072016':'Y','date03072016':'Y','date05072016':'Y','date07072016':'Y','date09072016':'Y','date11072016':'Y','date13072016':'Y'},
          {'sId':'160105003','sName':'Tahsin Sarwar','date11012016':'Y','date21022016':'Y','date01032016':'Y','date10042016':'Y','date15052016':'Y','date22052016':'Y','date01062016':'Y','date07062016':'Y','date12062016':'Y','date25062016':'Y','date01072016':'Y','date03072016':'Y','date05072016':'Y','date07072016':'Y','date09072016':'Y','date11072016':'Y','date13072016':'Y'},
          {'sId':'160105004','sName':'Abid Mahmud','date11012016':'N','date21022016':'Y','date01032016':'Y','date10042016':'Y','date15052016':'Y','date22052016':'Y','date01062016':'Y','date07062016':'Y','date12062016':'Y','date25062016':'Y','date01072016':'Y','date03072016':'Y','date05072016':'Y','date07072016':'Y','date09072016':'Y','date11072016':'Y','date13072016':'Y'},
          {'sId':'160105005','sName':'Sudipta Mondal','date11012016':'Y','date21022016':'N','date01032016':'Y','date10042016':'Y','date15052016':'Y','date22052016':'Y','date01062016':'Y','date07062016':'Y','date12062016':'Y','date25062016':'Y','date01072016':'Y','date03072016':'Y','date05072016':'Y','date07072016':'Y','date09072016':'Y','date11072016':'Y','date13072016':'Y'},
          {'sId':'160105006','sName':'Md. Farhan Fardus','date11012016':'Y','date21022016':'Y','date01032016':'Y','date10042016':'Y','date15052016':'Y','date22052016':'Y','date01062016':'Y','date07062016':'Y','date12062016':'Y','date25062016':'Y','date01072016':'Y','date03072016':'Y','date05072016':'Y','date07072016':'Y','date09072016':'Y','date11072016':'Y','date13072016':'Y'},
          {'sId':'160105007','sName':'Farah Farzana Mou','date11012016':'Y','date21022016':'Y','date01032016':'N','date10042016':'Y','date15052016':'Y','date22052016':'Y','date01062016':'Y','date07062016':'Y','date12062016':'Y','date25062016':'Y','date01072016':'Y','date03072016':'Y','date05072016':'Y','date07072016':'Y','date09072016':'Y','date11072016':'Y','date13072016':'Y'},
          {'sId':'160105008','sName':'Afsana Akter','date11012016':'Y','date21022016':'Y','date01032016':'Y','date10042016':'Y','date15052016':'Y','date22052016':'Y','date01062016':'Y','date07062016':'Y','date12062016':'Y','date25062016':'Y','date01072016':'Y','date03072016':'Y','date05072016':'Y','date07072016':'Y','date09072016':'Y','date11072016':'Y','date13072016':'Y'},
          {'sId':'160105009','sName':'Md. Golam Saklaen','date11012016':'Y','date21022016':'Y','date01032016':'Y','date10042016':'Y','date15052016':'Y','date22052016':'Y','date01062016':'Y','date07062016':'Y','date12062016':'Y','date25062016':'Y','date01072016':'Y','date03072016':'Y','date05072016':'Y','date07072016':'Y','date09072016':'Y','date11072016':'Y','date13072016':'Y'},
          {'sId':'160105010','sName':'Mir Hasibul Hasan','date11012016':'Y','date21022016':'Y','date01032016':'Y','date10042016':'Y','date15052016':'Y','date22052016':'N','date01062016':'Y','date07062016':'Y','date12062016':'Y','date25062016':'Y','date01072016':'Y','date03072016':'Y','date05072016':'Y','date07072016':'Y','date09072016':'Y','date11072016':'Y','date13072016':'Y'},
          {'sId':'160105011','sName':'Syeda Meherin Haider','date11012016':'Y','date21022016':'Y','date01032016':'Y','date10042016':'Y','date15052016':'Y','date22052016':'Y','date01062016':'Y','date07062016':'Y','date12062016':'Y','date25062016':'Y','date01072016':'Y','date03072016':'Y','date05072016':'Y','date07072016':'Y','date09072016':'Y','date11072016':'Y','date13072016':'Y'},
          {'sId':'160105012','sName':'Zannatul Mashrekee Hossain Kristy','date11012016':'Y','date21022016':'Y','date01032016':'Y','date10042016':'Y','date15052016':'Y','date22052016':'Y','date01062016':'Y','date07062016':'Y','date12062016':'Y','date25062016':'Y','date01072016':'Y','date03072016':'Y','date05072016':'Y','date07072016':'Y','date09072016':'Y','date11072016':'Y','date13072016':'Y'},
          {'sId':'160105013','sName':'Ankit Dev','date11012016':'Y','date21022016':'Y','date01032016':'Y','date10042016':'N','date15052016':'Y','date22052016':'Y','date01062016':'Y','date07062016':'Y','date12062016':'Y','date25062016':'Y','date01072016':'Y','date03072016':'Y','date05072016':'Y','date07072016':'Y','date09072016':'Y','date11072016':'Y','date13072016':'Y'},
          {'sId':'160105014','sName':'Farhana Islam Chowdhury Prity','date11012016':'Y','date21022016':'Y','date01032016':'Y','date10042016':'Y','date15052016':'N','date22052016':'Y','date01062016':'Y','date07062016':'Y','date12062016':'Y','date25062016':'Y','date01072016':'Y','date03072016':'Y','date05072016':'Y','date07072016':'Y','date09072016':'Y','date11072016':'Y','date13072016':'Y'},
          {'sId':'160105015','sName':'Md Ashfaqur Rahman Khan','date11012016':'Y','date21022016':'Y','date01032016':'Y','date10042016':'Y','date15052016':'Y','date22052016':'N','date01062016':'Y','date07062016':'Y','date12062016':'Y','date25062016':'Y','date01072016':'Y','date03072016':'Y','date05072016':'Y','date07072016':'Y','date09072016':'Y','date11072016':'Y','date13072016':'Y'},
          {'sId':'160105016','sName':'Mahzabin Musfikamomo','date11012016':'N','date21022016':'Y','date01032016':'Y','date10042016':'Y','date15052016':'Y','date22052016':'Y','date01062016':'Y','date07062016':'Y','date12062016':'Y','date25062016':'Y','date01072016':'Y','date03072016':'Y','date05072016':'Y','date07072016':'Y','date09072016':'Y','date11072016':'Y','date13072016':'Y'},
          {'sId':'160105017','sName':'Miftahul Islam','date11012016':'Y','date21022016':'Y','date01032016':'Y','date10042016':'Y','date15052016':'Y','date22052016':'Y','date01062016':'Y','date07062016':'Y','date12062016':'Y','date25062016':'Y','date01072016':'Y','date03072016':'Y','date05072016':'Y','date07072016':'Y','date09072016':'Y','date11072016':'Y','date13072016':'Y'},
          {'sId':'160105018','sName':'Rahul Khan','date11012016':'Y','date21022016':'Y','date01032016':'Y','date10042016':'Y','date15052016':'Y','date22052016':'Y','date01062016':'Y','date07062016':'Y','date12062016':'Y','date25062016':'Y','date01072016':'Y','date03072016':'Y','date05072016':'Y','date07072016':'Y','date09072016':'Y','date11072016':'Y','date13072016':'Y'},
          {'sId':'160105019','sName':'Mir Hasibul Hasan','date11012016':'Y','date21022016':'Y','date01032016':'Y','date10042016':'Y','date15052016':'Y','date22052016':'N','date01062016':'Y','date07062016':'Y','date12062016':'Y','date25062016':'Y','date01072016':'Y','date03072016':'Y','date05072016':'Y','date07072016':'Y','date09072016':'Y','date11072016':'Y','date13072016':'Y'},
          {'sId':'160105020','sName':'Syeda Meherin Haider','date11012016':'Y','date21022016':'Y','date01032016':'Y','date10042016':'Y','date15052016':'Y','date22062016':'Y','date01062016':'Y','date07062016':'Y','date12062016':'Y','date25062016':'Y','date01072016':'Y','date03072016':'Y','date05072016':'Y','date07072016':'Y','date09072016':'Y','date11072016':'Y','date13072016':'Y'},
          {'sId':'160105021','sName':'Zannatul Mashrekee Hossain Kristy','date11012016':'Y','date21022016':'Y','date01032016':'Y','date10042016':'Y','date15052016':'Y','date22052016':'Y','date01062016':'Y','date07062016':'Y','date12062016':'Y','date25062016':'Y','date01072016':'Y','date03072016':'Y','date05072016':'Y','date07072016':'Y','date09072016':'Y','date11072016':'Y','date13072016':'Y'},
          {'sId':'160105022','sName':'Ankit Dev','date11012016':'Y','date21022016':'Y','date01032016':'Y','date10042016':'N','date15052016':'Y','date22052016':'Y','date01062016':'Y','date07062016':'Y','date12062016':'Y','date25062016':'Y','date01072016':'Y','date03072016':'Y','date05072016':'Y','date07072016':'Y','date09072016':'Y','date11072016':'Y','date13072016':'Y'},
          {'sId':'160105023','sName':'Farhana Islam Chowdhury Prity','date11012016':'Y','date21022016':'Y','date01032016':'Y','date10042016':'Y','date15052016':'N','date22052016':'Y','date01062016':'Y','date07062016':'Y','date12062016':'Y','date25062016':'Y','date01072016':'Y','date03072016':'Y','date05072016':'Y','date07072016':'Y','date09072016':'Y','date11072016':'Y','date13072016':'Y'},
          {'sId':'160105024','sName':'Md Ashfaqur Rahman Khan','date11012016':'Y','date21022016':'Y','date01032016':'Y','date10042016':'Y','date15052016':'Y','date22052016':'N','date01062016':'Y','date07062016':'Y','date12062016':'Y','date25062016':'Y','date01072016':'Y','date03072016':'Y','date05072016':'Y','date07072016':'Y','date09072016':'Y','date11072016':'Y','date13072016':'Y'},
          {'sId':'160105025','sName':'Mahzabin Musfikamomo','date11012016':'N','date21022016':'Y','date01032016':'Y','date10042016':'Y','date15052016':'Y','date22052016':'Y','date01062016':'Y','date07062016':'Y','date12062016':'Y','date25062016':'Y','date01072016':'Y','date03072016':'Y','date05072016':'Y','date07072016':'Y','date09072016':'Y','date11072016':'Y','date13072016':'Y'},
          {'sId':'160105026','sName':'Miftahul Islam','date11012016':'Y','date21022016':'Y','date01032016':'Y','date10042016':'Y','date15052016':'Y','date22052016':'Y','date01062016':'Y','date07062016':'Y','date12062016':'Y','date25062016':'Y','date01072016':'Y','date03072016':'Y','date05072016':'Y','date07072016':'Y','date09072016':'Y','date11072016':'Y','date13072016':'Y'},
          {'sId':'160105027','sName':'Rahul Khan','date11012016':'Y','date21022016':'Y','date01032016':'Y','date10042016':'Y','date15052016':'Y','date22052016':'Y','date01062016':'Y','date07062016':'Y','date12062016':'Y','date25062016':'Y','date01072016':'Y','date03072016':'Y','date05072016':'Y','date07072016':'Y','date09072016':'Y','date11072016':'Y','date13072016':'Y'}],
        columns: [
          {
            data: 'sId',
            title: 'Student Id'
          },
          {
            data: 'sName',
            title: 'Student Name'
          },
          {
            data: 'date11012016',
            title: '11 Jan, 16',
            renderer: this.imageRenderer
          },
          {
            data: 'date21022016',
            title: '21 Feb, 16',
            renderer: this.imageRenderer
          },
          {
            data: 'date01032016',
            title: '01 Mar, 16',
            renderer: this.imageRenderer
          },
          {
            data: 'date10042016',
            title: '10 Apr, 16',
            renderer: this.imageRenderer
          },
          {
            data: 'date15052016',
            title: '15 Mar, 16',
            renderer: this.imageRenderer
          },
          {
            data: 'date22052016',
            title: '22 May, 16',
            renderer: this.imageRenderer
          },
          {
            data: 'date01062016',
            title: '01 Jun, 16',
            renderer: this.imageRenderer
          },
          {
            data: 'date07062016',
            title: '07 Jun, 16',
            renderer: this.imageRenderer
          },
          {
            data: 'date12062016',
            title: '12 Jun, 16',
            renderer: this.imageRenderer
          },
          {
            data: 'date25062016',
            title: '25 Jun, 16',
            renderer: this.imageRenderer
          },
          {
            data: 'date01072016',
            title: '01 Jul, 16',
            renderer: this.imageRenderer
          },
          {
            data: 'date03072016',
            title: '03 Jul, 16',
            renderer: this.imageRenderer
          },
          {
            data: 'date05072016',
            title: '05 Jul, 16',
            renderer: this.imageRenderer
          },
          {
            data: 'date07072016',
            title: '07 Jul, 16',
            renderer: this.imageRenderer
          },
          {
            data: 'date09072016',
            title: '09 Jul, 16',
            renderer: this.imageRenderer
          },
          {
            data: 'date11072016',
            title: '11 Jul, 16',
            renderer: this.imageRenderer
          },
          {
            data: 'date13072016',
            title: '13 Jul, 16',
            renderer: this.imageRenderer
          }
        ]
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

    }
    private imageRenderer (instance, td, row, col, prop, value, cellProperties) {


    var value = Handsontable.helper.stringify(value),
        img;


      img = document.createElement('IMG');
      if(value=="Y")
      img.src = "https://cdn2.iconfinder.com/static/b8f60f4d8c7eba9114a36481bae51c41/assets/img/checkmark-green.png";
      else
        img.src = "https://cdn1.iconfinder.com/data/icons/silk2/cross.png";

      Handsontable.Dom.addEvent(img, 'mousedown', function (e){
        e.preventDefault(); // prevent selection quirk
      });

      Handsontable.Dom.empty(td);
      td.appendChild(img);
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