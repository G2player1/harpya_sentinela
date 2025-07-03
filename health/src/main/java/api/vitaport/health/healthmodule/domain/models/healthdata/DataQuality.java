package api.vitaport.health.healthmodule.domain.models.healthdata;

import api.vitaport.health.commonmodule.infra.exceptions.CannotGetEnumException;
import api.vitaport.health.commonmodule.infra.exceptions.ErrorEnum;

public enum DataQuality {
    BAD("ruim"),
    MODERATED("moderado"),
    GOOD("bom");

    private final String quality;

    DataQuality(String quality){
        this.quality = quality;
    }

    public static DataQuality fromString(String quality){
        for (DataQuality dataQuality : DataQuality.values()){
            if (dataQuality.quality.equalsIgnoreCase(quality))
                return dataQuality;
        }
        throw new CannotGetEnumException(400, ErrorEnum.LAPI,"quality is invalid");
    }
}
