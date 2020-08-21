package com.test.mosun.camera

//import android.support.v4.app.ActivityCompat
//import android.support.v7.app.AppCompatActivity
import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.priyankvasa.android.cameraviewex.Modes
import com.radslow.tflitedemo.Classifier
import com.test.mosun.AppManager
import com.test.mosun.R
import kotlinx.android.synthetic.main.activity_camera.*

class CameraActivity : AppCompatActivity() {

    private lateinit var classifier: Classifier

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)
        val view: View = window.decorView
        view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
        window.statusBarColor = Color.parseColor("#add8e6")
        classifier = Classifier(assets)

        if (!canUseCamera()) {
            requestCameraPermissions()
        } else {
            setupCamera()
        }
    }

    private fun requestCameraPermissions() {
        ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA),
                REQUEST_CAMERA_CODE
        )
    }


    @SuppressLint("MissingPermission", "LongLogTag")
    private fun setupCamera() {
        camera.addPictureTakenListener {
            val recognitions = classifier.recognize(it.data)
            Log.i("모은 ", "cameraActivyt-recognition :" + recognitions[0].toString())
            var checkPercentString = recognitions[0].toString()
            var checkPercent = checkPercentString.split(": ")
            Log.i("모은 checkPercent", checkPercentString)
            Log.i("모은 checkPercent", checkPercent[1].substring(0, 2))

            if(recognitions[0].toString().startsWith("0"))
            {
                var checkPercentString = recognitions[0].toString()
                var checkPercent = checkPercentString.split(": ")
                Log.i("모은 checkPercent", checkPercentString)
                Log.i("모은 checkPercent", checkPercent[1].substring(0, 2))
                checkPercentString = checkPercent[1].substring(0, 2)
                if(Integer.parseInt(checkPercentString)<60) {
                    Toast.makeText(this, "마스크를 제대로 착용 후 다시 사진을 찍어주세요", Toast.LENGTH_LONG).show()
                }
                else
                {
                    //50%가 넘으면
                    if(AppManager.getInstance().maskCount==3)
                    {

                        Toast.makeText(this, "하루 세 번 마스크 스탬프를 얻을 수 있습니다", Toast.LENGTH_LONG).show()
                    }
                    else{
                        AppManager.getInstance().maskCount++;
                        Toast.makeText(this, "마스크 스탬프를 획득합니다", Toast.LENGTH_LONG).show()
                    }
                    finish();
                }



            }
            else{


                Toast.makeText(this, "마스크를 쓰고 사진을 찍어주세요", Toast.LENGTH_LONG).show()

            }
            val txt = recognitions.joinToString(separator = "\n")
            //Toast.makeText(this, txt, Toast.LENGTH_LONG).show()
            Log.i("모은", "마스크 스탬프 확률 : $txt")
        }

        scan_btn.setOnClickListener {
            camera.capture()
        }

        switch_btn.setOnClickListener {
            camera.facing = when (camera.facing) {
                Modes.Facing.FACING_BACK -> Modes.Facing.FACING_FRONT
                else -> Modes.Facing.FACING_BACK
            }
        }

    }

    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
    ) {



        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (REQUEST_CAMERA_CODE == requestCode) {


            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {//접근 승낙
                setupCamera()
            } else {




                if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA))
                {
                    //최초 접속이 아니고, 사용자가 다시 보지 않기에 체크를 하지 않고, 거절만 누른 경우

                    Toast.makeText(this, "마스크 인증을 위해 카메라 허가가 필요합니다", Toast.LENGTH_LONG).show()
                    requestCameraPermissions();
                    //Toast.makeText(this, "마스크 인증을 위해 카메라 허가가 필요합니다\n설정에서 카메라 권한 허가를 해주세요", Toast.LENGTH_LONG).show()

                }
                else
                {
                    //최초 접속 시 사용자가 다시 보지 않기에 체크를 했을 경우
                    Toast.makeText(this, "마스크 인증을 위해 카메라 허가가 필요합니다\n" +
                            "설정에서 카메라 권한을 설정해주세요", Toast.LENGTH_LONG).show()


                    finish();



                }


            }
        }
    }

    @SuppressLint("MissingPermission")
    override fun onResume() {
        super.onResume()
        if (canUseCamera()) {
            camera.start()
        }
    }

    override fun onPause() {
        if (canUseCamera()) {
            camera.stop()
        }
        super.onPause()
    }

    override fun onDestroy() {
        if (canUseCamera()) {
            camera.destroy()
        }

        super.onDestroy()
    }

    private fun canUseCamera() =
            ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED

    companion object {
        private const val REQUEST_CAMERA_CODE = 1
    }
}
