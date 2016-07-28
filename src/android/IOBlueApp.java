package cordova.plugin.blueapp;

import android.content.Context;
import android.util.Log;

import com.vensi.connect.MainActivity;

import org.apache.cordova.*;
import org.gatt_ip.GATTIP;
import org.gatt_ip.GATTIPListener;
import org.json.JSONArray;
import org.json.JSONException;

public class IOBlueApp extends CordovaPlugin {
    public static String TAG = "IOBlueApp";

    @Override
    public boolean execute(String action, JSONArray data, final CallbackContext callbackContext) throws JSONException {

        switch (action) {
            case "init":
                MainActivity.gattip.setGATTIPListener(new GATTIPListener() {
                    @Override
                    public void response(String gattipResponse) {
                        Log.d(TAG, "IOBlueApp Response  >>>>>>>>>> : " + gattipResponse);
                        PluginResult result = new PluginResult(PluginResult.Status.OK, gattipResponse);
                        result.setKeepCallback(true);
                        callbackContext.sendPluginResult(result);
                    }
                });
                return true;
            case "message":
                String gattipRequest = data.getString(0);

                if (gattipRequest != null) {
                    try {
                        Log.d(TAG, "IOBlueApp Request <<<<<<<<<<< : " + gattipRequest);                        
                        MainActivity.gattip.request(gattipRequest);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return true;
            default:
                Log.w(TAG, "Invalid ACTION : " + action);            
                return false;
        }
    }

}
