<?php
    ini_set('display_errors', TRUE);
    header("Content-Type: application/json; charset=utf-8");
    header("Access-Control-Allow-Origin: *");

    $result = array();
    $errors = array();


    if (isset($_GET["name"])){
        $result["name"] = $_GET["name"];
    } else {
        $errors[] = "name not provided";
    }


    if (isset($_GET["age"])){
        $result["age"] = $_GET["age"];
    } else {
        $errors[] = "age not provided";
    }


    if (isset($_GET["email"])){
        $result["email"] = $_GET["email"];
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


