package jpa.service;

import jpa.dao.StudentDAO;
import jpa.entitymodels.Course;
import jpa.entitymodels.Student;
import jpa.entitymodels.StudentCourse;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;

public class StudentService implements StudentDAO {

    private static final String PERSISTENCE_UNIT_NAME = "smsdb";

    private static EntityManagerFactory enFactoryObj = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
    EntityManager entityMgr = enFactoryObj.createEntityManager();

    @Override
    public List<Student> getAllStudents() {
        String sql = "Select s From Student s";
        TypedQuery<Student> query = entityMgr.createQuery(sql, Student.class);
        List<Student> result = query.getResultList();
        if (result.size() == 0) {
            System.out.println("No students currently enrolled");
            return null;
        }
        try {
            System.out.printf("%-30s %-20s %-20s \n", "EMAIL", "NAME", "PASSWORD");
            for (Student x : result) {
                System.out.printf("%-30s %-20s %-20s \n", x.getSEmail(), x.getSName(), x.getSPass());
            }
            return result;
        } catch (Exception e) {
            System.out.println("No student found");
            return null;
        }
    }

    @Override
    public Student getStudentByEmail(String sEmail) {
        String sql = "Select s From Student s Where s.sEmail = :email";
        TypedQuery<Student> query = entityMgr.createQuery(sql, Student.class);
        query.setParameter("email", sEmail);
        try {
            Student result = query.getSingleResult();
            System.out.printf("%-30s %-20s %-20s \n", "EMAIL", "NAME", "PASSWORD");
            System.out.printf("%-30s %-20s %-20s \n", result.getSEmail(), result.getSName(), result.getSPass());
            return result;
        } catch (Exception e) {
            System.out.println("Email not found");
            return null;
        }
    }

    @Override
    public boolean validateStudent(String sEmail, String sPassword) {
        String sql = "Select s From Student s Where s.sEmail = :email and s.sPass = :pw";
        TypedQuery<Student> query = entityMgr.createQuery(sql, Student.class);
        query.setParameter("email", sEmail);
        query.setParameter("pw", sPassword);
        try {
            query.getSingleResult();
//            System.out.println(true);
            return true;
        } catch (Exception e) {
//            System.out.println(false);
            return false;
        }
    }

    @Override
    public void registerStudentToCourse(String sEmail, int cId) {
        String sql = "Select sc From StudentCourse sc Where sc.stEmail = :email and sc.csId =: id";
        TypedQuery<StudentCourse> query = entityMgr.createQuery(sql, StudentCourse.class);
        query.setParameter("email", sEmail);
        query.setParameter("id", cId);
        try {
            System.out.println("Registration failed. Class already registered");
            query.getSingleResult();
        } catch (Exception e) {
            StudentCourse stc = new StudentCourse(sEmail, cId);
            Student st = getStudentByEmail(sEmail);
            Course c = new CourseService().getCourseById(cId);
            stc.setAStudent(st);
            stc.setACourse(c);
            save(stc);
            getStudentCourses(sEmail);
            System.out.println("Course registered successful");
            System.out.println("You have been signed out");
        }
    }
    public StudentCourse save(StudentCourse sCourse) {
        EntityManager em = enFactoryObj.createEntityManager();
        em.getTransaction().begin();
        em.persist(sCourse);
        em.getTransaction().commit();
        em.clear();
        return sCourse;
    }

    @Override
    public List<Course> getStudentCourses(String sEmail) {
        String sql = "Select c From StudentCourse sc, Course c Where sc.stEmail = :email and sc.csId = c.cId";
        TypedQuery<Course> query = entityMgr.createQuery(sql, Course.class);
        query.setParameter("email", sEmail);
        List<Course> result = query.getResultList();
        if (result.size() == 0) {
            System.out.println("Have not enrolled into a course");
            return null;
        }
        try {
            System.out.println("My Classes:");
            System.out.printf("%-5s %-30s %-25s \n", "ID", "COURSE NAME", "INSTRUCTOR NAME");
            for (Course x : result) {
                System.out.printf("%-5d %-30s %-25s \n", x.getCId(), x.getCName(), x.getCInstructorName());
            }
            return result;
        } catch (Exception e) {
            System.out.println("No courses found");
            return null;
        }
    }
}
