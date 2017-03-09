package chk.kingnet.app.plugins;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;

import chk.kingnet.app.control.BitmapUtils.ScreenShot;
import chk.kingnet.app.control.RemindToast;

/**
 * Created by clery on 2017/3/9.
 */

public class DataTransportPlugin extends CordovaPlugin {


    private static final String ACTION_screen = "getscreenshot";


    private CallbackContext callbackContext;

    private Context context;

    private final String [] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private static final int REQUEST_CAMERA_PERMISSION = 200;

    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        try {

            Log.d("--0", "-----");
            if(ACTION_screen.equals(action)){

                Log.d("--1", "-----");
                context = cordova.getActivity().getApplicationContext();

                this.callbackContext = callbackContext;

                int permission2 = ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);

                if (permission2 != PackageManager.PERMISSION_GRANTED) {
                    // 無權限，向使用者請求
                    cordova.requestPermissions(this, REQUEST_CAMERA_PERMISSION, permissions);

                }else{
                    getScreenShot();
                }
                return true;
            }
            else{
                callbackContext.error("Invalid action");
                return false;
            }

        } catch (Exception e) {

            callbackContext.error(e.getMessage());
            return false;
        }
    }

    private void getScreenShot(){
        cordova.getActivity().runOnUiThread(new Runnable() {
            public void run() {
                ScreenShot screenShot = new ScreenShot(cordova.getActivity());
                Bitmap screenBitmap = screenShot.getScreenBitmap();

                if(screenBitmap != null){
                    screenShot.SaveScreenBitmap(screenBitmap);
                    screenShot.GalleryAddPic();

                    RemindToast.showText(context,"畫面擷取成功");

                    String str = "Success";

                    getPluginResult(str);

                }else{
                    RemindToast.showText(context,"發生不明原因，畫面擷取失敗");
                    callbackContext.error("error");
                }
            }
        });
    }

    @Override
    public void onRequestPermissionResult(int requestCode, String[] permissions, int[] grantResults) throws JSONException {
        super.onRequestPermissionResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                getScreenShot();

            } else {
                RemindToast.showText(context,"無法擷取畫面，請確認權限");
                callbackContext.error("error");
            }
        }
    }
    //通过PluginResult和callbackContext返回给js接口
    private void getPluginResult(String string){
        PluginResult pluginResults = new PluginResult(PluginResult.Status.OK, string);
        callbackContext.sendPluginResult(pluginResults);
        pluginResults.setKeepCallback(true);
        callbackContext.success();
    }
}
