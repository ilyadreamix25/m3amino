package ua.ilyadreamix.m3amino.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.recyclerview.widget.LinearLayoutManager
import ua.ilyadreamix.m3amino.R
import ua.ilyadreamix.m3amino.databinding.FragmentComsBinding
import ua.ilyadreamix.m3amino.http.model.CommunitiesResponseModel
import ua.ilyadreamix.m3amino.http.model.Community
import ua.ilyadreamix.m3amino.http.request.BaseResponse
import ua.ilyadreamix.m3amino.http.request.CommunityRequest
import ua.ilyadreamix.m3amino.http.utility.AminoSessionUtility
import ua.ilyadreamix.m3amino.rv.CommunityItemAdapter

class ComsFragment : Fragment() {

    private lateinit var binding: FragmentComsBinding
    private lateinit var adapter: CommunityItemAdapter
    private lateinit var communities: List<Community>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentComsBinding.inflate(inflater)

        val accountCommunitiesLiveData: LiveData<BaseResponse<CommunitiesResponseModel>> =
            liveData {
                val aminoSessionUtility = AminoSessionUtility(requireActivity())
                val aminoSession = aminoSessionUtility.getSessionData()

                val requester = CommunityRequest(
                    deviceId = aminoSession.deviceId!!,
                    acceptLanguage = getString(R.string.language),
                    ndcLang = getString(R.string.ndc_language),
                    ndcAuth = aminoSession.sessionId!!
                )
                val response = requester.getAccountCommunities()

                emit(response)
            }

        accountCommunitiesLiveData.observe(requireActivity()) {
            communities = it.data!!.communityList
            adapter = CommunityItemAdapter(communities)

            binding.comsRv.layoutManager = LinearLayoutManager(requireContext())
            binding.comsRv.adapter = adapter
        }

        return binding.root
    }
}