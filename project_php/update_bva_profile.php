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
            $id = $_POST['id'];
            $name = $_POST['name'];
			$phone = $_POST['phone'];
            $email = $_POST['email'];

			
            $sql_select_check = "SELECT * FROM bva WHERE  id = '$id'";
            $dupe_results = mysqli_query($con, $sql_select_check);
            if (mysqli_num_rows($dupe_results) > 0)
            {
                $sql_update = "UPDATE bva SET name = '$name', phone = '$phone', email = '$email' WHERE  id = '$id'";
                if(mysqli_query($con, $sql_update))
                {
                    echo "Operation successful!";
                }
                else
                {
                    echo "SQL (update) query error!";
                }
            }
            else
            {
                echo "No row selected!";
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