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
import com.example.osvascainos.databinding.FragmentGetRfidBinding
import com.example.osvascainos.retrofit.RFIDResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GetRfidFragment : Fragment() {

    private var _binding: FragmentGetRfidBinding? = null
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

        _binding = FragmentGetRfidBinding.inflate(inflater, container, false)
        val root: View = binding.root

        resultRfid = binding.resultGetRfid
        resultRfid.text = "Fetching data..."

        registerButton = binding.buttonReadRfid
        registerButton.setOnClickListener{
            testRfid()
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun testRfid(){
        val retrofit = RetrofitService.getRetrofitInstance()
        val get = retrofit.getRfid(id=Data.user.id)
        val context = context
        get.enqueue(object: Callback<RFIDResponse> {
            override fun onResponse(call: Call<RFIDResponse>, response: Response<RFIDResponse>) {
                if(response.isSuccessful){
                    resultRfid.text = response.body()!!.permitido.toString()
                }else {
                    Log.d("TAG", "onResponse: " + response.message())
                }
            }
            override fun onFailure(call: Call<RFIDResponse>, t: Throwable) {
                resultRfid.text = "err"
                Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }
}