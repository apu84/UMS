module ums {
    export class Utils {
        static APPROVED: string = "#FFFFCC";
        static APPLICATION: string = "#CCFFCC";
        static APPROVED_APPLICATION: string = "#E0FFFF";
        static NONE: string = "none";
        static UG: number = 11;
        static PG: number = 22;
        static DEFAULT_SEMESTER_COUNT: number = 10;

        static SHORT_MONTH_ARR = new Array("Jan", "Feb", "Mar",
            "Apr", "May", "Jun", "Jul", "Aug", "Sep",
            "Oct", "Nov", "Dec");

        /*
         *Status code for Optional Course Status Fields
         * Database Table: Optional_Course_Application
         * Field: Status
         */
        static SCODE_APPLIED: number = 0;
        static SCODE_APPROVED: number = 1;
        static SCODE_REJECTED: number = 2;
        static SCODE_REJECTED_AND_SHIFTED: number = 3;

        /**
         * Exam Type
         * Regular 1
         * CCI 2
         */
        static EXAM_TYPE_REGULAR: number = 1;
        static EXAM_TYPE_CCI: number = 2;

        /*
        * Application Type
        * */
        static APPLICATION_TYPE_REGULAR: number = 1;
        static APPLICATION_TYPE_CLEARANCE: number = 2;
        static APPLICATION_TYPE_CARRY: number = 3;
        static APPLICATION_TYPE_SPECIAL_CARRY: number = 4;
        static APPLICATION_TYPE_IMPROVEMENT: number = 5;
        static APPLICATION_TYPE_LEAVE: number = 6;
        /*
         * Course Type*/
        static COURSE_TYPE_THEORY: number = 1;
        static COURSE_TYPE_SESSIONAL: number = 2;
        /**
         * Semester Status
         */
        static SEMESTER_STATUS_INACTIVE: number = 0;
        static SEMESTER_STATUS_ACTIVE: number = 1;
        static SEMESTER_STATUS_NEWLY_CREATED: number = 2;
        static SEMESTER_FETCH_ALL: number = 3;
        static SEMESTER_FETCH_WITH_NEWLY_CREATED: number = 4;
        /*
         * Leave Application Status
         * */
        static LEAVE_APPLICATION_SAVED: number = 1;
        static LEAVE_APPLICATION_ACCEPTED: number = 3;
        static LEAVE_APPLICATION_PENDING: number = 2;
        static LEAVE_APPLICATION_REJECTED: number = 4;

        /*
         * Leave Application Approval Status
         * */

        static LEAVE_APPLICATION_WAITING_FOR_HEADS_APPROVAL = 1;
        static LEAVE_APPLICATION_WAITING_FOR_REGISTRARS_APPROVAL = 2;
        static LEAVE_APPLICATION_REJECTED_BY_HEAD = 3;
        static LEAVE_APPLICATION_WAITING_FOR_VC_APPROVAL = 4;
        static LEAVE_APPLICATION_REJECTED_BY_REGISTRAR = 5;
        static LEAVE_APPLICATION_REJECTED_BY_VC = 6;
        static LEAVE_APPLICATION_APPROVED = 7;
        static LEAVE_APPLICATION_ALL = 8;

        /*
         * Role Types
         * */
        static REGISTRAR: number = 7201;
        static STUDENT: number = 1011;
        static S_ADMIN: number = 9999;
        static COE: number = 7301;
        static IUMS_SECRETARY: number = 9011;
        static TEACHER: number = 1021;
        static ASSISTANT_ADMINISTRATIVE_OFFICER: number = 1041;
        static ASSISTANT_ADMINISTRATIVE_OFFICER_COE: number = 7303;
        static ASSISTANT_ADMINISTRATIVE_OFFICER_REG: number = 7204;
        static ASSISTANT_ADMINISTRATIVE_OFFICER_LIB: number = 7403;
        static ASSISTANT_ADMINISTRATIVE_OFFICER_SW: number = 7602;
        static DP_REGISTRAR: number = 1022;
        static DEPT_HEAD: number = 1022;
        static VC: number = 7001;
        static DP_COE: number = 7302;
        static UG_ADMISSION_CHAIRMAN: number = 9961;
        static DEAN: number = 1023;
        static PROCTOR: number = 7401;
        static TREASURER: number = 7101;
        static UNIVERSITY_ENGINEER: number = 7801;
        static LIBRARIAN: number = 7701;


        /*
         * Department Offices
         * */
        static DEPT_MPE = '07';
        static DEPT_TE = '06';
        static DEPT_CE = '03';
        static DEPT_EEE = '05';
        static DEPT_CSE = '04';
        static DEPT_BBA = '02';
        static DEPT_ARC = '01';
        static DEPT_COE = '73';
        static DEPT_RO = '72';
        static DEPT_TO = '82';
        static DEPT_EO = '83';
        static DEPT_AOSW = '84';
        static DEPT_PO = '85';
        static DEPT_AS = 'AS';
        static DEPT_OVC = '99';
        static DEPT_ALL = '9999';

        /*
         * Present status during department selection
         *
         * */
        static ABSENT: number = 0;
        static PRESENT: number = 1;

        /*
         * Department selection type
         * */
        static MERIT_PROGRAM_SELECTED = 1;
        static MERIT_WAITING_PROGRAMS_SELECTED = 2;
        static WAITING_PROGRAM_SELECTED = 3;

        /*
         * Migration status*/
        static NOT_MIGRATED = 1;
        static MIGRATION_ABLE = 2;
        static MIGRATED = 3;

        /*
         * Quota type for admission*/
        static GENERAL = 1;
        static FREEDOM_FIGHTER = 2;
        static REMOTE_AREA = 3;
        static ENGLISH_MEDIUM = 4;

        /*
         *Payment Types
         * */
        static ADMISSION_FEE = 1;
        static MIGRATION_FEE = 2;

        /*
         * Payment Modes*/
        static CASH = 1;
        static DEMAND_NOTE = 2;
        static PAY_ORDER = 3;


        /*Fee Type*/
        static SEMESTER_FEE = 1;
        static CERTIFICATE_FEE = 2;
        static DUES = 3;
        static PENALTY = 4;
        static OTHER_FEE = 0;
        static DEPT_CERTIFICATE_FEE = 5;
        static REG_CERTIFICATE_FEE = 6;
        static PROC_CERTIFICATE_FEE = 7;

        /**
         * Student Id block colors (Advisor Assignment from Admin Officer Account)
         */
        static DEFAULT_COLOR: string = "#DEF";
        static SELECTED_COLOR: string = "#FADBD8";

        /**
         * Grade Submission Yellow Error Row Color
         */
        static ERROR_ROW: string = "#FCDC3B";
        static SEARCH_ROW: string = "#A2D9CE";


        /**
         * Dropdown(SelectBox) Default Selected Value
         */
        static NUMBER_SELECT: number = 101101;
        static STRING_SELECT: string = "";
        /**
         * Theory Sections
         */
        static THEORY_SECTION_A: string = "A";
        static THEORY_SECTION_B: string = "B";
        static THEORY_SECTION_C: string = "C";
        static THEORY_SECTION_D: string = "D";

        public static findIndex(source_arr: Array<any>, element_value: string): number {
            var targetIndex = -1;
            for (var i = 0; i < source_arr.length; i++) {
                if (source_arr[i].id == element_value) {
                    targetIndex = i;
                    break;
                }
            }
            return targetIndex;
        }

        /*
         This is how you should use this function.
         var index = findIndexByKeyValue(studentsArray, "name", "tanmay");
         alert(index);
         */
        public static findIndexByProperty(arrayToSearch: Array<any>, property, valueToSearch) {
            for (var i = 0; i < arrayToSearch.length; i++) {
                if (arrayToSearch[i][property] == valueToSearch) {
                    return i;
                }
            }
            return null;
        }

        public static arrayMaxIndex(array: Array<any>): number {
            var val: number = 0;
            if (array.length != 0)
                val = Math.max.apply(Math, array.map(function (o) {
                    return o.index;
                })) + 1;
            return val;
        }

        public static getSessionalSection(theorySectionName: string): any {
            if (theorySectionName == this.THEORY_SECTION_A) {
                return Constants.Default().sessionalSectionsA;
            } else if (theorySectionName == this.THEORY_SECTION_B) {
                return Constants.Default().sessionalSectionsB;
            } else if (theorySectionName == this.THEORY_SECTION_C) {
                return Constants.Default().sessionalSectionsC;
            } else {
                return Constants.Default().sessionalSectionsD;
            }
        }

        public static expandRightDiv() {
            $("#leftDiv").hide(100);
            $("#arrowDiv").show(50);
        }

        public static setValidationOptions(formClass: string): void {
            $("." + formClass).validate({
                errorPlacement: function (error, element) {
                    error.insertAfter(element);
                },
                unhighlight: function (element, errorClass) {
                    var $element = $(element);
                    $element.siblings(".error_status").removeClass("check");
                },
                success: function (label, element) {
                    label.parent().removeClass('state-error');
                    label.remove();
                }
            });
        }

        /**
         * Get Id from location Header URL
         */
        public static getIdFromUrl(url: string): string {
            var resourceUrl = url;
            var startIndex = url.lastIndexOf('/') + 1;
            var lastIndex = resourceUrl.length;
            return url.substring(startIndex, lastIndex);
        }

        /**
         * Set Select2 Value from parentElement and Search Term
         */
        public static setSelect2Value(parentElementId: string, targetElementId: string, searchTerm: string) {
            $('#' + parentElementId).find('.select2-input').each(function (index) {
                let inputElement: any = $(this)[0];
                let inputElementId = inputElement.id;

                $("#" + targetElementId).select2("search", searchTerm);
                let e = jQuery.Event("keydown");
                e.which = 13;
                $("#" + inputElementId).trigger(e);
            });
        }

        public static getDateObject(dateString: string): Date {
            let dateArray: string[] = dateString.split("-" || "/");
            let newDate = dateArray[1] + "/" + dateArray[0] + "/" + dateArray[2];
            return new Date(newDate);
        }

        public static convertFromJacksonDate(dateString: string): string {
            let dateArray: string[] = dateString.split("-" || "/");
            let newDate = dateArray[2] + "-" + dateArray[1] + "-" + dateArray[0];
            return newDate;
        }

        public static getYearRange(): any {
            let yearArray = [];
            let startingYear = 1900;
            let currentYear = new Date().getFullYear();
            for (let i = startingYear; i <= currentYear; i++) {
                yearArray.push(i);
            }
            return yearArray;
        }

        public static getDateDiffInDays(fromDate: string, toDate: string, bias: number): number{
            let b = moment(fromDate, 'DD/MM/YYYY'),
                a = moment(toDate, 'DD/MM/YYYY'),
                intervals = ['years','months','days'],
                out = [];

            a.add(bias, 'days');
            return a.diff(b, 'days');
        }

        public static getFormattedDateDiff(fromDate: string, toDate: string, bias: number): string{
            let b = moment(fromDate, 'DD/MM/YYYY'),
                a = moment(toDate, 'DD/MM/YYYY'),
                intervals = ['years','months','days'],
                out = [];

                a.add(bias, 'days');

                for (let i = 0; i < intervals.length; i++) {
                    let diff = a.diff(b, intervals[i]);
                    b.add(diff, intervals[i]);
                    out.push(diff + ' ' + intervals[i]);
                }
                return out.join(' ');
        }

        public static validateDateInput(fromDate: string, toDate: string, bias: number): boolean{
            let b = moment(fromDate, 'DD/MM/YYYY'),
                a = moment(toDate, 'DD/MM/YYYY');
            return a.add(bias, 'days').isAfter(b);
        }

        public static getFormattedCurrentDate(): string{
            let today = new Date();
            let dd = today.getDate();
            let mm = today.getMonth()+1; //January is 0!
            let modifiedDD = "";
            let modifiedMM = "";
            let modifiedDate = "";

            let yyyy = today.getFullYear();
            if(dd < 10){
                modifiedDD = '0' + dd;
            }
            else{
                modifiedDD = dd.toString();
            }
            if(mm < 10){
                modifiedMM = '0' + mm;
            }
            else{
                modifiedMM = mm.toString();
            }
            modifiedDate = modifiedDD + '/' + modifiedMM + '/' + yyyy;
            return modifiedDate;
        }
    }
}