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
            $email = $_POST['email'];

            $sql = "SELECT name, password FROM users WHERE email = '$email'";

            if (mysqli_query($con, $sql))
            {

                $sql_results = mysqli_query($con, $sql);

                if (mysqli_num_rows($sql_results) > 0)
                {
                    $row = mysqli_fetch_row($sql_results);

                    $name = $row[0];
                    $password = $row[1];

                    //mailing password
                    $to = $email;
                    $subject = "Forgot Password";
                    $message = "
                        $name,
                        
                        Your password is : $password";
                    $headers = "From: DoNotReply@vetdevelopers.com\r\n";
                    $headers .= "Reply-To: DoNotReply@vetdevelopers.com\r\n";
                    $headers .= "Return-Path: DoNotReply@vetdevelopers.com\r\n";
                    $headers .= "CC: sombodyelse@example.com\r\n";
                    $headers .= "BCC: hidden@example.com\r\n";

                    if (mail($to, $subject, $message, $headers))
                    {
                        echo "Please check your email!";
                    }
                    else
                    {
                        echo "Mail sending failed!";
                    }
                    //mailing password
                }
                else
                {
                    echo "This email is not registered!";
                }
            }
            else
            {
                echo "SQL error!";
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