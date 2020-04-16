<?php
require_once "connect.php";

if (!$con)
{
    echo "Connection failed!";
}
else
{
    if ($_SERVER['HTTP_USER_AGENT'] == "VetNetwork")
    {

        if ($_SERVER['REQUEST_METHOD'] == "POST")
        {
			$name = $_POST['name'];
			$phone = $_POST['phone'];
            $posting_area = $_POST['postingArea'];
			$district = $_POST['district'];
			$division = $_POST['division'];
			
			if($name == "")
			{
				$name = "N/A";
			}
			if($phone == "")
			{
				$phone = "N/A";
			}
			if($posting_area == "Select")
			{
				$posting_area = "N/A";
			}
			if($district == "Select")
			{
				$district = "N/A";
			}
			if($division == "Select")
			{
				$division = "N/A";
			}

            $sql = "SELECT name, phone FROM users WHERE (user_request = 'verified' OR user_request = 'accepted')";
			
			
			//00000
			if($name == "N/A" && $phone == "N/A" && $posting_area == "N/A" && $district == "N/A" && $division == "N/A")
			{
				$sql = "SELECT name,phone FROM users WHERE (user_request = 'verified' OR user_request = 'accepted')";
			}//00001
			else if($name == "N/A" && $phone == "N/A" && $posting_area == "N/A" && $district == "N/A" && $division != "N/A")
			{
				$sql = "SELECT name,phone FROM users WHERE (user_request = 'verified' OR user_request = 'accepted') AND division = '".$division."'";
			}//00010
			else if($name == "N/A" && $phone == "N/A" && $posting_area == "N/A" && $district != "N/A" && $division == "N/A")
			{
				$sql = "SELECT name,phone FROM users WHERE (user_request = 'verified' OR user_request = 'accepted') AND district = '".$district."'";
			}//00011
			else if($name == "N/A" && $phone == "N/A" && $posting_area == "N/A" && $district != "N/A" && $division != "N/A")
			{
				$sql = "SELECT name,phone FROM users WHERE (user_request = 'verified' OR user_request = 'accepted') AND district = '".$district."' AND division = '".$division."'";
			}
			
			
			
			//00100
			else if($name == "N/A" && $phone == "N/A" && $posting_area != "N/A" && $district == "N/A" && $division == "N/A")
			{
				$sql = "SELECT name,phone FROM users WHERE (user_request = 'verified' OR user_request = 'accepted') AND posting_area = '".$posting_area."'";
			}//00101
			else if($name == "N/A" && $phone == "N/A" && $posting_area != "N/A" && $district == "N/A" && $division != "N/A")
			{
				$sql = "SELECT name,phone FROM users WHERE (user_request = 'verified' OR user_request = 'accepted') AND posting_area = '".$posting_area."' AND division = '".$division."'";
			}//00110
			else if($name == "N/A" && $phone == "N/A" && $posting_area != "N/A" && $district != "N/A" && $division == "N/A")
			{
				$sql = "SELECT name,phone FROM users WHERE (user_request = 'verified' OR user_request = 'accepted') AND posting_area LIKE '%".$posting_area." % AND district = '".$district."'";
			}//00111
			else if($name == "N/A" && $phone == "N/A" && $posting_area != "N/A" && $district != "N/A" && $division != "N/A")
			{
				$sql = "SELECT name,phone FROM users WHERE (user_request = 'verified' OR user_request = 'accepted') AND posting_area = '".$posting_area."' AND district = '".$district."' AND division = '".$division."'";
			}
			
			
			//01000
			else if($name == "N/A" && $phone != "N/A" && $posting_area == "N/A" && $district == "N/A" && $division == "N/A")
			{
				$sql = "SELECT name,phone FROM users WHERE (user_request = 'verified' OR user_request = 'accepted') AND phone LIKE '%".$phone."%'";
			}//01001
			else if($name == "N/A" && $phone != "N/A" && $posting_area == "N/A" && $district == "N/A" && $division != "N/A")
			{
				$sql = "SELECT name,phone FROM users WHERE (user_request = 'verified' OR user_request = 'accepted') AND phone LIKE '%".$phone."%' AND division = '".$division."'";
			}//01010
			else if($name == "N/A" && $phone != "N/A" && $posting_area == "N/A" && $district != "N/A" && $division == "N/A")
			{
				$sql = "SELECT name,phone FROM users WHERE (user_request = 'verified' OR user_request = 'accepted') AND phone LIKE '%".$phone."%' AND district = '".$district."'";
			}//01011
			else if($name == "N/A" && $phone != "N/A" && $posting_area == "N/A" && $district != "N/A" && $division != "N/A")
			{
				$sql = "SELECT name,phone FROM users WHERE (user_request = 'verified' OR user_request = 'accepted') AND phone LIKE '%".$phone."%' AND district = '".$district."' AND division = '".$division."'";
			}
			
			
			//01100
			else if($name == "N/A" && $phone != "N/A" && $posting_area != "N/A" && $district == "N/A" && $division == "N/A")
			{
				$sql = "SELECT name,phone FROM users WHERE (user_request = 'verified' OR user_request = 'accepted') AND phone LIKE '%".$phone."%' AND posting_area = '".$posting_area."'";
			}//01101
			else if($name == "N/A" && $phone != "N/A" && $posting_area != "N/A" && $district == "N/A" && $division != "N/A")
			{
				$sql = "SELECT name,phone FROM users WHERE (user_request = 'verified' OR user_request = 'accepted') AND phone LIKE '%".$phone."%' AND posting_area = '".$posting_area."' AND division = '".$division."'";
			}//01110
			else if($name == "N/A" && $phone != "N/A" && $posting_area != "N/A" && $district != "N/A" && $division == "N/A")
			{
				$sql = "SELECT name,phone FROM users WHERE (user_request = 'verified' OR user_request = 'accepted') AND phone LIKE '%".$phone."%' AND posting_area = '".$posting_area."' AND district = '".$district."'";
			}//01111
			else if($name == "N/A" && $phone != "N/A" && $posting_area != "N/A" && $district != "N/A" && $division != "N/A")
			{
				$sql = "SELECT name,phone FROM users WHERE (user_request = 'verified' OR user_request = 'accepted') AND phone LIKE '%".$phone."%' AND posting_area = '".$posting_area."' AND district = '".$district."' AND division = '".$division."'";
			}
			
			
			//10000
			else if($name != "N/A" && $phone == "N/A" && $posting_area == "N/A" && $district == "N/A" && $division == "N/A")
			{
				$sql = "SELECT name,phone FROM users WHERE (user_request = 'verified' OR user_request = 'accepted') AND name like '%".$name."%'";
			}//10001
			else if($name != "N/A" && $phone == "N/A" && $posting_area == "N/A" && $district == "N/A" && $division != "N/A")
			{
				$sql = "SELECT name,phone FROM users WHERE (user_request = 'verified' OR user_request = 'accepted') AND name LIKE '%".$name."%' AND division = '".$division."'";
			}//10010
			else if($name != "N/A" && $phone == "N/A" && $posting_area == "N/A" && $district != "N/A" && $division == "N/A")
			{
				$sql = "SELECT name,phone FROM users WHERE (user_request = 'verified' OR user_request = 'accepted') AND name LIKE '%".$name."%' AND district = '".$district."'";
			}//10011
			else if($name != "N/A" && $phone == "N/A" && $posting_area == "N/A" && $district != "N/A" && $division != "N/A")
			{
				$sql = $sql = "SELECT name,phone FROM users WHERE (user_request = 'verified' OR user_request = 'accepted') AND name LIKE '%".$name."%' AND district = '".$district."' AND division = '".$division."'";
			}
			
			
			//10100
			else if($name != "N/A" && $phone == "N/A" && $posting_area != "N/A" && $district == "N/A" && $division == "N/A")
			{
				$sql = "SELECT name,phone FROM users WHERE (user_request = 'verified' OR user_request = 'accepted') AND name LIKE '%".$name."%' AND posting_area = '".$posting_area."'";
			}//10101
			else if($name != "N/A" && $phone == "N/A" && $posting_area != "N/A" && $district == "N/A" && $division != "N/A")
			{
				$sql = $sql = "SELECT name,phone FROM users WHERE (user_request = 'verified' OR user_request = 'accepted') AND name LIKE '%".$name."%' AND posting_area = '".$posting_area."' AND division = '".$division."'";
			}//10110
			else if($name != "N/A" && $phone == "N/A" && $posting_area != "N/A" && $district != "N/A" && $division == "N/A")
			{
				$sql = $sql = "SELECT name,phone FROM users WHERE (user_request = 'verified' OR user_request = 'accepted') AND name LIKE '%".$name."%' AND posting_area = '".$posting_area."' AND district = '".$district."'";
			}//10111
			else if($name != "N/A" && $phone == "N/A" && $posting_area != "N/A" && $district != "N/A" && $division != "N/A")
			{
				$sql = "SELECT name,phone FROM users WHERE (user_request = 'verified' OR user_request = 'accepted') AND name LIKE '%".$name."%' AND posting_area = '".$posting_area."' AND district = '".$district."' AND division = '".$division."'";
			}
			
			
			//11000
			else if($name != "N/A" && $phone != "N/A" && $posting_area == "N/A" && $district == "N/A" && $division == "N/A")
			{
				$sql = "SELECT name,phone FROM users WHERE (user_request = 'verified' OR user_request = 'accepted') AND name LIKE '%".$name."%' AND phone LIKE '%".$phone."%'";
			}//11001
			else if($name != "N/A" && $phone != "N/A" && $posting_area == "N/A" && $district == "N/A" && $division != "N/A")
			{
				$sql = "SELECT name,phone FROM users WHERE (user_request = 'verified' OR user_request = 'accepted') AND name LIKE '%".$name."%' AND phone LIKE '%".$phone."%' AND division = '".$division."'";
			}//11010
			else if($name != "N/A" && $phone != "N/A" && $posting_area == "N/A" && $district != "N/A" && $division == "N/A")
			{
				$sql = "SELECT name,phone FROM users WHERE (user_request = 'verified' OR user_request = 'accepted') AND name LIKE '%".$name."%' AND phone LIKE '%".$phone."%' AND district = '".$district."'";
			}//11011
			else if($name != "N/A" && $phone != "N/A" && $posting_area == "N/A" && $district != "N/A" && $division != "N/A")
			{
				$sql = "SELECT name,phone FROM users WHERE (user_request = 'verified' OR user_request = 'accepted') AND name LIKE '%".$name."%' AND phone LIKE '%".$phone."%' AND district = '".$district."' AND division = '".$division."'";
			}
			
			
			
			//11100
			else if($name != "N/A" && $phone != "N/A" && $posting_area != "N/A" && $district == "N/A" && $division == "N/A")
			{
				$sql = "SELECT name,phone FROM users WHERE (user_request = 'verified' OR user_request = 'accepted') AND name LIKE '%".$name."%' AND phone LIKE '%".$phone."%' AND posting_area = '".$posting_area."'";
			}//11101
			else if($name != "N/A" && $phone != "N/A" && $posting_area != "N/A" && $district == "N/A" && $division != "N/A")
			{
				$sql = "SELECT name,phone FROM users WHERE (user_request = 'verified' OR user_request = 'accepted') AND name LIKE '%".$name."%' AND phone LIKE '%".$phone."%' AND posting_area = '".$posting_area."' AND division = '".$division."'";
			}//11110
			else if($name != "N/A" && $phone != "N/A" && $posting_area != "N/A" && $district != "N/A" && $division == "N/A")
			{
				$sql = "SELECT name,phone FROM users WHERE (user_request = 'verified' OR user_request = 'accepted') AND name LIKE '%".$name."%' AND phone LIKE '%".$phone."%' AND posting_area = '".$posting_area."' AND district = '".$district."'";
			}//11111
			else if($name != "N/A" && $phone != "N/A" && $posting_area != "N/A" && $district != "N/A" && $division != "N/A")
			{
				$sql = "SELECT name,phone FROM users WHERE (user_request = 'verified' OR user_request = 'accepted') AND name LIKE '%".$name."%' AND phone LIKE '%".$phone."%' AND posting_area = '".$posting_area."' AND district = '".$district."' AND division = '".$division."'";
			}

            if (mysqli_query($con, $sql))
            {
                $dupe_results = mysqli_query($con, $sql);
                $response = array();

                if (mysqli_num_rows($dupe_results) > 0)
                {

					//retrieve and print every record
					while ($r = $dupe_results->fetch_assoc())
					{
						// $rows[] = $r; has the same effect, without the superfluous data attribute
						$response[] = $r;
						//array_push($response,array('posting_area'->$r['posting_area']));
					}
					
					// now all the rows have been fetched, it can be encoded
                    echo json_encode($response);

					//close connection
                    mysqli_close($con);
                }
                else
                {
                    if (mysqli_error($con))
                    {
                        echo "sql (select) query error!-inner";
                    }
                    else
                    {
                        echo "No row selected! Please debug!";
                    }
                }
            }
            else
            {
                echo "sql (select) query error!-outer"." message : ".mysqli_error($con);
            }
        }
        else
        {
            echo "Improper request method!";
        }
    }
    else
    {
        echo "Invalid platform!";
    }
}
?>