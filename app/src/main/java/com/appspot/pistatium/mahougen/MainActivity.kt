package com.appspot.pistatium.mahougen

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.content.FileProvider
import android.view.View
import com.appspot.pistatium.mahougen.utils.BitmapUtils
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onClickClear(v: View) {
        mahouCanvasView.clear()
    }

    fun onClickShare(v: View) {
        mahouCanvasView.isDrawingCacheEnabled = true;
        mahouCanvasView.destroyDrawingCache();

        val bmp = mahouCanvasView.drawingCache;
        val path = BitmapUtils.save(bmp, "mahougen.png", applicationContext)
        val contentUri = FileProvider.getUriForFile(applicationContext, "$packageName.fileprovider", path);

        val shareIntent = Intent();
        shareIntent.action = Intent.ACTION_SEND;
        shareIntent.type = "image/png";
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        shareIntent.setDataAndType(contentUri, contentResolver.getType(contentUri));
        shareIntent.putExtra(Intent.EXTRA_STREAM, contentUri);
        startActivity(Intent.createChooser(shareIntent, "Choose an app"));
    }
}
