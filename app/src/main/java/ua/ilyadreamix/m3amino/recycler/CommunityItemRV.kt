package ua.ilyadreamix.m3amino.recycler

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.color.MaterialColors
import com.google.android.material.R as AR
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

            val context = communityItemView.root.context
            val root = communityItemView.root

            val loadingIcon = AppCompatResources.getDrawable(context, R.drawable.pad_ic_bad_image)
            loadingIcon!!.setTint(MaterialColors.getColor(root, AR.attr.colorOnSurfaceVariant))

            Glide.with(root).asBitmap()
                .load(item.icon!!.replace("http://", "https://"))
                .dontTransform()
                .error(loadingIcon)
                .placeholder(loadingIcon)
                .into(communityItemView.communityIcon)

            communityItemView.root.setOnClickListener {
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