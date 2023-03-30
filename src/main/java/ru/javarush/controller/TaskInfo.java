package ru.javarush.controller;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import ru.javarush.entity.Status;

@Getter @Setter
@EqualsAndHashCode
public class TaskInfo {
    private String description;
    private Status status;
}
