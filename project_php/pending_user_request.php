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

            $sql = "SELECT name, phone FROM users WHERE user_request = 'pending'";

            if (mysqli_query($con, $sql))
            {
                $dupe_results = mysqli_query($con, $sql);
                $response = array();

                if (mysqli_num_rows($dupe_results) > 0)
                {

					while ($r = $dupe_results->fetch_assoc())
					{
						$response[] = $r;
					}
					
                    echo json_encode($response);

                    mysqli_close($con);
                }
                else
                {
                    echo "No user request found!";
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