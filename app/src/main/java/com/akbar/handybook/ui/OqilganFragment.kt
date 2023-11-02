package com.akbar.handybook.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.akbar.handybook.R
import com.akbar.handybook.adapters.Kitob2Adapter
import com.akbar.handybook.adapters.Kitob3Adapter
import com.akbar.handybook.adapters.KitobAdapter
import com.akbar.handybook.databinding.FragmentOqilayotganBinding
import com.akbar.handybook.databinding.FragmentOqilganBinding
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

class OqilganFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    var allBooks = mutableListOf<Book>()
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
        val binding= FragmentOqilganBinding.inflate(inflater,container,false)
        val api=APIClient.getInstance().create(APIService::class.java)

        api.getAllBooks().enqueue(object : Callback<List<Book>> {
            override fun onResponse(call: Call<List<Book>>, response: Response<List<Book>>) {
                if (response.isSuccessful && response.body() != null){
                    allBooks = response.body()!!.toMutableList()


                    var adapter = Kitob3Adapter(allBooks)
                    binding.rv.adapter = adapter

                }
            }

            override fun onFailure(call: Call<List<Book>>, t: Throwable) {
                Log.d("TAG", "onFailure: $t")
            }
        })





        binding.rv.layoutManager = GridLayoutManager(requireContext(),2,GridLayoutManager.VERTICAL,false)

        binding.back.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.main, ShaxsiyKabinetFragment())
                .commit()
        }
        return binding.root
    }

    companion object {

        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            OqilganFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}