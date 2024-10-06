// Wait until the DOM is loaded, then grab a list of photos
$(function(){
  loadPhotos();
});


// Retrieve a list of photos and append them to the #student_photos element
function loadPhotos(){

  $.ajax({
    url: "http://turing.une.edu.au/~jbisho23/tutorial6/images.php",
    dataType: "json",
    success: function(data){
      
      // Add all images to the #student_photos <div>
      data.forEach(function(img_src){
        $('<img>').attr('src', img_src).insertAfter("#student_photos > .button_left");
      });

      // Hide all except the first
      $("#student_photos > img").not(":first").addClass('photo_hidden');

      // Make the first photo visible
      $("#student_photos > img:first").addClass('photo_visible');

      // Disable the left arrow since the first is shown
      $("#student_photos > .button_left").addClass('button_disabled');

      // If we made it this far our photos have loaded!
      
      // Initialise the left/right photo buttons
      initPhotoButtons();

    }
  });
}


// Initialise the left/right photo buttons
function initPhotoButtons(){

  // Click handler for the right-arrow button
  $('.button_left').click(function(){
    $currentImage = $('#student_photos > img.photo_visible');
    $prevImage = $currentImage.prev();

    if (!$prevImage.hasClass('button')){
      $currentImage.removeClass('photo_visible');
      $currentImage.addClass('photo_hidden');

      $prevImage.removeClass('photo_hidden');
      $prevImage.addClass('photo_visible');

      if ($prevImage.prev().hasClass('button')){
        $('#student_photos > .button_left').addClass('button_disabled');
      }
      $('#student_photos > .button_right').removeClass('button_disabled');

    }

  });

  // Click handler for the right-arrow button
  $('.button_right').click(function(){
    $currentImage = $('#student_photos > img.photo_visible');
    $nextImage = $currentImage.next();

    if (!$nextImage.hasClass('button')){
      $currentImage.removeClass('photo_visible');
      $currentImage.addClass('photo_hidden');

      $nextImage.removeClass('photo_hidden');
      $nextImage.addClass('photo_visible');

      if ($nextImage.next().hasClass('button')){
        $('#student_photos > .button_right').addClass('button_disabled');
      }
      $('#student_photos > .button_left').removeClass('button_disabled');
    }
  });


}
