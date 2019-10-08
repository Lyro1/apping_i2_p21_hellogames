package fr.lyro.hellogames

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

import kotlinx.android.synthetic.main.activity_game_details.*
import kotlinx.android.synthetic.main.content_game_details.*

class GameDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_details)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        val originIntent = intent

        val selectedGame = GameObject(
            originIntent.getIntExtra("id", 0),
            originIntent.getStringExtra("name"),
            originIntent.getStringExtra("type"),
            originIntent.getIntExtra("players", 0),
            originIntent.getIntExtra("year", 0),
            originIntent.getStringExtra("url"),
            originIntent.getStringExtra("picture"),
            originIntent.getStringExtra("description_fr"),
            originIntent.getStringExtra("description_en")
        )

        Glide.with(this).load(selectedGame.picture).into(logo)
        label_name.text = "Name: " + selectedGame.name
        label_type.text = "Type: " + selectedGame.type
        label_nb_players.text = "Nb player: " + selectedGame.players
        label_year.text = "Year:" + selectedGame.year

        description.text = selectedGame.description_en

        val implicitIntent = Intent(Intent.ACTION_VIEW)
        implicitIntent.data = Uri.parse(selectedGame.url)

        button_details.setOnClickListener {
            view -> startActivity(implicitIntent)
        }
    }

}
