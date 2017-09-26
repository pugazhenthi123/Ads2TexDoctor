<?php

/**
 * check for POST request
 */
if (isset($_POST['tag']) && $_POST['tag'] != '') {
    // get tag
    $tag = $_POST['tag'];

    // include db handler
    require_once 'db_functions.php';
    include_once 'db.php';
    $db = new db_functions();

    // response Array
    $response = array("tag" => $tag, "error" => FALSE);

    // check for tag type
    if ($tag == 'login') {
        // Request type is check Login
        $email = $_POST['email'];
        $email = strtolower($email);
        $Imei = $_POST['imei'];
        $password = md5($_POST['password']);
        $Login = true;
        $nos=1;
        $nos2 = 0;
        // check for user
        $user = $db->checkEmailAndPassword($email, $password);
        if ($user == true) {
            // user found
            $result = mysqli_query($con, "SELECT sno FROM doctors WHERE email = '" . $email . "' and status = '". $nos ."'") or die(mysqli_error($con));
            $result2 = mysqli_fetch_array($result);
            $records = mysqli_num_rows($result);
            if ($records != 0) {
                $select_email = mysqli_query($con, "SELECT * from doctors where email='" . $email . "'");
                $fetch_email = mysqli_fetch_array($select_email);
                $select_logo = mysqli_query($con,"Select * from hospital where sno = '".$fetch_email['hospital_no']."'");
                $fetch_logo = mysqli_fetch_array($select_logo);
                $doctorid = $fetch_email['sno'];
                $updq2 = mysqli_query($con,"UPDATE doctors SET imei_no = '" . $Imei . "',login = '". $nos ."' WHERE sno = '".$doctorid."'");
                $response["doctor"] = $result2;
                $response["hospital_logo_link"] = $fetch_logo['logo'];
                $response["error"] = FALSE;
                $response["msg"] = "Login Success";
                $response["hospital"] = $fetch_email['hospital_no'];
                $response["imei"] = $Imei;
            } else {
                $verify = $db->sendconfirmmail($email);
                if ($verify == true) {
                    $response["error"] = TRUE;
                    $response["msg"] = "You have already registered and the activation link is sent to your email. Click the activation link to activate your account.";
                } else {
                    $response["error"] = TRUE;
                    $response["msg"] = "Try Again!";
                }

            }
            echo json_encode($response);
        } else {
            $result = mysqli_query($con, "SELECT sno FROM customer_reg WHERE Email = '" . $email . "' and status = 0") or die(mysqli_error($con));
            $result2 = mysqli_fetch_array($result);
            $records = mysqli_num_rows($result);
            if ($records != 0) {
                if ($verify == true) {
                    $response["error"] = TRUE;
                    $response["msg"] = "Your Account Deactivated";
                } else {
                    $response["error"] = TRUE;
                    $response["msg"] = "Try Again!";
                }
            } else {
                // user not found
                // echo json with error = 1
                $response["error"] = TRUE;
                $response["msg"] = "Incorrect email or password";
            }
            echo json_encode($response);
        }
    } else if ($tag == 'checkemail') {
        // Request type is check Login
        $email = $_POST['email'];
        $email = strtolower($email);
        // check for user
        $user = $db->checkEmailAndPassword($email, $password);
        if ($user == true) {
            // user found
            $response["error"] = FALSE;
        } else {
            // user not found
            // echo json with error = 1
            $response["error"] = TRUE;
        }
        echo json_encode($response);
    } else if ($tag == 'checkEmailActive') {
        // Request type is check Login
        $email = $_POST['email'];
        $email = strtolower($email);
        // check for user
        $user = $db->checkEmailActive($email);
        if ($user == true) {
            // user found
            $response["error"] = FALSE;
        } else {
            // user not found
            // echo json with error = 1
            $response["error"] = TRUE;
        }
        echo json_encode($response);
    } else if ($tag == 'resetpass') {
        // Request type is ResetPassword
        $email = $_POST['email'];
        $email = strtolower($email);
        $State = $_POST['state'];
        $user = $db->ForgetPassword($email, $State);
        if ($user == true) {
            $response["error"] = FALSE;
            $response["msg"] = "Reset password link sent to your mail";
        } else {
            $response["error"] = TRUE;
            $response["msg"] = "Email Id Does Not Exist";
        }
        echo json_encode($response);
    } else if ($tag == 'setpass') {
        // Request type is SetPass
        $email = $_POST['email'];
        $email = strtolower($email);
        $pass = md5($_POST['pass']);
        $user = $db->SetPassword($email, $pass);
        if ($user == true) {
            $response["error"] = FALSE;
            $response["msg"] = "Password Set Successfully";
        } else {
            $response["error"] = TRUE;
            $response["msg"] = "Email Id Does Not Exist";
        }
        echo json_encode($response);
    } else {
        // user failed to store
        $response["error"] = TRUE;
        $response["msg"] = "Unknow 'tag' value. It should be either 'login' or 'register'";
        echo json_encode($response);
    }
} else {
    $response["error"] = TRUE;
    $response["msg"] = "Required parameter 'tag' is missing!";
    echo json_encode($response);
}
?>