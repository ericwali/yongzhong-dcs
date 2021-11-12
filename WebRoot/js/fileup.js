///<reference path="jquery-1.11.1.js" />
function fileSelected(ctlID) {
    var file = document.getElementById(ctlID).files[0];
    if (file) {
        var fileSize = 0;
        if (file.size > 1024 * 1024)
            fileSize = (Math.round(file.size * 100 / (1024 * 1024)) / 100).toString() + 'MB';
        else
            fileSize = (Math.round(file.size * 100 / 1024) / 100).toString() + 'KB';

        document.getElementById('uploadPross').innerHTML = file.name + "(" + fileSize + ")";
        //document.getElementById('fileType').innerHTML = 'Type: ' + file.type;
    }
}
function uploadFile(ctlID, actionPage) {
    var fd = new FormData();
    fd.append(ctlID, document.getElementById(ctlID).files[0]);
    fd.append("__RequestVerificationToken", document.getElementsByName("__RequestVerificationToken")[0].value);
    fd.append("noFresh", true);
    var xhr = new XMLHttpRequest();
    xhr.upload.addEventListener("progress", uploadProgress, false);
    xhr.addEventListener("load", uploadComplete, false);
    xhr.addEventListener("error", uploadFailed, false);
    xhr.addEventListener("abort", uploadCanceled, false);
    xhr.open("POST", actionPage);
    $("#upfilebtn").hide();
    $("#probar").show();
    $("#probar").next("div").show();
    xhr.send(fd);
}

function uploadProgress(evt) {
    if (evt.lengthComputable) {
        var width1 = parseInt($("#probar").width());
        var percentComplete = Math.round(evt.loaded * 100 / evt.total);
        if (percentComplete.toString() == "100") {
            $("#probar").next("div").text("马上就好……");
        } else {
            $("#probar").next("div").text(percentComplete.toString() + '%');
        }
        
        $("#probar>div").css("width", width1 * evt.loaded / evt.total);
    }
    else {
        $("#probar").next("div").text("上传中……");
    }
}

function uploadComplete(evt) {

    /* This event is raised when the server send back a response */
    var txt = evt.target.responseText.toString(); //var txt = "";
    if (txt.substr(0, 1) == "!") {
        document.getElementById("showmsg").innerText = txt.substr(1,txt.length-1);
    } else {
        $("#viewFrame").attr("src", txt);
        $("#showViewDiv").prev("div").hide();
        $("#showViewDiv").slideDown("slow");
        $("#upfilebtn").show();
        $("#probar").hide();
        $("#probar").next("div").hide();
    }
    //alert(evt.target.responseText);
}

function uploadFailed(evt) {
    alert("There was an error attempting to upload the file.");
}

function uploadCanceled(evt) {
    alert("The upload has been canceled by the user or the browser dropped the connection.");
}
function canvasSupported() {
    var canvas = document.createElement('canvas');
    return (canvas.getContext && canvas.getContext('2d'));

}