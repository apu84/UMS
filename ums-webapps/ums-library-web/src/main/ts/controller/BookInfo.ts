module ums{
  export interface IMaterialScope extends ng.IScope {
    data: any;
    bulkAssignmentOptionSelected:boolean;
    showBookUI:Function;
    showAccessionUI:Function;
    bookInfo:boolean;
    accessionInfo:boolean;
    colWidthArray:any;
    addNewRow:Function;
    deleteRow:Function;
    roleDropDown:string;
    contributorNameDropDown:string;
    contributorList:Array<IContributor>;
    contributors:Array<IContributorDD>;
    noteList:Array<INote>;
    subjectList:Array<ISubject>;

  }

  export interface IContributor{
    viewOrder:number;
    role:number;
    name:string;
  }
  export interface IContributorDD{
    id:string;
    text:string;
  }
  export interface INote{
    viewOrder:number;
    note:string;
  }
  export interface ISubject{
    viewOrder:number;
    subject:string;
  }
  export class BookInfo{
    private anchorPrefix=".btn.btn-xs.btn-default.";
    private columnHeader;
    public static $inject = ['HttpClient','$scope','$q','notify','libConstants'];
    constructor(private httpClient: HttpClient, private $scope: IMaterialScope,
                private $q:ng.IQService, private notify: Notify,private libConstants: any) {



      $scope.bulkAssignmentOptionSelected=false;
      $scope.showBookUI=this.showBookUI.bind(this);
      $scope.showAccessionUI=this.showAccessionUI.bind(this);
      $scope.addNewRow=this.addNewRow.bind(this);
      $scope.deleteRow=this.deleteRow.bind(this);

      this.$scope.bookInfo=true;
      this.$scope.accessionInfo=false;
      setTimeout(this.$scope.showBookUI(),500);
      this.columnHeader=[];
      this.$scope.colWidthArray=[100,180, 150,150,250]; // Student Id and Student Name column width
      var that=this;


        $scope.contributorList = new Array<IContributor>();
        $scope.contributors=new Array<IContributorDD>();
        $scope.noteList= new Array<INote>();
      $scope.subjectList= new Array<ISubject>();




      var contributor={id:"1",text:"Ifti"};
      this.$scope.contributors.push(contributor);
      contributor={id:"2",text:"Jami"};
      this.$scope.contributors.push(contributor);
      contributor={id:"3",text:"Hasan"};
      this.$scope.contributors.push(contributor);
      contributor={id:"4",text:"Hasan Ahmed"};
      this.$scope.contributors.push(contributor);
      contributor={id:"5",text:"Hasan Kabir"};
      this.$scope.contributors.push(contributor);
      contributor={id:"6",text:"Abul"};
      this.$scope.contributors.push(contributor);
      contributor={id:"7",text:"Abul Kalam"};
      this.$scope.contributors.push(contributor);
      contributor={id:"8",text:"Jamal"};
      this.$scope.contributors.push(contributor);
      contributor={id:"9",text:"Hasan1"};
      this.$scope.contributors.push(contributor);
      contributor={id:"10",text:"Hasan2"};
      this.$scope.contributors.push(contributor);
      contributor={id:"11",text:"Hasan3"};
      this.$scope.contributors.push(contributor);
      contributor={id:"12",text:"Hasan4"};
      this.$scope.contributors.push(contributor);
      contributor={id:"13",text:"Hasan5"};
      this.$scope.contributors.push(contributor);
      contributor={id:"14",text:"Hasan6"};
      this.$scope.contributors.push(contributor);
      contributor={id:"15",text:"Hasan7"};
      this.$scope.contributors.push(contributor);
      contributor={id:"16",text:"Hasan8"};
      this.$scope.contributors.push(contributor);
      contributor={id:"17",text:"Hasan9"};
      this.$scope.contributors.push(contributor);
      contributor={id:"18",text:"Hasan10"};
      this.$scope.contributors.push(contributor);



      this.addNewRow("");
      this.addNewRow("contributor");
      this.addNewRow("note");
      this.addNewRow("subject");


      $scope.data = {
        languageOptions: libConstants.languages,
        bindingTypeOptions:libConstants.bindingTypes,
        acquisitionTypeOptions:libConstants.acquisitionTypes,
        contributorRoleOptions:libConstants.libContributorRoles,
        headerTitle:"Add New - Book Record",
        settings:{
          colWidths: this.$scope.colWidthArray,
          colHeaders: true,
          rowHeaders: true,
          width: 880,
          height:200,
          currentRowClassName: 'currentRow',
          columnSorting: true,
          currentColClassName: 'currentCol',
          fillHandle: false

        },
        items:[
          {"copyNumber":"1", "accessionNo":"11131221", "accessionDate":"11-11-2016", "price":"12321", "supplier":"The rainbow"},
          {"copyNumber":"2", "accessionNo":"11131221", "accessionDate":"11-11-2016", "price":"12321", "supplier":"The rainbow"},
          {"copyNumber":"3", "accessionNo":"11131221", "accessionDate":"11-11-2016", "price":"12321", "supplier":"The rainbow"},
          {"copyNumber":"4", "accessionNo":"11131221", "accessionDate":"11-11-2016", "price":"12321", "supplier":"The rainbow"},
          {"copyNumber":"5", "accessionNo":"11131221", "accessionDate":"11-11-2016", "price":"12321", "supplier":"The rainbow"},
          {"copyNumber":"6", "accessionNo":"11131221", "accessionDate":"11-11-2016", "price":"12321", "supplier":"The rainbow"}
        ],
        columns:[
          {
            "data":"copyNumber",
            "title":"Copy Number",
            "readOnly":true,
            className: "htCenter"
          },
          {
            "data":"accessionNo",
            "title":"Accession #",
            "readOnly":true
          },
          {
            "data":"accessionDate",
            "title":"Accession Date",
            "readOnly":true,
            className: "htCenter"
          },
          {
            "data":"price",
            "title":"Purchase Price",
            "readOnly":true,
            className: "htRight"
          },
          {
            "data":"supplier",
            "title":"Supplier",
            "readOnly":true
          }
        ]
      };
    }

    private addNewRow(divId:string){

      if(divId=='contributor'){
        var contributor:IContributor;
        contributor={viewOrder:this.$scope.contributorList.length+1 , name:"ABC",role:1};
        this.$scope.contributorList.push(contributor);
      }
      else if(divId=="note"){
        var note:INote;
        note={viewOrder:this.$scope.noteList.length+1 , note:""};
        this.$scope.noteList.push(note);
      }
      else if(divId=="subject"){
        var subject:ISubject;
        subject={viewOrder:this.$scope.subjectList.length+1 , subject:""};
        this.$scope.subjectList.push(subject);
      }
    }



    private deleteRow(event:any,index:number){

      if(event=="contributor") {
        this.$scope.contributorList.splice(index, 1);
      }
      else if(event=="note") {
        this.$scope.noteList.splice(index, 1);
      }
      else if(event=="subject") {
        this.$scope.subjectList.splice(index, 1);
      }
    }
    private showBookUI(){
      this.activateUI(1);
      this.resetMainSelections();
      this.$scope.data.headerTitle="Add New - Book Record";
      this.setSelection("bookAnchor","bookIcon");
    }
    private showAccessionUI(){
      this.activateUI(2);
      this.resetMainSelections();
      this.$scope.data.headerTitle="Items for Korean-English Dictionary (Call # 005.133/B174o)";
      this.setSelection("recordAnchor","recordIcon");
    }

    private setSelection(icon1,icon2){
      $(this.anchorPrefix+icon1).css({"background-color":"black"});
      $(".fa."+icon2).css({"color":"white"});
    }
    private resetMainSelections(){
      $(this.anchorPrefix+"bookAnchor").css({"background-color":"white"});
      $(this.anchorPrefix+"recordAnchor").css({"background-color":"white"});
      $(".fa.bookIcon").css({"color":"black"});
      $(".fa.recordIcon").css({"color":"black"});
    }



    private activateUI(activateNumber:number){
      this.disableAllUI().then((message:string)=>{
        if(activateNumber==1){
          this.$scope.bookInfo=true;
          this.$scope.accessionInfo=false;
        }else if(activateNumber==2){
          this.$scope.bookInfo=false;
          this.$scope.accessionInfo=true;
        }
      });

    }
    private disableAllUI():ng.IPromise<any>{
      var defer= this.$q.defer();
      defer.resolve("done");
      return defer.promise;
    }
  }
  UMS.controller("BookInfo",BookInfo);
}