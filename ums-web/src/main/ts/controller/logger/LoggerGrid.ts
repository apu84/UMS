module ums {

  interface ILoggerScope extends ng.IScope {
    loggerData:any;
  }

  interface ILevel {
    levelInt: number;
    levelStr: string;
  }

  interface LoggerRowData extends RowData {
    level : string;
    name : string;
  }

  export class LoggerGrid {
    public static $inject = ['appConstants', 'HttpClient', '$scope'];
    decoratedScope: GridConfig;
    private levelString: string = '';
    private levelArray: Array<ILevel>;

    constructor(private appConstants: any, private httpClient: HttpClient, private $scope: ILoggerScope) {
      this.httpClient.get('logger/levels', HttpClient.MIME_TYPE_JSON,
          (response: Array<ILevel>) => {
            this.levelArray = response;
            for (var i = 0; i < response.length; i++) {
              this.levelString = this.levelString + response[i].levelInt + ":" + response[i].levelStr;
              if (i < response.length - 1) {
                this.levelString = this.levelString + ';';
              }
            }
            this.loadData();
          });

    }

    private initializeGrid(): void {
      this.decoratedScope = GridDecorator.decorate(this.$scope);

      var columnModel = {
        colModel: [
          {
            label: 'Name',
            name: 'name',
            hidden: false,
            editable: true,
            key: true
          },
          {
            label: 'Level',
            name: 'level',
            editable: true,
            align: 'center',
            formatter: 'select',
            edittype: 'select',
            editoptions: {
              value: this.levelString,
              defaultValue: 'DEBUG'
            },
            stype: 'select',
            searchoptions: {
              sopt: ['eq', 'ne'],
              value: this.levelString
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
                  this.insert(this.decoratedScope.grid.api.getRowData(this.decoratedScope.gridOptions.currentSelectedRowId),
                      this.getLevelId("OFF"));
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

    private insert(rowData: LoggerRowData<any>, levelId?: number): void {
      this.decoratedScope.grid.api.toggleMessage('Saving...'); console.debug(levelId+"");
      if (levelId) {
        rowData.level = levelId.toString();
      }
      console.debug("%o",rowData);
      this.httpClient.post('logger', rowData, HttpClient.MIME_TYPE_JSON).success(()=> {
        this.decoratedScope.grid.api.toggleMessage();
        this.loadData();
      }).error((response) => {
        console.error(response);
        this.loadData();
        this.decoratedScope.grid.api.toggleMessage();
      });

    }

    private loadData(): void {
      this.httpClient.get("logger/list", HttpClient.MIME_TYPE_JSON,
          (response) => {
            this.initializeGrid();
            this.$scope.loggerData = response;
          });
    }

    private getLevelId(pLevelName): number {
      for (var i = 0; i < this.levelArray.length; i++) {
        if (this.levelArray[i].levelStr == pLevelName) {
          return this.levelArray[i].levelInt;
        }
      }
    }

  }
  UMS.controller('LoggerGrid', LoggerGrid);
}

