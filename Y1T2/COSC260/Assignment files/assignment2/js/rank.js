// Setting a global variable for the animation speed (in milliseconds).
var speed = 0.5 * 1000;

$(document).ready(function () {
    // Get the height of .rankElement and convert it to a number.
    var distance = $(".rankElement").css("height");
    distance = parseFloat(distance.substr(0, (distance.length - 2))) + 18;

    // Initializing arrays to hold rank IDs, corresponding positions and positions' array for each rank.
    var ranksIds = new Array();
    var idsRanks = new Array();
    var idsPoss = new Array();

    // Initializing arrays with their respective data.
    for (var i = 0; i < $(".rankElement").length; i++) {
        ranksIds[i] = i;
        idsRanks[i] = i;
        idsPoss[i] = 0;
    }

    // Attach click events for both the 'up' and 'down' buttons.
    $(".up").on("click", function () { rankChange(1, $(this).parent(), distance, ranksIds, idsRanks, idsPoss) });
    $(".down").on("click", function () { rankChange(-1, $(this).parent(), distance, ranksIds, idsRanks, idsPoss) });
});

/**
 * Function to change the rank of an element either upwards or downwards.
 * @param {number} dir - The direction of change. 1 for up and -1 for down.
 * @param {object} element - The jQuery object representing the element whose rank is to be changed.
 * @param {number} distance - The distance (in pixels) by which the element needs to be moved.
 * @param {Array} ranksIds - An array to map the rank ID to its position.
 * @param {Array} idsRanks - An array to map the rank position to its ID.
 * @param {Array} idsPoss - An array to store the top position of each rank.
 */
function rankChange(dir, element, distance, ranksIds, idsRanks, idsPoss) {
    // Extract the numerical ID from the element's ID attribute.
    var id = $(element).attr("id");
    id = parseFloat(id.substring(2, id.length));
    var idDisp = parseFloat(ranksIds[idsRanks[id] - dir]);

    // Calculate the effective distance to be moved based on the direction.
    distance = distance * dir;

    // Check the boundary conditions to ensure the elements can be moved up or down.
    if (((idsRanks[id] < (ranksIds.length - 1)) && (dir == -1)) || ((idsRanks[id] > 0) && (dir == 1))) {

        // Update the arrays to reflect the rank change.
        idsRanks[id] = idsRanks[id] - dir;
        ranksIds[idsRanks[id]] = id;
        idsPoss[id] = idsPoss[id] - distance;

        idsRanks[idDisp] = idsRanks[id] + dir;
        ranksIds[idsRanks[id] + dir] = idDisp;
        idsPoss[idDisp] = idsPoss[idDisp] + distance;

        // Update the value attribute for the corresponding input elements.
        $("input[name='" + id + "']").attr("value", idsRanks[id]);
        $("input[name='" + idDisp + "']").attr("value", idsRanks[idDisp]);

        // Update the displayed rank on the webpage.
        $("font.rankDisp#el" + id).html(idsRanks[id] + 1);
        $("font.rankDisp#el" + idDisp).html(idsRanks[idDisp] + 1);

        // Animate the rank change.
        $(".rankElement#el" + id).animate({ top: idsPoss[id] + "px" }, speed);
        $(".rankElement#el" + idDisp).animate({ top: idsPoss[idDisp] + "px" }, speed);
    }
}
