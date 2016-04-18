///<reference path="../../../service/HttpClient.ts"/>
///<reference path="../../../lib/jquery.notific8.d.ts"/>
///<reference path="../../../lib/jquery.notify.d.ts"/>
///<reference path="../../../lib/jquery.jqGrid.d.ts"/>
///<reference path="../../../grid/GridDecorator.ts"/>
module ums {

  interface IClassRoomScope extends ng.IScope {
    classRoomData:any;
  }

  export class ClassRoomInfo {
    public static $inject = ['appConstants', 'HttpClient', '$scope'];
    decoratedScope: GridConfig;

    constructor(private appConstants: any, private httpClient: HttpClient, private $scope: IClassRoomScope) {
      this.loadData();
    }

    private initializeGrid(): void {
      this.decoratedScope = GridDecorator.decorate(this.$scope);

      var columnModel = {
        colModel: [
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
              value: '1:Yes;0:No'
            }
          },
          {
            label: "Edit Actions",
            name: "actions",
            formatter: "actions",
            formatoptions: {
              keys: false,
              delOptions: {
                mtype: 'DELETE',
                onclickSubmit: () => {
                  this.httpClient.delete('academic/classroom/' + this.decoratedScope.gridOptions.currentSelectedRowId)
                      .success(() => {
                        $.notific8("Removed entry");
                      }).error((data) => {
                        console.error(data);
                      });
                }
              },
              afterSave: (rowid) => {
                this.insert(this.decoratedScope.grid.api.getRowData(rowid));
              }
            }
          }
        ]
      };

      this.decoratedScope.gridOptions.colModel = columnModel.colModel;
    }

    private insert(rowData: RowData<any>): void {
      this.decoratedScope.grid.api.toggleMessage('Saving...');
      if (rowData.id.indexOf('jqg') == 0) {
        this.httpClient.post('academic/classroom', rowData, HttpClient.MIME_TYPE_JSON).success(()=> {
          this.decoratedScope.grid.api.toggleMessage();
          this.loadData();
        }).error((response) => {
          console.error(response);
          this.loadData();
          this.decoratedScope.grid.api.toggleMessage();
        });
      } else {
        this.httpClient.put('academic/classroom', rowData, HttpClient.MIME_TYPE_JSON)
            .success(() => {
              this.decoratedScope.grid.api.toggleMessage();
            }).error((response) => {
              console.error(response);
              this.decoratedScope.grid.api.toggleMessage();
              this.loadData();
            });
      }
    }

    private loadData(): void {
      this.httpClient.get("academic/classroom/all", HttpClient.MIME_TYPE_JSON,
          (response) => {
            this.initializeGrid();
            this.$scope.classRoomData = response.rows;
          });
    }

  }
  UMS.controller('ClassRoomInfo', ClassRoomInfo);
}

