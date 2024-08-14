package it.unibo.io;

import it.unibo.io.model.SignorCervo.Member;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SimpleTest {

    JsonReader jsonReader = new JsonReader();

    @Test
    void jsonTest() {
        String dialoghi = GetResources.findResourcesDirectory(new File(System.getProperty("user.dir")), "dialoghi").get(5).toURI().toString().replace("file:/", "");
        assertEquals(true, dialoghi.contains("dialoghi6.json"));
        jsonReader.readJson(5);
        assertEquals("Vuoi giocare a Signor Cervo?\n", jsonReader.getDialog(0));
        assertEquals("signorcervo_insanguinato.png", jsonReader.getImage(1));
        assertEquals(16, jsonReader.getSize());
        assertEquals(true, jsonReader.checkChoice(3, 0, new Player()));
        assertEquals(true, jsonReader.checkRequire(3, 0, new Player()));
    }
}