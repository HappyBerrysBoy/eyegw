function ajaxGet(url, callback, errorCallback) {
  $.ajax({
    type: "GET",
    url: url,
    jsonp: "",
    success: function(json) {
    	callback(json);
    },
    error:function(error){
    	if(errorCallback){ 
    		errorCallback(error);
    	}else{ 
    		alert("Fail to load Serverlist.")
    	}
    }
  });
}

//function requestApi(url, param) {
//  $.jsonp({
//    url: url + "?callback=?",
//    data: param,
//    success: function(data) {
//      // handle user profile here
//      debugger;
//      console.log(data);
//    },
//    error: function(d, msg) {
//      debugger;
//      console.log(d, msg);
//    }
//  });
//}
//
//function jsonp(url, param) {
//  $.ajax({
//    url: url,
//    method: "POST",
//    dataType: "jsonp",
//    jsonp: "callback",
//    data: param,
//    success: function(data, textStatus, jqXHR) {
//      console.log("success.");
//    },
//    error: function(jqXHR, textStatus, errorThrown) {
//      console.log("error: ", jqXHR);
//    }
//  });
//}
//
//function jsonp2(url, param) {
//  $.ajax({
//    url: url,
//    type: "POST",
//    crossDomain: true,
//    data: param,
//    dataType: "jsonp",
//    success: function(result) {
//      alert(JSON.stringify(result));
//    },
//    error: function(xhr, status, error) {
//      alert(status);
//    }
//  });
//}
//
//function jsonp3(url, param) {
//  $.ajax({
//    type: "GET",
//    dataType: "jsonp",
//    data: param,
//    url: url,
//    jsonp: "",
//    success: function(json) {
//      $(".result").html(json.data.name);
//      alert(json);
//    }
//  });
//}
