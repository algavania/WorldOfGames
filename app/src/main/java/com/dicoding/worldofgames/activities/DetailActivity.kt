package com.dicoding.worldofgames.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.dicoding.worldofgames.R
import com.dicoding.worldofgames.config.RealmHelper
import com.dicoding.worldofgames.models.GameModel
import com.squareup.picasso.Picasso
import io.realm.Realm
import io.realm.RealmConfiguration

class DetailActivity : AppCompatActivity() {

    private var isFavorite = false
    private lateinit var menuItem: MenuItem
    private lateinit var realmHelper: RealmHelper
    private lateinit var gameModel: GameModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val configuration = RealmConfiguration.Builder().allowWritesOnUiThread(true).build()
        val realm = Realm.getInstance(configuration)
        realmHelper = RealmHelper(realm)

        gameModel = intent.extras?.getParcelable("game")!!
        supportActionBar?.title = gameModel.title

        isFavorite = realmHelper.checkIfFavorite(gameModel)

        val image = findViewById<ImageView>(R.id.img_detail)
        Picasso.get().load(gameModel.thumbnail).into(image)
        val title = findViewById<TextView>(R.id.tv_title_detail)
        title.text = gameModel.title
        val genre = findViewById<TextView>(R.id.tv_genre_detail)
        genre.text = gameModel.genre
        val platform = findViewById<TextView>(R.id.tv_platform_detail)
        platform.text = gameModel.platform
        val release = findViewById<TextView>(R.id.tv_release_detail)
        release.text = gameModel.releaseDate
        val publisher = findViewById<TextView>(R.id.tv_publisher_detail)
        publisher.text = gameModel.publisher
        val developer = findViewById<TextView>(R.id.tv_developer_detail)
        developer.text = gameModel.developer
        val description = findViewById<TextView>(R.id.tv_description_detail)
        description.text = gameModel.description

        val button = findViewById<Button>(R.id.btn_game)
        button.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(gameModel.url))
            ContextCompat.startActivity(this, browserIntent, null)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        Log.d("TAG", "on create options menu")
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        menuItem = menu.getItem(0)
        setFavoriteToolbarUi()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here.
        val id = item.itemId

        if (id == R.id.favorite_toolbar_menu) {
            setFavorite()
            return true
        }

        return super.onOptionsItemSelected(item)

    }

    private fun setFavoriteToolbarUi() {
        if (isFavorite) {
            menuItem.setIcon(R.drawable.ic_baseline_favorite_24)
        } else {
            menuItem.setIcon(R.drawable.ic_outline_favorite_border_24)
        }
    }

    private fun setFavorite() {
        if (!isFavorite) {
            realmHelper.save(gameModel)
            Toast.makeText(this, "Added to favorites", Toast.LENGTH_SHORT).show()
        } else {
            realmHelper.delete(gameModel)
            Toast.makeText(this, "Deleted from favorites", Toast.LENGTH_SHORT).show()
        }
        isFavorite = !isFavorite
        setFavoriteToolbarUi()
    }
}