module ums {
  export class MainController {
    public static $inject = ['$scope', 'HttpClient'];

    constructor(private $scope:any,
                private httpClient:HttpClient) {
      /*
      setTimeout(function () {
        $('.todo-list').slimScroll({
          "width": '100%',
          "height": '250px',
          "wheelStep": 30
        });
        $(".sortable").sortable();
        $(".sortable").disableSelection();
        //BEGIN COUNTER FOR SUMMARY BOX
        //counterNum($(".profit h4 span:first-child"), 189, 112, 1, 30);
        //counterNum($(".income h4 span:first-child"), 636, 812, 1, 50);
        //counterNum($(".task h4 span:first-child"), 103, 155, 1, 100);
        //counterNum($(".visit h4 span:first-child"), 310, 376, 1, 500);
        function counterNum(obj, start, end, step, duration) {
          $(obj).html(start);
          var interval = setInterval(function () {
            var val = Number($(obj).html());
            if (val < end) {
              $(obj).html(val + step);
            } else {
              clearInterval(interval);
            }
          }, duration);
        }

      }, 50);
*/
      httpClient.get("userHome", HttpClient.MIME_TYPE_JSON, (response) => {
        $scope.userHome = response.infoList;
        $scope.userRole = response.userRole;
        if(response.userRole=="student") {
          if(document.getElementById("empProfile"))
          document.getElementById("empProfile").style.display="none";
        }
      });
    }
  }
  UMS.controller('MainController', MainController);
}
