import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Color
import android.util.Log
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import java.text.SimpleDateFormat


// 선 그래프 설정
class DrawGraphClass2(private val lineChart: LineChart, private val context: Activity) :
    Thread() {

    var CHART_MADE_TIME = 0L
    var lineData: LineData? = LineData()
    var legend = Legend()
    var lineDataSet: LineDataSet? = null

    private val xLabelList = ArrayList<String>()

    // 그래프 X축, Y축 설정 및 모양 설정
    private fun setChart(setYMax: Int) {
        // X축
        val xAxis: XAxis = lineChart.xAxis
        xAxis.setDrawLabels(true) // 라벨 표시 여부
        xAxis.textColor = Color.BLUE
        xAxis.position = XAxis.XAxisPosition.BOTTOM // X축 라벨 위치
        xAxis.setDrawAxisLine(false) // AxisLine 표시
        xAxis.setDrawGridLines(false) // GridLine 표시
        xAxis.isGranularityEnabled = false // x축 간격을 제한하는 세분화 기능
        xAxis.valueFormatter = XAxisValueFormat() // X축 라벨데이터 포멧
        xAxis.granularity = 1f
        lineChart.isAutoScaleMinMaxEnabled = true // Max = Count
        xAxis.labelCount = 6 // 라벨 갯수
        xAxis.textSize = 12f // x축 데이터 크기
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

        // Y축
        val yAxis: YAxis = lineChart.axisLeft
        yAxis.axisMaximum = setYMax.toFloat() // Y축 값 최대값 설정
        yAxis.axisMinimum = 0.toFloat() // Y축 값 최솟값 설정
        yAxis.textColor = Color.BLUE // y축 글자 색상
        yAxis.valueFormatter = YAxisValueFormat() // y축 데이터 포맷
        yAxis.isGranularityEnabled = false // y축 간격을 제한하는 세분화 기능
        yAxis.setDrawLabels(true) // Y축 라벨 위치
        yAxis.labelCount = 0 // Y축 라벨 개수
        yAxis.textSize = 12f // Y축 라벨 텍스트 사이즈
        yAxis.setDrawGridLines(false) // GridLine 표시
        yAxis.setDrawAxisLine(false) // AxisLine 표시
        legend.isEnabled = false // 범례 비활성화
        lineChart.data = lineData // 라인차트 데이터 설정
    }

    // 차트에 쓰일 목록 UI Thread 에서 가져오기
    fun feedMultiple(SetYMax: Int, yData: Float): DrawGraphClass2 {
        context.runOnUiThread {
            setChart(SetYMax)
            addEntry(yData)
        }
        return this
    }

    // 엔트리 추가하기
    fun addEntry(yData: Float):DrawGraphClass2 {
        // 라인 차트
        context.runOnUiThread {
            if (lineData != null) {
                lineData = lineChart.data
                createSet()
                lineData!!.addDataSet(lineDataSet)
                lineData!!.addEntry(
                    Entry(lineData!!.entryCount.toFloat(), yData), 0
                ) // 데이터 엔트리 추가
                Log.d("GraphAdd", "Add Graph Entry " + lineData!!.entryCount + " : " + yData)
                lineData!!.notifyDataChanged() // 데이터 변경 알림
                lineChart.notifyDataSetChanged() // 라인차트 변경 알림
            }
        }
        return this
    }

    private fun createSet() {
        lineDataSet = LineDataSet(null, null) // 범례, yVals 설정
        lineDataSet!!.axisDependency = YAxis.AxisDependency.LEFT // Y값 데이터를 왼쪽으로
        lineDataSet!!.fillColor = Color.BLUE // 차트 채우기 색상
        lineDataSet!!.setDrawFilled(true) // 차트 채우기 설정
        lineDataSet!!.isHighlightEnabled = false // 하이라이트 설정
        lineDataSet!!.lineWidth = 2f // 그래프 선 굵기
        lineDataSet!!.valueTextColor = Color.BLUE
        lineDataSet!!.mode = LineDataSet.Mode.CUBIC_BEZIER // 선 그리는 방식
        lineDataSet!!.setDrawCircleHole(false) // 원 안에 작은 원 표시
        lineDataSet!!.setDrawCircles(false) // 원 표시
        lineDataSet!!.setDrawValues(true) // 차트포인트에 값 표시
        lineDataSet!!.valueFormatter = DataSetValueFormat()
        lineDataSet!!.valueTextSize = 11f // 차트 텍스트 크기
        lineDataSet!!.color = Color.BLUE
    }

    //Y축 엔트리 포멧
    inner class YAxisValueFormat : IndexAxisValueFormatter() {
        override fun getFormattedValue(value: Float): String {
            val newValue = value.toString() + ""
            return if (newValue.contains("\\.")) {
                val s = newValue.split("\\.").toTypedArray()
                s[0]
            } else {
                newValue.substring(0, newValue.length - 2)
            }
        }
    }

    inner class DataSetValueFormat : IndexAxisValueFormatter() {
        override fun getFormattedValue(value: Float): String {
            return value.toInt().toString() + ""
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