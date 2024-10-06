<?php
header('Content-Type: application/json; charset=utf-8');
header('Access-Control-Allow-Origin: *');

$responses = [
    400 => "Bad Request",
    404 => "Not Found",
    405 => "Method Not Allowed",
    500 => "Internal server error"
];

try {
    validate_request_method();
    validate_input_fields(['username', 'fullName', 'email', 'dateOfBirth']);
    validate_username($_POST['username']);
    validate_full_name($_POST['fullName']);
    validate_email($_POST['email']);
    validate_dob($_POST['dateOfBirth']);

    $temp_password = generate_temp_password($_POST['username'], $_POST['dateOfBirth']);
    echo json_encode(["password" => $temp_password]);

    save_geek_to_database($_POST['username'], $_POST['fullName'], $_POST['email'], $_POST['dateOfBirth']);
} catch (Exception $e) {
    send_error($e->getCode(), $e->getMessage());
}

// A) Error response function 
function send_error($code, $message) {
    global $responses;
    header($_SERVER['SERVER_PROTOCOL'] . ' ' . $code . ' ' . $responses[$code]);
    echo json_encode(["code" => $code, "status" => $responses[$code], "error" => $message]);
    exit;
}

// B) Request validation
function validate_request_method() {
    if ($_SERVER['REQUEST_METHOD'] != "POST") {
        throw new Exception("Only POST requests are accepted.", 405);
    }
}

// C) Data validation for empty input
function validate_input_fields($fields) {
    // Mapping field names to their readable forms
    $fieldNamesMapping = [
        'username'     => 'Username',
        'fullName'     => 'Full Name',
        'email'        => 'Email',
        'dateOfBirth'  => 'Date of Birth'
    ];

    foreach ($fields as $field) {
        if (!isset($_POST[$field]) || empty($_POST[$field])) {
            $readableField = isset($fieldNamesMapping[$field]) ? $fieldNamesMapping[$field] : ucfirst($field);
            throw new Exception("$readableField not entered. All fields must be filled in.", 400);
        }
    }
}

// C) Data validation for username
function validate_username($username) {
    if (strlen($username) < 8 || strlen($username) > 20 || !preg_match('/[A-Z]/', $username) || !preg_match('/[0-9]/', $username) || !preg_match('/[~!@#$%^&*]/', $username)) {
        throw new Exception("Invalid username. Username must be 8-20 characters long and include atleast 1 uppercase, 1 number and 1 special character like (~!@#$%^&*).", 400);
    }
}

// C) Data validation for full name
function validate_full_name($fullName) {
    if (!preg_match("/^[a-zA-Z-' ]+$/", $fullName) || substr_count($fullName, ' ') !== 1) {
        throw new Exception("Invalid full name. Full Name must include 1 space between first name & last name; use A-Z, a-z, -, or ' only.", 400);
    }
}

// C) Data validation for email
function validate_email($email) {
    if (!filter_var($email, FILTER_VALIDATE_EMAIL)) {
        throw new Exception("Invalid email. Email must be in a standard email format.", 400);
    }
}

// C) Data validation for date of birth
function validate_dob($dateOfBirth) {
    if (!preg_match('/^\d{4}-\d{2}-\d{2}$/', $dateOfBirth)) {
        throw new Exception("Invalid date of birth. Date must be in yyyy-mm-dd format.", 400);
    }

    $inputDate = DateTime::createFromFormat('Y-m-d', $dateOfBirth);
    $today = new DateTime();

    if ($inputDate > $today) {
        throw new Exception("Invalid date of birth. It seems you are not born yet.", 400);
    }
}

// D) Generate Temporary password
function generate_temp_password($username, $dateOfBirth) {
    $geek_names = ["grogu", "heisenberg", "fowler", "newton", "sheldon", "haverchuck", "shepman", "fring", "dwight", "spock", "vanHouten", "hermione"];
    $chosen_name = $geek_names[array_rand($geek_names)];
    $dateOfBirthParts = explode("-", $dateOfBirth);
    $age = date("Y") - intval($dateOfBirthParts[0]);
    $randomized_username = str_shuffle($username);
    return $chosen_name . $randomized_username . $age;
}

// E) Geek information written in file AllGeeks.json
function save_geek_to_database($username, $fullName, $email, $dateOfBirth) {
    try {
        // Including the AllGeeks.php file
        require 'AllGeeks.php';

        // Creating a new instance of AllGeeks class
        $newGeek = new AllGeeks($username, $fullName, $email, $dateOfBirth);

        // Writing to AllGeeks.json
        file_put_contents('AllGeeks.json', json_encode($newGeek) . PHP_EOL, FILE_APPEND);
    } catch (Exception $e) {
        // General exception handling.
        throw new Exception("Error saving geek to file: " . $e->getMessage(), $e->getCode());
    }
}