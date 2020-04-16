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
			$bvc_reg = $_POST['bvc_reg'];
            $password = $_POST['password'];
            
            $dupe = "SELECT * FROM users WHERE phone = '$phone' and password = '$password' or bvc_reg = '$bvc_reg' and password = '$password'";
            $dupe_results = mysqli_query($con, $dupe);
            
            
            $response = array();

            if (mysqli_num_rows($dupe_results) > 0)
            {

                $row = mysqli_fetch_row($dupe_results);

                $name = $row[0];
                $id = $row[1];
                $email = $row[2];
                $phone = $row[3];
                $bvc_reg = $row[4];
                $password = $row[5];
				$university = $row[6];
                $designation = $row[7];
                $posting_area = $row[8];
                $district = $row[9];
                $division = $row[10];
                $email_confirm = $row[11];
                $rand_code = $row[12];
                $user_request = $row[13];
                $user_type = $row[14];
				$admin_email = $row[15];

                array_push($response, array(
                    "name" => $name,
                    "id" => $id,
                    "email" => $email,
                    "phone" => $phone,
                    "bvc_reg" => $bvc_reg,
                    "password" => $password,
					"university" => $university,
                    "designation" => $designation,
                    "posting_area" => $posting_area,
                    "district" => $district,
                    "division" => $division,
                    "email_confirm" => $email_confirm,
                    "rand_code" => $rand_code,
                    "user_request" => $user_request,
                    "user_type" => $user_type,
					"admin_email" => $admin_email
                ));
                echo json_encode($response);

                mysqli_close($con);

            }
            else
            {
                if(mysqli_error($con))
                {
                    echo "sql error";
                }
                else
                {
                    echo "Please check your ID & Password!";
                }
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