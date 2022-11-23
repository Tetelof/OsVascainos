package com.example.osvascainos.ui.rfid

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.osvascainos.Data
import com.example.osvascainos.retrofit.RetrofitService
import com.example.osvascainos.databinding.FragmentRfidBinding
import com.example.osvascainos.retrofit.RegisterRfidResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RfidFragment : Fragment() {

    private var _binding: FragmentRfidBinding? = null
    private lateinit var registerButton: Button
    private lateinit var resultRfid: TextView
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentRfidBinding.inflate(inflater, container, false)
        val root: View = binding.root

        resultRfid = binding.resultRfid
        resultRfid.text = "Fetching data..."

        registerButton = binding.buttonRegisterRfid
        resultRfid.setOnClickListener{
            registerRfid()
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun registerRfid(){
        val retrofit = RetrofitService.getRetrofitInstance()
        val get = retrofit.registerRfid(Data.user.id)
        val context = context
        get.enqueue(object: Callback<RegisterRfidResponse> {
            override fun onResponse(call: Call<RegisterRfidResponse>, response: Response<RegisterRfidResponse>) {
                if(response.isSuccessful){
                    resultRfid.text = response.body()!!.cadastro.toString()
                }else {
                    Log.d("TAG", "onResponse: " + response.message())
                }
            }
            override fun onFailure(call: Call<RegisterRfidResponse>, t: Throwable) {
                resultRfid.text = "err"
                Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }
}