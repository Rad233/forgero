package com.sigmundgranaas.forgero.resources.loader;

import com.sigmundgranaas.forgero.core.resource.ForgeroResourceType;

public class ResourceLocations {
    public static String SCHEMATIC_LOCATION = "data/forgero/core/schematic/";
    public static String GEM_LOCATION = "data/forgero/core/upgrade/gem/";
    public static String MATERIAL_LOCATION = "data/forgero/core/materials/";
    public static String TOOL_LOCATION = "data/forgero/tools/";

    public static String TOOL_PART_LOCATION = "data/forgero/toolparts/";
    public static String MATERIAL_TEMPLATES_LOCATION = "assets/forgero/templates/materials/";

    public static String getPathFromType(ForgeroResourceType type) {
        return switch (type) {
            case MATERIAL -> MATERIAL_LOCATION;
            case GEM -> GEM_LOCATION;
            case SCHEMATIC -> SCHEMATIC_LOCATION;
            case TOOL -> TOOL_LOCATION;
            case TOOL_PART -> TOOL_PART_LOCATION;
        };
    }
}
