<%@ page contentType="text/html; charset=UTF-8" %>
<html>
<head><title>Add Headline</title></head>
<body bgcolor="white">
<h3>Add Headline Form</h3>
<form name="headlineInfo" action="/framework/headline/add" method="get">
    Line Name: <input type="text" name="lineName"><br><br>
    Line Link: <input type="text" name="lineLink"><br><br>
    Line Img: <input type="text" name="lineImg"><br><br>
    Priority: <input type="text" name="priority"><br><br>
    <input type="submit" name="Submit" value="Submit"><br><br>

    Result: Status Code: ${result.code} Mgs: ${result.msg}<br>
</form>

</body>