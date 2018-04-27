function FixTable(TableID, FixColumnNumber, width, height, ifPage) {  
    /// <summary>  
    ///     锁定表头和列  
    ///     <para> http://blog.csdn.net/SongYanJun2011 </para>  
    /// </summary>  
    /// <param name="TableID" type="String">  
    ///     要锁定的Table的ID  
    /// </param>  
    /// <param name="FixColumnNumber" type="Number">  
    ///     要锁定列的个数  
    /// </param>  
    /// <param name="width" type="Number">  
    ///     显示的宽度  
    /// </param>  
    /// <param name="height" type="Number">  
    ///     显示的高度  
    /// </param>  
    if ($("#" + TableID + "_tableLayout").length != 0) {  
        $("#" + TableID + "_tableLayout").before($("#" + TableID));  
        $("#" + TableID + "_tableLayout").empty();  
    }  
    else {
//        $("#" + TableID).after("<div id='" + TableID + "_tableLayout' style='overflow:hidden;height:" + height + "px; width:" + width + "px;'></div>");
        $("#" + TableID).after("<div id='" + TableID + "_tableLayout' style='overflow:hidden;height:" + height + "px;'></div>");
    }  
    $('<div id="' + TableID + '_tableFix"></div>'  
    + '<div id="' + TableID + '_tableHead"></div>'  
    + '<div id="' + TableID + '_tableColumn"></div>'  
    + '<div id="' + TableID + '_tableData"></div>').appendTo("#" + TableID + "_tableLayout");  
    var oldtable = $("#" + TableID);  
    var tableFixClone = oldtable.clone(true);  
    tableFixClone.attr("id", TableID + "_tableFixClone");  
    $("#" + TableID + "_tableFix").append(tableFixClone);  
    var tableHeadClone = oldtable.clone(true);  
    tableHeadClone.attr("id", TableID + "_tableHeadClone");  
    $("#" + TableID + "_tableHead").append(tableHeadClone);  
    var tableColumnClone = oldtable.clone(true);  
    tableColumnClone.attr("id", TableID + "_tableColumnClone");  
    $("#" + TableID + "_tableColumn").append(tableColumnClone);  
    $("#" + TableID + "_tableData").append(oldtable);  
    $("#" + TableID + "_tableLayout table").each(function () {  
        $(this).css("margin", "0");  
    });  
    var HeadHeight = $("#" + TableID + "_tableHead thead").height();  
    HeadHeight += 2;  
    $("#" + TableID + "_tableHead").css("height", HeadHeight);  
    $("#" + TableID + "_tableFix").css("height", HeadHeight);  
    var ColumnsWidth = 0;  
    var ColumnsNumber = 0;  
    $("#" + TableID + "_tableColumn tr:last td:lt(" + FixColumnNumber + ")").each(function () {  
        ColumnsWidth += $(this).outerWidth(true);  
        ColumnsNumber++;  
    });  
    ColumnsWidth += 2;  
    if ($.browser.msie) {  
        switch ($.browser.version) {  
            case "7.0":  
                if (ColumnsNumber >= 3) ColumnsWidth--;  
                break;  
            case "8.0":  
                if (ColumnsNumber >= 2) ColumnsWidth--;  
                break;  
        }  
    }  
    $("#" + TableID + "_tableColumn").css("width", ColumnsWidth);  
    $("#" + TableID + "_tableFix").css("width", ColumnsWidth);  
    $("#" + TableID + "_tableData").scroll(function () {  
        $("#" + TableID + "_tableHead").scrollLeft($("#" + TableID + "_tableData").scrollLeft());  
        $("#" + TableID + "_tableColumn").scrollTop($("#" + TableID + "_tableData").scrollTop());  
    });  
    $("#" + TableID + "_tableFix").css({ "overflow": "hidden", "position": "relative", "z-index": "50", "background-color": "Silver" });
    $("#" + TableID + "_tableColumn").css({ "overflow": "hidden", "height": height - 17, "position": "relative", "z-index": "40", "background-color": "Silver" });
    if (ifPage == "0") {
//        $("#" + TableID + "_tableHead").css({ "overflow": "hidden", "width": width - 17, "position": "relative", "z-index": "45", "background-color": "Silver" });
//        $("#" + TableID + "_tableData").css({ "overflow": "scroll", "width": width, "height": height, "position": "relative", "z-index": "35" });
        $("#" + TableID + "_tableHead").css({ "overflow": "hidden", "margin-right": "17px", "position": "relative", "z-index": "45", "background-color": "Silver" });
        $("#" + TableID + "_tableData").css({ "overflow": "scroll", "height": height, "position": "relative", "z-index": "35" });
    } else {
//        $("#" + TableID + "_tableHead").css({ "overflow": "hidden", "width": width, "position": "relative", "z-index": "45", "background-color": "Silver" });
//        $("#" + TableID + "_tableData").css({ "overflow-x": "scroll", "overflow-y": "hidden", "width": width, "height": height, "position": "relative", "z-index": "35" });
        $("#" + TableID + "_tableHead").css({ "overflow": "hidden", "position": "relative", "z-index": "45", "background-color": "Silver" });
        $("#" + TableID + "_tableData").css({ "overflow-x": "scroll", "overflow-y": "hidden", "height": height, "position": "relative", "z-index": "35" });
    }
//    if ($("#" + TableID + "_tableHead").width() > $("#" + TableID + "_tableFix table").width()) {  
//        $("#" + TableID + "_tableHead").css("width", $("#" + TableID + "_tableFix table").width());
//        if (ifPage == "0") {
//            $("#" + TableID + "_tableData").css("width", $("#" + TableID + "_tableFix table").width() + 17);
//        } else {
//            $("#" + TableID + "_tableData").css("width", $("#" + TableID + "_tableFix table").width());
//        }
//    }  
    if ($("#" + TableID + "_tableColumn").height() > $("#" + TableID + "_tableColumn table").height()) {  
        $("#" + TableID + "_tableColumn").css("height", $("#" + TableID + "_tableColumn table").height());  
        $("#" + TableID + "_tableData").css("height", $("#" + TableID + "_tableColumn table").height() + 17);  
    }  
    $("#" + TableID + "_tableFix").offset($("#" + TableID + "_tableLayout").offset());  
    $("#" + TableID + "_tableHead").offset($("#" + TableID + "_tableLayout").offset());  
    $("#" + TableID + "_tableColumn").offset($("#" + TableID + "_tableLayout").offset());  
    $("#" + TableID + "_tableData").offset($("#" + TableID + "_tableLayout").offset());  
}

function localeChange()
{
	var localeSelect = document.getElementById("localeId");
	var locale = localeSelect.options[localeSelect.selectedIndex].value;
	var url = getNoLocaleUrl(location.href) + "&locale="+locale;
	location.replace(url);
}

function getNoLocaleUrl(src)
{
	var index = src.indexOf("&locale");
	if(index != -1){
		var dst = src.substr(0,index);
		return dst;
	}
	return src;
}

//获取字典标签
function getDictChildList(arr,childId,parentId,childType){
	var sel2 = $("#"+childId);   
    sel2.empty();
    //sel2.append("<option value=''>请选择</option>");  
    $.ajax({
    	url:arr+"/sys/dict/listChildData",
        type: "get",
        async: false,
        data: {"parentId":parentId,
      	       "type":childType},
        dataType: "json",
        success: function (data) {
            var optionstring = "";
            optionstring += '<option value="" ></option>';
            for (var i = 0; i < data.length; i++) {
                optionstring += "<option value=\"" + data[i].value + "\" >" + data[i].label + "</option>";
            }
            sel2.html(optionstring);
            sel2.trigger("change");
        },
        error: function (msg) {
            alert(msg);
        }
    });
}

function getSelectListByAjax(url,sel,inputdata){
	$.ajax({
    	url: url,
        type: "get",
        async: false,
        data: inputdata,
        dataType: "json",
        success: function (data) {
            var optionstring = "<option value=\"\" ></option>";
            for (var i = 0; i < data.length; i++) {
                optionstring += "<option value=\"" + data[i].value + "\" >" + data[i].label + "</option>";
            }
            $("#"+sel).html(optionstring);
        },
        error: function (msg) {
            alert(msg);
        }
    });
}

//获取字典标签
/*function loadBussinessOp(arr,childId,obj){
	var sel2 = $(obj).parent().find($("input[name^='"+childId+"']"));   
    sel2.empty();
    $.ajax({
    	url: arr+"/sys/dict/loadBussinessOp",
        type: "post",
        async: false,
        data: {"parentId":'ddd'},
        dataType: "json",
        success: function (data) {
            var optionstring = "";
            for (var i = 0; i < data.length; i++) {
                optionstring += "<option value=\"" + data[i].businessOppNo + "\" >" + data[i].businessOppNo + "</option>";
            }
            sel2.html(optionstring);
        },
        error: function (msg) {
            alert(msg);
        }
    });
}
*/
