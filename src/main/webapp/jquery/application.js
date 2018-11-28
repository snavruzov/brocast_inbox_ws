$(function () {
    "use strict";

    var content = $('#content');
    var input = $('#input');
    var status = $('#status');
    var myName = false;
    var author = null;
    var logged = false;
    var socket = $.atmosphere;
    var request = { url: document.location.toString() + 'inbox/2723d092b63885e0d7c260cc007e8b9d',
        contentType : "application/json",
        logLevel : 'debug',
        transport : 'websocket' ,
        trackMessageLength : true,
        fallbackTransport: 'long-polling'};


    request.onOpen = function(response) {
        content.html($('<p>', { text: 'Atmosphere connected using ' + response.transport }));
        input.removeAttr('disabled').focus();
        status.text('Choose name:');
    };

    request.onMessage = function (response) {
        var message = response.responseBody;
        try {
            var json = jQuery.parseJSON(message);
        } catch (e) {
            console.log('This doesn\'t look like a valid JSON: ', message);
            return;
        }

        input.removeAttr('disabled').focus();
        {
            var me = json.username == username;
            var date =  typeof(json.dateadded) == 'string' ? parseInt(json.dateadded) : json.dateadded;
            addMessage(json.text, me ? 'blue' : 'black', new Date(date));
        }
    };

    request.onClose = function(response) {
        logged = false;
    };

    request.onError = function(response) {
        content.html($('<p>', { text: 'Sorry, but there\'s some problem with your '
            + 'socket or the server is down' }));
    };

    socket.subscribe(request);

    function addMessage(message, color, datetime) {
        content.append('<p><span style="color:' + color + '">' + message + '</p>');
    }
});