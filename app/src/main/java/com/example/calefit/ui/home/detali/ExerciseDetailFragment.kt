package com.example.calefit.ui.home.detali

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.calefit.R
import com.example.calefit.common.autoCleared
import com.example.calefit.common.repeatOnLifecycleExtension
import com.example.calefit.data.ExerciseSelection
import com.example.calefit.databinding.FragmentExerciseDetailBinding
import com.example.calefit.ui.adapter.NestedOuterListViewAdapter
import com.example.calefit.ui.common.InputCategory
import com.example.calefit.ui.decoration.NestedRecyclerDecoration
import com.example.calefit.ui.common.NumberPickFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class ExerciseDetailFragment : Fragment() {

    private var binding by autoCleared<FragmentExerciseDetailBinding>()

    private val viewModel: ExerciseDetailViewModel by viewModels()

    private val args: ExerciseDetailFragmentArgs by navArgs<ExerciseDetailFragmentArgs>()

    private val detailAdapter by lazy {
        NestedOuterListViewAdapter(
            { position -> viewModel.addAdditionalCycle(position) },
            { position -> viewModel.removeCycle(position) },
            { position -> viewModel.removeExercise(position) },
            { userClickPosition -> viewModel.setCurrentAdapterPositions(userClickPosition) },
            { category -> this.startBottomSheetFragment(category) }
        )
    }

    private val nestedRecyclerItemDecoration by lazy {
        NestedRecyclerDecoration(DEFAULT_INNER_RECYCLER_VIEW_ITEM_PADDING)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        args.templateDate.let { viewModel.setExerciseByDate(it) }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_exercise_detail, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController = findNavController()

        binding.rvTemplateExerciseList.apply {
            adapter = detailAdapter
            itemAnimator = null
            addItemDecoration(nestedRecyclerItemDecoration)
        }

        observeData()
        goToExerciseSelectFragment(navController)
        observeSelectionFragmentResult(navController)
        getDataFromBottomSheetFragment()
    }

    private fun startBottomSheetFragment(category: InputCategory) {
        val fragment = NumberPickFragment.newInstance(category)
        fragment.show(parentFragmentManager, fragment.tag)
    }

    private fun getDataFromBottomSheetFragment() {
        setFragmentResultListener("request_key") { _, bundle ->
            val selectedNumber = bundle.getInt("number")
            viewModel.setUserSelectedNumber(selectedNumber)
        }
    }

    private fun observeData() {
        viewLifecycleOwner.repeatOnLifecycleExtension {
            viewModel.exerciseList.collect { exerciseList ->
                detailAdapter.submitList(exerciseList.list)
                binding.templateTitle = exerciseList.templateName
            }
        }
    }

    private fun goToExerciseSelectFragment(navController: NavController) {
        binding.btnExerciseAdd.setOnClickListener {
            navController.navigate(R.id.action_exerciseDetailFragment_to_exerciseSelectFragment)
        }
    }

    private fun observeSelectionFragmentResult(navController: NavController) {
        viewLifecycleOwner.repeatOnLifecycleExtension {
            navController.currentBackStackEntry?.savedStateHandle?.getStateFlow<List<ExerciseSelection>>(
                "key", listOf()
            )?.onEach {
                viewModel.addAdditionalExercise(it)
            }?.onCompletion {
                Log.d("DetailFragment", "done : for the flow")
            }?.collect()
        }
    }

    companion object {
        private const val DEFAULT_INNER_RECYCLER_VIEW_ITEM_PADDING = 50
    }
}
