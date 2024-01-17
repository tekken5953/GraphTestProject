package app.graphtestproject

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var pmGraphInstance: LineGraphClass

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val entry2 = java.util.ArrayList<Entry>()
        repeat(24) {
            entry2.add(Entry(it.toFloat(), Random.nextFloat() * 70))
        }
        createPMChart(entry2)
    }

    private fun createPMChart(pm10Entry: ArrayList<Entry>) {
        try {
            val lineChart = findViewById<LineChart>(R.id.lineChart)
            LineGraphClass(this,true)
                .getInstance(lineChart)
                .clear()
                .setChart()
                .addDataSet("미세먼지", pm10Entry)
                .createGraph()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}