package ru.bsuir.feedthecat

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import ru.bsuir.feedthecat.databinding.ActivityMainBinding
import ru.bsuir.feedthecat.model.Statistic
import ru.bsuir.feedthecat.service.DatabaseManager
import java.time.LocalDateTime

class MainActivity : AppCompatActivity() {

    private val databaseManager = DatabaseManager(this)

    private lateinit var binding: ActivityMainBinding
    private lateinit var toggle: ActionBarDrawerToggle

    private lateinit var animation: Animation

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        toggle = ActionBarDrawerToggle(this, binding.drawerLayout, R.string.open, R.string.close)
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        animation = AnimationUtils.loadAnimation(this, R.anim.anim)
        initNavigationViewListener()

        binding.feedButton.setOnClickListener {
            feedButtonPressed()
        }
    }

    private fun initNavigationViewListener() {
        binding.navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.about_menu -> {
                    val intent = Intent(this, AboutActivity::class.java)
                    startActivity(intent)
                }
                R.id.score_menu -> {
                    val intent = Intent(this, StatisticActivity::class.java)
                    startActivity(intent)
                }
                R.id.share_menu -> {
                    val intent = Intent().apply {
                        this.action = Intent.ACTION_SEND
                        this.putExtra(
                            Intent.EXTRA_TEXT,
                            "My new score is: " + binding.score.text.toString()
                        )
                        this.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        this.type = "text/plain"
                    }
                    startActivity(Intent.createChooser(intent, "Share score"))
                }
            }
            true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) return true

        return super.onOptionsItemSelected(item)
    }

    private fun feedButtonPressed() {
        val scoreValue = binding.score.text.toString().toInt()
        val updatedValue = scoreValue + 1
        binding.score.text = updatedValue.toString()
        if (updatedValue % 15 == 0) {
            binding.catImage.startAnimation(animation)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onDestroy() {
        databaseManager.openDb()
        databaseManager.insert(
            Statistic(
                time = LocalDateTime.now().toString(),
                satiety = binding.score.text.toString().toInt()
            )
        )
        databaseManager.close()
        super.onDestroy()
    }
}