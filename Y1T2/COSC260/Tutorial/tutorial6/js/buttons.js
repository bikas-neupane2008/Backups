// jQuery onLoad: this will run once the DOM has been loaded
// This is a jQuery shortcut for document.onload()
$(function(){
    initButtonListeners()
});


// Setup the onclick event listeners for the buttons
function initButtonListeners() {

  // find all objects with button clss
  var $buttons = $('.button_up, .button_down');

  // loop over each button, accessed using the variable 'this'
  $buttons.each(function() {

    // create the function that runs when this element is clicked on.
    $(this).click(function() {
      // toggle button
      toggleButton($(this))
      // show/hide the text in the next sibling with 500ms animation
      $(this).next().toggle(500);
    });

  });
}


/*
 Show an up or down arrow depending on current state
 Calling this will toggle up/down state

 @param button jQuery object containing a button
*/
function toggleButton(button){
  if (button.hasClass('button_down')){
    button.removeClass('button_down');
    button.addClass('button_up');
  }
  else if (button.hasClass('button_up')){
    button.removeClass('button_up');
    button.addClass('button_down');
  }
}
