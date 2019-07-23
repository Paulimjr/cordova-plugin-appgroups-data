package cordova.plugin.appgroups.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * This class echoes a string called from JavaScript.
 */
public class SharedAppGroupsData extends CordovaPlugin {

    private static final String TAG = SharedAppGroupsData.class.getSimpleName();
    private CallbackContext callbackContext;
    private SharedPreferences sharedPreferences;
    private Context mContext;

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) {
        this.callbackContext = callbackContext;

        switch (Actions.getActionByName(action)) {
            case SAVE:
                save(args);
                break;
            case LOAD:
                load(args);
                break;
            case INVALID:
                break;
        }

        return true;
    }

    private void save(JSONArray args) {
        try {
            if (args != null) {
                JSONObject object = args.getJSONObject(0);

                String key = object.getString("key");
                String value = object.getString("value");
                String suite = object.getString("suite");

                this.mContext = this.cordova.getActivity().createPackageContext(suite, Context.MODE_PRIVATE);

                this.sharedPreferences = this.mContext.getSharedPreferences(suite, Context.MODE_PRIVATE);
                this.sharedPreferences.edit().putString(key, value).apply();

                this.callbackContext.success("Save success");

            } else {
                this.callbackContext.error("Error to save data");
            }

        } catch (Exception e) {
            Log.v(TAG, e.getMessage());
            this.callbackContext.error(e.getMessage());
        }
    }

    private void load(JSONArray args) {
        try {
            if (args != null) {
                JSONObject object = args.getJSONObject(0);

                String key = object.getString("key");
                String suite = object.getString("suite");

                this.mContext = this.cordova.getActivity().createPackageContext(suite, Context.MODE_PRIVATE);
                this.sharedPreferences = this.mContext.getSharedPreferences(suite, Context.MODE_PRIVATE);

                String value = this.sharedPreferences.getString(key, "");
                this.callbackContext.success(value);

            } else {
                this.callbackContext.error("");
            }

        } catch (Exception e) {
            Log.v(TAG, e.getMessage());
            this.callbackContext.error(e.getMessage());
        }
    }

    public enum Actions {
        SAVE("save", "Save information for the given entity in the shared storage."),
        LOAD("load", "Fetch the stored information for the given type from the shared storage."),
        INVALID("", "Invalid or not found action.");

        private String action;
        private String description;

        Actions(String action, String description) {
            this.action = action;
            this.description = description;
        }

        public String getDescription() {
            return description;
        }

        public static Actions getActionByName(String action) {
            for (Actions a : Actions.values()) {
                if (a.action.equalsIgnoreCase(action)) {
                    return a;
                }
            }
            return INVALID;
        }
    }
}