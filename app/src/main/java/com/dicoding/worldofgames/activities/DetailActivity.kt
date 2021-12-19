package com.dicoding.worldofgames.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.dicoding.worldofgames.R
import com.dicoding.worldofgames.models.GameModel
import com.squareup.picasso.Picasso

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val gameModel: GameModel = intent.extras?.getSerializable("game") as GameModel

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
}