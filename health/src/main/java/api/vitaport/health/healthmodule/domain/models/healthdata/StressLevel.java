package api.vitaport.health.healthmodule.domain.models.healthdata;

import api.vitaport.health.commonmodule.infra.exceptions.CannotGetEnumException;
import api.vitaport.health.commonmodule.infra.exceptions.ErrorEnum;

public enum StressLevel {
    LOW("low"),
    ALERT_STATE("alert"),
    TENSIONED("tensioned"),
    HIGH_STRESS("high");

    private final String level;

    StressLevel(String level){
        this.level = level;
    }

    public static StressLevel fromString(String quality){
        for (StressLevel stressLevel : StressLevel.values()){
            if (stressLevel.level.equalsIgnoreCase(quality))
                return stressLevel;
        }
        throw new CannotGetEnumException(400, ErrorEnum.GAPI, "the sleep quality is invalid");
    }
}
