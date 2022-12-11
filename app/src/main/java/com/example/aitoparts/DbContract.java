package com.example.aitoparts;

public class DbContract {
    private static String serverIP="192.168.0.7";

    public static final String SERVER_LOGIN_URL="http://"+ serverIP +"/loginAPI/checkLogin.php";
    public static final String SERVER_REGISTER_URL="http://"+ serverIP +"/loginAPI/createData.php";
    public static final String SERVER_LOADPARTS_URL="http://"+ serverIP +"/loginApi/loadParts.php";
    public static final String SERVER_LOADTIPS_URL="http://"+ serverIP +"/loginApi/loadTips.php";
    public static final String SERVER_LOADTIPSPAGE_URL="http://"+ serverIP +"/loginApi/loadTipsPage.php";
}
