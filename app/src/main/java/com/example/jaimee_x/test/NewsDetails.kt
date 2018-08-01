package com.example.jaimee_x.test

import android.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import dmax.dialog.SpotsDialog
import kotlinx.android.synthetic.main.activity_news_details.*

class NewsDetails : AppCompatActivity() {

    lateinit var alertDialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_details)

        alertDialog = SpotsDialog(this)

        //webView
        web_view.settings.javaScriptEnabled=true
        web_view.webChromeClient = WebChromeClient()
        web_view.webViewClient = object :WebViewClient(){
            override fun onPageFinished(view: WebView?, url: String?) {
                alertDialog.dismiss()
            }
        }

        if (intent != null)
            if (!intent.getStringExtra("webURL").isEmpty())
                web_view.loadUrl(intent.getStringExtra("webURL"))
    }
}
