package ua.ilyadreamix.m3amino.rv

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
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