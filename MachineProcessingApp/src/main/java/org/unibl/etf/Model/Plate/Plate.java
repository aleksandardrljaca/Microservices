package org.unibl.etf.Model.Plate;

import org.unibl.etf.Util.HttpUtil;
import org.unibl.etf.Util.JsonUtil;
import org.unibl.etf.Util.PropertiesService;


public class Plate {
    private final int id;

    public Plate() {
        String plateId = HttpUtil.sendHttpGetRequest(PropertiesService.getLastPlateIdEndpointUrl());
        Integer lastPlateId = JsonUtil.extractPlateIdFromJSON(plateId);
        if (lastPlateId != null)
            this.id = lastPlateId + 1;
        else this.id = 1;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\":" + id +
                "}";
    }
}
