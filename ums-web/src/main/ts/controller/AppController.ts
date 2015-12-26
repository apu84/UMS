module ums {
  export class AppController {
    public static $inject = ['$scope', '$rootScope'];

    constructor(private $scope:any,
                private $rootScope:any) {
      this.$rootScope.style = 'style1';
      this.$rootScope.theme = 'pink-blue';
      this.$scope.data = {};
      this.$scope.effect = '';
      this.$scope.header = {
        form: false,
        chat: false,
        theme: false,
        footer: true,
        history: false,
        animation: '',
        boxed: '',
        layout_menu: '',
        theme_style: 'style1',
        header_topbar: 'static',
        menu_style: 'sidebar-colors',
        menu_collapse: '',
        menu_style_restore: 'sidebar-default',
        layout_horizontal_menu: '',

        toggle: function (k) {
          switch (k) {
            case 'chat':
              this.$scope.header.chat = !$scope.header.chat;
              break;
            case 'form':
              this.$scope.header.form = !$scope.header.form;
              break;
            case 'sitebar':
              this.$scope.header.menu_style = this.$scope.header.menu_style ? '' : (($scope.header.layout_menu === '') ? 'sidebar-collapsed' : 'right-side-collapsed');
              break;
            case 'theme':
              this.$scope.header.theme = !$scope.header.theme;
              break;
            case 'history':
              this.$scope.header.history = !$scope.header.history;
              this.$scope.header.menu_style = this.$scope.header.history ? 'sidebar-collapsed' : 'sidebar-default';
              break;
          }
        },

        collapse: (c) => {
          if (c === 'change') {
            this.$scope.header.menu_collapse = '';
          } else {
            if ($scope.header.menu_style) {
              this.$scope.header.menu_style = '';
              this.$scope.header.menu_collapse = this.$scope.header.menu_collapse ? '' : 'sidebar-collapsed';
            } else {
              this.$scope.header.menu_collapse = this.$scope.header.menu_collapse ? '' : 'sidebar-collapsed';
              this.$scope.header.menu_style = this.$scope.header.menu_style_restore;
            }
          }

        }
      };

      this.$scope.$on('$stateChangeSuccess', (event, toState, toParams, fromState, fromParams) => {
        this.$scope.header.animation = 'fadeInUp';
        setTimeout( () => {
          this.$scope.header.animation = '';
        }, 100);

        $('.sidebar-collapse').removeClass('in').addClass('collapse');

        this.$scope.data = $.fn.Data.get(toState.url);
        if (-1 == $.inArray(toState.url, ['/extra-500', '/extra-404', '/extra-lock-screen', '/extra-signup', '/extra-signin'])) {
          $('body').removeClass('bounceInLeft');
          $("body>.default-page").show();
          $("body>.extra-page").hide();
        }
        else {
          window.scrollTo(0, 0);
        }

        this.$scope.header.boxed = '';
        this.$scope.header.footer = true;

        this.$rootScope.style = 'style1';
        this.$rootScope.theme = 'pink-blue';

        if ('/layout-left-sidebar' === toState.url) {
          this.$scope.header.layout_menu = '';
          this.$scope.header.header_topbar = '';
          this.$scope.header.layout_horizontal_menu = '';
        }
        else if ('/layout-left-sidebar-collapsed' === toState.url) {
          this.$scope.header.layout_menu = '';
          this.$scope.header.header_topbar = 'sidebar-collapsed';
          this.$scope.header.layout_horizontal_menu = '';
        }
        else if ('/layout-right-sidebar' === toState.url) {
          this.$scope.header.layout_menu = 'right-sidebar';
          this.$scope.header.header_topbar = '';
          this.$scope.header.layout_horizontal_menu = '';
        }
        else if ('/layout-right-sidebar-collapsed' === toState.url) {
          this.$scope.header.layout_menu = 'right-sidebar';
          this.$scope.header.header_topbar = 'right-side-collapsed';
          this.$scope.header.layout_horizontal_menu = '';
        }
        else if ('/layout-horizontal-menu' === toState.url) {
          this.$scope.header.layout_menu = '';
          this.$scope.header.header_topbar = 'horizontal-menu-page';
          this.$scope.header.layout_horizontal_menu = 'horizontal-menu hidden-sm hidden-xs';
        }
        else if ('/layout-horizontal-menu-sidebar' === toState.url) {
          this.$scope.header.layout_horizontal_menu = 'horizontal-menu hidden-sm hidden-xs';
        }
        else if ('/layout-fixed-topbar' === toState.url) {
          this.$scope.header.layout_menu = '';
          this.$scope.header.header_topbar = 'fixed-topbar';
          this.$scope.header.layout_horizontal_menu = '';
        }
        else if ('/layout-boxed' === toState.url) {
          this.$scope.header.boxed = 'container';
        }
        else if ('/layout-hidden-footer' == toState.url) {
          this.$scope.header.footer = false;
        }
        else if ($.inArray(toState.url, ['/extra-500', '/extra-404']) >= 0) {
          this.$rootScope.style = 'style1';
          this.$rootScope.theme = 'pink-violet';
        }
      });

      this.$scope.style_change = () => {
        this.$rootScope.style = this.$scope.header.theme_style;
      };

      this.$scope.theme_change = (t) => {
        this.$rootScope.theme = t;
      };

      $(window).scroll( () => {
        if ($(this).scrollTop() > 0) {
          $('.quick-sidebar').css('top', '0');
        } else {
          $('.quick-sidebar').css('top', '50px');
        }
      });
      /*    $('.quick-sidebar > .header-quick-sidebar').slimScroll({
       "height": $(window).height() - 50,
       'width': '280px',
       "wheelStep": 30
       });*/
      $('#news-ticker-close').click(function (e) {
        $('.news-ticker').remove();
      });
    }
  }
  UMS.controller('AppController', AppController);
}