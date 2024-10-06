// jQuery onLoad function
$(function(){
  // run the updateImage function every 5000 ms
  setInterval("updateImage()", 5000);
})


// switch the current image in the image carousel using a 1 second fade animation.
// The currently active image will have the 'active' class
function updateImage() {

  // select the active image
  var $active = $('#slide_img img.active');

  // select the next element
  var $next = $active.next();

  // selecting 'next' on the last element results in a zero length object,
  // so set next to the first element, looping around
  if ($next.length === 0){
    $next = $('#slide_img img:first');
  }

  // Fade out current image, remove the 'active' class
  $active.fadeTo(1000, 0.0, function(){
    $active.removeClass('active');
  });
 
  // Fade in next image and add the 'active' class
  $next.fadeTo(1000, 1.0, function(){
    $next.addClass('active');
  });


  // select the text for the current book
  var $active_text = $('#slide_info div.active');
  // fade out the current book text over 1 second, remove 'active' class, add 'inactive' class
  $active_text.fadeTo(1000, 0.0, function() {
    $active_text.removeClass('active');
  });

  // select the text for the next book, or the first if we need to loop around
  var $next_text = $active_text.next()
  if ($next_text.length === 0){
    $next_text = $('#slide_info div:first');
  }
  // fade in the next book text over 1 second, remove 'inactive' class, add 'active' class
  $next_text.fadeTo(1000, 1.0, function(){
    $next_text.addClass('active');
  });

}
