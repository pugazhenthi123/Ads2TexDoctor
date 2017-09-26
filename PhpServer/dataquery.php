<?php

class dataquery
{
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

    public function getToken($sno) {
        $result_sql1 = mysqli_query($this->db->connect(),"SELECT * FROM token WHERE sno = '".$sno."'") or die(mysqli_error($this->db->connect()));
        $result_sql2 = mysqli_fetch_array($result_sql1);
        return $result_sql2;
    }
    public function getDoctor($sno) {
        $result_sql1 = mysqli_query($this->db->connect(),"SELECT * FROM doctors WHERE sno = '".$sno."'") or die(mysqli_error($this->db->connect()));
        $result_sql2 = mysqli_fetch_array($result_sql1);
        return $result_sql2;
    }
    public function getPatient($sno,$hospital) {
        $result_sql1 = mysqli_query($this->db->connect(),"SELECT * FROM patient WHERE sno = '".$sno."' and hospital_no = '".$hospital."'") or die(mysqli_error($this->db->connect()));
        $result_sql2 = mysqli_fetch_array($result_sql1);
        return $result_sql2;
    }
    public function getPatientCount($sno,$hospital) {
        $result_sql1 = mysqli_query($this->db->connect(),"SELECT * FROM patient WHERE sno = '".$sno."' and hospital_no = '".$hospital."'") or die(mysqli_error($this->db->connect()));
        $result_sql2 = mysqli_num_rows($result_sql1);
        return $result_sql2;
    }
    public function getPatientWithLimitArray($sno,$hospital,$start,$number_of_posts) {
        $result_sql1 = mysqli_query($this->db->connect(),"SELECT * FROM patient WHERE sno = '".$sno."' and hospital_no = '".$hospital."' ORDER BY name ASC LIMIT $start,$number_of_posts") or die(mysqli_error($this->db->connect()));
        $result_sql2 = mysqli_fetch_array($result_sql1);
        return $result_sql2;
    }
    public function getPatientWithLimit($sno,$hospital,$start,$number_of_posts) {
        $result_sql1 = mysqli_query($this->db->connect(),"SELECT * FROM patient WHERE sno = '".$sno."' and hospital_no = '".$hospital."' ORDER BY name ASC LIMIT $start,$number_of_posts") or die(mysqli_error($this->db->connect()));
        return $result_sql1;
    }
    public function getPatientWithLimitCount($sno,$hospital,$start,$number_of_posts) {
        $result_sql1 = mysqli_query($this->db->connect(),"SELECT * FROM patient WHERE sno = '".$sno."' and hospital_no = '".$hospital."' ORDER BY name ASC LIMIT $start,$number_of_posts") or die(mysqli_error($this->db->connect()));
        $result_sql2 = mysqli_num_rows($result_sql1);
        return $result_sql2;
    }
    public function getPatientPrescription($patient_no,$hospital) {
        $result_sql1 = mysqli_query($this->db->connect(),"SELECT * FROM prescription WHERE patient_no = '".$patient_no."' and hospital_no = '".$hospital."' ORDER BY date ASC") or die(mysqli_error($this->db->connect()));
        return $result_sql1;
    }
    public function getSearchDrugs($search,$start,$number_of_posts) {
        $result_sql1 = mysqli_query($this->db->connect(),"SELECT * FROM drugs WHERE name like '%".$search."%' or generic_search like '%".$search."%' or manufacturer_search like '%".$search."%' or unit_search like '%".$search."%' or diseases_search like '%".$search."%' ORDER BY name ASC LIMIT $start,$number_of_posts") or die(mysqli_error($this->db->connect()));
        return $result_sql1;
    }
    public function getWithoutSearchDrugs($start,$number_of_posts) {
        $result_sql1 = mysqli_query($this->db->connect(),"SELECT * FROM drugs LIMIT $start,$number_of_posts") or die(mysqli_error($this->db->connect()));
        return $result_sql1;
    }
    public function getDrugUnit($drug_no)
    {
        $result_drug_unit = mysqli_query($this->db->connect(),"SELECT * FROM drug_unit WHERE drug_no = '".$drug_no."'") or die(mysqli_error($this->db->connect()));
        return $result_drug_unit;
    }
    
}
?>