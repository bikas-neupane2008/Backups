$(function(){
  setInterval("updateFooter()", 1000);
})


function updateFooter(){
  $.ajax({
    url: "http://turing.une.edu.au/~jbisho23/time/time.php",
    dataType: "json",
    success: function(data){
      $('footer>p:last').html(data.date + " " + data.time);
    }
  })
}
