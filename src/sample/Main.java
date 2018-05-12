package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.shape.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.Group;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.application.Application;

public class Main extends Application {

    public boolean checkBox(TextField box1[][][])
    {
        boolean works = true;
        //################### BOX CHECK
        bigloop:
        for (int big = 0; big < 9; big++)
        {
            boolean[] numcheck = new boolean[9];
            for (int i = 0; i < numcheck.length; i++)
            {
                numcheck[i] = false;
            }
            for (int k = 0; k < 3; k++)      //goes over all the points in each of the 3x3 boxes. for check button
            {
                for (int g = 0; g < 3; g++)
                {
                    numcheck[Integer.valueOf(box1[big][k][g].getText()) - 1] = !numcheck[Integer.valueOf(box1[big][k][g].getText()) - 1];
                }
            }
            for (int i = 0; i < numcheck.length; i++)
            {
                if (!numcheck[i])
                {
                    works = false;
                    break bigloop;
                }
            }
        }
        return works;     //returns the result if it works or not
    }

    public boolean checkRow(TextField box1[][][]) {
        boolean works = true;
        bigloop:
        for (int g = 0; g < 3; g++) {
            boolean[] numcheck = new boolean[9];
            for (int big = 0; big < 9; big++)      //goes over the numbers in each row makes. for check button
            {
                for (int k = 0; k < 3; k++) {
                    numcheck[Integer.valueOf(box1[big][g][k].getText()) - 1] = !numcheck[Integer.valueOf(box1[big][g][k].getText()) - 1];
                }
            }
            for (int i = 0; i < numcheck.length; i++) {
                if (!numcheck[i]) {
                    works = false;
                    break bigloop;
                }
            }
        }
        return works;
    }

    //################### VERTICAL CHECK
    public boolean checkCol(TextField box1[][][])
    {
        boolean works = true;
        /**/bigloop:
    for (int g = 0; g < 3; g++)
    {
        boolean[] numcheck = new boolean[9];     //check if all the individual columns are solved. for check button
        for (int big = 0; big < 9; big++)
        {
            for (int k = 0; k < 3; k++)
            {
                numcheck[Integer.valueOf(box1[big][k][g].getText()) - 1] = !numcheck[Integer.valueOf(box1[big][k][g].getText()) - 1];
            }
        }
        for (int i = 0; i < numcheck.length; i++)
        {
            if (!numcheck[i])
            {
                works = false;
                break bigloop;
            }
        }
    }
        return works;
        //################### END VERTICAL CHECK
    }

    public boolean boxCoord(TextField box1[][][], int box, int y, int x)
    {
        int count = 0;
        int enteredvalue = Integer.valueOf(box1[box][y][x].getText());
        for (int k = 0; k < 3; k++)
        {
            for (int b = 0; b < 3; b++)
            {
                if (enteredvalue == Integer.valueOf(String.valueOf(box1[box][k][b].getText())))
                {
                    count++;  //found a number similar to the value
                }
            }
        }
        if (count == 1)
        {
            return true;    //the value is valid for the box
        }
        else
        {
            return false;   //the value is invalid for the box
        }
    }

    //checks if the value makes the column invalid
    public boolean colCoord(TextField box1[][][], int box, int y, int x)
    {
        int enteredvalue = Integer.valueOf(box1[box][y][x].getText());
        int count = 0;
        int bigcol = (box % 3);             //way to search the column only through the specified 3x3 boxes
        for (int big = 0; big < 9; big++)
        {
            if (big % 3 == bigcol)
            {
                for (int k = 0; k < 3; k++)
                {
                    if (enteredvalue == Integer.valueOf(String.valueOf(box1[big][k][x].getText())))
                    {
                        count++;  //found a number similar to the value
                    }
                }
            }
        }
        if (count == 1)
        {
            return true;  //te column is valid
        }
        else
        {
            return false;  //the column is invalid
        }
    }

    public boolean rowCoord(TextField box1[][][], int box, int y, int x)
    {
        int count = 0;  //counts the repetitions of the value
        int bigstart = 0;

        //finds the row of boxes for the specified row
        if (box <= 2)
        {
            bigstart = 0;
        }
        if (box > 2 && box <= 5)
        {
            bigstart = 3;
        }
        if (box > 5 && box <= 8)
        {
            bigstart = 6;
        }


        int enteredvalue = Integer.valueOf(box1[box][y][x].getText());   //the value it is checking for now
        for (int big = bigstart; big <= (bigstart + 2); big++)
        {
            for (int k = 0; k < 3; k++)
            {
                if (enteredvalue == Integer.valueOf(String.valueOf(box1[big][y][k].getText())))  //checks if num in textfield matches the entered
                {
                    count++;   //counts another repetition found
                }
            }
        }
        if (count == 1)   //if there is only one of the value true
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean checkCoord(TextField box1[][][], int big, int y, int x, int value)    //takes coordinates for a point and a value
    {                                                                                    //plugs the value in and checks for violations
        int original = Integer.valueOf(box1[big][y][x].getText());    //saves original value to not change the board

        box1[big][y][x].setText(String.valueOf(value));
        if (colCoord(box1, big, y, x) && boxCoord(box1, big, y, x) && rowCoord(box1, big, y, x))   //checks if all conditions return true
        {
            box1[big][y][x].setText(String.valueOf(original));  //puts back original value
            return true;   										//did not violate
        }
        else
        {
            box1[big][y][x].setText(String.valueOf(original));  //puts back original value
            return false; 										//violated
        }
    }

    public String nextCell(TextField box1[][][])    //finds the next empty cell in the program
    {
        for (int big = 0; big < 9; big++)      //cycles through all the 3x3 squares
        {
            for (int h = 0; h < 3; h++)       //cycles through the h in every 3x3
            {
                for (int k = 0; k < 3; k++)    //cycles through the k in every 3x3
                {
                    String value = box1[big][h][k].getText();
                    if (value.length() == 0 || Integer.valueOf(value) == 0)    //check if the cell it is currently on is empty
                    {
                        String coords = String.valueOf(big) + String.valueOf(h) + String.valueOf(k);  //if it is it'll take the coordinates
                        return coords;                                                             //connect them in a string an return them
                    }
                }
            }
        }
        return "9999";    //returns an impossible coordinate if there are no empty cells left
    }

    //checks that the inputs don't aren't invalid
    public boolean checkGiven(TextField box1[][][])
    {
        for (int big = 0; big < 9; big++)
        {
            for (int y = 0; y < 3; y++)
            {
                for (int x = 0; x < 3; x++)
                {
                    String value = box1[big][y][x].getText();
                    if (!(value.length() == 0 || Integer.valueOf(value) == 0))
                    {
                        if (checkCoord(box1, big, y, x, Integer.valueOf(value)) == false)
                        {
                            return false;   //if there are more than one of the same input in a row/column/box of 3x3
                        }
                    }
                }
            }
        }
        return true;  //if the inputs are valid
    }

    public boolean solve(TextField box1[][][], int big, int y, int x)
    {
        if (Integer.valueOf(box1[big][y][x].getText()) != 0)        //checks if the textfield is not empty
        {
            String coords = nextCell(box1);                         //if so gets next cell
            if (coords.length() == 4)
            {
                return true;                                        //returns true if there are no more cells left
            }
            big = coords.charAt(0) - 48;
            y = coords.charAt(1) - 48;                              //-48 to convert from unicode to numbers
            x = coords.charAt(2) - 48;                              //gets the new coordinates for the new textfield
        }

        for (int atmpt = 1; atmpt <= 9; atmpt++)                    //cycles through the possible numbers
        {
            if (checkCoord(box1, big, y, x, atmpt))                 //if an attempt doesnt immediatly crash the board
            {
                box1[big][y][x].setText(String.valueOf(atmpt));     //puts the attempt in the checkbox
                //pack.getChildren().add(box1[big][y][x]);

                if (solve(box1, 0, 0, 0))                           //send the modified board to the beginning of the method
                {
                    return true;                                    //goes through "rabbit hole". If last var in board
                }                                                   //returns true, all values before return true

                box1[big][y][x].setText("0");             //atmpt resulted in the board crashing further down which will reset it and
            }                                             //try the next atmpt
        }
        return false;
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) throws Exception
    {
        try
        {
            primaryStage.setTitle("sudoku solver");     //title for the program
            Group root = new Group();
            Scene scene = new Scene(root, 380, 265, Color.BLACK);       //size for the program window
            Group pack = new Group();


            //################### START BOARD CREATION
            int spacing = 25;
            int spacing2 = 78;
            TextField[][][] box1 = new TextField[9][3][3];

            for (int big = 0; big < 3; big++)
            {
                for (int r = 0; r < 3; r++)
                {
                    for (int d = 0; d < 3; d++)
                    {

                        box1[big][r][d] = new TextField();
                        box1[big][r][d].setLayoutX(d * spacing + (spacing2 * (big) + 2));
                        box1[big][r][d].setLayoutY(r * spacing + 2);                              //layout for the thextfields
                        box1[big][r][d].setPrefWidth(25);
                        pack.getChildren().addAll(box1[big][r][d]);
                    }
                }

            }
            for (int big = 3; big < 6; big++)
            {
                for (int r = 0; r < 3; r++)
                {
                    for (int d = 0; d < 3; d++)
                    {

                        box1[big][r][d] = new TextField();
                        box1[big][r][d].setLayoutX(d * spacing + (spacing2 * (big - 3) + 2));
                        box1[big][r][d].setLayoutY(r * spacing + spacing2 + 2);                 //layout for the textfields
                        box1[big][r][d].setPrefWidth(25);
                        pack.getChildren().addAll(box1[big][r][d]);
                    }
                }

            }
            for (int big = 6; big < 9; big++)
            {
                for (int r = 0; r < 3; r++)
                {
                    for (int d = 0; d < 3; d++)
                    {

                        box1[big][r][d] = new TextField();
                        box1[big][r][d].setLayoutX(d * spacing + (spacing2 * (big - 6) + 2));    //layout for the textfields
                        box1[big][r][d].setLayoutY(r * spacing + spacing2 * 2 + 2);
                        box1[big][r][d].setPrefWidth(25);
                        pack.getChildren().addAll(box1[big][r][d]);
                    }
                }

            }

            Button check = new Button("check");
            Text list = new Text();
            list.setFont(Font.font("Verdana", 13));
            list.setLayoutX(240);
            list.setLayoutY(15);
            list.setFill(Color.WHITE);
            list.setText("Boxes Solved: " + "\nClmns Solved:" + "\nRows  Solved:" + "\n\nBoard Solved:");   //list of facotrs

            Button solve = new Button("solve");

            solve.setLayoutX(135);
            solve.setLayoutY(235);
            check.setLayoutX(55);
            check.setLayoutY(235);
            pack.getChildren().addAll(check, list, solve);
            //################### END BOARD CREATION


            check.setOnAction(new EventHandler<ActionEvent>() {
                @Override public void handle(ActionEvent e) {
                    for (int big = 0; big < 9; big++)
                    {
                        for (int y = 0; y < 3; y++)
                        {
                            for (int x = 0; x < 3; x++)
                            {
                                if (!checkCoord(box1, big, y, x, Integer.valueOf(box1[big][y][x].getText())))
                                {
                                    box1[big][y][x].setStyle("-fx-text-fill: red");
                                }
                                else
                                {
                                    box1[big][y][x].setStyle("-fx-text-fill: black");
                                }
                            }
                        }
                    }


                    //###################### makes up the list of checked items on the right

                    boolean works = false;
                    if (checkBox(box1) && checkRow(box1) && checkCol(box1))
                    {
                        works = true;
                    }

                    Text bxs = new Text();
                    bxs.setFont(Font.font("Verdana", 13));
                    bxs.setLayoutX(340);
                    bxs.setLayoutY(15);
                    if (checkBox(box1))
                    {
                        bxs.setFill(Color.GREEN);
                    }
                    else
                    {
                        bxs.setFill(Color.RED);
                    }
                    bxs.setText(String.valueOf(checkBox(box1)));


                    Text col = new Text();
                    col.setFont(Font.font("Verdana", 13));
                    col.setLayoutX(340);
                    col.setLayoutY(15);
                    if (checkCol(box1))
                    {
                        col.setFill(Color.GREEN);
                    }
                    else
                    {
                        col.setFill(Color.RED);
                    }
                    col.setText("\n" + String.valueOf(checkCol(box1)));

                    Rectangle hideOld = new Rectangle();
                    hideOld.setLayoutX(335);
                    hideOld.setLayoutY(0);
                    hideOld.setWidth(50);
                    hideOld.setHeight(85);
                    hideOld.setFill(Color.BLACK);

                    Text row = new Text();
                    row.setFont(Font.font("Verdana", 13));
                    row.setLayoutX(340);
                    row.setLayoutY(15);
                    if (checkRow(box1))
                    {
                        row.setFill(Color.GREEN);
                    }
                    else
                    {
                        row.setFill(Color.RED);
                    }
                    row.setText("\n\n" + String.valueOf(checkRow(box1)));


                    Text total = new Text();
                    total.setFont(Font.font("Verdana", 13));
                    total.setLayoutX(340);
                    total.setLayoutY(15);
                    if (works)
                    {
                        total.setFill(Color.GREEN);
                    }
                    else
                    {
                        total.setFill(Color.RED);
                    }
                    total.setText("\n\n\n\n" + String.valueOf(works));

                    pack.getChildren().addAll(hideOld, bxs, col, row, total);

                    //#################################### end of the list of checked items on the right
                }
            });


            solve.setOnAction(new EventHandler<ActionEvent>() {
                @Override public void handle(ActionEvent e) {


                    for (int big = 0; big < 9; big++)
                    {
                        for (int y = 0; y < 3; y++)         //cycles through all the textfields on the board
                        {
                            for (int x = 0; x < 3; x++)
                            {
                                String value = box1[big][y][x].getText();
                                if (value.length() == 0 || value == null)   //fills all the empty spaces on the board with 0
                                {
                                    box1[big][y][x].setText("0");
                                }
                            }
                        }
                    }


                    if (checkGiven(box1)) //checks that there arent multiple values as an input in a row/box/column
                    {
                        if (solve(box1, 0, 0 ,0))   //sends the board to solve method
                        {
                            System.out.println("SUDOKU COMPLETE");
                        }
                        else
                        {
                            System.out.println("WAS NOT ABLE TO SOLVE");
                        }
                    }
                    else
                    {
                        System.out.println("BAD INPUT");    //bad input
                    }
                }
            });

            root.getChildren().add(pack);            //submits the shapes
            primaryStage.setScene(scene);         //sets the scene
            primaryStage.show();              //shows the final image
        }
        catch (Exception e)
        {

        }
    }
}
