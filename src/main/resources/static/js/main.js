$('#produce-btn').click(function() {
    var mes=$('#message').val();
    var url="api/produce/" + mes;
    console.log("Calling: " + url);
    $('.response-body-amqp').html('Calling REST endpoint');
    if ( mes == "" ) {
        $('.response-body-amqp').html('Please enter a message.');
    } else {
        $.ajax({
            type: 'POST',
            url: url,
            success: function(){
                $('.response-body-amqp').html("ok");
            },
            error: function(xhr, status, error) {
                $('.response-body-amqp').html(
                    "status: " + status + "<br>" +
                    "error: " + error + "<br>" +
                    "xhr: " + "<pre>" + syntaxHighlight(xhr) + "</pre>"
                );
            },
        });
    }
});
$('#produceRandom-btn').click(function() {
    var url="api/produce/random/" + 10;
    console.log("Calling: " + url);
    $('.response-body-amqp').html('Calling REST endpoint');
    $.ajax({
        type: 'POST',
        url: url,
        success: function(){
            $('.response-body-amqp').html("ok");
        },
        error: function(xhr, status, error) {
            $('.response-body-amqp').html(
                "status: " + status + "<br>" +
                "error: " + error + "<br>" +
                "xhr: " + "<pre>" + syntaxHighlight(xhr) + "</pre>"
            );
        },
    });
});

$('#consume-btn').click(function() {
    var url="api/consume";
    console.log("Calling: " + url);
    $('.response-body-amqp').html('Calling REST endpoint');
    $.ajax({
        type: 'POST',
        url: url,
        success: function(result){
            var ret = "<table><tr><th>Message</th></tr>"
            ret += "<tr><td>"+result+"</td></tr>";
            ret += "</table>"
            $('.response-body-amqp').html(ret);
        },
        error: function(xhr, status, error) {
            $('.response-body-amqp').html(
                "status: " + status + "<br>" +
                "error: " + error + "<br>" +
                "xhr: " + "<pre>" + syntaxHighlight(xhr) + "</pre>"
            );
        },
        dataType: 'text'
    });
});
$('#consumeAll-btn').click(function() {
    var url="api/consume/*";
    console.log("Calling: " + url);
    $('.response-body-amqp').html('Calling REST endpoint');
    $.ajax({
        type: 'POST',
        url: url,
        success: function(result){
            var ret = "<table><tr><th>Message</th></tr>"
   	    if(0 == result.length) {
		ret += "<tr><td></td></tr>";
	    } else {
		for (i in result) {
		ret += "<tr><td>"+result[i]+"</td></tr>";
		}
	    }
            ret += "</table>"
            $('.response-body-amqp').html(ret);
        },
        error: function(xhr, status, error) {
            $('.response-body-amqp').html(
                "status: " + status + "<br>" +
                "error: " + error + "<br>" +
                "xhr: " + "<pre>" + syntaxHighlight(xhr) + "</pre>"
            );
        },
        dataType: 'json'
    });
});
$('#count-btn').click(function() {
    var url="api/count";
    console.log("Calling: " + url);
    $('.response-body-amqp').html('Calling REST endpoint');
    $.ajax({
        type: 'GET',
        url: url,
        success: function(result){
            $('.response-body-amqp').html(result);
        },
        error: function(xhr, status, error) {
            $('.response-body-amqp').html(
                "status: " + status + "<br>" +
                "error: " + error + "<br>" +
                "xhr: " + "<pre>" + syntaxHighlight(xhr) + "</pre>"
            );
        },
        dataType: 'json'
    });
});
//https://stackoverflow.com/a/7220510
function syntaxHighlight(json) {
    if (typeof json != 'string') {
         json = JSON.stringify(json, undefined, 2);
    }
    json = json.replace(/&/g, '&amp;').replace(/</g, '&lt;').replace(/>/g, '&gt;');
    return json.replace(/("(\\u[a-zA-Z0-9]{4}|\\[^u]|[^\\"])*"(\s*:)?|\b(true|false|null)\b|-?\d+(?:\.\d*)?(?:[eE][+\-]?\d+)?)/g, function (match) {
        var cls = 'number';
        if (/^"/.test(match)) {
            if (/:$/.test(match)) {
                cls = 'key';
            } else {
                cls = 'string';
            }
        } else if (/true|false/.test(match)) {
            cls = 'boolean';
        } else if (/null/.test(match)) {
            cls = 'null';
        }
        return '<span class="' + cls + '">' + match + '</span>';
    });
}
var localhost = window.location.hostname
var restResponse = 'curl -X POST "https://' + localhost + '/api/produce/{message}"<br>'
restResponse += 'curl -X POST "https://' + localhost + '/api/produce/random/{int}"<br>'
restResponse += 'curl -X POST "https://' + localhost + '/api/consume"<br>'
restResponse += 'curl -X POST "https://' + localhost + '/api/consume/*"<br>'
restResponse += 'curl -X POST "https://' + localhost + '/api/count"<br>'
$('#response-body-rest').html(restResponse);
