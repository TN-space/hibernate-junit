package jpa.service;

import jpa.dao.CourseDAO;
import jpa.entitymodels.Course;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;

public class CourseService implements CourseDAO {
    private static final String PERSISTENCE_UNIT_NAME = "smsdb";

    private static EntityManagerFactory enFactoryObj = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
    EntityManager entityMgr = enFactoryObj.createEntityManager();

    @Override
    public List<Course> getAllCourses() {
        String sql = "Select c From Course c";
        TypedQuery<Course> query = entityMgr.createQuery(sql, Course.class);
        List<Course> result = query.getResultList();
        if (result.size() == 0) {
            System.out.println("No courses offered at this time");
            return null;
        }
        try {
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

    public Course getCourseById(int cId) {
        String sql = "Select c From Course c Where c.cId = :id";
        TypedQuery<Course> query = entityMgr.createQuery(sql, Course.class);
        query.setParameter("id", cId);
        try {
            Course result = query.getSingleResult();
            return result;
        } catch (Exception e) {
            System.out.println("Course ID not found");
            return null;
        }
    }
}
