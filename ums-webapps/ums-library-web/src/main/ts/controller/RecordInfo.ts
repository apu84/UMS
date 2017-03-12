module ums {
  export interface IMaterialScope extends ng.IScope {
    data: any;
    bulkAssignmentOptionSelected: boolean;
    showBookUI: Function;
    showAccessionUI: Function;
    fillSampleData: Function;
    bookInfo: boolean;
    accessionInfo: boolean;
    colWidthArray: any;
    addNewRow: Function;
    deleteRow: Function;
    roleDropDown: string;
    contributorNameDropDown: string;
    contributors: Array<IContributorDD>;
    bulkItemList: Array<IItem>;
    addNewItems: Function;
    showHideItemsTable: Function;
    setMaterialTypeName: Function;
    record: IRecord;
    item: IItem;
    saveRecord: Function;
    saveItem: Function;
    saveBulkItems:Function;
    bulk: any;
    setBulkItemsValue: Function;
    itemList : Array<IItem>;
  }
  export interface IContributorEntry {
    viewOrder: number;
    role: number;
    id: string,
    name: string;
  }
  export interface IContributorDD {
    id: string;
    text: string;
  }
  export interface INoteEntry {
    viewOrder: number;
    note: string;
  }
  export interface ISubjectEntry {
    viewOrder: number;
    subject: string;
  }

  export interface IImprint {
    publisher: number;
    publisherName: string;
    placeOfPublication: string;
    yearDateOfPublication: string;
    copyRightDate: string;
  }
  export interface IPhysicalDescription {
    pagination: string;
    illustrations: string;
    accompanyingMaterials: string;
    dimensions: string;
    paperQuality: string;
  }

  export interface IRecord {
    mfnNo: string;
    language: number;
    languageName: string;
    materialType: number;
    materialTypeName: string;
    status: number;
    statusName: string;
    bindingType: number;
    bindingTypeName: string;
    acqType: number;
    acqTypeName: string;
    title: string;
    subTitle: string;
    gmd: string;
    seriesTitle: string;
    volumeNo: string;
    volumeTitle: string;
    serialIssueNo: string;
    serialNumber: string;
    serialSpecial: string;
    libraryLacks: string;
    changedTitle: string;
    isbn: string;
    issn: string;
    corpAuthorMain: string;
    corpSubBody: string;
    corpCityCountry: string;
    edition: string;
    translateTitleEdition: string;
    frequency: number;
    callNo: string;
    classNo: string;
    callDate: string;
    authorMark: string;
    contributorList: Array<IContributorEntry>; //need separate channel
    imprint: IImprint;
    physicalDescription: IPhysicalDescription;
    subjectList: Array<ISubjectEntry>;
    noteList: Array<INoteEntry>;
    keywords: string;
    contributorJsonString: string;
    noteJsonString: string;
    subjectJsonString: string;
  }

  export interface IItem {
    mfnNo: string;
    itemId: string;
    copyNumber: number;
    accessionNumber: string;
    accessionDate: string;
    barcode: string;
    price: number;
    internalNote: string;
    supplier: ISupplier;
    status: number;
    statusName: string;
  }
  export interface ISupplier {
    id: number;
    name: string;
    address: string,
    contactPerson: string;
    contactNumber: string;
  }


  export class RecordInfo {
    private anchorPrefix = ".btn.btn-xs.btn-default.";
    private columnHeader;
    public static $inject = ['HttpClient', '$scope', '$q', 'notify', 'libConstants'];

    constructor(private httpClient: HttpClient, private $scope: IMaterialScope,
                private $q: ng.IQService, private notify: Notify, private libConstants: any) {


      $scope.bulkAssignmentOptionSelected = false;
      $scope.fillSampleData = this.fillSampleData.bind(this);
      $scope.showBookUI = this.showBookUI.bind(this);
      $scope.showAccessionUI = this.showAccessionUI.bind(this);
      $scope.addNewRow = this.addNewRow.bind(this);
      $scope.deleteRow = this.deleteRow.bind(this);
      $scope.addNewItems = this.addNewItems.bind(this);
      $scope.showHideItemsTable = this.showHideItemsTable.bind(this);
      $scope.setMaterialTypeName = this.setMaterialTypeName.bind(this);
      $scope.saveRecord = this.saveRecord.bind(this);
      $scope.saveItem = this.saveItem.bind(this);
      $scope.saveBulkItems = this.saveBulkItems.bind(this);
      $scope.setBulkItemsValue = this.setBulkItemsValue.bind(this);

      this.$scope.bookInfo = true;
      this.$scope.accessionInfo = false;

      this.columnHeader = [];
      this.$scope.colWidthArray = [100, 180, 150, 150, 250]; // Student Id and Student Name column width
      var that = this;

      $scope.contributors = new Array<IContributorDD>();
      $scope.bulkItemList = new Array<IItem>();

      var contributor = {id: "", text: ""};
      this.$scope.contributors.push(contributor);
      contributor = {id: "2", text: "Jami1111"};
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


      $scope.data = {
        languageOptions: libConstants.languages,
        bindingTypeOptions: libConstants.bindingTypes,
        acquisitionTypeOptions: libConstants.acquisitionTypes,
        contributorRoleOptions: libConstants.libContributorRoles,
        recordStatusOptions: libConstants.recordStatus,
        itemOptions: libConstants.itemStatus,
        materialTypeOptions: libConstants.materialTypes,
        journalFrequencyOptions: libConstants.journalFrequency,
        readOnlyMode: false,
        showItemMainButtonPanel: true,
        multipleItemAdd: false,
        bulkAddCount: 4,
        collapsedItemTable: false,
        headerTitle: "Add New - Book Record"
      };
      setTimeout(this.$scope.showBookUI(), 1000);
      $scope.$watch('selectedMaterialId', function (NewValue, OldValue) {
        console.log("bbbb");
      }, true);

      jQuery.validator.addMethod("cRequired", function (value, element) {

        if ($(element).attr('id') == "recordStatus" && value == 101101)
          return false;
        else if ($("#recordStatus").val() == 0)
          return true;
        else if ($("#recordStatus").val() == 2 && (value == "" || value == 101101)) return false;
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
        imprint: {
          "publisher": 0,
          "publisherName": "",
          "placeOfPublication": "",
          "yearDateOfPublication": "",
          "copyRightDate": ""
        },
        physicalDescription: {
          "pagination": "",
          "illustrations": "",
          "accompanyingMaterials": "",
          "dimensions": "",
          "paperQuality": ""
        },
        subjectList: new Array<ISubjectEntry>(), //done
        keywords: "",
        noteList: new Array <INoteEntry>(),
        contributorJsonString: "[[{'viewOrder':1,'role':1,'contributor':1},{'viewOrder':2,'role':'2','contributor':2}]]",
        noteJsonString: "[{'viewOrder':1,'note':'note1'},{'viewOrder':2,'note':'note2'}]",
        subjectJsonString: "[{'viewOrder':1,'subject':'subject1'},{'viewOrder':2,'subject':'subject2'}]"
      }


      $scope.item = {
        mfnNo: "123",
        itemId: undefined,
        copyNumber: 3,
        accessionNumber: "1234",
        accessionDate: "",
        barcode: "1",
        price: 1,
        internalNote: "",
        supplier: undefined,
        status: 101101,
        statusName: ""
      }

      $scope.bulk = {
        config: {}
      }

      this.addNewRow("");
      this.addNewRow("contributor");
      this.addNewRow("note");
      this.addNewRow("subject");
      this.initializeDatePickers();

      this.fetchItems();
    }

    private fetchItems() {
      this.httpClient.get('item/mfn/1', 'application/json',

          (json:any, etag:string) => {
            console.log(json.entries);
            this.$scope.itemList = json.entries;
          },
          (response:ng.IHttpPromiseCallbackArg<any>) => {
            console.error(response);
          });
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
      this.$scope.bulkItemList = new Array<IItem>();
      for (var i = 0; i < this.$scope.data.bulkAddCount; i++) {
        var item: IItem;

        item = {
          mfnNo: "1",
          itemId: "1",
          copyNumber: 1,
          accessionNumber: "",
          accessionDate: "",
          barcode: "",
          price: 1,
          internalNote: "",
          supplier: {
            "id": 1,
            "name": "",
            "address": "",
            "contactPerson": "",
            "contactNumber": ""
          },
          status: 1,
          statusName: ""
        };


        this.$scope.bulkItemList.push(item);
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

    private saveRecord(): void {

      var action = $("button[type=button][clicked=true]").val();

      console.log("this.$scope.record.mfnNo : " + this.$scope.record.mfnNo);
      var url = "record";
      var that = this;

      this.$scope.record.contributorJsonString = JSON.stringify(this.$scope.record.contributorList);
      this.$scope.record.noteJsonString = JSON.stringify(this.$scope.record.noteList);
      this.$scope.record.subjectJsonString = JSON.stringify(this.$scope.record.subjectList);


      if (this.$scope.record.mfnNo != undefined) {
        console.log("This time i need a put request");
        this.httpClient.put(url + '/' + this.$scope.record.mfnNo, this.$scope.record, 'application/json')
            .success(() => {
              that.notify.success("Data Saved Updated");
              // this.$scope.item.mfnNo= this.$scope.record.mfnNo;
            }).error((data) => {
        });
      }
      else {
        this.httpClient.post(url, this.$scope.record, 'application/json').then(function successCallback(response) {
          that.notify.success("Data Saved Successfully");
          var Id = that.getIdFromUrl(response.headers('Location'));
          that.$scope.record.mfnNo = Id;
          that.$scope.item.mfnNo = Id;
        }, function errorCallback(response) {
          console.error(response);
        });
      }

      console.log("action :" + action);

      if (action == "save_edit_items") {
        this.showAccessionUI();

      }
      else if (action == "save_view") {
        this.$scope.data.readOnlyMode = true;
      }
    }

    private saveItem(): void {
      console.log("Inside saveItem()");
      var url = "item";
      var that = this;

      if (this.$scope.item.itemId != undefined) {
        console.log("This time i need a put request");
        this.httpClient.put(url + '/' + this.$scope.item.itemId, this.$scope.item, 'application/json')
            .success(() => {
              that.notify.success("Data Saved Updated");

            }).error((data) => {
        });
      }
      else {
        console.log(this.$scope.item);
        this.httpClient.post(url, this.$scope.item, 'application/json').then(function successCallback(response) {
          that.notify.success("Data Saved Successfully");
          var Id = that.getIdFromUrl(response.headers('Location'));
          alert(Id);

          that.$scope.item.itemId = Id;
        }, function errorCallback(response) {
          console.error(response);
        });
      }
    }

    private saveBulkItems():void{
      console.log("Inside saveBulkItems");
      var complete_json = {};
      complete_json["items"] = this.$scope.bulkItemList;
      var url = "item/batch";
      var that = this;
      console.log(this.$scope.item);
      this.httpClient.post(url, complete_json, 'application/json').then(function successCallback(response) {
        that.notify.success("Data Saved Successfully");
      }, function errorCallback(response) {
        console.error(response);

      });
    }

    private setBulkItemsValue(): void {
      console.log(this.$scope.bulk.config);
      var bulkItemList = this.$scope.bulkItemList;
      var copyStartFrom: number = 0;
      var incrementSegment;
      var firstAccession;
      for (var i: number = 0; i < bulkItemList.length; i++) {
        var item: IItem = bulkItemList[i];

        if (this.$scope.bulk.config.copyStartFrom != "") {
          if (i == 0) {
            copyStartFrom = this.$scope.bulk.config.copyStartFrom;
          }
          item.copyNumber = Number(copyStartFrom) + i;
        }

        if (this.$scope.bulk.config.internalNote != "") {
          item.internalNote = this.$scope.bulk.config.internalNote;
        }

        item.status = this.$scope.bulk.config.status;

        item.price = Number(this.$scope.bulk.config.price);
        item.accessionDate = this.$scope.bulk.config.accessionDate;
        item.barcode = this.$scope.bulk.config.barcode;

        if(this.$scope.bulk.config.firstAccession != "" &&  this.$scope.bulk.config.incrementSegment != "") {
          firstAccession = this.$scope.bulk.config.firstAccession;
           incrementSegment = this.$scope.bulk.config.incrementSegment;
        }

        var is = Number(incrementSegment)+i;
        item.accessionNumber = firstAccession.replace(incrementSegment,is);


      }


      var data = $("#configSupplierName").select2("data");
      var searchTerm = data.text;

      $('#bulkItemContainer').find('.select2-input').each(function (index) {

        var inputElement: any = $(this)[0];
        var inputElementId = inputElement.id;

        $("#bulkContributorName" + index).select2("search", searchTerm);
        var e = jQuery.Event("keydown");
        e.which = 13;
        $("#" + inputElementId).trigger(e);

      });

    }

    private fillSampleData() {
      this.$scope.data.readOnlyMode = false;
      this.$scope.record.mfnNo = undefined;
      this.$scope.record.language = 1;
      this.$scope.record.status = 0;
      this.$scope.record.bindingType = 1;
      this.$scope.record.acqType = 1;
      this.$scope.record.language = 1;
      var offSet = (new Date).getMilliseconds();
      this.$scope.record.title = "Material Title " + offSet;
      this.$scope.record.subTitle = "Sub Title " + offSet;

      if ($("#gmd"))
        this.$scope.record.gmd = "General Material Description " + offSet;
      if ($("#seriesTitle"))
        this.$scope.record.seriesTitle = "Series " + offSet;
      if ($("#volumeNo"))
        this.$scope.record.volumeNo = "Volume No " + offSet;
      if ($("#volumeTitle"))
        this.$scope.record.volumeTitle = "Volume Title " + offSet;
      if ($("#serialIssueNo"))
        this.$scope.record.serialIssueNo = "Serial Issue No " + offSet;
      if ($("#serialNumber"))
        this.$scope.record.serialNumber = "Serial No " + offSet;
      if ($("#serialSpecial"))
        this.$scope.record.serialSpecial = "Serial Special " + offSet;
      if ($("#libraryLacks"))
        this.$scope.record.libraryLacks = "Library Lacks " + offSet;
      if ($("#changedTitle"))
        this.$scope.record.changedTitle = "Changed Title " + offSet;
      if ($("#isbn"))
        this.$scope.record.isbn = "ISBN " + offSet;
      if ($("#corpAuthorMain"))
        this.$scope.record.corpAuthorMain = "Corporate Author Main " + offSet;
      if ($("#corpSubBody"))
        this.$scope.record.corpSubBody = "Corporate Sub Body " + offSet;
      if ($("#corpCityCountry"))
        this.$scope.record.corpCityCountry = "City, Country " + offSet;
      if ($("#edition"))
        this.$scope.record.edition = "Edition " + offSet;
      if ($("#corpSubBody"))
        this.$scope.record.translateTitleEdition = "Translate Title Edition " + offSet;
      if ($("#issn"))
        this.$scope.record.issn = "ISSN " + offSet;
      if ($("#callNo"))
        this.$scope.record.callNo = "Call No " + offSet;
      if ($("#classNo"))
        this.$scope.record.classNo = "Class No " + offSet;
      if ($("#callDate"))
        this.$scope.record.callDate = "11-11-2017";
      if ($("#authorMark"))
        this.$scope.record.authorMark = "Author Mark " + offSet;

      this.$scope.record.imprint.placeOfPublication = "Publication Place " + offSet;
      this.$scope.record.imprint.yearDateOfPublication = "Year of Publication " + offSet;
      this.$scope.record.imprint.copyRightDate = "09-09-2016";

      this.$scope.record.physicalDescription.pagination = "Pagination " + offSet;
      this.$scope.record.physicalDescription.illustrations = "Illustrations " + offSet;
      this.$scope.record.physicalDescription.accompanyingMaterials = "Materials " + offSet;
      this.$scope.record.physicalDescription.dimensions = "Dimensions " + offSet;
      this.$scope.record.physicalDescription.paperQuality = "Paper Quality " + offSet;

    }

    private getIdFromUrl(url: string): string {
      var resourceUrl = url;
      var startIndex = url.lastIndexOf('/') + 1;
      var lastIndex = resourceUrl.length;
      return url.substring(startIndex, lastIndex);
    }

  }

  UMS.controller("RecordInfo", RecordInfo);
}