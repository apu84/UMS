///<reference path="../../service/HttpClient.ts"/>

module ums {
  export interface IMarksSubmissionScope extends ng.IScope {
    allStudents: any;
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


        //$('.page-title.ng-binding').html("Online Grade Submission &nbsp;&nbsp;&nbsp;&nbsp;<a href='javascript:void(0)' onclick='goBack()'><i class='fa fa-arrow-circle-left'></i></a>");

    }


    public onTotalPartChange(): void {

      if(this.$scope.total_part==1) {
        this.$scope.toggleColumn = false;
        $("#partDiv").hide();
      }
      else {

        this.$scope.toggleColumn = true;
        $("#partDiv").show();
      }

      this.$scope.$broadcast("renderScrollableTable");

    //this.$scope.$broadcast("renderScrollableTable");
  }
    private toggleStatRules(table_id:string){
      $("#tbl_stat").hide();
      $("#tbl_rules").hide();
      $("#"+table_id).show();
    }

    private pasteExcelData():void {
    var data = $('textarea[name=excel_data]').val();
    //console.log(data);
    var rows = data.split("\n");

   // var table = $('<table />');asdfasfasdf -------------

    for(var y in rows) {
     // console.log(y);
      var row = rows[y].split("\t");
      if(y==0){
        this.validateExcelSheetHeader(row);
      }
      //var row = $('<tr /....----asdfasfd=====------------====
      //for(var x in cells) {
       // row.append('<td>'+cells[x]+'</td>');
        console.log("quiz_"+row[0]+"---"+row[2]);
        this.setFieldValue("quiz_"+row[0], row[2]);
        this.setFieldValue("class_perf_"+row[0], row[3]);
        this.setFieldValue("part_a_"+row[0], row[4]);
        this.setFieldValue("part_b_"+row[0], row[5]);
        this.setFieldValue("total_"+row[0], row[6]);
        this.setFieldValue("grade_letter_"+row[0], row[7]);

      //}-------==========
     // table.append(row);
    }

// Insert into DOM
    //$('#excel_table').html(table);
        $('#modal-prompt').modal('hide');



  }
      private setFieldValue(field_id:string,field_value:any){
          $("#"+field_id).val(field_value);
      }

    private validateExcelSheetHeader(cells:any):void{;
console.log(cells);
        if(cells[0]!="Student Id" || cells[1]!="Student Name" || cells[2]!="Quiz" || cells[3]!="Class Perf." || cells[4]!="Part-A" || cells[5]!="Part-B" || cells[6]!="Total" || cells[7]!="Grade Letter")
        {
          this.$scope.excel_copy_paste_error_div=true;

        }
        else
            this.$scope.excel_copy_paste_error_div=false;


    }


  }

  UMS.controller('MarksSubmission', MarksSubmission);
}
