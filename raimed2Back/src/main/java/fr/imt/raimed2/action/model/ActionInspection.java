package fr.imt.raimed2.action.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("INSPECTION")
public class ActionInspection extends ActionExamen {
}
