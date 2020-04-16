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
            $designation = $_POST['designation'];
            $name = $_POST['name'];
            $phone = $_POST['phone'];
            $email = $_POST['email'];

            $sql_insert = "INSERT INTO bva (name, phone, email, designation) VALUES ('$name', '$phone', '$email', '$designation')";

            if (mysqli_query($con, $sql_insert))
            {
                echo "Operation successful";

                mysqli_close($con);
            }
            else
            {
                echo "sql (insert) query error!";

                mysqli_close($con);
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
