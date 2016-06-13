///<reference path="../../service/HttpClient.ts"/>

module ums {
  export interface IMarksSubmissionScope extends ng.IScope {
    data:any;
    chart1:any;
    amChartOptions:any;
    noneSubmittedGrades: any;
    waitingForScrutinyGrades :any;
    waitingForHeadApprovalGrades :any;
    waitingForCoEApprovalGrades :any;
    submittedGrades: any;
    candidatesGrades: any;
    scrutinizeCandidatesGrades: any;
    approveCandidatesGrades: any;
    scrutinizedGrades: any;
    approvedGrades: any;
    acceptCandidatesGrades: any;
    recheckCandidatesGrades: any;
    acceptedGrades: any;
    recheckAcceptedGrades:any;
    allMarksSubmissionStatus:any;
    toggleColumn:boolean;
    excel_copy_paste_error_div:boolean;
    gradeSubmissionStatus:number;  //Grade Submission Status for the current Course
    onTotalPartChange: Function;
    toggleStatRules:Function;
    fetchGradeSheet:Function;
    pasteExcelData:Function;
    validateExcelSheetHeader:Function;
    setFieldValue:Function;
    checkNumber:Function;
    gradeLetterFromMarks:Function;
    validateGradeSheet:Function;
    saveGradeSheet:Function;
    saveAndSendToScrutinizer:Function;
    fetchGradeSubmissionTable:Function;
    calculateTotalAndGradeLetter:Function;

    recheckAll:Function;
    approveAll:Function;
    onRecheckClick:Function;
    onApproveClick:Function;
    totalRecheck:number;
    totalApprove:number;

    gradeTitle:string;
    currentActor:string;
    approveAction:string;
    recheckButtonLabel:string;
    approveButtonLabel:string;
    approveStatusName:string;
    approveStatusId:number;

    saveRecheckApproveGrades:Function;
    sendRecheckRequestToVC:Function;
    closePopupModal:Function;

    userRole:string;
    downloadPdf:Function;
    copyGradeRow:Function;
  }
  interface IStudentMarks {
    studentId:string;
    studentName:string;
    quiz:number;
    classPerformance:number;
    partA:number;
    partB:number;
    partTotal:number;
    total:number;
    gradeLetter:string;
    gradePoint:number;
    statusId:number;
    statusName:string;
  }
  interface IStudent{
    studentId:string;
  }
  interface IMarksSubmissionStatus {
    courseId:string;
    courseNo:string;
    courseTitle:string;
    semesterId:number;
    semesterName:string;
    examType:number;
    examTypeName:string;
    statusId:number;
    statusName:string;
    preparerName:string;
    scrutinizerName:string;
    year:string;
    semester:string;
    section:string;
    offeredTo:string;
  }
  interface ICourseInfo{
    course_id:string;
    semester_id:number;
    exam_type:number;
    total_part:number;
    part_a_total:number;
    part_b_total:number;
  }

  export class MarksSubmission {
    public static $inject = ['$scope', 'appConstants', 'HttpClient','$stateParams', '$window', '$sce'];

    constructor(private $scope:IMarksSubmissionScope,
                private appConstants:any,
                private httpClient:HttpClient, private $stateParams:any,private $window: ng.IWindowService, private $sce: ng.ISCEService) {

                //console.clear();
      console.log($stateParams["1"]);
                //his.$scope.userRole=$params.role;


        this.$scope.userRole = $stateParams["1"];
                $scope.data = {
                  gradeLetterOptions: appConstants.gradeLetters,
                  total_part:Number,
                  part_a_total:Number,
                  part_b_total:Number,
                  recheck_accepted_studentId:String
                };

      $scope.onTotalPartChange = this.onTotalPartChange.bind(this);
      $scope.toggleStatRules = this.toggleStatRules.bind(this);
      $scope.fetchGradeSheet = this.fetchGradeSheet.bind(this);
      $scope.pasteExcelData = this.pasteExcelData.bind(this);
      $scope.validateExcelSheetHeader = this.validateExcelSheetHeader.bind(this);
      $scope.setFieldValue = this.setFieldValue.bind(this);
      $scope.checkNumber = this.checkNumber.bind(this);
      $scope.gradeLetterFromMarks = this.gradeLetterFromMarks.bind(this);
      $scope.validateGradeSheet = this.validateGradeSheet.bind(this);
      $scope.saveGradeSheet = this.saveGradeSheet.bind(this);
      $scope.saveAndSendToScrutinizer = this.saveAndSendToScrutinizer.bind(this);
      $scope.fetchGradeSubmissionTable = this.fetchGradeSubmissionTable.bind(this);
      $scope.calculateTotalAndGradeLetter = this.calculateTotalAndGradeLetter.bind(this);
      $scope.recheckAll=this.recheckAll.bind(this);
      $scope.approveAll=this.approveAll.bind(this);

      $scope.downloadPdf=this.downloadPdf.bind(this);

      $scope.onRecheckClick=this.onRecheckClick.bind(this);
      $scope.onApproveClick=this.onApproveClick.bind(this);

      $scope.saveRecheckApproveGrades=this.saveRecheckApproveGrades.bind(this);
      $scope.closePopupModal=this.closePopupModal.bind(this);

      $scope.copyGradeRow=this.copyGradeRow.bind(this);
      $scope.sendRecheckRequestToVC=this.sendRecheckRequestToVC.bind(this);

      $scope.data.recheck_accepted_studentId="";
      $scope.chart1 =[{
        "country": "A+",
        "visits": 2000,
        "color": "#FF0F00"
      },
        {
          "country": "A",
          "visits": 1882,
          "color": "#FF6600"
        },
        {
          "country": "A-",
          "visits": 1809,
          "color": "#FF9E01"
        },
        {
          "country": "B+",
          "visits": 1322,
          "color": "#FCD202"
        },
        {
          "country": "B",
          "visits": 1122,
          "color": "#F8FF01"
        },
        {
          "country": "B-",
          "visits": 1114,
          "color": "#B0DE09"
        },
        {
          "country": "C+",
          "visits": 984,
          "color": "#04D215"
        },
        {
          "country": "C",
          "visits": 711,
          "color": "#0D8ECF"
        },
        {
          "country": "D",
          "visits": 665,
          "color": "#0D52D1"
        },
        {
          "country": "F",
          "visits": 580,
          "color": "#2A0CD0"
        }];

      $scope.amChartOptions = {
        data:  $scope.chart1,
        type: "serial",

        categoryField: "country",
        depth3D : 20,
         angle : 30,
        pathToImages: 'http://www.amcharts.com/lib/3/images/',
        categoryAxis: {
          gridPosition: "start",
          parseDates: false,
            dashLength : 5
        },
        valueAxes: [{
          dashLength : 5,
          title: "Visitors"
        }],
        graphs: [{
          type: "column",
          valueField: "visits",
          balloonText :"<span style='font-size:14px'>[[category]]: <b>[[value]]</b></span>",
          fillAlphas: 1,
            colorField : "color",
          lineAlpha : 0,
      labelText :  '[[value]]'
        }],
        chartCursor:[{
          cursorAlpha:0,
          zoomable:false,
          categoryBalloonEnabled:false
        }]
      };
    }

    private copyGradeRow():void{

      var studentId:any=this.$scope.data.recheck_accepted_studentId;
      var newRowId:any="recheck_accepted_"+studentId;
      if ($("#"+newRowId).length) return;
      var $clone = $("#"+studentId).clone();
      $clone.attr("id", newRowId);
      $clone.append('<td style="text-align: center;cursor:pointer;" onclick="removeTableRow(\''+newRowId+'\')"><img src="https://cdn4.iconfinder.com/data/icons/6x16-free-application-icons/16/Delete.png" /></td>');
      $clone.appendTo($('#tbl_recheck_accepted > tbody'));
      this.$scope.data.recheck_accepted_studentId="";

    }

    private sendRecheckRequestToVC():void{

      console.log("aaaa  ");
      $("#tbl_recheck_accepted  tbody tr[id^='recheck_accepted_']").each(function (i, el) {
          console.log(el.id);
      });
      console.log("bbbb");
      var newDAtaSet =[{
        "country": "A+",
        "visits": 4025,
        "color": "#FF0F00"
      },
        {
          "country": "A",
          "visits": 1882,
          "color": "#FF6600"
        },
        {
          "country": "A-",
          "visits": 1809,
          "color": "#FF9E01"
        },
        {
          "country": "B+",
          "visits": 1322,
          "color": "#FCD202"
        },
        {
          "country": "B",
          "visits": 1122,
          "color": "#F8FF01"
        },
        {
          "country": "B-",
          "visits": 1114,
          "color": "#B0DE09"
        },
        {
          "country": "C+",
          "visits": 984,
          "color": "#04D215"
        },
        {
          "country": "C",
          "visits": 711,
          "color": "#0D8ECF"
        },
        {
          "country": "D",
          "visits": 665,
          "color": "#0D52D1"
        },
        {
          "country": "F",
          "visits": 665,
          "color": "#2A0CD0"
        }];
      this.$scope.$broadcast("amCharts.updateData", newDAtaSet);
    }


    private downloadPdf():void {
      this.httpClient.get("https://localhost/ums-webservice-common/gradeReport", 'application/pdf',
          (data:any, etag:string) => {
            var file = new Blob([data], {type: 'application/pdf'});
            var fileURL = this.$sce.trustAsResourceUrl(URL.createObjectURL(file));
            this.$window.open(fileURL);
          },
          (response:ng.IHttpPromiseCallbackArg<any>) => {
            console.error(response);
          }, 'arraybuffer');
    }

    private fetchGradeSubmissionTable():void {
      this.httpClient.get("academic/gradeSubmission/semester/11012016/examtype/1/dept/05/role/"+this.$scope.userRole,
          this.appConstants.mimeTypeJson,
          (data:any, etag:string)=> {
            this.$scope.allMarksSubmissionStatus = data.entries;
          });

      $("#leftDiv").hide();
      $("#arrowDiv").show();

      $("#rightDiv").removeClass("orgRightClass");
      $("#rightDiv").addClass("newRightClass");
    }


    private fetchGradeSheet():void {
      this.$scope.toggleColumn = true;
      //https://localhost/ums-webservice-common/sadf
      this.httpClient.get("academic/gradeSubmission/semester/11012016/courseid/EEE1101_S2014_110500/examtype/1/role/"+this.$scope.userRole,
          this.appConstants.mimeTypeJson,
          (data:any, etag:string)=> {
            this.$scope.noneSubmittedGrades = data.none_and_submit_grades;

            this.$scope.recheckCandidatesGrades = data.recheck_candidates_grades;

            this.$scope.waitingForScrutinyGrades = data.waiting_for_scrutiny_grades;
            this.$scope.waitingForHeadApprovalGrades = data.waiting_for_head_approval_grades;
            this.$scope.waitingForCoEApprovalGrades = data.waiting_for_CoE_approval_grades;


            this.$scope.submittedGrades = data.submitted_grades;

            this.$scope.scrutinizeCandidatesGrades = data.scrutinize_candidates_grades;
            this.$scope.approveCandidatesGrades = data.approve_candidates_grades;
            this.$scope.acceptCandidatesGrades = data.accept_candidates_grades;


            this.$scope.scrutinizedGrades = data.scrutinized_grades;
            this.$scope.approvedGrades = data.approved_grades;
            this.$scope.acceptedGrades = data.accepted_grades;
            this.$scope.recheckAcceptedGrades = data.recheck_accepted_grades;


            var part_info = data.part_info;
            console.log(part_info.total_part);
            this.onTotalPartChange();

            //$("#total_part").val(part_info.total_part);
            this.$scope.data.part_a_total = part_info.part_a_total == 0 ? null : part_info.part_a_total;
            this.$scope.data.part_b_total = part_info.part_b_total == 0 ? null : part_info.part_b_total;
            this.$scope.data.total_part = part_info.total_part;
            this.$scope.gradeSubmissionStatus=part_info.statusId;
            this.$scope.currentActor = data.current_actor;

            if( this.$scope.currentActor=="preparer" &&  this.$scope.gradeSubmissionStatus==0){
              this.$scope.gradeTitle="Non-Submitted Grades";
            }
            if( this.$scope.currentActor=="scrutinizer" &&  this.$scope.gradeSubmissionStatus==1){
              this.$scope.gradeTitle="Waiting for Scrutinizer's Approval";
              this.$scope.approveAction="Scrutiny";
              //this.$scope.approveStatusName="scrutinized"; //may be we can remove this
              //this.$scope.approveStatusId=4; // May be we can remove this....
              this.$scope.recheckButtonLabel="Save & Send back to Preparer";
              this.$scope.approveButtonLabel="Scrutiny & Send to Head";
              this.$scope.candidatesGrades=this.$scope.scrutinizeCandidatesGrades;
            }
            if( this.$scope.currentActor=="head" &&  this.$scope.gradeSubmissionStatus==3){
              this.$scope.gradeTitle="Waiting for Head's Approval";
              this.$scope.approveAction="Approve";
              this.$scope.recheckButtonLabel="Save & Send back to Preparer";
              this.$scope.approveButtonLabel="Approve & Send to CoE";
              this.$scope.candidatesGrades=this.$scope.approveCandidatesGrades;
            }
            if( this.$scope.currentActor=="coe" && this.$scope.gradeSubmissionStatus==5){
              this.$scope.gradeTitle="Waiting for CoE's Approval";
              this.$scope.approveAction="Accept";
              this.$scope.recheckButtonLabel="Save & Send back to Preparer";
              this.$scope.approveButtonLabel="Save and Accept";
              this.$scope.candidatesGrades=this.$scope.acceptCandidatesGrades;
            }

          });

      $("#selection1").hide();
      $("#selection2").show();
      //$("#btn_stat").focus();
      $(window).scrollTop($('#panel_top').offset().top - 56);
    }

    public recalculateTotalAndGradeLetter():void {

    }

    private calculateTotalAndGradeLetter(student_id:string):void {

      var quiz:number = Number($("#quiz_" + student_id).val()) || 0;
      var class_perf:number = Number($("#class_perf_" + student_id).val()) || 0;
      var part_a:number = Number($("#part_a_" + student_id).val()) || 0;
      var part_b:number = 0;

      if ($("#total_part") && $("#total_part").val() == 2)
        part_b = Number($("#part_b_" + student_id).val()) || 0;
      if (this.$scope.data.total_part == 2)
        part_b = Number($("#part_b_" + student_id).val()) || 0;

      var total = quiz + class_perf + part_a + part_b;

      $("#total_" + student_id).val(String(total));
      var grade_letter:string = this.gradeLetterFromMarks(total);
      $("#grade_letter_" + student_id).val(grade_letter);

      this.validateGrade(false, student_id, String(quiz), String(class_perf), String(part_a), String(part_b), String(total), grade_letter);
    }

    public onTotalPartChange():void {

      if (this.$scope.data.total_part == 1) {
        this.$scope.toggleColumn = false;
        this.$scope.data.part_a_total = 70;
        this.$scope.data.part_b_total = 0;
        $("#partDiv").hide();
      }
      else {
        this.$scope.data.part_a_total = 0;
        this.$scope.data.part_b_total = 0;
        this.$scope.toggleColumn = true;
        $("#partDiv").show();
      }

      this.$scope.$broadcast("renderScrollableTable");

      //this.$scope.$broadcast("renderScrollableTable");
    }

    private checkNumber(sNum):boolean {
      var pattern = /^\d+(.\d{1,2})?$/;
      return pattern.test(sNum);
    }

    private toggleStatRules(table_id:string) {
      $("#tbl_stat").hide();
      $("#tbl_rules").hide();
      $("#" + table_id).show();
    }

    private pasteExcelData():void {
      var studentId = "";
      var data = $('textarea[name=excel_data]').val();
      //console.log(data);
      var rows = data.split("\n");

      // var table = $('<table />');asdfasfasddfj========asdfasdfasdfasdf==============f -------------asdfasdfasdkflask
      var rowError = false;
      for (var y = 0; y < rows.length; y++) {
        if (rows[y] == "") continue;
        // console.log(y); asfasdasdfasdfs;
        var row = rows[y].split("\t");

        if (y == 0) {
          if (this.validateExcelSheetHeader(row) == false) {
            return;
          }
          continue;
        }

        //for(var x in cells) {
        // row.append('<td>'+cells[x]+'</td>');
        studentId = row[0];
        this.setFieldValue("quiz_" + studentId, row[2]);
        this.setFieldValue("class_perf_" + studentId, row[3]);
        this.setFieldValue("part_a_" + studentId, row[4]);
        this.setFieldValue("part_b_" + studentId, row[5]);
        this.setFieldValue("total_" + studentId, row[6]);
        if (row[7] != "")
          this.setFieldValue("grade_letter_" + studentId, row[7]);

        rowError = false;

        console.log(studentId); //

        this.validateGrade(false, studentId, row[2], row[3], row[4], row[5], row[6], row[7]);

        //}-------==========
        // table.append(row);
      }

// Insert into DOM
      //$('#excel_table').html(table); asdfasdfasdfas
      $('#modal-prompt').modal('hide');


    }

    private validateGrade(force_validate:boolean, student_id:string, quiz:string, class_performance:string, part_a:string, part_b:string, total:string, grade_letter:string):boolean {
      var row_error:boolean = false;
      var border_error:any = {"border": "2px solid red"};
      var border_ok:any = {"border": "1px solid grey"};

      //Quiz
      if (quiz != "" || force_validate) {
        if ((this.checkNumber(quiz) == false || Number(quiz) > 20)) {
          $("#quiz_" + student_id).css(border_error);
          row_error = true;
        }
        else {
          $("#quiz_" + student_id).css(border_ok);
        }
      }

      //Class Performance
      if (class_performance != "" || force_validate) {
        if (this.checkNumber(class_performance) == false || Number(class_performance) > 10) {
          $("#class_perf_" + student_id).css(border_error);
          row_error = true;
        }
        else {
          $("#class_perf_" + student_id).css(border_ok);
        }
      }

      //Part A
      if (part_a != "" || force_validate) {
        if (this.$scope.data.part_a_total != null && (this.checkNumber(part_a) == false || Number(part_a) > this.$scope.data.part_a_total)) {
          $("#part_a_" + student_id).css(border_error);
          row_error = true;
        }
        else {
          $("#part_a_" + student_id).css(border_ok);
        }
      }

      //Part B
      if (part_b != "" || force_validate) {
        if (this.$scope.data.total_part == 2 && this.$scope.data.part_b_total != null && (this.checkNumber(part_b) == false || Number(part_b) > this.$scope.data.part_b_total)) {
          $("#part_b_" + student_id).css(border_error);
          row_error = true;
        }
        else {
          $("#part_b_" + student_id).css(border_ok);
        }
      }

      //Total
      if (total != "" || force_validate) {
        if (this.checkNumber(total) == false || Number(total) != Number(quiz) + Number(class_performance) + Number(part_a) + Number(part_b)) {
          $("#total_" + student_id).css(border_error);
          row_error = true;
        }
        else {
          $("#total_" + student_id).css(border_ok);
        }
      }

      //Grade Letter
      if (grade_letter != "" || force_validate) {
        if (grade_letter != "" && this.gradeLetterFromMarks(Number(total)) != grade_letter) {
          $("#grade_letter_" + student_id).css(border_error);
          row_error = true;
        }
        else {
          $("#grade_letter_" + student_id).css(border_ok);
        }
      }

      var parentRow = document.getElementById("row_" + student_id);
      var tdArray = parentRow.getElementsByTagName('td');

      if (row_error == true) {
        for (var i = 0; i < tdArray.length; i++) {
          tdArray[i].style.backgroundColor = "#FCDC3B";//"#FFFF7E";
        }
      }
      else {
        for (var i = 0; i < tdArray.length; i++) {
          //tdArray[i].style.backgroundColor = "none";
          //  tdArray[i].style.backgroundColor = "transparent";//"#FFFF7E";
          if($('#'+tdArray[i].id).is("[style]")) {
            $('#' + tdArray[i].id).attr('style', function (i, style) {
              return style.replace(/background-color[^;]+;?/g, '');
            });
          }
          //('#foo [style]').removeAttr('style');
        }//asdfasdfasdfasfasdfsadfasdfsssss

      }
      return row_error;

    }

    private setFieldValue(field_id:string, field_value:any) {
      $("#" + field_id).val(field_value);
    }

    private validateExcelSheetHeader(cells:any):boolean {
      console.log(cells);
      if (cells[0] != "Student Id" || cells[1] != "Student Name" || cells[2] != "Quiz" || cells[3] != "Class Perf." || cells[4] != "Part-A" || cells[5] != "Part-B" || cells[6] != "Total" || cells[7] != "Grade Letter") {
        this.$scope.excel_copy_paste_error_div = true;
        return false;

      }
      else
        this.$scope.excel_copy_paste_error_div = false;
      return true;


    }

    private validateGradeSheet():boolean {
      return false;
    }

    private gradeLetterFromMarks(total_marks:number):string {

      if (total_marks >= 80) return "A+";
      else if (total_marks >= 75 && total_marks < 80) return "A";
      else if (total_marks >= 70 && total_marks < 75) return "A-";
      else if (total_marks >= 65 && total_marks < 70) return "B+";
      else if (total_marks >= 60 && total_marks < 65) return "B";
      else if (total_marks >= 55 && total_marks < 60) return "B-";
      else if (total_marks >= 50 && total_marks < 55) return "C+";
      else if (total_marks >= 45 && total_marks < 50) return "C";
      else if (total_marks >= 40 && total_marks < 45) return "D";
      else if (total_marks < 40) return "F";

    }

    private saveGradeSheet():boolean {
      var gradeList:Array<IStudentMarks> = this.getTargetGradeList(1);
      this.postGradeSheet(gradeList,'save');


      return false;
    }

    private getTargetGradeList(status:number):Array<IStudentMarks> {
      var gradeList:Array<IStudentMarks> = new Array<IStudentMarks>();
      var allStudents:Array<IStudentMarks> = new Array<IStudentMarks>();
      var studentId = "";
      var studentMark:IStudentMarks;
      if(this.$scope.gradeSubmissionStatus == 0)
        allStudents = this.$scope.noneSubmittedGrades;
      else if(this.$scope.gradeSubmissionStatus == 2 || this.$scope.gradeSubmissionStatus == 4 || this.$scope.gradeSubmissionStatus == 6)
        allStudents = this.$scope.recheckCandidatesGrades;

      for (var ind in allStudents) {
        var currentStudent:IStudentMarks = allStudents[ind];
        var studentMark:IStudentMarks;
        if (currentStudent.statusId == 0 || currentStudent.statusId == 1) {
          //0= None, 1=Submit
          studentMark = <IStudentMarks>{};
          studentId = currentStudent.studentId;
          studentMark.studentId = studentId;
          studentMark.quiz = $("#quiz_" + studentId).val();
          studentMark.classPerformance = $("#class_perf_" + studentId).val();

          studentMark.partA = $("#part_a_" + studentId).val();
          studentMark.partB = $("#part_b_" + studentId).val();
          studentMark.total = $("#total_" + studentId).val();
          studentMark.gradeLetter = $("#grade_letter_" + studentId).val();
          studentMark.total = $("#total_" + studentId).val();
          studentMark.statusId = status;

          if ((this.$scope.gradeSubmissionStatus == 2 || this.$scope.gradeSubmissionStatus == 4 || this.$scope.gradeSubmissionStatus == 6)) {
            if ($('#recheck_' + studentId) && !$('#recheck_' + studentId).prop('checked'))
                studentMark.statusId = 0;
          }
        }

          gradeList.push(studentMark);
      }
      return gradeList;
    }

    private saveAndSendToScrutinizer():void {
      var gradeList:Array<IStudentMarks> = this.getTargetGradeList(2);
      var validate:boolean = true;
      for (var ind in gradeList) {
        var studentMark:IStudentMarks = gradeList[ind];
        if (this.validateGrade(true, studentMark.studentId, studentMark.quiz.toString(), studentMark.classPerformance.toString(), studentMark.partA.toString(), studentMark.partB.toString(), studentMark.total.toString(), studentMark.gradeLetter) == false)
          validate = true;
      }
      if (validate == false) {
        alert("Correct your data ...");
        return;
      }
      this.postGradeSheet(gradeList,'submit');

    }

    private postGradeSheet(gradeList:Array<IStudentMarks>,action:string):void{
      var url = "academic/gradeSubmission";
      var complete_json =this.createCompleteJson(action,gradeList,null,null);
      //console.clear();
      console.log(complete_json);
      //studentMark is undefined asdfasdfasdf asd f asdfasdfasdf asfasdfasfdsadfasfafasfdsaf
      this.httpClient.put(url, complete_json, 'application/json')
          .success(() => {
            $.notific8("Successfully Saved");
            this.fetchGradeSheet();
            /* if(statusId==1)
             this.$scope.optional.applicationStatus="Submitted";
             else
             this.$scope.optional.applicationStatus="Saved";
             */

          }).error((data) => {
          });
    }

    private createCompleteJson(action:string,gradeList:Array<IStudentMarks>,recheckList:Array<IStudent>,approveList:Array<IStudent>):any{
      var complete_json = {};
      complete_json["gradeList"] = gradeList;
      complete_json["recheckList"] = recheckList;
      complete_json["approveList"] = approveList;

      var courseInfo:ICourseInfo = {
        course_id: '',
        semester_id: 0,
        exam_type: 0,
        total_part: 0,
        part_a_total: 0,
        part_b_total: 0
      };
      //alert(this.$scope.data.part_a_total);
      courseInfo.course_id = "EEE1101_S2014_110500";
      courseInfo.semester_id = 11012016;
      courseInfo.exam_type = 1;
      courseInfo.total_part = Number(this.$scope.data.total_part);
      courseInfo.part_a_total = Number(this.$scope.data.part_a_total);
      courseInfo.part_b_total = Number(this.$scope.data.part_b_total);

      complete_json["courseInfo"] = courseInfo;
      complete_json["action"] = action;
      complete_json["actor"] = this.$scope.currentActor;
      complete_json["course_current_status"] = this.$scope.gradeSubmissionStatus;
      return complete_json;
    }

    private recheckAll(actor:string):void {
      var studentMark:IStudentMarks ;
      var gradeList:Array<IStudentMarks>=this.getGradeList(actor);
        for (var ind in gradeList) {
          studentMark = gradeList[ind];
          if($('#recheckAllCheckBox').prop('checked'))
            $("#recheck_" + studentMark.studentId).prop('checked', true);
          else
            $("#recheck_" + studentMark.studentId).prop('checked', false);
      }
      this.enableDisableRecheckApproveButton(actor);
    }

    private approveAll(actor:string):void {
      var studentMark:IStudentMarks ;
      var gradeList:Array<IStudentMarks>=this.getGradeList(actor);

        for (var ind in gradeList) {
          studentMark = gradeList[ind];
          if($('#approveAllCheckBox').prop('checked'))
            $("#approve_" + studentMark.studentId).prop('checked', true);
          else
            $("#approve_" + studentMark.studentId).prop('checked', false);
      }
      this.enableDisableRecheckApproveButton(actor);
    }

    private closePopupModal():void{
      $("#msg_div24").css({
        display:"none"
      });
      $(".table_overlay").fadeOut();
    }
    private showPopupModal():void{
      var topDiv=$("#top_div");
      $(".table_overlay").css({
        background:'url("https://localhost/ums-web/iums/images/overlay1.png")',
        opacity : 0.5,
        top     : topDiv.position().top-150,
        width   : topDiv.outerWidth()+20,
        height  : 450,
        zIndex:100
      });
      $(".table_overlay").fadeIn();

      $("#msg_div24").css({
        display:"block",
        top     : $(".table_overlay").position().top,
        left: $(".table_overlay").position().left+(topDiv.outerWidth()+20)/2-$("#msg_div24").width()/2,
        zIndex:105
      });
    }
    private saveRecheckApproveGrades(actor:string,action:string):void{
      var validate=this.validateGradesForSaveRecheckApprove(actor,action);

      if(!validate) {
        this.showPopupModal();
        return;
      }

      var gradeList:Array<IStudentMarks>=this.getGradeList(actor);
      var recheckStudentList:Array<IStudent> = new Array<IStudent>();
      var approveStudentList:Array<IStudent> = new Array<IStudent>();
      var student:IStudent;
      var candidateGrade:IStudentMarks;
      for (var ind in gradeList) {
        student={
          studentId:""
        }
        candidateGrade = gradeList[ind];
        student.studentId=candidateGrade.studentId;
        if($('#recheck_'+ student.studentId).prop('checked')){
          recheckStudentList.push(student);
        }
        if($('#approve_'+ student.studentId).prop('checked')){
          approveStudentList.push(student);
        }
      }

      var url = "academic/gradeSubmission/recheckApprove";

      var complete_json =this.createCompleteJson(action,null,recheckStudentList,approveStudentList);
      //console.clear();
      console.log(complete_json);

      this.httpClient.put(url, complete_json, 'application/json')
          .success(() => {
            $.notific8("Successfully Saved");
            this.fetchGradeSheet();
            /* if(statusId==1)
             this.$scope.optional.applicationStatus="Submitted";
             else
             this.$scope.optional.applicationStatus="Saved";
             */

          }).error((data) => {
          });
    }

    private validateGradesForSaveRecheckApprove(actor:String,action:String):boolean{
      var validate:boolean=true;
      var totalGrade=0;
      var gradeList:Array<IStudentMarks>=this.getGradeList(actor);
      totalGrade = gradeList.length;
      this.getTotalRecheckApproveGrade(actor);
      var msg:string="";

      if(action=="recheck" && this.$scope.totalRecheck==0 ){
        msg="You should recheck at least one student grade for Recheck Request.";
      }
      else if(action=="recheck" && this.$scope.totalRecheck+this.$scope.totalApprove !=totalGrade){
        msg="Total Number of Recheck and Scrutiny Grade should be equal to the Total Number of Students.";
      }
      else if(action=="approve" && this.$scope.totalApprove !=totalGrade ){
        if(actor=="scrutinizer")
          msg="You must scrutiny all grades for sending it to Head.";
        else if(actor=="head")
          msg="You must approve all grades for sending it to CoE.";
        else if(actor=="coe")
          msg="You must accept all grades for acceptance.";
      }
      else if(action=="approve" && this.$scope.totalRecheck>0){
           if(actor=="scrutinizer")
             msg="There should not be any recheck grade while you send grades to Head.";
          if(actor=="head")
            msg="There should not be any recheck grade while you send grades to CoE.";
          if(actor=="coe")
            msg="There should not be any recheck grade while you accept grades.";
      }
      if(msg!="") {
        $("#msg_content").html(msg);
        validate = false;
      }
      return validate;
    }

    private getGradeList(actor:String):any{
      var gradeList:any;
      if(actor=="scrutinizer") {
          gradeList = this.$scope.scrutinizeCandidatesGrades;
        }
      else if(actor=="head") {
        gradeList = this.$scope.approveCandidatesGrades;
      }
      else if(actor=="coe") {
        gradeList = this.$scope.acceptCandidatesGrades;
      }
      return gradeList;
  }

    private onRecheckClick(actor:String,stdMarkObj:IStudentMarks):void{
    if($("#recheck_"+stdMarkObj.studentId).prop('checked')==true && $('#approve_'+ stdMarkObj.studentId).prop('checked')==true)
      $("#approve_" +stdMarkObj.studentId).prop('checked', false);
      this.enableDisableRecheckApproveButton(actor);
  }
    private onApproveClick(actor:String, stdMarkObj:IStudentMarks):void{
    if($("#approve_"+stdMarkObj.studentId).prop('checked')==true  && $('#recheck_'+stdMarkObj.studentId).prop('checked')==true)
      $("#recheck_" +stdMarkObj.studentId).prop('checked', false);
     this.enableDisableRecheckApproveButton(actor);
  }

    private getTotalRecheckApproveGrade(actor:String):void{
      var gradeList:Array<IStudentMarks>=this.getGradeList(actor);
      var totalRecheckGrade:number=0;
      var totalApproveGrade:number=0;
      var studentMark:IStudentMarks;
      for (var ind in gradeList) {
        studentMark =gradeList[ind];
        if ($('#recheck_' + studentMark.studentId).prop('checked')) {
          totalRecheckGrade++;
        }
        if ($('#approve_' + studentMark.studentId).prop('checked')) {
          totalApproveGrade++;
        }
      }
      this.$scope.totalRecheck=totalRecheckGrade;
      this.$scope.totalApprove=totalApproveGrade;

    }
    private enableDisableRecheckApproveButton(actor:String){
      this.getTotalRecheckApproveGrade(actor);
      //console.clear();
      console.log("Recheck :"+this.$scope.totalRecheck+"=="+"Approve :"+this.$scope.totalApprove);
      $("#recheckBtn").removeClass("disabled");
      $("#approveBtn").removeClass("disabled");

      if(this.$scope.totalRecheck==0 && this.$scope.totalApprove==0) {
        $("#recheckBtn").addClass("disabled");
        $("#approveBtn").addClass("disabled");
      }
      else if(this.$scope.totalRecheck==0 && this.$scope.totalApprove>0) {
        $("#recheckBtn").addClass("disabled");
        $("#approveBtn").removeClass("disabled");
      }
      else if(this.$scope.totalRecheck>0) {
        $("#approveBtn").addClass("disabled");
        $("#recheckBtn").removeClass("disabled");
      }

    }

  }
  UMS.controller('MarksSubmission', MarksSubmission);
}
