package pl.coderslab.dao;

import java.util.Collection;

import pl.coderslab.model.Exercise;

public interface ExerciseDao {
	
	int save(Exercise ex);
	boolean update(Exercise ex);
	Exercise loadExerciseById(int id);
	Collection<Exercise> loadAllExercises();
	boolean delete(long id);

}
