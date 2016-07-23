///<reference path="../../service/HttpClient.ts"/>
///<reference path="../../lib/jquery.notific8.d.ts"/>
///<reference path="../../lib/jquery.notify.d.ts"/>
///<reference path="../../lib/jquery.jqGrid.d.ts"/>
///<reference path="../../grid/GridDecorator.ts"/>

module ums {

  interface ISemesterScope extends ng.IScope {
    semesterData:any;
  }

  export class SemesterInfo implements GridEditActions {
    public static $inject = ['appConstants', 'HttpClient', '$scope'];

    constructor(private appConstants: any, private httpClient: HttpClient, private $scope: ISemesterScope) {
      this.loadData();
    }

    private initializeGrid(): void {
      GridDecorator.decorate(this);
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
          name: 'programType',
          editable: true
        },
        {
          label: 'Semester Name',
          name: 'name',
          editable: true,
          width: 200
        },
        {
          label: 'Start Date',
          name: 'startDate',
          editable: true
        },
        {
          label: 'End Date',
          name: 'endDate',
          width: 50,
          editable: true
        },
        {
          label: 'Status',
          name: 'status',
          editable: true
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

