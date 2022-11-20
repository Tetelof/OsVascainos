package com.example.osvascainos.ui.tensao

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.osvascainos.retrofit.RetrofitService
import com.example.osvascainos.databinding.FragmentTensaoBinding
import com.example.osvascainos.retrofit.VoltagemResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TensaoFragment : Fragment() {

    private var _binding: FragmentTensaoBinding? = null
    private lateinit var tensao: TextView

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentTensaoBinding.inflate(inflater, container, false)
        val root: View = binding.root

        tensao = binding.tensaoValue
        tensao.text = "Fetching data..."
        getTension()

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun getTension(){
        val retrofit = RetrofitService.getRetrofitInstance()
        val get = retrofit.getVoltagem()
        val context = context
        get.enqueue(object: Callback<VoltagemResponse> {
            override fun onResponse(call: Call<VoltagemResponse>, response: Response<VoltagemResponse>) {
                if(response.isSuccessful){
                    tensao.text = response.body()!!.valor.toString()
                }else {
                    Log.d("TAG", "onResponse: " + response.message())
                }
            }
            override fun onFailure(call: Call<VoltagemResponse>, t: Throwable) {
                Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }
}