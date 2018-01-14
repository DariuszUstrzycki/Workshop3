SELECT 
    exercise.id,
    exercise.title,
    attachment.id,
    attachment.attachment_name,
    solution.id,
    solution.created,
    solution.updated,
    solution.description,
    solution.exercise_id,
    solution.user_id,
    user.username
FROM
    solution
LEFT JOIN  attachment ON attachment.solution_id = solution.id  
LEFT JOIN user ON user.id =  solution.user_id
INNER JOIN exercise ON solution.exercise_id = exercise.id
WHERE solution.exercise_id = 3 
       
ORDER BY solution.created DESC; 