module ums {
  export class Constants {
    static  Default():any {
      return {
        Empty:"",
        initDept: [{id:'',name:'Select Dept./School'}],
        initProgram: [{id: '', longName: 'Select a Program'}],
        initSemester: [{id: '', name: 'Select a Semester'}],
        initSyllabus: [{id: '', semester_name: 'Select a ',program_name:'Syllabus'}],
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
        days:':None;01:Saturday;02:Sunday;03:Monday;04:Tuesday;05:Wednesday;06:Thursday;07:Friday',
        startTime:':None;08.00 AM:08.00 AM;08.50 AM:08.50 AM;09.40 AM:09.40 AM;10.30 AM:10.30 AM;11.20 AM:11.20 AM;12.10 PM:12.10 PM;01.00 PM:1.00 PM;01.50 PM:1.50 PM;02.40 PM:2.40 PM;03.30 PM:3.30 PM;04.20 PM:4.20 PM;05.10 PM:5.10 PM;06.00 PM:6.00 PM',
        endTime:':None;08.00 AM:08.00 AM;08.50 AM:08.50 AM;09.40 AM:09.40 AM;10.30 AM:10.30 AM;11.20 AM:11.20 AM;12.10 PM:12.10 PM;01.00 PM:1.00 PM;01.50 PM:1.50 PM;02.40 PM:2.40 PM;03.30 PM:3.30 PM;04.20 PM:4.20 PM;05.10 PM:5.10 PM;06.00 PM:6.00 PM',

        day:{
          '01':'SATURDAY',
          '02':'SUNDAY',
          '03':'MONDAY',
          '04':'TUESDAY',
          '05':'WEDNESDAY',
          '06':'THURSDAY',
          '07':'FRIDAY'
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
        theorySectionsGrid:":None;A:A;B:B;C:C;D:D",
        theorySections: [
          {id: "A", name: "A"},
          {id: "B", name: "B"},
          {id: "C", name: "C"},
          {id: "D", name: "D"}
        ],
        sessionalSectionsGrid:";A1:A1;B1:B1;C1:C1;D1:D2;A2:A2;B2:B2;C2:C2;D2:D2",
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
        mimeTypeJson : 'application/json',
        mimeTypePdf : 'application/pdf'
      };
    }
  }
}