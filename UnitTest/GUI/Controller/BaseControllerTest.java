package GUI.Controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


class BaseControllerTest {

    @Test
    void displayError() {

        //To test an abstract class 'Mockito Framework' has been added.

        //Triple A pattern. Arrange, Act, Assert.

        //Arrange
        BaseController baseController = Mockito.mock(BaseController.class, Mockito.CALLS_REAL_METHODS);

        //ACT
        Error error = Assertions.assertThrows(Error.class, () -> {
                baseController.displayError(new IllegalArgumentException());
        });

        //Assert

        String expectedMessage = "Something went wrong";
        String actualMessage = error.getMessage();

        Assertions.assertEquals(expectedMessage, actualMessage);
    }
}