package com.xstar.star.intent_test

import android.Manifest
import android.app.Dialog
import android.app.DialogFragment
import android.content.pm.PackageManager
import android.content.res.AssetFileDescriptor
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.support.annotation.RequiresApi
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.widget.*;
import android.view.*;
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.*;
import java.io.File
import java.util.ArrayList;



class MainActivity : AppCompatActivity() {
    internal lateinit var background_music: MediaPlayer
    internal var fileDescriptor: AssetFileDescriptor? = null
    internal var music = 1
    internal lateinit var Array_tree: Array<String?>
    internal lateinit var file: Array<File>
    internal lateinit var up_file: File

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.op_about -> {
                toast("此程序由星辰开发。\nQQ:1787074172");
                alert("此程序由星辰开发。\nQQ:1787074172") {
                        title("关于我")
                        neutralButton {  }
                }.show()
            }
            R.id.op_exit -> {
                toast("即将退出程序。")
                finish()
            }
        }
        return true
        //return super.onOptionsItemSelected(item);
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // background_music=new MediaPlayer();
        // fileDescriptor=getAssets().openFd("bc.mp3");
        // background_music.setDataSource(fileDescriptor);
        background_music = MediaPlayer.create(this, R.raw.bc)
        // background_music.prepare();
        background_music.isLooping = true
        background_music.start()
        toast("播放背景音乐成功啦~")
        //开始LISTVIEW

        var permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        // 声明一个集合，在后面的代码中用来存储用户拒绝授权的权
        val mPermissionList = ArrayList<String>()
        mPermissionList.clear()
        for (i in permissions.indices) {
            if (ContextCompat.checkSelfPermission(this, permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                mPermissionList.add(permissions[i])
            }
        }
        if (mPermissionList.isEmpty()) {//未授予的权限为空，表示都授予了
            toast("所需权限已被授权。")
            file = Environment.getExternalStorageDirectory().listFiles()
            up_file = File(Environment.getExternalStorageDirectory().parent)

            //String[] list_itm = new String[]{"Baidu","Google","3","4","5","6","7","8","9","10","11","12","13","14","15"};

            Array_tree = arrayOfNulls(file.size + 1)
            for (i in file.indices) {
                Array_tree[i + 1] = file[i].toString()
            }
            Array_tree[0] = "---->>返回上一级"
            val list_itm = Array_tree

            val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, list_itm)
            val listView = findViewById<View>(R.id.List_music) as ListView
            listView.adapter = adapter

            listView.onItemClickListener = AdapterView.OnItemClickListener { l, v, position, id ->
                val s = l.getItemAtPosition(position) as String
                when (s) {
                    "---->>返回上一级" -> Toast.makeText(this@MainActivity, "Get", Toast.LENGTH_SHORT).show()
                    else -> Toast.makeText(this@MainActivity, s, Toast.LENGTH_SHORT).show()
                }
                //Toast.makeText(MainActivity.this,s,Toast.LENGTH_SHORT).show();
            }
        } else {//请求权限方法
            toast("如所需文件读写权限未被授权，将无法正常执行程序。\n授权后可能需要手动重新打开程序才能正常读取数据。")
            permissions = mPermissionList.toTypedArray()//将List转为数组
            ActivityCompat.requestPermissions(this, permissions, 0)

        }
        findViewById<View>(R.id.onmusic).setOnClickListener {
            when (this.music) {
                1 -> {
                    this.music = 0
                    background_music.pause()
                    onmusic.text= "开始播放背景音乐"
                }
                0 -> {
                    this.music = 1
                    background_music.start()
                    onmusic.text = "停止播放背景音乐"
                }
            }

        }

    fun show_files(view: View) {

        //finish();

    }

    fun Start_Files_list(files: Array<Array<File>>) {}


}

    override fun onBackPressed() {
        toast("即将退出程序")
        if (this.music == 1) {
            background_music.stop()
        }
        finish()
        super.onBackPressed()
    }

    override fun onDestroy() {
        background_music.stop()
        super.onDestroy()
    }
}

