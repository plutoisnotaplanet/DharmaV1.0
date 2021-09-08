package com.example.dharmav10.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dharmav10.R
import com.example.dharmav10.adapters.ArticleAdapter
import com.example.dharmav10.databinding.FragmentHomeBinding
import com.example.dharmav10.domain.NewsProperty
import com.prof.rssparser.Article
import com.prof.rssparser.Channel
import com.prof.rssparser.Parser
import timber.log.Timber


class HomeFragment : Fragment() {

    private lateinit var parser: Parser
    private lateinit var binding: FragmentHomeBinding



    //Initialize ViewModel
    private val viewModel: HomeViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        ViewModelProviders.of(this, HomeViewModel.Factory(activity.application))
            .get(HomeViewModel::class.java)
    }


    private var viewModelAdapter: ArticleAdapter? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.listNewsAll.observe(viewLifecycleOwner, Observer<List<NewsProperty>> {
            it?.apply {
                viewModelAdapter?.news = it
            }
        })

        //category button
        binding.ctgrKalmyk.setOnClickListener{
            switchContent("kalmyk")
            setNonActiveButton()
            binding.ctgrKalmyk.setBackgroundResource(R.drawable.active)
        }

        binding.ctgrRussia.setOnClickListener{
            switchContent("russia")
            setNonActiveButton()
            binding.ctgrRussia.setBackgroundResource(R.drawable.active)
        }

        binding.ctgrDharma.setOnClickListener{
            switchContent("dharma")
            setNonActiveButton()
            binding.ctgrDharma.setBackgroundResource(R.drawable.active)
        }

        binding.ctgrEconomy.setOnClickListener{
            switchContent("economy")
            setNonActiveButton()
            binding.ctgrEconomy.setBackgroundResource(R.drawable.active)
        }

        binding.ctgrEntertainment.setOnClickListener{
            switchContent("entertainment")
            setNonActiveButton()
            binding.ctgrEntertainment.setBackgroundResource(R.drawable.active)
        }

        binding.ctgrWorld.setOnClickListener{
            switchContent("all")
            setNonActiveButton()
            binding.ctgrWorld.setBackgroundResource(R.drawable.active)
        }

        binding.ctgrSport.setOnClickListener{
            switchContent("sport")
            setNonActiveButton()
            binding.ctgrSport.setBackgroundResource(R.drawable.active)
        }


    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_home,
            container,
            false
        )
        binding.setLifecycleOwner ( viewLifecycleOwner )
        binding.viewModel = viewModel

        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()

        viewModel.navigateToDetailNews.observe(viewLifecycleOwner, Observer {
            if (null != it) {
                this.findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToDetailFragment(it)
                )
                viewModel.onFinishItemSelected()
            }
        })

        viewModelAdapter = ArticleAdapter(ArticleAdapter.NewsClickListener { article ->
            viewModel.onItemSelected(article)
        })

        binding.rvRegular.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = viewModelAdapter
        }

       return binding.root
    }

    fun setNonActiveButton() {
        binding.ctgrKalmyk.setBackgroundResource(R.drawable.no_active)
        binding.ctgrRussia.setBackgroundResource(R.drawable.no_active)
        binding.ctgrSport.setBackgroundResource(R.drawable.no_active)
        binding.ctgrDharma.setBackgroundResource(R.drawable.no_active)
        binding.ctgrWorld.setBackgroundResource(R.drawable.no_active)
        binding.ctgrEntertainment.setBackgroundResource(R.drawable.no_active)
        binding.ctgrEconomy.setBackgroundResource(R.drawable.no_active)
    }

    fun switchContent(category: String) {
        when (category) {
            "all" -> viewModel.listNewsAll.observe(viewLifecycleOwner, Observer<List<NewsProperty>> {
                it?.apply {
                    viewModelAdapter?.news = it
                }
            })
            "kalmyk" -> viewModel.listNewsKalmyk.observe(viewLifecycleOwner, Observer<List<NewsProperty>> {
                it?.apply {
                    viewModelAdapter?.news = it
                }
            })

            "entertainment" -> viewModel.listNewsEntertainment.observe(viewLifecycleOwner, Observer<List<NewsProperty>> {
                it?.apply {
                    viewModelAdapter?.news = it
                }
            })

            "russia" -> viewModel.listNewsRussia.observe(viewLifecycleOwner, Observer<List<NewsProperty>> {
                it?.apply {
                    viewModelAdapter?.news = it
                }
            })

            "world" -> viewModel.listNewsWorld.observe(viewLifecycleOwner, Observer<List<NewsProperty>> {
                it?.apply {
                    viewModelAdapter?.news = it
                }
            })

            "sport" -> viewModel.listNewsSports.observe(viewLifecycleOwner, Observer<List<NewsProperty>> {
                it?.apply {
                    viewModelAdapter?.news = it
                }
            })

            "economy" -> viewModel.listNewsEconomy.observe(viewLifecycleOwner, Observer<List<NewsProperty>> {
                it?.apply {
                    viewModelAdapter?.news = it
                }
            })

            "dharma" -> viewModel.listNewsDharma.observe(viewLifecycleOwner, Observer<List<NewsProperty>> {
                it?.apply {
                    viewModelAdapter?.news = it
                }
            })
        }
    }
}