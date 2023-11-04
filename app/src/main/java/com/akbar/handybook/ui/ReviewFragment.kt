package com.akbar.handybook.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.akbar.handybook.MyShared
import com.akbar.handybook.R
import com.akbar.handybook.databinding.FragmentOqilayotganBinding
import com.akbar.handybook.databinding.FragmentReviewBinding
import com.akbar.handybook.databinding.FragmentShaxsiyKabinetBinding
import com.akbar.handybook.model.AddComment
import com.akbar.handybook.model.Book
import com.akbar.handybook.model.Comment
import com.akbar.handybook.networking.APIClient
import com.akbar.handybook.networking.APIService
import org.jetbrains.annotations.ApiStatus
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A simple [Fragment] subclass.
 * Use the [ReviewFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
class ReviewFragment : Fragment() {
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
        val binding= FragmentReviewBinding.inflate(inflater, container, false)
        val api = APIClient.getInstance().create(APIService::class.java)
        val shared = MyShared.getInstance(requireContext())
        val user = shared.getUser()

        api.getBookById(param1.toString().toInt()).enqueue(object:Callback<Book>{
            @SuppressLint("SetTextI18n")
            override fun onResponse(call: Call<Book>, response: Response<Book>) {
               if (response.isSuccessful && response.body() != null){
                   var item = response.body()!!
                   if (item.audio == null){
                       book = Book("item.audio",item.author, item.count_page, item.description, item.file, item.id, item.image, item.lang, item.name, item.publisher, item.reyting, item.status, item.type_id, item.year)
                       binding.bookName.text = book.name + " romani sizga qanchalik manzur keldi?"
                       binding.back.setOnClickListener {
                           parentFragmentManager.beginTransaction()
                               .replace(R.id.main, CommentsFragment())
                               .commit()
                       }
                       var rating = binding.ratingBar.rating
                       if (rating.toString().toDouble()>3.0){
                           binding.emoji.setImageResource(R.drawable.book)
                       }
                       binding.send.setOnClickListener {
                           var c = AddComment(book.id, reyting = rating.toDouble(), text = binding.review.text.toString(), user_id = user!!.id)
                           api.addComment(c).enqueue(object : Callback<AddComment>{
                               override fun onResponse(call: Call<AddComment>, response: Response<AddComment>) {
                                   parentFragmentManager.beginTransaction()
                                       .replace(R.id.main, MainFragment())
                                       .commit()
                               }

                               override fun onFailure(call: Call<AddComment>, t: Throwable) {
                                   Log.d("TAG", "onFailure: $")
                               }
                           })
                       }
                   }
                   else{
                       book = Book(item.audio,item.author, item.count_page, item.description, item.file, item.id, item.image, item.lang, item.name, item.publisher, item.reyting, item.status, item.type_id, item.year)
                       book = Book(item.audio,item.author, item.count_page, item.description, item.file, item.id, item.image, item.lang, item.name, item.publisher, item.reyting, item.status, item.type_id, item.year)
                       binding.bookName.text = book.name + " romani sizga qanchalik manzur keldi?"
                       binding.back.setOnClickListener {
                           parentFragmentManager.beginTransaction()
                               .replace(R.id.main, CommentsFragment())
                               .commit()
                       }
                       var rating = binding.ratingBar.rating
                       if (rating.toString().toDouble()>3.0){
                           binding.emoji.setImageResource(R.drawable.book)
                       }
                       binding.send.setOnClickListener {
                           var c = AddComment(book.id, reyting = rating.toDouble(), text = binding.review.text.toString(), user_id = user!!.id)
                           api.addComment(c).enqueue(object : Callback<AddComment>{
                               override fun onResponse(call: Call<AddComment>, response: Response<AddComment>) {
                                   parentFragmentManager.beginTransaction()
                                       .replace(R.id.main, MainFragment())
                                       .commit()
                               }

                               override fun onFailure(call: Call<AddComment>, t: Throwable) {
                                   Log.d("TAG", "onFailure: $")
                               }
                           })
                       }
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

        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ReviewFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}