<?php
include 'db.php';
require_once 'dataquery.php';
$dataquery = new dataquery();
?>
<!DOCTYPE html>
<html>
    <head>
        Ads2Tex Doctor
        </head>
        <title>Ads2Tex Doctor</title>
       <body>
       <p>
       <?php

       $Search_text="Amidol";
       if($Search_text == "")
       {
           $result_search=$dataquery->getWithoutSearchDrugs(0,10);
       }else{
           $result_search=$dataquery->getSearchDrugs($Search_text,0,10);
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
       echo json_encode($posts_search);

       ?>
       </p>
    </body>
</html>
