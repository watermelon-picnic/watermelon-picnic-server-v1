package com.server.watermelonserverv1.global.entity;

import lombok.Getter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;


@Getter
@MappedSuperclass
public abstract class BasedIdEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
}
