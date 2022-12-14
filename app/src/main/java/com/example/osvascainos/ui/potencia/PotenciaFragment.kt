package com.example.osvascainos.ui.potencia

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.osvascainos.retrofit.RetrofitService
import com.example.osvascainos.databinding.FragmentPotenciaBinding
import com.example.osvascainos.retrofit.AmperagemResponse
import com.example.osvascainos.retrofit.VoltagemResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.roundToInt

class PotenciaFragment : Fragment() {

    private var _binding: FragmentPotenciaBinding? = null
    private var tensao: Int = 0
    private var amperagem: Float = 0f
    private lateinit var potencia: TextView


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentPotenciaBinding.inflate(inflater, container, false)
        val root: View = binding.root

        potencia = binding.potenciaValue
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
                    tensao = response.body()!!.valor
                    if(tensao == 0){
                        tensao = 127
                    }
                    Handler().postDelayed(Runnable {
                        getAmps()
                    }, 1500)
                }else {
                    Log.d("TAG", "onResponse: " + response.message())
                    tensao = 10
                }
            }
            override fun onFailure(call: Call<VoltagemResponse>, t: Throwable) {
                Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
                potencia.text = "err"
            }
        })
    }
    fun getAmps(){
        val retrofit = RetrofitService.getRetrofitInstance()
        val get = retrofit.getAmperagem()
        val context = context
        get.enqueue(object: Callback<AmperagemResponse> {
            override fun onResponse(call: Call<AmperagemResponse>, response: Response<AmperagemResponse>) {
                if(response.isSuccessful){
                    amperagem = response.body()!!.valor
                    if (amperagem == 0f) {
                        amperagem = 0.56f
                    }

                    potencia.text = (tensao * amperagem).roundToInt().toString() + "Watts"
                }else {
                    Log.d("TAG", "onResponse: " + response.message())
                    amperagem = 10f
                }
            }
            override fun onFailure(call: Call<AmperagemResponse>, t: Throwable) {
                Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
                potencia.text = "err"
            }
        })
    }
}