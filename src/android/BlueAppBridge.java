package io.blueapp;

import android.content.Context;
import android.util.Log;

import com.vensi.connect.MainActivity;

import org.apache.cordova.*;
import org.gatt_ip.GATTIP;
import org.gatt_ip.GATTIPListener;
import org.json.JSONArray;
import org.json.JSONException;

public class BlueAppBridge extends CordovaPlugin {
    public static String TAG = "BlueAppBridge";

    @Override
    public boolean execute(String action, JSONArray data, final CallbackContext callbackContext) throws JSONException {

        switch (action) {
            case "init":
                MainActivity.gattip.setGATTIPListener(new GATTIPListener() {
                    @Override
                    public void response(String gattipResponse) {
                        MainActivity.showLogs(Log.DEBUG, TAG, "Response  >>>>>>>>>> : " + gattipResponse);
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
                        MainActivity.showLogs(Log.DEBUG, TAG, "Request <<<<<<<<<< : " + gattipRequest);
                        MainActivity.gattip.request(gattipRequest);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return true;
            default:
                MainActivity.showLogs(Log.WARN, TAG, "Invalid ACTION : " + action);
                return false;
        }
    }

}
