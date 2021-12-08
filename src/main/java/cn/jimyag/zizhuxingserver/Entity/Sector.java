package cn.jimyag.zizhuxingserver.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
public class Sector {

    @Id
    private String secretKey;

    @Column(name = "sectorName")
    private String sectorName;

    public Sector() {
    }

    public Sector(String secretKey, String sectorName) {
        this.secretKey = secretKey;
        this.sectorName = sectorName;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getSectorName() {
        return sectorName;
    }

    public void setSectorName(String sectorName) {
        this.sectorName = sectorName;
    }

    @Override
    public String toString() {
        return "Sector{" +
                "secretKey='" + secretKey + '\'' +
                ", sectorName='" + sectorName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Sector)) return false;
        Sector sector = (Sector) o;
        return Objects.equals(secretKey, sector.secretKey);
    }

    @Override
    public int hashCode() {
        return Objects.hash(secretKey);
    }
}
