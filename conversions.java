public class MetricConversion {

    private final static int LENGTH = -5;
    private final static int MASS = -4;
    private final static int VOLUME = -3;
    private final static int UNSUPPORTED = -1;
    private final static int INVALIDCONVERSION = -2;
    private final static int INCHES = 0;
    private final static int FEET = 1;
    private final static int YARDS = 2;
    private final static int MILES = 3;
    private final static int OUNCES = 4;
    private final static int POUNDS = 5;
    private final static int TONS = 6;
    private final static int GALLONS = 7;
    private final static int QUARTS = 8;
    private final static int CENTIMETERS = 0;
    private final static int MILLIMETERS = 1;
    private final static int METERS = 2;
    private final static int KILOMETERS = 3;
    private final static int GRAMS= 4;
    private final static int KILOGRAMS = 5;
    private final static int LITERS = 6;
    private final static int MILLILITERS = 7;
    /**
     * String entered by user for conversion
     */
    private final String conversionString;
    /**
     * holds the split representation of the conversion string
     */
    private final String[] splitConversion;
    /**
     * int representation of the english unit
     */
    private int englishUnit;
    /**
     * int representation of metric unit
     */
    private int metricUnit;
    /**
     * 2d array that holds the conversion factors. Accessed via int representation of units. rows index is metric unit and column index is english unit.
     */
    private final double[][] conversionFactors;
    /**
     * conversion in english unit. type double.
     */
    private double englishConversion;
    /**
     * metric value as type double.
     */
    private double metric;


    /**
     * Constructor takes in the conversion string and sets all values. Puts conversion factors into conversion array. Splits the input by whitespace. And assigns the double representation of metric input.
     * @param conversionString
     */
    public MetricConversion(String conversionString) {
        this.conversionString = conversionString;
        this.splitConversion = this.conversionString.split(" ");
        conversionFactors = new double[8][9];
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 9; j++) {
                conversionFactors[i][j] = 0.0 ;
            }
        }
        conversionFactors[CENTIMETERS][INCHES] = 0.393701;
        conversionFactors[CENTIMETERS][FEET] = 0.0328084;
        conversionFactors[CENTIMETERS][YARDS] = 0.0109361;
        conversionFactors[CENTIMETERS][MILES] = 6.2137 * Math.pow(10.0, -6);
        conversionFactors[MILLIMETERS][INCHES] = 0.0393701;
        conversionFactors[MILLIMETERS][FEET] = 0.00328084;
        conversionFactors[MILLIMETERS][YARDS] = 0.00109361;
        conversionFactors[MILLIMETERS][MILES] = 6.2137 * Math.pow(10.0, -7);
        conversionFactors[METERS][INCHES] = 39.3701;
        conversionFactors[METERS][FEET] = 3.28084;
        conversionFactors[METERS][YARDS] = 1.09361;
        conversionFactors[METERS][MILES] = 6.2137 * Math.pow(10.0, -4);
        conversionFactors[KILOMETERS][INCHES] = 39370.1;
        conversionFactors[KILOMETERS][FEET] = 3280.84;
        conversionFactors[KILOMETERS][YARDS] = 1093.61;
        conversionFactors[KILOMETERS][MILES] = 0.62137;

        conversionFactors[GRAMS][OUNCES] = 0.035274;
        conversionFactors[GRAMS][POUNDS] = 0.00220462;
        conversionFactors[GRAMS][TONS] = 1.1023 * Math.pow(10.0, -6);
        conversionFactors[KILOGRAMS][OUNCES] = 35.274;
        conversionFactors[KILOGRAMS][POUNDS] = 2.20462;
        conversionFactors[KILOGRAMS][TONS] = 0.00110231;

        conversionFactors[LITERS][GALLONS] = 0.264172;
        conversionFactors[LITERS][QUARTS] = 1.05669;
        conversionFactors[MILLILITERS][GALLONS] = 0.000264172;
        conversionFactors[MILLILITERS][QUARTS] = 0.00105669;

        this.metric = Integer.valueOf(splitConversion[5]);

    }

    /**
     * convert method that return the conversion.
     * @return conversion as string.
     */
    public String convert () {

        int type = this.determineConversionType();
        String finalConversion = null;

        if(type == LENGTH) {
            System.out.println("Length Conversion");
        }
        else if(type == MASS) {
            System.out.println("Mass Conversion");
        }
        else if(type == VOLUME) {
            System.out.println("Volume Conversion");
        }
        else if(type == UNSUPPORTED) {
            return "One of the units is not supported at this time";
        }
        else if(type == INVALIDCONVERSION) {
            return "Invalid conversion between specified units";
        }

        this.calculateConversion();


        return "Result is: " + this.englishConversion + " " +  splitConversion[2];

    }


    /**
     * Method to determine the type of conversion. Calls determineMetricUnit and determineEnglishUnit if the conversion type is valid.
     * @return The type: Length, mass, or volume.
     */
    private int determineConversionType () {
        int typeEnglish = -1;
        int typeMetric = -1;

        //use the split representation to access the unit keywords

        if(splitConversion[2].equals("inches") || splitConversion[2].equals("feet") || splitConversion[2].equals("yards") || splitConversion[2].equals("miles")) {
            typeEnglish = LENGTH;
        }
        else if(splitConversion[2].equals("ounces") || splitConversion[2].equals("pounds") || splitConversion[2].equals("tons")) {
            typeEnglish = MASS;
        }
        else if(splitConversion[2].equals("gallons") || splitConversion[2].equals("quarts")) {
            typeEnglish = VOLUME;
        }
        else {
            typeEnglish = UNSUPPORTED;
            return UNSUPPORTED;
        }

        if(splitConversion[6].equals("centimeters?") || splitConversion[6].equals("millimeters?") || splitConversion[6].equals("meters?") || splitConversion[6].equals("kilometers?")) {
            typeMetric = LENGTH;
        }
        else if(splitConversion[6].equals("grams?") || splitConversion[6].equals("kilograms?")) {
            typeMetric = MASS;
        }
        else if(splitConversion[6].equals("liters?") || splitConversion[6].equals("milliliters?")) {
            typeMetric = VOLUME;
        }
        else {
            typeMetric = UNSUPPORTED;
            return UNSUPPORTED;
        }

        //if the type is valid then determine the units and return the type
        if(typeEnglish == typeMetric) {
            this.determineMetricUnit();
            this.determineEnglishUnit();
            return typeEnglish;
        }
        else {
            return INVALIDCONVERSION;
        }

    }

    /**
     * Determines the unit of metric input.
     */
    private void determineMetricUnit() {
        if(splitConversion[6].equals("centimeters?")) {
            this.metricUnit = CENTIMETERS;
        }
        else if(splitConversion[6].equals("millimeters?")) {
            this.metricUnit = MILLIMETERS;
        }
        else if(splitConversion[6].equals("meters?")) {
            this.metricUnit = METERS;
        }
        else if(splitConversion[6].equals("kilometers?")) {
            this.metricUnit = KILOMETERS;
        }
        else if(splitConversion[6].equals("grams?")) {
            this.metricUnit = GRAMS;
        }
        else if(splitConversion[6].equals("kilograms?")) {
            this.metricUnit = KILOGRAMS;
        }
        else if(splitConversion[6].equals("liters?")) {
            this.metricUnit = LITERS;
        }
        else if(splitConversion[6].equals("milliliters?")) {
            this.metricUnit = MILLILITERS;
        }
    }

    /**
     * Determines the english unit specified by user.
     */
    private void determineEnglishUnit() {
        if(splitConversion[2].equals("inches")) {
            this.englishUnit = INCHES;
        }
        else if(splitConversion[2].equals("feet")) {
            this.englishUnit = FEET;
        }
        else if(splitConversion[2].equals("yards")) {
            this.englishUnit = YARDS;
        }
        else if(splitConversion[2].equals("miles")) {
            this.englishUnit = MILES;
        }
        else if(splitConversion[2].equals("ounces")) {
            this.englishUnit = OUNCES;
        }
        else if(splitConversion[2].equals("pounds")) {
            this.englishUnit = POUNDS;
        }
        else if(splitConversion[2].equals("tons")) {
            this.englishUnit = TONS;
        }
        else if(splitConversion[2].equals("gallons")) {
            this.englishUnit = GALLONS;
        }
        else if(splitConversion[2].equals("quarts")) {
            this.englishUnit = QUARTS;
        }
    }

    /**
     * Carries out the conversion by multiplying the metric value by the conversion factor from the conversionFactor array.
     */
    private void calculateConversion() {
       this.englishConversion =  this.metric * conversionFactors[metricUnit][englishUnit];
    }
}
