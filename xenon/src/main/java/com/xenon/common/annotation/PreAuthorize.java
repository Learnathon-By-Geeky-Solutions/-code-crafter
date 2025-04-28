package com.xenon.common.annotation;

import com.xenon.data.entity.user.UserRole;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @ interface PreAuthorize {
    UserRole[] authorities() default {UserRole.USER, UserRole.ADMIN, UserRole.DOCTOR, UserRole.HEALTH_AUTHORIZATION, UserRole.HOSPITAL, UserRole.BLOOD_BANK, UserRole.PHARMACY, UserRole.AMBULANCE};
    boolean shouldCheckAccountStatus() default false;
}