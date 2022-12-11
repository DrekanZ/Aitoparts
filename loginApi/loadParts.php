<?php 
 
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
 
 //creating a query
 $stmt = $conn->prepare("SELECT id_barang, nama_barang, harga, stok, gambar FROM sparepart;");
 
 //executing the query 
 $stmt->execute();
 
 //binding results to the query 
 $stmt->bind_result($id, $title, $price, $stock, $image);
 
 $parts = array(); 
 
 //traversing through all the result 
 while($stmt->fetch()){
 $temp = array();
 $temp['id'] = $id; 
 $temp['title'] = $title;
 $temp['price'] = $price;
 $temp['stock'] = $stock;
 $temp['image'] = $image;
 array_push($parts, $temp);
 }
 
 //displaying the result in json format 
 echo json_encode($parts);
 ?>