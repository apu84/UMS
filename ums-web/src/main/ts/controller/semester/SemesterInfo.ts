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
    public static $inject = ['appConstants', 'HttpClient', '$scope'];

    constructor(private appConstants: any, private httpClient: HttpClient, private $scope: ISemesterScope) {
      this.loadData();
    }

    private initializeGrid(): void {
      GridDecorator.decorate(this);
      RowAttributeDecorator.decorate(this,this);
    }

    public insert(rowData: RowData): void {
      this.decorateScope().grid.api.toggleMessage('Saving...');
      if (rowData.id.indexOf('jqg') == 0) {
        this.httpClient.post('academic/classroom', rowData, HttpClient.MIME_TYPE_JSON).success(()=> {
          this.decorateScope().grid.api.toggleMessage();
          this.loadData();
        }).error((response) => {
          console.error(response);
          this.loadData();
          this.decorateScope().grid.api.toggleMessage();
        });
      }
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
      this.httpClient.delete('academic/classroom/' + rowData)
          .success(() => {
            $.notific8("Removed entry");
          }).error((data) => {
            console.error(data);
          });
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
            value: '11:Undergraduate;22:Postgraduate',
            readonly: "readonly"
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
          editable: false,
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

