package ua.ilyadreamix.m3amino.rv

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ua.ilyadreamix.m3amino.R
import ua.ilyadreamix.m3amino.activity.LoginActivity
import ua.ilyadreamix.m3amino.databinding.ItemCommunityBinding
import ua.ilyadreamix.m3amino.http.model.Community

class CommunityItemAdapter(
    private val communities: List<Community>
): RecyclerView.Adapter<CommunityItemAdapter.CommunityItemViewHolder>() {

    private lateinit var binding: ItemCommunityBinding

    class CommunityItemViewHolder(
        private val communityItemView: ItemCommunityBinding
    ): RecyclerView.ViewHolder(communityItemView.root) {
        fun bind(item: Community) {
            communityItemView.communityName.text = item.name

            Glide.with(communityItemView.root.context)
                .asBitmap()
                .dontTransform()
                .error(R.drawable.ic_cross)
                .placeholder(R.drawable.ic_check)
                .load(item.icon!!.replace("http://", "https://"))
                .into(communityItemView.communityIcon)

            communityItemView.root.setOnClickListener {
                val context = communityItemView.root.context
                val intent = Intent(context, LoginActivity::class.java)
                context.startActivity(intent)
                (context as Activity).finish()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommunityItemViewHolder {
        binding = ItemCommunityBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CommunityItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CommunityItemViewHolder, position: Int) {
        holder.bind(communities[position])
    }

    override fun getItemCount(): Int {
        return communities.size
    }
}