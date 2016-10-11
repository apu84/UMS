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
      firstSelected:'=first',
      secondSelected:'=second',
      addFirstSelectedValue:'&addFirst',
      addSecondSelectedValue:'&addSecond',
      addOneStudent:'&addOne',
      addRangeStudent:'&addRange'
    };

    public link = ($scope:any, element:any, attributes:any) => {


      console.log("Hello there");
      console.log($scope);
      $scope.selected={};
      var currentScope = this;



      //$scope.$watch('firstSelected',(value, oldValue)=>{
        console.log("value changed");
        $( "#tags" ).autocomplete({
          source: $scope.autoSuggestionList
        });
        $('#tags').on('select', function (event,ui) {
          var id= ui.item.value;
          $scope.addFirstSelectedValue({id:ui.item.value});
        }).select();
      //});

      //$scope.$watch('secondSelected',(value)=>{
        $( "#tags2" ).autocomplete({
          source: $scope.autoSuggestionList
        });
        $('#tags2').on('select', function (event,ui) {
          var id= ui.item.value;
          $scope.addSecondSelectedValue({id:ui.item.value});
        }).select();

      //});




        //$scope.$watch('addedStudents',(value)=>{
          currentScope.$timeout(()=>{
            $("#addOneBtn").click(()=>{
              console.log("in the click event");
              $scope.addOneStudent();
            });

            $("#addRangeBtn").click(()=>{
              console.log("in the click event");
              $scope.addRangeStudent();
            });
          });
        //});





    };

    public templateUrl:string = "./views/directive/auto-completion.html";
  }

  UMS.directive("autoComplete", ['$timeout',($timeout:ng.ITimeoutService) => new AutoComplete($timeout) ]);
}