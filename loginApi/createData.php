<?php
require_once 'connection.php';

if ($con) {
    $username = $_POST['username'];
    $nama = $_POST['name'];
    $password = $_POST['password'];
    $notelp = $_POST['notelp'];

    $insert = "INSERT INTO pelanggan(username, nama, password, no_telepon   ) VALUES('$username','$nama', md5('$password'), '$notelp')";

    if ($username !="" && $nama !="" && $password !="" && $notelp !="") {
            $result = mysqli_query($con, $insert);
            $response = array();

            if ($result) {
                array_push($response, array(
                    'status' => 'OK'
                ));
            } else {
                array_push($response, array(
                    'status' => 'FAILED1'
                ));
            }
    } else {
        array_push($response, array(
            'status' => 'FAILED2'
        ));
    }
}
else
{
    array_push($response, array(
            'status' => 'FAILED3'
    ));
}

echo json_encode(array("server_response" => $response));
mysqli_close($con);

?>