package b3ast.thunder.ageinminutes

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
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
        val year = c.get(Calendar.YEAR)
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

                var tempMinutes = differenceInMinutes

                var currYear = currentDate.year
                var currMonth = currentDate.month
                var currDate = currentDate.date
                var selYear = theDate.year
                var selMonth = theDate.month
                var selDate = theDate.date

                currMonth++
                selMonth++

                Log.d("PRINT STATEMENTS", "Selected date: $selDate/$selMonth/$selYear")
                Log.d("PRINT STATEMENTS", "Current date: $currDate/$currMonth/$currYear")

                var numberOfMonths: Int = 0
                var numberofDays: Int = 0
                var numberofYears: Int = 0

                if(selMonth<currMonth){

                    Log.d("PRINT STATEMENTS", "Inside first IF condition")

                    numberofYears = currYear - selYear
                    if(selDate<currDate){

                        Log.d("PRINT STATEMENTS", "IF -> IF")

                        numberOfMonths = currMonth-selMonth
                        numberofDays = currDate - selDate
                    }
                    else if(selDate>currDate){

                        Log.d("PRINT STATEMENTS", "IF -> ELSE IF 1")

                        numberOfMonths = currMonth - selMonth - 1
                        Log.d("PRINT STATEMENTS", " current month = $currMonth, selected month = $selMonth, numberofmonths = $numberOfMonths")
                        var remainingDay = remainingDays(selMonth, currYear)
                        numberofDays= remainingDays(selMonth, selYear) - selDate + currDate + 1
                        Log.d("PRINT STATEMENTS", "$remainingDay, $numberofDays")
                    }
                    else if(selDate==currDate){

                        Log.d("PRINT STATEMENTS", "IF -> ELSE IF 2")

                        numberOfMonths = currMonth - selMonth
                        numberofDays = 0
                    }
                }
                else if(selMonth>currMonth){

                    Log.d("PRINT STATEMENTS", "Inside first Else IF condition")

                    numberofYears = currYear - selYear - 1
                    if(selDate<currDate){

                        Log.d("PRINT STATEMENTS", "ELSE IF 1 -> IF")

                        numberOfMonths = (12-selMonth) + currMonth
                        numberofDays = currDate - selDate
                    }
                    else if(selDate>currDate){

                        Log.d("PRINT STATEMENTS", "ELSE IF 1 -> ELSE IF 1")

                        numberOfMonths = (12-selMonth) + currMonth - 1
                        numberofDays = remainingDays(selMonth, selYear) - selDate + currDate + 1
                    }
                    else if(selDate==currDate){

                        Log.d("PRINT STATEMENTS", "ELSE IF 1 -> ELSE IF 2")

                        numberOfMonths = (12-selMonth) + currMonth
                        numberofDays = 0
                    }
                }
                else if(selMonth==currMonth){

                    Log.d("PRINT STATEMENTS", "Inside second Ese IF condition")

                    numberofYears = currYear - selYear
                    if(selDate<currDate){

                        Log.d("PRINT STATEMENTS", "ELSE IF 2 -> IF")

                        numberOfMonths = 12
                        numberofDays = currDate - selDate
                    }
                    else if(selDate>currDate){

                        Log.d("PRINT STATEMENTS", "ELSE IF 2 -> ELSE IF 1")

                        numberOfMonths =11
                        numberofDays = remainingDays(selMonth, selYear) - selDate + currDate + 1
                    }
                    else if(selDate==currDate){

                        Log.d("PRINT STATEMENTS", "ELSE IF 2 -> ELSE IF 2")
                        numberOfMonths = 0
                        numberofDays = 0
                    }
                }

                numberofYears = numberofYears + numberOfMonths/12
                numberOfMonths = numberOfMonths%12

                tvSelectedDateInYears.setText("$numberofYears year/s")
                tvSelectedDateInMonths.setText("$numberOfMonths month/s")
                tvSelectedDateInDays.setText("$numberofDays day/s")

            },
            year,
            month,
            day
        )

        dpd.datePicker.setMaxDate(Date().time - 86400000)
        dpd.show()
    }

    fun checkIfLeap (year: Int): Boolean
    {
        if (year % 400 == 0)
            return true;

        if (year % 100 == 0)
            return false;

        if (year % 4 == 0)
            return true;

        return false;
    }

    fun remainingDays (month: Int, year: Int): Int
    {
        if(month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12)
            return 31
        else if(month == 4 || month == 6 || month == 9 || month == 11)
            return 30
        else if(month == 2 && checkIfLeap(year)== true)
            return 29
        else if(month == 2 && checkIfLeap(year)==false)
            return 28
        else
            return 0;
    }
}