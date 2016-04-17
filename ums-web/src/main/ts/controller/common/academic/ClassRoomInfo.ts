///<reference path="../../../model/master_data/ClassRoom.ts"/>
///<reference path="../../../service/HttpClient.ts"/>
///<reference path="../../../lib/jquery.notific8.d.ts"/>
///<reference path="../../../lib/jquery.notify.d.ts"/>
///<reference path="../../../lib/jquery.jqGrid.d.ts"/>
module ums {

  interface IClassRoomScope extends ng.IScope {
    addNewRoom: Function;
    updateRoom: Function;
    classRoom:ClassRoom;
    data:any;
    rowId:any;
  }

  export class ClassRoomInfo {
    public static $inject = ['appConstants', 'HttpClient','$scope'];
    constructor(private appConstants:any,private httpClient:HttpClient,private $scope:IClassRoomScope) {

      var lastSel,
      cancelEditing = function(myGrid) {
        var lrid;
        if (typeof lastSel !== "undefined") {
          myGrid.jqGrid('restoreRow',lastSel);
          lrid = $.jgrid.jqID(lastSel);
          $("tr#" + lrid + " div.ui-inline-edit, " + "tr#" + lrid + " div.ui-inline-del").show();
          $("tr#" + lrid + " div.ui-inline-save, " + "tr#" + lrid + " div.ui-inline-cancel").hide();
        }
      };

      var hideDelIcon = function(rowid) {
        setTimeout(function() {
          $("tr#"+$.jgrid.jqID(rowid)+ " div.ui-inline-edit").hide();
          $("tr#"+$.jgrid.jqID(rowid)+ " div.ui-inline-del").hide();
        },50);
      };
      var thisGrid =$("#jqGrid").jqGrid({
        datatype: "json",
        url: 'https://localhost/ums-webservice-common/academic/classroom/all',
        editurl:'https://localhost/ums-webservice-common/academic/classroom',
        loadBeforeSend: function(jqXHR) {
          jqXHR.setRequestHeader("X-Authorization", 'Basic ZHByZWdpc3RyYXI6Yzg5ZTdhYzMtNjM1NS00ZjQ0LWE5OWEtNzZjNmJmNDkyNWM5');
        },
        jsonReader: { repeatitems: false },
        colModel: [
          {
            label: 'Room Id',
            name: 'id',
            width: 10,
            hidden:true,
            key: true
          },
          {
            label: 'Room Number',
            name: 'roomNo',
            editable: true,
            width: 100
          },
          {
            label: 'Description',
            name: 'description',
            editable: true,
            width: 200
          },
          {
            label : 'Row',
            name: 'totalRow',
            width: 50,
            editable: true
          },
          {
            label: 'Column',
            name: 'totalColumn',
            width: 50,
            editable: true
          },
          {
            label: 'Capacity',
            name: 'capacity',
            width: 50,
            editable: true
          },
          {
            label: 'Room Type',
            name: 'roomType',
            editable: true,
            width: 100, align: 'center', formatter: 'select',
            edittype: 'select',
            editoptions: {
              value: '1:Theory;2:Sessional;0:Others',
              defaultValue: 'Theory'
            },
            stype: 'select',
            searchoptions: {
              sopt: ['eq', 'ne'],
              value: '1:Theory;2:Sessional;0:Others'
            }},


          {
            label: 'Dept./School',
            name: 'dept_id',
            editable: true,
            width: 100, align: 'center', formatter: 'select',
            edittype: 'select',
            editoptions: {
              value: appConstants.dept4JqGridSelectBox,
              defaultValue: 'None'
            },
            stype: 'select',
            searchoptions: {
              sopt: ['eq', 'ne'],
              value:appConstants.dept4JqGridSelectBox
            }
          },
          {
            label: 'Seat Plan',
            name: 'examSeatPlan',
            editable: true,
            width: 80, align: 'center', formatter: 'checkbox',
            edittype: 'checkbox', editoptions: {value: '1:0', defaultValue: '1'},
            stype: 'select',
            searchoptions: {
              sopt: ['eq', 'ne'],
              value: '1:Yes;0:No'
            }
          },
          {
            label: "Edit Actions",
            name: "actions",
            width: 100,
            formatter: "actions",
            formatoptions: {
              keys: true,
              mtype: "PUT",
              delOptions: {
                mtype: 'DELETE',
                onclickSubmit: function(rp_ge) {
                  var selrow_id = thisGrid.getGridParam('selrow');
                  var rowdata = thisGrid.getRowData(selrow_id);
                  rp_ge.url = "https://localhost/ums-webservice-common/academic/classroom" + '/' + selrow_id ;
                },
                ajaxDelOptions: {
                  contentType: "application/json"  ,
                  beforeSend: function(jqXHR) {
                    jqXHR.setRequestHeader("X-Authorization", 'Basic ZHByZWdpc3RyYXI6MTMwOTc3YzUtNzgyZC00NDA5LWIzNTUtZDdhYzZkYWQ1ODYx');
                  }
                },
                serializeDelData: function(postdata) {
                  return JSON.stringify(postdata);
                }
              },
              onEdit: function (id) {
                if (typeof (lastSel) !== "undefined" && id !== lastSel) {
                  cancelEditing(thisGrid);
                }
                lastSel = id;
                $("#jqGrid").setGridParam({ editurl: "https://localhost/ums-webservice-common/academic/classroom/" + encodeURIComponent(id)});
              }

            }
          }
        ],
        sortname: 'id',
        loadonce: true,
        autowidth: true,
        pager: "#jqGridPager",
        rownumbers: true,
        height:500,

        rowList: [],        // disable page size dropdown
        pgbuttons: false,     // disable page control like next, back button
        pgtext: null,

        ondblClickRow: function(id, ri, ci,e) {
          if (typeof (lastSel) !== "undefined" && id !== lastSel) {
            cancelEditing($(this));
          }
          lastSel = id;
          var lrid = $.jgrid.jqID(lastSel);
          if (!e) e = window.event; // get browser independent object
          var element = e.target || e.srcElement;


          $("#jqGrid").jqGrid('editRow',id,true,function() {
            var colModel = jQuery("#jqGrid").jqGrid ('getGridParam', 'colModel');
            var colName = colModel[ci].name;
            var input = $('#' + id + '_' + colName);
            console.log(input)
            setTimeout(function(){  input.get(0).focus(); }, 300);
          },null,"https://localhost/ums-webservice-common/academic/classroom/"+ encodeURIComponent(id));
          $("tr#" + lrid + " div.ui-inline-edit, " + "tr#" + lrid + " div.ui-inline-del").hide();
          $("tr#" + lrid + " div.ui-inline-save, " + "tr#" + lrid + " div.ui-inline-cancel").show();
        }


        });


      var addOptions = {
        keys: true,
        mtype: "POST",
        url: "https://localhost/ums-webservice-common/academic/classroom",
        successfunc: function () {
          //Fire hoy na
          var $self = $(this);
          setTimeout(function () {
            alert("abc");
            $self.trigger("reloadGrid");
            alert("def");
          }, 50);
        },
        oneditfunc: function(){
          hideDelIcon("empty");
        }

      };
      $("#jqGrid").jqGrid("inlineNav", "#jqGridPager", {
        addParams: {
          position: "last",
          rowID: 'empty',
          useDefValues: true,
          addRowParams: addOptions
        }
      });

    }

  }
  UMS.controller('ClassRoomInfo', ClassRoomInfo);
}

