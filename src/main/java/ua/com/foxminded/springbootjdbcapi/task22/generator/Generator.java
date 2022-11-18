package ua.com.foxminded.springbootjdbcapi.task22.generator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import ua.com.foxminded.springbootjdbcapi.task22.models.Course;

public class Generator {

	private static final int NUMBER_OF_COURSES = 10;
	private static final int MAX_NUMBER_OF_REPEATS = 3;
	private static final List<String> FIRST_NAMES = new ArrayList<>(Arrays.asList("Noah", "Oliver", "Amelia", "Emma",
			"Liam", "Charlotte", "Mia", "Ava", "William", "Olivia", "Elijah", "James", "Sophia", "Benjamin", "Isabella",
			"Lucas", "Henry", "Evelyn", "Theodore", "Harper"));
	private static final List<String> LAST_NAMES = new ArrayList<>(Arrays.asList("Smith", "Johnson", "Williams",
			"Brown", "Jones", "Garcia", "Miller", "Davis", "Rodriguez", "Martinez", "Hernandez", "Lopez", "Gonzalez",
			"Wilson", "Anderson", "Thomas", "Taylor", "Moore", "Jackson", "Lee"));
	private static final Map<String, String> COURSE_NAMES_AND_DESCRIPTIONS = new HashMap<>();

	static {

		COURSE_NAMES_AND_DESCRIPTIONS.put("Math", "Math course");
		COURSE_NAMES_AND_DESCRIPTIONS.put("Biology", "Biology course");
		COURSE_NAMES_AND_DESCRIPTIONS.put("Physics", "Physics course");
		COURSE_NAMES_AND_DESCRIPTIONS.put("English", "English course");
		COURSE_NAMES_AND_DESCRIPTIONS.put("Music", "Music course");
		COURSE_NAMES_AND_DESCRIPTIONS.put("Art", "Art course");
		COURSE_NAMES_AND_DESCRIPTIONS.put("Geography", "Geography course");
		COURSE_NAMES_AND_DESCRIPTIONS.put("Literature", "Literature course");
		COURSE_NAMES_AND_DESCRIPTIONS.put("History", "History course");
		COURSE_NAMES_AND_DESCRIPTIONS.put("Sports", "Sports course");

	}

	private static final List<Course> COURSES = COURSE_NAMES_AND_DESCRIPTIONS.entrySet().stream()
			.map(entry -> new Course(0, entry.getKey(), entry.getValue())).collect(Collectors.toList());

	public static Map<Integer, ArrayList<Integer>> getRandomStudentCoursesMap(int studentQuantity) {

		Map<Integer, ArrayList<Integer>> studentsAndCourses = new LinkedHashMap<>();

		for (int studentId = 0; studentId < studentQuantity; studentId++) {

			Set<Integer> studentCourses = getRandomCoursesForStudent();

			studentsAndCourses.put((studentId + 1), new ArrayList<>());

			for (int j = 0; j < studentCourses.size(); j++) {

				studentsAndCourses.get(studentId + 1).add((int) studentCourses.toArray()[j]);

			}

		}

		return studentsAndCourses;

	}

	private static Set<Integer> getRandomCoursesForStudent() {

		Set<Integer> studentCourses = new LinkedHashSet<>();

		int randomRepeats = randomInRange(1, MAX_NUMBER_OF_REPEATS);

		while (studentCourses.size() < randomRepeats) {

			int randomCourse = randomInRange(1, NUMBER_OF_COURSES);

			studentCourses.add(randomCourse);

		}

		return studentCourses;

	}

	public static List<Course> getCourses() {

		return COURSES;

	}

	public static List<String> getRandomNames(int quantity) {

		int index = 0;

		List<String> names = new ArrayList<>();

		for (int i = 0; i < quantity; i++) {

			Collections.shuffle(FIRST_NAMES);

			names.add(FIRST_NAMES.get(index++));

			if (index == FIRST_NAMES.size()) {

				index = 0;

			}

		}

		Collections.shuffle(names);

		return names;

	}

	public static List<String> getRandomSurnames(int quantity) {

		int index = 0;

		List<String> surnames = new ArrayList<>();

		for (int i = 0; i < quantity; i++) {

			Collections.shuffle(LAST_NAMES);

			surnames.add(LAST_NAMES.get(index++));

			if (index == LAST_NAMES.size()) {

				index = 0;

			}

		}

		Collections.shuffle(surnames);

		return surnames;
	}

	public static int randomInRange(int min, int max) {

		return (int) Math.floor(Math.random() * (max - min + 1) + min);

	}

}