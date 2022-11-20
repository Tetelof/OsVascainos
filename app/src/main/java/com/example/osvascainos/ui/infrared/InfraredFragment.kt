package com.example.osvascainos.ui.infrared

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.osvascainos.retrofit.RetrofitService
import com.example.osvascainos.databinding.FragmentInfraredBinding
import com.example.osvascainos.retrofit.InfraResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InfraredFragment : Fragment() {

    private var _binding: FragmentInfraredBinding? = null
    private lateinit var infrared: TextView

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentInfraredBinding.inflate(inflater, container, false)
        val root: View = binding.root

        infrared = binding.infraredValue
        infrared.text = "Fetching data..."
        getInfra()
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun getInfra(){
        val retrofit = RetrofitService.getRetrofitInstance()
        val get = retrofit.getSensorInfravermelho()
        val context = context
        get.enqueue(object: Callback<InfraResponse> {
            override fun onResponse(call: Call<InfraResponse>, response: Response<InfraResponse>) {
                if(response.isSuccessful){
                    infrared.text = if (response.body()!!.encontrado_item) "Item encontrado" else "Nada encontrado"
                }else {
                    Log.d("TAG", "onResponse: " + response.message())
                }
            }
            override fun onFailure(call: Call<InfraResponse>, t: Throwable) {
                infrared.text = "err"
                Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }
}