$.jgrid.defaults.responsive = true;
$.jgrid.defaults.styleUI = 'Bootstrap';
$.extend($.jgrid.inlineEdit, { restoreAfterError: false });

$.extend($.jgrid.defaults, {
    ajaxRowOptions: { contentType: "application/json", async: true,
        beforeSend: function (jqXHR, settings) {
            jqXHR.setRequestHeader("Authorization", 'Basic ZHByZWdpc3RyYXI6MTIzNDU=');
            jqXHR.setRequestHeader("If-Match", '*');
        },
        complete: function(res, stat) {
            if (res.status==200 || res.status==204 ||  res.status==201) {
                $("#jqGrid").jqGrid('setGridParam',{datatype:'json'}).trigger('reloadGrid');
                return [true,"",''];
            } else {
                $("#load_jqGrid").hide();
                return [false, res.responseText ];
            }
        }
    },
    serializeRowData: function (data) {
        var propertyName, propertyValue, dataToSend = {};
        for (propertyName in data) {
            if (data.hasOwnProperty(propertyName)) {
                propertyValue = data[propertyName];
                if ($.isFunction(propertyValue)) {
                    dataToSend[propertyName] = propertyValue();
                } else {
                    dataToSend[propertyName] = propertyValue;
                }
            }
        }
        return JSON.stringify(dataToSend);
    }
});