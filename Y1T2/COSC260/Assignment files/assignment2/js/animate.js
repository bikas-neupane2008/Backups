// Start the screen animation after a delay based on the score.
function animateScreen(score) {
  setTimeout(() => updateImage(score), 2000);
}

// Hide all visible containers in the current view.
function hideAllContainers() {
  document.getElementById('geekTestContainer').style.display = 'none';
  document.getElementById('animationContainer').style.display = 'none';
  document.getElementById('registrationContainer').style.display = 'none';
}

// Display the registration form after hiding all other containers.
function displayRegistrationForm() {
  hideAllContainers();
  document.getElementById('registrationContainer').style.display = 'block';
}

// Display the geek test form after hiding all other containers.
function displayTestForm() {
  hideAllContainers();
  document.getElementById('geekTestContainer').style.display = 'block';
}

// Initiate the animation sequence based on the provided score.
function displayAnimation(imageId, score) {
  hideAllContainers();
  document.getElementById('animationContainer').style.display = 'block';
  animateImages(imageId, score);
}

// Apply animation effects on images depending on the score range.
function animateImages(imageId, score) {
  var $active = $(`#${imageId} img.active`);

  if (score < 50) {
    // If score is below 50, slide the image down and then up.
    $active.slideDown(5000, function () {
      $active.slideUp(5000);
    });
  }
  else if (score >= 50 && score < 100) {
    // If score is between 50 and 100, fade the image in and then out.
    $active.fadeTo(5000, 1.0, function () {
      $active.fadeTo(5000, 0.0);
    });
  }
  else if (score === 100) {
    // If score is exactly 100, toggle the image's width and height.
    $active.animate({
      width: "toggle",
      height: "toggle"
    }, 5000, function () {
      $active.animate({
        width: "toggle",
        height: "toggle"
      }, 5000);
    });
  }
}

// Update the score display, message, and animation based on the provided score.
function updateImage(score) {
  if (isNaN(score)) {
    document.getElementById("scoreText").innerText = "Error calculating score!";
    return;
  }

  if (score < 50) {
    document.getElementById("scoreText").innerText = "Your geek score: " + score;
    document.getElementById("scoreText").style.color = 'red';
    document.getElementById("message").innerText = "Sorry! You are not geeky enough. You may try again.";
    displayAnimation('nge', score);
    setTimeout(displayTestForm, 10000);
  }
  else if (score >= 50 && score < 100) {
    document.getElementById("scoreText").innerText = "Your geek score: " + score;
    document.getElementById("scoreText").style.color = 'green';
    document.getElementById("message").innerText = "Welcome! to be a geek in SeekAGeek";
    displayAnimation('wg', score);
    setTimeout(displayRegistrationForm, 10000);
  }
  else if (score === 100) { // This condition should be '===' instead of '='
    document.getElementById("scoreText").innerText = "Your geek score: " + score;
    document.getElementById("scoreText").style.color = 'gold';
    document.getElementById("message").innerText = "Wow! you are a perfect geek in SeekAGeek";
    displayAnimation('sg', score);
    setTimeout(displayRegistrationForm, 10000);
  }
}
