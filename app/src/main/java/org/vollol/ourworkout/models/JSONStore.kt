package org.vollol.ourworkout.models

import android.content.Context
import android.net.Uri
import com.google.gson.*
import com.google.gson.reflect.TypeToken
import org.vollol.ourworkout.helpers.exists
import org.vollol.ourworkout.helpers.*
import timber.log.Timber
import java.lang.reflect.Type
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.ArrayList

const val JSON_EXERCISE_FILE = "exercises.json"
const val JSON_WORKOUT_FILE = "workouts.json"
const val JSON_WORKOUTDONE_FILE = "workoutsdone.json"

val gsonBuilder: Gson = GsonBuilder().setPrettyPrinting()
    .registerTypeAdapter(Uri::class.java, UriParser())
    .registerTypeAdapter(LocalDateTime::class.java, LocalDateTimeConverter())
    .create()
val listExerciseType: Type = object : TypeToken<ArrayList<Exercise>>() {}.type
val listWorkoutType: Type = object : TypeToken<ArrayList<Workout>>() {}.type

fun generateRandomId(): Long{
    return Random().nextLong()
}

class ExerciseJSONStore(private val context: Context) :ExerciseStore{
    var exercises = mutableListOf<Exercise>()

    init {
        if(exists(context, JSON_EXERCISE_FILE)) {
            deserialize()
        }
    }

    override fun findAll(): MutableList<Exercise> {
        logAll()
        return exercises
    }

    override fun create(exercise: Exercise){
        exercise.id = generateRandomId()
        exercises.add(exercise)
        serialize()
    }

    override fun update(exercise: Exercise){
        val exercisesList = findAll() as ArrayList<Exercise>
        var foundExercise: Exercise? = exercisesList.find {p -> p.id == exercise.id}

        if (foundExercise != null) {
            foundExercise.title = exercise.title
            foundExercise.name = exercise.name
            foundExercise.desc = exercise.desc
            foundExercise.isEndurance = exercise.isEndurance
            foundExercise.unit = exercise.unit
            foundExercise.calories = exercise.calories
            foundExercise.weight = exercise.weight
            foundExercise.repsPerRound = exercise.repsPerRound
            foundExercise.rounds = exercise.rounds
            foundExercise.round = exercise.round
            foundExercise.roundDuration = exercise.roundDuration
            foundExercise.onTime = exercise.onTime
            foundExercise.offTime = exercise.offTime
        }
        serialize()
    }

    override fun delete(exercise: Exercise) {
        exercises.remove(exercise)
        serialize()
    }

    private fun serialize(){
        val jsonString = gsonBuilder.toJson(exercises, listExerciseType)
        write(context, JSON_EXERCISE_FILE, jsonString)
    }

    private fun deserialize(){
        val jsonString = read(context, JSON_EXERCISE_FILE)
        exercises = gsonBuilder.fromJson(jsonString, listExerciseType)
    }

    private fun logAll(){
        exercises.forEach{ Timber.i("$it")}
    }
}


class WorkoutJSONStore(private val context: Context) :WorkoutStore{
    private var workouts = mutableListOf<Workout>()
    private var file = JSON_WORKOUT_FILE

    init {
        if(exists(context, file)) {
            deserialize()
        }
    }

    override fun findAll(): MutableList<Workout> {
        logAll()
        return workouts
    }

    override fun create(workout: Workout){
        workout.blueprintId = generateRandomId()
        workouts.add(workout)
        serialize()
    }

    override fun update(workout: Workout){
        val workoutsList = findAll() as ArrayList<Workout>
        var foundWorkout: Workout? = null

        foundWorkout = workoutsList.find { p -> p.blueprintId == workout.blueprintId }

        if (foundWorkout != null) {
            foundWorkout.title = workout.title
            foundWorkout.strengthExercises = workout.strengthExercises
            foundWorkout.strengthDuration = workout.strengthDuration
            foundWorkout.enduranceExercises = workout.enduranceExercises
            foundWorkout.enduranceDuration = workout.enduranceDuration
            foundWorkout.enduranceRounds = workout.enduranceRounds
        }
        serialize()
    }

    override fun delete(workout: Workout) {
        workouts.remove(workout)
        serialize()
    }

    private fun serialize(){
        val jsonString = gsonBuilder.toJson(workouts, listWorkoutType)
        write(context, file, jsonString)
    }

    private fun deserialize(){
        val jsonString = read(context, file)
        workouts = gsonBuilder.fromJson(jsonString, listWorkoutType)
    }

    private fun logAll(){
        workouts.forEach{ Timber.i("$it")}
    }
}


class DoAbleWorkoutJSONStore(private val context: Context) :DoAbleWorkoutStore{
    private var workouts = mutableListOf<DoAbleWorkout>()
    private var file = JSON_WORKOUTDONE_FILE

    init {
        if(exists(context, file)) {
            deserialize()
        }
    }

    override fun findAll(): MutableList<DoAbleWorkout> {
        logAll()
        return workouts
    }

    override fun create(workout: DoAbleWorkout){
        workout.workoutDoneId = generateRandomId()
        workouts.add(workout)
        serialize()
    }

    override fun update(workout: DoAbleWorkout){
        val workoutsList = findAll() as ArrayList<DoAbleWorkout>
        var foundWorkout: DoAbleWorkout? = null

        foundWorkout = workoutsList.find { p -> p.workoutDoneId == workout.workoutDoneId }

        if (foundWorkout != null) {
            foundWorkout.title = workout.title
            foundWorkout.timeStamp = workout.timeStamp
            foundWorkout.exercises = workout.exercises
            foundWorkout.strengthDuration = workout.strengthDuration
            foundWorkout.enduranceDuration = workout.enduranceDuration
            foundWorkout.enduranceRounds = workout.enduranceRounds
        }
        serialize()
    }

    override fun delete(workout: DoAbleWorkout) {
        workouts.remove(workout)
        serialize()
    }

    private fun serialize(){
        val jsonString = gsonBuilder.toJson(workouts, listWorkoutType)
        write(context, file, jsonString)
    }

    private fun deserialize(){
        val jsonString = read(context, file)
        workouts = gsonBuilder.fromJson(jsonString, listWorkoutType)
    }

    private fun logAll(){
        workouts.forEach{ Timber.i("$it")}
    }
}

class UriParser: JsonDeserializer<Uri>, JsonSerializer<Uri>{
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Uri {
        return Uri.parse(json?.asString)
    }

    override fun serialize(
        src: Uri?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        return JsonPrimitive(src.toString())
    }
}

class LocalDateTimeConverter : JsonSerializer<LocalDateTime>, JsonDeserializer<LocalDateTime> {
    override fun serialize(src: LocalDateTime?, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement {
        return JsonPrimitive(src?.toString())
    }

    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): LocalDateTime {
        return LocalDateTime.parse(json?.asString)
    }
}