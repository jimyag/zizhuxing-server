package cn.jimyag.zizhuxingserver.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
public class Student {
    @Id
    private String studentId;

    @Column(name = "studentName")
    private String studentName;

    @Column(name = "institute")
    private String institute;

    @Column(name = "major")
    private String major;

    @Column(name = "phone")
    private String phone;

    public Student() {
    }

    public Student(String studentId, String studentName, String institute, String major, String phone) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.institute = institute;
        this.major = major;
        this.phone = phone;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getInstitute() {
        return institute;
    }

    public void setInstitute(String institute) {
        this.institute = institute;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentId='" + studentId + '\'' +
                ", studentName='" + studentName + '\'' +
                ", institute='" + institute + '\'' +
                ", major='" + major + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;
        Student student = (Student) o;
        return Objects.equals(studentId, student.studentId) && Objects.equals(studentName, student.studentName) && Objects.equals(institute, student.institute) && Objects.equals(major, student.major) && Objects.equals(phone, student.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId, studentName, institute, major, phone);
    }
}
