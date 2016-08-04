///<reference path="../../service/HttpClient.ts"/>
///<reference path="../../lib/jquery.notific8.d.ts"/>
///<reference path="../../lib/jquery.notify.d.ts"/>
///<reference path="../../lib/jquery.jqGrid.d.ts"/>
///<reference path="../../grid/GridDecorator.ts"/>

module ums {

  export interface RowData {
    status:number;
  }

  interface ISemesterScope extends ng.IScope {
    semesterData:any;
  }

  export class SemesterInfo implements GridEditActions,RowAttribute {
    public static $inject = ['appConstants', 'HttpClient', '$scope', 'notify'];

    constructor(private appConstants: any, private httpClient: HttpClient, private $scope: ISemesterScope,private notify: Notify) {
      this.loadData();
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
      this.httpClient.delete('academic/semester/' + rowData)
          .success(() => {
            notify.success("Successfully deleted select semester.");
            this.decorateScope().grid.api.toggleMessage();
            this.loadData();
          }).error((data) => {
            console.error(data);
          });
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
      this.httpClient.get("academic/semester/all", HttpClient.MIME_TYPE_JSON,
          (response) => {
            this.initializeGrid();
            this.$scope.semesterData = response.entries;
          });
    }

  }
  UMS.controller('SemesterInfo', SemesterInfo);
}

