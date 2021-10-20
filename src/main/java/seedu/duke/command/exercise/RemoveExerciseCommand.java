package seedu.duke.command.exercise;

import seedu.duke.command.Command;
import seedu.duke.command.CommandResult;
import seedu.duke.exception.GetJackDException;
import seedu.duke.exercises.Exercise;
import seedu.duke.lists.WorkoutList;
import seedu.duke.storage.Storage;

import java.util.logging.Logger;

import static seedu.duke.logger.LoggerUtil.setupLogger;

/**
 * To remove an exercise from a Workout.
 */
public class RemoveExerciseCommand extends Command {
    public static final String COMMAND_WORD = "remove";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Removes the exercise from the workout.\n"
            + "Format: remove [Exercise index], [Workout index]\n"
            + "Parameters:\n"
            + "\tExercise index - Index of exercise to remove\n"
            + "\tWorkout index - Index of workout containing exercise to remove\n"
            + "Example: " + COMMAND_WORD + " 1, 2  - remove exercise 1 from workout 2";
    public static final String MESSAGE_SUCCESS = "Removed exercise: %1$s";
    private static final Logger LOGGER = Logger.getLogger(RemoveExerciseCommand.class.getName());

    private final int workoutIndex;
    private final int exerciseIndex;

    /**
     * Instantiates object and sets workoutIndex and exerciseIndex.
     *
     * @param workoutIndex  display index of Workout that the exercise is in
     * @param exerciseIndex display index of exercise to remove
     */
    public RemoveExerciseCommand(int workoutIndex, int exerciseIndex) {
        this.workoutIndex = workoutIndex;
        this.exerciseIndex = exerciseIndex;
        assert workoutIndex >= 0;
        assert exerciseIndex >= 0;
        setupLogger(LOGGER);
    }

    /**
     * Executes remove exercise command to remove an exercise of given index from the list.
     *
     * @param workouts is the list of Workouts
     * @param storage  is a storage object
     * @throws GetJackDException if there is an invalid index used or an error occurs within the storage
     */
    @Override
    public CommandResult executeUserCommand(WorkoutList workouts, Storage storage) throws GetJackDException {
        try {
            Exercise toRemove = workouts.getWorkout(workoutIndex).removeExercise(exerciseIndex);

            String jsonString = storage.convertToJson(workouts);
            storage.saveData(jsonString);

            return new CommandResult(String.format(MESSAGE_SUCCESS, toRemove));
        } catch (IndexOutOfBoundsException e) {
            LOGGER.info("remove exercise failed - exercise not found");
            throw new GetJackDException(ERROR_MESSAGE_EXERCISE_NOT_FOUND);
        }
    }
}
