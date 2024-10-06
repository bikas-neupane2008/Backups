// Add an event listener that triggers when the entire page has fully loaded.
window.addEventListener("load", function (e) {
    // Once the page is loaded, attach an event listener to the registration form's submit action.
    document.getElementById("registrationForm").addEventListener("submit", validate);
});

// This function is triggered when the registration form is submitted.
function validate(e) {
    // Prevent the default behavior of the form (i.e., the form submission).
    e.preventDefault();

    // Initialize the 'valid' flag to true. It will be set to false if any validation fails.
    let valid = true;

    // Get the full name from the form.
    const fullName = this.elements["fullName"].value.trim();

    // Get the age input element and its value.
    const ageInput = this.querySelector('[name="age"]');
    const age = parseInt(ageInput.value, 10);

    // Get the email and phone input elements.
    const emailInput = this.querySelector('[name="email"]');
    const phoneInput = this.querySelector('[name="phone"]');

    // Validation for the full name field.
    if (!fullName) {
        alert("Full name cannot be empty! It is mandatory.");
        valid = false;
    } else if (fullName.length < 2 || fullName.length > 100 || !/^[a-zA-Z' -]+$/.test(fullName)) {
        alert("Invalid full name! Please enter your valid full name.");
        valid = false;
    }

    // Validation for the age field.
    else if (isNaN(age) || age < 18 || age > 130) {
        alert('Invalid age! Please enter an age between 18 and 130.');
        valid = false;
    }

    // Validation for the email field.
    else if (!emailInput.value.match(/^[a-zA-Z-]([\w-.]+)?@([\w-]+\.)+[\w]+$/)) {
        emailInput.setCustomValidity('Invalid email! Please enter a valid email address.');
        valid = false;
    }

    // Validation for the phone number field (optional, but if provided, should match the specified pattern).
    else if (phoneInput.value && !phoneInput.value.match(/^04\d{8}$/)) {
        alert('Invalid phone number! Please enter a valid phone number starting with "04" followed by 8 digits.');
        valid = false;
    }

    // If all validations passed (i.e., valid remains true), then display a success message and redirect to a2.html.
    if (valid) {
        alert('Congratulation! You have successfully registered.');
        window.location.href = 'a2.html';
    } else {
        // If any validation failed, prevent the form from submitting.
        e.preventDefault();
    }
}
