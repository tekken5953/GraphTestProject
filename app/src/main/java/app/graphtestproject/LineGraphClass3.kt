package app.graphtestproject

import android.graphics.Color
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet

class LineGraphClass3 {
    private lateinit var lineChart: LineChart
    private lateinit var lineDataSet1: LineDataSet
    private lateinit var lineDataSet2: LineDataSet

    fun getInstance(chart: LineChart): LineGraphClass3 {
        lineChart = chart
        return this
    }

    fun setDataSet(): LineGraphClass3 {
        // LineDataSet1 설정
        lineDataSet1 = LineDataSet(ArrayList(), "Line 1")
        lineDataSet1.color = Color.RED
        lineDataSet1.setCircleColor(Color.RED)
        lineDataSet1.valueTextColor = Color.RED

        // LineDataSet2 설정
        lineDataSet2 = LineDataSet(ArrayList(), "Line 2")
        lineDataSet2.color = Color.BLUE
        lineDataSet2.setCircleColor(Color.BLUE)
        lineDataSet2.valueTextColor = Color.BLUE

        lineChart.setBackgroundColor(Color.parseColor("#40FFFFFF")) // 배경 색
        lineChart.setTouchEnabled(true)
        lineChart.isEnabled = true
        lineChart.isClickable = false
        lineChart.legend.isEnabled = false
        lineChart.description.isEnabled = false // description 표시
        lineChart.axisRight.isEnabled = false
        lineChart.axisLeft.isEnabled = true
        lineChart.isDragEnabled = true
        lineChart.isAutoScaleMinMaxEnabled = false
//            lineChart.setOnChartValueSelectedListener(this@LineGraphClass)
        lineChart.setScaleEnabled(false)
        lineChart.setPinchZoom(false) // pinch zoom
        lineChart.minOffset = 5f
        lineChart.setVisibleXRangeMaximum(5f)
        lineChart.isDoubleTapToZoomEnabled = false
        val lineData = LineData(lineDataSet1, lineDataSet2)
        lineChart.data = lineData

//        setYAxis()
//        setXAxis()
        return this
    }

//    private fun setXAxis() {
//        // X축
//        val xAxis = lineChart.xAxis
//        xAxis.position = XAxis.XAxisPosition.BOTTOM // X축을 그래프 아래로 위치하기
//        xAxis.textSize = 10f // 레이블 텍스트 사이즈
//        xAxis.textColor = Color.GRAY // 레이블 텍스트 색
//        xAxis.axisLineColor = Color.GRAY // 축 색
//        xAxis.setDrawAxisLine(false) // 그래프 뒷 배경의 그리드 표시
//        xAxis.setDrawGridLines(false) // 그래프 뒷 배경의 그리드 표시
//        xAxis.spaceMax = 0.5f // 레이블 간격
//        xAxis.isGranularityEnabled = true // 축 레이블 표시 간격
//        xAxis.granularity = 1f // 축 레이블 표시 간격
//        xAxis.setAvoidFirstLastClipping(true)
//    }
//
//    private fun setYAxis() {
//        // Y축
//        val yAxis = lineChart.axisLeft
//        yAxis.textSize = 10f
//        yAxis.textColor = Color.GRAY
//        yAxis.axisLineColor = Color.GRAY
//        yAxis.setDrawAxisLine(false)
//        yAxis.setDrawGridLines(false)
//    }


    fun addEntryToLine(sort: Int) {
        val lineDataset = if (sort == 0) lineDataSet1 else lineDataSet2
        val lineData = lineChart.data
        val lineEntries: MutableList<Entry> = lineDataset.values as MutableList<Entry>
        val nextX = lineEntries.size.toFloat()
        val nextY = (Math.random() * 50).toFloat() // 임의의 Y값 생성

        lineData.addEntry(Entry(nextX, nextY),sort)

        lineDataset.values = lineEntries

        // LineData에 변경된 LineDataSet 반영
        lineData.notifyDataChanged()
        lineChart.notifyDataSetChanged()
        lineChart.invalidate()
    }
}