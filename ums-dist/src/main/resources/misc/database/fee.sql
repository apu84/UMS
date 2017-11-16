CREATE TABLE DB_IUMS.CERTIFICATE_STATUS
(
  ID              NUMBER,
  STUDENT_ID      VARCHAR2(9 BYTE),
  SEMESTER_ID     NUMBER,
  FEE_CATEGORY    NUMBER,
  TRANSACTION_ID  VARCHAR2(40 BYTE),
  STATUS          INTEGER,
  PROCESSED_ON    DATE,
  PROCESSED_BY    VARCHAR2(20 BYTE),
  LAST_MODIFIED   VARCHAR2(18 BYTE)
);

CREATE TABLE DB_IUMS.FEE
(
  ID               NUMBER,
  FEE_CATEGORY_ID  VARCHAR2(30 BYTE),
  SEMESTER_ID      NUMBER,
  FACULTY_ID       NUMBER,
  AMOUNT           NUMBER,
  LAST_MODIFIED    VARCHAR2(18 BYTE)
);

CREATE TABLE DB_IUMS.FEE_CATEGORY
(
  FEE_ID         VARCHAR2(50 BYTE),
  NAME           VARCHAR2(100 BYTE),
  DESCRIPTION    VARCHAR2(300 BYTE),
  TYPE           NUMBER,
  LAST_MODIFIED  VARCHAR2(18 BYTE),
  ID             VARCHAR2(3 BYTE)
);

CREATE TABLE DB_IUMS.FEE_TYPE
(
  ID             NUMBER,
  DESCRIPTION    VARCHAR2(50 BYTE),
  LAST_MODIFIED  VARCHAR2(18 BYTE),
  NAME           VARCHAR2(20 BYTE)
);

CREATE TABLE DB_IUMS.INSTALLMENT_SETTINGS
(
  ID             NUMBER,
  SEMESTER_ID    NUMBER,
  IS_ENABLED     INTEGER,
  LAST_MODIFIED  VARCHAR2(18 BYTE)
);

CREATE TABLE DB_IUMS.INSTALLMENT_STATUS
(
  ID                 NUMBER,
  STUDENT_ID         VARCHAR2(9 BYTE),
  SEMESTER_ID        NUMBER,
  INSTALLMENT_ORDER  INTEGER,
  PAYMENT_COMPLETED  INTEGER,
  RECEIVED_ON        DATE,
  LAST_MODIFIED      VARCHAR2(18 BYTE)
);

CREATE TABLE DB_IUMS.LATE_FEE
(
  ID              NUMBER,
  FROM_DATE       DATE,
  TO_DATE         DATE,
  FEE             NUMBER,
  SEMESTER_ID     NUMBER,
  LAST_MODIFIED   VARCHAR2(18 BYTE),
  ADMISSION_TYPE  NUMBER
);

CREATE TABLE DB_IUMS.PAYMENT_ACCOUNT_MAPPING
(
  ID               NUMBER,
  FEE_TYPE         INTEGER,
  ACCOUNT          VARCHAR2(50 BYTE),
  ACCOUNT_DETAILS  VARCHAR2(500 BYTE),
  FACULTY          INTEGER,
  LAST_MODIFIED    VARCHAR2(18 BYTE)
);

CREATE TABLE DB_IUMS.PAYMENT_STATUS
(
  ID                 NUMBER,
  ACCOUNT            VARCHAR2(50 BYTE),
  TRANSACTION_ID     VARCHAR2(50 BYTE),
  METHOD_OF_PAYMENT  INTEGER,
  STATUS             INTEGER,
  RECEIVED_ON        DATE,
  COMPLETED_ON       DATE,
  PAYMENT_DETAILS    VARCHAR2(500 BYTE),
  LAST_MODIFIED      VARCHAR2(18 BYTE),
  AMOUNT             NUMBER,
  RECEIPT_NO         VARCHAR2(20 BYTE)
);

CREATE TABLE DB_IUMS.READMISSION_APPLICATION
(
  ID             NUMBER,
  STUDENT_ID     VARCHAR2(9 BYTE),
  SEMESTER_ID    NUMBER,
  COURSE_ID      VARCHAR2(25 BYTE),
  STATUS         INTEGER,
  APPLIED_ON     DATE,
  LAST_MODIFIED  VARCHAR2(18 BYTE)
);

CREATE TABLE DB_IUMS.SEMESTER_ADMISSION_STATUS
(
  ID             NUMBER,
  STUDENT_ID     VARCHAR2(9 BYTE),
  SEMESTER_ID    NUMBER,
  IS_ADMITTED    INTEGER,
  LAST_MODIFIED  VARCHAR2(18 BYTE)
);

CREATE TABLE DB_IUMS.STUDENT_DUES
(
  ID              NUMBER,
  FEE_CATEGORY    VARCHAR2(3 BYTE),
  DESCRIPTION     VARCHAR2(500 BYTE),
  STUDENT_ID      VARCHAR2(9 BYTE),
  AMOUNT          NUMBER,
  ADDED_ON        DATE,
  ADDED_BY        VARCHAR2(20 BYTE),
  PAY_BEFORE      DATE,
  TRANSACTION_ID  VARCHAR2(40 BYTE),
  LAST_MODIFIED   VARCHAR2(18 BYTE),
  STATUS          INTEGER
);

CREATE TABLE DB_IUMS.STUDENT_PAYMENT
(
  ID                      NUMBER,
  TRANSACTION_ID          VARCHAR2(40 BYTE),
  STUDENT_ID              VARCHAR2(9 BYTE),
  SEMESTER_ID             NUMBER,
  AMOUNT                  NUMBER,
  STATUS                  NUMBER,
  APPLIED_ON              DATE,
  VERIFIED_ON             DATE,
  TRANSACTION_VALID_TILL  DATE,
  LAST_MODIFIED           VARCHAR2(18 BYTE),
  FEE_CATEGORY            VARCHAR2(3 BYTE)
);

ALTER TABLE MST_PARAMETER_SETTING DROP COLUMN PS_ID;
ALTER TABLE MST_PARAMETER_SETTING ADD (PS_ID NUMBER);

UPDATE MST_PARAMETER_SETTING t1
   SET ps_id =
          (SELECT ROUND (
                       DBMS_RANDOM.VALUE (1, 1000)
                     * t1.semester_id
                     * t1.parameter_id)
                     num
             FROM DUAL);

Insert into DB_IUMS.FEE_CATEGORY
   (FEE_ID, NAME, DESCRIPTION, TYPE, LAST_MODIFIED,
    ID)
 Values
   ('ADMISSION', 'Admission fee', 'Admisssion fee', 1, '201602141037',
    '1');
Insert into DB_IUMS.FEE_CATEGORY
   (FEE_ID, NAME, DESCRIPTION, TYPE, LAST_MODIFIED,
    ID)
 Values
   ('REGISTRATION', 'Registration fee', 'Registration fee required during admission', 1, '201602141037',
    '2');
Insert into DB_IUMS.FEE_CATEGORY
   (FEE_ID, NAME, DESCRIPTION, TYPE, LAST_MODIFIED,
    ID)
 Values
   ('ESTABLISHMENT', 'Establishment fee', 'Establishment fee', 1, '201602141037',
    '3');
Insert into DB_IUMS.FEE_CATEGORY
   (FEE_ID, NAME, DESCRIPTION, TYPE, LAST_MODIFIED,
    ID)
 Values
   ('TUITION', 'Tuition fee', 'Tuition fee', 1, '201602141037',
    '4');
Insert into DB_IUMS.FEE_CATEGORY
   (FEE_ID, NAME, DESCRIPTION, TYPE, LAST_MODIFIED,
    ID)
 Values
   ('LABORATORY', 'Laboratory fee', 'Laboratory fee', 1, '201602141037',
    '5');
Insert into DB_IUMS.FEE_CATEGORY
   (FEE_ID, NAME, DESCRIPTION, TYPE, LAST_MODIFIED,
    ID)
 Values
   ('READMISSION', 'Re-admission fee', 'Re-admission fee', 1, '201602141037',
    '6');
Insert into DB_IUMS.FEE_CATEGORY
   (FEE_ID, NAME, DESCRIPTION, TYPE, LAST_MODIFIED,
    ID)
 Values
   ('IMPROVEMENT', 'Improvememt exam fee', 'Improvement exam fee', 1, '201602141037',
    '7');
Insert into DB_IUMS.FEE_CATEGORY
   (FEE_ID, NAME, DESCRIPTION, TYPE, LAST_MODIFIED,
    ID)
 Values
   ('CARRY', 'Carry over exam fee', 'Carry over exam fee', 1, '201602141037',
    '8');
Insert into DB_IUMS.FEE_CATEGORY
   (FEE_ID, NAME, DESCRIPTION, TYPE, LAST_MODIFIED,
    ID)
 Values
   ('THEORY_REPEATER', 'Theory course fee', 'Tuition fee for theory courses (readmission)', 1, '201602141037',
    '9');
Insert into DB_IUMS.FEE_CATEGORY
   (FEE_ID, NAME, DESCRIPTION, TYPE, LAST_MODIFIED,
    ID)
 Values
   ('SESSIONAL_REPEATER', 'Sessional course fee', 'Tuition fee for sessional courses (readmission)', 1, '201602141037',
    '10');
Insert into DB_IUMS.FEE_CATEGORY
   (FEE_ID, NAME, DESCRIPTION, TYPE, LAST_MODIFIED,
    ID)
 Values
   ('THEORY', 'Theory course fee', 'Tuition fee for theory courses', 1, '201602141037',
    '11');
Insert into DB_IUMS.FEE_CATEGORY
   (FEE_ID, NAME, DESCRIPTION, TYPE, LAST_MODIFIED,
    ID)
 Values
   ('SESSIONAL', 'Sessional course fee', 'Tuition fee for sessional course', 1, '201602141037',
    '12');
Insert into DB_IUMS.FEE_CATEGORY
   (FEE_ID, NAME, DESCRIPTION, TYPE, LAST_MODIFIED,
    ID)
 Values
   ('INSTALLMENT_CHARGE', 'Installment charge', 'Installment charge', 1, '201602141037',
    '13');
Insert into DB_IUMS.FEE_CATEGORY
   (FEE_ID, NAME, DESCRIPTION, TYPE, LAST_MODIFIED,
    ID)
 Values
   ('GRADESHEET_PROVISIONAL', 'Gradesheet charge', 'Semester final gradesheet (Provisional)', 2, '201602141037',
    '14');
Insert into DB_IUMS.FEE_CATEGORY
   (FEE_ID, NAME, DESCRIPTION, TYPE, LAST_MODIFIED,
    ID)
 Values
   ('GRADESHEET_DUPLICATE', 'Duplicate Gradesheet charge', 'Semeter final gradesheet (Duplicate)', 2, '201602141037',
    '15');
Insert into DB_IUMS.FEE_CATEGORY
   (FEE_ID, NAME, DESCRIPTION, TYPE, LAST_MODIFIED,
    ID)
 Values
   ('PROVISIONAL_CERTIFICATE_INITIAL', 'Provisional certifcate charge', 'Provisional certifcate (Initial)', 2, '201602141037',
    '16');
Insert into DB_IUMS.FEE_CATEGORY
   (FEE_ID, NAME, DESCRIPTION, TYPE, LAST_MODIFIED,
    ID)
 Values
   ('PROVISIONAL_CERTIFICATE_DUPLICATE', 'Provisional certifcate charge', 'Provisional certifcate (Duplicate)', 2, '201602141037',
    '17');
Insert into DB_IUMS.FEE_CATEGORY
   (FEE_ID, NAME, DESCRIPTION, TYPE, LAST_MODIFIED,
    ID)
 Values
   ('TRANSCRIPT_INITIAL', 'Transcript charge', 'Transcript charge (Initial)', 2, '201602141037',
    '18');
Insert into DB_IUMS.FEE_CATEGORY
   (FEE_ID, NAME, DESCRIPTION, TYPE, LAST_MODIFIED,
    ID)
 Values
   ('TRANSCRIPT_DUPLICATE', 'Transcript charge', 'Transcript charge (Duplicate)', 2, '201602141037',
    '19');
Insert into DB_IUMS.FEE_CATEGORY
   (FEE_ID, NAME, DESCRIPTION, TYPE, LAST_MODIFIED,
    ID)
 Values
   ('CERTIFICATE_CONVOCATION', 'Original certificate', 'Original certificate (Without convocation)', 2, '201602141037',
    '20');
Insert into DB_IUMS.FEE_CATEGORY
   (FEE_ID, NAME, DESCRIPTION, TYPE, LAST_MODIFIED,
    ID)
 Values
   ('CERTIFICATE_DUPLICATE', 'Original certificate', 'Original certificate (Duplicate)', 2, '201602141037',
    '21');
Insert into DB_IUMS.FEE_CATEGORY
   (FEE_ID, NAME, DESCRIPTION, TYPE, LAST_MODIFIED,
    ID)
 Values
   ('LATE_FEE', 'Late fee', 'Late fee', 1, '201602141037',
    '22');
Insert into DB_IUMS.FEE_CATEGORY
   (FEE_ID, NAME, DESCRIPTION, TYPE, LAST_MODIFIED,
    ID)
 Values
   ('INSTALLMENT_CHARGE', 'Installment charge', 'Installment charge', 1, '201602141037',
    '23');
Insert into DB_IUMS.FEE_CATEGORY
   (FEE_ID, NAME, DESCRIPTION, TYPE, LAST_MODIFIED,
    ID)
 Values
   ('DROP_PENALTY', 'Drop penalty', 'Drop penalty fee', 1, '201602141037',
    '24');
Insert into DB_IUMS.FEE_CATEGORY
   (FEE_ID, NAME, DESCRIPTION, TYPE, LAST_MODIFIED,
    ID)
 Values
   ('SMOKING', 'Smoking', 'Smoking in the campus', 4, '201602141037',
    '25');
Insert into DB_IUMS.FEE_CATEGORY
   (FEE_ID, NAME, DESCRIPTION, TYPE, LAST_MODIFIED,
    ID)
 Values
   ('PLAYING_CARD', 'Playing card', 'Playing card in campus', 4, '201602141037',
    '26');
Insert into DB_IUMS.FEE_CATEGORY
   (FEE_ID, NAME, DESCRIPTION, TYPE, LAST_MODIFIED,
    ID)
 Values
   ('PLAYING_GAME', 'Playing game', 'Playing game in campus', 4, '201602141037',
    '27');


SET DEFINE OFF;
Insert into DB_IUMS.FEE
   (ID, FEE_CATEGORY_ID, SEMESTER_ID, FACULTY_ID, AMOUNT,
    LAST_MODIFIED)
 Values
   (1, '1', 11022014, 1, 8900,
    '201602141037');
Insert into DB_IUMS.FEE
   (ID, FEE_CATEGORY_ID, SEMESTER_ID, FACULTY_ID, AMOUNT,
    LAST_MODIFIED)
 Values
   (2, '1', 11022014, 2, 7000,
    '201602141037');
Insert into DB_IUMS.FEE
   (ID, FEE_CATEGORY_ID, SEMESTER_ID, FACULTY_ID, AMOUNT,
    LAST_MODIFIED)
 Values
   (6, '2', 11022014, 1, 24400,
    '201602141037');
Insert into DB_IUMS.FEE
   (ID, FEE_CATEGORY_ID, SEMESTER_ID, FACULTY_ID, AMOUNT,
    LAST_MODIFIED)
 Values
   (7, '2', 11022014, 2, 15600,
    '201602141037');
Insert into DB_IUMS.FEE
   (ID, FEE_CATEGORY_ID, SEMESTER_ID, FACULTY_ID, AMOUNT,
    LAST_MODIFIED)
 Values
   (11, '3', 11022014, 1, 22750,
    '201602141037');
Insert into DB_IUMS.FEE
   (ID, FEE_CATEGORY_ID, SEMESTER_ID, FACULTY_ID, AMOUNT,
    LAST_MODIFIED)
 Values
   (12, '3', 11022014, 2, 21840,
    '201602141037');
Insert into DB_IUMS.FEE
   (ID, FEE_CATEGORY_ID, SEMESTER_ID, FACULTY_ID, AMOUNT,
    LAST_MODIFIED)
 Values
   (16, '4', 11022014, 1, 35750,
    '201602141037');
Insert into DB_IUMS.FEE
   (ID, FEE_CATEGORY_ID, SEMESTER_ID, FACULTY_ID, AMOUNT,
    LAST_MODIFIED)
 Values
   (17, '4', 11022014, 2, 34320,
    '201602141037');
Insert into DB_IUMS.FEE
   (ID, FEE_CATEGORY_ID, SEMESTER_ID, FACULTY_ID, AMOUNT,
    LAST_MODIFIED)
 Values
   (18, '5', 11022014, 1, 13000,
    '201602141037');
Insert into DB_IUMS.FEE
   (ID, FEE_CATEGORY_ID, SEMESTER_ID, FACULTY_ID, AMOUNT,
    LAST_MODIFIED)
 Values
   (19, '5', 11022014, 2, 1600,
    '201602141037');
Insert into DB_IUMS.FEE
   (ID, FEE_CATEGORY_ID, SEMESTER_ID, FACULTY_ID, AMOUNT,
    LAST_MODIFIED)
 Values
   (20, '6', 11022014, 1, 8000,
    '201602141037');
Insert into DB_IUMS.FEE
   (ID, FEE_CATEGORY_ID, SEMESTER_ID, FACULTY_ID, AMOUNT,
    LAST_MODIFIED)
 Values
   (21, '6', 11022014, 2, 7000,
    '201602141037');
Insert into DB_IUMS.FEE
   (ID, FEE_CATEGORY_ID, SEMESTER_ID, FACULTY_ID, AMOUNT,
    LAST_MODIFIED)
 Values
   (22, '7', 11022014, 1, 13000,
    '201602141037');
Insert into DB_IUMS.FEE
   (ID, FEE_CATEGORY_ID, SEMESTER_ID, FACULTY_ID, AMOUNT,
    LAST_MODIFIED)
 Values
   (23, '7', 11022014, 1, 1300,
    '201602141037');
Insert into DB_IUMS.FEE
   (ID, FEE_CATEGORY_ID, SEMESTER_ID, FACULTY_ID, AMOUNT,
    LAST_MODIFIED)
 Values
   (24, '7', 11022014, 2, 1200,
    '201602141037');
Insert into DB_IUMS.FEE
   (ID, FEE_CATEGORY_ID, SEMESTER_ID, FACULTY_ID, AMOUNT,
    LAST_MODIFIED)
 Values
   (25, '8', 11022014, 1, 1000,
    '201602141037');
Insert into DB_IUMS.FEE
   (ID, FEE_CATEGORY_ID, SEMESTER_ID, FACULTY_ID, AMOUNT,
    LAST_MODIFIED)
 Values
   (26, '8', 11022014, 2, 1500,
    '201602141037');
Insert into DB_IUMS.FEE
   (ID, FEE_CATEGORY_ID, SEMESTER_ID, FACULTY_ID, AMOUNT,
    LAST_MODIFIED)
 Values
   (27, '9', 11022014, 1, 6500,
    '201602141037');
Insert into DB_IUMS.FEE
   (ID, FEE_CATEGORY_ID, SEMESTER_ID, FACULTY_ID, AMOUNT,
    LAST_MODIFIED)
 Values
   (28, '9', 11022014, 2, 6500,
    '201602141037');
Insert into DB_IUMS.FEE
   (ID, FEE_CATEGORY_ID, SEMESTER_ID, FACULTY_ID, AMOUNT,
    LAST_MODIFIED)
 Values
   (29, '10', 11022014, 1, 1000,
    '201602141037');
Insert into DB_IUMS.FEE
   (ID, FEE_CATEGORY_ID, SEMESTER_ID, FACULTY_ID, AMOUNT,
    LAST_MODIFIED)
 Values
   (30, '10', 11022014, 2, 1000,
    '201602141037');
Insert into DB_IUMS.FEE
   (ID, FEE_CATEGORY_ID, SEMESTER_ID, FACULTY_ID, AMOUNT,
    LAST_MODIFIED)
 Values
   (37, '13', 11022014, 1, 4000,
    '201602141037');
Insert into DB_IUMS.FEE
   (ID, FEE_CATEGORY_ID, SEMESTER_ID, AMOUNT, LAST_MODIFIED)
 Values
   (46, '21', 11022014, 3000, '201602141037');
Insert into DB_IUMS.FEE
   (ID, FEE_CATEGORY_ID, SEMESTER_ID, FACULTY_ID, AMOUNT,
    LAST_MODIFIED)
 Values
   (38, '13', 11022014, 2, 4000,
    '201602141037');
Insert into DB_IUMS.FEE
   (ID, FEE_CATEGORY_ID, SEMESTER_ID, AMOUNT, LAST_MODIFIED)
 Values
   (39, '14', 11022014, 100, '201602141037');
Insert into DB_IUMS.FEE
   (ID, FEE_CATEGORY_ID, SEMESTER_ID, AMOUNT, LAST_MODIFIED)
 Values
   (40, '15', 11022014, 450, '201602141037');
Insert into DB_IUMS.FEE
   (ID, FEE_CATEGORY_ID, SEMESTER_ID, AMOUNT, LAST_MODIFIED)
 Values
   (41, '16', 11022014, 500, '201602141037');
Insert into DB_IUMS.FEE
   (ID, FEE_CATEGORY_ID, SEMESTER_ID, AMOUNT, LAST_MODIFIED)
 Values
   (42, '17', 11022014, 1500, '201602141037');
Insert into DB_IUMS.FEE
   (ID, FEE_CATEGORY_ID, SEMESTER_ID, AMOUNT, LAST_MODIFIED)
 Values
   (43, '18', 11022014, 500, '201602141037');
Insert into DB_IUMS.FEE
   (ID, FEE_CATEGORY_ID, SEMESTER_ID, AMOUNT, LAST_MODIFIED)
 Values
   (44, '19', 11022014, 1000, '201602141037');
Insert into DB_IUMS.FEE
   (ID, FEE_CATEGORY_ID, SEMESTER_ID, AMOUNT, LAST_MODIFIED)
 Values
   (45, '20', 11022014, 2500, '201602141037');
Insert into DB_IUMS.FEE
   (ID, FEE_CATEGORY_ID, SEMESTER_ID, AMOUNT, LAST_MODIFIED)
 Values
   (46, '24', 11022014, 15000, '201602141037');


/*Navigation*/
Insert into DB_IUMS.MAIN_NAVIGATION
   (NAVIGATION_ID, MENU_TITLE, PERMISSION, LOCATION, PARENT_MENU,
    ICON_IMG_CLASS, ICON_COLOR_CLASS, VIEW_ORDER, STATUS, LAST_MODIFIED)
 Values
   (3000, 'Fee', 'student:fee', '#', 0,
    'fa fa-money', 'bg-blue', 65, 1, 'xyz');
Insert into DB_IUMS.MAIN_NAVIGATION
   (NAVIGATION_ID, MENU_TITLE, PERMISSION, LOCATION, PARENT_MENU,
    ICON_IMG_CLASS, ICON_COLOR_CLASS, VIEW_ORDER, STATUS, LAST_MODIFIED)
 Values
   (3001, 'Semester fee', 'student:semester:fee', '/semesterFee', 3000,
    'fa-plus-square-o', 'bg-blue', 101, 1, 'xyz');
Insert into DB_IUMS.MAIN_NAVIGATION
   (NAVIGATION_ID, MENU_TITLE, PERMISSION, LOCATION, PARENT_MENU,
    ICON_IMG_CLASS, ICON_COLOR_CLASS, VIEW_ORDER, STATUS, LAST_MODIFIED)
 Values
   (3002, 'Certificate', 'student:certificate:fee', '/certificateFee', 3000,
    'fa-plus-square-o', 'bg-blue', 102, 1, 'xyz');
Insert into DB_IUMS.MAIN_NAVIGATION
   (NAVIGATION_ID, MENU_TITLE, PERMISSION, LOCATION, PARENT_MENU,
    ICON_IMG_CLASS, ICON_COLOR_CLASS, VIEW_ORDER, STATUS, LAST_MODIFIED)
 Values
   (3003, 'Penalty', 'student:dues:penalty', '/studentDues', 3000,
    'fa-plus-square-o', 'bg-blue', 103, 1, 'xyz');
Insert into DB_IUMS.MAIN_NAVIGATION
   (NAVIGATION_ID, MENU_TITLE, PERMISSION, LOCATION, PARENT_MENU,
    ICON_IMG_CLASS, ICON_COLOR_CLASS, VIEW_ORDER, STATUS, LAST_MODIFIED)
 Values
   (3004, 'History', 'student:payment:history', '/paymentHistory', 3000,
    'fa-plus-square-o', 'bg-blue', 104, 1, 'xyz');
Insert into DB_IUMS.MAIN_NAVIGATION
   (NAVIGATION_ID, MENU_TITLE, PERMISSION, LOCATION, PARENT_MENU,
    ICON_IMG_CLASS, ICON_COLOR_CLASS, VIEW_ORDER, STATUS, LAST_MODIFIED)
 Values
   (3020, 'Readmission Application', 'student:readmission:application', '/readmission', 0,
    'fa fa-th', 'bg-blue', 64, 1, 'xyz');
Insert into DB_IUMS.MAIN_NAVIGATION
   (NAVIGATION_ID, MENU_TITLE, PERMISSION, LOCATION, PARENT_MENU,
    ICON_IMG_CLASS, ICON_COLOR_CLASS, VIEW_ORDER, STATUS, LAST_MODIFIED)
 Values
   (4000, 'Receive payment', 'bank:receive:payment', '/receive', 0,
    'fa fa-th', 'bg-blue', 150, 1, 'xyz');
Insert into DB_IUMS.MAIN_NAVIGATION
   (NAVIGATION_ID, MENU_TITLE, PERMISSION, LOCATION, PARENT_MENU,
    ICON_IMG_CLASS, ICON_COLOR_CLASS, VIEW_ORDER, STATUS, LAST_MODIFIED)
 Values
   (4001, 'Payment status', 'bank:payment:status', '/payments', 0,
    'fa fa-th', 'bg-blue', 151, 1, 'xyz');
Insert into DB_IUMS.MAIN_NAVIGATION
   (NAVIGATION_ID, MENU_TITLE, PERMISSION, LOCATION, PARENT_MENU,
    ICON_IMG_CLASS, ICON_COLOR_CLASS, VIEW_ORDER, STATUS, LAST_MODIFIED)
 Values
   (2500, 'Student dues/Penalty', 'dues:penalty', '/listDues', 0,
    'fa fa-th', 'bg-blue', 180, 1, 'xyz');
Insert into DB_IUMS.MAIN_NAVIGATION
   (NAVIGATION_ID, MENU_TITLE, PERMISSION, LOCATION, PARENT_MENU,
    ICON_IMG_CLASS, ICON_COLOR_CLASS, VIEW_ORDER, STATUS, LAST_MODIFIED)
 Values
   (2600, 'Certificate application', 'certificate:application', '/certificateStatus', 0,
    'fa-file-text', 'bg-blue', 171, 1, 'xyz');
Insert into DB_IUMS.MAIN_NAVIGATION
   (NAVIGATION_ID, MENU_TITLE, PERMISSION, LOCATION, PARENT_MENU,
    ICON_IMG_CLASS, ICON_COLOR_CLASS, VIEW_ORDER, STATUS, LAST_MODIFIED)
 Values
   (2700, 'Installment settings', 'installment:settings', '/installmentSetting', 0,
    'fa fa-th', 'fa fa-th', 181, 1, 'xyz');
Insert into DB_IUMS.MAIN_NAVIGATION
   (NAVIGATION_ID, MENU_TITLE, PERMISSION, LOCATION, PARENT_MENU,
    ICON_IMG_CLASS, ICON_COLOR_CLASS, VIEW_ORDER, STATUS, LAST_MODIFIED)
 Values
   (2710, 'Payments', 'payment:status', '/payment-status', 0,
    'fa fa-money', 'bg-blue', 182, 1, 'xyz');

/*Role*/
Insert into DB_IUMS.MST_ROLE
   (ROLE_ID, ROLE_NAME, LAST_MODIFIED)
 Values
   (102, 'bankuser', 'xzy');
Insert into DB_IUMS.MST_ROLE
   (ROLE_ID, ROLE_NAME, LAST_MODIFIED)
 Values
   (45, 'Proctor', 'xyz');

/*Permissions*/
Insert into DB_IUMS.PERMISSIONS
   (ROLE_ID, PERMISSIONS, PERMISSION_ID, LAST_MODIFIED)
 Values
   (102, 'bank:*', 1017, 'xyz');
Insert into DB_IUMS.PERMISSIONS
   (ROLE_ID, PERMISSIONS, PERMISSION_ID, LAST_MODIFIED)
 Values
   (45, 'dues:*', 1018, 'xyz');

DELETE FROM DB_IUMS.PERMISSIONS WHERE ROLE_ID = 11;
DELETE FROM DB_IUMS.PERMISSIONS WHERE ROLE_ID = 81;
DELETE FROM DB_IUMS.PERMISSIONS WHERE ROLE_ID = 72;

Insert into DB_IUMS.PERMISSIONS
   (ROLE_ID, PERMISSIONS, PERMISSION_ID, LAST_MODIFIED)
 Values
   (11, 'user:home,student:profile,student:classRoutine,student:optCourseApplication,student:applications,student:courseMaterial,student:applicationCCI,student:viewSeatPlanning,student:gradesheet,student:fee,student:semester:fee,student:certificate:fee,student:dues:penalty,student:payment:history,student:readmission:application', 1003, 'xyz');
Insert into DB_IUMS.PERMISSIONS
   (ROLE_ID, PERMISSIONS, PERMISSION_ID, LAST_MODIFIED)
 Values
   (81, 'semester:initialization,user:home,new:semester,semester:syllabusmap,new:student,syllabus:bank,syllabus:list,create:syllabus,create:course,user:management,reset:password,deparment:activity,course:teacher,class:routine,student:profile,exam:routine,student:enroll,installment:settings,payment:status', 1001, 'xyz');
Insert into DB_IUMS.PERMISSIONS
   (ROLE_ID, PERMISSIONS, PERMISSION_ID, LAST_MODIFIED)
 Values
   (72, 'coe:*,assign:preparerScrutinizer,class:room,exam:routine,stat:marksSubmissionStat,admission:statistics,certificate:application', 1012, 'xyz');




alter table FEE
add dept_id VARCHAR2(2);



create table certificate_status_log(
  certificate_status_id NUMBER,
  status NUMBER,
  processed_on TIMESTAMP,
  processed_by VARCHAR2(20)
);
