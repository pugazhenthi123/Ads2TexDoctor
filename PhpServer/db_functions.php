<?php

class db_functions {
	private $db;
	public $conn;

	//put your code here
	// constructor
	function __construct() {
		require_once 'db_connect.php';
		include_once 'config.php';

		// connecting to database
		$this->db = new db_connect();
		$this->db->connect();
	}

	// destructor
	function __destruct() {

	}
	/**
	 *check user
	 */
	public function checkEmailAndPassword($email, $password) {
		$UserName = $email;
		$Password = $password;
		$result = mysqli_query($this->db->connect(),"SELECT * FROM doctors WHERE email = '".$UserName."' and pass = '".$Password."'") or die(mysqli_error($this->db->connect()));
		$records = mysqli_num_rows($result);
		//echo $records;
		if ($records > 0) {
			return true;
		} else {
			return false;
		}

	}
		
	
	public function checkEmailActive($email) {
		$UserName = $email;
		$result = mysqli_query($this->db->connect(),"SELECT * FROM doctors WHERE email = '" . $UserName . "' and status = 1") or die(mysqli_error($this->db->connect()));
		$records = mysqli_num_rows($result);
		$result = mysqli_fetch_array($result);
		//echo $records;
		if ($records > 0) {
			return true;
		} else {
			return false;
		}

	}
	
	public function SetPassword($email, $pass) {
		$UserName = $email;
		$Password = $pass;
		$result = mysqli_query($this->db->connect(),"Update doctors set pass = '" . $Password . "' where email = '" . $UserName . "'") or die(mysqli_error($this->db->connect()));
		return true;

	}
	
	public function sendconfirmmail($Email) {
		$email = $Email;
		$result = mysqli_query($this->db->connect(),"SELECT * FROM doctors WHERE email = '$email'") or die(mysqli_error($this->db->connect()));
		$records = mysqli_num_rows($result);
		$result = mysqli_fetch_array($result);
		//echo $records;
		if ($records != 0) {
			$customerid = $result['sno'];			
			$customerid_erc = base64_encode($customerid);
			$actual_link = "http://$_SERVER[HTTP_HOST]/" . "activate.php?id=" . $customerid_erc;
			$toEmail = $email;
			$subject = "User Registration Activation Email";
			$content = "Click this link to activate your account. $actual_link";
			$mailHeaders ="From:Ads2Tex Tirupur <support@ads2tex.in>\r\n";
                        $mailHeaders .= "MIME-Version: 1.0\r\n"; 
                        $mailHeaders .= "Content-Type: text/plain; charset=utf-8\r\n"; 
                        $mailHeaders .= "X-Priority: 1\r\n";
			mail($toEmail, $subject, $content, $mailHeaders)?>
		<?php
			return true;
		} else {
			return false;
		}
	}
	
	public function ForgetPassword($email,$state) {
	    $timezone = new DateTimeZone("Asia/Kolkata");
        $date = new DateTime();
        $date->setTimezone($timezone);
        $data = $date->format('sYsIs');
        $pass = md5($data);
		$UserName = $email;
		$State = $state;
		$result = mysqli_query($this->db->connect(),"SELECT * FROM doctors WHERE email = '$UserName'") or die(mysqli_error($this->db->connect()));
		$records = mysqli_num_rows($result);
		$result = mysqli_fetch_array($result);
		//echo $records;
		if ($records > 0) {
		    $fpass_sql = mysqli_query($this->db->connect(),"UPDATE doctors set pass = '" .$pass. "' where sno = '" .$result['sno']. "' ");
			$toEmail = $email;
			if($State!='fpass')
			{
			$subject = "User set password";
			}
			else
			{
			$subject = "User forgot password";
			}
			
			$content = "Your New Password: ". $data;
			$mailHeaders ="From:Ads2Tex Doctor <support@ads2tex.in>\r\n";
                        $mailHeaders .= "MIME-Version: 1.0\r\n"; 
                        $mailHeaders .= "Content-Type: text/plain; charset=utf-8\r\n"; 
                        $mailHeaders .= "X-Priority: 1\r\n";
			mail($toEmail, $subject, $content, $mailHeaders)?>
		<?php
			
			return true;
		} else {
			return false;
		}

	}

	public function getPatient($hospital) {
		$result_patient = mysqli_query($this->db->connect(),"SELECT * FROM patient WHERE hospital_no = '".$hospital."' ORDER BY name ASC") or die(mysqli_error($this->db->connect()));
		return $result_patient;
	}

	public function getDrugUnit()
	{
		$result_drug_unit = mysqli_query($this->db->connect(),"SELECT * FROM unit ORDER BY name ASC") or die(mysqli_error($this->db->connect()));
		return $result_drug_unit;
	}

	public function getPatientStatus()
	{
		$result_patient_status = mysqli_query($this->db->connect(),"SELECT * FROM patient_status ORDER BY name ASC") or die(mysqli_error($this->db->connect()));
		return $result_patient_status;
	}

	public function getDrugType() {
		$result_drug_type = mysqli_query($this->db->connect(),"SELECT * FROM drug_type ORDER BY name ASC") or die(mysqli_error($this->db->connect()));
		return $result_drug_type;
	}
	
	public function getDisease() {
		$result_disease = mysqli_query($this->db->connect(),"SELECT * FROM sub_disease ORDER BY name ASC") or die(mysqli_error($this->db->connect()));
		return $result_disease;
	}
	public function getDrugs() {
		$result_drug = mysqli_query($this->db->connect(),"SELECT * FROM drugs ORDER BY name ASC") or die(mysqli_error($this->db->connect()));
		return $result_drug;
	}
	public function getManufacturer() {
		$result_manufacturer = mysqli_query($this->db->connect(),"SELECT * FROM manufacturer ORDER BY name ASC") or die(mysqli_error($this->db->connect()));
		return $result_manufacturer;
	}
	public function getGenericNames() {
		$result_generic = mysqli_query($this->db->connect(),"SELECT * FROM generic_names ORDER BY name ASC") or die(mysqli_error($this->db->connect()));
		return $result_generic;
	}
	
	
	/**
	 * Get user by email and password
	 */
	public function getUserByEmailAndPassword($email, $password) {
		$result = mysqli_query($this->db->connect(),"SELECT * FROM doctors WHERE email = '$email'") or die(mysqli_error($this->db->connect()));
		// check for result
		$no_of_rows = mysqli_num_rows($result);
		if ($no_of_rows > 0) {
			$result = mysqli_fetch_array($result);
			$salt = $result['salt'];
			$encrypted_password = $result['encrypted_password'];
			$hash = $this->checkhashSSHA($salt, $password);
			// check for password equality
			if ($encrypted_password == $hash) {
				// user authentication details are correct
				return $result;
			}
		} else {
			// user not found
			return false;
		}
	}

	/**
	 * Check user is existed or not
	 */
	public function isUserExisted($email) {
		$result = mysqli_query($this->db->connect(),"SELECT * FROM doctors WHERE email = '$email'") or die(mysqli_error($this->db->connect()));
		$records = mysqli_num_rows($result);
		//echo $records;
		if ($records > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Encrypting password
	 * @param password
	 * returns salt and encrypted password
	 */
	public function hashSSHA($password) {

		$salt = sha1(rand());
		$salt = substr($salt, 0, 10);
		$encrypted = base64_encode(sha1($password . $salt, true) . $salt);
		$hash = array("salt" => $salt, "encrypted" => $encrypted);
		return $hash;
	}

	/**
	 * Decrypting password
	 * @param salt, password
	 * returns hash string
	 */
	public function checkhashSSHA($salt, $password) {

		$hash = base64_encode(sha1($password . $salt, true) . $salt);

		return $hash;
	}

}

?>