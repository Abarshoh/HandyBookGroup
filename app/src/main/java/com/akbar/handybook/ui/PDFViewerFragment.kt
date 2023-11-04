package com.akbar.handybook.ui

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.akbar.handybook.R
import com.akbar.handybook.databinding.FragmentPdfViewerBinding
import com.akbar.handybook.model.Book
import com.akbar.handybook.networking.APIClient
import com.akbar.handybook.networking.APIService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
        val binding = FragmentPdfViewerBinding.inflate(inflater, container, false)
        val api = APIClient.getInstance().create(APIService::class.java)

        api.getBookById(param1.toString().toInt()).enqueue(object : Callback<Book>{
            override fun onResponse(call: Call<Book>, response: Response<Book>) {
                if (response.isSuccessful && response.body() != null){
                    var item = response.body()!!
                    if (item.audio == null){
                        book = Book("item.audio",item.author, item.count_page, item.description, item.file, item.id, item.image, item.lang, item.name, item.publisher, item.reyting, item.status, item.type_id, item.year)
                        binding.pdf.fromUri(Uri.parse(book.file)).load()
                    }else{
                        book = Book(item.audio,item.author, item.count_page, item.description, item.file, item.id, item.image, item.lang, item.name, item.publisher, item.reyting, item.status, item.type_id, item.year)
                        binding.pdf.fromUri(Uri.parse(book.file)).load()
                    }
                }
            }

            override fun onFailure(call: Call<Book>, t: Throwable) {
                Log.d("TAG", "onFailure: $t")
            }
        })



        return binding.root
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