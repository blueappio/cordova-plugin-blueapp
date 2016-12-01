package io.blueapp.webbluetooth;

import android.content.Context;
import android.util.Log;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.gatt_ip.GATTIP;
import org.gatt_ip.GATTIPListener;
import org.json.JSONArray;
import org.json.JSONException;

public class IOBlueApp extends CordovaPlugin {
    public static String TAG = "IOBlueApp";

    private GATTIP gattip;

    /**
     * Constructor.
     */
    public IOBlueApp() {
        Log.d(TAG, "IOBlueApp()");
    }

    @Override
    public boolean execute(String action, JSONArray data, final CallbackContext callbackContext) throws JSONException {

        switch (action) {
            case "init":
                Log.d(TAG, "init()");
                if(gattip == null) {
                    Context context = this.cordova.getActivity().getApplicationContext();
                    gattip = new GATTIP(context);

                    gattip.setGATTIPListener(new GATTIPListener() {
                        @Override
                        public void response(String gattipResponse) {
                            Log.d(TAG, "IOBlueApp Response  >>>>>>>>>> : " + gattipResponse);
                            PluginResult result = new PluginResult(PluginResult.Status.OK, gattipResponse);
                            result.setKeepCallback(true);
                            callbackContext.sendPluginResult(result);
                        }
                    });
                }
                return true;
            case "message":
                String gattipRequest = data.getString(0);

                if (gattipRequest != null) {
                    try {
                        Log.d(TAG, "IOBlueApp Request <<<<<<<<<<< : " + gattipRequest);                        
                        gattip.request(gattipRequest);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return true;
            case "close":
                Log.d(TAG, "close()");
                gattip = null;
                return true;
            default:
                Log.w(TAG, "Invalid ACTION : " + action);            
                return false;
        }
    }

}
