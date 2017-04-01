module ums {

  interface IClassRoomScope extends ng.IScope {
    classRoomData:any;
  }

  export class ClassRoomInfo implements GridEditActions {

    public static $inject = ['appConstants', 'HttpClient', '$scope'];


    constructor(private appConstants: any, private httpClient: HttpClient, private $scope: IClassRoomScope) {
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
      console.log('rowData');
      console.log(rowData);
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

    public beforeShowEditForm(formId: string, gridElement: JQuery): void {
    }

    public afterShowEditForm(formId: String, gridElement: JQuery): void {
    }

    public decorateScope(): GridConfig {
      return this.$scope;
    }

    public getColumnModel(): any {
      return [
        {
          label: 'Room Id',
          name: 'id',
          hidden: true,
          key: true
        },
        {
          label: 'Room Number',
          name: 'roomNo',
          editable: true
        },
        {
          label: 'Description',
          name: 'description',
          editable: true,
          width: 200
        },
        {
          label: 'Row',
          name: 'totalRow',
          editable: true
        },
        {
          label: 'Column',
          name: 'totalColumn',
          width: 50,
          editable: true
        },
        {
          label: 'Capacity',
          name: 'capacity',
          editable: true
        },
        {
          label: 'Room Type',
          name: 'roomType',
          editable: true,
          align: 'center',
          formatter: 'select',
          edittype: 'select',
          editoptions: {
            value: '1:Theory;2:Sessional;0:Others',
            defaultValue: 'Theory'
          },
          stype: 'select',
          searchoptions: {
            sopt: ['eq', 'ne'],
            value: '1:Theory;2:Sessional;0:Others'
          }
        },


        {
          label: 'Dept./School',
          name: 'dept_id',
          editable: true,
          width: 100, align: 'center', formatter: 'select',
          edittype: 'select',
          editoptions: {
            value: this.appConstants.dept4JqGridSelectBox,
            defaultValue: 'None'
          },
          stype: 'select',
          searchoptions: {
            sopt: ['eq', 'ne'],
            value: this.appConstants.dept4JqGridSelectBox
          }
        },
        {
          label: 'Seat Plan',
          name: 'examSeatPlan',
          editable: true,
          width: 80, align: 'center', formatter: 'checkbox',
          edittype: 'checkbox', editoptions: {value: '1:0', defaultValue: '1'},
          stype: 'select',
          searchoptions: {
            sopt: ['eq', 'ne'],
            value: 'true:Yes;false:No'
          }
        }
      ]
    }

    private loadData(): void {
      this.httpClient.get("academic/classroom/all", HttpClient.MIME_TYPE_JSON,
          (response) => {
            this.initializeGrid();
            this.$scope.classRoomData = response.rows;
          });
    }

    public loadComplete():any{
      alert("ifti");
    }

  }
  UMS.controller('ClassRoomInfo', ClassRoomInfo);
}

