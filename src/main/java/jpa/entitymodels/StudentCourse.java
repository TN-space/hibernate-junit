package jpa.entitymodels;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
@Table(name = "Student_Course")
public class StudentCourse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "st_email", updatable = false, insertable = false)
    private String stEmail;

    @Column(name = "course_id", updatable = false, insertable = false)
    private Integer csId;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "st_email", nullable = false)
    private Student aStudent;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "course_id", nullable = false)
    private Course aCourse;

    public StudentCourse(String stEmail, Integer csId) {
        this.stEmail = stEmail;
        this.csId = csId;
    }

    public StudentCourse() {};
}
