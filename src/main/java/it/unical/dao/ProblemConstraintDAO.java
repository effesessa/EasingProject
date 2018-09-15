package it.unical.dao;

import it.unical.entities.ProblemConstraint;

public interface ProblemConstraintDAO
{
	void create(ProblemConstraint problemConstraint);

	void delete(ProblemConstraint problemConstraint);

	void deleteByContest(Integer idContest);

	void update(ProblemConstraint problemConstraint);
}
