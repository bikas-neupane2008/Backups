/* On DOM Load, update the information in the right hand column */
$(function(){
    /* code here runs when DOM is loaded */
  updateTimetable();
  updateStaff();
})


/**
 * Add a <li> list item to the specified HTML element
 *
 * @param {string} selector jQuery selector for the list element, e.g. <ul>, <ol>
 * @param {string} text The text to be inserted
 */
function addListItem(selector, text){
  $(selector).append($("<li>").text(text));
}


/*
 * Perform an AJAX request to get timetable information.
 * Update the timetable information in the right hand column in the callback. 
 */

function updateTimetable(){
  $.ajax({
    url: "http://turing.une.edu.au/~jbisho23/tutorial6/timetable.php",
    dataType: "json",
    success: function(data){
     
      // use .forEach when looping over a JS array
      data.lectures.forEach(function(time){
        addListItem($("#lecture_timetable"), time);
      });

      data.tutorials.forEach(function(time){
        addListItem("#tutorial_timetable", time);
      });

      $("#timetable_button").click();
    }

  });
}



/*
 * Perform an AJAX request to get information on the teaching staff.
 * Update the teaching staff information in the right hand column in the callback. 
 */

function updateStaff(){

  $.ajax({
    url: "http://turing.une.edu.au/~jbisho23/tutorial6/staff.php",
    dataType: "json",
    success: function(data){

      addListItem("#coordinator_info", data.coordinator.name);
      addListItem("#coordinator_info", data.coordinator.email);
      addListItem("#coordinator_info", data.coordinator.phone);
      
      addListItem("#lecturer_info", data.lecturer.name);
      addListItem("#lecturer_info", data.lecturer.email);
      addListItem("#lecturer_info", data.lecturer.phone);
      
      addListItem("#tutorials_info", data.tutorials.name);
      addListItem("#tutorials_info", data.tutorials.email);
      addListItem("#tutorials_info", data.tutorials.phone);

      $("#staff_button").click();
      
    }
  });
}
