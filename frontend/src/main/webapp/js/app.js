
var App = {};

App.topicList = [];
App.topicSelect;
App.topicSelected;
App.message;

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
      var messages = document.getElementById('messages');
      var message = document.createElement('label');
      message.innerHTML = val.text;
      var tr = document.createElement('tr');
      var td = document.createElement('td');
      td.appendChild(message);
      tr.appendChild(td);
      messages.appendChild(tr);
    });
  });
};

App.updateMessageList = function() {
  App.topicSelected = App.topicSelect.value;
  document.getElementById('selectedTopic').innerHTML = App.topicSelected;
};

App.sendMessage = function() {
  $.ajax({
    url: "http://localhost:8080/backend/messages",
    method: 'PUT',
//    data: '{queue: ' + '"' + App.topicSelected + '"' + ', text: ' + '"' + App.message.value + '"' + '}',
    dataType: 'json',
//    data: {queue: App.topicSelected, text: App.message.value}
    data: {queue: "App.topicSelected", text: "App.message.value"},
    contentType: 'application/json',
    processData: false
  }).done(function() {console.log("asdf");});
}

App.init = function() {
  App.topicSelect = document.getElementById('topics');
  App.message = document.getElementById('message');
  App.topicSelect.addEventListener('change', function(e) {App.updateMessageList()});
  document.getElementById('send').addEventListener('click', App.sendMessage);
};

$(document).ready(function () {
  App.init();
  App.loadTopics();
});
