<?php
require_once 'connection.php';

if ($con) {
    $username = $_POST['username'];
    $password = $_POST['password'];

    $query = "SELECT * FROM pelanggan WHERE username = '$username' and password = MD5($password)";
    $result = mysqli_query($con, $query);
    $response = array();
    $row = mysqli_num_rows($result);

    if ($row > 0) {
        array_push($response, array(
            'status' => 'OK'
        ));
    } else {
        array_push($response, array(
            'status' => 'FAILED'
        ));
    }
}
else
{
    array_push($response, array(
            'status' => 'FAILED'
    ));
}

echo json_encode(array("server_response" => $response));
mysqli_close($con);

?>