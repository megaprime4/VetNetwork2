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
			$bvc = $_POST['bvc'];
			
			if($bvc == "")
			{
				$bvc = "N/A";
			}

            $sql = "SELECT name, phone FROM users WHERE (user_request = 'verified' OR user_request = 'accepted') AND bvc_reg LIKE '%".$bvc."%'";

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