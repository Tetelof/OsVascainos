package com.example.osvascainos.ui.gas

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.osvascainos.retrofit.RetrofitService
import com.example.osvascainos.databinding.FragmentGasBinding
import com.example.osvascainos.retrofit.GasResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GasFragment : Fragment() {

    private var _binding: FragmentGasBinding? = null
    private lateinit var gas: TextView

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentGasBinding.inflate(inflater, container, false)
        val root: View = binding.root

        gas = binding.gasValue
        gas.text = "Fetching data..."
        getGas()
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun getGas(){
        val retrofit = RetrofitService.getRetrofitInstance()
        val get = retrofit.getSensorGas()
        val context = context
        get.enqueue(object: Callback<GasResponse> {
            override fun onResponse(call: Call<GasResponse>, response: Response<GasResponse>) {
                if(response.isSuccessful){
                    gas.text = response.body()!!.valor.toString()
                }else {
                    Log.d("TAG", "onResponse: " + response.message())
                }
            }
            override fun onFailure(call: Call<GasResponse>, t: Throwable) {
                gas.text = "err"
                Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }
}