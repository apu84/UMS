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
    columns:Array<any>;

    showUploadPortion:boolean;
    disableSaveButton:boolean;
    showSpinner:boolean;
    searchSpinner:boolean;

    getSemesters:Function;
    fetchTaletalkData:Function;
    fetchExcelFormat:Function;
    processData:Function;
    saveData:Function;
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
      $scope.disableSaveButton=true;
      $scope.showSpinner=false;
      $scope.searchSpinner=false;



      //this.configureHandsOnTable();

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
          width:$(".page-content").width()-5,
          height:$(".page-content").height()-5,
          observeChanges:true,
          search:true,
          columns:[{"title":"Receipt Id","data":"receiptId"},
            {"title":"Pin","data":"pin"},
            {"title":"HSC Board","data":"hscBoard"},
            {"title":"HSC Roll","data":"hscRoll"},
            {"title":"HSC RegNo","data":"hscRegNo"},
            {"title":"HSC Year","data":"hscYear"},
            {"title":"HSC Group","data":"hscGroup"},
            {"title":"SSC Board","data":"sscBoard"},
            {"title":"SSC Roll","data":"sscRoll"},
            {"title":"SSC Year","data":"sscYear"},
            {"title":"SSC Group","data":"sscGroup"},
            {"title":"Gender","data":"gender"},
            {"title":"Student Name","data":"studentName"},
            {"title":"Father Name","data":"fatherName"},
            {"title":"Mother Name","data":"motherName"},
            {"title":"SSC GPA","data":"sscGpa"},
            {"title":"HSC GPA","data":"hscGpa"},
            {"title":"Quota","data":"quota"},
            {"title":"Unit","data":"unit"}]

    }

      };



      $scope.programTypes = appConstants.programType;
      $scope.programType = $scope.programTypes[0];
      $scope.getSemesters = this.getSemesters.bind(this);
      $scope.fetchTaletalkData = this.fetchTaletalkData.bind(this);
      $scope.fetchExcelFormat = this.fetchExcelFormat.bind(this);
      $scope.processData = this.processData.bind(this);
      $scope.saveData = this.saveData.bind(this);

      this.getSemesters();
      Utils.setValidationOptions("form-horizontal");
      

    }





    private getSemesters(){


      this.semesterService.fetchSemesters(+this.$scope.programType.id).then((semesters:Array<Semester>)=>{
        this.$scope.semesters=[];
        for(var i=0;i<semesters.length;i++){
          if(semesters[i].status==2){
            this.$scope.semester = semesters[i];
          }
          this.$scope.semesters.push(semesters[i]);
        }
      });
    }

    private fetchTaletalkData(){
      Utils.expandRightDiv();

      this.$scope.searchSpinner=true;
      this.$scope.admissionStudents=[];
      this.admissionStudentService.fetchTaletalkData(this.$scope.semester.id).then((admissionStudents:Array<AdmissionStudent>)=>{
        console.log("upload portion");
        console.log(admissionStudents);
        if(admissionStudents.length==0){
          this.$scope.showUploadPortion=true;
          this.$scope.searchSpinner=false;
          this.$scope.disableSaveButton=false;
        }else{

          this.$scope.searchSpinner=false;
          this.$scope.admissionStudents=admissionStudents;

          this.$scope.showUploadPortion=false;
          this.$scope.disableSaveButton=true;
        }


      });
    }

    private hideTheTablePortion():ng.IPromise<any>{

      var defer = this.$q.defer();
      this.$scope.showSpinner=false;

      defer.resolve("hidden");
      return defer.promise;
    }

    private fetchExcelFormat(){
      this.admissionStudentService.downloadExcelFile(this.$scope.semester.id);
    }





    private processData(modalData:any):void{
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
      student.semesterId=this.$scope.semester.id;
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
      student.dateOfBirth=this.formateDate(cellData[12]);;
      student.studentName=cellData[13];
      student.fatherName=cellData[14];
      student.motherName=cellData[15];
      student.sscGpa=Number(cellData[16]).toFixed(2);
      student.hscGpa = Number(cellData[17]).toFixed(2);
      student.quota=cellData[18];
      student.unit = cellData[19];
      this.$scope.admissionStudents.push(student);
    }


    private formateDate(date:string):string{
      let formatedBirthDate:string="";
      if(date  .indexOf('/')>-1){
        formatedBirthDate=date  ;
      }
      else if(date  .length==8){
        formatedBirthDate = [date  .slice(0,2),'/',date  .slice(2,4),'/',date  .slice(6,8)].join('');
      }
      else if(date  .length==6){
        formatedBirthDate = [date  .slice(0,2),'/',date  .slice(2,4),'/',date  .slice(4,6)].join('');
      }else{
        formatedBirthDate = ['0',date  .slice(0,1),'/',date  .slice(1,3),'/',date  .slice(3,5),''].join('');
      }

      return formatedBirthDate;
    }

    private saveData(){

      this.$scope.showSpinner=true;

      this.convertToJson().then((completeJson:any)=>{
        this.admissionStudentService.saveTaletalkData(completeJson, this.$scope.semester.id).then((message:string)=>{
          if(message == "success"){
            this.notify.success("Data successfully saved");
            this.$scope.disableSaveButton=true;
            this.$scope.showSpinner=false;
          }else{
            this.notify.error("Error in saving data");
            this.$scope.showSpinner=false;
          }

        });
      });


    }

    private convertToJson():ng.IPromise<any>{
      var defer = this.$q.defer();
      var completeJson={};
      var jsonObject=[];
      var students:Array<AdmissionStudent>=this.$scope.admissionStudents;
      for(var i=0;i<this.$scope.admissionStudents.length;i++){
        var item:any={};
        item['semesterId']=this.$scope.semester.id;
        item['receiptId'] = students[i].receiptId;
        item['pin'] = students[i].pin;
        item['hscBoard'] = students[i].hscBoard;
        item['hscRoll'] = students[i].hscRoll;
        item['hscRegNo']=students[i].hscRegNo;
        item['hscYear'] = students[i].hscYear;
        item['hscGroup'] = students[i].hscGroup;
        item['sscBoard'] = students[i].sscBoard;
        item['sscRoll'] = students[i].sscRoll;
        item['sscYear'] = students[i].sscYear;
        item['sscGroup'] = students[i].sscGroup;
        item['gender'] = students[i].gender;
        item['dateOfBirth'] = students[i].dateOfBirth;
        item['studentName'] = students[i].studentName;
        item['fatherName'] = students[i].fatherName;
        item['motherName'] = students[i].motherName;
        item['sscGpa'] = students[i].sscGpa;
        item['hscGpa'] = students[i].hscGpa;
        item['quota'] = students[i].quota;
        item['unit'] = students[i].unit;
        item['admissionRoll'] = 'null';
        item['meritSlNo'] = 'null';
        item['programId'] = 'null';
        item['migrationStatus'] = 'null';
        jsonObject.push(item);
      }
      completeJson["entries"]=jsonObject;
      defer.resolve(completeJson);
      return defer.promise

    }

  }

  UMS.controller("AdmissionTaletalkData", AdmissionTaletalkData);
}