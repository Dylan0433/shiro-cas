
mimo = {};

mimo.select = function() {

	var selectItems = [];

	$(':checkbox[name="items"][checked]').each(function() {
		selectItems.push($(this).val());
	});

	return selectItems;
};

mimo.selectAll = function() {
	$(':checkbox[name="items"]').attr('checked', true);
};

mimo.unselectAll = function() {
	$(':checkbox[name="items"]').attr('checked', false);
};

mimo.search = function() {
	$('#myForm').attr("method", "get").submit();
};

mimo.jumpPage = function(pageNo){
	$("#pageNo").val(pageNo);
	$("#myForm").attr("method", "get");
	$("#myForm").submit();
};

mimo.formatDate = function(dateAsLong){
	 var date = new Date(parseInt(dateAsLong, 10));
	 var year = date.getFullYear();
	 var month = date.getMonth() + 1;
	 month = month >= 10 ? month.toString() : ("0" + month);
	 var day = date.getDate() >= 10 ? date.getDate() : ("0" + date.getDate());
	 var hours = date.getHours() >= 10 ? date.getHours().toString() : ("0" + date.getHours());
	 var minutes = date.getMinutes() >= 10 ? date.getMinutes().toString() : ("0" + date.getMinutes());
	 var seconds = date.getSeconds() >= 10 ? date.getSeconds().toString() : ("0" + date.getSeconds());
	 return "".concat(year, "-", month, "-", day, " ", hours, ":", minutes, ":", seconds);
};

var kbytes = 1024;
var mbytes = 1024 * 1024;
var gbytes = 1024 * 1024 * 1024;
mimo.formatFileSize = function(fileSize){
	if(fileSize > gbytes){
		return mimo.round((fileSize / gbytes),1) + "G"; 
	}
	
	if(fileSize > mbytes){
		return mimo.round((fileSize / mbytes),1) + "M"; 
	}
	
	if(fileSize > kbytes){
		return mimo.round((fileSize / kbytes),1) + "K"; 
	}
	
	return fileSize + "bytes";
};

mimo.round = function(val, scale){
	var valAsString = val.toString();
	var scaleOnVal = 0;
	try{
		scaleOnVal = valAsString.split(".")[1].length;
	}catch(e){}
	if(scaleOnVal === 0){
		return valAsString;
	}
	
	if(scale >= scaleOnVal){
		return valAsString;
	}
	var length = valAsString.length;
	return valAsString.slice(0, length-(scaleOnVal - scale));
};

$(function() {
	$("#select_all").click(function() {
		mimo.selectAll();
	});
	
	$("#unselect_all").click(function() {
		mimo.unselectAll();
	});
	
	$("#search").click(function() {
		mimo.search();
	});
	
	$("#clear").click(function(){
		$("#keyword").val("").focus();
		return false;
	});
	
	 $(".date").each(function(){
		 var $this = $(this);
		 var lastModified = $this.text();
 		 $this.empty().append(mimo.formatDate(lastModified));
	  });
	 
	 $(".fileSize").each(function(){
		 var $this = $(this);
		 var fileSize = $this.text();
 		 $this.empty().append(mimo.formatFileSize(fileSize));
	 });
});