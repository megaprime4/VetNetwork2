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

            $admin_name = $_POST['admin_name'];
            $admin_phone = $_POST['admin_phone'];
            $admin_email = $_POST['admin_email'];  //new email

            $code = rand();  //generating random code for email verification

            $sql_insert = "INSERT INTO adminEmail (admin_name, admin_phone, rand_code, admin_email, email_confirm) 
                            VALUES ('$admin_name', '$admin_phone', '$code', '$admin_email', 'not confirmed')";

            if (mysqli_query($con, $sql_insert))
            {
                $to = $admin_email;
                $subject = "Admin Email Verification";
                $message = "
                        $admin_name,
                        
                        Please click on the link below
                        
                        http://vetdevelopers.com/app_connect/adminEmailVer.php?admin_phone=$admin_phone&code=$code
                        ";
                $headers = "From: DoNotReply@vetdevelopers.com\r\n";
                $headers .= "Reply-To: DoNotReply@vetdevelopers.com\r\n";
                $headers .= "Return-Path: DoNotReply@vetdevelopers.com\r\n";
                //$headers .= "CC: sombodyelse@example.com\r\n";  //causes delivery problem
                //$headers .= "BCC: hidden@example.com\r\n";  //causes delivery problem

                mail($to, $subject, $message, $headers);

                echo "Please check your e-mail!";
            }
            else
            {
                if (mysqli_error($con))
                {
                    echo "SQL (insert) query error!";
                }
                else
                {
                    echo "Data insertion failed!";
                }
            }

            mysqli_close($con); //finally closing connection
            
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