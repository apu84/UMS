module ums{

    export class ModifyEmployee{
        public static $inject = [
            'registrarConstants',
            '$q',
            'notify',
            'departments',
            'designations',
            'employeeService'
        ];

        private allDepartments = [];
        private allDesignations = [];

        constructor(private registrarConstants: any,
                    private $q: ng.IQService,
                    private notify: Notify,
                    private departments: any,
                    private designations: any,
                    private employeeService: EmployeeService) {

            this.allDepartments = departments;
            this.allDesignations = designations;

        }
    }

    UMS.controller("ModifyEmployee", ModifyEmployee);
}