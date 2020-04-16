<?php
require_once "connect.php";

$admin_phone = $_GET["admin_phone"];
$code = $_GET["code"];
$dbcode = "";
$admin_email = "";
$admin_name = "";

$sql = "SELECT * FROM adminEmail WHERE admin_phone = '$admin_phone'";

$sql_result = mysqli_query($con, $sql);
echo $phone . "<br>" . $code . "<br>";

if ($sql_result)
{
    while ($row = mysqli_fetch_assoc($sql_result))
    {
        $dbcode = $row["rand_code"];
        $admin_email = $row["admin_email"];
        $admin_name = $row["admin_name"];
    }

    if ($dbcode == $code)
    {
        //$sql_select = "SELECT admin_email FROM latestAdminEmail WHERE id = 1";
        //$sql_select_results = mysqli_query($con, $sql_select);

        $sql_update_1 = "UPDATE adminEmail 
                        SET rand_code = '0', email_confirm = 'confirmed' 
                        WHERE  admin_phone = '$admin_phone'";

            if (mysqli_query($con, $sql_update_1))
            {

                $sql_update_2 = "UPDATE latestAdminEmail 
                                SET admin_name = '$admin_name', admin_phone = '$admin_phone', admin_email = '$admin_email' 
                                WHERE  id = 1";

                if(mysqli_query($con, $sql_update_2))
                {

                    echo "Admin e-mail successfully updated!";

                }
                else
                {
                    if (mysqli_error($con))
                    {
                        echo "SQL (update_2) query error!";
                    }
                else
                    {
                        echo "Data update_2 failed!";
                    }
                }
            }
            else
            {
                if (mysqli_error($con))
                {
                    echo "SQL (update_1) query error!";
                }
                else
                {
                    echo "Data update_1 failed!";
                }
            }
    }
    else
    {
        echo "E-mail verification failed!";
    }

    mysqli_close($con); //closing database connection...i don't know why! -_-
}
else
{
    echo "No results was returned!";
}
?>