package com.appspot.pistatium.mahougen

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import android.support.v4.content.FileProvider
import android.util.Log
import android.view.View
import android.widget.SeekBar
import com.appspot.pistatium.mahougen.utils.BitmapUtils
import com.google.android.gms.ads.AdRequest
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), SeekBar.OnSeekBarChangeListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)

        vertexSlider.setOnSeekBarChangeListener(this)
    }

    fun onClickClear(v: View) {
        mahouCanvasView.clear()
    }

    fun onClickShare(v: View) {
        mahouCanvasView.isDrawingCacheEnabled = true
        mahouCanvasView.destroyDrawingCache()

        val bmp = mahouCanvasView.drawingCache
        val path = BitmapUtils.save(bmp, "mahougen.png", applicationContext)
        val contentUri = FileProvider.getUriForFile(applicationContext, "$packageName.fileprovider", path)

        val shareIntent = Intent()
        shareIntent.action = Intent.ACTION_SEND
        shareIntent.type = "image/png"
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        shareIntent.setDataAndType(contentUri, contentResolver.getType(contentUri))
        shareIntent.putExtra(Intent.EXTRA_STREAM, contentUri)
        shareIntent.putExtra(Intent.EXTRA_TEXT, " #mahougen")
        startActivity(Intent.createChooser(shareIntent, getString(R.string.choose_app)))
    }


    override fun onProgressChanged(p0: SeekBar?, progress: Int, fromUser: Boolean) {
        val mp = progress + 3
        mahouCanvasView.configure(mp)
        tvMp.text = "MP: $mp"
    }
    override fun onStartTrackingTouch(p0: SeekBar?) {
    }
    override fun onStopTrackingTouch(p0: SeekBar?) {
    }
}

