// Attaching an event listener to the window to call 'init' function on page load
window.addEventListener("load", init);

// Initialization function to attach a submit event listener to the geekTestForm
function init() {
    document.getElementById("geekTestForm").addEventListener("submit", geekTestScoreCalculator);
}

// Function to calculate the geek test score
function geekTestScoreCalculator(e) {
    // Preventing form submission if inputs are not valid
    if (!validateInputs()) {
        e.preventDefault();
        return;
    }

    e.preventDefault();

    let score = 0;
    // Calculating score based on different criteria
    score += scoreForGlasses();
    score += scoreForDrink();
    score += scoreForOuting();
    score += scoreForAttributes();
    score += scoreForShows();

    // Function to animate the screen based on the calculated score (not defined in the given code)
    animateScreen(score);
}

// Function to calculate score based on glasses selection
function scoreForGlasses() {
    return $("#glasses_yes").is(":checked") ? 10 : 0;
}

// Function to calculate score based on drink selection
function scoreForDrink() {
    // Mapping of drink choices to their respective scores
    const DRINK_SCORES = {
        "blue_v": 10,
        "coffee": 5,
        "tea": 3
    };
    return DRINK_SCORES[$("select[name='drink']").val()] || 0;
}

// Function to calculate score based on outing selection
function scoreForOuting() {
    // Mapping of outing choices to their respective scores
    const OUTING_SCORES = {
        "star_wars": 9,
        "apple_store": 7,
        "subway_meal": 5,
        "cinema": 1
    };
    return OUTING_SCORES[$("select[name='outing']").val()] || 0;
}

// Function to calculate score based on attribute rankings
function scoreForAttributes() {
    // Defining score values for different attributes
    const attributeValues = [5, 3, 1, 0.5, 0];
    let attributeScore = 0;

    // Iterating over attribute values to calculate total score
    attributeValues.forEach((value, index) => {
        let rank = $(`input[name='${index}']`).val() ? parseInt($(`input[name='${index}']`).val()) : 0;
        attributeScore += value * (5 - rank);
    });

    return attributeScore;
}

// Function to calculate score based on selected shows
function scoreForShows() {
    // List of possible shows
    const SHOWS = ["big_bang_theory", "mandalorian", "battlestar", "breaking_bad", "cosc120", "last_of_us"];
    return SHOWS.reduce((accumulatedScore, show) => {
        return accumulatedScore + ($("#" + show).is(":checked") ? 5 : 0);
    }, 0);
}

// Function to validate all the form inputs
function validateInputs() {
    // Check if any of the glasses options is selected
    if (!$('#glasses_yes').is(':checked') && !$('#glasses_no').is(':checked')) {
        alert('Please select an option for glasses.');
        return false;
    }

    // Check if a drink option is selected
    if ($("select[name='drink']").val() === '') {
        alert('Please select a drink.');
        return false;
    }

    // Check if an outing option is selected
    if ($("select[name='outing']").val() === '') {
        alert('Please select an outing option.');
        return false;
    }

    // Check if all attribute rankings are filled
    for (let i = 0; i < 5; i++) {
        if ($(`input[name='${i}']`).val() === '') {
            alert('Please fill out all attribute rankings.');
            return false;
        }
    }

    // Check if at least one show is selected
    const SHOWS = ["big_bang_theory", "mandalorian", "battlestar", "breaking_bad", "cosc120", "last_of_us"];
    const anyShowChecked = SHOWS.some(show => $("#" + show).is(":checked"));
    if (!anyShowChecked) {
        alert('Please select at least one show.');
        return false;
    }

    return true;
}
