package fr.imt.raimed2.action.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("PERCUSSION")
public class ActionPercussion extends ActionExamen {
}
