package com.example.simplenotif

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.app.NotificationCompat

class MainActivity : AppCompatActivity() {

    companion object{
        private const val NOTIFICATION_ID = 1
        private const val CHANNEL_ID = "Channel_01"
        private const val CHANNEL_NAME = "Yummi Channel"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    //aksi untuk onClick pada button
    fun sendNotification(view: View) { // Kelebihan dari cara ini yakni kita tidak perlu menginisialisasi Button tersebut di onCreate, sehingga pemanggilan kode bisa lebih cepat.

        /*
        Digunakan untuk memberikan action jika notifikasi disentuh.
         */
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://dicoding.com"))
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

        val mNotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val mBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
            /*
            Small Icon : Ikon ini yang akan muncul pada status bar (wajib ada).
            Large Icon : Ikon ini yang akan muncul di sebelah kiri dari text notifikasi.
            Content Intent : Intent ini sebagai action jika notifikasi ditekan.
            Content Title : Judul dari notifikasi (wajib ada).
            Content Text : Text yang akan muncul di bawah judul notifikasi (wajib ada).
            Subtext : Text ini yang akan muncul di bawah content text.
            Auto Cancel : Digunakan untuk menghapus notifikasi setelah ditekan.
             */
            .setContentIntent(pendingIntent) //Pending intent adalah sebuah token yang diberikan untuk aplikasi lainnya (termasuk juga aplikasi itu sendiri). Di sini kita kan menggunakan pending intent untuk mengirimkan notifikasi
            .setSmallIcon(R.drawable.ic_notifications_white_48px)
            .setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.ic_notifications_white_48px))
            .setContentTitle(resources.getString(R.string.content_title))
            .setContentText(resources.getString(R.string.content_text))
            .setSubText(resources.getString(R.string.subtext))
            .setAutoCancel(true)

        /*
        Untuk android Oreo ke atas perlu menambahkan notification channel agar notifikasi bisa berjalan
        */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            /* Create or update. */
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT)
            channel.description = CHANNEL_NAME
            mBuilder.setChannelId(CHANNEL_ID)
            mNotificationManager.createNotificationChannel(channel)
        }

        val notification = mBuilder.build()
        /*
        untuk mengirim notifikasi sesuai dengan id yang kita berikan. Fungsi id di sini nanti juga
        bisa untuk membatalkan notifikasi yang sudah muncul
         */
        mNotificationManager.notify(NOTIFICATION_ID, notification)
    }
}