///<reference path="../../service/HttpClient.ts"/>

module ums {
  export interface IMarksSubmissionScope extends ng.IScope {
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
    fetchGradeSubmissionTable:Function;
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



  export class MarksSubmission {
    public static $inject = ['$scope', 'appConstants', 'HttpClient'];

    constructor(private $scope:IMarksSubmissionScope,
                private appConstants:any,
                private httpClient:HttpClient) {


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
        $scope.fetchGradeSubmissionTable=this.fetchGradeSubmissionTable.bind(this);


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
            this.$scope.part_b_total=part_info.part_b_total==0?null:part_info.part_b_total

          });

        $("#selection1").hide();
        $("#selection2").show();
        //$("#btn_stat").focus();

      $(window).scrollTop($('#panel_top').offset().top-56);


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
        //Quiz
        if(row[2] !="" && (this.checkNumber(row[2])==false ||  row[2]>20)){
            document.getElementById("quiz_"+studentId).style.border = "2px solid red";
            rowError=true;
        }
        else{
            document.getElementById("quiz_"+studentId).style.border = "1px solid grey";
        }

        //Class Performance
        if(row[3] !="" && (this.checkNumber(row[3])==false ||  row[3]>10)){
            document.getElementById("class_perf_"+studentId).style.border = "2px solid red";
            rowError=true;
        }
        else{
            document.getElementById("class_perf_"+studentId).style.border = "1px solid grey";
        }

        //Part A
        if(this.$scope.part_a_total != null && row[4] !="" &&  (this.checkNumber(row[4])==false ||  row[4]>this.$scope.part_a_total)){
            document.getElementById("part_a_"+studentId).style.border = "2px solid red";
            rowError=true;
        }
        else{
            document.getElementById("part_a_"+studentId).style.border = "1px solid grey";
        }

        //Part B
        if(this.$scope.total_part==2 && this.$scope.part_b_total != null && row[5] !="" &&  (this.checkNumber(row[5])==false ||  row[5]>this.$scope.part_b_total)){
            document.getElementById("part_b_"+studentId).style.border = "2px solid red";
            rowError=true;
        }
        else{
            document.getElementById("part_b_"+studentId).style.border = "1px solid grey";
        }

        //Total
        if(row[6] !="" &&  (this.checkNumber(row[6])==false ||  row[6]>row[2]+row[3]+row[4]+row[5])){
            document.getElementById("total_"+studentId).style.border = "2px solid red";
            rowError=true;
        }
        else{
            document.getElementById("total_"+studentId).style.border = "1px solid grey";
        }

        //Grade Letter
        if(row[7] !="" &&  (this.checkNumber(row[7])==false ||  this.gradeLetterFromMarks(parseFloat(row[6]))!=row[7])){
            document.getElementById("grade_letter_"+studentId).style.border = "2px solid red";
            rowError=true;
        }
        else{
            document.getElementById("grade_letter_"+studentId).style.border = "1px solid grey";
        }


        if(rowError==true ) {
            var parentRow = document.getElementById("row_" + row[0]);
            var tdArray = parentRow.getElementsByTagName('td');
            for (var i = 0; i < tdArray.length; i++) {
                tdArray[i].style.backgroundColor = "#FCDC3B";//"#FFFF7E";
            }

        }

      //}-------==========
     // table.append(row);
    }

// Insert into DOM
    //$('#excel_table').html(table); asdfasdfasdfas
        $('#modal-prompt').modal('hide');



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
          complete_json["course_id"] = "cId";
          complete_json["semester_id"] = "sId";
          complete_json["exam_type"] = "examType";
          complete_json["total_part"] = 2;
          complete_json["part_a_total"] = 35;
          complete_json["part_b_total"] = 35;

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


  }

  UMS.controller('MarksSubmission', MarksSubmission);
}
