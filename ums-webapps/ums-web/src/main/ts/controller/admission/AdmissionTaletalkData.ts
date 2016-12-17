/**
 * Created by My Pc on 17-Dec-16.
 */

module ums{
  interface IAdmissionTaletalkData extends ng.IScope{

  }

  class AdmissionTaletalkData{
    public static $inject = ['appConstants','HttpClient','$scope','$q','notify','$sce','$window','semesterService','facultyService', 'admissionStudentService'];
    constructor(private appConstants: any,
                private httpClient: HttpClient,
                private $scope: IAdmissionTaletalkData,
                private $q:ng.IQService,
                private notify: Notify,
                private $sce:ng.ISCEService,
                private $window:ng.IWindowService,
                private semesterService: SemesterService,
                private facultyService: FacultyService,
                private admissionStudentService:AdmissionStudentService) {

    }
  }

  UMS.controller("AdmissionTaletalkData", AdmissionTaletalkData);
}