module ums {

  export class GroupController {
    public static $inject = ['$scope', '$modal', 'notify', 'GroupService'];

    public groups: IGroup[];
    public addedGroup: IGroup;
    public groupMapWithId: any;
    public gridOptions: any;
    public handsOnTableFeature: any;

    constructor($scope: ng.IScope, private $modal: any, private notify: Notify, private groupService: GroupService) {

      this.groupMapWithId = {};
      this.gridOptions = <GridOptions>{};

      // this.gridOptions.data = {};
      this.initialize();
      this.initializeHandsOnTable();


    }

    private addModalClicked() {
      this.addedGroup = <IGroup>{};
      this.addedGroup.mainGroup = "";
    }


    private initialize() {
      this.groupService.getAllGroups().then((groups: IGroup[]) => {
        this.assignToGroupAndMap(groups);

      });


    }

    private assignToGroupAndMap(groups: ums.IGroup[]) {
      this.groups = [];
      this.groups = groups;
      this.groups.forEach((g: IGroup) => {
        this.groupMapWithId[g.groupCode] = g;
        g.mainGroupObject = this.groupMapWithId[g.mainGroup];
        g.flagBoolValue = g.flag == null ? false : true;
      });
    }

    private initializeHandsOnTable() {
      this.handsOnTableFeature = {};
      this.handsOnTableFeature = {
        settings: {
          colHeaders: true,
          rowHeaders: true,
          currentRowClassName: 'currentRow',
          currentColClassName: 'currentCol',
          fillHandle: false,
          manualRowResize: true,
          manualColumnResize: true,
          columnSorting: true,
          sortIndicator: true,
          readOnly: true,
          stretchH: 'all',
          height: $(".page-content").height() - 5,
          observeChanges: true,
          search: true,
          columns: [
            {"title": "Group Name", "data": "groupName"},
            {"title": "Under The Main Group Of", "data": "mainGroupObject.groupName"},
            {"title": "Sub Ledger", "data": "flag"}
          ]
        }
      };
    }


    private add() {
      console.log('added group');

      console.log(this.addedGroup);

      this.groupService.saveAGroup(this.addedGroup).then((groups) => {
        this.addedGroup = <IGroup>{};
        this.assignToGroupAndMap(groups);

      });
    }
  }


  UMS.controller("GroupController", GroupController);
}