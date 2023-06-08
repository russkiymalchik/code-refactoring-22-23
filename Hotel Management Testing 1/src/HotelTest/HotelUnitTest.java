package HotelTest;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

//import testing.Doubleroom;
//import testing.Food;
//import testing.HotelMain;
//import testing.Singleroom;
//import testing.holder;

class HotelUnitTest {

	private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOutput = System.out;
    private final InputStream originalInput = System.in;

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOutput);
        System.setIn(originalInput);
    }

    @Test
    public void testMainMethodWithUserInput_LuxuryDoubleRoom() {
        // Prepare the input
        String input = "1\n1\nn\n";

        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);

        // Call the method being tested
        HotelMain.main(new String[]{});

        // Verify the output
        // Verify the output
        String expectedOutput = "Welcome to Paradise Inn\n\nEnter your choice :\n1.Display room details\n2.Display room availability \n3.Book\n4.Order food\n5.Checkout\n6.Exit\n\n" +
                "\nChoose room type :\n1.Luxury Double Room \n2.Deluxe Double Room \n3.Luxury Single Room \n4.Deluxe Single Room\n" +
               
        		"\nNumber of double beds : 1\nAC : Yes\nFree breakfast : Yes\nCharge per day:4000 \n"+
                "\nContinue : (y/n)";

        assertEquals(expectedOutput, outputStream.toString().trim());
        
    }
    
    @Test
    public void testMainMethodWithUserInput_DeluxeDoubleRoom() {
        // Prepare the input
        String input = "1\n2\nn\n";

        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);

        // Call the method being tested
        HotelMain.main(new String[]{});

        // Verify the output
        // Verify the output
        String expectedOutput = "Welcome to Paradise Inn\n\nEnter your choice :\n1.Display room details\n2.Display room availability \n3.Book\n4.Order food\n5.Checkout\n6.Exit\n\n" +
                "\nChoose room type :\n1.Luxury Double Room \n2.Deluxe Double Room \n3.Luxury Single Room \n4.Deluxe Single Room\n" +
               
        		"\nNumber of double beds : 1\nAC : No\nFree breakfast : Yes\nCharge per day:3000 \n"+
                "\nContinue : (y/n)";

        assertEquals(expectedOutput, outputStream.toString().trim());
        
    }
    
    @Test
    public void testMainMethodWithUserInput_LuxurySingleRoom() {
        // Prepare the input
        String input = "1\n3\nn\n";

        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);

        // Call the method being tested
        HotelMain.main(new String[]{});

        // Verify the output
        // Verify the output
        String expectedOutput = "Welcome to Paradise Inn\n\nEnter your choice :\n1.Display room details\n2.Display room availability \n3.Book\n4.Order food\n5.Checkout\n6.Exit\n\n" +
                "\nChoose room type :\n1.Luxury Double Room \n2.Deluxe Double Room \n3.Luxury Single Room \n4.Deluxe Single Room\n" +
               
        		"\nNumber of single beds : 1\nAC : Yes\nFree breakfast : Yes\nCharge per day:2200  \n"+
                "\nContinue : (y/n)";

        assertEquals(expectedOutput, outputStream.toString().trim());
        
    }
    
    @Test
    public void testMainMethodWithUserInput_DeluxeSingleRoom() {
        // Prepare the input
        String input = "1\n3\nn\n";

        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);

        // Call the method being tested
        HotelMain.main(new String[]{});

        // Verify the output
        // Verify the output
        String expectedOutput = "Welcome to Paradise Inn\n\nEnter your choice :\n1.Display room details\n2.Display room availability \n3.Book\n4.Order food\n5.Checkout\n6.Exit\n\n" +
                "\nChoose room type :\n1.Luxury Double Room \n2.Deluxe Double Room \n3.Luxury Single Room \n4.Deluxe Single Room\n" +
               
        		"\nNumber of single beds : 1\nAC : Yes\nFree breakfast : Yes\nCharge per day:2200  \n"+
                "\nContinue : (y/n)";

        assertEquals(expectedOutput, outputStream.toString().trim());
        
    }
    
    @Test
    public void testFoodCreation() {
        // Create a Food object with item number 1 and quantity 5
        Food food = new Food(1, 5);

        // Verify the item number and quantity
        assertEquals(1, food.itemno);
        assertEquals(5, food.quantity);

        // Verify the price calculation
        assertEquals(250.0f, food.price);
    }
    
    @Test
    public void testFoodCreation2() {
        // Create a Food object with item number 1 and quantity 5
        Food food = new Food(2, 3);

        // Verify the item number and quantity
        assertEquals(2, food.itemno);
        assertEquals(3, food.quantity);

        // Verify the price calculation
        assertEquals(180.0f, food.price);
    }
    
    @Test
    public void testFoodPriceCalculation() {
        int itemNo = 1;
        int quantity = 3;

        Food food = new Food(itemNo, quantity);

        float expectedPrice = 150.0f;

        assertEquals(expectedPrice, food.getPrice());
    }
    
    @Test
    public void testFoodPriceCalculation2() {
        int itemNo = 2;
        int quantity = 7;

        Food food = new Food(itemNo, quantity);

        float expectedPrice = 420.0f;

        assertEquals(expectedPrice, food.getPrice());
    }

    @Test
    public void testSerialization() throws Exception {
        int itemNo = 2;
        int quantity = 2;

        Food food = new Food(itemNo, quantity);

        // Serialize the Food object
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.writeObject(food);
        objectOutputStream.flush();
        objectOutputStream.close();

        // Deserialize the Food object (just to show that serialization/deserialization works)
        // You can add more assertions to test the deserialized object if needed
        Food deserializedFood = (Food) new ObjectInputStream(new ByteArrayInputStream(outputStream.toByteArray())).readObject();

        assertEquals(food.getItemNo(), deserializedFood.getItemNo());
        assertEquals(food.getQuantity(), deserializedFood.getQuantity());
        assertEquals(food.getPrice(), deserializedFood.getPrice());
    }
    
    @Test
    public void testIndecentExposure_Doubleroom_name2() throws NoSuchFieldException {
        Field name2Field = Doubleroom.class.getDeclaredField("name2");
        assertFalse(name2Field.isAccessible());
    }
    
    @Test
    public void testIndecentExposure_Doubleroom_contact2() throws NoSuchFieldException {
        Field contact2Field = Doubleroom.class.getDeclaredField("contact2");
        assertFalse(contact2Field.isAccessible());
    }
    
    @Test
    public void testIndecentExposure_Doubleroom_gender2() throws NoSuchFieldException {
        Field gender2Field = Doubleroom.class.getDeclaredField("gender2");
        assertFalse(gender2Field.isAccessible());
    }
    
    @Test
    public void testConditionalComplexity() {
        // Prepare the input
        String userInput = "4\n61\nn";
        InputStream inputStream = new ByteArrayInputStream(userInput.getBytes());
        System.setIn(inputStream);

        // Capture the output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        // Call the method being tested
        HotelMain.main(new String[]{});

        // Verify the output
        String expectedOutput = "Welcome to Paradise Inn\n\nEnter your choice :\n1.Display room details\n2.Display room availability \n3.Book\n4.Order food\n5.Checkout\n6.Exit" +
        		"\n\nRoom Number -"+
        		"Room doesn't exist\n"+
        		"\nContinue : (y/n)";
        assertEquals(expectedOutput, outputStream.toString().trim());
    }
    
    @Test
    public void testDataClumps() {
    	holder holder = new holder();

        Doubleroom[] luxuryDoubleRooms = holder.getLuxuryDoubleRooms();
        Doubleroom[] deluxeDoubleRooms = holder.getDeluxeDoubleRooms();
        Singleroom[] luxurySingleRooms = holder.getLuxurySingleRooms();
        Singleroom[] deluxeSingleRooms = holder.getDeluxeSingleRooms();

        // Verify the relationships between the data clumps
        assertNotNull(luxuryDoubleRooms);
        assertNotNull(deluxeDoubleRooms);
        assertNotNull(luxurySingleRooms);
        assertNotNull(deluxeSingleRooms);

        // Perform assertions on the data clumps as needed
        assertEquals(10, luxuryDoubleRooms.length);
        assertEquals(20, deluxeDoubleRooms.length);
        assertEquals(10, luxurySingleRooms.length);
        assertEquals(20, deluxeSingleRooms.length);
    }
    
    @Test
    public void testDataClumps_LuxuryDoubleRooms() {
    	holder holder = new holder();

        Doubleroom[] luxuryDoubleRooms = holder.getLuxuryDoubleRooms();

        // Verify the relationships between the data clumps
        assertNotNull(luxuryDoubleRooms);

        // Perform assertions on the data clumps as needed
        assertEquals(10, luxuryDoubleRooms.length);
    }
    
    @Test
    public void testDataClumps_DeluxeDoubleRooms() {
    	holder holder = new holder();

        Doubleroom[] deluxeDoubleRooms = holder.getDeluxeDoubleRooms();

        // Verify the relationships between the data clumps
        assertNotNull(deluxeDoubleRooms);

        // Perform assertions on the data clumps as needed
        assertEquals(20, deluxeDoubleRooms.length);
    }
    
    @Test
    public void testDataClumps_LuxurySingleRooms() {
    	holder holder = new holder();
        Singleroom[] luxurySingleRooms = holder.getLuxurySingleRooms();

        // Verify the relationships between the data clumps
        assertNotNull(luxurySingleRooms);

        // Perform assertions on the data clumps as needed
        assertEquals(10, luxurySingleRooms.length);
    }
    
    @Test
    public void testDataClumps_DeluxeSingleRooms() {
    	holder holder = new holder();
        Singleroom[] deluxeSingleRooms = holder.getDeluxeSingleRooms();

        // Verify the relationships between the data clumps
        assertNotNull(deluxeSingleRooms);

        // Perform assertions on the data clumps as needed
        assertEquals(20, deluxeSingleRooms.length);
    }

}
