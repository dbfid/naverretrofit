package com.example.naverretrofit

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.naverretrofit.api.NetworkService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private val TAG = javaClass.simpleName

    private lateinit var searchBtn: Button
    private lateinit var searchMoive: EditText
    private lateinit var progressBar: ProgressBar
    private lateinit var recyclerView: RecyclerView

    private lateinit var adapter: MovieAdapter
    private lateinit var call: Call<Movie>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
        initAdapter()
    }

    private fun initView() {
        searchBtn = findViewById(R.id.btn_search)
        searchMoive = findViewById(R.id.et_search)
        progressBar = findViewById(R.id.progressBar)
        recyclerView = findViewById(R.id.recyclerView)

        onClickListener()
    }


    private fun onClickListener(){
        searchBtn.setOnClickListener{searchMovie()}
    }

    private fun isLoading(loading: Boolean){
        progressBar.visibility =
            if(loading){
                View.VISIBLE
            }else{
                View.INVISIBLE
            }
    }

    private fun initAdapter(){ // 어뎁터를 연결
        adapter = MovieAdapter()
        recyclerView.adapter = adapter

        // 액션을 받는 액티비티가 없으면 예외가 발생할 경우에 대한 처리
        adapter.setItemClickListener { movie ->
            Intent(Intent.ACTION_VIEW, Uri.parse(movie.link)).takeIf{
                it.resolveActivity(packageManager) != null
            }?.run(this::startActivity)
        }
    }

    private fun searchMovie(){
        val movie = searchMoive.text.toString().trim()

        if(movie.isEmpty()){
            onMessage("영화제목을 입력해 주세요")
        }else{
            onMessage("잠시만 기달려 주세요")
            requestMovie(movie)
        }
    }

    fun onMessage(message: String){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun requestMovie(query: String){
        isLoading(true)
        adapter.clear()

        val select = NetworkService.naverApi
        call = select.getSearchMovie(query)

        call.enqueue(object : Callback<Movie>{
            override fun onFailure(call: Call<Movie>, t: Throwable){
                isLoading(false)
                onMessage("통신에 실패함")
            }

            override fun onResponse(call: Call<Movie>, response: Response<Movie>){
                isLoading(false)
                with(response){
                    val body = body() //밑에 body라는 것을 만들어주기 위해 써준것

                    if(isSuccessful && body != null){ // 뭔가 신호가 잡힌다면 성공인거고 아니면 실패인거고
                        body.items.let{
                            adapter.setItems(it)
                        }
                    }else{
                        onMessage("실패")
                    }
                }
            }
        })
    }
 /*   override fun onDestroy(){ // 여기는 왜 써주는지 몰라서 주석처리 해놨습니다.
        super.onDestroy()

        if(this::call.isInitialized) call.cancel()
    }*/

}