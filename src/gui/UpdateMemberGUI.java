package gui;

import db.MainProgramOperations;
import model.Alley;
import model.EmailValidator;
import model.Member;
import model.NumberValidator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Created by Peter on 09/03/2015.
 */
public class UpdateMemberGUI implements ActionListener{
    private JDialog updateD;
    private ResultSet rSet;
    private MainProgramOperations progOps;
    private MemberTab mTab;
    private ArrayList<Member> memList = new ArrayList<Member>();
    private GuiElements ge;
    private JPanel updatePanel, bottomPanel;
    private JButton updateB, cancel;

    public UpdateMemberGUI(MemberTab mt, MainProgramOperations po, ArrayList<Member> m, String s) {
        System.out.println("Inside : UpdateMemberGUI");
        this.progOps = po;
        this.memList = m;
        this.mTab = mt;

        updateD = new JDialog();
        updateD.setTitle("Update Member");
        updateD.setSize(new Dimension(300, 400));
        updateD.setLocationRelativeTo(null);

        ge = new GuiElements();
        updatePanel = ge.membersGui();

        bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout());
        bottomPanel.setBorder(BorderFactory.createEtchedBorder());
        bottomPanel.add(Box.createRigidArea(new Dimension(40, 0)));
        bottomPanel.setBackground(Color.white);

        updateB = new JButton("Update");
        updateB.addActionListener(this);
        bottomPanel.add(updateB);

        cancel = new JButton("Cancel");
        cancel.addActionListener(this);
        bottomPanel.add(cancel);

        updatePanel.add(bottomPanel, BorderLayout.SOUTH);

        updatePanel.setBackground(Color.white);
        updateD.add(updatePanel);
        updateD.setVisible(true);

        fillFields(s);
    }

    public void fillFields(String s) {
        System.out.println("Inside : fillFields() in UpdateMemberGUI");
        try {
            rSet = progOps.searchMembers(s);
            while (rSet.next()) {
                ge.idTxt.setText(Integer.toString(rSet.getInt(1)));
                ge.lNameTxt.setText(rSet.getString(2));
                ge.fNameTxt.setText(rSet.getString(3));
                ge.genTxt.setText(rSet.getString(4));
                ge.phoneTxt.setText(rSet.getString(5));
                ge.emailTxt.setText(rSet.getString(6));
                ge.addTxt.setText(rSet.getString(7));
                ge.townTxt.setText(rSet.getString(8));
                ge.coCombo.setSelectedItem(rSet.getString(9));
                ge.visTxt.setText(Integer.toString(rSet.getInt(10)));
            }
        } catch (Exception e) {
        }
    }

    public void actionPerformed(ActionEvent e) {
        System.out.println("Inside : ActionPerformed() in UpdateMemberGUI");
        EmailValidator emailValidator = new EmailValidator();
        NumberValidator numValidator = new NumberValidator();
        if (e.getSource().equals(updateB)) {
            try {
                if (ge.fNameTxt.getText().equals("") || ge.lNameTxt.getText().equals("") || ge.phoneTxt.getText().equals("") ||
                        ge.emailTxt.getText().equals("") || ge.addTxt.getText().equals("") || ge.townTxt.getText().equals("")) {
                    JOptionPane.showMessageDialog(null,
                            "Fields cannot be blank");
                } else if (emailValidator.validate(ge.emailTxt.getText()) == false) {
                    JOptionPane.showMessageDialog(null, "Email address is not valid", "ERROR", JOptionPane.WARNING_MESSAGE);
                } else {
                    String fName = ge.fNameTxt.getText();
                    String lName = ge.lNameTxt.getText();
                    String gender = ge.genTxt.getText();
                    String phone = ge.phoneTxt.getText();
                    String email = ge.emailTxt.getText();
                    String add = ge.addTxt.getText();
                    String town = ge.townTxt.getText();
                    String co = ge.coCombo.getSelectedItem().toString();

                    if (numValidator.isNumeric(fName) == false && numValidator.isNumeric(lName) == false && numValidator.isNumeric(gender) == false
                            && numValidator.isNumeric(phone) == true && numValidator.isNumeric(email) == false && numValidator.isNumeric(add) == false
                            && numValidator.isNumeric(town) == false) {
                        progOps.updateMember(ge.idTxt.getText(), fName, lName, gender, phone, email, add, town, co);
                        Alley a = new Alley(progOps);
                        a.updateMember(new Member(fName, lName, gender, phone, email, add, town, co));
                        JOptionPane.showMessageDialog(null, "Updated Member Data Saved", "ERROR", JOptionPane.WARNING_MESSAGE);
                        mTab.refreshTable();
                        updateD.setVisible(false);
                    } else {
                        JOptionPane.showMessageDialog(null,
                                "Only Phone Field can be numeric", "ERROR", JOptionPane.WARNING_MESSAGE);
                    }
                }
            } catch (NumberFormatException nf) {
                JOptionPane.showMessageDialog(null, "Wrong data format", "ERROR", JOptionPane.WARNING_MESSAGE);
            }
        }
        if (e.getSource().equals(cancel)) {
            updateD.setVisible(false);
        }
    }
}