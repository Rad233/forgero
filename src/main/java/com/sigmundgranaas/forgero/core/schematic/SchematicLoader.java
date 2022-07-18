package com.sigmundgranaas.forgero.core.schematic;

import com.sigmundgranaas.forgero.ForgeroInitializer;
import com.sigmundgranaas.forgero.core.data.factory.SchematicFactory;
import com.sigmundgranaas.forgero.core.data.v1.pojo.SchematicPojo;
import com.sigmundgranaas.forgero.core.util.JsonPOJOLoader;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.*;
import java.util.*;

public class SchematicLoader {
    public List<Schematic> loadSchematics() {
        var location = "/data/forgero/core/schematic/";
        URI uri = null;
        try {
            var etc = ForgeroInitializer.class.getResource(location);
            assert etc != null;
            uri = etc.toURI();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        Path dirPath = null;
        try {

            assert uri != null;
            dirPath = Paths.get(uri);
        } catch (FileSystemNotFoundException e) {
            // If this is thrown, then it means that we are running the JAR directly (example: not from an IDE)
            var env = new HashMap<String, String>();
            try {
                dirPath = FileSystems.newFileSystem(uri, env).getPath(location);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        try {
            assert dirPath != null;
            var pojos = Files.list(dirPath).map(path -> JsonPOJOLoader.loadPOJO(String.format("/data/forgero/core/schematic/%s", path.getFileName()), SchematicPojo.class)).flatMap(Optional::stream).toList();
            var factory = new SchematicFactory(pojos, Set.of("forgero", "minecraft"));
            return pojos.stream().map(factory::buildResource).flatMap(Optional::stream).toList();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }


}
