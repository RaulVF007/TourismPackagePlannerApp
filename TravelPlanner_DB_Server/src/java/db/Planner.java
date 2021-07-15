/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Lab-DIS
 */
@Entity
@Table(name = "planner")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Planner.findAll", query = "SELECT p FROM Planner p")
    , @NamedQuery(name = "Planner.findById", query = "SELECT p FROM Planner p WHERE p.id = :id")
    , @NamedQuery(name = "Planner.findByFirstName", query = "SELECT p FROM Planner p WHERE p.firstName = :firstName")
    , @NamedQuery(name = "Planner.findByLastName", query = "SELECT p FROM Planner p WHERE p.lastName = :lastName")
    , @NamedQuery(name = "Planner.findByCheckIn", query = "SELECT p FROM Planner p WHERE p.checkIn = :checkIn")
    , @NamedQuery(name = "Planner.findByCheckOut", query = "SELECT p FROM Planner p WHERE p.checkOut = :checkOut")
    , @NamedQuery(name = "Planner.findByDestination", query = "SELECT p FROM Planner p WHERE p.destination = :destination")
    , @NamedQuery(name = "Planner.findByHotel", query = "SELECT p FROM Planner p WHERE p.hotel = :hotel")
    , @NamedQuery(name = "Planner.findByFlight", query = "SELECT p FROM Planner p WHERE p.flight = :flight")
    , @NamedQuery(name = "Planner.findByBudget", query = "SELECT p FROM Planner p WHERE p.budget = :budget")})
public class Planner implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Integer id;
    @Size(max = 1000)
    @Column(name = "FirstName")
    private String firstName;
    @Size(max = 1000)
    @Column(name = "LastName")
    private String lastName;
    @Column(name = "CheckIn")
    @Temporal(TemporalType.TIMESTAMP)
    private Date checkIn;
    @Column(name = "CheckOut")
    @Temporal(TemporalType.TIMESTAMP)
    private Date checkOut;
    @Size(max = 1000)
    @Column(name = "Destination")
    private String destination;
    @Size(max = 1000)
    @Column(name = "Hotel")
    private String hotel;
    @Size(max = 1000)
    @Column(name = "Flight")
    private String flight;
    @Size(max = 1000)
    @Column(name = "Budget")
    private String budget;

    public Planner() {
    }

    public Planner(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(Date checkIn) {
        this.checkIn = checkIn;
    }

    public Date getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(Date checkOut) {
        this.checkOut = checkOut;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getHotel() {
        return hotel;
    }

    public void setHotel(String hotel) {
        this.hotel = hotel;
    }

    public String getFlight() {
        return flight;
    }

    public void setFlight(String flight) {
        this.flight = flight;
    }

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Planner)) {
            return false;
        }
        Planner other = (Planner) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "db.Planner[ id=" + id + " ]";
    }
    
}
