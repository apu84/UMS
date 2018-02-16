module ums {
    export class ElementValidation implements ng.IDirective {

        constructor(private employeeService: EmployeeService) {
            console.log("In Directive");
        }

        public restrict: string = 'A';
        public require: any = 'ngModel';

        public link = (scope, element, attrs, ctrl) => {

            element.on('focus', function () {
                if (!scope.initialValue) {
                    scope.initialValue = ctrl.$viewValue;
                }
            });
            element.on('blur', function () {
                console.log("ctrl.$viewValue: _------------");
                console.log(ctrl.$viewValue);
                if (ctrl.$viewValue != scope.initialValue) {
                    console.log("inside id");
                    console.log(this.employeeService);
                    this.employeeService.checkDuplicate(ctrl.$viewValue).then((data) => {
                        console.log("Date");
                        console.log(data);
                        ctrl.$setValidity('isUnique', data);
                    });
                }
            });
        };
    }

    UMS.directive("elementValidation", ['employeeService', function(employeeService) {
        return new ElementValidation(employeeService)
    }
    ])
}