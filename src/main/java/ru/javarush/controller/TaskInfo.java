package ru.javarush.controller;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import ru.javarush.entity.Status;

@Data
public class TaskInfo {
    private String description;
    private Status status;
}
