package ua.ilyadreamix.m3amino.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ua.ilyadreamix.m3amino.databinding.ItemFakeCommunityBinding

class CommunityFakeItemAdapter(
    private val communities: List<Int>
): RecyclerView.Adapter<CommunityFakeItemAdapter.CommunityFakeItemViewHolder>() {

    private lateinit var binding: ItemFakeCommunityBinding

    class CommunityFakeItemViewHolder(
        private val communityItemView: ItemFakeCommunityBinding
    ): RecyclerView.ViewHolder(communityItemView.root) {
        fun bind() {
            communityItemView.root.startShimmer()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommunityFakeItemViewHolder {
        binding = ItemFakeCommunityBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CommunityFakeItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CommunityFakeItemViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int {
        return communities.size
    }
}