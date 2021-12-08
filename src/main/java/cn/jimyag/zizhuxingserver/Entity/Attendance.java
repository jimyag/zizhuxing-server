package cn.jimyag.zizhuxingserver.Entity;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.Objects;

@Entity
public class Attendance {

    @Id
    @GeneratedValue
    private int id;



    @Column(name = "studentId")
    private String studentId;

    @Column(name = "signInTime")
    private BigInteger signInTime;

    @Column(name = "signOutTime")
    private BigInteger signOutTime;

    @Column(name = "remark")
    private String remark;

    public Attendance() {
    }

    public Attendance(int id, String studentId, BigInteger signInTime, BigInteger signOutTime, String remark) {
        this.id = id;
        this.studentId = studentId;
        this.signInTime = signInTime;
        this.signOutTime = signOutTime;
        this.remark = remark;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public BigInteger getSignInTime() {
        return signInTime;
    }

    public void setSignInTime(BigInteger signInTime) {
        this.signInTime = signInTime;
    }

    public BigInteger getSignOutTime() {
        return signOutTime;
    }

    public void setSignOutTime(BigInteger signOutTime) {
        this.signOutTime = signOutTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "Attendance{" +
                "id=" + id +
                ", studentId='" + studentId + '\'' +
                ", signInTime=" + signInTime +
                ", signOutTime=" + signOutTime +
                ", remark='" + remark + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Attendance)) return false;
        Attendance that = (Attendance) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
