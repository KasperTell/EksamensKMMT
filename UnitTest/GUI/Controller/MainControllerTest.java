package GUI.Controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

class MainControllerTest {

    @Test
    void checkNameAndAddressFields() {

        //Arrange
        //Mock-up of TextFields
        String[] textFields = {"", "", ""};
        String[] field = {"Peter", "Johansen", "Bogade 12"};

        String[] errorText = {"Error in first Name","Error in Last Name","Error in Address"};

        List<String> actualLines = new ArrayList<>();

        //Act
        for (int i = 0; i < 3; i++) {
            if (!textFields[i].equals(field[i]))
                textFields[i] = errorText[i];
            actualLines.add(textFields[i]);
        }

        //Assert
        List<String> expectedLines = new ArrayList<>();
        expectedLines.add("Error in first Name");
        expectedLines.add("Error in Last Name");
        expectedLines.add("Error in Address");

        Assertions.assertLinesMatch(expectedLines, actualLines);
    }
}
