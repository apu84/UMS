module ums {

  interface ILoggerScope extends ng.IScope, GridConfig {
    loggerData:any;
  }

  interface ILevel {
    levelInt: number;
    levelStr: string;
  }

  export interface LoggerRowData extends RowData {
    level : string;
    name : string;
  }

  export class LoggerGrid implements GridEditActions {
    public static $inject = ['appConstants', 'HttpClient', '$scope'];
    //decoratedScope: GridConfig;
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
      GridDecorator.decorate(this);
    }

    public decorateScope(): GridConfig {
      return this.$scope;
    }

    public getColumnModel(): any {
      return [
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
        }
      ]
    }

    public insert(rowData: LoggerRowData, levelId?: number): void {
      this.decorateScope().grid.api.toggleMessage('Saving...');
      this.httpClient.post('logger', rowData, HttpClient.MIME_TYPE_JSON).success(()=> {
        this.decorateScope().grid.api.toggleMessage();
        this.loadData();
      }).error((response) => {
        console.error(response);
        this.loadData();
        this.decorateScope().grid.api.toggleMessage();
      });

    }

    public edit(rowData: LoggerRowData): void {
      this.decorateScope().grid.api.toggleMessage('Saving...');
      this.httpClient.post('logger', rowData, HttpClient.MIME_TYPE_JSON).success(()=> {
        this.decorateScope().grid.api.toggleMessage();
        this.loadData();
      }).error((response) => {
        console.error(response);
        this.loadData();
        this.decorateScope().grid.api.toggleMessage();
      });
    }

    public remove(rowData: LoggerRowData): void {
      rowData.level = this.getLevelId("OFF").toString();
      this.decorateScope().grid.api.toggleMessage('Saving...');
      this.httpClient.post('logger', rowData, HttpClient.MIME_TYPE_JSON).success(()=> {
        this.decorateScope().grid.api.toggleMessage();
        this.loadData();
      }).error((response) => {
        console.error(response);
        this.loadData();
        this.decorateScope().grid.api.toggleMessage();
      });
    }

    public beforeShowEditForm(formId: string,gridElement: JQuery): void {
    }

    public afterShowEditForm(formId: String, gridElement: JQuery): void {
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

