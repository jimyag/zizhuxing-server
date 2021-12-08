package cn.jimyag.zizhuxingserver.Entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Schedule {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "sectorName")
    private String sectorName;

    @Column(name = "studentName")
    private String studentName;

    @Column(name = "dayOfWeek")
    private int dayOfWeek;

    @Column(name = "courseIndex")
    private String courseIndex;

    @Column(name = "address")
    private String address;

    public Schedule() {
    }

    public Schedule(String sectorName, String studentName, int dayOfWeek, String courseIndex, String address) {
        this.sectorName = sectorName;
        this.studentName = studentName;
        this.dayOfWeek = dayOfWeek;
        this.courseIndex = courseIndex;
        this.address = address;
    }

    public String getSectorName() {
        return sectorName;
    }

    public void setSectorName(String sectorName) {
        this.sectorName = sectorName;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentId) {
        this.studentName = studentId;
    }

    public int getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(int dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getCourseIndex() {
        return courseIndex;
    }

    public void setCourseIndex(String courseIndex) {
        this.courseIndex = courseIndex;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Schedule{" +
                "sectorName='" + sectorName + '\'' +
                ", studentName=" + studentName +
                ", dayOfWeek=" + dayOfWeek +
                ", courseIndex='" + courseIndex + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Schedule)) return false;
        Schedule schedule = (Schedule) o;
        return studentName.equals(schedule.studentName) && dayOfWeek == schedule.dayOfWeek && Objects.equals(sectorName, schedule.sectorName) && Objects.equals(courseIndex, schedule.courseIndex) && Objects.equals(address, schedule.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sectorName, studentName, dayOfWeek, courseIndex, address);
    }
}
