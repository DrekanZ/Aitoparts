<?php 
 
 /*
 * Created by Belal Khan
 * website: www.simplifiedcoding.net 
 * Retrieve Data From MySQL Database in Android
 */
 
 //database constants
 define('DB_HOST', 'localhost');
 define('DB_USER', 'root');
 define('DB_PASS', '');
 define('DB_NAME', 'dealer');
 
 //connecting to database and getting the connection object
 $conn = new mysqli(DB_HOST, DB_USER, DB_PASS, DB_NAME);
 
 //Checking if any error occured while connecting
 if (mysqli_connect_errno()) {
 echo "Failed to connect to MySQL: " . mysqli_connect_error();
 die();
 }
 
 $id = $_POST['id'];
 //creating a query
 $stmt = $conn->prepare("SELECT judul_berita, isi, gambar FROM tips where id_berita='$id';");
 
 //executing the query 
 $stmt->execute();
 
 //binding results to the query 
 $stmt->bind_result($title, $teks, $image);
 
 $tips = array(); 
 
 //traversing through all the result 
 while($stmt->fetch()){
 $temp = array();
 $temp['title'] = $title; 
 $temp['teks'] = $teks;
 $temp['image'] = $image;
 array_push($tips, $temp);
 }
 
 //displaying the result in json format 
 echo json_encode($tips);
 ?>