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

            $sql = "SELECT DISTINCT posting_area FROM users WHERE posting_area != 'N/A' AND user_request = 'verified' OR user_request = 'accepted'";

            if (mysqli_query($con, $sql))
            {
                $dupe_results = mysqli_query($con, $sql);
                $response = array();

                if (mysqli_num_rows($dupe_results) > 0)
                {

					//retrieve and print every record
					while ($row = $dupe_results->fetch_assoc())
					{
						$response[] = $row;
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
                echo "sql (select) query error!-outer";
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