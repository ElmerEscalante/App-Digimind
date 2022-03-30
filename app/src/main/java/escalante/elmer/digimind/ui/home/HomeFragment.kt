package escalante.elmer.digimind.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import escalante.elmer.digimind.AdaptadorTareas
import escalante.elmer.digimind.R
import escalante.elmer.digimind.ui.Task
import kotlinx.android.synthetic.main.fragment_home.view.*

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    companion object{
        var tasks: ArrayList<Task> = ArrayList<Task>()
        var first = true
        lateinit var adaptador: AdaptadorTareas
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        val gridView: GridView = root.gridview

        if(first){
            fill_tasks()
            first = false
        }

        cargar_tareas()


        adaptador = AdaptadorTareas(root.context, tasks)

        gridView.adapter = adaptador

        return root
    }

    fun fill_tasks(){
        tasks.add(Task("Tarea 1", "Lunes", "15:00"))
        tasks.add(Task("Tarea 2", "Lunes", "15:00"))
        tasks.add(Task("Tarea 3", "Lunes", "15:00"))
        tasks.add(Task("Tarea 4", "Lunes", "15:00"))
        tasks.add(Task("Tarea 5", "Lunes", "15:00"))
    }

    fun cargar_tareas(){
        val preferencias = context?.getSharedPreferences("preferencias", Context.MODE_PRIVATE)
        val gson: Gson = Gson()

        var json = preferencias.getString("tareas", null)

        val type = object : TypeToken<ArrayList<Task?>?>(){}.type

        if(json==null){
            tasks = ArrayList<Task>()
        }else{
            tasks = gson.fromJson(json, type)
        }
    }
}