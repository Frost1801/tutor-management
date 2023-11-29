-- Active: 1700402446726@@127.0.0.1@3306

CREATE TABLE users (
    id INTEGER PRIMARY KEY,
    first_name TEXT NOT NULL,
    last_name TEXT NOT NULL,
    email TEXT NOT NULL UNIQUE,
    password TEXT NOT NULL
);

-- Students table
CREATE TABLE students (
    id INTEGER PRIMARY KEY,
    year INTEGER NOT NULL,
    section TEXT NOT NULL,
    FOREIGN KEY (id) REFERENCES users(id)
);
-- Tutors table
CREATE TABLE tutors (
    id INTEGER PRIMARY KEY,
    subject TEXT NOT NULL,
    hours INTEGER NOT NULL,
    FOREIGN KEY (id) REFERENCES users(id)
);

-- Managers table
CREATE TABLE managers (
    id INTEGER PRIMARY KEY,
    otp INTEGER UNIQUE NOT NULL,
    FOREIGN KEY (id) REFERENCES users(id)
);

-- Candidates table
CREATE TABLE candidates (
    id INTEGER PRIMARY KEY,
    GPA REAL NOT NULL,
    FOREIGN KEY (id) REFERENCES users(id)
);

-- Lessons table
CREATE TABLE lessons (
    id INTEGER PRIMARY KEY,
    tutor_id INTEGER NOT NULL,
    subject TEXT NOT NULL,
    date_time DATETIME NOT NULL,
    max_students INTEGER NOT NULL,
    status TEXT NOT NULL,
    FOREIGN KEY (tutor_id) REFERENCES tutors(id)
);

-- Applications table
CREATE TABLE applications (
    id INTEGER PRIMARY KEY,
    candidate_id INTEGER NOT NULL,
    subject TEXT NOT NULL,
    result TEXT NOT NULL,
    FOREIGN KEY (candidate_id) REFERENCES candidates(id)
);


CREATE TABLE student_lessons (
    student_id INTEGER NOT NULL,
    lesson_id INTEGER NOT NULL,
    PRIMARY KEY (student_id, lesson_id),
    FOREIGN KEY (student_id) REFERENCES students(id),
    FOREIGN KEY (lesson_id) REFERENCES lessons(id)
);
