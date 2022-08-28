package com.franciscothiago.bookstoremanager.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum Status {
    RETURNED_BEFORE("Devolvido (sem atraso)"),
    RETURNED_AFTER("Devolvido (com atraso)"),
    WAITING("Em espera...");

    private String description;

}
