package ua.ilyadreamix.m3amino.fragment

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.graphics.Rect
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.transition.MaterialFade
import ua.ilyadreamix.m3amino.R
import ua.ilyadreamix.m3amino.component.Alerts
import ua.ilyadreamix.m3amino.databinding.FragmentComsBinding
import ua.ilyadreamix.m3amino.http.model.CommunitiesResponseModel
import ua.ilyadreamix.m3amino.http.model.Community
import ua.ilyadreamix.m3amino.http.request.BaseResponse
import ua.ilyadreamix.m3amino.http.request.CommunityRequest
import ua.ilyadreamix.m3amino.http.request.ResponseState
import ua.ilyadreamix.m3amino.rv.CommunityItemAdapter

class ComsFragment : Fragment() {

    private lateinit var binding: FragmentComsBinding
    private lateinit var adapter: CommunityItemAdapter
    private lateinit var communities: List<Community>
    private lateinit var alerts: Alerts

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentComsBinding.inflate(inflater)
        alerts = Alerts(requireActivity())

        val accountCommunitiesLiveData: LiveData<BaseResponse<CommunitiesResponseModel>> =
            liveData {
                val requester = CommunityRequest(
                    acceptLanguage = getString(R.string.language),
                    ndcLang = getString(R.string.ndc_language)
                )
                val response = requester.getAccountCommunities()

                emit(response)
            }

        accountCommunitiesLiveData.observe(requireActivity()) {
            if (it.state == ResponseState.BAD) {
                alerts.alertNoButtons(
                    getString(R.string.ad_request_error),
                    it.error!!.message
                )
            } else {
                communities = it.data!!.communityList
                adapter = CommunityItemAdapter(communities)

                binding.comsRv.addItemDecoration(object: RecyclerView.ItemDecoration() {
                    override fun getItemOffsets(
                        outRect: Rect,
                        view: View,
                        parent: RecyclerView,
                        state: RecyclerView.State
                    ) {
                        super.getItemOffsets(outRect, view, parent, state)

                        val spacing = resources.getDimensionPixelSize(R.dimen.card_grid_two_items_spacing) / 2
                        outRect.set(spacing, spacing, spacing, spacing)
                    }
                })
                binding.comsRv.layoutManager = GridLayoutManager(requireContext(), 3)
                binding.comsRv.adapter = adapter

                binding.comsCpi.animate()
                    .alpha(0f)
                    .setDuration(300)
                    .setListener(object: AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator) {
                            binding.comsCpi.visibility = View.GONE

                            binding.comsContent.apply {
                                alpha = 0f
                                visibility = View.VISIBLE

                                animate()
                                    .alpha(1f)
                                    .setDuration(300)
                                    .setListener(null)
                            }
                        }
                    })
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(binding.comsTopBarLayout) { topBarLayoutView, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemGestures())
            topBarLayoutView.updatePadding(top = insets.top)
            WindowInsetsCompat.CONSUMED
        }

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enterTransition = MaterialFade()
    }
}