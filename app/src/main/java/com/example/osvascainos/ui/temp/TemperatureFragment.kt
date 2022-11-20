package com.example.osvascainos.ui.temp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.osvascainos.retrofit.RetrofitService
import com.example.osvascainos.databinding.FragmentTemperatureBinding
import com.example.osvascainos.retrofit.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TemperatureFragment : Fragment() {

    private var _binding: FragmentTemperatureBinding? = null
    private lateinit var temp: TextView
    private lateinit var umidity: TextView


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentTemperatureBinding.inflate(inflater, container, false)
        val root: View = binding.root

        temp = binding.temperaturaValue
        umidity = binding.umidadeValue
        temp.text = "Fetching data..."
        umidity.text = "Fetching data..."
        getTemp()


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun getTemp(){
        val retrofit = RetrofitService.getRetrofitInstance()
        val get = retrofit.getTempUmidity()
        val context = context
        get.enqueue(object: Callback<DHT11Response> {
            override fun onResponse(call: Call<DHT11Response>, response: Response<DHT11Response>) {
                if(response.isSuccessful){
                    temp.text = response.body()!!.temperatura.toString()
                    umidity.text = response.body()!!.umidade.toString()
                }else {
                    Log.d("TAG", "onResponse: " + response.message())
                }
            }
            override fun onFailure(call: Call<DHT11Response>, t: Throwable) {
                temp.text = "err"
                umidity.text = "err"
                Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }


}