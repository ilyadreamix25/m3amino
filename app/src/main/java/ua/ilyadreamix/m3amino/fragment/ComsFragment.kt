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
import androidx.transition.Fade
import ua.ilyadreamix.m3amino.R
import ua.ilyadreamix.m3amino.component.Alerts
import ua.ilyadreamix.m3amino.databinding.FragmentComsBinding
import ua.ilyadreamix.m3amino.http.model.CommunitiesResponseModel
import ua.ilyadreamix.m3amino.http.model.Community
import ua.ilyadreamix.m3amino.http.request.BaseResponse
import ua.ilyadreamix.m3amino.http.request.CommunityRequest
import ua.ilyadreamix.m3amino.http.request.ResponseState
import ua.ilyadreamix.m3amino.rv.CommunityFakeItemAdapter
import ua.ilyadreamix.m3amino.rv.CommunityItemAdapter
import kotlin.random.Random

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

        setInsets()
        insertFakeData()

        makeRequest().observe(requireActivity()) {
            if (it.state == ResponseState.BAD) {
                alerts.alertNoButtons(
                    getString(R.string.ad_request_error),
                    it.error!!.message
                )
            } else {
                communities = it.data!!.communityList
                adapter = CommunityItemAdapter(communities)

                insertCommunities()
            }
        }

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enterTransition = Fade(Fade.IN).setDuration(150)
    }

    private fun makeRequest(): LiveData<BaseResponse<CommunitiesResponseModel>> =
        liveData {
            val requester = CommunityRequest(
                acceptLanguage = getString(R.string.language),
                ndcLang = getString(R.string.ndc_language)
            )
            val response = requester.getAccountCommunities()

            emit(response)
        }

    private fun getRandomFakeData() = listOf(1..Random.nextInt(15)).flatten()

    private fun setInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.comsTopBarLayout) { topBar, insets ->
            val systemInsets = insets.getInsets(WindowInsetsCompat.Type.systemGestures())
            topBar.updatePadding(top = systemInsets.top)
            WindowInsetsCompat.CONSUMED
        }
    }

    private fun insertFakeData() {
        binding.comsFakeRv.addItemPadding()
        binding.comsFakeRv.layoutManager = GridLayoutManager(requireContext(), 3)
        binding.comsFakeRv.adapter = CommunityFakeItemAdapter(getRandomFakeData())
    }

    private fun insertCommunities() {

        binding.comsRv.addItemPadding()
        binding.comsRv.layoutManager = GridLayoutManager(requireContext(), 3)
        binding.comsRv.adapter = adapter

        binding.comsFakeRv.animate()
            .alpha(0f)
            .setDuration(150)
            .setListener(object: AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    binding.comsFakeRv.visibility = View.GONE

                    binding.comsRv.apply {
                        alpha = 0f
                        visibility = View.VISIBLE

                        animate()
                            .alpha(1f)
                            .setDuration(150)
                            .setListener(null)
                    }
                }
            })
    }

    private fun RecyclerView.addItemPadding() {
        this.addItemDecoration(object: RecyclerView.ItemDecoration() {
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
    }
}