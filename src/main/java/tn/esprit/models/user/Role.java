package tn.esprit.models.user;

public enum Role {
    CONDUCTEUR, PASSAGER, ADMINISTRATEUR;

    public static Role fromString(String role) {
        for (Role r : Role.values()) {
            if (r.name().equalsIgnoreCase(role)) {
                return r;
            }
        }
        throw new IllegalArgumentException("Valeur de rôle invalide : " + role);
    }
}

