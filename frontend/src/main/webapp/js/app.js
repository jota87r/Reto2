
var App = {};

App.topicList = [];
App.topicSelect;
App.topicSelected;

App.loadTopics = function() {
  $.ajax({
    url: "http://localhost:8080/backend/topics"
  }).then(function (data) {
    var option;
    data.forEach(function(val) {
      App.topicList = val;
      option = document.createElement('option');
      option.innerHTML = val;
      option.value = val;
      App.topicSelect.appendChild(option);
    });
    App.updateMessageList();
    App.loadMessages();
  });
};

App.loadMessages = function() {
  $.ajax({
    url: "http://localhost:8080/backend/topics/" + App.topicSelected
  }).then(function (data) {
    data.forEach(function(val) {
      console.log(val);
    });
  });
};

App.updateMessageList = function() {
  App.topicSelected = App.topicSelect.value;
  document.getElementById('selectedTopic').innerHTML = App.topicSelected;
};

App.init = function() {
  App.topicSelect = document.getElementById('topics');
  App.topicSelect.addEventListener('change', function(e) {App.updateMessageList()});
};

$(document).ready(function () {
  App.init();
  App.loadTopics();
});
