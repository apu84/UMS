module ums {
    export class ModifySeatPlan {

        private roomList: ClassRoom[];
        private roomMapWithId: any;
        private selectedRoom: ClassRoom;
        private student: Student;
        private examType: string;
        private examRoutineList: IDateTime[];
        private distinctExamRoutineList: IDateTime[];
        private selectedExamRoutine: IDateTime;
        private modifiedSelectedExamRoutine: IDateTime;
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
                console.log("Rooms");
                console.log(rooms);
                this.roomList = rooms;
                this.roomMapWithId = {};
                rooms.forEach((room: ClassRoom) => this.roomMapWithId[room.id] = room);
            });

            this.semesterService.fetchSemesters(11).then((semesters: Semester[]) => {
                this.semester = semesters.filter((s: Semester) => s.status == 1)[0];
                console.log(this.semester);
            });
            this.student = <Student>{};
            this.selectedExamRoutine = <IDateTime>{};
        }


        public search() {
            this.seatPlanService.getSeatPlanByStudent(this.semester.id, +this.examType, this.modifiedSelectedExamRoutine.examDate, this.student.id).then((seatPlan: ISeatPlan) => {
                this.seatPlan = seatPlan;
                console.log("selected room");

                this.selectedRoom = this.roomMapWithId[seatPlan.roomId];
                console.log(this.selectedRoom);
            })
        }

        public examTypeChanged() {
            if (+this.examType == 2) {
                this.selectedExamRoutine = <IDateTime>{};
                this.examRoutineService.getExamRoutine(this.semester.id, +this.examType).then((examRoutineList: IDateTime[]) => {
                    this.examRoutineList = examRoutineList;
                }).then(() => {
                    this.getUniqueExamRoutineList();
                })
            }
        }

        public getUniqueExamRoutineList(): void {
            this.distinctExamRoutineList = [];
            this.distinctExamRoutineList = this.examRoutineList.filter((val, index) => {
                return this.examRoutineList.map(mapObj => mapObj['examDate']).indexOf(val['examDate']) === index;
            });
        }

        public update() {
            this.seatPlan.roomId = this.selectedRoom.id;
            this.seatPlanService.updateSeatPlan(this.seatPlan);
        }

        public modifyDateFormat(): void {
            this.modifiedSelectedExamRoutine = <IDateTime>{};
            this.modifiedSelectedExamRoutine.examDate = this.selectedExamRoutine.examDate.split("/").join("-");
        }
    }


    UMS.controller("ModifySeatPlan", ModifySeatPlan);
}