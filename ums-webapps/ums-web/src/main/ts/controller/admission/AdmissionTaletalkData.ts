/**
 * Created by My Pc on 17-Dec-16.
 */

module ums{
  import Semester = ums.Semester;
  interface IAdmissionTaletalkData extends ng.IScope{

    data:any;
    semesters:Array<Semester>;
    semester:Semester;
    programTypes:Array<IProgramType>;
    programType:IProgramType;
    modalData:string;
    admissionStudents:Array<AdmissionStudent>;

    showUploadPortion:boolean;

    getSemesters:Function;
    fetchTaletalkData:Function;
    fetchExcelFormat:Function;
    showPopupModal:Function;
    closePopupModal:Function;
    processData:Function;
  }


  interface  IProgramType{
    id:string;
    name:string;
  }


  class AdmissionTaletalkData{
    public static $inject = ['appConstants','HttpClient','$scope','$q','notify','$sce','$window','semesterService','facultyService', 'admissionStudentService'];
    constructor(private appConstants: any,
                private httpClient: HttpClient,
                private $scope: IAdmissionTaletalkData,
                private $q:ng.IQService,
                private notify: Notify,
                private $sce:ng.ISCEService,
                private $window:ng.IWindowService,
                private semesterService: SemesterService,
                private facultyService: FacultyService,
                private admissionStudentService:AdmissionStudentService) {

      $scope.modalData="";
      $scope.data = {
        settings:{
          colHeaders: true,
          rowHeaders: true,
          currentRowClassName: 'currentRow',
          currentColClassName: 'currentCol',
          fillHandle: false,
          manualRowResize:true,
          manualColumnResize:true,
          columnSorting:true,
          sortIndicator:true,
          readOnly:true,
          width:$(".page-content").width()
        }
      };
      $scope.programTypes = appConstants.programType;
      $scope.programType = $scope.programTypes[0];
      $scope.getSemesters = this.getSemesters.bind(this);
      $scope.fetchTaletalkData = this.fetchTaletalkData.bind(this);
      $scope.fetchExcelFormat = this.fetchExcelFormat.bind(this);
      $scope.showPopupModal = this.showPopupModal.bind(this);
      $scope.closePopupModal = this.closePopupModal.bind(this);
      $scope.processData = this.processData.bind(this);

      this.getSemesters();
      Utils.setValidationOptions("form-horizontal");
      

    }




    private getSemesters(){
      console.log("This is program type");
      console.log(this.$scope.programType);

      this.semesterService.fetchSemesters(+this.$scope.programType.id).then((semesters:Array<Semester>)=>{
        this.$scope.semesters=[];
        for(var i=0;i<semesters.length;i++){
          if(semesters[i].status==2){
            this.$scope.semester = semesters[i];
          }
          this.$scope.semesters.push(semesters[i]);
        }
        console.log(this.$scope.semesters);
      });
    }

    private fetchTaletalkData(){
      Utils.expandRightDiv();
      this.closePopupModal();

      this.admissionStudentService.fetchTaletalkData(this.$scope.semester.id).then((admissionStudents:Array<AdmissionStudent>)=>{
        if(admissionStudents.length==0){
          this.$scope.showUploadPortion=true;
        }else{
          this.$scope.showUploadPortion=false;
        }
      });
    }

    private fetchExcelFormat(){
      this.admissionStudentService.downloadExcelFile(this.$scope.semester.id);
    }


    private closePopupModal():void{
      $("#msg_div").css({
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
      $("#msg_div").css({
        display:"block",
        top     : $(".table_overlay").position().top,
        left: $(".table_overlay").position().left+(topDiv.outerWidth()+20)/2-$("#msg_div").width()/2,
        zIndex:105
      });
    }


    private processData(modalData:any):void{
      console.log("Modal data---->");
      this.$scope.admissionStudents=[];

      this.fillUpAdmissionStudents(modalData).then((students:Array<AdmissionStudent>)=>{
        this.$scope.showUploadPortion=false;
      });

    }

    private fillUpAdmissionStudents(modalData:any):ng.IPromise<any>{

      var defer = this.$q.defer();

      var rows = modalData.split("\n");
      var counter:number=0;
      for(var r in rows){
        var cells = rows[r].split('\t');
        //console.log(cells[0]);
        if(+r==0 && cells.length>20 || cells.length<20){
          this.notify.error("Wrong format, please paste the excel data again")
          break;
        }else{
          if(+r!=0){
            this.insertDataIntoAdmissionStudents(cells);
          }
        }

      }


      defer.resolve(this.$scope.admissionStudents);
      return defer.promise;
    }

    private insertDataIntoAdmissionStudents(cellData:Array<string>){

      var student:any={};
      student.receiptId=cellData[0];
      student.pin = cellData[1];
      student.hscBoard = cellData[2];
      student.hscRoll = cellData[3];
      student.hscRegNo=cellData[4];
      student.hscYear=+cellData[5];
      student.hscGroup = cellData[6];
      student.sscBoard=cellData[7];
      student.sscRoll=cellData[8];
      student.sscYear=+cellData[9];
      student.sscGroup=cellData[10];
      student.gender=cellData[11];
      var formatedBirthDate="";
      if(cellData[12].length==6){
        formatedBirthDate = [cellData[12].slice(0,2),'/',cellData[12].slice(2,4),'/',cellData[12].slice(4,6)].join('');
      }else{
        formatedBirthDate = ['0',cellData[12].slice(0,1),'/',cellData[12].slice(1,3),'/',cellData[12].slice(3,5),''].join('');
      }


      student.dateOfBirth=formatedBirthDate;
      student.studentName=cellData[13];
      student.fatherName=cellData[14];
      student.motherName=cellData[15];
      student.sscGpa=cellData[16];
      student.hscGpa = cellData[17];
      student.quota=cellData[18];
      student.unit = cellData[19];
      this.$scope.admissionStudents.push(student);
    }

  }

  UMS.controller("AdmissionTaletalkData", AdmissionTaletalkData);
}