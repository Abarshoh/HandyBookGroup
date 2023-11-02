package com.akbar.handybook.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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

        api.getBookById(param1.toString().toInt()).enqueue(object:Callback<Book>{
            override fun onResponse(call: Call<Book>, response: Response<Book>) {
               if (response.isSuccessful){}
            }

            override fun onFailure(call: Call<Book>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })

        var  book = arguments?.getSerializable("book") as Book
        binding.bookName.text=book.name+" romani sizga qanchalik manzur keldi?"
        binding.back.setOnClickListener {
            requireActivity().onBackPressed()
        }
        var rating = binding.ratingBar.rating
        if (rating.toString().toDouble()>3.0){
            binding.emoji.setImageResource(R.drawable.book)
        }
        binding.send.setOnClickListener{
            val c = AddComment(book_id = 2, user_id = 1, reyting = rating.toString().toDouble(), text = binding.review.text.toString())
            val api = APIClient.getInstance().create(APIService::class.java)
            api.addComment(c).enqueue(object : Callback<AddComment> {
                override fun onResponse(call: Call<AddComment>, response: Response<AddComment>) {
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.main,MainFragment())
                        .commit()
                }

                override fun onFailure(call: Call<AddComment>, t: Throwable) {
                    Log.d("failure", "onFailure: $t")
                }
            })
            api.getBookComment(1).enqueue(object :Callback<List<Comment>>{
                override fun onResponse(call: Call<List<Comment>>, response: Response<List<Comment>>) {
                    Log.d("comment", response.toString())
                }

                override fun onFailure(call: Call<List<Comment>>, t: Throwable) {
                    Log.d("Asdf", "onFailure: $t")
                }

            })
        }
        binding.send.setOnClickListener{
            parentFragmentManager.beginTransaction()
                .replace(R.id.main,MainFragment())
                .commit()
        }
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