/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var RestClient = {};

RestClient.request = function () {
  var result = null;
  if (window.XMLHttpRequest) {
    result = new XMLHttpRequest(); // FireFox, Safari, etc.
    if (typeof xmlhttp.overrideMimeType !== undefined) {
      result.overrideMimeType('application/json');
    }
  }
//  else if (window.ActiveXObject) { // MSIE
//    result = new ActiveXObject("Microsoft.XMLHTTP");
//  } else {
//    // No known mechanism -- consider aborting the application
//  }
  return result;
};

RestClient.get = function (url, callback) {
  var request = RestClient.request();
  request.onreadystatechange = function () {
    if (request.readyState !== 4)
      return; // Not there yet
    if (request.status !== 200) {
    }
    callback(request.responseText);
  };
  request.open('GET', url, true);
  request.send();
};

