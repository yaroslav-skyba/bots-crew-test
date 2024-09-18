package com.botscrew.test.entity;

import com.botscrew.test.util.Degree;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Lector {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToMany(mappedBy = "lectorSet")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private final Set<Department> departmentSet = new HashSet<>();

    @OneToOne(mappedBy = "head", fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Department current;

    @Enumerated(EnumType.STRING)
    private Degree degree;

    private String firstName;
    private String lastName;
    private Float salary;
}
