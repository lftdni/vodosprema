<?php


$con = mysqli_connect('domena','baza','tablica','korisnik');
if (!$con) {
  die('Could not connect: ' . mysqli_error($con));
}

mysqli_select_db($con,"ajax_demo");
$sql="SELECT * FROM tablica";
$result = mysqli_query($con,$sql);



while($row = mysqli_fetch_array($result)) {


echo "<label id='lbl' value='" . $row['Razina'] . "' style='height:50px; height:200px; font-size:22px;'>" . $row['Razina'] . "</label>";
}
mysqli_close($con);
?>