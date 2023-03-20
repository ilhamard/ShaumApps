package com.dev.shaumapps

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.dev.shaumapps.databinding.ActivityDetailKutipanBinding
import java.io.File
import java.io.FileOutputStream

class DetailKutipanActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailKutipanBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailKutipanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.myToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Detail Kutipan"

        val data = intent.getStringExtra(EXTRA_KUTIPAN)
        if (data != null) {
            binding.imgDetailKutipan.setImageResource(data.toInt())
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_bar_detail_kutipan, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_share_kutipan -> {
            val bitmapDrawable: BitmapDrawable = binding.imgDetailKutipan.drawable as BitmapDrawable
            val bitmap = bitmapDrawable.bitmap
            shareImage(bitmap)
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    private fun shareImage(bitmap: Bitmap) {
        val uri = getImageToShare(bitmap)
        val intent = Intent(Intent.ACTION_SEND)

        intent.apply {
            putExtra(Intent.EXTRA_STREAM, uri)
            putExtra(Intent.EXTRA_SUBJECT, "Subject here")
            type = "image/jpeg"
        }
        startActivity(Intent.createChooser(intent, "Share via"))
    }

    private fun getImageToShare(bitmap: Bitmap): Uri? {
        val imageFolder = File(cacheDir, "images")
        var uri: Uri? = null
        try {
            imageFolder.mkdirs()
            val file = File(imageFolder, "shared_image.jpeg")
            val outputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStream)
            outputStream.flush()
            outputStream.close()
            uri = FileProvider.getUriForFile(this, "com.dev.shaumapps", file)
        } catch (e: Exception) {
            Toast.makeText(this, "${e.message}", Toast.LENGTH_SHORT).show()
        }
        return uri
    }

    companion object {
        const val EXTRA_KUTIPAN = "EXTRA_KUTIPAN"
    }
}