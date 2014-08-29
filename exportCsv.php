<?php

    $filename="Filename".date("YmdHis").".xls";   // 建立檔名
    header("Content-type:application/vnd.ms-excel; charset=utf-8" ); // 送出header
    header("Content-Disposition:filename=$filename");  // 指定檔名€

echo "<html>";
echo '<head><meta http-equiv="content-type" content="application/vnd.ms-excel; charset=UTF-8"></head>'."\n";
echo '<body>';
 
    echo "<table>";
    echo $_POST['data'];
    echo "</table>";
 
echo '</body></html>';
?>