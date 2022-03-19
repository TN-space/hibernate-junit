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
@Table(name = "Student")
public class Student {

    @Id
    @Column(name = "email")
    private String sEmail;

    @Column(name = "name")
    private String sName;

    @Column(name = "password")
    private String sPass;

    @ToString.Exclude
    @OneToMany(mappedBy = "aStudent", fetch = FetchType.LAZY)
    private List<StudentCourse> studentCourseList;

    public Student() {
        this.sEmail = "";
        this.sName = "";
        this.sPass = "";
    }

    public Student(String sEmail, String sName, String sPass) {
        this.sEmail = sEmail;
        this.sName = sName;
        this.sPass = sPass;
    }
}
