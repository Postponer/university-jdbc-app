CREATE TABLE IF NOT EXISTS courses
(
    course_id SERIAL,
    course_name character(255) COLLATE pg_catalog."default" NOT NULL,
    course_description character(255) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT courses_pkey PRIMARY KEY (course_id)
);
    
CREATE TABLE IF NOT EXISTS groups
(
    group_id SERIAL,
    group_name character(255) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT groups_pkey PRIMARY KEY (group_id)
);

CREATE TABLE IF NOT EXISTS students
(
    student_id SERIAL,
    group_id integer,
    first_name character(255) COLLATE pg_catalog."default" NOT NULL,
    last_name character(255) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT students_pkey PRIMARY KEY (student_id)
);

CREATE TABLE students_courses
( 
	student_id integer REFERENCES students(student_id) ON DELETE CASCADE, 
	course_id integer REFERENCES courses(course_id) ON DELETE CASCADE, 
	PRIMARY KEY(student_id, course_id) 
);