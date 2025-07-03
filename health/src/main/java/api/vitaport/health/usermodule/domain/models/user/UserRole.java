package api.vitaport.health.usermodule.domain.models.user;

import api.vitaport.health.commonmodule.infra.exceptions.CannotGetEnumException;
import api.vitaport.health.commonmodule.infra.exceptions.ErrorEnum;

public enum UserRole {
    EMPLOYEE("employee"),
    DATA_ANALYST("data_analyst"),
    SYSTEM_MANAGER("system_manager");

    private final String role;

    UserRole(String role){
        this.role = role;
    }

    public static UserRole fromString(String role){
        for (UserRole userRole : UserRole.values()){
            if (userRole.role.equalsIgnoreCase(role))
                return userRole;
        }
        throw new CannotGetEnumException(400, ErrorEnum.LAPI,"cannot get enum by role string");
    }
}
