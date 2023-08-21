package bgu.spl.net;


import bgu.spl.net.impl.Course;
import bgu.spl.net.impl.User;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

/**
 * Passive object representing the Database where all courses and users are stored.
 * <p>
 * This class must be implemented safely as a thread-safe singleton.
 * You must not alter any of the given public methods of this class.
 * <p>
 * You can add private fields and methods to this class as you see fit.
 */
public class Database {
	private HashMap<String, User> users;
	private ConcurrentHashMap<Short, Course> courses;
	private HashMap<Short, Short> collectionCoursesCourses= new HashMap<Short, Short>();
	private AtomicInteger currentLoggedIn = new AtomicInteger(0);

	public void currentLoggedPlus(){
		int val;
		do{
			val = currentLoggedIn.get();
		}
		while (!currentLoggedIn.compareAndSet(val,val+1));
	}
	public void currentLoggedMinus(){
		int val;
		do{
			val = currentLoggedIn.get();
		}
		while (!currentLoggedIn.compareAndSet(val,val-1));
	}

	public AtomicInteger getCurrentLoggedIn(){
		return currentLoggedIn;
	}




	private static class SingletonHolder {
		private static Database instance = new Database();
	}


	//to prevent user from creating new Database
	private Database() {
		users = new HashMap<String, User>();
		courses = new ConcurrentHashMap<>();


	}


	/**
	 * Retrieves the single instance of this class.
	 */
	public static Database getInstance() {

		return SingletonHolder.instance;
	}


	public HashMap<String, User> getUsers() {

		return users;
	}

	public void addUser(String userName, User user){
		users.put(userName,user);
	}

	public ConcurrentHashMap<Short, Course> getCourses(){
		return courses;
	}
	public HashMap<Short,Short> getCollectionCourses(){
		return collectionCoursesCourses;
	}


	/**
	 * loades the courses from the file path specified
	 * into the Database, returns true if successful.
	 */
	public boolean initialize(String coursesFilePath) {
		try {

			List<String> allLines = Files.readAllLines(Paths.get(coursesFilePath), StandardCharsets.UTF_8);
			for (short i = 0; i < allLines.size(); i++) {
				initializeCourseHashMap(allLines.get(i),i);
			}

		}
		catch (Exception e){
			return false;
		}
		return true;

	}


			private void initializeCourseHashMap(String line,Short index) {
			String[] splitCourseInfo = line.split("\\|");
				if (splitCourseInfo.length != 4) {
					throw new IllegalArgumentException("line is missing data");
				}

				short courseNum = (short) Integer.parseInt(splitCourseInfo[0]);

				collectionCoursesCourses.put(courseNum, index);
				int[] kdamArray = new int[]{};
				if (!splitCourseInfo[2].equals("[]")) {
					String kdam = splitCourseInfo[2].replace("[", "").replace("]", "");
					kdamArray = Stream.of(kdam.split(","))
							.map(String::trim)
							.mapToInt(Integer::parseInt)
							.toArray();
				}

				courses.putIfAbsent(courseNum, new Course(courseNum, splitCourseInfo[1], kdamArray, Integer.parseInt(splitCourseInfo[3])));

			}


}
