///<reference path="../../service/HttpClient.ts"/>

module ums {
  export interface IMarksSubmissionScope extends ng.IScope {
      data:any;
    allStudents: any;
      allMarksSubmissionStatus:any;
    toggleColumn:boolean;
    excel_copy_paste_error_div:boolean;
    total_part:number;
    part_a_total:number;
    part_b_total:number;
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
        gradeLetter:number;
        gradePoint:number;
        status:number;
        statusName:string;
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
    public static $inject = ['$scope', 'appConstants', 'HttpClient'];

    constructor(private $scope:IMarksSubmissionScope,
                private appConstants:any,
                private httpClient:HttpClient) {


        $scope.data = {
            gradeLetterOptions: appConstants.gradeLetters
        };

      $scope.onTotalPartChange = this.onTotalPartChange.bind(this);
      $scope.toggleStatRules = this.toggleStatRules.bind(this);
      $scope.fetchGradeSheet = this.fetchGradeSheet.bind(this);
      $scope.pasteExcelData = this.pasteExcelData.bind(this);
      $scope.validateExcelSheetHeader = this.validateExcelSheetHeader.bind(this);
      $scope.setFieldValue = this.setFieldValue.bind(this);
      $scope.checkNumber = this.checkNumber.bind(this);
      $scope.gradeLetterFromMarks=this.gradeLetterFromMarks.bind(this);
      $scope.validateGradeSheet=this.validateGradeSheet.bind(this);
      $scope.saveGradeSheet=this.saveGradeSheet.bind(this);
      $scope.saveAndSendToScrutinizer=this.saveAndSendToScrutinizer.bind(this);
      $scope.fetchGradeSubmissionTable=this.fetchGradeSubmissionTable.bind(this);
      $scope.calculateTotalAndGradeLetter=this.calculateTotalAndGradeLetter.bind(this);



    }


    private fetchGradeSubmissionTable():void{
        this.httpClient.get("https://localhost/ums-webservice-common/academic/gradeSubmission/semester/1/examtype/1",
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
      this.$scope.toggleColumn=true;

              this.httpClient.get("https://localhost/ums-webservice-common/academic/gradeSubmission/semester/1/courseid/1/examtype/1",
                  this.appConstants.mimeTypeJson,
                  (data:any, etag:string)=> {
                      this.$scope.allStudents = data.entries;
                      var part_info=data.part_info;
                      console.log(part_info.total_part);
                      this.onTotalPartChange();


                      // this.$scope.total_part=part_info.total_part;
                      $("#total_part").val(part_info.total_part);
                      this.$scope.part_a_total=part_info.part_a_total==0?null:part_info.part_a_total;
            this.$scope.part_b_total=part_info.part_b_total==0?null:part_info.part_b_total;
                      this.$scope.total_part=part_info.total_part;

          });

        $("#selection1").hide();
        $("#selection2").show();
        //$("#btn_stat").focus();

      $(window).scrollTop($('#panel_top').offset().top-56);


    }

    public recalculateTotalAndGradeLetter():void{

    }

    private calculateTotalAndGradeLetter(student_id:string):void{

        var quiz:number=Number($("#quiz_"+student_id).val()) || 0;
        var class_perf:number=Number($("#class_perf_"+student_id).val()) || 0;
        var part_a:number=Number($("#part_a_"+student_id).val()) || 0;
        var part_b:number=0;

        if($("#total_part").val()==2)
            part_b=Number($("#part_b_"+student_id).val()) || 0;


        var total=quiz+class_perf+part_a+part_b;


        $("#total_"+student_id).val(String(total));
        var grade_letter:string=this.gradeLetterFromMarks(total);
        $("#grade_letter_"+student_id).val(grade_letter);

        this.validateGrade(false,student_id,String(quiz),String(class_perf),String(part_a),String(part_b),String(total),grade_letter);
    }

    public onTotalPartChange(): void {

      if(this.$scope.total_part==1) {
        this.$scope.toggleColumn = false;
          this.$scope.part_a_total=70;
          this.$scope.part_b_total=0;
        $("#partDiv").hide();
      }
      else {
          this.$scope.part_a_total=0;
          this.$scope.part_b_total=0;
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

    private toggleStatRules(table_id:string){
      $("#tbl_stat").hide();
      $("#tbl_rules").hide();
      $("#"+table_id).show();
    }

    private pasteExcelData():void {
        var studentId="";
    var data = $('textarea[name=excel_data]').val();
    //console.log(data);
    var rows = data.split("\n");

   // var table = $('<table />');asdfasfasddfj========asdfasdfasdfasdf==============f -------------asdfasdfasdkflask
    var rowError=false;
    for(var y=0;y<rows.length;y++) {
        if(rows[y]=="") continue;
     // console.log(y); asfasdasdfasdfs;
      var row = rows[y].split("\t");

      if(y==0){
          if(this.validateExcelSheetHeader(row)==false) {
              return;
          }
          continue;
      }

      //for(var x in cells) {
       // row.append('<td>'+cells[x]+'</td>');
        studentId = row[0];
        this.setFieldValue("quiz_"+studentId, row[2]);
        this.setFieldValue("class_perf_"+studentId, row[3]);
        this.setFieldValue("part_a_"+studentId, row[4]);
        this.setFieldValue("part_b_"+studentId, row[5]);
        this.setFieldValue("total_"+studentId, row[6]);
        if(row[7]!="")
            this.setFieldValue("grade_letter_"+studentId, row[7]);

        rowError=false;

        console.log(studentId); //

        this.validateGrade(false,studentId,row[2],row[3],row[4],row[5],row[6],row[7]);

      //}-------==========
     // table.append(row);
    }

// Insert into DOM
    //$('#excel_table').html(table); asdfasdfasdfas
        $('#modal-prompt').modal('hide');



  }
      private validateGrade(force_validate:boolean,student_id:string,quiz:string,class_performance:string,part_a:string,part_b:string,total:string,grade_letter:string):void{
          var row_error:boolean=false;
          var border_error:any={"border" : "2px solid red"};
          var border_ok:any={"border" : "1px solid grey"};

          //Quiz
          if(quiz !="" || force_validate){
              if( (this.checkNumber(quiz)==false ||  Number(quiz)>20)) {
                  $("#quiz_" + student_id).css(border_error);
                  row_error = true;
              }
              else{
                  $("#quiz_"+student_id).css(border_ok);
              }
          }

          //Class Performance
          if(class_performance !="" || force_validate) {
              if (this.checkNumber(class_performance) == false || Number(class_performance) > 10) {
                  $("#class_perf_" + student_id).css(border_error);
                  row_error = true;
              }
              else {
                  $("#class_perf_" + student_id).css(border_ok);
              }
          }

          //Part A
          if(part_a !="" || force_validate){
              if(this.$scope.part_a_total != null &&  (this.checkNumber(part_a)==false ||  Number(part_a)>this.$scope.part_a_total)) {
                  $("#part_a_" + student_id).css(border_error);
                  row_error = true;
              }
              else{
                  $("#part_a_"+student_id).css(border_ok);
              }
          }


          //Part B
          if(part_b !=""  || force_validate){
           if(this.$scope.total_part==2 && this.$scope.part_b_total != null &&  (this.checkNumber(part_b)==false ||  Number(part_b)>this.$scope.part_b_total)) {
               $("#part_b_" + student_id).css(border_error);
               row_error = true;
           }
           else{
               $("#part_b_"+student_id).css(border_ok);
           }
          }

          //Total
          if(total !="" || force_validate){
            if(this.checkNumber(total)==false ||  Number(total)!=Number(quiz)+Number(class_performance)+Number(part_a)+Number(part_b))
              {
                  $("#total_" + student_id).css(border_error);
                  row_error = true;
              }
            else{
                  $("#total_"+student_id).css(border_ok);
              }
          }

          //Grade Letter
          if(grade_letter !="" || force_validate) {
              if (grade_letter != "" && this.gradeLetterFromMarks(Number(total)) != grade_letter) {
                  $("#grade_letter_" + student_id).css(border_error);
                  row_error = true;
              }
              else {
                  $("#grade_letter_"+student_id).css(border_ok);
              }
          }

          var parentRow = document.getElementById("row_" + student_id);
          var tdArray = parentRow.getElementsByTagName('td');


          if(row_error==true ) {
              for (var i = 0; i < tdArray.length; i++) {
                  tdArray[i].style.backgroundColor = "#FCDC3B";//"#FFFF7E";
              }
          }
          else{
              for (var i = 0; i < tdArray.length; i++) {
                  //tdArray[i].style.backgroundColor = "none";
                //  tdArray[i].style.backgroundColor = "transparent";//"#FFFF7E";
              }//asdfasdfasdfasfasdfsadfasdfsssss

          }

      }
      private setFieldValue(field_id:string,field_value:any){
          $("#"+field_id).val(field_value);
      }

    private validateExcelSheetHeader(cells:any):boolean{
console.log(cells);
        if(cells[0]!="Student Id" || cells[1]!="Student Name" || cells[2]!="Quiz" || cells[3]!="Class Perf." || cells[4]!="Part-A" || cells[5]!="Part-B" || cells[6]!="Total" || cells[7]!="Grade Letter")
        {
          this.$scope.excel_copy_paste_error_div=true;
            return false;

        }
        else
            this.$scope.excel_copy_paste_error_div=false;
        return true;


    }

      private validateGradeSheet():boolean{

      return false;
  }

      private gradeLetterFromMarks(total_marks:number):string{

          if(total_marks>=80) return "A+";
          else if(total_marks>=75 && total_marks<80) return "A";
          else if(total_marks>=70 && total_marks<75) return "A-";
          else if(total_marks>=65 && total_marks<70) return "B+";
          else if(total_marks>=60 && total_marks<65) return "B";
          else if(total_marks>=55 && total_marks<60) return "B-";
          else if(total_marks>=50 && total_marks<55) return "C+";
          else if(total_marks>=45 && total_marks<50) return "C";
          else if(total_marks>=40 && total_marks<45) return "D";
          else if(total_marks<40) return "F";

      }

      private saveGradeSheet():boolean{
          var gradeList:Array<IStudentMarks>=new Array<IStudentMarks>();
          var studentId="";
          var studentMark:IStudentMarks;
          for(var ind in this.$scope.allStudents){
              var currentStudent:IStudentMarks=this.$scope.allStudents[ind];
              var studentMark:IStudentMarks;
              if(currentStudent.status==0 || currentStudent.status==1 || currentStudent.status==5){
                  //0= None, 1=Saved, 5=Recheck
                  studentMark=<IStudentMarks>{};
                  studentId=currentStudent.studentId;
                  studentMark.studentId=studentId;
                  studentMark.quiz=$("#quiz_"+studentId).val();
                  studentMark.classPerformance=$("#class_perf_"+studentId).val();

                  studentMark.partA=$("#part_a_"+studentId).val();
                  studentMark.partB=$("#part_b_"+studentId).val();
                  studentMark.total=$("#total_"+studentId).val();
                  studentMark.gradeLetter=$("#grade_letter_"+studentId).val();
                  studentMark.total=$("#total_"+studentId).val();
                  studentMark.status=1;
                  gradeList.push(studentMark);
              }
          }
          var  url="https://localhost/ums-webservice-common/academic/gradeSubmission";
          var complete_json={};
          complete_json["gradeList"] = gradeList;

          var courseInfo:ICourseInfo={
              course_id:'',
              semester_id:0,
              exam_type:0,
              total_part:0,
              part_a_total:0,
              part_b_total:0
          };
          courseInfo.course_id="CID1";
          courseInfo.semester_id=11012016;
          courseInfo.exam_type=1;
          courseInfo.total_part=Number(this.$scope.total_part);
          courseInfo.part_a_total=Number(this.$scope.part_a_total);
          courseInfo.part_b_total=Number(this.$scope.part_b_total);


          complete_json["courseInfo"] = courseInfo;

          console.clear();
          console.log(complete_json);
          //studentMark is undefined asdfasdfasdf asd f asdfasdfasdf asfasdfasfd



          this.httpClient.put(url, complete_json, 'application/json')
              .success(() => {
                  $.notific8("Successfully Saved");
                 /* if(statusId==1)
                      this.$scope.optional.applicationStatus="Submitted";
                  else
                      this.$scope.optional.applicationStatus="Saved";
                      */

              }).error((data) => {
              });

          return false;
      }

      private saveAndSendToScrutinizer():void{

      }


  }

  UMS.controller('MarksSubmission', MarksSubmission);
}
