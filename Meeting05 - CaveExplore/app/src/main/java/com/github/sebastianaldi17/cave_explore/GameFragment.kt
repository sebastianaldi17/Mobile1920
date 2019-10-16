/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.sebastianaldi17.cave_explore

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.github.sebastianaldi17.cave_explore.databinding.FragmentGameBinding
import timber.log.Timber

class GameFragment : Fragment() {
    data class Question(
            val text: String,
            val answers: List<String>)

    // The first answer is the correct one.  We randomize the answers before showing the text.
    // All questions must have four answers.  We'd want these to contain references to string
    // resources so we could internationalize. (Or better yet, don't define the questions in code...)
    private val questions: MutableList<Question> = mutableListOf(
            Question(text = "The terrain is very rocky and uneven. What do you do?",
                    answers = listOf("Crawl", "Continue walking", "Run to even terrain")),
            Question(text = "Your oil lamp is running low. What do you do??",
                    answers = listOf("Use wood as a torch", "Continue exploring", "Try to exit before oil runs out")),
            Question(text = "You arrived at a crossroad. There are three paths.\n" +
                    "On the right, a dark room with strange noises.\n" +
                    "On the middle, a ledge that leads to quite a fall.\n" +
                    "On the left, a large body of water. The water looks ice cold. \n" +
                    "Which one do you take?",
                    answers = listOf("Left", "Right", "Middle")),
            Question(text = "You notice a chest in the middle of nowhere. What do you do?",
                    answers = listOf("Avoid the chest", "Open the chest", "Destroy the chest")),
            Question(text = "You are thirsty and notice a source of water. What do you do?",
                    answers = listOf("Heat the water up", "Drink immediately", "Filter the water")),
            Question(text = "You arrived at two roads. You notice a carving in the wall.\n" +
                    "\"Back is the coward's way out. Left shall be a quick journey, and right is a harsh journey.\"\n" +
                    "Which road do you take?",
                    answers = listOf("Right", "Left", "Back"))
            )

    lateinit var currentQuestion: Question
    lateinit var answers: MutableList<String>
    private var questionIndex = 0
    private var bearAttack = 0
    private val numQuestions = Math.min(questions.size, 4)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentGameBinding>(
                inflater, R.layout.fragment_game, container, false)

        // Shuffles the questions and sets the question index to the first question.
        randomizeQuestions()
        // Bind this fragment class to the layout
        binding.game = this

        // Set the onClickListener for the submitButton
        binding.submitButton.setOnClickListener @Suppress("UNUSED_ANONYMOUS_PARAMETER")
        { view: View ->
            val checkedId = binding.questionRadioGroup.checkedRadioButtonId
            // Do nothing if nothing is checked (id == -1)
            if (-1 != checkedId) {
                var answerIndex = 0
                when (checkedId) {
                    R.id.secondAnswerRadioButton -> answerIndex = 1
                    R.id.thirdAnswerRadioButton -> answerIndex = 2
                }
                if(questionIndex == 0) {
                    // The first one is different
                    if(answers[answerIndex] == currentQuestion.answers[0]) {
                        questionIndex++
                        currentQuestion = questions[questionIndex]
                        setQuestion()
                        binding.invalidateAll()
                    } else if(answers[answerIndex] == currentQuestion.answers[1]){
                        if(!MyApplication.normalEnding) {
                            Toast.makeText(view.context, "Ending get: Coward", Toast.LENGTH_SHORT).show()
                        }
                        MyApplication.normalEnding = true
                        view.findNavController().
                                navigate(R.id.action_gameFragment_to_gameNormalFragment)
                    } else {
                        // By doing nothing, a bear attacks.
                        if(bearAttack < 3) {
                            Toast.makeText(view.context, "An angry bear is approaching... Do something!", Toast.LENGTH_LONG).show()
                            bearAttack++
                        } else {
                            if(!MyApplication.badEnding) {
                                Toast.makeText(view.context, "Ending get: Death", Toast.LENGTH_SHORT).show()
                            }
                            MyApplication.badEnding = true
                            view.findNavController().
                                    navigate(R.id.action_gameFragment_to_gameOverFragment)
                        }
                    }

                } else {
                    // The first answer in the original question is always the correct one, so if our
                    // answer matches, we have the correct answer.
                    if (answers[answerIndex] == currentQuestion.answers[0]) {
                        questionIndex++
                        // Advance to the next question
                        if (questionIndex < numQuestions) {
                            currentQuestion = questions[questionIndex]
                            setQuestion()
                            binding.invalidateAll()
                        } else {
                            if(!MyApplication.goodEnding) {
                                Toast.makeText(view.context, "Ending get: Prosperous", Toast.LENGTH_SHORT).show()
                            }
                            MyApplication.goodEnding = true
                            view.findNavController().navigate(R.id.action_gameFragment_to_gameWonFragment)
                        }
                    } else {
                        if(!MyApplication.badEnding) {
                            Toast.makeText(view.context, "Ending get: Death", Toast.LENGTH_SHORT).show()
                        }
                        MyApplication.badEnding = true
                        view.findNavController().navigate(R.id.action_gameFragment_to_gameOverFragment)
                    }
                }
            }
        }
        return binding.root
    }

    // randomize the questions and set the first question
    private fun randomizeQuestions() {
        questions.shuffle()
        questionIndex = 0
        questions.add(0, Question(text = "You are standing in front of a cave. Legends say that pirates buried their treasure here. What do you do?",
                answers =listOf("Enter the cave", "Run away", "Do nothing")))
        setQuestion()
    }

    // Sets the question and randomizes the answers.  This only changes the data, not the UI.
    // Calling invalidateAll on the FragmentGameBinding updates the data.
    private fun setQuestion() {
        currentQuestion = questions[questionIndex]
        // randomize the answers into a copy of the array
        answers = currentQuestion.answers.toMutableList()
        // and shuffle them
        answers.shuffle()
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.app_name)
    }
    override fun onStart() {
        super.onStart()
        Timber.i("onStart called")
    }
    override fun onPause() {
        super.onPause()
        Timber.i("onPause called")
    }
    override fun onResume() {
        super.onResume()
        Timber.i("onResume called")
    }
    override fun onStop() {
        super.onStop()
        Timber.i("onStop called")
    }
    override fun onDestroy() {
        super.onDestroy()
        Timber.i("onDestroy called")
    }
    override fun onAttach(context: Context?) {
        super.onAttach(context)
        Timber.i("onAttach called")
    }
    override fun onDetach() {
        super.onDetach()
        Timber.i("onDetach called")
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Timber.i("onActivityCreated called")
    }
    override fun onDestroyView() {
        super.onDestroyView()
        Timber.i("onDestroyView called")
    }
}
