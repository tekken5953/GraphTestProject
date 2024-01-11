package app.graphtestproject

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.animation.AlphaAnimation
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import java.text.SimpleDateFormat

class LineGraphClass(private val context: Context,private val isGradient: Boolean) {
    private lateinit var mChart: LineChart
    private lateinit var lineData: LineData

    private var CHART_MADE_TIME = 0L
    private val xLabelList = ArrayList<String>()

    fun getInstance(chartView: LineChart): LineGraphClass {
        mChart = chartView
        lineData = LineData()
        setYAxis()
        setXAxis()
        return this
    }

    fun setChart(): LineGraphClass {
        try {
            mChart.apply {
                setBackgroundColor(Color.parseColor("#40FFFFFF")) // 배경 색
                this.legend.isEnabled = false
                description.isEnabled = false // description 표시
//                setTouchEnabled(false) // 그래프 터치
                axisRight.isEnabled = false
                axisLeft.isEnabled = true
                isDragEnabled = true
                zoom(1.3f,0f,1.3f,0f)
                isHighlightPerTapEnabled = false
                setScaleEnabled(false)
                setPinchZoom(false) // pinch zoom
                this.minOffset = 15f
//                enableScroll()
                mChart.setVisibleXRangeMaximum(5f)
                isDoubleTapToZoomEnabled = false
                isLongClickable = true
                this.isAutoScaleMinMaxEnabled = false
             }
        } catch (e: Exception) {
            e.stackTraceToString()
        }

        return this
    }

    private fun setXAxis() {
        mChart.xAxis.apply {
            position = XAxis.XAxisPosition.BOTTOM // X축을 그래프 아래로 위치하기
            textSize = 10f // 레이블 텍스트 사이즈
            textColor = Color.GRAY // 레이블 텍스트 색
            axisLineColor = Color.GRAY // 축 색
            setDrawAxisLine(false) // 그래프 뒷 배경의 그리드 표시
            setDrawGridLines(false) // 그래프 뒷 배경의 그리드 표시
            spaceMax = 2f // 레이블 간격
            isGranularityEnabled = true // 축 레이블 표시 간격
            granularity = 1f // 축 레이블 표시 간격
            this.axisMaximum = 5f
            this.setAvoidFirstLastClipping(true)
            setAvoidFirstLastClipping(true)
            valueFormatter = XAxisValueFormat()
        }
    }

    private fun setYAxis() {
        mChart.axisLeft.apply {
            textSize = 10f
            textColor = Color.GRAY
            axisLineColor = Color.GRAY
            setDrawAxisLine(false)
            setDrawGridLines(false)
        }
    }

    fun addDataSet(sort: String, entry: ArrayList<Entry>): LineGraphClass {
        try {
            val applyColor = if (sort == "미세먼지") Color.parseColor("#FF0000")
            else Color.parseColor("#cc0004ff")

            val dataSet = LineDataSet(ArrayList(), sort) // DataSet 생성
            dataSet.apply {
                mode = LineDataSet.Mode.CUBIC_BEZIER // 선 그리는 방식
                color = applyColor // 선 색
                valueTextColor = applyColor // 데이터 수치 텍스트 색
                valueTextSize = 12f // 데이터 수치 텍스트 사이즈
                lineWidth = 2f // 선 굵기
                setDrawCircleHole(false)
                setDrawCircles(true)
                circleRadius = 2.5F
                setCircleColor(applyColor)
                if (isGradient) {
                    this.setDrawFilled(true)
                    this.fillAlpha = 70
                    this.fillDrawable = if (sort == "미세먼지") ContextCompat.getDrawable(context, R.drawable.graph_fill_red)
                    else ContextCompat.getDrawable(context, R.drawable.graph_fill_blue)
                }
            }
            lineData.addDataSet(dataSet)
            entry.forEach {
                lineData.addEntry(it,if (sort == "미세먼지") 0 else 1)
                lineData.notifyDataChanged()
                mChart.notifyDataSetChanged()
            }
        } catch (e: Exception) {
            e.stackTraceToString()
        }

        return this
    }

    fun createGraph() {
        mChart.data = lineData // 데이터 적용
        val fadeIn = AlphaAnimation(0f,1f).apply {
            duration = 400
            repeatCount = 0
        }
        mChart.startAnimation(fadeIn)
        mChart.animateX(400)
        mChart.invalidate()
        mChart.moveViewToX(mChart.lineData.entryCount.toFloat()) // 가장 최근에 추가한 데이터의 위치로 이동처리
    }

    class DataSetValueFormat : IndexAxisValueFormatter() {
        override fun getFormattedValue(value: Float): String {
            return value.toInt().toString()
        }
    }

    //X축 엔트리 포멧
    inner class XAxisValueFormat : IndexAxisValueFormatter() {
        override fun getFormattedValue(value: Float): String {
            return chartTimeDivider(xLabelList, value.toInt())
        }
    }

    // 현재 시간기준으로 그래프의 X축 라벨을 포맷합니다
    private fun chartTimeDivider(arrayList: ArrayList<String>, mCount: Int): String {
        try {
            @SuppressLint("SimpleDateFormat") val simpleDateFormat = SimpleDateFormat("hh:mm")
            CHART_MADE_TIME = System.currentTimeMillis()
            val lArray: Long
            return when (mCount) {
                0 -> {
                    lArray = CHART_MADE_TIME
                    arrayList.add(0, simpleDateFormat.format(lArray))
                    arrayList[0]
                }
                else -> {
                    lArray = CHART_MADE_TIME + (mCount-1) * 60 * 60 * 1000
                    arrayList.add(mCount-1, simpleDateFormat.format(lArray))
                    arrayList[mCount-1]
                }
            }
        } catch (e: IndexOutOfBoundsException) {
        } catch (e: NullPointerException) {
        }
        return " "
    }

}