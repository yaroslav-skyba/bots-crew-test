package com.botscrew.test.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Department {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "department_lector",
               joinColumns = @JoinColumn(name = "department_id"),
               inverseJoinColumns = @JoinColumn(name = "lector_id"))
    private final Set<Lector> lectorSet = new HashSet<>();

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Lector head;

    @Column(unique = true)
    private String departmentName;

    public void addLector(Lector lector) {
        lectorSet.add(lector);

        lector.getDepartmentSet().add(this);
    }

    public void removeLector(Lector lector) {
        lectorSet.remove(lector);

        lector.getDepartmentSet().remove(this);
    }

    public void setHead(Lector head) {
        if (head == null) {
            if (this.head != null) {
                this.head.setCurrent(null);
            }
        } else {
            head.setCurrent(this);
        }

        this.head = head;
    }
}
