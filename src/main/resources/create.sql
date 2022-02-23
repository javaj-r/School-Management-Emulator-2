/*
DROP DATABASE IF EXISTS university;

CREATE DATABASE university
    WITH
    ENCODING = 'UTF8';

CREATE SCHEMA IF NOT EXISTS public;

SET SEARCH_PATH TO university, public;
*/

-- DROP TABLE IF EXISTS employee;

CREATE TABLE IF NOT EXISTS employee
(
    id           SERIAL PRIMARY KEY,
    username     VARCHAR(100) unique,
    password     VARCHAR(100),
    firstname    VARCHAR(100),
    lastname     VARCHAR(100),
    nationalCode BIGINT,
    salary       BIGINT
);

-- DROP TABLE IF EXISTS professor;

CREATE TABLE IF NOT EXISTS professor
(
    id            SERIAL PRIMARY KEY,
    username      VARCHAR(100) unique,
    password      VARCHAR(100),
    firstname     VARCHAR(100),
    lastname      VARCHAR(100),
    nationalCode  BIGINT,
    salary        BIGINT,
    facultyMember BOOLEAN
);


-- DROP TABLE IF EXISTS student;

CREATE TABLE IF NOT EXISTS student
(
    id           SERIAL PRIMARY KEY,
    username     VARCHAR(100) unique,
    password     VARCHAR(100),
    firstname    VARCHAR(100),
    lastname     VARCHAR(100),
    nationalCode BIGINT,
    studentCode  INTEGER,
    termNumber   INTEGER
);


-- DROP TABLE IF EXISTS course;

CREATE TABLE IF NOT EXISTS course
(
    id               SERIAL PRIMARY KEY,
    name             VARCHAR(100),
    unit             INTEGER,
    requiredCourseId INTEGER
);


-- DROP TABLE IF EXISTS student_course;

CREATE TABLE IF NOT EXISTS student_course
(
    id         SERIAL PRIMARY KEY,
    studentId  INTEGER,
    courseId   INTEGER,
    score      INTEGER,
    termNumber INTEGER
);


-- DROP TABLE IF EXISTS professor_course;

CREATE TABLE IF NOT EXISTS professor_course
(
    id          SERIAL PRIMARY KEY,
    professorId INTEGER,
    courseId    INTEGER,
    termNumber  INTEGER
);
