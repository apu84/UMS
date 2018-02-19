module ums {
    export class CirculationSearchBox implements ng.IDirective {

        constructor() {
        }

        public restrict: string = "E";
        public scope = {
            data: '=',
            searchValue: '=',
            doSearch: '&'
        };

        public link = (scope: any, element: JQuery, attributes: any) => {
            scope.searchValue = $('dirProgram').val();

        };

        public templateUrl: string = "./views/directive/circulation-search-box.html";
    }

    UMS.directive("circulationSearchBox", () => new CirculationSearchBox());
}