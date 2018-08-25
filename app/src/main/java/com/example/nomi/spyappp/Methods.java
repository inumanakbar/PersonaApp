package com.example.nomi.spyappp;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static android.content.Context.ACTIVITY_SERVICE;

/**
 * Created by AK on 26/01/2018.
 */

public class Methods {
    static boolean isEnabled = false;

    public static String getCurrentDate(){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = df.format(c.getTime());
        return formattedDate;
    }

    public static String getCurrentDateServerFormat(){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = df.format(c.getTime());
        return formattedDate;
    }

    public static String getCurrentTime(){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("hh:mm a");
        String formattedDate = df.format(c.getTime());
        return formattedDate;
    }

    public static String getCurrentTime24HrFormat(){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("HH:mm a");
        String formattedDate = df.format(c.getTime());
        return formattedDate;
    }

    public static long getCurrentTimeInSeconds(){
        Calendar c = Calendar.getInstance();
        return c.getTimeInMillis();
    }

    public static int timeDifference(String d1, String d2){
        int totalMins = 0;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
            Date startDate = simpleDateFormat.parse(d1);
            Date endDate = simpleDateFormat.parse(d2);

            long difference = endDate.getTime() - startDate.getTime();
            if (difference < 0) {
                Date dateMax = simpleDateFormat.parse("24:00");
                Date dateMin = simpleDateFormat.parse("00:00");
                difference = (dateMax.getTime() - startDate.getTime()) + (endDate.getTime() - dateMin.getTime());
            }
            int days = (int) (difference / (1000 * 60 * 60 * 24));
            int hours = (int) ((difference - (1000 * 60 * 60 * 24 * days)) / (1000 * 60 * 60));
            int min = (int) (difference - (1000 * 60 * 60 * 24 * days) - (1000 * 60 * 60 * hours)) / (1000 * 60);
            totalMins = (hours * 60) + min;
        }
        catch (ParseException ex){
            Log.e("log_tag",ex.getLocalizedMessage());
        }
        return totalMins;
    }

    public static long timeDifferenceInSeconds(String d1, String d2){
        int totalMins = 0;
        long totalSeconds = 0;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
            Date startDate = simpleDateFormat.parse(d1);
            Date endDate = simpleDateFormat.parse(d2);

            long difference = endDate.getTime() - startDate.getTime();
            if (difference < 0) {
                Date dateMax = simpleDateFormat.parse("24:00");
                Date dateMin = simpleDateFormat.parse("00:00");
                difference = (dateMax.getTime() - startDate.getTime()) + (endDate.getTime() - dateMin.getTime());
            }
            int days = (int) (difference / (1000 * 60 * 60 * 24));
            int hours = (int) ((difference - (1000 * 60 * 60 * 24 * days)) / (1000 * 60 * 60));
            int min = (int) (difference - (1000 * 60 * 60 * 24 * days) - (1000 * 60 * 60 * hours)) / (1000 * 60);
            totalMins = (hours * 60) + min;
            totalSeconds = TimeUnit.MINUTES.toSeconds(totalMins);
        }
        catch (ParseException ex){
            Log.e("log_tag",ex.getLocalizedMessage());
        }
        return totalSeconds;
    }

    public static String formatHoursAndMinutes(int totalMinutes) {
        String minutes = Integer.toString(totalMinutes % 60);
        minutes = minutes.length() == 1 ? "0" + minutes : minutes;
        return (totalMinutes / 60) + " hours ago";
    }

    public static String daysBetween(String inputString1,String inputString2){
        SimpleDateFormat myFormat = new SimpleDateFormat("dd-MM-yyyy");
        long diff = 0;
        try {
            Date date1 = myFormat.parse(inputString1);
            Date date2 = myFormat.parse(inputString2);
            diff = date2.getTime() - date1.getTime();
        } catch (ParseException e) {
            Log.e("error",e.getLocalizedMessage());
        }
        return "" + TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS) +" days ago";
    }

    public static String daysToday(String inputString1,String inputString2){
        SimpleDateFormat myFormat = new SimpleDateFormat("dd-MM-yyyy");
        long diff = 0;
        try {
            Date date1 = myFormat.parse(inputString1);
            Date date2 = myFormat.parse(inputString2);
            diff = date2.getTime() - date1.getTime();
        } catch (ParseException e) {
            Log.e("error",e.getLocalizedMessage());
        }
        return "Day: " + (TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS) + 1);
    }

    public static long daysDifference(String inputString1,String inputString2){
        SimpleDateFormat myFormat = new SimpleDateFormat("dd-MM-yyyy");
        long diff = 0;
        try {
            Date date1 = myFormat.parse(inputString1);
            Date date2 = myFormat.parse(inputString2);
            diff = date2.getTime() - date1.getTime();
        } catch (ParseException e) {
            Log.e("error",e.getLocalizedMessage());
        }
        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }

    public static double findDistance(Location loc1, Location loc2){
        double distance=loc1.distanceTo(loc2);
        return distance;
    }

    public static double calculateDistance(double lat1, double lon1, double lat2, double lon2)
    {
        final int earthRadius = 6371;
        double dLat = (double) Math.toRadians(lat2 - lat1);
        double dLon = (double) Math.toRadians(lon2 - lon1);
        double a =
                (double) (Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(Math.toRadians(lat1))
                        * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2) * Math.sin(dLon / 2));
        double c = (double) (2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a)));
        double d = earthRadius * c;
        return d;
    }

    public static void isLocationEnabled(final Activity ac, LocationManager locationManager) {
        /*if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            AlertDialog.Builder alertDialog=new AlertDialog.Builder(ac);
            alertDialog.setTitle("Enable Location");
            alertDialog.setMessage("Your locations setting is not enabled. Please enabled it in settings menu.");
            alertDialog.setPositiveButton("Location Settings", new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface dialog, int which){
                    Intent intent=new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    ac.startActivity(intent);
                }
            });
            alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface dialog, int which){
                    dialog.cancel();
                    DashboardActivity dac = (DashboardActivity) ac;
                    dac.permissionNotGranted();
                }
            });
            AlertDialog alert=alertDialog.create();
            alert.show();
        }*/
    }

    public static Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public static void clearAppData(Activity ac) {
        try {
            // clearing app data
            if (Build.VERSION_CODES.KITKAT <= Build.VERSION.SDK_INT) {
                ((ActivityManager)ac.getSystemService(ACTIVITY_SERVICE)).clearApplicationUserData(); // note: it has a return value!
            } else {
                String packageName = ac.getPackageName();
                Runtime runtime = Runtime.getRuntime();
                runtime.exec("pm clear "+packageName);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
