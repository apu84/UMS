module ums {
  interface Course {
    id: string;
    no: string;
    title: string;
    crhr: string;
    type: string;
    category: string;
    year: string;
    semester: string;
    groupName: string;
  }

  interface GroupMap {
    [optionalGroup: string] : Array<Course>
  }

  interface SemesterCourseMap {
    [semester: string]: GroupMap
  }

  interface CourseMap {
    [year: string] : SemesterCourseMap
  }

  export class FullSyllabus {
    public static $inject = ['$scope', 'HttpClient', '$stateParams'];

    constructor(private $scope: any, private httpClient: HttpClient,
                private $stateParams) {
      $(".portlet").each(function (index, element) {
        var me = $(this);
        $(">.portlet-header>.tools>i", me).click(function (e) {
          if ($(this).hasClass('fa-chevron-up')) {
            $(">.portlet-body", me).slideUp('fast');
            $(this).removeClass('fa-chevron-up').addClass('fa-chevron-down');
          }
          else if ($(this).hasClass('fa-chevron-down')) {
            $(">.portlet-body", me).slideDown('fast');
            $(this).removeClass('fa-chevron-down').addClass('fa-chevron-up');
          }
          else if ($(this).hasClass('fa-cog')) {
            //Show modal
          }
          else if ($(this).hasClass('fa-refresh')) {
            $(">.portlet-body", me).addClass('wait');

            setTimeout(function () {
              $(">.portlet-body", me).removeClass('wait');
            }, 1000);
          }
          else if ($(this).hasClass('fa-times')) {
            me.remove();
          }
        });
      });

      this.httpClient.get('academic/course/syllabus/' + this.$stateParams.syllabusId, 'application/json',
          (data: any, etag: string) => {
            var courses: Array<Course> = data.entries;
            this.$scope.courseMap = this.formatCourses(courses);
            console.debug(this.$scope.courseMap);
          });
    }

    private formatCourses(courses: Array<Course>): CourseMap {
      var courseMap: CourseMap = {};
      var optionalCourses: GroupMap;

      for (var i = 0; i < courses.length; i++) {

        var year: string = FullSyllabus.getSuffix(courses[i].year);
        var semester: string = FullSyllabus.getSuffix(courses[i].semester);
        var optionalGroup: string = courses[i].groupName ? courses[i].groupName : " "+courses[i].category;

        if (courses[i].semester == '0') {
          if (!optionalCourses) {
            optionalCourses = {};
          }
          if (!optionalCourses[optionalGroup]) {
            optionalCourses[optionalGroup] = [];
          }
          optionalCourses[optionalGroup][optionalCourses[optionalGroup].length] = courses[i];
        }
        else {
          if (!courseMap[year]) {
            courseMap[year] = {};
          }
          if (!courseMap[year][semester]) {
            courseMap[year][semester] = {};
          }
          if (!courseMap[year][semester][optionalGroup]) {
            courseMap[year][semester][optionalGroup] = [];
          }

          courseMap[year][semester][optionalGroup][courseMap[year][semester][optionalGroup].length] = courses[i];
        }
      }
      if (optionalCourses) {
        courseMap['Optional courses']= {};
        courseMap['Optional courses'][''] = optionalCourses;
      }
      return courseMap;
    }

    //TODO: Move this function to utility class
    private static getSuffix(n: string): string {
      var suffix: string = "th";
      switch (parseInt(n)) {
        case 1:
          suffix = "st";
          break;
        case 2:
          suffix = "nd";
          break;
        case 3:
          suffix = "rd";
          break;
        default :
          suffix = "th";
          break;
      }

      return n + "" + suffix;
    }
  }
  UMS.controller('FullSyllabus', FullSyllabus);
}
