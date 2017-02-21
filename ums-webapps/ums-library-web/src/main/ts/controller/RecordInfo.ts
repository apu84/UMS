module ums{
  export interface IMaterialScope extends ng.IScope {
    data: any;
    bulkAssignmentOptionSelected:boolean;
    showBookUI:Function;
    showAccessionUI:Function;
    fillSampleData:Function;
    bookInfo:boolean;
    accessionInfo:boolean;
    colWidthArray:any;
    addNewRow:Function;
    deleteRow:Function;
    roleDropDown:string;
    contributorNameDropDown:string;
    contributors:Array<IContributorDD>;
    newItemList:Array<IItem>;
    addNewItems:Function;
    showHideItemsTable:Function;
    setMaterialTypeName:Function;
    submit:Function;
    record:IRecord;
    saveRecord:Function;

  }
export interface IItem{
    id:number;
    copyNo:string;
}
  export interface IContributorEntry{
    viewOrder:number;
    role:number;
    id:string,
    name:string;
  }
  export interface IContributorDD{
    id:string;
    text:string;
  }
  export interface INoteEntry{
    viewOrder:number;
    note:string;
  }
  export interface ISubjectEntry{
    viewOrder:number;
    subject:string;
  }

  export interface IImprint{
    publisher:number;
    publisherName:string;
    placeOfPublication:string;
    yearDateOfPublication:string;
    copyRightDate:string;
  }
  export interface IPhysicalDescription{
    pagination:string;
    illustrations:string;
    accompanyingMaterials:string;
    dimensions:string;
    paperQuality:string;
  }

  export interface IRecord{
    mfnNo:number;
    language:number;
    languageName:string;
    materialType:number;
    materialTypeName:string;
    status:number;
    statusName:string;
    bindingType:number;
    bindingTypeName:string;
    acqType:number;
    acqTypeName:string;
    title:string;
    subTitle:string;
    gmd:string;
    seriesTitle:string;
    volumeNo:string;
    volumeTitle:string;
    serialIssueNo:string;
    serialNumber:string;
    serialSpecial:string;
    libraryLacks:string;
    changedTitle:string;
    isbn:string;
    issn:string;
    corpAuthorMain:string;
    corpSubBody:string;
    corpCityCountry:string;
    edition:string;
    translateTitleEdition:string;
    frequency:number;
    callNo:string;
    classNo:string;
    callDate:string;
    authorMark:string;
    contributorList:Array<IContributorEntry>; //need separate channel
    imprint:IImprint;
    physicalDescription:IPhysicalDescription;
    subjectList:Array<ISubjectEntry>;
    noteList:Array<INoteEntry>;
    keywords:string;
    contributorJsonString:string;
    noteJsonString:string;
    subjectJsonString:string;
  }
  export class RecordInfo {
    private anchorPrefix = ".btn.btn-xs.btn-default.";
    private columnHeader;
    public static $inject = ['HttpClient', '$scope', '$q', 'notify', 'libConstants'];

    constructor(private httpClient: HttpClient, private $scope: IMaterialScope,
                private $q: ng.IQService, private notify: Notify, private libConstants: any) {



      $scope.bulkAssignmentOptionSelected = false;
      $scope.fillSampleData=this.fillSampleData.bind(this);
      $scope.showBookUI = this.showBookUI.bind(this);
      $scope.showAccessionUI = this.showAccessionUI.bind(this);
      $scope.addNewRow = this.addNewRow.bind(this);
      $scope.deleteRow = this.deleteRow.bind(this);
      $scope.addNewItems = this.addNewItems.bind(this);
      $scope.showHideItemsTable = this.showHideItemsTable.bind(this);
      $scope.setMaterialTypeName = this.setMaterialTypeName.bind(this);
      $scope.submit = this.submit.bind(this);
      $scope.saveRecord = this.saveRecord.bind(this);


      this.$scope.bookInfo = true;
      this.$scope.accessionInfo = false;

      this.columnHeader = [];
      this.$scope.colWidthArray = [100, 180, 150, 150, 250]; // Student Id and Student Name column width
      var that = this;

      $scope.contributors = new Array<IContributorDD>();
      $scope.newItemList = new Array<IItem>();
//asdfasasdf
      //adsasfasdfasf
      var contributor = {id: "", text: ""};
      this.$scope.contributors.push(contributor);
      contributor = {id: "2", text: "Jami"};
      this.$scope.contributors.push(contributor);
      contributor = {id: "3", text: "Hasan"};
      this.$scope.contributors.push(contributor);
      contributor = {id: "4", text: "Hasan Ahmed"};
      this.$scope.contributors.push(contributor);
      contributor = {id: "5", text: "Hasan Kabir"};
      this.$scope.contributors.push(contributor);
      contributor = {id: "6", text: "Abul"};
      this.$scope.contributors.push(contributor);
      contributor = {id: "7", text: "Abul Kalam"};
      this.$scope.contributors.push(contributor);
      contributor = {id: "8", text: "Jamal"};
      this.$scope.contributors.push(contributor);
      contributor = {id: "9", text: "Hasan1"};
      this.$scope.contributors.push(contributor);
      contributor = {id: "10", text: "Hasan2"};
      this.$scope.contributors.push(contributor);
      contributor = {id: "11", text: "Hasan3"};
      this.$scope.contributors.push(contributor);
      contributor = {id: "12", text: "Hasan4"};
      this.$scope.contributors.push(contributor);
      contributor = {id: "13", text: "Hasan5"};
      this.$scope.contributors.push(contributor);
      contributor = {id: "14", text: "Hasan6"};
      this.$scope.contributors.push(contributor);
      contributor = {id: "15", text: "Hasan7"};
      this.$scope.contributors.push(contributor);
      contributor = {id: "16", text: "Hasan8"};
      this.$scope.contributors.push(contributor);
      contributor = {id: "17", text: "Hasan9"};
      this.$scope.contributors.push(contributor);
      contributor = {id: "18", text: "Hasan10"};
      this.$scope.contributors.push(contributor);


      console.log("------");
      console.log(libConstants.recordStatus);
      $scope.data = {
        languageOptions: libConstants.languages,
        bindingTypeOptions: libConstants.bindingTypes,
        acquisitionTypeOptions: libConstants.acquisitionTypes,
        contributorRoleOptions: libConstants.libContributorRoles,
        recordStatusOptions: libConstants.recordStatus,
        itemOptions: libConstants.itemStatus,
        materialTypeOptions: libConstants.materialTypes,
        journalFrequencyOptions: libConstants.journalFrequency,
        showItemMainButtonPanel: true,
        multipleItemAdd: false,
        bulkAddCount: 0,
        collapsedItemTable: false,
        headerTitle: "Add New - Book Record",
        settings: {
          colWidths: this.$scope.colWidthArray,
          colHeaders: true,
          rowHeaders: true,
          width: 1000,
          height: 200,
          currentRowClassName: 'currentRow',
          columnSorting: true,
          currentColClassName: 'currentCol',
          fillHandle: false
        },
        items: [
          {
            "copyNumber": "1",
            "accessionNo": "11131221",
            "accessionDate": "11-11-2016",
            "price": "12321",
            "supplier": "The rainbow",
            "status": "Available"
          },
          {
            "copyNumber": "2",
            "accessionNo": "11131221",
            "accessionDate": "11-11-2016",
            "price": "12321",
            "supplier": "The rainbow",
            "status": "Available"
          },
          {
            "copyNumber": "3",
            "accessionNo": "11131221",
            "accessionDate": "11-11-2016",
            "price": "12321",
            "supplier": "The rainbow",
            "status": "Available"
          },
          {
            "copyNumber": "4",
            "accessionNo": "11131221",
            "accessionDate": "11-11-2016",
            "price": "12321",
            "supplier": "The rainbow",
            "status": "Available"
          },
          {
            "copyNumber": "5",
            "accessionNo": "11131221",
            "accessionDate": "11-11-2016",
            "price": "12321",
            "supplier": "The rainbow",
            "status": "Available"
          },
          {
            "copyNumber": "6",
            "accessionNo": "11131221",
            "accessionDate": "11-11-2016",
            "price": "12321",
            "supplier": "The rainbow",
            "status": "Available"
          }
        ],
        columns: [
          {
            "data": "copyNumber",
            "title": "Copy Number",
            "readOnly": true,
            className: "htCenter"
          },
          {
            "data": "accessionNo",
            "title": "Accession #",
            "readOnly": true
          },
          {
            "data": "accessionDate",
            "title": "Accession Date",
            "readOnly": true,
            className: "htCenter"
          },
          {
            "data": "price",
            "title": "Purchase Price",
            "readOnly": true,
            className: "htRight"
          },
          {
            "data": "supplier",
            "title": "Supplier",
            "readOnly": true
          },
          {
            "data": "status",
            "title": "Status",
            "readOnly": true
          }
        ]
      };
      setTimeout(this.$scope.showBookUI(), 1000);
      $scope.$watch('selectedMaterialId', function (NewValue, OldValue) {
        console.log("bbbb");
      }, true);

      jQuery.validator.addMethod("cRequired", function (value, element) {
        console.log("Status :" + $("#recordStatus").val());
        console.log("Value :" + value);
        console.log($(element).attr('id')  );

       if($(element).attr('id')=="recordStatus" &&  value  == 101101)
         return false;
       else if ($("#recordStatus").val() == 0)
          return true;
        else if ($("#recordStatus").val() == 2 && (value == "" || value  == 101101)) return false;
        else return true;
      }, "This field is required");

        $scope.record = {
        mfnNo: undefined,
        language: 1,
        languageName: "",
        materialType: 1,
        materialTypeName: "Books",
        status: 101101,
        statusName: "",
        bindingType: 101101,
        bindingTypeName: "",
        acqType: 101101,
        acqTypeName: "",
        title: "",
        subTitle: "",
        gmd: "",
        seriesTitle: "",
        volumeNo: "",
        volumeTitle: "",
        serialIssueNo: "",
        serialNumber: "",
        serialSpecial: "",
        libraryLacks: "",
        changedTitle: "",
        isbn: "",
        issn: "",
        corpAuthorMain: "",
        corpSubBody: "",
        corpCityCountry: "",
        edition: "",
        translateTitleEdition: "",
        frequency: null,
        callNo: "",
        classNo: "",
        callDate: "",
        authorMark: "",
        contributorList: new Array <IContributorEntry>(),  //done
        imprint: undefined,
        physicalDescription: undefined,
        subjectList: new Array<ISubjectEntry>(), //done
        keywords:"",
        noteList: new Array <INoteEntry>(),
        contributorJsonString:"[[{'viewOrder':1,'role':1,'contributor':1},{'viewOrder':2,'role':'2','contributor':2}]]",
        noteJsonString:"[{'viewOrder':1,'note':'note1'},{'viewOrder':2,'note':'note2'}]",
        subjectJsonString:"[{'viewOrder':1,'subject':'subject1'},{'viewOrder':2,'subject':'subject2'}]"
      }

      this.addNewRow("");
      this.addNewRow("contributor");
      this.addNewRow("note");
      this.addNewRow("subject");
      this.initializeDatePickers();
    }

    public setMaterialTypeName(id) {
      var that = this;
      var a = this.$scope.data.materialTypeOptions;
      angular.forEach(a, function (attr: any) {
        if (attr.id == id) {
          that.$scope.record.materialTypeName = attr.name;
        }
      });
    }

    private addNewRow(divId: string) {
      if (divId == 'contributor') {
        var contributor: IContributorEntry;
        contributor = {viewOrder: this.$scope.record.contributorList.length + 1, name: "ABC", role: 1, id: ""};
        this.$scope.record.contributorList.push(contributor);
        var index = this.$scope.record.contributorList.length - 1;

        //ToDo: This should be removed and check whether it still works or not
        setTimeout(function () {
          $("#contributorName" + index).rules("add", "required");
          $('#ami' + index).bind('$destroy', function () {
            $(this).select2('destroy');
          });
        }, 1000);
      }
      else if (divId == "note") {
        var note: INoteEntry;
        note = {viewOrder: this.$scope.record.noteList.length + 1, note: ""};
        this.$scope.record.noteList.push(note);
      }
      else if (divId == "subject") {
        var subject: ISubjectEntry;
        subject = {viewOrder: this.$scope.record.subjectList.length + 1, subject: ""};
        this.$scope.record.subjectList.push(subject);
      }
    }


    private deleteRow(event: any, index: number) {

      if (event == "contributor") {
        var that = this;
        // $("#cont"+index).rules("remove", "required");

        this.$scope.record.contributorList.splice(index, 1);
      }
      else if (event == "note") {
        this.$scope.record.noteList.splice(index, 1);
      }
      else if (event == "subject") {
        this.$scope.record.subjectList.splice(index, 1);
      }
    }

    private showBookUI() {
      this.activateUI(1);
      this.resetMainSelections();
      this.$scope.data.headerTitle = "Add New - Book Record";
      this.setSelection("bookAnchor", "bookIcon");

    }


    private showAccessionUI() {
      this.activateUI(2);
      this.resetMainSelections();
      this.$scope.data.headerTitle = "Items for Korean-English Dictionary (Call # 005.133/B174o)";
      this.setSelection("recordAnchor", "recordIcon");
    }

    private setSelection(icon1, icon2) {
      $(this.anchorPrefix + icon1).css({"background-color": "black"});
      $(".fa." + icon2).css({"color": "white"});
    }

    private resetMainSelections() {
      $(this.anchorPrefix + "bookAnchor").css({"background-color": "white"});
      $(this.anchorPrefix + "recordAnchor").css({"background-color": "white"});
      $(".fa.bookIcon").css({"color": "black"});
      $(".fa.recordIcon").css({"color": "black"});
    }

    private addNewItems() {
      this.$scope.data.multipleItemAdd = true;
      this.showHideItemsTable("hide");
      this.$scope.newItemList = new Array<IItem>();
      for (var i = 0; i < this.$scope.data.bulkAddCount; i++) {
        var item: IItem;
        item = {id: 0, copyNo: ""};
        this.$scope.newItemList.push(item);
      }

    }

    private showHideItemsTable(action: string) {
      if (action == "show") {
        this.$scope.data.collapsedItemTable = false;
        $("#itemsDiv").show(400);
      }
      else {
        this.$scope.data.collapsedItemTable = true;
        $("#itemsDiv").hide(400);
      }
    }

    private activateUI(activateNumber: number) {
      this.disableAllUI().then((message: string) => {
        if (activateNumber == 1) {
          this.$scope.bookInfo = true;
          this.$scope.accessionInfo = false;
        } else if (activateNumber == 2) {
          this.$scope.bookInfo = false;
          this.$scope.accessionInfo = true;
        }
      });

    }

    private disableAllUI(): ng.IPromise<any> {
      var defer = this.$q.defer();
      defer.resolve("done");
      return defer.promise;
    }

    private initializeDatePickers() {
      setTimeout(function () {
        $('.datepicker-default').datepicker();
        $('.datepicker-default').on('change', function () {
          $('.datepicker').hide();
        });
      }, 200);
    }


    private submit(): void {

    }

    private saveRecord(): void {

      this.$scope.record.contributorJsonString = JSON.stringify(this.$scope.record.contributorList);
      this.$scope.record.noteJsonString = JSON.stringify(this.$scope.record.noteList);
      this.$scope.record.subjectJsonString = JSON.stringify(this.$scope.record.subjectList);

      var url = "record";
      this.httpClient.post(url,this.$scope.record,'application/json')
          .success(()=>{
            this.notify.success("Data Saved Successfully");
          }).error((data)=>{
            this.notify.error(data);
      });
    }
    private fillSampleData() {

      this.$scope.record.language= 1;
      console.log( this.$scope.record.language);

    }




  }

  UMS.controller("RecordInfo",RecordInfo);
}