<?php
    ini_set("display_errors", TRUE);

    function check_parameter($param, &$results, &$errors){
        if (isset($_POST[$param])) {
            $results[$param] = $_POST[$param];
        } else {
            $errors[] = "$param not provided";
        }
    }

    if ($_SERVER["REQUEST_METHOD"] !== "POST"){
        http_response_code(405);
        die();
    }

    // If we made it this far then we know it's a POST request
    header("Content-Type: application/json; charset=utf-8");
    header("Access-Control-Allow-Origin: *");

    $result_array = array();
    $error_array  = array();

    check_parameter("name",  $result_array, $error_array);
    check_parameter("age",   $result_array, $error_array);
    check_parameter("email", $result_array, $error_array);

    if (!empty($error_array)){
        $result_array["errors"] = $error_array;
    } else {
        $result_array["user_id"] = rand(10000, 99999);
    }

    print(json_encode($result_array));
?>


