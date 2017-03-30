module ums {
  export interface ICatalogingScope extends ng.IScope {
    data: any;
    fillSampleData: Function;
    showRecordInfo: boolean;
    showItemInfo: boolean;
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
    supplier: ISupplier;
    saveRecord: Function;
    saveItem: Function;
    saveBulkItems: Function;
    bulk: any;
    setBulkItemsValue: Function;
    itemList: Array<IItem>;
    supplierService: any;
    notifyService: any;
    callbackList : Array<Function>;
  }


  export class Cataloging {
    public static $inject = ['$scope', '$q', 'notify', 'libConstants','supplierService','catalogingService'];
    constructor(private $scope: ICatalogingScope,
                private $q: ng.IQService, private notify: Notify, private libConstants: any,
                private supplierService : SupplierService, private catalogingService : CatalogingService) {

      $scope.addNewRow = this.addNewRow.bind(this);
      $scope.deleteRow = this.deleteRow.bind(this);
      $scope.addNewItems = this.addNewItems.bind(this);
      $scope.showHideItemsTable = this.showHideItemsTable.bind(this);
      $scope.setMaterialTypeName = this.setMaterialTypeName.bind(this);
      $scope.saveRecord = this.saveRecord.bind(this);
      $scope.saveItem = this.saveItem.bind(this);
      $scope.saveBulkItems = this.saveBulkItems.bind(this);
      $scope.setBulkItemsValue = this.setBulkItemsValue.bind(this);
      $scope.fillSampleData = this.fillSampleData.bind(this);
      $scope.supplierService = supplierService;
      $scope.notifyService = notify;
      this.$scope.showRecordInfo = true;
      this.$scope.showItemInfo = false;

      $scope.contributors = Array<IContributorDD>();
      $scope.bulkItemList = Array<IItem>();

      let contributor = {id: "", text: ""};
      this.$scope.contributors.push(contributor);
      contributor = {id: "2", text: "Jami1111"};
      this.$scope.contributors.push(contributor);
      contributor = {id: "3", text: "Hasan"};

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
        supplierReadOnlyMode:false,
        showItemMainButtonPanel: true,
        multipleItemAdd: false,
        bulkAddCount: 4,
        collapsedItemTable: false,
        headerTitle: "Add New - Book Record"
      };
      // setTimeout(this.$scope.showRecordUI(), 1000);
      // $scope.$watch('selectedMaterialId', function (NewValue, OldValue) {
      //   console.log("bbbb");
      // }, true);

      jQuery.validator.addMethod("cRequired", function (value, element) {

        if ($(element).attr('id') == "recordStatus" && value == 101101)
          return false;
        else if ($("#recordStatus").val() == 0)
          return true;
        else if ($("#recordStatus").val() == 2 && (value == "" || value == 101101))
          return false;
        else return true;
      }, "This field is required");

      $scope.record = <IRecord>{};

      $scope.item = <IItem> {};
      $scope.supplier = <ISupplier> {};
      $scope.bulk = {
        config: {}
      };


      $scope.record.contributorList = Array <IContributorEntry>();
      $scope.record.subjectList = Array<ISubjectEntry>();
      $scope.record.noteList = Array <INoteEntry>();
      $scope.callbackList = Array<Function>();

      this.initNavButtonCallbacks();

      this.addNewRow("");
      this.addNewRow("contributor");
      this.addNewRow("note");
      this.addNewRow("subject");
      this.initializeDatePickers();
      catalogingService.fetchItems("1");

    }

    /**
     * Settings of navigation buttons callback. We will send this to NavigationButton directive .
     */
    private initNavButtonCallbacks() {
      let recordCallback= function() {
        $("#headerTitle").html("Add Record");
      };
      let itemCallback =  function() {
        $("#headerTitle").html("Add Items");
      };

      this.$scope.callbackList.push(recordCallback);
      this.$scope.callbackList.push(itemCallback);

      //Items for Korean-English Dictionary (Call # 005.133/B174o
    }

    /**
     * Set Material name when user select material type from the materil type
     * drop down.
     */
    public setMaterialTypeName(id) {
      angular.forEach(this.$scope.data.materialTypeOptions, (attr: any)  => {
        if (attr.id == id) {
          this.$scope.record.materialTypeName = attr.name;
        }
      });
    }

    /**
     * Add new row for the following dynamic tables
     * Contributor, Note and Subject
     */
    private addNewRow(tableType: string) {
      let size = 1;
      if (tableType == 'contributor') {
        if(this.$scope.record.contributorList != undefined)
          size = this.$scope.record.contributorList.length;

        let contributor: IContributorEntry;
        contributor = {viewOrder: size, name: "", role: 1, id: ""};
        this.$scope.record.contributorList.push(contributor);
        let index = size - 1;
        //ToDo: This should be removed and check whether it still works or not
        /*setTimeout(function () {
          $("#contributorName" + index).rules("add", "required");
          $('#ami' + index).bind('$destroy', function () {
            $(this).select2('destroy');
          });
        }, 1000);*/
      }
      else if (tableType == "note") {
        if(this.$scope.record.noteList != undefined)
          size = this.$scope.record.noteList.length;
        let note: INoteEntry;
        note = {viewOrder:size, note: ""};
        this.$scope.record.noteList.push(note);
      }
      else if (tableType == "subject") {
        let subject: ISubjectEntry;
        if(this.$scope.record.subjectList != undefined)
          size = this.$scope.record.subjectList.length;
        subject = {viewOrder: size, subject: ""};
        this.$scope.record.subjectList.push(subject);
      }
    }


    /**
     * Delete dynamic table rows
     * Using Add More- Delete feature
     */
    private deleteRow(event: any, index: number) {
      if (event == "contributor") {
        this.$scope.record.contributorList.splice(index, 1);
      }
      else if (event == "note") {
        this.$scope.record.noteList.splice(index, 1);
      }
      else if (event == "subject") {
        this.$scope.record.subjectList.splice(index, 1);
      }
    }

    /**
     * Add Multiple Items by a Single Click
     */
    private addNewItems() {
      this.$scope.data.multipleItemAdd = true;
      this.showHideItemsTable("hide");
      this.$scope.bulkItemList = Array<IItem>();
      for (let i = 0; i < this.$scope.data.bulkAddCount; i++) {
        let item = <IItem> {};
        this.$scope.bulkItemList.push(item);
      }
    }


    /**
     * Show-Hide Items Table
     */
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

    //ToDo: Need to move it to a Directive
    private initializeDatePickers() {
      setTimeout(function () {
        let itemSelector = $('.datepicker-default');
        itemSelector.datepicker();
        itemSelector.on('change', function () {
          $('.datepicker').hide();
        });
      }, 200);
    }


    /**
     * Save Record
     */
    private saveRecord(): void {
      let action = $("button[type=button][clicked=true]").val();
      this.catalogingService.saveRecord(this.$scope.record).then((response : any ) => {
        this.notify.show(response);
        this.saveRecordPostOperation(action);
      }, function errorCallback(response) {
        this.notify.error(response);
      });
    }

    /**
     * Save Record Post Operation.
     */
    private saveRecordPostOperation(action : string) {
      if (action == "save_edit_items") {
        //this.showItemUI();
      }
      else if (action == "save_view") {
        this.$scope.data.readOnlyMode = true;
      }
    }

    /**
     * Save Item
     */
    private saveItem(): void {
      this.catalogingService.saveItem(this.$scope.item).then((response : any ) => {
        this.notify.show(response);
      }, function errorCallback(response) {
        this.notify.error(response);
      });
    }


    /**
     * Saves more than one item at a time
     */
    private saveBulkItems(): void {
      let complete_json = {};
      complete_json["items"] = this.$scope.bulkItemList;
      this.catalogingService.saveBulkItems(complete_json).then((response : any ) => {
        this.notify.show(response);
      }, function errorCallback(response) {
        this.notify.error(response);
      });
    }

    /**
     * Set common values for Bulk Items
     */
    private setBulkItemsValue(): void {
      let bulkItemList = this.$scope.bulkItemList;
      this.catalogingService.setBulkItemsValue(bulkItemList, this.$scope.bulk.config);
      this.setSelect2ValuesForBulkItems();
    }


    /**
     * Set Select2 Values for Bulk Items Row, during a new save operation.
     */
    private setSelect2ValuesForBulkItems() : void {
      let data = $("#configSupplierName").select2("data");
      let searchTerm = data.text;
      $('#bulkItemContainer').find('.select2-input').each(function (index) {
        let inputElement: any = $(this)[0];
        let inputElementId = inputElement.id;

        $("#bulkContributorName" + index).select2("search", searchTerm);
        let e = jQuery.Event("keydown");
        e.which = 13;
        $("#" + inputElementId).trigger(e);
      });
    }

    /**/
    private fillSampleData() {
      this.$scope.data.readOnlyMode = false;
      this.$scope.record.mfnNo = undefined;
      this.$scope.record.language = 1;
      this.$scope.record.status = 0;
      this.$scope.record.bindingType = 1;
      this.$scope.record.acqType = 1;
      this.$scope.record.language = 1;
      let offSet = (new Date).getMilliseconds();
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

  }

  UMS.controller("Cataloging", Cataloging);
}