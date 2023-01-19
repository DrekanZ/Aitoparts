package com.example.aitoparts;

public class DbContract {
    public static final String localIp = "192.168.0.4";
    public static final String SERVER_LOGIN_URL="http://" + localIp + "/aitoparts/mobileAPI/checkLogin.php";
    public static final String SERVER_REGISTER_URL="http://" + localIp + "/aitoparts/mobileAPI/createData.php";
    public static final String SERVER_LOADPARTS_URL="http://" + localIp + "/aitoparts/mobileAPI/loadParts.php";
    public static final String SERVER_LOADTIPS_URL="http://" + localIp + "/aitoparts/mobileAPI/loadTips.php";
    public static final String SERVER_LOADTIPSPAGE_URL="http://" + localIp + "/aitoparts/mobileAPI/loadTipsPage.php";
    public static final String SERVER_GETUSERCRED_URL="http://" + localIp + "/aitoparts/mobileAPI/getUsername.php";
    public static final String SERVER_LOADBOOK_URL="http://" + localIp + "/aitoparts/mobileAPI/loadBook.php";
    public static final String SERVER_LOADSESI_URL="http://" + localIp + "/aitoparts/mobileAPI/loadSesi.php";
    public static final String SERVER_LOADPAKET_URL="http://" + localIp + "/aitoparts/mobileAPI/loadPaket.php";
    public static final String SERVER_GETMEKANIK_URL="http://" + localIp + "/aitoparts/mobileAPI/getMekanik.php";
    public static final String SERVER_BOOKINGBARU_URL="http://" + localIp + "/aitoparts/mobileAPI/bookingBaru.php";
    public static final String SERVER_BOOKINGSUCCESS_URL="http://" + localIp + "/aitoparts/mobileAPI/bookingBaruSuccess.php";
    public static final String SERVER_LOADRIWAYAT_URL="http://" + localIp + "/aitoparts/mobileAPI/loadHistory.php";
    public static final String SERVER_UPLOADPROFILEIMAGE_URL="http://" + localIp + "/aitoparts/mobileAPI/uploadImage.php";
    public static final String SERVER_GETPROFILEIMAGE_URL="http://" + localIp + "/aitoparts/mobileAPI/getProfileImage.php";
    public static final String SERVER_LUPAPASSWORD_URL="http://" + localIp + "/aitoparts/mobileAPI/checkLupaPassword.php";
    public static final String SERVER_CHANGEPASSWORD_URL="http://" + localIp + "/aitoparts/mobileAPI/changePassword.php";
}
