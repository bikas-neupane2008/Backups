<?php
    ini_set('display_errors', TRUE);

    header("Content-Type: application/json; charset=utf-8");
    header("Access-Control-Allow-Origin: *");

    date_default_timezone_set("Australia/Sydney");

    $t = date("h:i:s");
    $d = date("Y-m-d");

    $dt = array(
        "date" => $d,
        "time" => $t
    );

    $json_dt = json_encode($dt);

    print($json_dt);
?>
