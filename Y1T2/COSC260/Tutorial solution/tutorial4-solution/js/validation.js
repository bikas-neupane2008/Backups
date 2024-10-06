window.addEventListener("load", function (e) {
    document.getElementById("myform").addEventListener("submit", validate);
});

function clearErrors() {
    document.getElementById("errors").innerHTML = "";
}

function addError(message) {
    var p = document.createElement("p");
    var text = document.createTextNode(message);
    p.appendChild(text);
    document.getElementById("errors").appendChild(p);
}

function validate(e) {
    e.preventDefault();
    clearErrors();

    var success = true;
    var form = document.getElementById("myform");

    // validate first name
    var first = form.elements["first"].value;
    if (first.length === 0) {
        addError("You must enter a first name.");
        success = false;
    } else if (!/^[a-zA-Z]+$/.test(first)) {
        addError("Your first name contains invalid characters.")
        success = false;
    }

    // validate last name
    var last = form.elements["last"].value;
    if (last.length === 0) {
        addError("You must enter a last name.");
        success = false;
    } else if (!/^[a-zA-Z]+$/.test(last)) {
        addError("Your last name contains invalid characters.")
        success = false;
    }

    // validate email
    var email = form.elements["email"].value;
    if (!/^[a-z]+(|[1-9][0-9]*)@(une|myune)[.]edu[.]au$/.test(email)) {
        addError("You must enter your student email address.");
        success = false;
    }

    // validate mobile
    var mobile = form.elements["mobile"].value;
    if (mobile.length !== 10) {
        addError("Your mobile number must be 10 digits long.");
        success = false;
    } else if (!/^[0-9]+$/.test(mobile)) {
        addError("Your mobile number must only contain the digits 0-9.");
        success = false;
    } else if (!/^04/.test(mobile)) {
        addError("Your mobile number must begin with 04.");
        success = false;
    }

    // validate type
    var type = form.elements["type"].value;
    if (type === "none") {
        addError("Your must select a degree type.");
        success = false;
    }

    // validate study mode
    var modeOn = document.getElementById("mode_on");
    var modeOff = document.getElementById("mode_off");
    if (!modeOn.checked && !modeOff.checked) {
        addError("You must select a study mode.");
        success = false;
    }

    if (success) {
        alert("The information you provided is valid.");
    }

    return success;
}
