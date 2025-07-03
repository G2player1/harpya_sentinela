package api.vitaport.health.wearablemodule.domain.models.wearable;

import api.vitaport.health.commonmodule.infra.exceptions.CannotGetEnumException;
import api.vitaport.health.commonmodule.infra.exceptions.ErrorEnum;

public enum WearableType {
    WATCH("watch"),
    BAND("band");

    private String type;

    WearableType(String type){
        this.type = type;
    }

    public static WearableType fromString(String type){
        for (WearableType wearableType : WearableType.values()){
            if (wearableType.type.equalsIgnoreCase(type))
                return wearableType;
        }
        throw new CannotGetEnumException(400, ErrorEnum.GAPI, "the wearable type is invalid");
    }
}
