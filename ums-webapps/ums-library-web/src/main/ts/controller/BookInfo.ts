module ums{
  export class BookInfo{
    private anchorPrefix=".btn.btn-xs.btn-default.";
    private columnHeader;
    public static $inject = ['HttpClient','$scope','$q','notify'];
    constructor(private httpClient: HttpClient, private $scope: any,
                private $q:ng.IQService, private notify: Notify) {


      $scope.bulkAssignmentOptionSelected=false;
      $scope.showBookUI=this.showBookUI.bind(this);
      $scope.showAccessionUI=this.showAccessionUI.bind(this);

      this.$scope.bookInfo=true;
      this.$scope.accessionInfo=false;
      setTimeout(this.$scope.showBookUI(),500);
 console.log(this.$scope);
      this.columnHeader=[];
      this.$scope.colWidthArray=[100,180, 150,150,250]; // Student Id and Student Name column width
      var that=this;
      $scope.data = {

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