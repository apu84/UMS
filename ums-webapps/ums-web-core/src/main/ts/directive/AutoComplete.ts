/**
 * Created by My Pc on 05-Oct-16.
 */
module ums{
  class AutoComplete implements ng.IDirective{
    //studentIds:any;
    static $inject=['$timeout'];
    constructor(public $timeout:ng.ITimeoutService){

    }
    public restrict:string="A";
    public scope={
      autoSuggestionList:'=ids',
      addedStudents:'=x',
      addFirstSelectedValue:'&addFirst',
      addSecondSelectedValue:'&addSecond',
      addOneStudent:'&addOne',
      addRangeStudent:'&addRange'
    };

    public link = ($scope:any, element:any, attributes:any) => {


      console.log("Hello there");
      console.log($scope);
      $scope.selected={};



        $( "#tags" ).autocomplete({
          source: $scope.autoSuggestionList,

          select:(event,ui)=>{
            var id= ui.item.value;
            console.log($scope);
            console.log(ui.item.value);
            $scope.addFirstSelectedValue({id:ui.item.value});
          }
        });

        $( "#tags2" ).autocomplete({
          source: $scope.autoSuggestionList,

          select:(event,ui)=>{
            var id= ui.item.value;
            console.log($scope);
            console.log(ui.item.value);
            $scope.addSecondSelectedValue({id:ui.item.value});
          }
        });

        var currentScope = this;

        $scope.$watch('addedStudents',function(value){
          currentScope.$timeout(()=>{
            $("#addOneBtn").click(()=>{
              console.log("in the click event");
              $scope.addOneStudent();
            });

            $("#addRangeBtn").click(()=>{
              console.log("in the click event");
              $scope.addRangeStudent();
            });
          },1000);
        });





    };

    public templateUrl:string = "./views/directive/student-adviser-students.html";
  }

  UMS.directive("autoComplete", ['$timeout',($timeout:ng.ITimeoutService) => new AutoComplete($timeout) ]);
}