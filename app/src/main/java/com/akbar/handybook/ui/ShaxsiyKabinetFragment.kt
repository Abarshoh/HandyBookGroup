package com.akbar.handybook.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.akbar.handybook.databinding.FragmentShaxsiyKabinetBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ShaxsiyKabinetFragment : Fragment() {
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

        val binding= FragmentShaxsiyKabinetBinding.inflate(inflater,container,false)\


//      var  user = arguments?.getSerializable("user") as User
//        var userList = ShPHelper.getInstance(requireContext()).getUser()
//        img = binding.imageView3
//        for (i in userList) {
//            if (i == user) {
//                if (i.url != null) {
//                    img.setImageURI(Uri.parse(i.url))
//                }
//                else{
//                    img.setImageResource(R.drawable.user)
//                }
//            }
//        }
//        binding.username.text = user.name + " " + user.surname
//        binding.gmail.text = user.email
//        binding.back.setOnClickListener {
//            requireActivity().onBackPressed()
//        }
//


    return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ShaxsiyKabinetFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ShaxsiyKabinetFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}