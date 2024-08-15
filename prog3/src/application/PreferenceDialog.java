package application;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;

public class PreferenceDialog extends JDialog {
    private JTextField preferenceIdField;
    private JTextField preferenceTypeField;
    private JTextField additionField;
    private preference preference;
    private int companyId;

    public PreferenceDialog(Frame parent,Integer companyId) {
        super(parent, "Create New Preference", true);
        setLayout(new GridLayout(4, 2));
        this.companyId=companyId;

        add(new JLabel("Preference Type:"));
        preferenceTypeField = new JTextField();
        add(preferenceTypeField);

        add(new JLabel("Addition:"));
        additionField = new JTextField();
        add(additionField);

        JButton createButton = new JButton("Create");
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String preferenceType = preferenceTypeField.getText();
                double addition = Double.parseDouble(additionField.getText());
                
                System.out.println(preferenceType);
                System.out.println(addition);
                preference newPref = new preference(preferenceType, addition);
                List<Integer> generatedIds = DatabaseConnection.insertPreferences(Arrays.asList(newPref),companyId);
                newPref.setId(generatedIds.get(0));
                dispose();
            }
        });
        add(createButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        add(cancelButton);

        pack();
        setLocationRelativeTo(parent);
    }

    public preference getPreference() {
        return preference;
    }

}
