package pocketDock.com.pocketDock.service.Interfaces;

import java.io.IOException;

public interface IServiceMapbox {

    public String getAddressFromCoordinates(Double longitude, Double latitude) throws IOException;

}
