module ums {
  export class AppController {
    public static $inject = ['$scope', '$rootScope', '$window', '$http', '$templateCache','HttpClient', '$transitions'];

    private transition: any;
    constructor(private $scope:any,
                private $rootScope:any,
                private $window: ng.IWindowService,
                private $http: ng.IHttpService,
                private $templateCache: ng.ITemplateCacheService,
                private httpClient: HttpClient,
                private $transition: any) {

      this.transition = $transition;
      $scope.downloadUserGuide = this.downloadUserGuide.bind(this);

      this.$rootScope.style = 'style1';
      this.$rootScope.theme = 'orange-blue';
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
        header_topbar: 'header-fixed',   //static or header-fixed // changed by ifti
        menu_style: 'sidebar-colors',
        menu_collapse: '',
        menu_style_restore: 'sidebar-colors', // sidebar-default [changed by ifti]
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

      this.transition.onSuccess({}, ($transition$) => {
        /*
        //Commented by Ifti
        this.$scope.header.animation = 'fadeInUp';

        setTimeout( () => {
          this.$scope.header.animation = '';
        }, 100);
        */

        var breadcrumb="";
        if($transition$.promise.$$state.value["url"] =="/gradeSheetSelectionTeacher/:1")
          breadcrumb="/gradeSheetSelectionTeacher/T";
        else
          breadcrumb =$transition$.promise.$$state.value["url"];
        this.$scope.data = $.fn.Data.get(breadcrumb);


        $('.sidebar-collapse').removeClass('in').addClass('collapse');
        if (-1 == $.inArray($transition$.promise.$$state.value["url"], ['/extra-500', '/extra-404', '/extra-lock-screen', '/extra-signup', '/extra-signin'])) {
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
        this.$rootScope.theme = 'orange-blue';
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

      $scope.user = JSON.parse($window.sessionStorage.getItem('ums.user'));
    }

    private downloadUserGuide(navigationId:string,manualTitle:string,manualType:string):any{

      if(manualType=="html") {
        this.httpClient.get("userGuide/html/" + navigationId, HttpClient.MIME_TYPE_JSON,
            (response: any) => {
             console.log(response);
              this.$scope.data.htmlUserGuide=response.htmlContent;
            });
      }
      else if(manualType=="pdf") {
        var fileName = manualTitle + ".pdf";
        var contentType:string = UmsUtil.getFileContentType("pdf");
        this.httpClient.get("userGuide/pdf/" + navigationId, contentType,
            (data:any, etag:string) => {
              UmsUtil.writeFileContent(data, contentType, fileName);
            },
            (response:ng.IHttpPromiseCallbackArg<any>) => {
              console.error(response);
            }, 'arraybuffer');
      }
    }


  }
  UMS.controller('AppController', AppController);
}
