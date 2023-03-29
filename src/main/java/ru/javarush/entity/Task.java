package ru.javarush.entity;

import jakarta.persistence.*;

@Entity
@Table(schema = "todo", name = "task")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "description", length = 100, nullable = false)
    private String description;

    @Enumerated(EnumType.ORDINAL)
    private Status status;
}
