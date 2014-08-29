function updatePeriod(p, os){
    switch(p){
        case "daily":
            updateData();
            break;
        case "weekly":
            updateData();
            break;
        case "monthly":
            var date_start_input = document.getElementById("date_start");
            var d = date_start_input.value;
            date_start_input.value = d.substring(0,2)+"/"+"01"+"/"+d.substring(6,10);
            updateData();
            break;
        case "year":
            var date_start_input = document.getElementById("date_start");
            var d = date_start_input.value;
            // var year = parseInt(d.substring(6,10)) - 1;
            if(os == "Android"){
                date_start_input.value = "01"+"/"+"01"+"/"+"2013";
            }else if(os == "iOS"){
                date_start_input.value = "06"+"/"+"03"+"/"+"2013";
                alert("itunes 最舊的資料只到2013/06/03\n\r建議到Android功能區查詢更舊的資料。")
            }else if(os == "All"){
                date_start_input.value = "06"+"/"+"03"+"/"+"2013";
                alert("itunes 最舊的資料只到2013/06/03\n\r建議到Android功能區查詢更舊的資料。")
            }else{
                alert("Not exist os type.");
            }
            updateData();
            break;
        default:
            break;
    }
}