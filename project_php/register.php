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
        if ($_SERVER["REQUEST_METHOD"] == "POST")
        {

            $user_type = $_POST['user_type'];
            $name = $_POST['name'];
            $email = $_POST['email'];
            $admin_email = $_POST['admin_email'];
            $phone = $_POST['phone'];
            $bvc_reg = $_POST['bvc_reg'];
            $password = $_POST['password'];
            $university = $_POST['university'];
            $designation = $_POST['designation'];
            $posting_area = $_POST['posting_area'];
            $district = $_POST['district'];
            $division = $_POST['division'];

            if($bvc_reg == "")
			{
				$bvc_reg = "N/A";
			}
			if ($designation == "")
            {
                $designation = "N/A";
            }
            if ($posting_area == "")
            {
                $posting_area = "N/A";
            }
			if($district =="Select")
			{
				$district  = "N/A";
			}
			if($division =="Select")
			{
				$division  = "N/A";
			}
			

            $dupe = "SELECT * FROM users WHERE email = '$email' OR bvc_reg ='$bvc_reg' OR phone ='$phone'";
            $dupe_results = mysqli_query($con, $dupe);
            if (mysqli_num_rows($dupe_results) > 0)
            {
                echo "Account already exists!";
            }
            else
            {
                if ($user_type == "Doctor")
                {
                    $sql = "INSERT INTO users (name, email, phone, bvc_reg, password, university, designation, posting_area, district, division, email_confirm, user_request, user_type, admin_email) VALUES ('$name',  '$email', '$phone', '$bvc_reg', '$password', '$university', '$designation', '$posting_area', '$district', '$division', '0', 'pending', '$user_type', '$admin_email')";
					
					if (mysqli_query($con, $sql))
					{
						echo "Registration complete! Request sent to admin!";
					}
					else
					{
						echo "sql query error!";
					}
                }
                else if ($user_type == "Admin")
                {
                    $code = rand();

                    $sql = "INSERT INTO users (name, email, phone, bvc_reg, password, university, designation, posting_area, district, division, admin_email, rand_code, user_type) VALUES ('$name',  '$email', '$phone', '$bvc_reg', '$password', '$university', '$designation', '$posting_area', '$district', '$division', '$admin_email', '$code', 'pending')";

                    if (mysqli_query($con, $sql))
                    {
                        $to = $admin_email;
                        $subject = "Email verification";
                        $message = 
                        "
                        $name,
                        
                        Please click on the link below
                        
                        http://vetdevelopers.com/app_connect/emailver.php?phone=$phone&code=$code
                        ";
                        $headers = "From: DoNotReply@vetdevelopers.com\r\n";
                        $headers .= "Reply-To: DoNotReply@vetdevelopers.com\r\n";
                        $headers .= "Return-Path: DoNotReply@vetdevelopers.com\r\n";
                        //$headers .= "CC: sombodyelse@example.com\r\n";
                        //$headers .= "BCC: hidden@example.com\r\n";

                        if ( mail($to,$subject,$message,$headers) ) 
                        {
                            echo "Please check your e-mail!";
                        }
                        else 
                        {
                            echo "The email has failed!";
                        }
                    }
                }

            }

            mysqli_close($con);
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
    
    mysqli_close($con);
}
?>