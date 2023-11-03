package com.akbar.handybook.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import coil.load
import com.akbar.handybook.R
import com.akbar.handybook.adapters.BooksAdapter
import com.akbar.handybook.adapters.CategoryAdapter
import com.akbar.handybook.databinding.FragmentHomeBinding
import com.akbar.handybook.model.Book
import com.akbar.handybook.model.Category
import com.akbar.handybook.networking.APIClient
import com.akbar.handybook.networking.APIService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val api = APIClient.getInstance().create(APIService::class.java)
    private lateinit var adapter: BooksAdapter
    private var allBooks = listOf<Book>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.allbooksrecyle.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)


        adapter = BooksAdapter(listOf(), requireContext(), object : BooksAdapter.OnClickBook {
            override fun onClickRoman(book: Book) {
                var bundle = Bundle()
                var details = ReviewFragment()
                bundle.putSerializable("book", book)
                details.arguments = bundle
                parentFragmentManager.beginTransaction()
                    .replace(R.id.main, details)
                    .commit()
            }

        })
        binding.allbooksrecyle.adapter = adapter

        api.getMainBook().enqueue(object : Callback<Book>{
            @SuppressLint("SetTextI18n")
            override fun onResponse(call: Call<Book>, response: Response<Book>) {
                val mainBook = response.body()!!
                binding.mainbookimage.load(mainBook.image)
                binding.mainText.text = """${mainBook.author}ning "${mainBook.name}" asari"""
                binding.readnow.setOnClickListener {
                    // TODO Set listener
                }
            }
            override fun onFailure(call: Call<Book>, t: Throwable) {
                Log.d("TAG", "$t")
            }

        })
        api.getAllBooks().enqueue(object : Callback<List<Book>>{
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(call: Call<List<Book>>, response: Response<List<Book>>) {
                val books = response.body()!!
                cacheAllBooks(books)
                AllBooks(books)
            }

            override fun onFailure(call: Call<List<Book>>, t: Throwable) {
                Log.d("TAG", "$t")
            }
        })
        binding.searchLayout.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.searchLayout.clearFocus()
                return false
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    binding.cardView.visibility = if (newText  == "") View.VISIBLE else View.GONE
                    api.search(newText).enqueue(object : Callback<List<Book>>{
                        override fun onResponse(
                            call: Call<List<Book>>,
                            response: Response<List<Book>>
                        ) {
                            if (!response.isSuccessful) return
                            AllBooks(response.body()!!)
                        }

                        override fun onFailure(call: Call<List<Book>>, t: Throwable) {
                            Log.d("TAG", "$t")
                        }
                    })
                    return true
                }
                AllBooks(allBooks)
                return false
            }

        })
        binding.homeCategoryRecycler.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        api.getCategories().enqueue(object : Callback<List<Category>>{
            override fun onResponse(
                call: Call<List<Category>>,
                response: Response<List<Category>>
            ) {
                if (!response.isSuccessful) return
                binding.homeCategoryRecycler.adapter = CategoryAdapter(response.body()!!, requireContext(), binding.homeCategoryRecycler, object : CategoryAdapter.CategoryPressed{
                    override fun onPressed(category: String?) {
                        if (category!= null){
                            binding.cardView.visibility = View.GONE
                            binding.allbooks.text = category
                        }else{
                            binding.cardView.visibility = View.VISIBLE
                            binding.allbooks.text = "Barcha kitoblar"
                        }
                        if (category == null){
                            AllBooks(allBooks)
                            return
                        }
                        setCategoryChanger(category)
                    }
                })
            }

            override fun onFailure(call: Call<List<Category>>, t: Throwable) {
                Log.d("TAG", "$t")
            }
        })




        return binding.root
    }



    private fun setCategoryChanger(category: String) {
        api.getBooksByCategory(category).enqueue(object : Callback<List<Book>>{
            override fun onResponse(call: Call<List<Book>>, response: Response<List<Book>>) {
                if (!response.isSuccessful) return
                val books = response.body()!!
                AllBooks(books)
            }

            override fun onFailure(call: Call<List<Book>>, t: Throwable) {
                Log.d("TAG", "$t")
            }
        })
    }


    private fun cacheAllBooks(books: List<Book>) {
        allBooks = books
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun AllBooks(books: List<Book>) {
        adapter.list = books
        adapter.notifyDataSetChanged()
//        binding.homeNothingFound.visibility = if (books.isEmpty()) View.VISIBLE else View.GONE
    }



}