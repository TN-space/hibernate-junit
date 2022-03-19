package jpa.entitymodels;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@Entity
@Table(name = "Course")
public class Course {

    @Id
    @Column(name = "id")
    private Integer cId;

    @Column(name = "name")
    private String cName;

    @Column(name = "instructor")
    private String cInstructorName;

    @ToString.Exclude
    @OneToMany(mappedBy = "aCourse", fetch = FetchType.LAZY)
    private List<StudentCourse> studentCourseList;

    public Course() {
        this.cId = 0;
        this.cName = "";
        this.cInstructorName = "";
    }

    public Course(int cId, String cName, String cInstructorName) {
        this.cId = cId;
        this.cName = cName;
        this.cInstructorName = cInstructorName;
    }
}
