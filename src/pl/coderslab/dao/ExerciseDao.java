package pl.coderslab.dao;

import java.util.Collection;
import java.util.List;

import pl.coderslab.model.Exercise;
import pl.coderslab.model.Solution;

public interface ExerciseDao {
	
	int save(Exercise ex);
	boolean update(Exercise ex);
	Exercise loadExerciseById(int id);
	Collection<Exercise> loadAllExercises();
	boolean delete(long id);
	Collection<Exercise> loadExercisesByUserId(long i);
	Collection<Exercise> loadAllExercises(int limit);

}
