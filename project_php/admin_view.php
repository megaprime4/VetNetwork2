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
			$all_user_phone = $_POST['all_user_phone'];
			

            $sql = "SELECT name, posting_area, district, division, email, phone, bvc_reg, university, designation, user_request FROM users WHERE phone = '".$all_user_phone."'";

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
                    echo "No row selected! Please debug!";
                }
            }
            else
            {
                echo "SQL (select) query error!";
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