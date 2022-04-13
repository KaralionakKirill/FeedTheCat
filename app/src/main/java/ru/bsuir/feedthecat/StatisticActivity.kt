package ru.bsuir.feedthecat

import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import ru.bsuir.feedthecat.databinding.ScoreLayoutBinding
import ru.bsuir.feedthecat.model.Score
import ru.bsuir.feedthecat.service.DatabaseManager
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class StatisticActivity : AppCompatActivity() {
    private val databaseManager = DatabaseManager(this)
    private lateinit var binding: ScoreLayoutBinding

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ScoreLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initTable()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun initTable() {
        databaseManager.openDb()
        val scoreList = databaseManager.read()
        val table = findViewById<View>(R.id.table) as TableLayout

        scoreList.forEach {score ->
            val row = createTableRow()

            fillTableRow(row, score)

            table.addView(row)

        }
        databaseManager.close()
    }

    private fun createTableRow(): TableRow {
        val tableRow = TableRow(this)
        tableRow.layoutParams = TableRow.LayoutParams(
            TableRow.LayoutParams.MATCH_PARENT,
            TableRow.LayoutParams.WRAP_CONTENT,
        )
        tableRow.background =
            ResourcesCompat.getDrawable(
                applicationContext.resources,
                R.drawable.table_border,
                null
            )

        tableRow.weightSum = 100F
        tableRow.dividerDrawable = ResourcesCompat.getDrawable(
            applicationContext.resources,
            R.color.black,
            null
        )
        tableRow.showDividers = TableLayout.SHOW_DIVIDER_MIDDLE

        return tableRow
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun fillTableRow(row: TableRow, score: Score) {
        val scoreTextView = TextView(this)

        scoreTextView.layoutParams = TableRow.LayoutParams(
            TableRow.LayoutParams.FILL_PARENT,
            TableRow.LayoutParams.WRAP_CONTENT,
            50f
        )

        scoreTextView.gravity = Gravity.CENTER
        scoreTextView.text = score.score.toString()
        scoreTextView.textSize = 20F

        val dateTextView = TextView(this)

        dateTextView.layoutParams = TableRow.LayoutParams(
            TableRow.LayoutParams.FILL_PARENT,
            TableRow.LayoutParams.WRAP_CONTENT,
            50f
        )
        dateTextView.gravity = Gravity.CENTER
        dateTextView.text = LocalDateTime.parse(score.date)
            .format(DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm:ss a")).toString()
        dateTextView.textSize = 20F
        row.addView(scoreTextView)
        row.addView(dateTextView)
    }
}