module ums {
    
  export class ClassRoutine  {

    private selectedSemester: Semester;
    private semesterList: Semester[];
    private programType: string;
    private ACTIVE_STATUS:number=1;
    private NEWLY_CREATED_STATUS:number=2;
    private UNDERGRADUATE:string='11';
    private POSTGRADUATE:string='22';
    private studentsYear:string;
    private studentsSemester: string;
    private selectedTheorySection:IParameter;
    private theorySectionList:IParameter[];

    public static $inject = ['appConstants','HttpClient','$q','notify','$sce','$window','semesterService','courseService','classRoomService','classRoutineService','$timeout'];
    constructor(private appConstants: any, private httpClient: HttpClient, 
                private $q:ng.IQService, private notify: Notify,
                private $sce:ng.ISCEService,private $window:ng.IWindowService, private semesterService:SemesterService,
                private courseService:CourseService, private classRoomService:ClassRoomService, private classRoutineService:ClassRoutineService,private $timeout : ng.ITimeoutService) {

        this.init();
    }

    private init(){
        this.programType = this.UNDERGRADUATE;
        this.theorySectionList = this.appConstants.theorySections;
        this.selectedTheorySection = this.theorySectionList[0];
        this.fetchSemesters();
    }

    public fetchSemesters(){
        this.semesterService.fetchSemesters(+this.programType).then((semesterList: Semester[])=>{
            this.semesterList=[];
            this.selectedSemester=<Semester>{};
            for(let i:number=0; i<semesterList.length; i++){
                if(semesterList[i].status==this.ACTIVE_STATUS)
                    this.selectedSemester = semesterList[i];
                this.semesterList.push(semesterList[i]);
            }
            console.log(this.semesterList);
        });
    }

  }

  UMS.controller("ClassRoutine", ClassRoutine);
}