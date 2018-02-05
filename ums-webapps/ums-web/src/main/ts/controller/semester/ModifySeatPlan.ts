module ums {
  export class ModifySeatPlan {

    private roomList: ClassRoom[];
    private student: Student;
    private examType: string;
    private examRoutineList: IDateTime[];
    private selectedExamRoutine: IDateTime;
    private semester: Semester;
    private seatPlan: ISeatPlan;

    public static $inject = ['classRoomService', 'studentService', 'semesterService', 'seatPlanService', 'examRoutineService'];

    constructor(private classRoomService: ClassRoomService,
                private studentService: StudentService,
                private semesterService: SemesterService,
                private seatPlanService: SeatPlanService,
                private examRoutineService: ExamRoutineService) {

      this.initialize();
    }


    public initialize() {
      this.classRoomService.getClassRoomsForSeatPlan().then((rooms: ClassRoom[]) => {
        this.roomList = rooms;
      });

      this.semesterService.fetchSemesters(11).then((semesters: Semester[]) => {
        this.semester = semesters.filter((s: Semester) => s.status == 1)[0];
      });
      this.student = <Student>{};
      this.selectedExamRoutine = <IDateTime>{};
    }


    public search() {
      this.seatPlanService.getSeatPlanInfo(this.semester.semesterId, +this.examType, this.selectedExamRoutine.examDate, this.student.id).then((seatPlan: ISeatPlan) => {
        this.seatPlan = seatPlan;
      })
    }

    public examTypeChanged() {
      if (+this.examType == 2) {
        this.examRoutineService.getExamRoutine(this.semester.semesterId, +this.examType).then((examRoutineList: IDateTime[]) => {
          this.examRoutineList = examRoutineList;
        });
      }

    }
  }


  UMS.controller("ModifySeatPlan", ModifySeatPlan);
}