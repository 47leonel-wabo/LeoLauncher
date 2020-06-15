package com.aiwamob.leolauncher.ui

import android.content.Intent
import android.content.pm.ResolveInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.aiwamob.leolauncher.databinding.ActivityLeoLauncherBinding

//private const val TAG = "NeoLauncherApp"

class LeoLauncherActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLeoLauncherBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLeoLauncherBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupAdapter()
    }

    private fun setupAdapter() {
        val startupIntent = Intent(Intent.ACTION_MAIN).apply {
            addCategory(Intent.CATEGORY_LAUNCHER)
        }
        val activities = packageManager.queryIntentActivities(startupIntent, 0)

        activities.sortWith(Comparator { a, b ->
            String.CASE_INSENSITIVE_ORDER.compare(
                a.loadLabel(packageManager).toString(),
                b.loadLabel(packageManager).toString()
            )
        })
        binding.appRecyclerView.adapter = LeoActivityAdapter(activities)
        //Log.i(TAG, "Found ${activities.size} activities")
    }
}

class LeoActivityViewHolder(item: View) : RecyclerView.ViewHolder(item), View.OnClickListener {
    private val nameTxt = item as TextView
    private lateinit var resolveInfo: ResolveInfo
    init {
        nameTxt.setOnClickListener(this)
    }
    fun bindActivity(rInfo: ResolveInfo) {
        resolveInfo = rInfo
        val pckMgr = itemView.context.packageManager
        val appName = rInfo.loadLabel(pckMgr).toString()
        nameTxt.text = appName
    }

    override fun onClick(v: View?) {
        val activityInfo = resolveInfo.activityInfo
        val intent = Intent(Intent.ACTION_MAIN).apply {
            setClassName(activityInfo.applicationInfo.packageName, activityInfo.name)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        val context = v?.context
        context?.startActivity(intent)
    }
}

private class LeoActivityAdapter(val availableActivities: List<ResolveInfo>) :
    RecyclerView.Adapter<LeoActivityViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeoActivityViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(android.R.layout.simple_list_item_1, parent, false)
        return LeoActivityViewHolder(view)
    }

    override fun getItemCount(): Int = availableActivities.size

    override fun onBindViewHolder(holder: LeoActivityViewHolder, position: Int) {
        val resolveInfo = availableActivities[position]
        holder.bindActivity(resolveInfo)
    }
}