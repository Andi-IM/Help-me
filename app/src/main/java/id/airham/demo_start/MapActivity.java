package id.airham.demo_start;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import id.co.telkom.iot.AntaresHTTPAPI;
import id.co.telkom.iot.AntaresResponse;

public class MapActivity extends AppCompatActivity implements AntaresHTTPAPI.OnResponseListener {

    private Menu menu;
    private String TAG = "ANTARES-API";
    private AntaresHTTPAPI antaresAPIHTTP;
    private String dataDevice;
    private Handler mHandler = new Handler(Looper.getMainLooper());

    private final String PRIVATE_KEY = "7921411b92d84e8f:6346c94b054c9983";
    private final String APP_NAME = "helpme";
    private final String DEVICE_NAME = "helpapp";

    private final static int REQUEST_CODE_ASK_PERMISSIONS = 1;
    private static final String[] RUNTIME_PERMISSIONS = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.ACCESS_NETWORK_STATE
    };
    private MapFragmentView m_mapFragmentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        if (hasPermissions(this, RUNTIME_PERMISSIONS)) {
            setupMapFragmentView();
        } else {
            ActivityCompat
                    .requestPermissions(this, RUNTIME_PERMISSIONS, REQUEST_CODE_ASK_PERMISSIONS);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sos_menu, menu);
        this.menu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.item_sos){
            showDialog();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showDialog(){
        antaresAPIHTTP = new AntaresHTTPAPI();
        antaresAPIHTTP.addListener(this);

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MapActivity.this);

        //set title dialog
        alertDialog.setTitle("Set Status Bencana");
        alertDialog.setMessage("Select Status LED: ");
        String[] items = {"Kebakaran","Longsor","Banjir","Terjebak/Terkunci","Sakit Kronis","OFF"};
        int checkedItem = 5;
        alertDialog.setSingleChoiceItems(items, checkedItem, (dialog, which) -> {
            switch (which){
                case 0:
                    antaresAPIHTTP.storeDataofDevice(
                            1,
                            PRIVATE_KEY,
                            APP_NAME,
                            DEVICE_NAME,
                            "{\\\"led_state\\\":\\\"1\\\",\\\"change\\\":\\\"1\\\"}"
                    );
                    break;
                case 1:
                    antaresAPIHTTP.storeDataofDevice(
                            2,
                            PRIVATE_KEY,
                            APP_NAME,
                            DEVICE_NAME,
                            "{\\\"led_state\\\":\\\"2\\\",\\\"change\\\":\\\"1\\\"}"
                    );
                    break;
                case 2:
                    antaresAPIHTTP.storeDataofDevice(
                            3,
                            PRIVATE_KEY,
                            APP_NAME,
                            DEVICE_NAME,
                            "{\\\"led_state\\\":\\\"3\\\",\\\"change\\\":\\\"1\\\"}"
                    );
                    break;
                case 3:
                    antaresAPIHTTP.storeDataofDevice(
                            4,
                            PRIVATE_KEY,
                            APP_NAME,
                            DEVICE_NAME,
                            "{\\\"led_state\\\":\\\"4\\\",\\\"change\\\":\\\"1\\\"}"
                    );
                    break;
                case 4:
                    antaresAPIHTTP.storeDataofDevice(
                            5,
                            PRIVATE_KEY,
                            APP_NAME,
                            DEVICE_NAME,
                            "{\\\"led_state\\\":\\\"5\\\",\\\"change\\\":\\\"1\\\"}"
                    );
                    break;
                case 5:
                    antaresAPIHTTP.storeDataofDevice(
                            6,
                            PRIVATE_KEY,
                            APP_NAME,
                            DEVICE_NAME,
                            "{\\\"led_state\\\":\\\"6\\\",\\\"change\\\":\\\"1\\\"}"
                    );
                    break;
            }
        });
        alertDialog.setPositiveButton("Set", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialog.setNegativeButton("Refresh", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                antaresAPIHTTP.getLatestDataofDevice(
                        PRIVATE_KEY,
                        APP_NAME,
                        DEVICE_NAME);
            }
        });
        alertDialog.show();
    }

    /**
     * Only when the app's target SDK is 23 or higher, it requests each dangerous permissions it
     * needs when the app is running.
     */
    private static boolean hasPermissions(Context context, String... permissions) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission)
                        != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS: {
                for (int index = 0; index < permissions.length; index++) {
                    if (grantResults[index] != PackageManager.PERMISSION_GRANTED) {

                        /*
                         * If the user turned down the permission request in the past and chose the
                         * Don't ask again option in the permission request system dialog.
                         */
                        if (!ActivityCompat
                                .shouldShowRequestPermissionRationale(this, permissions[index])) {
                            Toast.makeText(this, "Required permission " + permissions[index]
                                            + " not granted. "
                                            + "Please go to settings and turn on for sample app",
                                    Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(this, "Required permission " + permissions[index]
                                    + " not granted", Toast.LENGTH_LONG).show();
                        }
                    }
                }

                setupMapFragmentView();
                break;
            }
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void setupMapFragmentView() {
        // All permission requests are being handled. Create map fragment view. Please note
        // the HERE Mobile SDK requires all permissions defined above to operate properly.
        m_mapFragmentView = new MapFragmentView(this);
    }

    @Override
    public void onResponse(AntaresResponse antaresResponse) {
        // --- Cetak hasil yang didapat dari ANTARES ke System Log --- //
        Log.d(TAG,antaresResponse.toString());
        Log.d(TAG,Integer.toString(antaresResponse.getRequestCode()));
        if(antaresResponse.getRequestCode()==0){
            try {
                JSONObject body = new JSONObject(antaresResponse.getBody());
                dataDevice = body.getJSONObject("m2m:cin").getString("con");
                mHandler.post(() -> toast(dataDevice));
                Log.d(TAG,dataDevice);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void toast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
