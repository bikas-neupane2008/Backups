// Window onLoad listener
window.addEventListener("load", function (e) {
    // Wait until DOM is loaded
    alert("DOM has loaded!");
    document.getElementById("myform").addEventListener("submit", validate);
});


// Add an error message to the #errors element
function addError(message) {
    var p = document.createElement("p");
    var text = document.createTextNode(message);
    p.appendChild(text);
    document.getElementById("errors").appendChild(p);
}


// Clear all error messages from the #errors element
function clearErrors(){
    document.getElementById("errors").innerHTML = "";
}


// Validate the form, returning true if valid, false otherwise
function validate(e) {
    e.preventDefault();
    clearErrors();
    var success = true;
    var form = document.getElementById("myform");
    var firstName = form.elements["first"].value;
    if (firstName.length === 0){
        addError("Please enter your first name");
        success=false;
    }else if (!/^[a-zA-Z]+$/.test(firstName)){
        addError("First name should always be appropriate without invalid characters.");
        success=false;
    }

    var lastName = form.elements["last"].value;
    if (lastName.length === 0){
        addError("Please enter your last name");
        success=false;
    }else if (!/^[a-zA-Z]+$/.test(lastName)){
        addError("Last name should always be appropriate without invalid characters.");
        success=false;
    }

    var email = form.elements["email"].value;
    if (!/^[a-z]+(|[1-9][0-9]*)@(une|myune)[.]edu[.]au$/.test(email)) {
        addError("Email should always be appropriate without invalid characters.");
        success = false;
    }

    var mobile = form.elements["mobile"].value;
    if (mobile.length !== 10) {
        addError("Mobile should always be of 10 digit.");
        success = false;
    } else if (!/^[0-9]+$/.test(mobile)) {
        addError("Mobile should always be of numeric.");
        success = false;
    } else if (!/^04/.test(mobile)) {
        addError("Mobile format should always be from 04XXXXXXXX.");
        success = false;
    }

    var degreeType = form.elements["type"].value;
    if (degreeType === "none") {
        addError("Please select your degree type.");
        success = false;
    }

    var modeOn = document.getElementById("mode_on");
    var modeOff = document.getElementById("mode_off");
    if (!modeOn.checked && !modeOff.checked) {
        addError("Please choose your study mode.");
        success = false;
    }

    if (success) {
        alert("The information you provided is valid.");
    }
    return success;
}
