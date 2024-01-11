package app.graphtestproject

import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val lineChart = findViewById<LineChart>(R.id.lineChart)
        val editText = findViewById<EditText>(R.id.editText)
        val addBtn = findViewById<Button>(R.id.addBtn)

        lineChart.setNoDataText("현재 생성된 엔트리가 없습니다")
        lineChart.setNoDataTextColor(Color.WHITE)

        addBtn.setOnClickListener { 
            try {
                create2(editText.text.toString().toFloat())
            } catch (e: Exception) {
                Toast.makeText(this, "올바른 데이터가 아닙니다", Toast.LENGTH_SHORT).show()
            }
        }
        val clearData = findViewById<Button>(R.id.clearBtn)
        clearData.setOnClickListener {
            lineChart.clear()
        }

//        create3()
//        create1()

//       DrawGraphClass2(lineChart,this)
//            .feedMultiple(150,50f).addEntry(40f).addEntry(90f)
//           .addEntry(120f).addEntry(40f).addEntry(10f).addEntry(70f)
//           .addEntry(10f).addEntry(30f).addEntry(50f).addEntry(20f)
//           .addEntry(10f).addEntry(30f).addEntry(50f).addEntry(20f)
//           .addEntry(10f).addEntry(30f).addEntry(50f).addEntry(20f)
//           .addEntry(10f).addEntry(30f).addEntry(50f).addEntry(20f)
    }

//    private fun create3() {
//        val btn1 = findViewById<Button>(R.id.addE1)
//        val btn2 = findViewById<Button>(R.id.addE2)
//
//        val instance = LineGraphClass3()
//            .getInstance(findViewById<LineChart>(R.id.lineChart))
//            .setDataSet()
//
//        btn1.setOnClickListener {
//            instance.addEntryToLine(0)
//        }
//        btn2.setOnClickListener {
//            instance.addEntryToLine(1)
//        }
//    }

    private fun create2(yValue: Float) {
        try {
//            val yValue1 = listOf(
//                Random.nextFloat() * 50,
//                Random.nextFloat() * 50,
//                Random.nextFloat() * 50,
//                Random.nextFloat() * 50,
//                Random.nextFloat() * 50,
//                Random.nextFloat() * 50,
//                Random.nextFloat() * 50,
//                Random.nextFloat() * 50,
//                Random.nextFloat() * 50,
//
//            )
//            val yValue2 = listOf(
//                Random.nextFloat() * 100,
//                Random.nextFloat() * 100,
//                Random.nextFloat() * 100,
//                Random.nextFloat() * 100,
//                Random.nextFloat() * 100,
//                Random.nextFloat() * 100,
//                Random.nextFloat() * 100,
//                Random.nextFloat() * 100,
//                Random.nextFloat() * 100
//            )

//            val entry1 = ArrayList<Entry>()
//            yValue1.forEachIndexed { i, d ->
//                entry1.add(Entry(i.toFloat(), d))
//            }
//            val entry2 = ArrayList<Entry>()
//            yValue2.forEachIndexed { i, d ->
//                entry2.add(Entry(i.toFloat(), d))
//            }

            val lineChart = findViewById<LineChart>(R.id.lineChart)

            val chart = LineGraphClass2(this,true)
                .getInstance(lineChart)
                .addDataSet("미세먼지")


            chart.addEntry(yValue)

//            yValue1.forEach {
//                chart.addEntry(it)
//            }
//
//            yValue2.forEach {
//                chart.addEntry(it)
//            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun create1() {
        try {
            val yValue1 = listOf(
                Random.nextFloat() * 50,
                Random.nextFloat() * 50,
                Random.nextFloat() * 50,
                Random.nextFloat() * 50,
                Random.nextFloat() * 50,
                Random.nextFloat() * 50,
                Random.nextFloat() * 50,
                Random.nextFloat() * 50,
                Random.nextFloat() * 50,
                Random.nextFloat() * 50,
                Random.nextFloat() * 50,
                Random.nextFloat() * 50,
                Random.nextFloat() * 50,
                Random.nextFloat() * 50,
                Random.nextFloat() * 50,
                Random.nextFloat() * 50,
                Random.nextFloat() * 50,
                Random.nextFloat() * 50
            )
            val yValue2 = listOf(
                Random.nextFloat() * 100,
                Random.nextFloat() * 100,
                Random.nextFloat() * 100,
                Random.nextFloat() * 100,
                Random.nextFloat() * 100,
                Random.nextFloat() * 100,
                Random.nextFloat() * 100,
                Random.nextFloat() * 100,
                Random.nextFloat() * 100,
                Random.nextFloat() * 100,
                Random.nextFloat() * 100,
                Random.nextFloat() * 100,
                Random.nextFloat() * 100,
                Random.nextFloat() * 100,
                Random.nextFloat() * 100,
                Random.nextFloat() * 100,
                Random.nextFloat() * 100,
                Random.nextFloat() * 100
                )

            val entry1 = ArrayList<Entry>()
            yValue1.forEachIndexed { i, d ->
                entry1.add(Entry(i.toFloat(), d))
            }
            val entry2 = ArrayList<Entry>()
            yValue2.forEachIndexed { i, d ->
                entry2.add(Entry(i.toFloat(), d))
            }

            val lineChart = findViewById<LineChart>(R.id.lineChart)

            LineGraphClass(this, false)
                .getInstance(lineChart)
                .setChart()
                .addDataSet("미세먼지",entry1)
                .addDataSet("초미세먼지",entry2)
                .createGraph()

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}