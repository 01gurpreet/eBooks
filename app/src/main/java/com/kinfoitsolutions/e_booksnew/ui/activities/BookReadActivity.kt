package com.kinfoitsolutions.e_booksnew.ui.activities

import android.app.AlertDialog
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.*
import androidx.annotation.RequiresApi
import com.kinfoitsolutions.e_booksnew.AppConstants
import com.kinfoitsolutions.e_booksnew.R
import com.kinfoitsolutions.e_booksnew.ui.BaseActivity
import com.kinfoitsolutions.e_booksnew.util.Utils
import com.orhanobut.hawk.Hawk
import kotlinx.android.synthetic.main.activity_book_read.*
import kotlinx.android.synthetic.main.activity_web_view.view.*
import kotlinx.android.synthetic.main.toolbar_help.*

class BookReadActivity : BaseActivity(), View.OnClickListener {


    private lateinit var progressDialog: ProgressDialog
    private lateinit var bookName: String
    internal var isSelected = false
    internal lateinit var slideDialog: Dialog
    internal lateinit var seekBar: SeekBar
    internal var textSize = 18
    internal var saveProgress: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_read)


        backArrowBookRead.setOnClickListener {
            finish()
        }

        bookName = Hawk.get(AppConstants.BOOK_NAME,"")

        bookTitle.setText(bookName)


        val url = Hawk.get(AppConstants.PDF_FILE,"")

        if (url.equals(null)){
            noPdfAlert()
        }

        val bookTextData = Hawk.get(AppConstants.BOOKS_TEXT,"")

        bookText.setText(bookTextData)

        bookText.setTextIsSelectable(true)

        audioListen.setOnClickListener {

            startActivity(Intent(this@BookReadActivity,AudioBookActivity::class.java))
        }

        linear1.setOnClickListener(this)
        linear2.setOnClickListener(this)
        linear3.setOnClickListener(this)
        linear4.setOnClickListener(this)

    }


    override fun onClick(v: View) {

        when (v.id) {

            R.id.linear1 ->


                if (!isSelected) {
                    linear.setBackgroundColor(Color.parseColor("#fffbe7"))

                    image1.setImageResource(R.drawable.ic_eye_orange)
                    image2.setImageResource(R.drawable.ic_font_1)
                    image3.setImageResource(R.drawable.ic_brightness_1)
                    image4.setImageResource(R.drawable.ic_zoom_1)

                    isSelected = true
                } else if (isSelected) {
                    linear.setBackgroundColor(Color.parseColor("#ffffff"))

                    image1.setImageResource(R.drawable.ic_eye_1)
                    image2.setImageResource(R.drawable.ic_font_1)
                    image3.setImageResource(R.drawable.ic_brightness_1)
                    image4.setImageResource(R.drawable.ic_zoom_1)

                    isSelected = false
                }


            R.id.linear2 -> {
                linear.setBackgroundColor(Color.parseColor("#ffffff"))

                image1.setImageResource(R.drawable.ic_eye_1)
                image2.setImageResource(R.drawable.ic_font_orange)
                image3.setImageResource(R.drawable.ic_brightness_1)
                image4.setImageResource(R.drawable.ic_zoom_1)


                slideDialog = Dialog(this@BookReadActivity, R.style.CustomDialogAnimation)
                slideDialog.setContentView(R.layout.zooming_popup1)
                slideDialog.getWindow()!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                val layoutParams3 = WindowManager.LayoutParams()
                val layoutParams = WindowManager.LayoutParams()
                slideDialog.getWindow()!!.getAttributes().windowAnimations = R.style.CustomDialogAnimation
                layoutParams.copyFrom(slideDialog.getWindow()!!.getAttributes())


                seekBar = slideDialog.findViewById(R.id.seekBar1) as SeekBar


                seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {


                    override fun onStopTrackingTouch(seekBar: SeekBar) {


                    }

                    override fun onStartTrackingTouch(seekBar: SeekBar) {

                    }

                    override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                        textSize = textSize + (progress - saveProgress)
                        saveProgress = progress
                        bookText.setTextSize(textSize.toFloat())


                    }
                })


                // int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.85);
                val height = (resources.displayMetrics.heightPixels * 0.18)


                layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
                layoutParams.height = height.toInt()
                // layoutParams.width = width;
                layoutParams.gravity = Gravity.BOTTOM


                slideDialog.getWindow()!!.setAttributes(layoutParams)
                slideDialog.setCancelable(true)
                slideDialog.setCanceledOnTouchOutside(true)
                slideDialog.show()
            }

            R.id.linear3 -> {

                image1.setImageResource(R.drawable.ic_eye_1)
                image2.setImageResource(R.drawable.ic_font_1)
                image3.setImageResource(R.drawable.ic_brightness_orange)
                image4.setImageResource(R.drawable.ic_zoom_1)

                slideDialog = Dialog(this@BookReadActivity, R.style.CustomDialogAnimation)
                slideDialog.setContentView(R.layout.zooming_popup)
                slideDialog.getWindow()!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

                title = "dev2qa.com - Seekbar Change Screen Brightness Example."

                // Get display screen brightness value text view object.
                //final TextView screenBrightnessValueTextView = (TextView)findViewById(R.id.change_screen_brightness_value_text_view);

                // Get the seekbar instance.
                seekBar = slideDialog.findViewById(R.id.seekBar1) as SeekBar
                seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

                    @RequiresApi(api = Build.VERSION_CODES.M)
                    override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {

                        val context = applicationContext

                        if (Build.VERSION.SDK_INT >= 23) {
                            // if (!Settings.canDrawOverlays(this)) {
                            //
                        } else {
                            // another similar method that supports device have API < 23
                        }

                        val canWriteSettings = Settings.System.canWrite(context)

                        if (canWriteSettings) {

                            // Because max screen brightness value is 255
                            // But max seekbar value is 100, so need to convert.
                            val screenBrightnessValue = i * 255 / 100

                            // Set seekbar adjust screen brightness value in the text view.
                            //screenBrightnessValueTextView.setText(SCREEN_BRIGHTNESS_VALUE_PREFIX + screenBrightnessValue);

                            // Change the screen brightness change mode to manual.
                            Settings.System.putInt(
                                context.contentResolver,
                                Settings.System.SCREEN_BRIGHTNESS_MODE,
                                Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL
                            )
                            // Apply the screen brightness value to the system, this will change the value in Settings ---> Display ---> Brightness level.
                            // It will also change the screen brightness for the device.
                            Settings.System.putInt(
                                context.contentResolver,
                                Settings.System.SCREEN_BRIGHTNESS,
                                screenBrightnessValue
                            )
                        } else {
                            // Show Can modify system settings panel to let user add WRITE_SETTINGS permission for this app.
                            val intent = Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS)
                            context.startActivity(intent)
                        }
                    }

                    override fun onStartTrackingTouch(seekBar: SeekBar) {

                    }

                    override fun onStopTrackingTouch(seekBar: SeekBar) {

                    }
                })

                //Getting Current screen brightness.
                val currBrightness = Settings.System.getInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS, 0)
                // Set current screen brightness value in the text view.
                // screenBrightnessValueTextView.setText( SCREEN_BRIGHTNESS_VALUE_PREFIX + currBrightness);
                // Set current screen brightness value to seekbar progress.
                seekBar.setProgress(currBrightness)


                val layoutParams1 = WindowManager.LayoutParams()
                slideDialog.getWindow()!!.getAttributes().windowAnimations = R.style.CustomDialogAnimation
                layoutParams1.copyFrom(slideDialog.getWindow()!!.getAttributes())

                val width1 = (resources.displayMetrics.widthPixels * 0.60)
                val height1 = (resources.displayMetrics.heightPixels * 0.18)

                layoutParams1.width = WindowManager.LayoutParams.MATCH_PARENT
                layoutParams1.height = height1.toInt()
                layoutParams1.gravity = Gravity.BOTTOM


                slideDialog.getWindow()!!.setAttributes(layoutParams1)
                slideDialog.setCancelable(true)
                slideDialog.setCanceledOnTouchOutside(true)
                slideDialog.show()
            }

            R.id.linear4 -> {

                image1.setImageResource(R.drawable.ic_eye_1)
                image2.setImageResource(R.drawable.ic_font_1)
                image3.setImageResource(R.drawable.ic_brightness_1)
                image4.setImageResource(R.drawable.ic_zoom_orange)


                linear.setBackgroundColor(Color.parseColor("#ffffff"))

                slideDialog = Dialog(this@BookReadActivity, R.style.CustomDialogAnimation)
                slideDialog.setContentView(R.layout.zooming_popup2)
                slideDialog.getWindow()!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                val layoutParams2 = WindowManager.LayoutParams()
                slideDialog.getWindow()!!.getAttributes().windowAnimations = R.style.CustomDialogAnimation
                layoutParams2.copyFrom(slideDialog.getWindow()!!.getAttributes())


                //  txtSeekBar.setTextScaleX(textSize);
                seekBar = slideDialog.findViewById(R.id.seekBar1) as SeekBar
                seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                    internal var p = 0

                    override fun onStopTrackingTouch(seekBar: SeekBar) {
                        // TODO Auto-generated method stub
                        if (p < 15) {
                            p = 15
                            seekBar.progress = p
                        }
                    }

                    override fun onStartTrackingTouch(seekBar: SeekBar) {
                        // TODO Auto-generated method stub
                    }

                    override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                        // TODO Auto-generated method stub
                        p = progress
                        bookText.setTextSize(p.toFloat())
                    }
                })


                val width2 = (resources.displayMetrics.widthPixels * 0.60)
                val height2 = (resources.displayMetrics.heightPixels * 0.18)

                layoutParams2.width = WindowManager.LayoutParams.MATCH_PARENT
                layoutParams2.height = height2.toInt()
                layoutParams2.gravity = Gravity.BOTTOM

                slideDialog.getWindow()!!.setAttributes(layoutParams2)
                slideDialog.setCancelable(true)
                slideDialog.setCanceledOnTouchOutside(true)
                slideDialog.show()
            }
        }
    }


    private fun noPdfAlert() {
        val builder1 = AlertDialog.Builder(this)
        builder1.setMessage("No Pdf to Read")
        builder1.setCancelable(false)

        builder1.setPositiveButton("OK") { dialog, id ->
            dialog.cancel()
        }


        val alert11 = builder1.create()
        alert11.show()

    }





}
