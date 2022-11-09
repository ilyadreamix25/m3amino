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
import ua.ilyadreamix.m3amino.databinding.FragmentCommunitiesBinding
import ua.ilyadreamix.m3amino.http.model.CommunitiesResponseModel
import ua.ilyadreamix.m3amino.http.model.Community
import ua.ilyadreamix.m3amino.http.request.BaseResponse
import ua.ilyadreamix.m3amino.http.request.CommunityRequest
import ua.ilyadreamix.m3amino.http.request.ResponseState
import ua.ilyadreamix.m3amino.http.utility.AminoSPUtility
import ua.ilyadreamix.m3amino.recycler.CommunityFakeItemAdapter
import ua.ilyadreamix.m3amino.recycler.CommunityItemAdapter
import kotlin.random.Random

class CommunitiesFragment : Fragment() {

    private lateinit var binding: FragmentCommunitiesBinding
    private lateinit var adapter: CommunityItemAdapter
    private lateinit var communities: List<Community>
    private lateinit var alerts: Alerts

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCommunitiesBinding.inflate(inflater)
        alerts = Alerts(requireActivity())

        setInsets()

        val lastLoginCache = AminoSPUtility.getLastLoginCache()
        val communitiesSize = lastLoginCache.communitiesSize

        if (communitiesSize == 0) insertFakeData()
        else insertFakeData(listOf(1..communitiesSize).flatten())

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

                if (communitiesSize == 0)
                    AminoSPUtility.saveLastLoginCache(
                        AminoSPUtility.AminoLastLoginCache(it.data.communityList.size)
                    )
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
            try {
                val requester = CommunityRequest(
                    acceptLanguage = getString(R.string.language),
                    ndcLang = getString(R.string.ndc_language)
                )
                val response = requester.getAccountCommunities()

                emit(response)
            } catch (_: Exception) {
                hideEverythingAndShowError()
            }
        }

    private fun getRandomFakeData() = listOf(1..Random.nextInt(15)).flatten()

    private fun setInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(
            binding.communitiesTopBarLayout
        ) { topBar, insets ->
            val systemInsets = insets.getInsets(WindowInsetsCompat.Type.systemGestures())
            topBar.updatePadding(top = systemInsets.top)
            WindowInsetsCompat.CONSUMED
        }
    }

    private fun insertFakeData(size: List<Int> = getRandomFakeData()) {
        binding.communitiesFakeRv.addItemPadding()
        binding.communitiesFakeRv.layoutManager = GridLayoutManager(requireContext(), 3)
        binding.communitiesFakeRv.adapter = CommunityFakeItemAdapter(size)
    }

    private fun hideEverythingAndShowError() {
        hideFakeRv {
            binding.communitiesLoadingError.apply {
                alpha = 0f
                visibility = View.VISIBLE

                animate()
                    .alpha(1f)
                    .setDuration(150)
                    .setListener(null)
            }
        }
    }

    private fun hideFakeRv(onEnd: () -> Unit = {}) {
        binding.communitiesFakeRv.animate()
            .alpha(0f)
            .setDuration(150)
            .setListener(object: AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    binding.communitiesFakeRv.visibility = View.GONE
                    onEnd()
                }
            })
    }

    private fun insertCommunities() {
        binding.communitiesRv.addItemPadding()
        binding.communitiesRv.layoutManager = GridLayoutManager(requireContext(), 3)
        binding.communitiesRv.adapter = adapter

        hideFakeRv {
            binding.communitiesRv.apply {
                alpha = 0f
                visibility = View.VISIBLE

                animate()
                    .alpha(1f)
                    .setDuration(150)
                    .setListener(null)
            }
        }
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