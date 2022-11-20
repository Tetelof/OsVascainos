package com.example.osvascainos.ui.amperagem

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.osvascainos.retrofit.RetrofitService
import com.example.osvascainos.databinding.FragmentAmperagemBinding
import com.example.osvascainos.retrofit.AmperagemResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AmperagemFragment : Fragment() {
    private var _binding: FragmentAmperagemBinding? = null
    private lateinit var amperagem: TextView

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentAmperagemBinding.inflate(inflater, container, false)
        val root: View = binding.root

        amperagem = binding.amperagemValue
        amperagem.text = "Fetching data..."
        getAmps()
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    fun getAmps(){
        val retrofit = RetrofitService.getRetrofitInstance()
        val get = retrofit.getAmperagem()
        val context = context
        get.enqueue(object: Callback<AmperagemResponse> {
            override fun onResponse(call: Call<AmperagemResponse>, response: Response<AmperagemResponse>) {
                if(response.isSuccessful){
                    amperagem.text = response.body()!!.valor.toString()
                }else {
                    Log.d("TAG", "onResponse: " + response.message())
                }
            }
            override fun onFailure(call: Call<AmperagemResponse>, t: Throwable) {
                amperagem.text = "err"
                Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }
}