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
			

            $sql_select = "SELECT bvc_reg FROM users WHERE phone = '".$phone."'";

            if (mysqli_query($con, $sql_select))
            {
                $dupe_results = mysqli_query($con, $sql_select);

                if (mysqli_num_rows($dupe_results) > 0)
                {
                    $sql_update = "DELETE FROM users WHERE phone = '".$phone."'";

                    if (mysqli_query($con, $sql_update))
                    {
                        echo "Account permanently deleted!";

                        mysqli_close($con);
                    }
                    else
                    {
                        echo "SQL (delete) query error!";
                    }
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