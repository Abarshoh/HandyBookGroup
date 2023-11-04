package com.akbar.handybook.ui

import android.annotation.SuppressLint
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.akbar.handybook.R
import com.akbar.handybook.databinding.FragmentPdfViewerBinding
import com.akbar.handybook.model.Book
import com.akbar.handybook.networking.APIClient
import com.akbar.handybook.networking.APIService
import com.github.barteksc.pdfviewer.PDFView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.InputStream

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PDFViewerFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PDFViewerFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var book: Book
    lateinit var binding : FragmentPdfViewerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding = FragmentPdfViewerBinding.inflate(inflater, container, false)
        val api = APIClient.getInstance().create(APIService::class.java)

        api.getBookById(param1.toString().toInt()).enqueue(object : Callback<Book>{
            override fun onResponse(call: Call<Book>, response: Response<Book>) {
                if (response.isSuccessful && response.body() != null){
                    var item = response.body()!!
                    if (item.audio == null){
                        book = Book("item.audio",item.author, item.count_page, item.description, item.file, item.id, item.image, item.lang, item.name, item.publisher, item.reyting, item.status, item.type_id, item.year)
                        setupWebViewWithUrl(binding.pdf, book.file)
                    }else{
                        book = Book(item.audio,item.author, item.count_page, item.description, item.file, item.id, item.image, item.lang, item.name, item.publisher, item.reyting, item.status, item.type_id, item.year)
                        setupWebViewWithUrl(binding.pdf, book.file)
                    }
                }
            }

            override fun onFailure(call: Call<Book>, t: Throwable) {
                Log.d("TAG", "onFailure: $t")
            }
        })



        return binding.root
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setupWebViewWithUrl(webView: WebView?, url: String) {
        webView?.let {
            // Enable JavaScript in the WebView
            it.settings.javaScriptEnabled = true
            it.settings.loadWithOverviewMode = true
            it.settings.useWideViewPort = true

            // Configure a WebViewClient to handle navigation events
            it.webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                    // Return false to allow the WebView to handle the URL
                    return false
                }
            }

            // Configure a WebChromeClient (optional)
            it.webChromeClient = object : WebChromeClient() {}

            // Generate HTML content to embed the PDF
            val htmlContent = getPDFHtml(url)

            // Load the HTML content into the WebView
            it.loadData(htmlContent, "text/html", "utf-8")
        }
    }

    // This function generates the HTML content to embed the PDF.
    private fun getPDFHtml(url: String): String {
        return """ 
            <!DOCTYPE html> 
            <html> 
            <head> 
                <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no"> 
                <style> 
                    body, html { 
                        margin: 0; 
                        height: 100%; 
                        overflow: hidden; 
                    } 
                    iframe { 
                        position: absolute; 
                        top: 0; 
                        left: 0; 
                        width: 100%; 
                        height: 100%; 
                        border: none; 
                    } 
                </style> 
            </head> 
            <body> 
                <iframe src="$url" allow="autoplay"></iframe> 
            </body> 
            </html> 
        """
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment PDFViewerFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PDFViewerFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}