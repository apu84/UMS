module ums {
    export class Constants {
        static Default(): any {
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
                paymentMode: [
                    {id: 1, name: 'Cash'},
                    {id: 2, name: 'Demand note'},
                    {id: 3, name: 'Pay order'}
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
                    {id: '4', name: '4th Year'},
                    {id: '5', name: '5th Year'}
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
                weekday: [
                    {id: "1", name: 'Saturday'},
                    {id: "2", name: 'Sunday'},
                    {id: "3", name: 'Monday'},
                    {id: "4", name: 'Tuesday'},
                    {id: "5", name: 'Wednesday'},
                    {id: "6", name: 'Thursday'}
                ],
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
                        id: '',
                        val: 'Select time'
                    },
                    {
                        id: '08:00 AM',
                        val: '08:00 AM'
                    },
                    {
                        id: '08:50 AM',
                        val: '08:50 AM'
                    },
                    {
                        id: '09:40 AM',
                        val: '09:40 AM'
                    },
                    {
                        id: '10:30 AM',
                        val: '10:30 AM'
                    },
                    {
                        id: '11:20 AM',
                        val: '11:20 AM'
                    },
                    {
                        id: '12:10 PM',
                        val: '12:10 PM'
                    },
                    {
                        id: '01:00 PM',
                        val: '01:00 PM'
                    },
                    {
                        id: '01:50 PM',
                        val: '01:50 PM'
                    },
                    {
                        id: '02:40 PM',
                        val: '02:40 PM'
                    },
                    {
                        id: '03:30 PM',
                        val: '03:30 PM'
                    },
                    {
                        id: '04:20 PM',
                        val: '04:20 PM'
                    },
                    {
                        id: '05:00 PM',
                        val: '05:00 PM'
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
                    },
                    {
                        deptId: "07-ForExamRoutine",
                        programs: [
                            {
                                id: "110708",
                                shortName: "BSC in ME",
                                longName: "Bachelor in Mechanical Engineering"
                            }
                        ]
                    }
                ],
                pgPrograms: [],
                ugProgramMap: {
                    '110100': ['BSC in ARC'],
                    '110200': ['BBA'],
                    '110300': ['BSc in CE'],
                    '110400': ['BSc in CSE'],
                    '110500': ['BSc in EEE'],
                    '110600': ['BSc in TE'],
                    '110707': ['BSc in IPE'],
                    '110708': ['BSc in ME']
                },
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
                sessionalSectionsA: [
                    {id: 'A1', name: 'A1'},
                    {id: 'A2', name: 'A2'}
                ],
                sessionalSectionsB: [
                    {id: 'B1', name: 'B1'},
                    {id: 'B2', name: 'B2'}
                ], sessionalSectionsC: [
                    {id: 'C1', name: 'C1'},
                    {id: 'C2', name: 'C2'}
                ], sessionalSectionsD: [
                    {id: 'D1', name: 'D1'},
                    {id: 'D2', name: 'D2'}
                ],
                mimeTypeJson: 'application/json',
                mimeTypePdf: 'application/pdf',
                semesterEnrollmentTypes: [
                    {id: "", name: "Select Enrollment Type"},
                    {id: "0", name: "Temporary"},
                    {id: "1", name: "Permanent"}
                ],
                meritListTypes: [
                    {id: "1", name: "GL"},
                    {id: "2", name: "FF"},
                    {id: "3", name: "RA"},
                    {id: "4", name: "GCE"}
                ],
                quotaTypes: [
                    {id: "1", name: "General "},
                    {id: "2", name: "Freedom Fighter "},
                    {id: "3", name: "Remote Area "},
                    {id: "4", name: "English Medium "}
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
                courseRegType: {
                    REGULAR: 1,
                    CLEARANCE: 2,
                    CARRY: 3,
                    SPECIAL_CARRY: 4,
                    IMPROVEMENT: 5
                },
                regColorCode: {
                    CLEARANCE: "#BBFFFF",
                    CARRY: "#CCCCFF",
                    SPECIAL_CARRY: "#00FF00",
                    IMPROVEMENT: "#FFF68F"
                },
                programTypeEnum: {
                    UG: 11,
                    PG: 22
                },
                yearSemester: [
                    {id: "0", name: "Select Year-Semester"},
                    {id: "11", name: "1-1"},
                    {id: "12", name: "1-2"},
                    {id: "21", name: "2-1"},
                    {id: "22", name: "2-2"},
                    {id: "31", name: "3-1"},
                    {id: "32", name: "3-2"},
                    {id: "41", name: "4-1"},
                    {id: "42", name: "4-2"}
                ],
                leaveApprovalStatus: [
                    {id: 1, name: "Waiting for head's approval"},
                    {id: 2, name: "Waiting For Registrar's Approval"},
                    {id: 3, name: "Rejected By Head"},
                    {id: 4, name: "Waiting For VC's Approval"},
                    {id: 5, name: "Rejected By Registrar"},
                    {id: 6, name: "Rejected by VC"},
                    {id: 7, name: "Application Approved"},
                    {id: 8, name: "All"}
                ],
                departmentOffice: [
                    {id: '01', name: 'Department of Architecture'},
                    {id: '02', name: 'School of Business'},
                    {id: '03', name: 'Department of Civil Engineering'},
                    {id: '04', name: 'Department of Computer Science and Engineering'},
                    {id: '05', name: 'Department of Electrical and Electronic Engineering'},
                    {id: '06', name: 'Department of Textile Engineering'},
                    {id: '07', name: 'Department of Mechanical & Production Engineering'},
                    {id: '15', name: 'Department of Arts and Sciences'},
                    {id: '70', name: 'Office of VC'},
                    {id: '71', name: 'Office of the Treasurer'},
                    {id: '72', name: 'Office of the Registrar'},
                    {id: '73', name: 'Office of the Controller of Examinations'},
                    {id: '74', name: 'Proctor Office'},
                    {id: '75', name: 'Engineering Office'},
                    {id: '76', name: "Office of the Advisor of Student's Welfare"},
                    {id: '77', name: "Kazi Fazlur Rahman Library"}
                ],
                certificateStatus: [
                    {id: 1, name: 'Applied'},
                    {id: 2, name: 'Processed'},
                    {id: 3, name: 'Delivered'},
                    {id: 4, name: "Waiting for Head's Approval"},
                    {id: 5, name: "Forwarded by Head"}
                ],
                employeeTypes: [
                    {id: 1, name: "Teacher"},
                    {id: 2, name: "Officer"},
                    {id: 3, name: "Staff"},
                    {id: 9, name: "Top Management"}
                ],
                academicEmployeeTypes: [
                    {id: 1, name: "Teacher"},
                    {id: 2, name: "Officer"},
                    {id: 3, name: "Staff"}
                ],
                officialEmployeeTypes: [
                    {id: 2, name: "Officer"},
                    {id: 3, name: "Staff"},
                    {id: 9, name: "Top Management"}
                ],
                MARKS_SUBMISSION_STATUS: {
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
                TASK_STATUS: {
                    INPROGRESS: "INPROGRESS",
                    COMPLETED: "COMPLETED",
                    NONE: "NONE"
                },
                RESULT_PROCESS_STATUS: {
                    IN_PROGRESS: {
                        id: 1,
                        label: "Process in progress"
                    },
                    PROCESSED_ON: {
                        id: 2,
                        label: "Processed on"
                    },
                    UNPROCESSED: {
                        id: 3,
                        label: "Unprocessed"
                    },
                    READY_TO_BE_PROCESSED: {
                        id: 4,
                        label: "Ready to be processed"
                    },
                    STATUS_UNDEFINED: {
                        id: 5,
                        label: "Status undefined"
                    },
                    RESULT_PUBLISH_INPROGRESS: {
                        id: 6,
                        label: "Result publish in progress"
                    },
                    RESULT_PUBLISHED: {
                        id: 7,
                        label: "Result published on"
                    }
                },
                deptAll: {
                    id: '-1',
                    label: 'All'
                },
                programAll: {
                    id: -1,
                    label: 'All'
                }
            }
        }

        static LeaveConstants(): any {
            return {
                leaveApprovalStatus: [
                    {id: 1, name: "Waiting for head's approval"},
                    {id: 2, name: "Waiting For Registrar's Approval"},
                    {id: 3, name: "Rejected by Head"},
                    {id: 4, name: "Waiting for VC's approval"},
                    {id: 5, name: "Rejected By Registrar"},
                    {id: 6, name: "Rejected by VC"},
                    {id: 7, name: "Application Approved"}
                ]
            }
        }

        static LibConstant(): any {
            return {
                languages: [
                    {id: 101101, name: 'Select a Language'},
                    {id: 1, name: 'English'},
                    {id: 2, name: 'Bangla'},
                    {id: 3, name: 'French'},
                    {id: 4, name: 'Chinese'},
                    {id: 5, name: 'Russian'},
                    {id: 6, name: 'Spanish'},
                    {id: 7, name: 'German'},
                    {id: 8, name: 'Japanese'},
                    {id: 9, name: 'Hindi'},
                    {id: 10, name: 'Urdu'},
                    {id: 11, name: 'Arabic'},
                    {id: 12, name: 'Chinese'},
                    {id: 13, name: 'Sanskrit'}
                ],
                bindingTypes: [
                    {id: 101101, name: 'Select Binding Type'},
                    {id: 1, name: 'Hard Bound'},
                    {id: 2, name: 'Soft Cover'},
                    {id: 3, name: 'Paperback'},
                    {id: 4, name: 'Clip binding'},
                    {id: 5, name: 'Gum paste binding'},
                    {id: 6, name: 'Cloth binding'}
                ],
                acquisitionTypes: [
                    {id: 101101, name: 'Select Acquisition Type'},
                    {id: 1, name: 'Purchase'},
                    {id: 2, name: 'Donation'}
                ],
                libContributorRoles: [
                    {id: 101101, name: 'Select a Role'},
                    {id: 1, name: 'Author'},
                    {id: 2, name: 'Co-Author'},
                    {id: 3, name: 'Editor'},
                    {id: 4, name: 'Compiler'},
                    {id: 5, name: 'Translator'},
                    {id: 6, name: 'Composer'},
                    {id: 7, name: 'Illustrator'},
                    {id: 8, name: 'Cartographer'},
                    {id: 9, name: 'Corporate Author'}
                ],
                recordStatus: [
                    {id: 101101, name: 'Select Status'},
                    {id: 0, name: 'Entry Mode'},
                    {id: 2, name: 'Available'}
                ],
                itemStatus: [
                    {id: 101101, name: 'Select a Status'},
                    {id: 2, name: 'Available'},
                    {id: 3, name: 'On Hold'}
                ],
                materialTypes: [
                    {id: 101101, name: 'Select Material Type'},
                    {id: 1, name: 'Book'},
                    {id: 2, name: 'Journal'},
                    {id: 3, name: 'Thesis/Project'}
                ], /*
                 materialTypes: [
                 {id: '1', name: 'Books'},
                 {id: '2', name: 'Journals'},
                 {id: '3', name: 'Thesis/Project '},
                 {id: '4', name: 'Manuscripts'},
                 {id: '5', name: 'CD-DVD'},
                 {id: '6', name: 'Cartographic'},
                 {id: '7', name: 'Graphic'},
                 ],*/
                journalFrequency: [
                    {id: 101101, name: 'Select Frequency'},
                    {id: 1, name: 'Weekly'},
                    {id: 15, name: 'Bi-Weekly'},
                    {id: 30, name: 'Monthly '},
                    {id: 360, name: 'Yearly'}
                ]
            }
        }

        static RegistrarConstant(): any {
            return {
                genderTypes: [
                    {id: 'M', name: 'Male'},
                    {id: 'F', name: 'Female'}
                ],
                religionTypes: [
                    {id: 1, name: "Islam"},
                    {id: 2, name: "Buddhism"},
                    {id: 3, name: "Hinduism"},
                    {id: 4, name: "Jainism"},
                    {id: 5, name: "Judaism"},
                    {id: 6, name: "Sikhism"},
                    {id: 99, name: "Others"}
                ],
                nationalityTypes: [
                    {id: 1, name: "Bangladeshi"},
                    {id: 99, name: "Others"}
                ],
                bloodGroupTypes: [
                    {id: 1, name: "A+"},
                    {id: 2, name: "A-"},
                    {id: 3, name: "B+"},
                    {id: 4, name: "B-"},
                    {id: 5, name: "AB+"},
                    {id: 6, name: "AB-"},
                    {id: 7, name: "O+"},
                    {id: 8, name: "O-"}
                ],
                publicationTypes: [
                    {id: 1, name: "Conference Proceedings"},
                    {id: 2, name: "Journal Article"},
                    {id: 3, name: "Book"},
                    {id: 4, name: "Others"}
                ],
                maritalStatuses: [
                    {id: 1, name: "Single"},
                    {id: 2, name: "Married"}
                ],
                degreeTypes: [
                    {id: 1, name: "SSC/O-Level"},
                    {id: 2, name: "HSC/A-Level"},
                    {id: 3, name: "Bachelor"},
                    {id: 4, name: "Master's"},
                    {id: 5, name: "PhD"}
                ],
                relationTypes: [
                    {id: 1, name: "Spouse"},
                    {id: 2, name: "Aunt"},
                    {id: 3, name: "Brother"},
                    {id: 4, name: "Brother-in-law"},
                    {id: 5, name: "Colleague"},
                    {id: 6, name: "Cousin"},
                    {id: 7, name: "Daughter"},
                    {id: 8, name: "Daughter-in-law"},
                    {id: 9, name: "Employee"},
                    {id: 10, name: "Father"},
                    {id: 11, name: "Father-in-law"},
                    {id: 12, name: "Friend"},
                    {id: 13, name: "Grand Father"},
                    {id: 14, name: "Grand Mother"},
                    {id: 15, name: "Grand Son"},
                    {id: 16, name: "Mother"},
                    {id: 17, name: "Mother-in-law"},
                    {id: 18, name: "Nephew"},
                    {id: 19, name: "Niece"},
                    {id: 20, name: "Sister"},
                    {id: 21, name: "Sister-in-law"},
                    {id: 22, name: "Son"},
                    {id: 23, name: "Son-in-law"},
                    {id: 99, name: "Others"}
                ],
                servicePeriods: [
                    {id: 1, name: "Contractual"},
                    {id: 2, name: "Probation"},
                    {id: 3, name: "Permanent"},
                    {id: 4, name: "Contract"}
                ],
                meetingTypes: [
                    {id: 10, name: "Board Of Trustees Meeting"},
                    {id: 20, name: "Syndicate Meeting"},
                    {id: 30, name: "Finance Committee Meeting"},
                    {id: 40, name: "Academic Council Meeting"},
                    {id: 50, name: "Heads Meeting Meeting"}
                ],
                trainingCategories: [
                    {id: 10, name: "Local"},
                    {id: 20, name: "Foreign"}
                ],
                experienceCategories: [
                    {id: 10, name: "Job"},
                    {id: 20, name: "Research"}
                ]
            }
        }
    }
}
