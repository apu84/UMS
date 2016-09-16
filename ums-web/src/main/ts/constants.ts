module ums {
  export class Constants {
    static  Default():any {
      return {
        Empty: "",
        initDept: [{id: '', name: 'Select Dept./School'}],
        initProgram: [{id: '', longName: 'Select a Program'}],
        initSemester: [{id: '', name: 'Select a Semester'}],
        initSyllabus: [{id: '', semester_name: 'Select a ', program_name: 'Syllabus'}],
        semesterStatus: [
          {id: '', name: 'Select Semester Status'},
          {id: '0', name: 'Inactive'},
          {id: '1', name: 'Active'},
          {id: '2', name: 'Newly Created'}
        ],
        courseType: [
          {id: '', name: 'Select Course Type'},
          {id: '1', name: 'Theory'},
          {id: '2', name: 'Sessional'},
          {id: '3', name: 'Thesis/Project'}
        ],
        courseCategory: [
          {id: '', name: 'Select Course Category'},
          {id: '1', name: 'Mandatory'},
          {id: '2', name: 'Optional'}
        ],
        academicYear: [
          {id: '', name: 'Select Year'},
          {id: '1', name: '1st Year'},
          {id: '2', name: '2nd Year'},
          {id: '3', name: '3rd Year'},
          {id: '4', name: '4th Year'}
        ],
        academicSemester: [
          {id: '', name: 'Select Semester'},
          {id: '1', name: '1st Semester'},
          {id: '2', name: '2nd Semester'}
        ],
        semesterType: [
          {id: '', name: 'Select Semester'},
          {id: '01', name: 'Spring'},
          {id: '02', name: 'Fall'}
        ],
        gender: [
          {id: '', name: 'Select Gender'},
          {id: 'M', name: 'Male'},
          {id: 'F', name: 'Female'}
        ],
        programType: [
          {id: '', name: 'Select Program Type'},
          {id: '11', name: 'Undergraduate'},
          {id: '22', name: 'Postgraduate'}
        ],
        deptShort: [
          {id: '', name: 'Select Dept./School'},
          {id: '01', name: 'ARC'},
          {id: '02', name: 'BBA'},
          {id: '03', name: 'CE'},
          {id: '04', name: 'CSE'},
          {id: '05', name: 'EEE'},
          {id: '06', name: 'TE'},
          {id: '07', name: 'MPE'}
        ],
        deptLong: {
          '01': 'Department of Architecture',
          '02': 'School of Business',
          '03': 'Department of Civil Engineering',
          '04': 'Department of Computer Science and Engineering',
          '05': 'Department of Electrical and Electronic Engineering',
          '06': 'Department of Textile Engineering',
          '07': 'Department of Mechanical & Production Engineering'
        },
        dept4JqGridSelectBox: ':None;01:ARC;02:BBA;03:CE;04:CSE;05:EEE;06:TE;07:MPE',
        days: ':None;1:Saturday;2:Sunday;3:Monday;4:Tuesday;5:Wednesday;6:Thursday;7:Friday',
        startTime: ':None;08.00 AM:08.00 AM;08.50 AM:08.50 AM;09.40 AM:09.40 AM;10.30 AM:10.30 AM;11.20 AM:11.20 AM;12.10 PM:12.10 PM;01.00 PM:01.00 PM;01.50 PM:01.50 PM;02.40 PM:02.40 PM;03.30 PM:03.30 PM;04.20 PM:04.20 PM;05.10 PM:05.10 PM;06.00 PM:06.00 PM',
        endTime: ':None;08.00 AM:08.00 AM;08.50 AM:08.50 AM;09.40 AM:09.40 AM;10.30 AM:10.30 AM;11.20 AM:11.20 AM;12.10 PM:12.10 PM;01.00 PM:01.00 PM;01.50 PM:01.50 PM;02.40 PM:02.40 PM;03.30 PM:03.30 PM;04.20 PM:04.20 PM;05.10 PM:05.10 PM;06.00 PM:06.00 PM',

        day: {
          '01': 'SATURDAY',
          '02': 'SUNDAY',
          '03': 'MONDAY',
          '04': 'TUESDAY',
          '05': 'WEDNESDAY',
          '06': 'THURSDAY',
          '07': 'FRIDAY'
        },
        weekday: {
          1: 'SATURDAY',
          2: 'SUNDAY',
          3: 'MONDAY',
          4: 'TUESDAY',
          5: 'WEDNESDAY',
          6: 'THURSDAY',
          7: 'FRIDAY'
        },
        officeShort: {
          '80': 'RO',
          '81': 'CO',
          '82': 'TO',
          '83': 'EO',
          '84': 'AoSW',
          '85': 'PO'
        },
        officeLong: {
          '80': 'Office of the Registrar',
          '81': 'Office of the Controller of Examinations',
          '82': 'Office of the Treasurer',
          '83': 'Engineering Office',
          '84': "Office of the Advisor of Student''s Welfare",
          '85': 'Proctor Office'
        },
        status: {
          '0': 'Inactive',
          '1': 'Active'
        },
        examTime: [
          {id: '9:30 a.m. to 12:30 p.m', name: '9:30 a.m. to 12:30 p.m'}
        ],
        bloodGroup: [
          {id: '', name: 'Select Blood Group'},
          {id: 'O+', name: 'O+'},
          {id: 'O–', name: 'O-'},
          {id: 'A+', name: 'A+'},
          {id: 'A–	', name: 'A-'},
          {id: 'B+', name: 'B+'},
          {id: 'B–', name: 'B-'},
          {id: 'AB+', name: 'AB+'},
          {id: 'AB–', name: 'AB-'}
        ],
        ugDept: [
          {id: '', name: 'Select Dept./School'},
          {id: '01', name: 'ARC'},
          {id: '02', name: 'BBA'},
          {id: '03', name: 'CE'},
          {id: '04', name: 'CSE'},
          {id: '05', name: 'EEE'},
          {id: '06', name: 'TE'},
          {id: '07', name: 'MPE'}
        ],
        pgDept: [
          {id: '', name: 'Select Dept./School'},
          {id: '01', name: 'ARC'},
          {id: '02', name: 'BBA'},
          {id: '05', name: 'EEE'}
        ],
        timeChecker: [
          {
            id: '08.00 AM',
            val: '08.50 AM'
          },
          {
            id: '08.50 AM',
            val: '09.40 AM'
          },
          {
            id: '09.40 AM',
            val: '10.30 AM'
          },
          {
            id: '10.30 AM',
            val: '11.20 AM'
          },
          {
            id: '11.20 AM',
            val: '12.10 PM'
          },
          {
            id: '12.10 PM',
            val: '01.00 PM'
          },
          {
            id: '01.00 PM',
            val: '01.50 PM'
          },
          {
            id: '01.50 PM',
            val: '02.40 PM'
          },
          {
            id: '02.40 PM',
            val: '03.30 PM'
          },
          {
            id: '03.30 PM',
            val: '04.20 PM'
          },
          {
            id: '04.20 PM',
            val: '05.00 PM'
          }
        ],
        routineTime: [
          {
            counter: '1',
            id: '08.00 AM'
          },
          {
            counter: '2',
            id: '08.50 AM'
          },
          {
            counter: '3',
            id: '09.40 AM'
          },
          {
            counter: '4',
            id: '10.30 AM'
          },
          {
            counter: '5',
            id: '11.20 AM'
          },
          {
            counter: '6',
            id: '12.10 PM'
          },
          {
            counter: '7',
            id: '01.00 PM'
          },
          {
            counter: '8',
            id: '01.50 PM'
          },

          {
            counter: '9',
            id: '02.40 PM'
          },
          {
            counter: '10',
            id: '03.30 PM'
          },
          {
            counter: '11',
            id: '04.20 PM'
          },
          {
            counter: '12',
            id: '05.10 PM'
          }
        ],
        weekDays: [
          {
            id: '01',
            day: 'Saturday'
          },
          {
            id: '02',
            day: 'Sunday'
          },
          {
            id: '03',
            day: 'Monday'
          },
          {
            id: '04',
            day: 'Tuesday'
          },
          {
            id: '05',
            day: 'Wednesday'
          },
          {
            id: '06',
            day: 'Thursday'
          }
        ],
        ugPrograms: [
          {
            deptId: '01',
            programs: [
              {
                id: "110100",
                shortName: "BSC in ARC",
                longName: "Bachelor in Arch. Engineering"
              }
            ]
          },
          {
            deptId: '02',
            programs: [
              {
                id: "110200",
                shortName: "BBA",
                longName: "Bachelor in Business Administration"
              }
            ]
          },
          {
            deptId: "03",
            programs: [
              {
                id: "110300",
                shortName: "BSC in CE",
                longName: "Bachelor in Civil Engineering"
              }
            ]
          },
          {
            deptId: "04",
            programs: [
              {
                id: "110400",
                shortName: "BSC in CSE",
                longName: "Bachelor in Computer Science and Engineering"
              }
            ]
          },
          {
            deptId: "05",
            programs: [
              {
                id: "110500",
                shortName: "BSC in EEE",
                longName: "Bachelor in Electrical and Electronic Engineering"
              }
            ]
          },
          {
            deptId: "06",
            programs: [
              {
                id: "110600",
                shortName: "BSC in TE",
                longName: "Bachelor in Textile Engineering"
              }
            ]
          },
          {
            deptId: "07",
            programs: [
              {
                id: "110707",
                shortName: "BSC in IPE",
                longName: "Bachelor in Industrial and Production Engineering"
              },
              {
                id: "110708",
                shortName: "BSC in ME",
                longName: "Bachelor in Mechanical Engineering"
              }
            ]
          }
        ],
        pgPrograms: [],
        theorySectionsGrid: ":None;A:A;B:B;C:C;D:D",
        theorySections: [
          {id: "A", name: "A"},
          {id: "B", name: "B"},
          {id: "C", name: "C"},
          {id: "D", name: "D"}
        ],
        sessionalSectionsGrid: ":NONE;A1:A1;B1:B1;C1:C1;D1:D2;A2:A2;B2:B2;C2:C2;D2:D2",
        sessionalSectionsGrid2: ";A1:A1;B1:B1;C1:C1;D1:D2;A2:A2;B2:B2;C2:C2;D2:D2",
        sessionalSections: [
          {id: "A1", name: "A1"},
          {id: "B1", name: "B1"},
          {id: "C1", name: "C1"},
          {id: "D1", name: "D1"},
          {id: "A2", name: "A2"},
          {id: "B2", name: "B2"},
          {id: "C2", name: "C2"},
          {id: "D2", name: "D2"}
        ],
        mimeTypeJson: 'application/json',
        mimeTypePdf: 'application/pdf',
        semesterEnrollmentTypes: [
          {id: "", name: "Select Enrollment Type"},
          {id: "0", name: "Temporary"},
          {id: "1", name: "Permanent"}
        ],
        applicationTypes: [
          {id: "", name: "Select Application Type"},
          {id: "0", name: "Academic"},
          {id: "1", name: "Semester Withdraw"},
          {id: "2", name: "Optional Course Selection"}
        ],
        actors: [
          {id: "0", name: "Student"},
          {id: "1", name: "Head"},
          {id: "2", name: "Deputy Registrar"},
          {id: "3", name: "Registrar"},
          {id: "4", name: "Assistant Admin Officer"},
          {id: "5", name: "Vice Chancellor"}
        ],
        applicationStatus: [
          {id: "0", name: "Saved"},
          {id: "1", name: "Submitted"},
          {id: "2", name: "Approved"},
          {id: "3", name: "Rejected"}
        ],
        responseTypes: {
          'ERROR': 'ERROR',
          'SUCCESS': 'SUCCESS',
          'INFO': 'INFO',
          'WARN': 'WARN'
        },
        gradeLetters: [
          {id: "A+", name: "A+"},
          {id: "A", name: "A"},
          {id: "A-", name: "A-"},
          {id: "B+", name: "B+"},
          {id: "B", name: "B"},
          {id: "B-", name: "B-"},
          {id: "C+", name: "C+"},
          {id: "C", name: "C"},
          {id: "D", name: "D"},
          {id: "F", name: "F"}
        ],
        marksSubmissionStatus: [
          {id: "-1", name: "All"},
          {id: "0", name: "Not Submitted"},
          {id: "1", name: "Waiting for Scrutiny"},
          {id: "2", name: "Requested for recheck by Scrutinizer"},
          {id: "3", name: "Waiting for Head's Approval"},
          {id: "4", name: "Requested for recheck by Head"},
          {id: "5", name: "Waiting for CoE's Approval"},
          {id: "6", name: "Requested for recheck by CoE"},
          {id: "7", name: "Accepted by CoE"},
          {id: "8", name: "Waiting for recheck request approval"}
        ],
        marksSubmissionStatusEnum: { //Course Marks Submission Status
          NOT_SUBMITTED: 0,
          WAITING_FOR_SCRUTINY: 1,
          REQUESTED_FOR_RECHECK_BY_SCRUTINIZER: 2,
          WAITING_FOR_HEAD_APPROVAL: 3,
          REQUESTED_FOR_RECHECK_BY_HEAD: 4,
          WAITING_FOR_COE_APPROVAL: 5,
          REQUESTED_FOR_RECHECK_BY_COE: 6,
          ACCEPTED_BY_COE: 7,
          WAITING_FOR_RECHECK_REQUEST_APPROVAL: 8
        },
        marksStatusEnum: { //Student's Marks Status  slkjasdljflasdfjlj
          NONE: 0,
          SUBMIT: 1,
          SUBMITTED: 2,
          SCRUTINIY: 3,
          SCRUTINIZED: 4,
          APPROVE: 5,
          APPROVED: 6,
          ACCEPT: 7,
          ACCEPTED: 8
        },
        regType:{
          REGULAR:1,
          CLEARANCE:2,
          CARRY:3,
          SPECIAL_CARRY:4,
          IMPROVEMENT:5
        },
        regColorCode:{
          CLEARANCE:"#BBFFFF",
          CARRY:"#CCCCFF",
          SPECIAL_CARRY:"#00FF00",
          IMPROVEMENT:"#FFF68F"
        },
        programTypeEnum:{
          UG:11,
          PG:22
        }

      }
    }
  }
}