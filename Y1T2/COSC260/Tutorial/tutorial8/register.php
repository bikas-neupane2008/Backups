<?php
    ini_set('display_errors', TRUE);
    header("Content-Type: application/json; charset=utf-8");
    header("Access-Control-Allow-Origin: *");

    $result = array();
    $errors = array();


    if (isset($_POST["name"])){
        $result["name"] = $_POST["name"];
    } else {
        $errors[] = "name not provided";
    }


    if (isset($_POST["age"])){
        $result["age"] = $_POST["age"];
    } else {
        $errors[] = "age not provided";
    }


    if (isset($_POST["email"])){
        $result["email"] = $_POST["email"];
    } else {
        $errors[] = "email not provided";
    }


    if (!empty($errors)){
        $result["errors"] = $errors;
    } else {
        $result["user_id"] = rand(10000, 99999);
    }

    print(json_encode($result));

?>


