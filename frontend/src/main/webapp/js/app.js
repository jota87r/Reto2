var App = {};

App.getTopics = function() {
  
}

App.sendMessage = function() {
  
}

$(document).ready(function() {
  $('#test').click(function() {
    $.ajax({
        url: "http://localhost:8080/backend/topics"
    }).then(function(data) {
      console.log(data);
    })
  });
});