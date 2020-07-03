package streetsim.ui.spielfeld.elemente;

import javafx.scene.input.DataFormat;

import java.util.List;

public class ViewDataFormats {

    public static final String AUTO_FORMAT = "AUTO";
    public static final String GERADE_FORMAT ="GERADE";
    public static final String KREUZUNG_FORMAT ="KREUZUNG";
    public static final String KURVE_FORMAT = "KURVE";
    public static final String TSTUECK_FORMAT = "TSTUECK";
    public static final String AMPEL_FORMAT = "AMPEL";
    public static final List<String> DATA_FORMATS =
            List.of(AUTO_FORMAT, GERADE_FORMAT, KREUZUNG_FORMAT, KURVE_FORMAT, TSTUECK_FORMAT, AMPEL_FORMAT);

}
