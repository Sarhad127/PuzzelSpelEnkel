import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Pussel extends JFrame implements ActionListener {
    private JButton[][] siffror = new JButton[4][4];
    private JButton blandningsknapp;
    private JButton Facit;
    private int m;
    private int n;
    public Pussel() {
        setTitle("Mitt Spel");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        Container knappPlatser = getContentPane();
        knappPlatser.setLayout(new BorderLayout());
        JPanel knapparna = new JPanel(new GridLayout(4, 4));
        blandningsknapp = new JButton("Bläddra");
        blandningsknapp.addActionListener(new BlandaKnappLyssnare());
        blandningsknapp.setBackground(Color.gray);
        knappPlatser.add(blandningsknapp, BorderLayout.SOUTH); //sätter in blandaknappen i containern
        Facit = new JButton("Facit");
        Facit.addActionListener(new FacitLyssnare());
        Facit.setBackground(Color.DARK_GRAY);
        Facit.setForeground(Color.white);
        knappPlatser.add(Facit, BorderLayout.NORTH);
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                siffror[i][j] = new JButton();
                siffror[i][j].addActionListener(this); //skapar upp siffrorna
                siffror[i][j].setBackground(Color.lightGray);
                siffror[i][j].setForeground(Color.black);
                knapparna.add(siffror[i][j]); // siffrorna sparas i knapparna panelen
            }
        }
        Blanda();
        knappPlatser.add(knapparna, BorderLayout.CENTER); // panelen sätts in i containern
        setVisible(true);
    }
    public void Blanda() {
        List<Integer> numbers = new ArrayList<>(); //heltal 0 - 15
        for (int i = 0; i < 16; i++) {
            numbers.add(i);
        }
        Collections.shuffle(numbers); //blandar siffrorna
        int index = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                int num = numbers.get(index); // hämtar värdet av numbers vid index och tilldelar den num
                if (num != 0) {
                    siffror[i][j].setText(String.valueOf(num)); //om värdet inte är noll sätts ny siffra
                } else {
                    siffror[i][j].setText("");
                    m = i; //sparar positionen av tom plats i m och n
                    n = j;
                }
                index++; // ökar index för att hämta nästa siffra
            }
        }
    }
    public void Drag(int i, int j) {
        boolean höger = (i == m) && (j == n + 1);
        boolean vänster = (i == m) && (j == n - 1);
        boolean under = (i == m + 1) && (j == n);
        boolean över = (i == m - 1) && (j == n);
        if (höger || vänster || under || över) {
            siffror[m][n].setText(siffror[i][j].getText()); //tomplats får siffra
            siffror[i][j].setText(""); //siffran får tomplats
            m = i; //nollställer tomplats
            n = j;
            if (siffror[m][n].equals("")){
                siffror[m][n].setBackground(Color.green);
            }
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (siffror[i][j] == e.getSource()) {
                    Drag(i, j);
                    if (harKlaratSpelet()) {
                        JOptionPane.showMessageDialog(null, "Du har klarat spelet.");
                    }
                }
            }
        }
    }
    public boolean harKlaratSpelet() {
        int nummer = 1;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                String siffra = siffror[i][j].getText();
                if (!siffra.equals("") && Integer.parseInt(siffra) != nummer) {
                    return false;
                }
                nummer++;
            }
        }
        return true;
    }
    private class BlandaKnappLyssnare implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Blanda();
        }
    }
    private class FacitLyssnare implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Färdig();
            harKlaratSpelet();
        }
    }
    public void Färdig() {
        int num = 1;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (num <= 14) {
                    siffror[i][j].setText(String.valueOf(num));
                    num++;
                } else if (num == 15) {
                    siffror[i][j].setText(String.valueOf(""));
                    m = i;
                    n = j;
                    num++;
                } else if (num == 16) {
                    siffror[i][j].setText(String.valueOf(15));
                }
            }
        }
    }
}





