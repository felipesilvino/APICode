package code.example.patient.model;

import javax.ws.rs.core.Link;
import java.time.Instant;

/**
 * Created by aweise on 03/01/17.
 */
public class PatientEntity {

    private Integer id;
    private String name;
    private Instant birthday;

    private Link medicines;

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Instant getBirthday() {
        return birthday;
    }

    public void setBirthday(final Instant birthday) {
        this.birthday = birthday;
    }

    public Link getMedicines() {
        return medicines;
    }

    public void setMedicines(final Link medicines) {
        this.medicines = medicines;
    }
}
