package id.airham.demo_start

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import id.co.telkom.iot.AntaresHTTPAPI
import id.co.telkom.iot.AntaresResponse
import org.json.JSONException
import org.json.JSONObject

class HomeActivity : AppCompatActivity(), AntaresHTTPAPI.OnResponseListener {

    companion object {
        private const val PRIVATE_KEY = "7921411b92d84e8f:6346c94b054c9983"
        private const val APP_NAME = "helpme"
        private const val DEVICE_NAME = "helpapp"
        private const val TAG = "ANTARES-API"
    }

    private lateinit var radioGroup: RadioGroup

    private lateinit var btnSubmit: Button
    private lateinit var btnRefresh:Button
    private lateinit var txtData: TextView

    private lateinit var antaresAPIHTTP: AntaresHTTPAPI
    private lateinit var dataDevice: String
    private val mHandler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        supportActionBar?.title = resources.getString(R.string.sos)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        radioGroup = findViewById(R.id.radio_group)
        btnSubmit = findViewById(R.id.btn_submit)
        btnRefresh = findViewById(R.id.btn_refresh)
        txtData = findViewById(R.id.txtData)


        // --- Inisialisasi API Antares --- //
        //antaresAPIHTTP = AntaresHTTPAPI.getInstance();
        antaresAPIHTTP = AntaresHTTPAPI()
        antaresAPIHTTP.addListener(this)
        
        btnRefresh.setOnClickListener{
            antaresAPIHTTP.getLatestDataofDevice(
                    PRIVATE_KEY,
                    APP_NAME,
                    DEVICE_NAME
            )
        }

        btnSubmit.setOnClickListener {
            val id = radioGroup.getCheckedRadioButtonId()
            when (id) {
                R.id.fire -> antaresAPIHTTP.storeDataofDevice(
                    1,
                    PRIVATE_KEY,
                    APP_NAME,
                    DEVICE_NAME,
                    "{\\\"led_state\\\":\\\"1\\\",\\\"change\\\":\\\"1\\\"}"
                )
                R.id.avalanche -> antaresAPIHTTP.storeDataofDevice(
                    2,
                    PRIVATE_KEY,
                    APP_NAME,
                    DEVICE_NAME,
                    "{\\\"led_state\\\":\\\"2\\\",\\\"change\\\":\\\"1\\\"}"
                )
                R.id.flood -> antaresAPIHTTP.storeDataofDevice(
                    3,
                    PRIVATE_KEY,
                    APP_NAME,
                    DEVICE_NAME,
                    "{\\\"led_state\\\":\\\"3\\\",\\\"change\\\":\\\"1\\\"}"
                )
                R.id.locked -> antaresAPIHTTP.storeDataofDevice(
                    4,
                    PRIVATE_KEY,
                    APP_NAME,
                    DEVICE_NAME,
                    "{\\\"led_state\\\":\\\"4\\\",\\\"change\\\":\\\"1\\\"}"
                )
                R.id.sick -> antaresAPIHTTP.storeDataofDevice(
                    5,
                    PRIVATE_KEY,
                    APP_NAME,
                    DEVICE_NAME,
                    "{\\\"led_state\\\":\\\"5\\\",\\\"change\\\":\\\"1\\\"}"
                )
                R.id.off -> antaresAPIHTTP.storeDataofDevice(
                    6,
                    PRIVATE_KEY,
                    APP_NAME,
                    DEVICE_NAME,
                    "{\\\"led_state\\\":\\\"6\\\",\\\"change\\\":\\\"1\\\"}"
                )
            }
        }
    }

    override fun onResponse(antaresResponse: AntaresResponse) {
        // --- Cetak hasil yang didapat dari ANTARES ke System Log --- //
        // --- Cetak hasil yang didapat dari ANTARES ke System Log --- //
        Log.d(TAG, antaresResponse.toString())
        Log.d(TAG, antaresResponse.requestCode.toString())
        if (antaresResponse.requestCode == 0) {
            try {
                val body = JSONObject(antaresResponse.body)
                dataDevice = body.getJSONObject("m2m:cin").getString("con")
                mHandler.post { txtData.text = dataDevice }
                Log.d(TAG, dataDevice)
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
    }
}
