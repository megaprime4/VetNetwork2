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
            $password = $_POST['password'];

            $sql_select = "SELECT password FROM users WHERE phone = '$phone'";

            if (mysqli_query($con, $sql_select))
            {
                $sql_select_results = mysqli_query($con, $sql_select);

                if (mysqli_num_rows($sql_select_results) > 0)
                {
                    $sql_update = "UPDATE users SET password = '$password' WHERE phone = '$phone'";

                    if(mysqli_query($con, $sql_update))
                    {
                        echo "Warning : Password changed!";
                    }
                    else
                    {
                        echo "SQL error (update)!";
                    }
                }
                else
                {
                    echo "No row selected! Please debug!";
                }
            }
            else
            {
                echo "SQL error (select)!";
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

    mysqli_close($con); //finally closing connection
}
?>