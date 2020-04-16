<?php
	require_once "connect.php";
	
	$phone = $_GET["phone"];
	$code = $_GET["code"];
	$dbcode = "";
	
	$query = "SELECT * FROM users WHERE phone = '$phone'";
	
	$result = mysqli_query($con, $query);
	//echo $phone."<br>".$code."<br>";
	
	
	if($result)
	{
		while($row = mysqli_fetch_assoc($result))
		{
			$dbcode = $row["rand_code"];
		}
		
		
		if($dbcode == $code)
		{
			$qry = "UPDATE users SET rand_code='0', user_request='verified', user_type='Admin' WHERE phone='$phone'";
			$fresult = mysqli_query($con, $qry);
			
			echo "Congratulation! Your admin account has been activated!";
		}
		else
		{
			echo "E-mail verification failed!"." ".$code." ".$dbcode;
		}
	}
	else
	{
		echo "No results was returned!";
	}
?>