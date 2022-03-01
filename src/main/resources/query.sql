INSERT INTO course(name, unit, requiredcourseid)
VALUES (?, ?, ?);

INSERT INTO employee(username, password, firstname, lastname, nationalCode, salary)
SELECT ?, ?, ?, ?, ?, ?
WHERE NOT exists(SELECT 1 FROM employee WHERE username = ?);

INSERT INTO professor(username, password, firstname, lastname, nationalcode, salary, facultymember)
SELECT ?, ?, ?, ?, ?, ?, ?
WHERE NOT exists(SELECT 1 FROM professor WHERE username = ?);

INSERT INTO student(username, password, firstname, lastname, nationalcode, studentcode, termnumber)
SELECT ?, ?, ?, ?, ?, ?, ?
WHERE NOT exists(SELECT 1 FROM student WHERE username = ?);



UPDATE course
SET name             = ?,
    unit             = ?,
    requiredCourseId = ?
WHERE id = ?;

UPDATE employee
SET username     =?,
    password     =?,
    firstname    =?,
    lastname     =?,
    nationalCode =?,
    salary       =?
WHERE id = ?;

UPDATE professor
SET username      =?,
    password      =?,
    firstname     =?,
    lastname      =?,
    nationalCode  =?,
    salary        =?,
    facultyMember = ?
WHERE id = ?;

UPDATE student
SET username     =?,
    password     =?,
    firstname    =?,
    lastname     =?,
    nationalCode =?,
    studentCode  =?,
    termNumber   = ?
WHERE id = ?;


DELETE FROM course
WHERE id = ?;

DELETE FROM employee
WHERE id = ?;

DELETE FROM professor
WHERE id = ?;

DELETE FROM student
WHERE id = ?;

SELECT id, name, unit, requiredcourseid
FROM course
WHERE 1 = 1;

SELECT id, username, password, firstname, lastname, nationalCode, salary
FROM employee
WHERE 1 = 1;


SELECT id, username, password, firstname, lastname, nationalcode, salary, facultymember
FROM professor
WHERE 1 = 1;


SELECT id, username, password, firstname, lastname, nationalcode, studentcode, termnumber
FROM student
WHERE 1 = 1;


INSERT INTO student_course(studentid, courseid, termnumber)
VALUES (?, ?, ?);


INSERT INTO professor_course(professorid, courseid, termnumber)
VALUES (?, ?, ?);


SELECT s.termnumber, s.score, c.id, c.name, c.unit, c.requiredcourseid FROM student_course s
JOIN course c ON s.courseid = c.id
WHERE s.studentid = ?;


SELECT termnumber, c.id, c.name, c.unit, c.requiredcourseid FROM professor_course p
JOIN course c ON p.courseid = c.id
WHERE p.professorid = ?;


UPDATE student_course SET
score = ?
WHERE studentid = ?
AND courseid = ?
AND termnumber = ?;










