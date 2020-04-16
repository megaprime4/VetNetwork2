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

            $sql = "SELECT admin_email FROM latestAdminEmail";

            if (mysqli_query($con, $sql))
            {
                $dupe_results = mysqli_query($con, $sql);
                $response = array();

                if (mysqli_num_rows($dupe_results) > 0)
                {

                    $row = mysqli_fetch_row($dupe_results);

                    $admin_email = $row[0];

                    array_push($response, array(
                        "admin_email" => $admin_email
                    ));
                    echo json_encode($response);

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
                        echo "No email found!";
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