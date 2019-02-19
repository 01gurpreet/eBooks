package com.kinfoitsolutions.e_booksnew.ui.activities

import android.os.Bundle
import com.kinfoitsolutions.e_booksnew.AppConstants
import com.kinfoitsolutions.e_booksnew.R
import com.kinfoitsolutions.e_booksnew.ui.BaseActivity
import com.orhanobut.hawk.Hawk
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_audio_book.*
import kotlinx.android.synthetic.main.activity_book_detail.*
import android.media.MediaPlayer
import android.view.View
import android.widget.SeekBar
import androidx.core.content.ContextCompat
import android.media.PlaybackParams
import android.os.Build
import androidx.annotation.RequiresApi


class AudioBookActivity : BaseActivity(), Runnable {

    private val seekForwardTime = 5000 // 5000 milliseconds
    private val seekBackwardTime = 5000 // 5000 milliseconds
    private lateinit var bookName: String
    var mediaPlayer = MediaPlayer()
    var wasPlaying = false
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_audio_book)




        bookName = Hawk.get(AppConstants.BOOK_NAME, "")
        val bookName = Hawk.get(AppConstants.BOOK_NAME, "")
        val bookImage = Hawk.get(AppConstants.BOOK_IMAGE, "")
        playimage.setOnClickListener {

            if (wasPlaying) {
                mediaPlayer.pause()
                playimage.setImageDrawable(ContextCompat.getDrawable(this@AudioBookActivity, R.drawable.ic_play_button))

            } else {
                mediaPlayer.start()
                playSong()
            }

        }
        /* pauseimage.setOnClickListener {


         }*/
        bookTitle.setText(bookName)

        speed_play.setOnClickListener {
            /* val playbackParams = PlaybackParams()
             playbackParams.speed = 2f
             playbackParams.pitch = 1f
             playbackParams.audioFallbackMode = PlaybackParams.AUDIO_FALLBACK_MODE_DEFAULT
             mediaPlayer.playbackParams = playbackParams*/
        }





        try {
            Picasso.get().load(bookImage).fit().placeholder(com.kinfoitsolutions.e_booksnew.R.drawable.loading)
                .error(R.drawable.no_image)
                .into(imageMain)
        } catch (e: Exception) {
            e.printStackTrace()
            imageBlur.setImageResource(R.drawable.no_image)
        }

        try {
            Picasso.get().load(bookImage).fit().placeholder(R.drawable.loading).error(R.drawable.no_image)
                .into(subImage)
        } catch (e: Exception) {
            e.printStackTrace()
            smallImage.setImageResource(R.drawable.no_image)
        }



        seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onStartTrackingTouch(seekBar: SeekBar) {

                seekBarHint.setVisibility(View.VISIBLE)
            }

            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromTouch: Boolean) {
                seekBarHint.setVisibility(View.VISIBLE)
                val x = Math.ceil((progress / 1000f).toDouble()).toInt()

                if (x < 10)
                    seekBarHint.setText("0:0$x")
                else
                    seekBarHint.setText("0:$x")

                val percent = progress / seekBar.max.toDouble()
                val offset = seekBar.thumbOffset
                val seekWidth = seekBar.width
                val `val` = Math.round(percent * (seekWidth - 2 * offset)).toInt()
                val labelWidth = seekBarHint.getWidth()
                seekBarHint.x = (offset.toFloat() + seekBar.x + `val`.toFloat()
                        - Math.round(percent * offset).toFloat()
                        - Math.round(percent * labelWidth / 2).toFloat())

                if (progress > 0 && mediaPlayer != null && !mediaPlayer.isPlaying) {
                    //pauseimage.setImageDrawable(ContextCompat.getDrawable(this@AudioBookActivity, R.drawable.ic_play_button))


                    this@AudioBookActivity.seekbar.setProgress(0)
                }

            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {


                if (mediaPlayer != null && mediaPlayer.isPlaying) {
                    mediaPlayer.seekTo(seekBar.progress)
                }
            }
        })


        backward.setOnClickListener(View.OnClickListener {
            val currentPosition = mediaPlayer.getCurrentPosition()
            if (currentPosition + seekForwardTime <= mediaPlayer.getDuration()) {
                mediaPlayer.seekTo(currentPosition + seekForwardTime)
            } else {
                mediaPlayer.seekTo(mediaPlayer.getDuration())
            }
        })



        forward.setOnClickListener(View.OnClickListener {
            val currentPosition = mediaPlayer.getCurrentPosition()
            if (currentPosition + seekForwardTime <= mediaPlayer.getDuration()) {
                mediaPlayer.seekTo(currentPosition + seekForwardTime)
            } else {
                mediaPlayer.seekTo(mediaPlayer.getDuration())
            }
        })


    }

    fun playSong() {

        try {

            if (mediaPlayer != null && mediaPlayer.isPlaying) {
                seekbar.setProgress(0)
                wasPlaying = true
                // pauseimage.setImageDrawable(ContextCompat.getDrawable(this@AudioBookActivity, R.drawable.ic_play_button))
                playimage.setImageDrawable(ContextCompat.getDrawable(this@AudioBookActivity, R.drawable.ic_play_button))

            } else {

            }


            if (!wasPlaying) {

                if (mediaPlayer == null) {
                    mediaPlayer = MediaPlayer()
                }

                playimage.setImageDrawable(ContextCompat.getDrawable(this@AudioBookActivity, R.mipmap.ic_pause))


                val url = Hawk.get(AppConstants.Audio_Book, "") // your URL here
                mediaPlayer.setDataSource(url)

                mediaPlayer.prepare()
                mediaPlayer.setVolume(0.5f, 0.5f)
                mediaPlayer.isLooping = false
                seekbar.setMax(mediaPlayer.duration)

                mediaPlayer.start()
                Thread(this).start()

            }

            wasPlaying = false
        } catch (e: Exception) {
            e.printStackTrace()

        }

    }


    override fun run() {

        var currentPosition = mediaPlayer.currentPosition
        val total = mediaPlayer.duration


        while (mediaPlayer != null && mediaPlayer.isPlaying && currentPosition < total) {
            try {
                Thread.sleep(1000)
                currentPosition = mediaPlayer.currentPosition
            } catch (e: InterruptedException) {
                return
            } catch (e: Exception) {
                return
            }

            seekbar.setProgress(currentPosition)

        }
    }

    override fun onPause() {
        super.onPause()

        try {
            if (mediaPlayer.isPlaying) {
                mediaPlayer.pause()

            }

        } catch (we: Exception) {
            we.printStackTrace()
        }

    }

    override fun onResume() {
        super.onResume()
        try {
            mediaPlayer.start()

        } catch (we: Exception) {
            we.printStackTrace()
        }

    }
}
