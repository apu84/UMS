///<reference path="../../service/HttpClient.ts"/>
///<reference path="../../lib/jquery.notify.d.ts"/>
///<reference path="../../lib/jquery.jqGrid.d.ts"/>
///<reference path="../../grid/GridDecorator.ts"/>

module ums {

  export interface RowData {
    status:number;
  }

  interface ISemesterScope extends ng.IScope {
    semesterData:any;
    manageAddButton: Function;
  }

  export class SemesterInfo implements GridEditActions,RowAttribute {
    public static $inject = ['appConstants', 'HttpClient', '$scope', 'notify','$location'];

    constructor(private appConstants: any, private httpClient: HttpClient, private $scope: ISemesterScope,private notify: Notify,private $location:Location) {
      console.log($location);
      this.loadData();
      $scope.manageAddButton=this.manageAddButton.bind(this);
    }

    private initializeGrid(): void {
      GridDecorator.decorate(this);
      RowAttributeDecorator.decorate(this,this);
    }

    public insert(rowData: RowData): void {
      //this.decorateScope().grid.api.setMessageDisplayed(false);
      this.decorateScope().grid.api.toggleMessage('Saving...');
      console.log(rowData);
      console.log(rowData.id);
        this.httpClient.post('academic/semester/', rowData, HttpClient.MIME_TYPE_JSON).success(()=> {
          this.decorateScope().grid.api.toggleMessage();
          this.loadData();
        }).error((response) => {
          console.log(response);
          // this.loadData();
          this.decorateScope().grid.api.removeLoadingMessage();
          this.decorateScope().grid.api.toggleMessage();
        });
    }
    public edit(rowData: RowData): void {
      this.decorateScope().grid.api.toggleMessage('Saving...');
      this.httpClient.put('academic/classroom', rowData, HttpClient.MIME_TYPE_JSON)
          .success(() => {
            this.decorateScope().grid.api.toggleMessage();
          }).error((response) => {
            console.error(response);
            this.decorateScope().grid.api.toggleMessage();
            this.loadData();
          });
    }

    public remove(rowData: RowData): void {
      var notify=this.notify;
      notify.error("Semester delete is not allowed.");
      //asdfasdasdf
    }

    public beforeShowEditForm(form: any, gridElement: JQuery): void {

      var rowId =gridElement.jqGrid('getGridParam','selrow');
      var rowData = gridElement.getRowData(rowId);
      if(rowData["status"]=="0") {
        gridElement.jqGrid('setColProp', 'programTypeId', {editoptions: {readonly: "readonly"}});
        gridElement.jqGrid('setColProp', 'name', {editoptions: {readonly: "readonly"}});
        gridElement.jqGrid('setColProp', 'status', {editoptions: {readonly: "readonly"}});
      }


    }

    public afterShowEditForm(form: any, gridElement: JQuery): void {
      var rowId =gridElement.jqGrid('getGridParam','selrow');
      var rowData = gridElement.getRowData(rowId);
      gridElement.jqGrid('setColProp','programTypeId',{  editoptions: {readonly: false}});
      gridElement.jqGrid('setColProp','name',{  editoptions: {readonly: false}});
      gridElement.jqGrid('setColProp','status',{  editoptions: {readonly: false}});
    }

    public decorateScope(): GridConfig {
      return this.$scope;
    }

    public rowattr(rowData:RowData):any{
       if(rowData.status==1)
            return {"class": "activeSemesterBG"};
      else if(rowData.status==2)
        return {"class": "newlyCreatedSemesterBG"};
    }

    public getColumnModel(): any {
      return [
        {
          label: 'Semester Id',
          name: 'id',
          hidden: true,
          editable: true,
          key: true
        },
        {
          label: 'Program Type',
          name: 'programTypeId',
          editable: true,
          formatter: 'select',
          edittype: 'select',
          width: 150,
          editoptions: {
            value: '11:Undergraduate;22:Postgraduate'
          },
          stype: 'select',
          searchoptions: {
            sopt: ['eq', 'ne'],
            value: '11:Undergraduate;22:Postgraduate'
          }
        },
        {
          label: 'Semester Name',
          name: 'name',
          editable: true,
          width: 250
        },
        {
          label: 'Start Date',
          name: 'startDate',
          editable: true
        },
        {
          label: 'End Date',
          name: 'endDate',
          width: 100,
          editable: true
        },
        {
          label: 'Status',
          name: 'status',
          editable: true,
          width:100,
          formatter: 'select',
          edittype: 'select',
          editoptions: {
            value: '0:Inactive; 1:Active; 2:Newly Created'
          },
          stype: 'select',
          searchoptions: {
            sopt: ['eq', 'ne'],
            value: '0:Inactive;1:Active;2:Newly Created'
          }
        }

      ]
    }

    private loadData(): void {
      var that=this;
      this.httpClient.get("academic/semester/all", HttpClient.MIME_TYPE_JSON,
          (response) => {
            this.initializeGrid();
            this.$scope.semesterData = response.entries;

            setTimeout(function(){ that.manageAddButton(); }, 100);
          });

    }

    public manageAddButton():void{
      var location:any=this.$location;
      var scope:any=this.$scope;
      var gridId=this.decorateScope().grid.api.getGrid()[0].id;
      var $td = $('#add_'+gridId );
        $td.hide();
      $("#"+gridId).navButtonAdd('#semesterListPager', {
        caption: "",
        title: "Click here to add new record",
        buttonicon: "glyphicon glyphicon-plus",
        onClickButton: function() {
          console.log(location);
          location.path("/createSemester");
          scope.$apply(); // this
        },
        position: "first"
      });
      }

  }
  UMS.controller('SemesterInfo', SemesterInfo);
}

