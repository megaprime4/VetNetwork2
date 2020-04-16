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
			$selected_radio_button = $_POST['radio_button'];
            $user_id = $_POST['user_id'];

			
			if($selected_radio_button == "Name")
            {
                $sql = "SELECT name, posting_area, district, division, email, phone, bvc_reg, university, designation, user_request FROM users WHERE name LIKE '%".$user_id."%' AND user_type != 'pending' AND user_type != 'Admin' AND user_request != 'pending'";

                if (mysqli_query($con, $sql))
                {
                    $dupe_results = mysqli_query($con, $sql);
                    $response = array();

                    if (mysqli_num_rows($dupe_results) > 0)
                    {
                        while ($row = $dupe_results->fetch_assoc())
                        {
                            $response[] = $row;
                        }

                        echo json_encode($response);
                    }
                    else
                    {
                        echo "No user found having like this name!";
                    }
                    
                    mysqli_close($con);
                }
                else
                {
                    echo "SQL query (Name) error!";
                }
            }
            else if($selected_radio_button == "Phone")
            {
                $sql = "SELECT name, posting_area, district, division, email, phone, bvc_reg, university, designation, user_request FROM users WHERE phone LIKE '%".$user_id."%'";

                if (mysqli_query($con, $sql))
                {
                    $dupe_results = mysqli_query($con, $sql);
                    $response = array();

                    if (mysqli_num_rows($dupe_results) > 0)
                    {
                        while ($row = $dupe_results->fetch_assoc())
                        {
                            $response[] = $row;
                        }

                        echo json_encode($response);
                    }
                    else
                    {
                        echo "No user found having like this phone number!";
                    }
                    
                    mysqli_close($con);
                }
                else
                {
                    echo "SQL query (Phone) error!";
                }
            }
            else if($selected_radio_button == "BVC")
            {
                $sql = "SELECT name, posting_area, district, division, email, phone, bvc_reg, university, designation, user_request FROM users WHERE bvc_reg = '".$user_id."'";

                if (mysqli_query($con, $sql))
                {
                    $dupe_results = mysqli_query($con, $sql);
                    $response = array();

                    if (mysqli_num_rows($dupe_results) > 0)
                    {
                        while ($row = $dupe_results->fetch_assoc())
                        {
                            $response[] = $row;
                        }

                        echo json_encode($response);
                    }
                    else
                    {
                        echo "No user found having this BVC registration number!";
                    }
                    
                    mysqli_close($con);
                }
                else
                {
                    echo "SQL query (BVC) error!";
                }
            }
            else
            {
                echo "Something wrong with the RadioButton! Please debug!";
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

    //mysqli_close($con);
}
?>