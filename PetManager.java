/**
 * PetManager class for CSC212 :: hw4p1
 *
 * @author      Devon Blandin
 * @contact     dblandin@gmail.com
 * @date        7/10/12
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.Arrays;
import javax.swing.table.DefaultTableModel;

public class PetManager extends JFrame
{
    // frame
    private JPanel controlsP;
    private JPanel viewerP;
    private final int WIDTH;
    private final int HEIGHT;

    // arrayList
    ArrayList<Pet> pets;

    // text fields
    private final JTextField nameTF;
    private final JTextField ageTF;
    private final JTextField countTF;

    // combo boxes
    private final JComboBox genderCB;

    // buttons
    private final JButton addPetB;
    private final JButton doneB;

    // labels
    private final JLabel nameL;
    private final JLabel ageL;
    private final JLabel countL;
    private final JLabel genderL;

    // tables
    private final JTable viewerT;

    // scroll panes
    private final JScrollPane viewerSP;

    // event handlers
    private ButtonHandler buttonHandler;

    // TableModels
    DefaultTableModel viewerTM;

    public static void main(String[] args)
    {
        PetManager petManager = new PetManager();
    } // end main

    // default constructor
    public PetManager()
    {
        // frames
        super("Pet Manager");
        this.WIDTH = 500;
        this.HEIGHT = 400;
        this.setSize(WIDTH, HEIGHT + 40);
        this.setLayout(new GridLayout(2, 1));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // panels
        this.controlsP = new JPanel(new GridLayout(5, 2));
        this.controlsP.setSize(WIDTH, HEIGHT/2);
        this.controlsP.setBackground(Color.WHITE);
        this.viewerP = new JPanel();
        this.viewerP.setSize(WIDTH, HEIGHT/2);
        this.viewerP.setBackground(Color.WHITE);

        // text fields
        this.nameTF = new JTextField();
        this.ageTF = new JTextField();
        this.countTF = new JTextField();
        this.countTF.setEnabled(false);

        // combo boxes
        this.genderCB = new JComboBox(new String[] {"Male", "Female"});

        // labels
        this.nameL = new JLabel("Name:", SwingConstants.RIGHT);
        this.ageL = new JLabel("Age:", SwingConstants.RIGHT);
        this.countL = new JLabel("Pets Count:", SwingConstants.RIGHT);
        this.genderL = new JLabel("Gender:", SwingConstants.RIGHT);

        // buttons
        this.addPetB = new JButton("Add Pet");
        this.doneB = new JButton("Saved");

        // data
        this.pets = new ArrayList<Pet>();
        this.readInPets();
        
        String[] columnNames = { "#", "Name", "Gender", "Age" };

        String[][] data = new String[this.pets.size()][4];

        for (int i = 0; i < this.pets.size(); i++)
        {
            data[i][0] = "" + (i + 1);
            data[i][1] = pets.get(i).getName();
            data[i][2] = "" + pets.get(i).getGender();
            data[i][3] = "" + pets.get(i).getAge();
        }

        // table models
        this.viewerTM = new DefaultTableModel(data, columnNames) {

            public boolean isCellEditable(int row, int column) {
               //all cells false
               return false;
            }
        };

        // table
        this.viewerT = new JTable(viewerTM);
        this.viewerT.setFillsViewportHeight(true);

        // scroll pane
        this.viewerSP = new JScrollPane(viewerT);
        viewerSP.setPreferredSize(viewerP.getSize());

        // add name
        controlsP.add(nameL);
        controlsP.add(nameTF);

        // add age
        controlsP.add(ageL);
        controlsP.add(ageTF);

        // add gender
        controlsP.add(genderL);
        controlsP.add(genderCB);

         // setup handlers
        buttonHandler = new ButtonHandler();

        // add buttons
        controlsP.add(addPetB);
        addPetB.addActionListener(buttonHandler);
        
        controlsP.add(doneB);
        doneB.setEnabled(false);
        doneB.addActionListener(buttonHandler);

        // add pet count
        controlsP.add(countL);
        controlsP.add(countTF);
        countTF.setText("" + pets.size());

        // add panels to frame
        this.add(controlsP);
        this.add(viewerP);

        // add scroll pane to viewer panel
        this.viewerP.add(viewerSP, BorderLayout.CENTER);

        // show frame
        this.setVisible(true);
        
    } // end PetManager constructor

    private void readInPets()
    {
        // try connecting to the pets.tsv file
        try
        {
            FileReader fileReader = new FileReader("pets.tsv");
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line = null;
            int lineCount = 0;

            bufferedReader.readLine();
            while ( (line = bufferedReader.readLine()) != null )
            {
                String name;
                int age;
                char gender;

                // split line into an array
                String[] splitLine = line.split("\t");

                // set name
                name = splitLine[0];

                // parse and get age
                try
                {
                    age = Integer.parseInt(splitLine[2]);
                }
                catch (Exception ex)
                {
                    age = -1;
                }

                // parse and get gender
                try
                {
                    gender = splitLine[1].charAt(0);
                }
                catch (Exception ex)
                {
                    gender = 'U';
                }

                // add the new pet to the ArrayList
                pets.add(new Pet(name, gender, age));

                // increase line count
                lineCount++;
            }
        }
        catch (Exception ex)
        {
            System.out.println("error reading file. might not exist: " + ex);
        }
    } // end method readInPets
    private void clearInputs()
    {
        nameTF.setText("");
        ageTF.setText("");
        genderCB.setSelectedIndex(0);
    } // end method clearInputs

    private class ButtonHandler implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            // check what button was pushed
            // if add pet button was pushed...
            if (e.getSource() == addPetB)
            {
                // get name
                String name = nameTF.getText();

                // parse and get age
                int age;
                try
                {
                    age = Integer.parseInt(ageTF.getText());
                }
                catch (Exception ex)
                {
                    age = -1;
                }

                // parse and get gender
                char gender;
                if (genderCB.getSelectedItem() == "Male")
                    gender = 'M';
                else
                    gender = 'F';

                // add new pet to ArrayList and to display table
                pets.add(new Pet(name, gender, age));
                viewerTM.insertRow(viewerTM.getRowCount(), 
                                   new Object[]{
                                        pets.size(), 
                                        pets.get(pets.size() - 1).getName(), 
                                        pets.get(pets.size() - 1).getGender(), 
                                        pets.get(pets.size() - 1).getAge()}
                                    );

                // update count TF
                countTF.setText("" + pets.size());
                
                // clear the inputs
                clearInputs();

                // enable save button
                doneB.setEnabled(true);
                doneB.setText("Save");
            }

            // otherwise, if done button was pushed...
            else
            {
                doneB.setText("Saving...");
                
                // write pets to file
                try
                {
                    FileWriter fileWriter = new FileWriter("pets.tsv");
                    BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

                    bufferedWriter.write("name\tgender\tage\n");

                    for (Pet pet : pets)
                    {
                        bufferedWriter.write(pet.toString() + "\n");
                    }

                    bufferedWriter.close();
                    fileWriter.close();
                    clearInputs();
                }
                catch (Exception ex)
                {
                    System.out.println("error saving: " + ex);
                }
                doneB.setText("Saved");
                doneB.setEnabled(false);
            }

        } //end method actionPerformed
    } //end class ButtonHandler

} // end class PetManager