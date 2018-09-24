(function ($) {
    $.fn.Data = function () {
        return {};
    };
    var $this: any = $.fn.Data;


    $.fn.Data.pages = {
        '/index': {title: 'Dashboard', 'breadcrumb': ['Dashboard']},
        '/userHome': {title: 'User Home', 'breadcrumb': ['User Home']},
        '/search': {title: 'Cataloging', 'breadcrumb': ['Cataloging', 'Search']},
        '/newBook': {title: 'Cataloging', 'breadcrumb': ['Cataloging', 'Books']},
        '/thesis': {title: 'Cataloging', 'breadcrumb': ['Cataloging', 'Thesis']},
        '/circulation': {title: 'Circulation', 'breadcrumb': ['Circulation']},
        '/patrons': {title: 'Patrons', 'breadcrumb': ['Patrons']},
        '/cataloging': {title: 'Cataloging', 'breadcrumb': ['Cataloging']},
        '/search/:1': {title: "Catalog: Search", 'breadcrumb': ['Catalog']},
        '/:1/record/:2': {title: 'Catalog: Records', 'breadcrumb': ['Catalog']},
        '/recordLog': {title: 'Log', 'breadcrumb': ['Log']},
        '/employeeProfile': {title: 'Profile', 'breadcrumb': ['Profile']},
        '/personal': {title: 'Profile', 'breadcrumb': ['Profile']},
        '/academic': {title: 'Profile', 'breadcrumb': ['Profile']},
        '/publication': {title: 'Profile', 'breadcrumb': ['Profile']},
        '/award': {title: 'Profile', 'breadcrumb': ['Profile']},
        '/training': {title: 'Profile', 'breadcrumb': ['Profile']},
        '/experience': {title: 'Profile', 'breadcrumb': ['Profile']},
        '/additional': {title: 'Profile', 'breadcrumb': ['Profile']},
        '/service': {title: 'Profile', 'breadcrumb': ['Profile']}
    };


    $.fn.Data.get = function (id) {
        if (id && $this.pages[id]) {
            return $this.pages[id];
        }
    };

    $.fn.Data.checkbox = function () {
        if ($('#demo-checkbox-radio').length <= 0) {
            /*      $('input[type="checkbox"]:not(".switch")').iCheck({
             checkboxClass: 'icheckbox_minimal-grey',
             increaseArea: '20%' // optional
             });
             $('input[type="radio"]:not(".switch")').iCheck({
             radioClass: 'iradio_minimal-grey',
             increaseArea: '20%' // optional
             });*/
        }
    };
})(jQuery);