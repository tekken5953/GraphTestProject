package app.graphtestproject

import android.content.Context
import android.content.pm.ApplicationInfo
import android.graphics.Color
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet


class BarChartClass(private val context: Context, private val barChart: BarChart) {
    // 바 차트 설정
    fun initBarChart(): BarChartClass {
        val barDataSet25 = BarDataSet(getBarEntriesOne(), "초미세먼지")
        val barDataSet10 = BarDataSet(getBarEntriesTwo(), "미세먼지")
        barDataSet25.valueTextSize = 14f
        barDataSet25.setValueTextColors(mutableListOf(Color.WHITE))
        barDataSet25.color = Color.RED
        barDataSet10.valueTextSize = 14f
        barDataSet10.setValueTextColors(mutableListOf(Color.WHITE))
        barDataSet10.color = Color.BLUE

        val data = BarData(barDataSet25, barDataSet10)

        barChart.data = data

        barChart.setPinchZoom(false)
        barChart.setScaleEnabled(false)
        barChart.description.isEnabled = false
        barChart.isLongClickable = false
        barChart.isHighlightFullBarEnabled = true
        barChart.isHighlightPerTapEnabled = true
        barChart.setDrawMarkers(true)
        barChart.marker = CustomMarkerView(context,R.layout.custom_maker)


        val xAxis = barChart.xAxis

        xAxis.setCenterAxisLabels(true)
        xAxis.setDrawGridLines(false)
        xAxis.setDrawAxisLine(false)
        xAxis.textSize = 12f
        xAxis.textColor = Color.WHITE
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.granularity = 1f
        xAxis.isGranularityEnabled = true
        xAxis.setAvoidFirstLastClipping(true)

        barChart.axisRight.isEnabled = false

        val yAxis = barChart.axisLeft

        yAxis.textSize = 12f
        yAxis.textColor = Color.WHITE
        yAxis.setDrawGridLines(false)
        yAxis.setDrawAxisLine(false)

        barChart.isDragEnabled = true

        barChart.setVisibleXRangeMaximum(20f)

        barChart.zoom(1.2f,0f,1.2f,0f)

        barChart.groupBars(0.2f, 0.3f, 0.1f)
        barChart.invalidate()

        return this
    }

    // array list for first set
    private fun getBarEntriesOne(): ArrayList<BarEntry> {

        // creating a new array list
        val barEntries = ArrayList<BarEntry>()

        // adding new entry to our array list with bar
        // entry and passing x and y axis value to it.
        barEntries.add(BarEntry(1f, 4f))
        barEntries.add(BarEntry(2f, 6f))
        barEntries.add(BarEntry(3f, 8f))
        barEntries.add(BarEntry(4f, 2f))
        barEntries.add(BarEntry(5f, 4f))
        barEntries.add(BarEntry(6f, 1f))
        return barEntries
    }

    // array list for second set.
    private fun getBarEntriesTwo(): ArrayList<BarEntry> {

        // creating a new array list
        val barEntries = ArrayList<BarEntry>()

        // adding new entry to our array list with bar
        // entry and passing x and y axis value to it.
        barEntries.add(BarEntry(1f, 8f))
        barEntries.add(BarEntry(2f, 12f))
        barEntries.add(BarEntry(3f, 4f))
        barEntries.add(BarEntry(4f, 1f))
        barEntries.add(BarEntry(5f, 7f))
        barEntries.add(BarEntry(6f, 3f))
        return barEntries
    }
}