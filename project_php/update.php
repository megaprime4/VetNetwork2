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
            $name = $_POST['name'];
            $email = $_POST['email'];
            $prevPhone = $_POST['prevPhone'];
			$phone = $_POST['phone'];
            $bvc_reg = $_POST['bvc_reg'];
            $university = $_POST['university'];
            $posting_area = $_POST['posting_area'];
            $district = $_POST['district'];
            $division = $_POST['division'];

            if($bvc_reg == "")
			{
				$bvc_reg = "N/A";
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

			
            $sql = "UPDATE users SET name = '$name', email = '$email', phone = '$phone', bvc_reg = '$bvc_reg', university = '$university', posting_area = '$posting_area', district = '$district', division = '$division' WHERE  phone = '$prevPhone' OR bvc_reg = '$bvc_reg'";

            if (mysqli_query($con, $sql))
            {
                //echo "Profile updated!";
				
				$dupe = "SELECT * FROM users WHERE phone = '$phone'";
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
                    if (mysqli_error($con))
                    {
                        echo "sql (select) query error!";
                    }
                    else
                    {
                        echo "No row selected! Please debug!";
                    }
                }
            }
            else
            {
                echo "sql (update) query error!";
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