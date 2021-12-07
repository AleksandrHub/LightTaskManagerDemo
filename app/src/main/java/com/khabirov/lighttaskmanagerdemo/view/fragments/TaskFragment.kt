package com.niww.lighttaskmanager.view.fragments

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.niww.lighttaskmanager.R
import com.niww.lighttaskmanager.model.datasource.Task
import com.niww.lighttaskmanager.view.adapters.TaskMaterialsPhotoRVAdapter
import kotlinx.android.synthetic.main.fragment_task_info.view.*
import kotlinx.android.synthetic.main.task_info_screen_layout.view.*
import java.io.FileNotFoundException
import java.io.InputStream

class TaskFragment : Fragment() {

    companion object {
        private const val TASK = "task"
        private const val Pick_image = 8907
    }

    private lateinit var photoList: MutableList<Bitmap>
    private lateinit var adapter: TaskMaterialsPhotoRVAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_task_info, container, false)
        init(view)
        initButtons(view)
        return view
    }

    private fun init(view: View) {

        val task = arguments?.getParcelable<Task>(TASK)

        when (task?.state) {
            0 -> {
                view.task_status_dot.setBackgroundResource(R.drawable.red_status_dot_8)
                view.task_status_text.text = "Задача ожидает принятия в работу"
            }
            1 -> {
                view.task_status_dot.setBackgroundResource(R.drawable.orange_status_dot_8)
                view.task_status_text.text = "Задача находится в работе"
            }
            2 -> {
                view.task_status_dot.setBackgroundResource(R.drawable.green_status_dot_8)
                view.task_status_text.text = "Задача завершена"
            }
        }

        view.task_status_id.text = "ID: ${task?.id}"
        view.task_theme_text.text = task?.name
        view.task_timing.text = task?.timing
        view.task_performer_text.text = task?.performer
        view.task_address_text.text = task?.address
        view.task_description_text.text = task?.description

        photoList = mutableListOf()
        adapter = TaskMaterialsPhotoRVAdapter()
        val recycler = view.photo_list_recyclerview
        recycler.adapter = adapter
    }

    private fun initButtons(view: View) {
        view.fragment_task_info_arrow_back_icon.setOnClickListener {
            findNavController().navigate(R.id.navigation_home)
        }

        view.add_materials.setOnClickListener {
            val photoPickerIntent = Intent(Intent.ACTION_PICK)
            //Тип получаемых объектов - image:
            //Тип получаемых объектов - image:
            photoPickerIntent.type = "image/*"
            //Запускаем переход с ожиданием обратного результата в виде информации об изображении:
            //Запускаем переход с ожиданием обратного результата в виде информации об изображении:
            startActivityForResult(photoPickerIntent, Pick_image)
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        println("-----onActivityResult()")
        when(requestCode) {
            Pick_image ->
                if (resultCode == AppCompatActivity.RESULT_OK) {
                    try {
                        println("-----onActivityResult = Ok")
                        //Получаем URI изображения, преобразуем его в Bitmap
                        //объект и отображаем в элементе ImageView нашего интерфейса:
                        val imageUri: Uri? = data?.getData();
                        val imageStream: InputStream? =
                            requireActivity().getContentResolver().openInputStream(imageUri!!);
                        val selectedImage: Bitmap = BitmapFactory.decodeStream(imageStream);
                        adapter.setData(selectedImage)
                    } catch (e: FileNotFoundException) {
                        e.printStackTrace();
                    }
                }
        }
    }

}