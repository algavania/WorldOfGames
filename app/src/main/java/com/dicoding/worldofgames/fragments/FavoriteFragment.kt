package com.dicoding.worldofgames.fragments

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.dicoding.worldofgames.R
import com.dicoding.worldofgames.activities.DetailActivity
import com.dicoding.worldofgames.adapters.GameAdapter
import com.dicoding.worldofgames.config.RealmHelper
import com.dicoding.worldofgames.models.GameModel
import io.realm.Realm
import io.realm.RealmConfiguration
import kotlin.collections.ArrayList

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FavoriteFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FavoriteFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var recyclerView: RecyclerView
    private lateinit var list: List<GameModel>
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var startForResult: ActivityResultLauncher<Intent>
    private lateinit var tvNoData: TextView
    private lateinit var tvFavorite: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        startForResult =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                fetchGames()
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FavoriteFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FavoriteFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.rv_favorite)
        tvFavorite = view.findViewById(R.id.tv_text_favorite)
        tvNoData = view.findViewById(R.id.tv_no_data_favorite)
        swipeRefreshLayout = view.findViewById(R.id.swipe_favorite)
        swipeRefreshLayout.setColorSchemeColors(resources.getColor(R.color.purple_500))

        swipeRefreshLayout.setOnRefreshListener {
            // set timer so the ui will look better
            Handler(Looper.getMainLooper()).postDelayed({
                fetchGames()
            }, 1000)
        }

        fetchGames()
    }

    private fun checkListSize() {
        if (list.isEmpty()) {
            tvFavorite.visibility = View.GONE
            tvNoData.visibility = View.VISIBLE
        } else {
            tvFavorite.visibility = View.VISIBLE
            tvNoData.visibility = View.GONE
        }
    }

    private fun fetchGames() {
        val configuration = RealmConfiguration.Builder().allowWritesOnUiThread(true).build()
        val realm = Realm.getInstance(configuration)
        val realmHelper = RealmHelper(realm)
        list = ArrayList()
        list = realmHelper.getFavoriteGames()!!

        val adapter = GameAdapter(list, onItemClick = {
            val intent = Intent(activity, DetailActivity::class.java)
            intent.putExtra("game", it)
            startForResult.launch(intent)
        })
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.setItemViewCacheSize(20)
        recyclerView.setHasFixedSize(true)
        swipeRefreshLayout.isRefreshing = false
        checkListSize()
    }

    override fun onResume() {
        super.onResume()
        fetchGames()
    }
}