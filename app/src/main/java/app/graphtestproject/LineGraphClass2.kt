package app.graphtestproject

import android.content.Context
import android.graphics.Color
import android.view.animation.AlphaAnimation
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter

class LineGraphClass2(private val context: Context, private val isGradient: Boolean) {
    private lateinit var mChart: LineChart
    private lateinit var lineData: LineData

    fun getInstance(chartView: LineChart): LineGraphClass2 {
        mChart = chartView
        lineData = LineData()
        return this
    }

    private fun setChart(): LineGraphClass2 {
        try {
            mChart.apply {
//                setBackgroundResource(R.drawable.pm_graph_bg)
                setBackgroundColor(Color.TRANSPARENT) // 배경 색
                legend.isEnabled = false
                description.isEnabled = false // description 표시
                setTouchEnabled(true) // 그래프 터치
                isClickable = false
                axisRight.isEnabled = false
                axisLeft.isEnabled = true
                isDragEnabled = true
//                zoom(1.3f, 0f, 1.3f, 0f)
                isHighlightPerTapEnabled = false
                minOffset = 35f
                setScaleEnabled(false)
                setPinchZoom(false) // pinch zoom
//                mChart.setVisibleXRangeMaximum(5f)
                isDoubleTapToZoomEnabled = false
                isLongClickable = false
                isAutoScaleMinMaxEnabled = false
                setYAxis()
                setXAxis()
            }
        } catch (e: Exception) {
            e.stackTraceToString()
            Toast.makeText(context, "차트 세팅 에러", Toast.LENGTH_SHORT).show()
        }

        return this
    }

    private fun setXAxis() {
        mChart.xAxis.apply {
            position = XAxis.XAxisPosition.BOTTOM // X축을 그래프 아래로 위치하기
            textSize = 12f // 레이블 텍스트 사이즈
            textColor = Color.WHITE // 레이블 텍스트 색
            axisLineColor = Color.WHITE // 축 색
            setDrawAxisLine(false) // 그래프 뒷 배경의 그리드 표시
            setDrawGridLines(false) // 그래프 뒷 배경의 그리드 표시
            spaceMax = 1f // 레이블 간격
            isGranularityEnabled = true // 축 레이블 표시 간격
            granularity = 1f // 축 레이블 표시 간격
            setDrawLabels(true)
            setAvoidFirstLastClipping(false)
        }
    }

    private fun setYAxis() {
        // Y축
        mChart.axisLeft.apply {
            textSize = 12f
            textColor = Color.WHITE
            axisLineColor = Color.WHITE
            setDrawAxisLine(false)
            setDrawGridLines(true)
            spaceMax = 5f
            labelCount = 5
            enableGridDashedLine(25f,15f,0f)
            gridColor = Color.parseColor("#80FFFFFF")
        }
    }

    fun addDataSet(sort: String): LineGraphClass2 {
        val dataSet = LineDataSet(null, sort)
        dataSet.apply {
            label = sort
            mode = LineDataSet.Mode.LINEAR // 선 그리는 방식
            color = Color.TRANSPARENT // 선 색
            valueTextColor = Color.WHITE // 데이터 수치 텍스트 색
            valueTextSize = 14f // 데이터 수치 텍스트 사이즈
            lineWidth = 2f // 선 굵기
            setDrawCircleHole(false)
            setDrawCircles(true)
            valueFormatter = DataSetValueFormat()
            circleRadius = 6F
            setCircleColor(Color.WHITE)
            if (isGradient) {
                this.setDrawFilled(true)
                this.fillDrawable = ContextCompat.getDrawable(
                    context,
                    R.drawable.graph_fill_red
                )
            }
        }
//            lineData.addDataSet(dataSet)

        lineData.addDataSet(dataSet)

        return this
    }

    private fun createGraph(): LineGraphClass2 {
        mChart.data = lineData // 데이터 적용
        val fadeIn = AlphaAnimation(0f,1f).apply {
            duration = 400
            repeatCount = 0
        }
        mChart.startAnimation(fadeIn)
        mChart.animateX(400)
        mChart.invalidate()
        mChart.moveViewToX(mChart.lineData.entryCount.toFloat()) // 가장 최근에 추가한 데이터의 위치로 이동처리
        mChart.data.notifyDataChanged()
        mChart.notifyDataSetChanged()

        return this
    }

//    override fun onValueSelected(e: Entry?, h: Highlight?) {
//        selected.text = e!!.y.toInt().toString()
//        Log.d("testtest","value selected ${e!!.y}")
//    }
//
//    override fun onNothingSelected() {
//        selected.text = "Nothing Selected"
//        Log.w("testtest","nothing selected")
//    }

    inner class DataSetValueFormat : IndexAxisValueFormatter() {
        override fun getFormattedValue(value: Float): String {
            return value.toInt().toString()
        }
    }

    // 엔트리 추가하기
    fun addEntry(yData: Float) {
        // 라인 차트
        setChart()
        mChart.data?.let {
            lineData = mChart.data
        }

        lineData.addEntry(
            Entry(
                lineData.entryCount.toFloat(), yData
            ), 0
        ) // 데이터 엔트리 추가
        mChart.moveViewToAnimated(lineData.entryCount.toFloat(),yData,YAxis.AxisDependency.RIGHT,500L)
//        lineData.notifyDataChanged() // 데이터 변경 알림
//        mChart.notifyDataSetChanged() // 라인차트 변경 알림
        createGraph()
    }

    fun clearData() {
        mChart.removeAllViews()
        mChart.notifyDataSetChanged()
    }
}