import java.util.HashSet;
import java.util.Set;
public class ExerciseBuddy extends DreamGeek {
    // fields used as per the question requirements
    private final Set<String> favouriteExercises;
    private final int exerciseTimesPerWeek;
    /**
     * Constructor for ExerciseBuddy and its parameters.
     *
     * @param minAge               the minimum age a user desires
     * @param maxAge               the maximum age a user desires
     * @param gender               an enum value representing the Geek’s gender
     * @param starSign             an enum representing the geek’s star sign
     * @param favouriteExercises   a Set of Strings representing the geek’s favourite exercises
     * @param exerciseTimesPerWeek the number of times the geek exercises per week
     */
    public ExerciseBuddy(int minAge, int maxAge, Gender gender, StarSign starSign, Set<String> favouriteExercises, int exerciseTimesPerWeek) {
        super(minAge, maxAge, gender, starSign); // get the values from super class DreamGeek
        this.favouriteExercises = new HashSet<>(favouriteExercises);
        this.exerciseTimesPerWeek = exerciseTimesPerWeek;
    }
    /**
     * @return the Set of favourite exercises of the geek
     */
    public Set<String> getFavouriteExercises() {
        return new HashSet<>(favouriteExercises);
    }
    /**
     * @return the number of times the geek exercises per week
     */
    public int getExerciseTimesPerWeek() {
        return exerciseTimesPerWeek;
    }
    /**
     * @return a String description of the ExerciseBuddy
     */
    @Override
    public String getDescription() {
        return super.getDescription() + ". Their favourite exercises are " + getFavouriteExercises() + ". They
        exercise " + getExerciseTimesPerWeek() + " times per week. ";
    }
    /**
     * Checks if a given DreamGeek matches this ExerciseBuddy’s criteria.
     * @param realGeek a DreamGeek representing a real, registered user
     * @return true if the DreamGeek matches this ExerciseBuddy’s criteria
     */
    @Override
    public boolean matches(DreamGeek realGeek) {
        if (!(realGeek instanceof ExerciseBuddy realExerciseBuddy)) {
            return false;
        }
        if (!super.matches(realGeek)) {
            return false;
        }
        Set<String> commonExercises = new HashSet<>(this.favouriteExercises);
        commonExercises.retainAll(realExerciseBuddy.favouriteExercises);
        if (commonExercises.isEmpty()) {
            return false;
        }
        int exerciseDifference = Math.abs(this.exerciseTimesPerWeek - realExerciseBuddy.exerciseTimesPerWeek);
        return exerciseDifference <= 1;
    }
}