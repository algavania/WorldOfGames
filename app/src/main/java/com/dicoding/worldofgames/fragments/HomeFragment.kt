package com.dicoding.worldofgames.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.dicoding.worldofgames.R
import com.dicoding.worldofgames.adapters.GameAdapter
import com.dicoding.worldofgames.models.GameModel
import org.json.JSONObject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var recyclerView: RecyclerView? = null
    private val list = ArrayList<GameModel>()
    private var progressBar: ProgressBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.rv_home)
        progressBar = view.findViewById(R.id.progress_home)
        fetchGames()
    }

    private fun fetchGames() {
        progressBar?.visibility = View.VISIBLE
        list.clear()
        AndroidNetworking.get("https://api.jsonbin.io/b/613c446b9548541c29afe943")
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject?) {
                    val jsonArray = response?.getJSONArray("games")
                    if (jsonArray != null) {
                        for (i in 0 until jsonArray.length()) {
                            val jsonObject = jsonArray.getJSONObject(i)
                            val title = jsonObject.getString("title")
                            val thumbnail = jsonObject.getString("thumbnail")
                            val url = jsonObject.getString("game_url")
                            val genre = jsonObject.getString("genre")
                            val platform = jsonObject.getString("platform")
                            val publisher = jsonObject.getString("publisher")
                            val developer = jsonObject.getString("developer")
                            val releaseDate = jsonObject.getString("release_date")
                            val description = jsonObject.getString("short_description")
                            val gameModel = GameModel(title, genre, platform, releaseDate, publisher, developer, description, thumbnail, url)
                            list.add(gameModel)
                        }
                    }

                    recyclerView?.adapter = GameAdapter(list)
                    recyclerView?.layoutManager = LinearLayoutManager(activity)
                    recyclerView?.setItemViewCacheSize(20)
                    recyclerView?.isDrawingCacheEnabled = true
                    recyclerView?.drawingCacheQuality = View.DRAWING_CACHE_QUALITY_HIGH
                    recyclerView?.setHasFixedSize(true)
                    progressBar?.visibility = View.GONE
                }

                override fun onError(anError: ANError?) {
                    Toast.makeText(activity, anError?.message, Toast.LENGTH_SHORT).show()
                    progressBar?.visibility = View.GONE
                }

            })
    }
}