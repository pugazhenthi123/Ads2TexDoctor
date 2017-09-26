<?php

/**
 * File to handle all API requests
 * Accepts GET and POST
 *
 * Each request will be identified by TAG
 * Response will be JSON data
 */

/**
 * check for POST request
 */
if (isset($_POST['tag']) && $_POST['tag'] != '') {
	// get tag
	$tag = $_POST['tag'];

	// include db handler
	include_once 'db.php';
	require_once 'db_functions.php';
	$db = new db_functions();

	// response Array
	$response = array("tag" => $tag, "error" => FALSE);

	// check for tag type
	if ($tag == 'slider') {
		$result = mysqli_query($con,"SELECT * FROM banner_app ORDER BY img ASC") or die(mysqli_error($con));
		$resultt = mysqli_fetch_row($result);
		$cnt = $resultt[0];
		if ($cnt == 0) {
			$posts = array();
			if (mysqli_num_rows($result)) {
				while ($post = mysqli_fetch_assoc($result)) {
					$posts[] = $post;
				}
			}
			$response["status"] = 'notfound';
			$response["result"] = $posts;
			$response["error"] = TRUE;
			$response["title"] = 'Ads2Tex Doctor';
			$response["header"] = 'Ads2Tex Browser';
		} else {
			$result = mysqli_query($con,"SELECT * FROM banner_app ORDER BY img ASC") or die(mysqli_error($con));
			$posts = array();
			if (mysqli_num_rows($result)) {
				while ($post = mysqli_fetch_assoc($result)) {
					$posts[] = $post;
				}
			}
			$response["status"] = 'found';
			$response["result"] = $posts;
			$response["error"] = FALSE;
			$response["title"] = 'First & Best Doctor Mobile App';
			$response["header"] = 'Ads2Tex Browser';
			
		}
		echo json_encode($response);

	} else {
		// user failed to store
		$response["error"] = TRUE;
		$response["msg"] = "Unknow 'tag' value. It should be 'Slider'";
		echo json_encode($response);
	}
} else {
	$response["error"] = TRUE;
	$response["msg"] = "Required parameter 'tag' is missing!";
	echo json_encode($response);
}
?>