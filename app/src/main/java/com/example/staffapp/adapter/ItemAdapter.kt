//package com.example.recyclerview.adapter
//
//import android.content.Intent
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.ImageView
//import android.widget.TextView
//import androidx.recyclerview.widget.RecyclerView
//import com.example.recyclerview.PlayVideoActivity
//import com.example.recyclerview.R
//import com.example.recyclerview.model.Fact
//import com.example.recyclerview.ui.home.HomeFragment
//
//class ItemAdapter(private val context: HomeFragment,
//                  private val dataset: List<Fact>):
//    RecyclerView.Adapter<ItemAdapter.ItemViewHolder>(){
//
//    class ItemViewHolder (private val view: View) :
//        RecyclerView.ViewHolder(view) {
//        val txtFact: TextView = view.findViewById(R.id.txtFact)
//        val imgFact: ImageView = view.findViewById(R.id.imgFact)
//
//        init {
//            // Set click listener on item view
//            view.setOnClickListener {
//                val context = itemView.context
//                val intent = Intent(context, PlayVideoActivity::class.java)
////                intent.putExtra("itemTitle", dataset)
//                context.startActivity(intent)
//            }
//        }
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
//        val adapterLayout =
//            LayoutInflater.from(parent.context).inflate(R.layout.list_view_fact,
//                parent, false)
//        return ItemViewHolder(adapterLayout)
//    }
//
//    override fun getItemCount() = dataset.size
//
//    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
//        val item = dataset[position]
//        holder.txtFact.text =
//            context.resources.getString(item.stringResourceId)
//        holder.imgFact.setImageResource(item.imageResourceId)
//        holder.itemView.setOnClickListener{
//            val context = holder.itemView.context
//            val intent = Intent(context, PlayVideoActivity::class.java)
//            intent.putExtra("itemTitle", context.resources.getString(item.stringResourceId))
//            context.startActivity(intent)
//        }
//    }
//}