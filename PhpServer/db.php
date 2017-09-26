<?php
// Establish Connection with MYSQL
$con=mysqli_connect("localhost","adstexin_doctor","ads2tex@doctor","adstexin_doctor");
mysqli_query($con, "SET character_set_results = 'utf8', character_set_client = 'utf8', character_set_connection = 'utf8', character_set_database = 'utf8', character_set_server = 'utf8'");


// Check connection
if (mysqli_connect_errno())
{
    echo "Failed to connect to MySQL: " . mysqli_connect_error();
}
?>