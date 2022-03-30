package escalante.elmer.digimind.ui.dashboard

import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.gson.Gson
import escalante.elmer.digimind.R
import escalante.elmer.digimind.ui.Task
import escalante.elmer.digimind.ui.home.HomeFragment
import kotlinx.android.synthetic.main.fragment_dashboard.*
import kotlinx.android.synthetic.main.fragment_dashboard.view.*
import java.text.SimpleDateFormat
import java.util.*

class DashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
            ViewModelProviders.of(this).get(DashboardViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)

        root.btn_time.setOnClickListener {
            set_time()
        }

        return root
    }

    fun guardar(){
        var titulo: String = et_task.text.toString()
        var tiempo: String = btn_time.text.toString()

        var dia: String = ""

        if (rb_day1.isChecked) dia = getString(R.string.day1)
        if (rb_day2.isChecked) dia = getString(R.string.day2)
        if (rb_day3.isChecked) dia = getString(R.string.day3)
        if (rb_day4.isChecked) dia = getString(R.string.day4)
        if (rb_day5.isChecked) dia = getString(R.string.day5)
        if (rb_day6.isChecked) dia = getString(R.string.day6)
        if (rb_day7.isChecked) dia = getString(R.string.day7)

        var tarea = Task(titulo, dia, tiempo)

        HomeFragment.tasks.add(tarea)
        Toast.makeText(context, "Se agrego la tarea", Toast.LENGTH_SHORT).show()

        guardar_json()
    }

    fun guardar_json(){
        val preferencias = context?.getSharedPreferences("preferencias", Context.MODE_PRIVATE)
        val editor = preferencias?.edit()

        val gson: Gson = Gson()

        var json = gson.toJson(HomeFragment.tasks)

        editor?.putString("tareas", json)
        editor?.apply()



    }

    fun set_time(){
        val cal = Calendar.getInstance()
        val timeSetListener = TimePickerDialog.OnTimeSetListener{ timePicker, hour, minute ->
            cal.set(Calendar.HOUR_OF_DAY, hour)
            cal.set(Calendar.MINUTE, minute)

            root.btnTime.text = SimpleDateFormat("HH:mm").format(cal.time)

        }
        TimePickerDialog(context, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
    }
}