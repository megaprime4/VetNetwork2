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
            $admin_email = $_POST['admin_email'];
            $name = $_POST['name'];
            $phone = $_POST['phone'];
            $email = $_POST['email'];
            $topic = $_POST['topic'];
            $description = $_POST['description'];
            $date = $_POST['date'];
            $time = $_POST['time'];


            $sql_insert = "INSERT INTO reportAdmin (name, phone, email, topic, description, date, time) VALUES ('$name', '$phone', '$email', '$topic', '$description', '$date', '$time')";

            if (mysqli_query($con, $sql_insert))
            {
                $to = $admin_email;
                $subject = "Application Problem";
                $message = "
                        Dear Admin,
                        
                        I am facing some problem using this application. Please check it out.
                        Report given below.
                        
                        Topic : $topic
                        Description : $description
                        Time : $time
                        Date : $date

                        From,
                        Type : Admin
                        Name : $name
                        Phone : $phone
                        Email : $email
                        ";
                $headers = "From: DoNotReply@vetdevelopers.com\r\n";
                $headers .= "Reply-To: DoNotReply@vetdevelopers.com\r\n";
                $headers .= "Return-Path: DoNotReply@vetdevelopers.com\r\n";
                //$headers .= "CC: sombodyelse@example.com\r\n";
                //$headers .= "BCC: hidden@example.com\r\n";

                if (mail($to, $subject, $message, $headers))
                {
                    echo "Thank you for your cooperation! We will review your problem as soon as possible!";
                    mysqli_close($con);
                }
                else
                {
                    echo "Sending failed!";
                    mysqli_close($con);
                }
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

            //mysqli_close($con); //finally closing connection
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
    
    //mysqli_close($con); //finally closing connection
}
?>