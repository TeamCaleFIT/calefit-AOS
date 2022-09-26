package com.example.calefit.ui.home.planner

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
import com.example.calefit.databinding.FragmentPlannerBinding
import com.example.calefit.ui.adapter.NestedOuterListViewAdapter
import com.example.calefit.ui.common.InputCategory
import com.example.calefit.ui.decoration.NestedRecyclerDecoration
import com.example.calefit.ui.common.NumberPickFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class PlannerFragment : Fragment() {

    private var binding by autoCleared<FragmentPlannerBinding>()

    private val viewModel: PlannerViewModel by viewModels()

    private val args: PlannerFragmentArgs by navArgs<PlannerFragmentArgs>()

    private val plannerAdapter by lazy {
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
        args.item?.let { viewModel.setExerciseList(it) }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_planner, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("PlannerFragment", "Planner onviewcreated ")
        Log.d("PlannerFragment", "args : ${args.item?.list?.size?.toString()}")

        val navController = findNavController()

        binding.rvExerciseListToday.apply {
            adapter = plannerAdapter
            itemAnimator = null
            addItemDecoration(nestedRecyclerItemDecoration)
        }

        selectExercise(navController)
        observeSelectionFragmentResult(navController)
        goToTemplateFragment(navController)
        getDataFromBottomSheetFragment()
        observeData()
    }

    private fun selectExercise(navController: NavController) {
        binding.btnExercisePlan.setOnClickListener {
            navController.navigate(R.id.action_plannerFragment_to_exerciseSelectFragment)
        }
    }

    private fun observeSelectionFragmentResult(navController: NavController) {
        viewLifecycleOwner.repeatOnLifecycleExtension {
            navController.currentBackStackEntry?.savedStateHandle?.getStateFlow<List<ExerciseSelection>>(
                "key", listOf()
            )?.onEach {
                viewModel.addAdditionalExercise(it)
            }?.onCompletion {
                Log.d("PlannerFragment", "done : for the flow")
            }?.collect()
        }
    }

    private fun observeData() {
        viewLifecycleOwner.repeatOnLifecycleExtension {
            viewModel.exercisePlan.collect { exerciseList ->
                plannerAdapter.submitList(exerciseList.list)
                binding.hasPlan = exerciseList.list.isNotEmpty()
            }
        }
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

    private fun goToTemplateFragment(navController: NavController) {
        binding.btnLoadExerciseTemplateList.setOnClickListener {
            navController.navigate(R.id.action_plannerFragment_to_templateFragment)
        }
    }

    //TODO saveExerciseData when the save button clicked and save it in the database when the revision is finished

    companion object {
        private const val DEFAULT_INNER_RECYCLER_VIEW_ITEM_PADDING = 50
    }
}
