///<reference path="../../service/HttpClient.ts"/>
module ums {
  export interface IMarksSubmissionScope extends ng.IScope {
    data:any;
    modalSettings:any;
    chartData:any;
    amChartOptions:any;
    inputParams:IInputParams;
    current_courseId:string;
    current_semesterId:number;
    current_examType:number;
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
    courseType:string; // THEORY OR SESSIONAL
    onTotalPartChange: Function;
    toggleStatRules:Function;
    fetchGradeSheet:Function;
    refreshGradeSheet:Function;
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
    recheckRequestHandler:Function;
    closePopupModal:Function;

    userRole:string;
    downloadPdf:Function;
    copyGradeRow:Function;
    loadSemesters:Function;
    loadPrograms:Function;

    generateXls:Function;
    calculateStyle:Function;
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
    regType:number;
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
    course_type:number
  }
  interface IInputParams{
    program_type:number;
    semester_id:number;
    exam_type:number;
    dept_id:string;
    program_id:number;
    status:number;
  }
  interface IOption{
    id:number;
    name:string;
    shortName:string;
  }
  export class MarksSubmission {
    public static $inject = ['$scope', 'appConstants', 'HttpClient','$stateParams', '$window', '$sce', '$q', 'notify'];

    constructor(private $scope:IMarksSubmissionScope,
                private appConstants:any,
                private httpClient:HttpClient, private $stateParams:any,private $window: ng.IWindowService, private $sce: ng.ISCEService,private $q:ng.IQService,private notify: Notify) {

      //console.clear();
      console.log($stateParams["1"]);
      //his.$scope.userRole=$params.role;


      this.$scope.userRole = $stateParams["1"];
      $scope.data = {
        gradeLetterOptions: appConstants.gradeLetters,
        total_part:Number,
        part_a_total:Number,
        part_b_total:Number,
        course_no:String,
        course_title:String,
        crhr:Number,
        semester_name:String,
        dept_name:String,
        recheck_accepted_studentId:String,
        semesters: Array<IOption>(),
        depts:Array<IOption>(),
        ugDepts: appConstants.ugDept,
        pgDepts: appConstants.pgDept,
        ugPrograms: appConstants.ugPrograms,
        pgPrograms: appConstants.pgPrograms,
        programs:Array<IOption>(),
        markSubmissionStatus:appConstants.marksSubmissionStatus
      };

      $scope.modalSettings = {};
      this.$scope.modalSettings.header = "Confirmation";

      $scope.onTotalPartChange = this.onTotalPartChange.bind(this);
      $scope.toggleStatRules = this.toggleStatRules.bind(this);
      $scope.fetchGradeSheet = this.fetchGradeSheet.bind(this);
      $scope.refreshGradeSheet=this.refreshGradeSheet.bind(this);
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
      $scope.recheckRequestHandler=this.recheckRequestHandler.bind(this);
      $scope.loadSemesters=this.loadSemesters.bind(this);
      $scope.loadPrograms=this.loadPrograms.bind(this);

      $scope.generateXls=this.generateXls.bind(this);
      $scope.calculateStyle=this.calculateStyle.bind(this);

      $scope.inputParams={program_type:11,
        semester_id:11,
        exam_type:1,
        dept_id:'',
        program_id:1,
        status:-1};

      this.loadSemesters();

      $scope.data.recheck_accepted_studentId="";
      $scope.chartData =[];
      this.initChart();
    }

    private generateXls(): void {
      this.httpClient.get("/ums-webservice-common/gradeReport/xls/semester/"+this.$scope.inputParams.semester_id+"/courseid/"+this.$scope.current_courseId+"/examtype/"+this.$scope.inputParams.exam_type+"/coursetype/"+(this.$scope.courseType=="THEORY"?"1":"2")+"/role/"+this.$scope.currentActor, 'application/vnd.ms-excel',
          (data: any, etag: string) => {
            var file = new Blob([data], {type: 'application/vnd.ms-excel'});
            var reader = new FileReader();
            reader.readAsDataURL(file);
            reader.onloadend = function (e) {
              window.open(reader.result, 'Excel', 'width=20,height=10,toolbar=0,menubar=0,scrollbars=no', false);
            };
          },
          (response: ng.IHttpPromiseCallbackArg<any>) => {
            console.error(response);
          }, 'arraybuffer');
    }

    private calculateStyle(regType:number):any{
      var style={backgroundColor:''};
      if (regType == 2) style.backgroundColor='#BBFFFF';
      else if (regType == 3) style.backgroundColor='#CCCCFF';
      else if (regType == 4) style.backgroundColor='#00FF00';
      else if (regType == 5) style.backgroundColor='#FFF68F';

      return style;
    }

    private fetchCurrentUser():ng.IPromise<any> {
      var url="/ums-webservice-common/users/current";
      var defer = this.$q.defer();
      this.httpClient.get(url, this.appConstants.mimeTypeJson,
          (json:any, etag:string) => {
            //console.log(json);
             defer.resolve({id:json.departmentId,name:json.departmentName});
          },
          (response:ng.IHttpPromiseCallbackArg<any>) => {
            console.error(response);
          });
      return defer.promise;
    }


    private loadSemesters():void{
      console.log("~~~~~~~~~~~~ddd~~~~~asdfsadasadfasdfafsdf~~");
      this.fetchSemesters(this.$scope.inputParams.program_type).then((semesters:Array<IOption>)=> {
        this.$scope.data.semesters=semesters;
        this.$scope.inputParams.semester_id=semesters[0].id;

      });
      if(this.$scope.inputParams.program_type==11) {
        if(this.$scope.userRole=="H") {

          this.fetchCurrentUser().then((departmentJson:any)=> {
            this.$scope.data.depts = [departmentJson];
            this.$scope.inputParams.dept_id=departmentJson.id;
            this.loadPrograms();

          });




        }
        else
          this.$scope.data.depts = this.$scope.data.ugDepts;
      }
      else if(this.$scope.inputParams.program_type==22)
        this.$scope.data.depts=this.$scope.data.pgDepts;

      console.log(this.$scope.data.depts);
      console.log(this.$scope.data.ugDepts);
      console.log(this.$scope.data.pgDepts);
    }

    private fetchSemesters(programType:number):ng.IPromise<any> {
      var url="/ums-webservice-common/academic/semester/program-type/"+programType+"/limit/0";
      var defer = this.$q.defer();
      this.httpClient.get(url, this.appConstants.mimeTypeJson,
          (json:any, etag:string) => {
            var semesters:any = json.entries;
            defer.resolve(semesters);
          },
          (response:ng.IHttpPromiseCallbackArg<any>) => {
            console.error(response);
          });
      return defer.promise;
    }

    private loadDepartments():void{
      console.log("------------");
      this.fetchSemesters(this.$scope.inputParams.program_type).then((semesters:Array<IOption>)=> {
        this.$scope.data.semesters=semesters;
        console.log(semesters);
        //this.$scope.CrHr= CrHr;
      });
    }
    private loadPrograms():void{
      var programArr:any;
      var controllerScope=this.$scope;
      if(this.$scope.inputParams.program_type==11)
        programArr=this.$scope.data.ugPrograms;
      else if(this.$scope.inputParams.program_type==22)
        programArr=this.$scope.data.pgPrograms;
      var programJson = $.map(programArr, function(el) { return el });
      console.log(programJson);
      console.log("~~~~~~~~~~~afadsf~~~~~~~~");
      var resultPrograms:any = $.grep(programJson, function(e:any){ return e.deptId ==controllerScope.inputParams.dept_id; });

      console.log("~~~~~~~~~~~~basddsf~~~~~~~");

      this.$scope.data.programs= resultPrograms[0].programs;
      console.log(resultPrograms[0].programs[0]);
      this.$scope.inputParams.program_id=resultPrograms[0].programs[0].id;

      console.log(this.$scope.data.programs);
    }

    private initChart():void{
      this.$scope.amChartOptions = {
        data:  this.$scope.chartData,
        type: "serial",
        categoryField: "gradeLetter",
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
          valueField: "total",
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


    private fetchChartData():ng.IPromise<any> {

      var url="academic/gradeSubmission/chartdata/semester/"+this.$scope.inputParams.semester_id+"/courseid/"+this.$scope.current_courseId+"/examtype/"+this.$scope.inputParams.exam_type+"/coursetype/"+(this.$scope.courseType=="THEORY"?"1":"2");
      var defer = this.$q.defer();
      this.httpClient.get(url, this.appConstants.mimeTypeJson,
          (json:any, etag:string) => {
            var chartData:any = json.entries;
            defer.resolve(chartData);
          },
          (response:ng.IHttpPromiseCallbackArg<any>) => {
            console.error(response);
          });
      return defer.promise;
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

      var recheckRequestStudentList:Array<IStudent> = new Array<IStudent>();
      var approveStudentList:Array<IStudent> = new Array<IStudent>();
      var student:IStudent;
      $("#tbl_recheck_accepted  tbody tr[id^='recheck_accepted_']").each(function (i, el) {
        student={studentId:""}
        student.studentId=el.id.substr(17,9);
        recheckRequestStudentList.push(student);
      });

      var url = "academic/gradeSubmission/recheckApprove";
      console.clear();
      console.log(recheckRequestStudentList);
      var complete_json =this.createCompleteJson("recheck_request_submit",null,recheckRequestStudentList,approveStudentList);
      console.log(complete_json);

      this.httpClient.put(url, complete_json, 'application/json')
          .success(() => {
            $.notific8("Successfully Saved");
            this.fetchGradeSheet(this.$scope.current_courseId,this.$scope.current_semesterId,this.$scope.current_examType);
            /* if(statusId==1)
             this.$scope.optional.applicationStatus="Submitted";
             else
             this.$scope.optional.applicationStatus="Saved";
             */
          }).error((data) => {
          });
    }

    private recheckRequestHandler(actor:string,action:string):void{
      var url = "academic/gradeSubmission/vc/recheckApprove";
      console.clear();
      var complete_json =this.createCompleteJson(action,null,null,null);
      console.log(complete_json);

      this.httpClient.put(url, complete_json, 'application/json')
          .success(() => {
            $.notific8("Successfully Saved");
            this.fetchGradeSheet(this.$scope.current_courseId,this.$scope.current_semesterId,this.$scope.current_examType);
            /* if(statusId==1)
             this.$scope.optional.applicationStatus="Submitted";
             else
             this.$scope.optional.applicationStatus="Saved";
             */
          }).error((data) => {
          });
    }


    private downloadPdf():void {
      this.httpClient.get("/ums-webservice-common/gradeReport/pdf/semester/"+this.$scope.inputParams.semester_id+"/courseid/"+this.$scope.current_courseId+"/examtype/"+this.$scope.current_examType+"/role/"+this.$scope.currentActor, 'application/pdf',

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

      var status=0;
      if(this.$scope.userRole!="T"){
        status=this.$scope.inputParams.status;
      }


      //this.httpClient.get("academic/gradeSubmission/semester/"+this.$scope.inputParams.semester_id+"/examtype/"+this.$scope.inputParams.exam_type+"/dept/"+this.$scope.inputParams.dept_id+"/role/"+this.$scope.userRole+"/status/"+status,
      this.httpClient.get("academic/gradeSubmission/semester/"+this.$scope.inputParams.semester_id+"/examtype/"+this.$scope.inputParams.exam_type+"/dept/05/role/"+this.$scope.userRole+"/status/"+status,
          this.appConstants.mimeTypeJson,
          (data:any, etag:string)=> {
            this.$scope.allMarksSubmissionStatus = data.entries;
          });

      $("#leftDiv").hide();
      $("#arrowDiv").show();

      $("#rightDiv").removeClass("orgRightClass");
      $("#rightDiv").addClass("newRightClass");
    }


    private refreshGradeSheet():void{
      this.fetchGradeSheet(this.$scope.current_courseId,this.$scope.current_semesterId,this.$scope.current_examType);
    }

    private fetchGradeSheet(courseId:string,semesterId:number,examType:number):void {
      this.$scope.current_courseId=courseId;
      this.$scope.current_semesterId=semesterId;
      this.$scope.current_examType=examType;

      /*
       var topPanelDiv=$("#panel_top");
       console.clear();
       console.log($("#panel_top").width());
       console.log($("#panel_top").height());
       $(".panel_overlay").css({
       background:'url("https://localhost/ums-web/iums/images/overlay1.png")',
       opacity : 0.5,
       top     : topPanelDiv.position().top-150,
       width   : $("#panel_top").width(),
       height  : $("#panel_top").height(),
       zIndex:100
       });
       $(".panel_overlay").fadeIn();
       */

      $('.page-title.ng-binding').html("Online Grade Submission/Approval");

      this.$scope.toggleColumn = true;
      var url="academic/gradeSubmission/semester/"+semesterId+"/courseid/"+this.$scope.current_courseId+"/examtype/"+examType+"/role/"+this.$scope.userRole;
      this.httpClient.get(url,
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


            //$("#total_part").val(part_info.total_part);
            this.$scope.data.part_a_total = part_info.part_a_total == 0 ? null : part_info.part_a_total;
            this.$scope.data.part_b_total = part_info.part_b_total == 0 ? null : part_info.part_b_total;
            this.$scope.data.total_part = part_info.total_part;

            this.$scope.data.course_no = part_info.courseNo;
            this.$scope.data.course_title = part_info.courseTitle;
            this.$scope.data.crhr = part_info.cRhR;
            this.$scope.data.semester_name = part_info.semesterName;
            this.$scope.data.dept_name = part_info.deptSchoolName;

            this.$scope.gradeSubmissionStatus=part_info.statusId;
            this.$scope.courseType=part_info.courseType;
            this.$scope.currentActor = data.current_actor;
            $("#partDiv").show();

            if( this.$scope.currentActor=="preparer" &&  this.$scope.gradeSubmissionStatus==0){
              this.$scope.gradeTitle="Non-Submitted Grades";

              this.$scope.modalSettings.submitBody = "Are you sure you want to send grades to the Scrutinizer?";
              this.$scope.modalSettings.submitHandler = "submitModal";
              this.$scope.modalSettings.submitRightButton = () => {
                this.saveAndSendToScrutinizer();
              }
            }
            if( this.$scope.currentActor=="scrutinizer" &&  this.$scope.gradeSubmissionStatus==1){
              this.$scope.gradeTitle="Waiting for Scrutinizer's Approval";
              this.$scope.approveAction="Scrutiny";
              //this.$scope.approveStatusName="scrutinized"; //may be we can remove this
              //this.$scope.approveStatusId=4; // May be we can remove this....
              this.$scope.recheckButtonLabel="Save & Send back to Preparer";
              this.$scope.approveButtonLabel="Scrutiny & Send to Head";
              this.$scope.candidatesGrades=this.$scope.scrutinizeCandidatesGrades;

              this.$scope.modalSettings.recheckBody = "Are you sure you want to send back the selected grades to preparer for recheck?";
              this.$scope.modalSettings.recheckHandler = "recheckModal";
              this.$scope.modalSettings.approveBody = "Are you sure you want to send grades to the Head for Approval?";
              this.$scope.modalSettings.approveHandler = "approveModal";
              this.$scope.modalSettings.rightButton = (currentActor,action) => {
                this.saveRecheckApproveGrades(currentActor,action);
              }

            }
            if( this.$scope.currentActor=="head" &&  this.$scope.gradeSubmissionStatus==3){
              this.$scope.gradeTitle="Waiting for Head's Approval";
              this.$scope.approveAction="Approve";
              this.$scope.recheckButtonLabel="Save & Send back to Preparer";
              this.$scope.approveButtonLabel="Approve & Send to CoE";
              this.$scope.candidatesGrades=this.$scope.approveCandidatesGrades;

              this.$scope.modalSettings.recheckBody = "Are you sure you want to send back the selected grades to preparer for recheck?";
              this.$scope.modalSettings.recheckHandler = "recheckModal";
              this.$scope.modalSettings.approveBody = "Are you sure you want to send grades to the CoE for Acceptance?";
              this.$scope.modalSettings.approveHandler = "approveModal";
              this.$scope.modalSettings.rightButton = (currentActor,action) => {
                this.saveRecheckApproveGrades(currentActor,action);
              }

            }
            if( this.$scope.currentActor=="coe"){
              if(this.$scope.gradeSubmissionStatus==5) {
                this.$scope.gradeTitle = "Waiting for CoE's Approval";
                this.$scope.approveAction = "Accept";
                this.$scope.recheckButtonLabel = "Save & Send back to Preparer";
                this.$scope.approveButtonLabel = "Save and Accept";
                this.$scope.candidatesGrades = this.$scope.acceptCandidatesGrades;

                this.$scope.modalSettings.recheckBody = "Are you sure you want to send back the selected grades to preparer for recheck?";
                this.$scope.modalSettings.recheckHandler = "recheckModal";
                this.$scope.modalSettings.approveBody = "Are you sure you want to Accept the grade sheet?";
                this.$scope.modalSettings.approveHandler = "approveModal";
                this.$scope.modalSettings.rightButton = (currentActor, action) => {
                  this.saveRecheckApproveGrades(currentActor, action);
                }
              }
              else if(this.$scope.gradeSubmissionStatus==7){
                this.$scope.modalSettings.submitBody = "Are you sure you want to send grade recheck request to Honorable Vice Chancellor?";
                this.$scope.modalSettings.submitHandler = "submitModal";
                this.$scope.modalSettings.submitRightButton = () => {
                  this.sendRecheckRequestToVC();
                }
              }

            }
            if( this.$scope.currentActor=="vc" && this.$scope.gradeSubmissionStatus==8) {

              this.$scope.modalSettings.rejectBody = "Are you sure you want to reject the recheck request?";
              this.$scope.modalSettings.rejectHandler = "rejectModal";
              this.$scope.modalSettings.rejectRightButton = (currentActor,action) => {
                this.recheckRequestHandler(currentActor,action);
              }

              this.$scope.modalSettings.approveBody = "Are you sure you want to approve the recheck request?";
              this.$scope.modalSettings.approveHandler = "approveModal";
              this.$scope.modalSettings.approveRightButton = (currentActor,action) => {
                this.recheckRequestHandler(currentActor,action);
              }
            }

            //Fetch Chart Data ---
            this.fetchChartData().then((chartData:any)=> {
              this.$scope.$broadcast("amCharts.updateData", chartData);
              this.$scope.chartData=chartData;
            });


          });

      $("#selection1").hide();
      $("#selection2").show();
      //$("#btn_stat").focus();
      $(window).scrollTop($('#panel_top').offset().top - 56);

      //$(".panel_overlay").fadeOut();

    }

    public recalculateTotalAndGradeLetter():void {

    }

    private calculateTotalAndGradeLetter(student_id:string):void {
      var total;
      var regType=$("#reg_type_" + student_id).val();
      if(this.$scope.courseType=="THEORY") {
        var quiz:number = Number($("#quiz_" + student_id).val()) || 0;
        var class_perf:number = Number($("#class_perf_" + student_id).val()) || 0;
        var part_a:number = Number($("#part_a_" + student_id).val()) || 0;
        var part_b:number = 0;

        if ($("#total_part") && $("#total_part").val() == 2)
          part_b = Number($("#part_b_" + student_id).val()) || 0;
        if (this.$scope.data.total_part == 2)
          part_b = Number($("#part_b_" + student_id).val()) || 0;

        total = quiz + class_perf + part_a + part_b;
        $("#total_" + student_id).val(String(total));
        var grade_letter:string = this.gradeLetterFromMarks(total,regType);
        $("#grade_letter_" + student_id).val(grade_letter);

        this.validateGrade(false, student_id, String(quiz), String(class_perf), String(part_a), String(part_b), String(total), grade_letter,regType);
      }
      else {
        total = $("#total_" + student_id).val();
        $("#total_" + student_id).val(String(total));
        var grade_letter:string = this.gradeLetterFromMarks(total,regType);
        $("#grade_letter_" + student_id).val(grade_letter);
        this.validateGrade(false, student_id, "", "", "", "", String(total), grade_letter,regType);
      }

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
      var regType:number;
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
        regType=$("#reg_type_" + studentId).val();
        console.clear();
        console.log(row);
        console.log(studentId);
        rowError = false;
        if(this.$scope.courseType=="THEORY") {
          this.setFieldValue("quiz_" + studentId, row[2]);
          this.setFieldValue("class_perf_" + studentId, row[3]);
          this.setFieldValue("part_a_" + studentId, row[4]);
          this.setFieldValue("part_b_" + studentId, row[5]);
          this.setFieldValue("total_" + studentId, row[6]);
          if (row[7] != "")
            this.setFieldValue("grade_letter_" + studentId, row[7]);
          else
            this.setFieldValue("grade_letter_" + studentId, this.gradeLetterFromMarks(Number(row[6]),regType));


          this.validateGrade(false, studentId, row[2], row[3], row[4], row[5], row[6], $("#grade_letter_" + studentId).val(),regType);
        }
        else{
          this.setFieldValue("total_" + studentId, row[2]);
          if (row[3] != "")
            this.setFieldValue("grade_letter_" + studentId, row[3]);
          else
            this.setFieldValue("grade_letter_" + studentId, this.gradeLetterFromMarks(Number(row[2]),regType));

          this.validateGrade(false, studentId, "","", "", "", row[2], $("#grade_letter_" + studentId).val(),regType);
        }


        //}-------==========
        // table.append(row);
      }

// Insert into DOM
      //$('#excel_table').html(table); asdfasdfasdfas
      $('#modal-prompt').modal('hide');


    }

    private validateGrade(force_validate:boolean, student_id:string, quiz:string, class_performance:string, part_a:string, part_b:string, total:string, grade_letter:string,reg_type:number):boolean {
      var row_error:boolean = false;
      var border_error:any = {"border": "2px solid red"};
      var border_ok:any = {"border": "1px solid grey"};

      if(this.$scope.courseType=="THEORY") {
        //Quiz
        if (quiz != "" || force_validate) {
          if (((this.checkNumber(quiz) == false || Number(quiz) > 20) && reg_type==1 )) {
            $("#quiz_" + student_id).css(border_error);
            row_error = true;
          }
          else {
            $("#quiz_" + student_id).css(border_ok);
          }
        }

        //Class Performance
        if (class_performance != "" || force_validate) {
          if ((this.checkNumber(class_performance) == false || Number(class_performance) > 10) && reg_type==1) {
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

      } //End of if
      if(this.$scope.courseType=="SESSIONAL") {
        //Total
        if (total != "" || force_validate) {
          if (this.checkNumber(total) == false) {
            $("#total_" + student_id).css(border_error);
            row_error = true;
          }
          else {
            $("#total_" + student_id).css(border_ok);
          }
        }
      }
      //Grade Letter
      if (grade_letter != "" || force_validate) {
        if (grade_letter != "" && this.gradeLetterFromMarks(Number(total),reg_type) != grade_letter) {
          $("#grade_letter_" + student_id).css(border_error);
          row_error = true;
        }
        else {
          $("#grade_letter_" + student_id).css(border_ok);
        }
      }

      var parentRow = document.getElementById("row_" + student_id);
      console.log(parentRow);
      var tdArray = parentRow.getElementsByTagName('td');

      if (row_error == true) {
        for (var i = 0; i < tdArray.length; i++) {
          if($("#reg_type_"+student_id).val()==1  || (i!=0 && $("#reg_type_"+student_id).val()!=1))
            tdArray[i].style.backgroundColor = "#FCDC3B";//"#FFFF7E";
        }
      }
      else {
        for (var i = 0; i < tdArray.length; i++) {
          if($('#'+tdArray[i].id).is("[style]") && ($("#reg_type_"+student_id).val()==1  || (i!=0 && $("#reg_type_"+student_id).val()!=1))) {
            $('#' + tdArray[i].id).attr('style', function (i, style) {
              return style.replace(/background-color[^;]+;?/g, '');
            });
          }
          //('#foo [style]').removeAttr('style');
        }//asdfasdfasdf

      }
      return row_error;

    }

    private setFieldValue(field_id:string, field_value:any) {
      $("#" + field_id).val(field_value);
    }

    private validateExcelSheetHeader(cells:any):boolean {
      console.log(cells);
      if ( this.$scope.courseType=="THEORY"){
        if(cells[0] != "Student Id" || cells[1] != "Student Name" || cells[2] != "Quiz" || cells[3] != "Class Perf." || cells[4] != "Part-A" || cells[5] != "Part-B" || cells[6] != "Total" || cells[7] != "Grade Letter") {
          this.$scope.excel_copy_paste_error_div = true;
          return false;
        }
        else
          this.$scope.excel_copy_paste_error_div = false;
      }
      else if ( this.$scope.courseType=="SESSIONAL"){
        if(cells[0] != "Student Id" || cells[1] != "Student Name" || cells[2] != "Total" || cells[3] != "Grade Letter") {
          this.$scope.excel_copy_paste_error_div = true;
          return false;
        }
        else
          this.$scope.excel_copy_paste_error_div = false;
      }

      return true;


    }

    private validateGradeSheet():boolean {
      return false;
    }

    private gradeLetterFromMarks(total_marks:number,reg_type:number):string {

      if (total_marks >= 80 && reg_type==1) return "A+";
      else if (total_marks >= 75 && total_marks < 80 && reg_type==1) return "A";
      else if (total_marks >= 70 && total_marks < 75 && reg_type==1) return "A-";
      else if (total_marks >= 65 && total_marks < 70 && reg_type==1) return "B+";
      else if (total_marks >= 60 && total_marks < 65 && reg_type==1) return "B";
      else if (total_marks >= 60 &&  reg_type==5) return "B"; // Improvement
      else if (total_marks >= 55 && total_marks < 60 && (reg_type==1 || reg_type==5)) return "B-";
      else if (total_marks >= 50 && total_marks < 55 && (reg_type==1 || reg_type==5)) return "C+";
      else if (total_marks >= 45 && total_marks < 50) return "C";
      else if (total_marks >= 45 && (reg_type==2 || reg_type==3  || reg_type==4)) return "C";  //Carry,Clearance, Special Carry
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
          studentMark.regType=$("#reg_type_" + studentId).val();
          if(this.$scope.courseType=="THEORY") {
            studentMark.quiz = $("#quiz_" + studentId).val();
            studentMark.classPerformance = $("#class_perf_" + studentId).val();

            studentMark.partA = $("#part_a_" + studentId).val();
            studentMark.partB = $("#part_b_" + studentId).val();
          }
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
      if(this.$scope.courseType=="THEORY") {
        for (var ind in gradeList) {
          var studentMark:IStudentMarks = gradeList[ind];
          if (this.validateGrade(true, studentMark.studentId, studentMark.quiz.toString(), studentMark.classPerformance.toString(), studentMark.partA.toString(), studentMark.partB.toString(), studentMark.total.toString(), studentMark.gradeLetter,studentMark.regType) == true)
            validate = false;
        }
      }
      else if(this.$scope.courseType=="SESSIONAL") {
        for (var ind in gradeList) {
          var studentMark:IStudentMarks = gradeList[ind];
          if (this.validateGrade(true, studentMark.studentId,"", "", "", "", studentMark.total.toString(), studentMark.gradeLetter,studentMark.regType) == true)
            validate = false;
        }
      }
      if (validate == false) {
        alert("There are some problem with the data you submitted. Please check and correct. Then submit it again.");
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
            this.fetchGradeSheet(this.$scope.current_courseId,this.$scope.current_semesterId,this.$scope.current_examType);
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
        part_b_total: 0,
        course_type:0
      };
      //alert(this.$scope.data.part_a_total);
      courseInfo.course_id = this.$scope.current_courseId;
      courseInfo.semester_id = Number(this.$scope.inputParams.semester_id);
      courseInfo.exam_type = Number(this.$scope.inputParams.exam_type);
      courseInfo.course_type=this.$scope.courseType=="THEORY"?1:2;
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
        background:'url("images/overlay1.png")',
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
            this.fetchGradeSheet(this.$scope.current_courseId,this.$scope.current_semesterId,this.$scope.current_examType);
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
