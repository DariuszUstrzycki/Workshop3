package pl.coderslab.dao;

import java.util.Collection;

import pl.coderslab.model.Solution;


public interface SolutionDao {

	int save(Solution ex);
	boolean update(Solution ex);
	Solution loadSolutionById(int id);
	Collection<Solution> loadSolutionsByExId(long exerciseId);
	Collection<Solution> loadAllSolutions();
	boolean delete(long id);
}
