package b3ast.thunder.ageinminutes

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        btnDatePicker.setOnClickListener { view ->
            clickDataPicker(view)
        }
    }

    fun clickDataPicker(view: View) {

        val c = Calendar.getInstance()
        val year =
            c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)


        val dpd = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

                val selectedDate = "$dayOfMonth/${monthOfYear + 1}/$year"

                tvSelectedDate.setText(selectedDate)

                val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)

                val theDate = sdf.parse(selectedDate)

                val selectedDateToMinutes = theDate!!.time / 60000

                val currentDate = sdf.parse(sdf.format(System.currentTimeMillis()))

                val currentDateToMinutes = currentDate!!.time / 60000

                var differenceInMinutes = currentDateToMinutes - selectedDateToMinutes


                var ageYearOld: Int = (differenceInMinutes / 525600).toInt()
                val ageDaysOld: Int = (differenceInMinutes / 1440).toInt()
                val ageSecondsOld = differenceInMinutes * 60
                val ageHoursOld = differenceInMinutes / 60

                tvSelectedDateInYears.setText("$ageYearOld years")
                tvSelectedDateInDays.setText("$ageDaysOld days")
                tvSelectedDateInHours.setText("$ageHoursOld hours")
                tvSelectedDateInMinutes.setText("$differenceInMinutes minutes")
                tvSelectedDateInSeconds.setText("$ageSecondsOld seconds")
            },
            year,
            month,
            day
        )

        dpd.datePicker.setMaxDate(Date().time - 86400000)
        dpd.show()
    }

}