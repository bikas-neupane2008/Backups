// enter the URL of your web server!
var url = "php/register.php";

$(function () {
  $('#registration').submit(function (e) {
    e.preventDefault();
    send_request();
  });
});


// send POST request with all form data to specified URL
function send_request() {
  // remove messages
  remove_msg();

  // make request
  $.ajax({
    url: url,
    method: 'POST',
    data: $('#registration').serialize(),
    dataType: 'json',
    success: function (data) {
      // log temporary password to console
      console.log('password : ' + data.password);

      // display user_id on page
      $('#server_response').addClass('success');
      $('#server_response span').html('Congratulations!<br>Your temporary password is : ' + data.password);
    },
    error: function (jqXHR) {
      // Log the raw response to the console
      console.log("Raw server response:", jqXHR.responseText);
      // parse JSON
      try {
        var $e = JSON.parse(jqXHR.responseText);

        // log error to console
        console.log('Error from server: ' + $e.error);

        // display error on page
        $('#server_response').addClass('error');
        $('#server_response span').text('Error from server: ' + $e.code + ' ' + $e.status + ' : ' + $e.error);
      }
      catch (error) {
        console.log('Could not parse JSON error message: ' + error);
      }
    }
  });
}


// remove all messages displayed on page
function remove_msg() {
  var $server_response = $('#server_response');
  if ($server_response.hasClass('success')) {
    $server_response.removeClass('success');
  }
  else if ($server_response.hasClass('error')) {
    $server_response.removeClass('error');
  }
  $('#server_response span').text('');
}