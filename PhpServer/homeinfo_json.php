<?php 
include 'db.php';

if (isset($_POST['tag']) && $_POST['tag'] != '') {
	// get tag
	$tag = $_POST['tag'];

	// include db handler
	require_once 'db_functions.php';
	require_once 'dataquery.php';
	$db = new db_functions();
	$dataquery = new dataquery();

	// response Array
	$response = array("tag" => $tag, "error" => FALSE);

	// check for tag type
	if ($tag == 'SpinnerAll') {
		$hospital = $_POST['hospital'];
		// check for area
		// $result = $db->getArea();
		$response["link"]= 'https://play.google.com/store/apps/details?id=com.ads2tex.ads2texdoctor';
		$response["VersionName"] = '1.0.0';
		$response["Update"] = 'Update Alert Dialog Message';
		$response["About"] = 'Ads2Tex Doctor App is Used for Doctor to prepare Prescription digitally.It helps to Retrieve the Tablet by different optimized Search .		
		 
Helpline:+91 8526061474';
	 	$response["Share"] = 'I found Ads2Tex Doctor App. Its nice to find tablets by different optimized Search.Download App';	
	 	$response["comp"] = TRUE;
	 	$response["cboard"] = 'Update Alert Dialog Title';

		$result_patient = $db->getPatient($hospital);
		$posts_patient = array();
		if (mysqli_num_rows($result_patient)) {
			while ($post_patient = mysqli_fetch_assoc($result_patient)) {
				$posts_patient[] = $post_patient;
			}
		}
		$response["result_patient"] = $posts_patient;

		$result_drug_unit = $db->getDrugUnit();
		$posts_drug_unit = array();
		if (mysqli_num_rows($result_drug_unit)) {
			while ($post_drug_unit = mysqli_fetch_assoc($result_drug_unit)) {
				$posts_drug_unit[] = $post_drug_unit;
			}
		}
		$response["result_drug_unit"] = $posts_drug_unit;

		$result_patient_status = $db->getPatientStatus();
		$posts_patient_status = array();
		if (mysqli_num_rows($result_patient_status)) {
			while ($post_patient_status = mysqli_fetch_assoc($result_patient_status)) {
				$posts_patient_status[] = $post_patient_status;
			}
		}
		$response["result_patient_status"] = $posts_patient_status;

		$result_drug_type = $db->getDrugType();
		$posts_drug_type = array();
		if (mysqli_num_rows($result_drug_type)) {
			while ($post_drug_type = mysqli_fetch_assoc($result_drug_type)) {
				$posts_drug_type[] = $post_drug_type;
			}
		}
		$response["result_drug_type"] = $posts_drug_type;

		$result_disease = $db->getDisease();
		$posts_disease = array();
		if (mysqli_num_rows($result_disease)) {
			while ($post_disease = mysqli_fetch_assoc($result_disease)) {
				$posts_disease[] = $post_disease;
			}
		}
		$response["result_disease"] = $posts_disease;

		$result_drug = $db->getDrugs();
		$posts_drug = array();
		if (mysqli_num_rows($result_drug)) {
			while ($post_drug = mysqli_fetch_assoc($result_drug)) {
				$posts_drug[] = $post_drug;
			}
		}
		$response["result_drug"] = $posts_drug;

		$result_manufacturer = $db->getManufacturer();
		$posts_manufacturer = array();
		if (mysqli_num_rows($result_manufacturer)) {
			while ($post_manufacturer = mysqli_fetch_assoc($result_manufacturer)) {
				$posts_manufacturer[] = $post_manufacturer;
			}
		}
		$response["result_manufacturer"] = $posts_manufacturer;

		$result_generic = $db->getGenericNames();
		$posts_generic = array();
		if (mysqli_num_rows($result_generic)) {
			while ($post_generic = mysqli_fetch_assoc($result_generic)) {
				$posts_generic[] = $post_generic;
			}
		}
		$response["result_generic"] = $posts_generic;

		$response["error"] = FALSE;		
		echo json_encode($response);

	} else if ($tag == 'getbill') {
		$cus_no = $_POST['sno'];
		$gbcompany = $_POST['company'];
		$np = "NotPaid";
		$result_bill = mysqli_query($con,"SELECT * from cus_bill where cus_no='" . $cus_no. "' and status = '" .$np."' and company = '" .$gbcompany. "' ORDER BY date DESC");
		$posts_bill = array();
		if (mysqli_num_rows($result_bill)) {
			while ($post_bill = mysqli_fetch_assoc($result_bill)) {
				$posts_bill[] = $post_bill;
			}
		}
		$response["resultbill"] = $posts_bill;
		$response["error"] = FALSE;
		echo json_encode($response);
		
	} else if ($tag == 'updateimei') {
		$Imei = $_POST['imei'];
		$UserId = $_POST['userId'];
		$Login = true;
		$update=mysqli_query($con,"update doctors set sno='".$UserId."',login='".$Login."' where imei_no='".$Imei."'");
		$response["error"] = FALSE;
		echo json_encode($response);
	}else if ($tag == 'receivedata') {
		$Imei = $_POST['imei'];
		$Login = true;
		$doctor_sql = mysqli_query($con,"select * FROM doctors where login = '".$Login."' and imei_no = '" . $Imei . "'");
		$fetch_doctor_sql = mysqli_fetch_array($doctor_sql);
		$no_of_users = mysqli_num_rows($doctor_sql);
		if($no_of_users > 0)
		{
			$sno = $fetch_doctor_sql['sno'];
			$users = mysqli_query($con,"select * FROM doctors where sno = '".$sno."'");
			$fetch_users = mysqli_fetch_array($users);
			$select_logo = mysqli_query($con,"Select * from hospital where sno = '".$fetch_users['hospital_no']."'");
			$fetch_logo = mysqli_fetch_array($select_logo);
			$response["sno"] = $sno;
			$response["email"] = $fetch_users['email'];
			$response["pass"] = $fetch_users['pass'];
			$response["hospital_logo_link"] = $fetch_logo['logo'];
			$response["hospital"] = $fetch_users['hospital_no'];
			$response["error"] = FALSE;		
		}
		else
		{
			$response["error"] = TRUE;
		}
		echo json_encode($response);
	}else if ($tag == 'logout') {
		$Imei = $_POST['imei'];
		$Login = 0;
		$update=mysqli_query($con,"update doctors set login='".$Login."' where imei_no='".$Imei."'");
		$response["error"] = FALSE;
		echo json_encode($response);
	}
	else if($tag == 'drug_search')
	{
		$start = $_POST['offset'];
		$number_of_posts = $_POST['count'];
		$result_search = "";
		$Search_text = $_POST['search'];
		if($Search_text == "")
		{
			$result_search=$dataquery->getWithoutSearchDrugs($start,$number_of_posts);
		}else{
			$result_search=$dataquery->getSearchDrugs($Search_text,$start,$number_of_posts);
		}
		$posts_search = array();
		if (mysqli_num_rows($result_search)!=0) {
			while ($post_search = mysqli_fetch_assoc($result_search)) {
				$posts_search[] = $post_search;
				$drug_unit=$dataquery->getDrugUnit($post_search['sno']);
				$drug_unit_posts = array();
				while ($drug_unit_post = mysqli_fetch_assoc($drug_unit)) {
					$drug_unit_posts[] = $drug_unit_post;
				}
				$response[$post_search['sno']]=$drug_unit_posts;
			}
			$response["status"] = 'found';
		}else{
			$response["status"] = 'notfound';
		}
		$response["count"] = mysqli_num_rows($result_search);
		$response["search"] = $posts_search;
		$response["error"] = FALSE;
		echo json_encode($response);
	}
	else if ($tag == 'patient_records') {
		$Patient_no = $_POST['patientno'];
		$Doctor_no = $_POST['doctorno'];
		$list = $_POST['list'];
		$Hospital_no = $_POST['hospital'];
		$start = $_POST['offset'];
		$number_of_posts = $_POST['count'];
		if($list == 0)
		{
			$Patient_token_no = $dataquery->getDoctor($Doctor_no)['token_no'];
			$Patient_no = $dataquery->getToken($Patient_token_no)['patient_no'];
		}
		$patient_records_with_limit = $dataquery->getPatientWithLimit($Patient_no,$Hospital_no,$start,$number_of_posts);
		$patient_records_with_limit_count = $dataquery->getPatientWithLimitCount($Patient_no,$Hospital_no,$start,$number_of_posts);
		$patient_records_count = $dataquery->getPatientCount($Patient_no,$Hospital_no);
		$posts = array();
		if ($patient_records_with_limit_count) {
			while ($post = mysqli_fetch_assoc($patient_records_with_limit)) {
				$posts[] = $post;
				$patient_prescription=$dataquery->getPatientPrescription($Patient_no,$Hospital_no);
				$patient_prescription_posts = array();
				while ($patient_prescription_post = mysqli_fetch_assoc($patient_prescription)) {
					$patient_prescription_posts[] = $patient_prescription_post;
				}
				$response[$post['sno']]=$patient_prescription_posts;
				}$response["status"] = 'found';
		}else
		{
			$response["status"] = 'notfound';
		}
		$response["count"] = $patient_records_count;
		$response["result"] = $posts;
		$response["error"] = FALSE;
		echo json_encode($response);

	} else {
		// user failed to store
		$response["error"] = TRUE;
		$response["msg"] = "Unknow 'tag' value. It should be either 'area' or 'field' or 'job' or 'jobrecords'";
		echo json_encode($response);
	}
} else {
	$response["error"] = TRUE;
	$response["msg"] = "Required parameter 'tag' is missing!";
	echo json_encode($response);
}
?>