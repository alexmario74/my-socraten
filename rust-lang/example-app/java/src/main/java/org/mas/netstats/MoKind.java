package org.mas.netstats;

public class MoKind {
    private final String kind;
    private final String model;
    private final String technology;

    private MoKind(String kind, String model, String technology){
        this.kind = kind;
        this.model = model;
        this.technology = technology;
    }

    public static MoKind of(String moClass) {
        switch (moClass) {
            case "ETAC":
            case "NTAC":
            case "HTAC":
                return new MoKind("Radio", "Area", "4G");

            case "EEND":
            case "NENB":
            case "HENB":
                return new MoKind("Radio", "Site", "4G");

            case "ECLL":
            case "NECL":
            case "HECL":
                return new MoKind("Radio", "Cell", "4G");

            case "RNC":
                return new MoKind("Radio", "Area", "3G");

            case "HNDB":
            case "ENDB":
            case "EBTS":
                return new MoKind("Radio", "Site", "3G");

            case "WCEL":
            case "ERCL":
            case "HRCL":
                return new MoKind("Radio", "Cell", "3G");

            case "BCF":
            case "BSC":
            case "EBSC":
            case "HBSC":
                return new MoKind("Radio", "Area", "2G");

            case "BTS":
            case "HBTS":
            case "WBTS":
                return new MoKind("Radio", "Site", "2G");

            case "ESCE":
            case "HWCL":
                return new MoKind("Radio", "Cell", "2G");

            case "EMGW":
                return new MoKind("Core", "", "MGW");

            case "EGGS":
                return new MoKind("Core", "", "GGSN");

            case "EHLR":
                return new MoKind("Core", "", "HLR");

            case "EINS":
                return new MoKind("Core", "", "INS");

            case "EMCS":
                return new MoKind("Core", "", "MSC");

            case "ESGS":
                return new MoKind("Core", "", "MME");

            case "ESMS":
                return new MoKind("Core", "", "SMSC");

            case "ETRP":
                return new MoKind("Core", "", "TR STP IP");

            case "ETSC":
                return new MoKind("Core", "", "TR TSC");

            case "EHSS":
                return new MoKind("Core", "", "HSS");

            case "ESBG":
                return new MoKind("Core", "", "SBG");

            default:
                throw new IllegalArgumentException("no service for class " + moClass);
        }
    }

    public boolean isRadio() {
        return "Radio".equals(this.kind);
    }

    public boolean isCore() {
        return "Core".equals(this.kind);
    }
}
