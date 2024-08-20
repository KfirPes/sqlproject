package application;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class CompanyFrame extends JFrame {
    private JComboBox<company> companyComboBox;

    public CompanyFrame(List<company> companies) {
        setTitle("Select Company");
        setSize(300, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new FlowLayout(FlowLayout.LEFT));

        companyComboBox = new JComboBox<>();
        for (company company : companies) {
            companyComboBox.addItem(company);
        }

        companyComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                company selectedCompany = (company) companyComboBox.getSelectedItem();
                if (selectedCompany != null) {
                    System.out.println("Selected Company ID: " + selectedCompany.getId());
                }
            }
        });

        add(companyComboBox);
    }
}