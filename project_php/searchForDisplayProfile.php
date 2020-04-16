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
			$phone = $_POST['phone'];
			
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

            $sql = "SELECT name, posting_area, district, division, email, phone, bvc_reg, university, designation, user_request FROM users WHERE phone = '".$phone."'";

            if (mysqli_query($con, $sql))
            {
                $dupe_results = mysqli_query($con, $sql);
                $response = array();

                if (mysqli_num_rows($dupe_results) > 0)
                {

					//retrieve and print every record
					while ($r = $dupe_results->fetch_assoc())
					{
						$response[] = $r;
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